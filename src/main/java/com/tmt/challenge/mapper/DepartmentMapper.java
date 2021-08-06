package com.tmt.challenge.mapper;

import com.tmt.challenge.dto.DepartmentDTO;
import com.tmt.challenge.model.Department;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {

    DepartmentDTO toDepartmentDTO(Department department);

    List<DepartmentDTO> toDepartmentDTO(Collection<Department> departments);

    Department toDepartmentEntity(DepartmentDTO departmentDTO);

    List<Department> toDepartmentEntity(Collection<DepartmentDTO> departmentDTO);

}
