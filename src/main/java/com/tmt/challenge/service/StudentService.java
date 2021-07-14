package com.tmt.challenge.service;

import com.tmt.challenge.dto.ResponseDTO;
import com.tmt.challenge.dto.StudentDTO;
import com.tmt.challenge.dto.StudentWithBooksDTO;
import com.tmt.challenge.model.Book;
import com.tmt.challenge.model.Student;
import com.tmt.challenge.repository.BookRepo;
import com.tmt.challenge.repository.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepo studentRepo;

    @Autowired
    public StudentService(StudentRepo studentRepo) {
        this.studentRepo = studentRepo;
    }

    // Convert to DTO;
    private StudentDTO convertToDTO(Student student) {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(student.getId());
        studentDTO.setFirstName(student.getFirstName());
        studentDTO.setLastName(student.getLastName());
        studentDTO.setEmail(student.getEmail());
        studentDTO.setAge(student.getAge());
        return studentDTO;
    }

    // CREATE New Student
    public StudentDTO addNewStudent(Student student) {
        Optional<Student> studentOptional = studentRepo.findStudentByEmail(student.getEmail());
        if (studentOptional.isPresent()) {
            throw new IllegalStateException("email already exist");
        }
        studentRepo.save(student);

//        List<String> bookNames = studentWithBooks.getBooks();
//        bookNames.forEach(bookName -> {
//            Book book = new Book();
//            book.setBookName(bookName);
//            book.setCreatedAt(new Timestamp(new Date().getTime()));
//            book.setStudent(student);
//            bookRepo.save(book);
//        });

//        JSONObject result = new JSONObject();
//        result.put("id", student.getId());
//        result.put("firstName", student.getFirstName());
//        result.put("lastName", student.getLastName());
//        result.put("email", student.getEmail());
//        result.put("age", student.getAge());
//        result.put("books", bookNames);
//        return result;
        return convertToDTO(student);
    }

    // READ All Students
    public List<StudentDTO> getAllStudents() {
        List<Student> students = studentRepo.findAll();
        return students.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // READ Student by ID
    public StudentDTO getStudentById(Long studentId) {
        Optional<Student> studentOptional = studentRepo.findById(studentId);
        if (!studentOptional.isPresent()) {
            throw new IllegalStateException("student with id " + studentId + " does not exist");
        }
        return convertToDTO(studentOptional.get());
    }

    // READ Student by Email
    public StudentDTO getStudentByEmail(String email) {
        Optional<Student> studentOptional = studentRepo.findStudentByEmail(email);
        if (!studentOptional.isPresent()) {
            throw new IllegalStateException("student with email " + email + " does not exist");
        }
        return convertToDTO(studentOptional.get());
    }

    // UPDATE Student
    @Transactional
    public ResponseDTO updateStudent(Long studentId, String firstName, String lastName) {
        Student student = studentRepo.findById(studentId)
                .orElseThrow(() -> new IllegalStateException("student with id " + studentId + " does not exist"));
        if (firstName != null && firstName.length() > 0 && !Objects.equals(student.getFirstName(), firstName)) {
            student.setFirstName(firstName);
        }
        if (lastName != null && lastName.length() > 0 && !Objects.equals(student.getLastName(), lastName)) {
            student.setLastName(lastName);
        }
        return new ResponseDTO("Update Success");
    }

    // DELETE Student
    public ResponseDTO deleteStudent(Long studentId) {
        boolean isStudentExist = studentRepo.existsById(studentId);
        if (!isStudentExist) {
            throw new IllegalStateException("student with id " + studentId + " does not exist");
        }
        studentRepo.deleteById(studentId);
        return new ResponseDTO("Delete Success");
    }
}
