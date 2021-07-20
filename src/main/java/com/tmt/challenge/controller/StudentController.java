package com.tmt.challenge.controller;

import com.tmt.challenge.dto.ResponseDTO;
import com.tmt.challenge.dto.StudentDTO;
import com.tmt.challenge.dto.StudentRequestDTO;
import com.tmt.challenge.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/create-new")
    public ResponseEntity<ResponseDTO> addNewStudent(@RequestBody StudentRequestDTO studentRequest) {
        ResponseDTO responseDTO = studentService.addNewStudent(studentRequest);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        List<StudentDTO> studentDTOs = studentService.getAllStudents();
        return new ResponseEntity<>(studentDTOs, HttpStatus.OK);
    }

    @GetMapping(path = "/{studentId}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable("studentId") Long studentId) {
        StudentDTO studentDTO = studentService.getStudentById(studentId);
        return new ResponseEntity<>(studentDTO, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<StudentDTO> getStudentByEmail(@RequestParam String email) {
        StudentDTO studentDTO = studentService.getStudentByEmail(email);
        return new ResponseEntity<>(studentDTO, HttpStatus.OK);
    }

    @GetMapping("/get-by-card-number")
    public ResponseEntity<StudentDTO> getStudentByCardNumber(@RequestParam String cardNumber) {
        StudentDTO responseDTO = studentService.getStudentByCardNumber(cardNumber);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/get-by-department")
    public ResponseEntity<List<StudentDTO>> getStudentByDepartment(@RequestParam String department) {
        List<StudentDTO> responseDTO = studentService.getStudentByDepartment(department);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PutMapping("/edit/{studentId}")
    public ResponseEntity<ResponseDTO> updateStudent(@PathVariable("studentId") Long studentId,
                                                     @RequestParam(required = false) String firstName,
                                                     @RequestParam(required = false) String lastName) {
        ResponseDTO responseDTO = studentService.updateStudent(studentId, firstName, lastName);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{studentId}")
    public ResponseEntity<ResponseDTO> deleteStudent(@PathVariable("studentId") Long studentId) {
        ResponseDTO responseDTO = studentService.deleteStudent(studentId);
        return new ResponseEntity<>(responseDTO, HttpStatus.ACCEPTED);
    }

    @GetMapping("/get-by-book-name")
    public ResponseEntity<Page<StudentDTO>> getStudentsByBookName(String bookName, Pageable pageable) {
        Page<StudentDTO> responseDTO = studentService.getStudentsByBookName(bookName, pageable);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/get-by-course-name")
    public ResponseEntity<Page<StudentDTO>> getStudentsByCourseName(String courseName, Pageable pageable) {
        Page<StudentDTO> responseDTO = studentService.getStudentsByCourseName(courseName, pageable);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    /**
     * {@code GET /students/search} : search all students
     * @param keyword the keyword for filter student
     * @param pageable the pagination information
     *
     * */
    @GetMapping("/search")
    public ResponseEntity<Page<StudentDTO>> search(@RequestParam Optional<String> keyword, Pageable pageable) {
        Page<StudentDTO> response = studentService.search(keyword.orElse(""), pageable);
        return ResponseEntity.ok().body(response);
    }
}
