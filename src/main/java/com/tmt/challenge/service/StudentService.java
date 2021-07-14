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
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepo studentRepo;
    private final BookRepo bookRepo;

    @Autowired
    public StudentService(StudentRepo studentRepo, BookRepo bookRepo) {
        this.studentRepo = studentRepo;
        this.bookRepo = bookRepo;
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
    public ResponseDTO addNewStudent(StudentWithBooksDTO studentWithBooks) {
        Optional<Student> studentOptional = studentRepo.findStudentByEmail(studentWithBooks.getEmail());
        if (studentOptional.isPresent()) {
            throw new IllegalStateException("email already exist");
        }

        Student student = studentWithBooks.getStudent();
        studentRepo.save(student);

        List<String> bookNames = studentWithBooks.getBooks();
        bookNames.forEach(name -> {
            Book book = new Book();
            book.setBookName(name);
            book.setStudent(student);
            bookRepo.save(book);
        });

        return new ResponseDTO("Data Created");
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
