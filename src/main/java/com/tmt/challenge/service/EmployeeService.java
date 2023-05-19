package com.tmt.challenge.service;

import com.tmt.challenge.constant.enums.Operator;
import com.tmt.challenge.constant.enums.SearchOperation;
import com.tmt.challenge.dto.AssignmentDTO;
import com.tmt.challenge.dto.EmployeeAddressDTO;
import com.tmt.challenge.dto.EmployeeDTO;
import com.tmt.challenge.dto.response.DefaultResponseDTO;
import com.tmt.challenge.dto.response.SearchResponseDTO;
import com.tmt.challenge.exception.ResourceNotFoundException;
import com.tmt.challenge.mapper.AssignmentMapper;
import com.tmt.challenge.mapper.EmployeeAddressMapper;
import com.tmt.challenge.mapper.EmployeeMapper;
import com.tmt.challenge.model.Assignment;
import com.tmt.challenge.model.Department;
import com.tmt.challenge.model.Employee;
import com.tmt.challenge.model.EmployeeAddress;
import com.tmt.challenge.model.composite.EmployeeId;
import com.tmt.challenge.repository.DepartmentRepository;
import com.tmt.challenge.repository.EmployeeRepository;
import com.tmt.challenge.repository.specs.EmployeeSpecification;
import com.tmt.challenge.repository.specs.SearchCriteria;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class EmployeeService {

    private final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeMapper employeeMapper;
    private final EmployeeAddressMapper employeeAddressMapper;
    private final AssignmentMapper assignmentMapper;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.employeeMapper = Mappers.getMapper(EmployeeMapper.class);
        this.employeeAddressMapper = Mappers.getMapper(EmployeeAddressMapper.class);
        this.assignmentMapper = Mappers.getMapper(AssignmentMapper.class);
    }

    /**
     * {@code Employee Specs private method} : create an employee specification from a keyword
     *
     * @param keyword the first input string
     * @param date the second input date
     * @return return an employee specification
     * */
    private EmployeeSpecification employeeSpecification(String keyword, Date date) {
        EmployeeSpecification employeeSpecification = new EmployeeSpecification();
        if (!keyword.equals("") && (date != null)) {
            employeeSpecification.add(new SearchCriteria("title", keyword, SearchOperation.MATCH, "assignments", null, null));
            employeeSpecification.add(new SearchCriteria("startDate", date, SearchOperation.DATE_LESS_THAN_EQUAL, "assignments", null, null));
            employeeSpecification.add(new SearchCriteria("endDate", date, SearchOperation.DATE_GREATER_THAN_EQUAL, "assignments", null, null));
        } else if (!keyword.equals("")) {
            employeeSpecification.add(new SearchCriteria("title", keyword, SearchOperation.MATCH, "assignments", null, null));
        } else if (date != null) {
            employeeSpecification.add(new SearchCriteria("startDate", date, SearchOperation.DATE_LESS_THAN_EQUAL, "assignments", null, null));
            employeeSpecification.add(new SearchCriteria("endDate", date, SearchOperation.DATE_GREATER_THAN_EQUAL, "assignments", null, null));
        }
        employeeSpecification.operator(Operator.AND);
        return employeeSpecification;
    }

    /**
     * {@code Employee Specs 2 private method} : create an employee specification from a keyword between dates
     *
     * @param keyword the first input string
     * @param startDate the second input date
     * @param endDate the second input date
     * @return return an employee specification
     * */
    private EmployeeSpecification employeeSpecification2(String keyword, Date startDate, Date endDate) {
        EmployeeSpecification employeeSpecification = new EmployeeSpecification();
        if (!keyword.equals("") && (startDate != null) && (endDate != null)) {
            employeeSpecification.add(new SearchCriteria("title", keyword, SearchOperation.MATCH, "assignments", null, null));
            employeeSpecification.add(new SearchCriteria("startDate", null, SearchOperation.DATE_BETWEEN, "assignments", startDate, endDate));
        } else if (!keyword.equals("")) {
            employeeSpecification.add(new SearchCriteria("title", keyword, SearchOperation.MATCH, "assignments", null, null));
        } else if (startDate != null || endDate != null) {
            employeeSpecification.add(new SearchCriteria("startDate", null, SearchOperation.DATE_BETWEEN, "assignments", startDate, endDate));
        }
        employeeSpecification.operator(Operator.AND);
        return employeeSpecification;
    }

    /**
     * {@code employee service get method} : GET All Employees
     *
     * @return return employee DTO list
     * */
    @Transactional(readOnly = true)
    public List<EmployeeDTO> getAll() {
        List<Employee> employees = employeeRepository.findAll();
        return employeeMapper.toEmployeeDTO(employees);
    }

    /**
     * {@code employee service get method} : GET Employee by department ID and employee ID
     *
     * @param departmentId the first input long
     * @param employeeId the second input long
     * @return return a response entity of employee DTO
     * */
    @Transactional(readOnly = true)
    public EmployeeDTO get(Long departmentId, Long employeeId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("department id didn't exist"));
        Optional<Employee> employeeOptional = employeeRepository.findById(new EmployeeId(department, employeeId));
        if (employeeOptional.isEmpty()) {
            throw new ResourceNotFoundException("employee not found");
        }
        Employee employee = employeeOptional.get();
        return employeeMapper.toEmployeeDTO(employee);
    }

    /**
     * {@code employee service create method} : CREATE New Employee
     *
     * @param employeeDTO the first input employee DTO
     * @return return an employee DTO
     * */
    public EmployeeDTO create(EmployeeDTO employeeDTO) {
        Optional<Department> employeeDepartment = departmentRepository.findById(employeeDTO.getDepartmentId());
        if (employeeDepartment.isEmpty()) {
            throw new IllegalStateException("department id didn't exist");
        }

        Optional<Employee> employeeWithEmail = employeeRepository.findEmployeeByEmail(employeeDTO.getEmail());
        if (employeeWithEmail.isPresent()) {
            throw new IllegalStateException("email already exist");
        }

        Optional<Employee> employeeWithPhone = employeeRepository.findEmployeeByPhoneNumber(employeeDTO.getPhoneNumber());
        if (employeeWithPhone.isPresent()) {
            throw new IllegalStateException("phone already exist");
        }

        employeeDTO.getAssignments().forEach(x -> {
            Date startDate = x.getStartDate();
            Date endDate = x.getEndDate();
            if (startDate.compareTo(endDate) > 0) {
                throw new IllegalStateException("startDate greater than endDate");
            }
        });
        Employee employee = employeeMapper.toEmployeeEntity((employeeDTO));

        //Address Reference increment
        long employeeAddressRef = employeeRepository.findLastAddressRefNumber().orElse(0L);
        employeeAddressRef += 1;
        employee.setAddressRef(employeeAddressRef);

        List<EmployeeAddressDTO> addressDTOS = employeeDTO.getAddress();
        List<EmployeeAddress> addresses = employeeAddressMapper.toEmployeeAddressEntity(addressDTOS);
        addresses.forEach(x -> x.setEmployee(employee));
        employee.setAddress(addresses);

        List<AssignmentDTO> assignmentDTOS = employeeDTO.getAssignments();
        List<Assignment> assignments = assignmentMapper.toAssignmentEntity(assignmentDTOS);
        assignments.forEach(x -> x.setEmployee(employee));
        employee.setAssignments(assignments);

        Employee savedData = employeeRepository.save(employee);
        EmployeeDTO response = employeeMapper.toEmployeeDTO(savedData);
        response.setDepartmentName(employeeDepartment.get().getName());
        return response;
    }

    /**
     * {@code employee service update method} : UPDATE employee
     *
     * @param employeeDTO the first input long
     * @return return a default response DTO
     * */
    public DefaultResponseDTO update(EmployeeDTO employeeDTO) {
        Long departmentId = employeeDTO.getDepartmentId();
        Long employeeId = employeeDTO.getEmployeeId();

        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("department id didn't exist"));
        Employee employee = employeeRepository.findById(new EmployeeId(department, employeeId))
                .orElseThrow(() -> new ResourceNotFoundException("employee not found"));

        String email = employeeDTO.getEmail();
        String name = employeeDTO.getName();
        String phoneNumber = employeeDTO.getPhoneNumber();
        employee.setEmploymentStatus(employeeDTO.getEmploymentStatus());

        List<EmployeeAddressDTO> addressDTOS = employeeDTO.getAddress();
        List<EmployeeAddress> address = employeeAddressMapper.toEmployeeAddressEntity(addressDTOS);
        address.forEach(x -> x.setEmployee(employee));

        List<AssignmentDTO> assignmentDTOS = employeeDTO.getAssignments();
        List<Assignment> assignments = assignmentMapper.toAssignmentEntity(assignmentDTOS);
        assignments.forEach(x -> x.setEmployee(employee));

        DefaultResponseDTO defaultResponseDTO = new DefaultResponseDTO();
        String message = "request success. but, nothing changed";

        if (email != null && email.length() > 0 && !Objects.equals(employee.getEmail(), email)) {
            employee.setEmail(email);
            message = "resource updated successfully";
        }
        if (name != null && name.length() > 0 && !Objects.equals(employee.getName(), name)) {
            employee.setName(name);
            message = "resource updated successfully";
        }
        if (phoneNumber != null && phoneNumber.length() > 0 && !Objects.equals(employee.getPhoneNumber(), phoneNumber)) {
            employee.setPhoneNumber(phoneNumber);
            message = "resource updated successfully";
        }
        if (!Objects.equals(employee.getAddress(), address)) {
            address.forEach(x -> logger.info("New address added: { " + x.getName() + " }"));
            employee.getAddress().clear();
            employee.getAddress().addAll(address);
            message = "resource updated successfully";
        }
        if (!Objects.equals(employee.getAssignments(), assignments)) {
            employee.setAssignments(assignments);
            message = "resource updated successfully";
        }

        defaultResponseDTO.setStatus(HttpStatus.ACCEPTED.value());
        defaultResponseDTO.setMessage(message);
        return defaultResponseDTO;
    }

    /**
     * {@code employee service delete method} : DELETE Employee
     *
     * @param departmentId the first input long
     * @param employeeId the second input long
     * @return return a default response DTO
     * */
    public DefaultResponseDTO delete(Long departmentId, Long employeeId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("department id didn't exist"));
        EmployeeId employeeEntityID = new EmployeeId(department, employeeId);
        boolean isEmployeeExist = employeeRepository.existsById(employeeEntityID);
        DefaultResponseDTO defaultResponseDTO = new DefaultResponseDTO();
        if (isEmployeeExist) {
            employeeRepository.deleteById(employeeEntityID);
            defaultResponseDTO.setStatus(HttpStatus.ACCEPTED.value());
            defaultResponseDTO.setMessage("resource deleted successfully");
        } else {
            throw new ResourceNotFoundException("employee not found");
        }
        return defaultResponseDTO;
    }

    /**
     * {@code employee service search method} : SEARCH all employees with specification
     *
     * @param keyword the keyword for filter student
     * @param date the second input date
     * @param pageable the pagination information
     * @return return student DTO page
     * */
    @Transactional(readOnly = true)
    public Page<SearchResponseDTO> search(String keyword, Date date, Pageable pageable) {
        return employeeRepository.findAll(employeeSpecification(keyword, date), pageable).map(employeeMapper::toSearchDTO);
    }

    /**
     * {@code employee service search between method} : SEARCH all employees between dates with specification
     *
     * @param keyword the keyword for filter student
     * @param startDate the second input date
     * @param endDate the third input date
     * @param pageable the pagination information
     * @return return student DTO page
     * */
    @Transactional(readOnly = true)
    public Page<SearchResponseDTO> searchBetween(String keyword, Date startDate, Date endDate, Pageable pageable) {
        return employeeRepository.findAll(employeeSpecification2(keyword, startDate, endDate), pageable).map(employeeMapper::toSearchDTO);
    }

}
