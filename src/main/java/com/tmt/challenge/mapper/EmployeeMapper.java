package com.tmt.challenge.mapper;

import com.tmt.challenge.dto.EmployeeDTO;
import com.tmt.challenge.model.Employee;
import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    EmployeeDTO toEmployeeDTO(Employee employee);
    List<EmployeeDTO> toEmployeeDTO(Collection<Employee> employees);
    Employee toEmployeeEntity(EmployeeDTO employeeDTO);
    List<Employee> toEmployeeEntity(Collection<EmployeeDTO> employeeDTOS);

}
