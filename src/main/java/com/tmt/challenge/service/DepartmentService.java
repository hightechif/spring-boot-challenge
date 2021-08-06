package com.tmt.challenge.service;

import com.tmt.challenge.dto.DepartmentDTO;
import com.tmt.challenge.dto.response.DefaultResponseDTO;
import com.tmt.challenge.exception.ResourceNotFoundException;
import com.tmt.challenge.mapper.DepartmentMapper;
import com.tmt.challenge.model.Department;
import com.tmt.challenge.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository, DepartmentMapper departmentMapper) {
        this.departmentRepository = departmentRepository;
        this.departmentMapper = departmentMapper;
    }

    public DepartmentDTO create(DepartmentDTO departmentDTO) {
        Department department = departmentMapper.toDepartmentEntity(departmentDTO);
        departmentRepository.save(department);
        return departmentMapper.toDepartmentDTO(department);
    }

    @Transactional(readOnly = true)
    public List<DepartmentDTO> getAll() {
        List<Department> departments = departmentRepository.findAll();
        return departmentMapper.toDepartmentDTO(departments);
    }

    @Transactional(readOnly = true)
    public DepartmentDTO get(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("department with id " + id + " not found."));
        return departmentMapper.toDepartmentDTO(department);
    }

    public DefaultResponseDTO update(Long id, String name) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("department with id " + id + " not found."));
        DefaultResponseDTO defaultResponseDTO = new DefaultResponseDTO();
        String message = "request success. but, nothing changed";
        if (name != null && name.length() > 0 && !Objects.equals(department.getName(), name)) {
            department.setName(name);
            message = "resource updated successfully";
        }
        defaultResponseDTO.setStatus(HttpStatus.ACCEPTED.value());
        defaultResponseDTO.setMessage(message);
        return defaultResponseDTO;
    }

    public DefaultResponseDTO delete(Long id) {
        boolean isDepartmentExist = departmentRepository.existsById(id);
        if (!isDepartmentExist) {
            throw new ResourceNotFoundException("department with id " + id + " not found.");
        }
        DefaultResponseDTO defaultResponseDTO = new DefaultResponseDTO();
        departmentRepository.deleteById(id);
        defaultResponseDTO.setStatus(HttpStatus.ACCEPTED.value());
        defaultResponseDTO.setMessage("resource deleted successfully");
        return defaultResponseDTO;
    }
}
