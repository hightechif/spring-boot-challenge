package com.tmt.challenge.controller;

import com.tmt.challenge.dto.ResponseDTO;
import com.tmt.challenge.dto.StudentDTO;
import com.tmt.challenge.dto.StudentRequestDTO;
import com.tmt.challenge.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/create-new")
    public ResponseDTO addNewStudent(@RequestBody StudentRequestDTO studentRequest) {
        return studentService.addNewStudent(studentRequest);
    }

    @GetMapping("/get-all")
    public List<StudentDTO> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping(path = "/{studentId}")
    public StudentDTO getStudentById(@PathVariable("studentId") Long studentId) {
        return studentService.getStudentById(studentId);
    }

    @GetMapping("/")
    public StudentDTO getStudentByEmail(@RequestParam String email) {
        return studentService.getStudentByEmail(email);
    }

    @GetMapping("/get-by-card-number")
    public StudentDTO getStudentByCardNumber(@RequestParam String cardNumber) {
        return studentService.getStudentByCardNumber(cardNumber);
    }

    @GetMapping("/get-by-department")
    public List<StudentDTO> getStudentByDepartment(@RequestParam String department) {
        return studentService.getStudentByDepartment(department);
    }

    @PutMapping("/edit/{studentId}")
    public ResponseDTO updateStudent(@PathVariable("studentId") Long studentId,
                                     @RequestParam(required = false) String firstName,
                                     @RequestParam(required = false) String lastName) {
        return studentService.updateStudent(studentId, firstName, lastName);
    }

    @DeleteMapping("/delete/{studentId}")
    public ResponseDTO deleteStudent(@PathVariable("studentId") Long studentId) {
        return studentService.deleteStudent(studentId);
    }
}
