package com.tmt.challenge.service;

import com.tmt.challenge.dto.BookDTO;
import com.tmt.challenge.dto.ResponseDTO;
import com.tmt.challenge.dto.StudentDTO;
import com.tmt.challenge.dto.StudentRequestDTO;
import com.tmt.challenge.exception.ResourceNotFoundException;
import com.tmt.challenge.model.Book;
import com.tmt.challenge.model.Student;
import com.tmt.challenge.model.StudentIdCard;
import com.tmt.challenge.repository.StudentIdCardRepo;
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
    private final StudentIdCardRepo studentIdCardRepo;

    @Autowired
    public StudentService(StudentRepo studentRepo, StudentIdCardRepo studentIdCardRepo) {
        this.studentRepo = studentRepo;
        this.studentIdCardRepo = studentIdCardRepo;
    }

    // Convert to DTO;
    private StudentDTO convertToDTO(Student student) {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(student.getId());
        studentDTO.setFirstName(student.getFirstName());
        studentDTO.setLastName(student.getLastName());
        studentDTO.setEmail(student.getEmail());
        studentDTO.setAge(student.getAge());
        List<BookDTO> books = student.getBooks().stream().map(x -> new BookDTO(x.getId(), x.getBookName(), x.getCreatedAt()))
                .collect(Collectors.toList());
        studentDTO.setBooks(books);
        StudentIdCard idCard = student.getStudentIdCard();
        studentDTO.setCardNumber(idCard.getCardNumber());
        return studentDTO;
    }

    // CREATE New Student
    public ResponseDTO addNewStudent(StudentRequestDTO studentRequest) {
        Optional<Student> studentOptional = studentRepo.findStudentByEmail(studentRequest.getEmail());
        if (studentOptional.isPresent()) {
            throw new IllegalStateException("email already exist");
        }

        Student student = studentRequest.getStudent();

        List<Book> books = studentRequest.getBooks().stream().map(x -> {
            x.setStudent(student);
            return x;
        }).collect(Collectors.toList());

        StudentIdCard idCard = new StudentIdCard();
        String cardNumber = studentRequest.getCardNumber();
        idCard.setCardNumber(cardNumber);
        idCard.setStudent(student);

        student.setBooks(books);
        student.setStudentIdCard(idCard);
        studentRepo.save(student);
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

    // READ Student by Card Number
    public StudentDTO getStudentByCardNumber(String cardNumber) {
        Optional<StudentIdCard> studentIdOptional = studentIdCardRepo.findStudentIdCardByCardNumber(cardNumber);
        if (!studentIdOptional.isPresent()) {
            throw new ResourceNotFoundException("Student ID with cardNumber " + cardNumber + " not found");
        }
        StudentIdCard studentIdCard = studentIdOptional.get();

        Student student = studentIdCard.getStudent();
        return convertToDTO(student);
    }
}
