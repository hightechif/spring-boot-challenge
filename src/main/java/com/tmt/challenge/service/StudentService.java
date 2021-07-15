package com.tmt.challenge.service;

import com.tmt.challenge.dto.*;
import com.tmt.challenge.exception.ResourceNotFoundException;
import com.tmt.challenge.model.Book;
import com.tmt.challenge.model.Course;
import com.tmt.challenge.model.Student;
import com.tmt.challenge.model.StudentIdCard;
import com.tmt.challenge.repository.CourseRepo;
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
    private final CourseRepo courseRepo;

    @Autowired
    public StudentService(StudentRepo studentRepo, StudentIdCardRepo studentIdCardRepo, CourseRepo courseRepo) {
        this.studentRepo = studentRepo;
        this.studentIdCardRepo = studentIdCardRepo;
        this.courseRepo = courseRepo;
    }

    // Convert to DTO;
    private StudentDTO convertToDTO(Student student) {
        // Generate new StudentDTO Object
        StudentDTO studentDTO = new StudentDTO();
        // Set id, firstName, lastName, email, and age
        studentDTO.setId(student.getId());
        studentDTO.setFirstName(student.getFirstName());
        studentDTO.setLastName(student.getLastName());
        studentDTO.setEmail(student.getEmail());
        studentDTO.setAge(student.getAge());
        // Set Collection of Book
        List<BookDTO> books = student.getBooks().stream().map(x -> new BookDTO(x.getId(), x.getBookName(), x.getCreatedAt()))
                .collect(Collectors.toList());
        studentDTO.setBooks(books);
        // Set cardNumber
        StudentIdCard idCard = student.getStudentIdCard();
        studentDTO.setCardNumber(idCard.getCardNumber());
        // Set Collection of Course
        List<CourseDTO> courses = student.getCourses().stream().map(x -> new CourseDTO(x.getId(), x.getName(), x.getDepartment()))
                .collect(Collectors.toList());
        studentDTO.setCourses(courses);
        return studentDTO;
    }

    // CREATE New Student
    public ResponseDTO addNewStudent(StudentRequestDTO studentRequest) {
        // Check Student with existing email
        Optional<Student> studentOptional = studentRepo.findStudentByEmail(studentRequest.getEmail());
        if (studentOptional.isPresent()) {
            throw new IllegalStateException("email already exist");
        }

        // Extract Student from request body
        Student student = studentRequest.getStudent();

        // Extract Collection of Book from request body and set student to it
        List<Book> books = studentRequest.getBooks().stream().map(x -> {
            x.setStudent(student);
            return x;
        }).collect(Collectors.toList());

        // Extract StudentIdCard from request body
        StudentIdCard idCard = new StudentIdCard();
        String cardNumber = studentRequest.getCardNumber();
        idCard.setCardNumber(cardNumber);
        idCard.setStudent(student);

        // Extract Collection of course from request body and set student of it
        List<Course> courses = studentRequest.getCourses().stream().map(x -> {
            x.setStudents(List.of(student));
            return x;
        }).collect(Collectors.toList());

        // Set all extracted data into student object and save it
        student.setBooks(books);
        student.setStudentIdCard(idCard);
        student.setCourses(courses);
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

    // READ Student by Department
    public List<StudentDTO> getStudentByDepartment(String department) {
        Optional<Course> courseOptional = courseRepo.findCourseByDepartment(department);
        if (!courseOptional.isPresent()) {
            throw new ResourceNotFoundException("Student ID at Department " + department + " not found");
        }
        Course course = courseOptional.get();

        List<StudentDTO> studentList = course.getStudents().stream().map(this::convertToDTO).collect(Collectors.toList());
        return studentList;
    }
}
