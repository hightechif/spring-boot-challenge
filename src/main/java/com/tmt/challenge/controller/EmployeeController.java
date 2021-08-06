package com.tmt.challenge.controller;

import com.tmt.challenge.dto.EmployeeDTO;
import com.tmt.challenge.dto.response.DefaultResponseDTO;
import com.tmt.challenge.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/create")
    public ResponseEntity<EmployeeDTO> create(@RequestBody EmployeeDTO employeeDTO) {
        EmployeeDTO responseData = employeeService.create(employeeDTO);
        return ResponseEntity.created(URI.create("/create/")).body(responseData);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<EmployeeDTO>> getAll() {
        List<EmployeeDTO> employeeDTOS = employeeService.getAll();
        return new ResponseEntity<>(employeeDTOS, HttpStatus.OK);
    }

    @GetMapping("/get-by-id/{departmentId}/{employeeId}")
    public ResponseEntity<EmployeeDTO> getById(@PathVariable(value = "departmentId") Long departmentId,
                                               @PathVariable(value = "employeeId") Long employeeId) {
        EmployeeDTO employeeDTO = employeeService.getById(departmentId, employeeId);
        return new ResponseEntity<>(employeeDTO, HttpStatus.OK);
    }

    @PutMapping("/edit/{departmentId}/{employeeId}")
    public ResponseEntity<DefaultResponseDTO> updateById(@PathVariable(value = "departmentId") Long departmentId,
                                                         @PathVariable(value = "employeeId") Long employeeId,
                                                         @RequestParam(required = false) String name,
                                                         @RequestParam(required = false) String phoneNumber){
        DefaultResponseDTO response = employeeService.updateById(departmentId, employeeId, name, phoneNumber);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete/{departmentId}/{employeeId}")
    public ResponseEntity<DefaultResponseDTO> deleteById(@PathVariable(value = "departmentId") Long departmentId,
                                                         @PathVariable(value = "employeeId") Long employeeId) {
        DefaultResponseDTO response = employeeService.deleteById(departmentId, employeeId);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

}
