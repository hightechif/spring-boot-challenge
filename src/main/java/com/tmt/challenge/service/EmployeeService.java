package com.tmt.challenge.service;

import com.tmt.challenge.constant.enums.Operator;
import com.tmt.challenge.constant.enums.SearchOperation;
import com.tmt.challenge.dto.AssignmentDTO;
import com.tmt.challenge.dto.EmployeeDTO;
import com.tmt.challenge.dto.response.DefaultResponseDTO;
import com.tmt.challenge.dto.response.SearchAssignmentDTO;
import com.tmt.challenge.exception.ResourceNotFoundException;
import com.tmt.challenge.mapper.AssignmentMapper;
import com.tmt.challenge.mapper.EmployeeMapper;
import com.tmt.challenge.model.Assignment;
import com.tmt.challenge.model.Department;
import com.tmt.challenge.model.Employee;
import com.tmt.challenge.model.EmployeeId;
import com.tmt.challenge.repository.DepartmentRepository;
import com.tmt.challenge.repository.EmployeeRepository;
import com.tmt.challenge.repository.specs.EmployeeSpecification;
import com.tmt.challenge.repository.specs.SearchCriteria;
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

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeMapper employeeMapper;
    private final AssignmentMapper assignmentMapper;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository, EmployeeMapper employeeMapper, AssignmentMapper assignmentMapper) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.employeeMapper = employeeMapper;
        this.assignmentMapper = assignmentMapper;
    }

    // Employee Specs private method
    private EmployeeSpecification employeeSpecification(String keyword, Date date) {
        EmployeeSpecification employeeSpecification = new EmployeeSpecification();
        if (!keyword.equals("") && (date != null)) {
            employeeSpecification.add(new SearchCriteria("title", keyword, SearchOperation.MATCH, "assignments", null, null));
            employeeSpecification.add(new SearchCriteria("startDate", date, SearchOperation.DATE_LESS_THAN_EQUAL, "assignments", null, null));
            employeeSpecification.add(new SearchCriteria("endDate", date, SearchOperation.DATE_GREATER_THAN_EQUAL, "assignments", null, null));
        } else if (!keyword.equals("") && (date == null)) {
            employeeSpecification.add(new SearchCriteria("title", keyword, SearchOperation.MATCH, "assignments", null, null));
        } else if (keyword.equals("") && (date != null)){
            employeeSpecification.add(new SearchCriteria("startDate", date, SearchOperation.DATE_LESS_THAN_EQUAL, "assignments", null, null));
            employeeSpecification.add(new SearchCriteria("endDate", date, SearchOperation.DATE_GREATER_THAN_EQUAL, "assignments", null, null));
        }
        employeeSpecification.operator(Operator.AND);
        return employeeSpecification;
    }

    // CREATE New Employee
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
        List<AssignmentDTO> assignmentDTOS = employeeDTO.getAssignments();
        List<Assignment> assignments = assignmentMapper.toAssignmentEntity(assignmentDTOS);
        assignments.forEach(x -> x.setEmployee(employee));
        employee.setAssignments(assignments);
        Employee savedData = employeeRepository.save(employee);
        return employeeMapper.toEmployeeDTO(savedData);
    }

    @Transactional(readOnly = true)
    public List<EmployeeDTO> getAll() {
        List<Employee> employees = employeeRepository.findAll();
        return employeeMapper.toEmployeeDTO(employees);
    }

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

    public DefaultResponseDTO update(Long departmentId, Long employeeId, String name, String phoneNumber) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("department id didn't exist"));
        Employee employee = employeeRepository.findById(new EmployeeId(department, employeeId))
                .orElseThrow(() -> new ResourceNotFoundException("employee not found"));
        DefaultResponseDTO defaultResponseDTO = new DefaultResponseDTO();
        String message = "request success. but, nothing changed";
        if (name != null && name.length() > 0 && !Objects.equals(employee.getName(), name)) {
            employee.setName(name);
            message = "resource updated successfully";
        }
        if (phoneNumber != null && phoneNumber.length() > 0 && !Objects.equals(employee.getPhoneNumber(), phoneNumber)) {
            employee.setPhoneNumber(phoneNumber);
            message = "resource updated successfully";
        }
        defaultResponseDTO.setStatus(HttpStatus.ACCEPTED.value());
        defaultResponseDTO.setMessage(message);
        return defaultResponseDTO;
    }

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

    @Transactional(readOnly = true)
    public Page<SearchAssignmentDTO> search(String keyword, Date date, Pageable pageable) {
        return employeeRepository.findAll(employeeSpecification(keyword, date), pageable).map(employeeMapper::toSearchDTO);
    }
}
