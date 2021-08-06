package com.tmt.challenge.controller;

import com.tmt.challenge.dto.DepartmentDTO;
import com.tmt.challenge.dto.response.DefaultResponseDTO;
import com.tmt.challenge.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/")
    public ResponseEntity<List<DepartmentDTO>> getAll() {
        List<DepartmentDTO> departments = departmentService.getAll();
        return ResponseEntity.created(URI.create("/")).body(departments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDTO> get(@PathVariable(value = "id") Long id) {
        DepartmentDTO departmentDTO = departmentService.get(id);
        return ResponseEntity.created(URI.create("/" + id)).body(departmentDTO);
    }

    @PostMapping("/create")
    public ResponseEntity<DepartmentDTO> create(@RequestBody DepartmentDTO departmentDTO) {
        DepartmentDTO responseData = departmentService.create(departmentDTO);
        return ResponseEntity.created(URI.create("/create/")).body(responseData);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<DefaultResponseDTO> update(@PathVariable(value = "id") Long id,
                                                     @RequestParam(required = false) String name) {
        DefaultResponseDTO defaultResponseDTO = departmentService.update(id, name);
        return ResponseEntity.created(URI.create("/edit/" + id)).body(defaultResponseDTO);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<DefaultResponseDTO> delete(@PathVariable(value = "id") Long id) {
        DefaultResponseDTO defaultResponseDTO = departmentService.delete(id);
        return ResponseEntity.created(URI.create("/delete/" + id)).body(defaultResponseDTO);
    }
}
