package com.tmt.challenge.controller;

import com.tmt.challenge.dto.response.DefaultResponseDTO;
import com.tmt.challenge.dto.StudentDTO;
import com.tmt.challenge.dto.request.StudentRequestDTO;
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

    @GetMapping("/")
    public ResponseEntity<List<StudentDTO>> getAll() {
        List<StudentDTO> studentDTOs = studentService.getAll();
        return new ResponseEntity<>(studentDTOs, HttpStatus.OK);
    }

    @GetMapping(path = "/{studentId}")
    public ResponseEntity<StudentDTO> get(@PathVariable("studentId") Long studentId) {
        StudentDTO studentDTO = studentService.get(studentId);
        return new ResponseEntity<>(studentDTO, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<DefaultResponseDTO> create(@RequestBody StudentRequestDTO studentRequest) {
        DefaultResponseDTO defaultResponseDTO = studentService.create(studentRequest);
        return new ResponseEntity<>(defaultResponseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/get-by-email")
    public ResponseEntity<StudentDTO> getByEmail(@RequestParam String email) {
        StudentDTO studentDTO = studentService.getByEmail(email);
        return new ResponseEntity<>(studentDTO, HttpStatus.OK);
    }

    @GetMapping("/get-by-card-number")
    public ResponseEntity<StudentDTO> getByCardNumber(@RequestParam String cardNumber) {
        StudentDTO responseDTO = studentService.getByCardNumber(cardNumber);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/get-by-department")
    public ResponseEntity<List<StudentDTO>> getByDepartment(@RequestParam String department) {
        List<StudentDTO> responseDTO = studentService.getByDepartment(department);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PutMapping("/edit/{studentId}")
    public ResponseEntity<DefaultResponseDTO> update(@PathVariable("studentId") Long studentId,
                                                            @RequestParam(required = false) String firstName,
                                                            @RequestParam(required = false) String lastName) {
        DefaultResponseDTO defaultResponseDTO = studentService.update(studentId, firstName, lastName);
        return new ResponseEntity<>(defaultResponseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{studentId}")
    public ResponseEntity<DefaultResponseDTO> delete(@PathVariable("studentId") Long studentId) {
        DefaultResponseDTO defaultResponseDTO = studentService.delete(studentId);
        return new ResponseEntity<>(defaultResponseDTO, HttpStatus.ACCEPTED);
    }

    @GetMapping("/get-by-book-name")
    public ResponseEntity<Page<StudentDTO>> getByBookName(String bookName, Pageable pageable) {
        Page<StudentDTO> responseDTO = studentService.getByBookName(bookName, pageable);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/get-by-course-name")
    public ResponseEntity<Page<StudentDTO>> getByCourseName(String courseName, Pageable pageable) {
        Page<StudentDTO> responseDTO = studentService.getByCourseName(courseName, pageable);
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
