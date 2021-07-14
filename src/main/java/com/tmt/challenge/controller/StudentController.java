package com.tmt.challenge.controller;

import com.tmt.challenge.dto.ResponseDTO;
import com.tmt.challenge.dto.StudentDTO;
import com.tmt.challenge.dto.StudentWithBooksDTO;
import com.tmt.challenge.model.Student;
import com.tmt.challenge.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/students")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping(path = "/create-new")
    public ResponseDTO addNewStudent(@RequestBody StudentWithBooksDTO studentWithBooks) {
        return studentService.addNewStudent(studentWithBooks);
    }

    @GetMapping(path = "/get-all")
    public List<StudentDTO> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping(path = "/get/{studentId}")
    public StudentDTO getStudentById(@PathVariable("studentId") Long studentId) {
        return studentService.getStudentById(studentId);
    }

    @GetMapping(path = "/get-by-email")
    public StudentDTO getStudentByEmail(@RequestParam String email) {
        return studentService.getStudentByEmail(email);
    }

    @PutMapping(path = "/edit/{studentId}")
    public ResponseDTO updateStudent(@PathVariable("studentId") Long studentId,
                                     @RequestParam(required = false) String firstName,
                                     @RequestParam(required = false) String lastName) {
        return studentService.updateStudent(studentId, firstName, lastName);
    }

    @DeleteMapping(path = "/delete/{studentId}")
    public ResponseDTO deleteStudent(@PathVariable("studentId") Long studentId) {
        return studentService.deleteStudent(studentId);
    }
}
