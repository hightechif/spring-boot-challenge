package com.tmt.challenge.controller;

import com.tmt.challenge.dto.StudentDTO;
import com.tmt.challenge.dto.request.StudentRequestDTO;
import com.tmt.challenge.dto.response.DefaultResponseDTO;
import com.tmt.challenge.service.StudentService;
import org.jetbrains.annotations.NotNull;
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

    /**
     * {@code GET /api/v1/students} : get a list of all students
     *
     * @return return a response entity of student DTO list
     */
    @GetMapping("/")
    public ResponseEntity<List<StudentDTO>> getAll() {
        List<StudentDTO> studentDTOs = studentService.getAll();
        return new ResponseEntity<>(studentDTOs, HttpStatus.OK);
    }

    /**
     * {@code GET /api/v1/students/{studentId}} : get students by ID
     *
     * @param studentId the first input long
     * @return return a response entity of student DTO
     */
    @GetMapping(path = "/{studentId}")
    public ResponseEntity<StudentDTO> get(@PathVariable("studentId") Long studentId) {
        StudentDTO studentDTO = studentService.get(studentId);
        return new ResponseEntity<>(studentDTO, HttpStatus.OK);
    }

    /**
     * {@code POST /api/v1/students/create} : create a new student
     *
     * @param studentRequest the first input student request DTO
     * @return return a response entity of default response DTO
     */
    @PostMapping("/create")
    public ResponseEntity<DefaultResponseDTO> create(@RequestBody StudentRequestDTO studentRequest) {
        DefaultResponseDTO defaultResponseDTO = studentService.create(studentRequest);
        return new ResponseEntity<>(defaultResponseDTO, HttpStatus.CREATED);
    }

    /**
     * {@code GET /api/v1/students/get-by-email} : get students by email
     *
     * @param email the first input string
     * @return return a response entity of student DTO
     */
    @GetMapping("/get-by-email")
    public ResponseEntity<StudentDTO> getByEmail(@RequestParam String email) {
        StudentDTO studentDTO = studentService.getByEmail(email);
        return new ResponseEntity<>(studentDTO, HttpStatus.OK);
    }

    /**
     * {@code GET /api/v1/students/get-by-card-number} : get students by card number
     *
     * @param cardNumber the first input string
     * @return return a response entity of student DTO
     */
    @GetMapping("/get-by-card-number")
    public ResponseEntity<StudentDTO> getByCardNumber(@RequestParam String cardNumber) {
        StudentDTO responseDTO = studentService.getByCardNumber(cardNumber);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    /**
     * {@code GET /api/v1/students/get-by-department} : get students by department
     *
     * @param department the first input string
     * @return return a response entity of student DTO list
     */
    @GetMapping("/get-by-department")
    public ResponseEntity<List<StudentDTO>> getByDepartment(@RequestParam String department) {
        List<StudentDTO> responseDTO = studentService.getByDepartment(department);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    /**
     * {@code PUT /api/v1/students/edit/{studentId}} : update student
     *
     * @param studentId the first input long
     * @param firstName the second input string
     * @param lastName  the third input string
     * @return return a response entity of default response DTO
     */
    @PutMapping("/edit/{studentId}")
    public ResponseEntity<DefaultResponseDTO> update(@PathVariable("studentId") Long studentId,
                                                     @RequestParam(required = false) String firstName,
                                                     @RequestParam(required = false) String lastName) {
        DefaultResponseDTO defaultResponseDTO = studentService.update(studentId, firstName, lastName);
        return new ResponseEntity<>(defaultResponseDTO, HttpStatus.OK);
    }

    /**
     * {@code DELETE /api/v1/students/delete/{studentId}} : delete student by id
     *
     * @param studentId the first input long
     * @return return a response entity of default response DTO
     */
    @DeleteMapping("/delete/{studentId}")
    public ResponseEntity<DefaultResponseDTO> delete(@PathVariable("studentId") Long studentId) {
        DefaultResponseDTO defaultResponseDTO = studentService.delete(studentId);
        return new ResponseEntity<>(defaultResponseDTO, HttpStatus.ACCEPTED);
    }

    /**
     * {@code GET /api/v1/students/get-by-book-name} : get students by book name
     *
     * @param bookName the first input string
     * @param pageable the second input pageable
     * @return return a response entity of student DTO page
     */
    @GetMapping("/get-by-book-name")
    public ResponseEntity<Page<StudentDTO>> getByBookName(String bookName, Pageable pageable) {
        Page<StudentDTO> responseDTO = studentService.getByBookName(bookName, pageable);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    /**
     * {@code GET /api/v1/students/get-by-course-name} : get students by course name
     *
     * @param courseName the first input string
     * @param pageable   the second input pageable
     * @return return a response entity of student DTO page
     */
    @GetMapping("/get-by-course-name")
    public ResponseEntity<Page<StudentDTO>> getByCourseName(String courseName, Pageable pageable) {
        Page<StudentDTO> responseDTO = studentService.getByCourseName(courseName, pageable);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    /**
     * {@code GET /api/v1/students/search} : search all students
     *
     * @param keyword  the keyword for filter student
     * @param pageable the pagination information
     * @return return a response entity of student DTO page
     */
    @GetMapping("/search")
    public ResponseEntity<Page<StudentDTO>> search(@NotNull @RequestParam Optional<String> keyword, Pageable pageable) {
        Page<StudentDTO> response = studentService.search(keyword.orElse(""), pageable);
        return ResponseEntity.ok().body(response);
    }
}
