package com.tmt.challenge.controller;

import com.tmt.challenge.dto.EmployeeDTO;
import com.tmt.challenge.dto.request.SearchBetweenDTO;
import com.tmt.challenge.dto.request.SearchRequestDTO;
import com.tmt.challenge.dto.response.DefaultResponseDTO;
import com.tmt.challenge.dto.response.SearchResponseDTO;
import com.tmt.challenge.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/")
    public ResponseEntity<List<EmployeeDTO>> getAll() {
        List<EmployeeDTO> employeeDTOS = employeeService.getAll();
        return new ResponseEntity<>(employeeDTOS, HttpStatus.OK);
    }

    @GetMapping("/{departmentId}/{employeeId}")
    public ResponseEntity<EmployeeDTO> get(@PathVariable(value = "departmentId") Long departmentId,
                                               @PathVariable(value = "employeeId") Long employeeId) {
        EmployeeDTO employeeDTO = employeeService.get(departmentId, employeeId);
        return new ResponseEntity<>(employeeDTO, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<EmployeeDTO> create(@RequestBody EmployeeDTO employeeDTO) {
        EmployeeDTO responseData = employeeService.create(employeeDTO);
        return ResponseEntity.created(URI.create("/create/")).body(responseData);
    }

    @PutMapping("/edit/{departmentId}/{employeeId}")
    public ResponseEntity<DefaultResponseDTO> update(@PathVariable(value = "departmentId") Long departmentId,
                                                         @PathVariable(value = "employeeId") Long employeeId,
                                                         @RequestParam(required = false) String name,
                                                         @RequestParam(required = false) String phoneNumber){
        DefaultResponseDTO response = employeeService.update(departmentId, employeeId, name, phoneNumber);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete/{departmentId}/{employeeId}")
    public ResponseEntity<DefaultResponseDTO> delete(@PathVariable(value = "departmentId") Long departmentId,
                                                         @PathVariable(value = "employeeId") Long employeeId) {
        DefaultResponseDTO response = employeeService.delete(departmentId, employeeId);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    /**
     * {@code GET /employees/search} : search all employees from related keyword
     *
     * @param request  the request DTO consist of keyword and date for filter title of employee assignment
     * @param pageable the pagination information
     */
    @GetMapping("/search")
    public ResponseEntity<Page<SearchResponseDTO>> search(@RequestBody SearchRequestDTO request,
                                                          Pageable pageable) {
        String keyword = request.getKeyword().orElse("");
        Date date = request.getDate().orElse(null);
        Page<SearchResponseDTO> response = employeeService.search(keyword, date, pageable);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/search-between")
    public ResponseEntity<Page<SearchResponseDTO>> searchBetween(@RequestBody SearchBetweenDTO request,
                                                                 Pageable pageable) {
        String keyword = request.getKeyword().orElse("");
        Date startDate = request.getStartDate().orElse(null);
        Date endDate = request.getEndDate().orElse(null);
        System.out.println("MASUK CONTROLLER");
        Page<SearchResponseDTO> response = employeeService.searchBetween(keyword, startDate, endDate, pageable);
        return ResponseEntity.ok().body(response);
    }

}
