package com.tmt.challenge.service;

import com.tmt.challenge.dto.*;
import com.tmt.challenge.exception.ResourceNotFoundException;
import com.tmt.challenge.model.Book;
import com.tmt.challenge.model.Course;
import com.tmt.challenge.model.Student;
import com.tmt.challenge.model.StudentIdCard;
import com.tmt.challenge.repository.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
        StudentIdCardDTO studentIdCardDTO = new StudentIdCardDTO();
        StudentIdCard studentIdCard = student.getStudentIdCard();
        studentIdCardDTO.setId(studentIdCard.getId());
        studentIdCardDTO.setCardNumber(studentIdCard.getCardNumber());
        studentDTO.setStudentIdCard(studentIdCardDTO);
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
        List<BookDTO> booksDTO = studentRequest.getBooks();
        List<Book> books = booksDTO.stream().map(x -> {
            Book book = new Book();
            book.setId(x.getId());
            book.setBookName(x.getBookName());
            book.setStudent(student);
            return book;
        }).collect(Collectors.toList());
        // Extract StudentIdCard from request body
        StudentIdCardDTO studentIdCardDTO = studentRequest.getStudentIdCard();
        StudentIdCard studentIdCard = new StudentIdCard();
        studentIdCard.setCardNumber(studentIdCardDTO.getCardNumber());
        studentIdCard.setStudent(student);
        // Extract Collection of course from request body and set student of it
        List<CourseDTO> coursesDTO = studentRequest.getCourses();
        List<Course> courses = coursesDTO.stream().map(x -> {
            Course course = new Course();
            course.setId(x.getId());
            course.setName(x.getName());
            course.setDepartment(x.getDepartment());
            course.setStudents(List.of(student));
            return course;
        }).collect(Collectors.toList());
        // Set all extracted data into student object and save it
        student.setBooks(books);
        student.setStudentIdCard(studentIdCard);
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
        if (studentOptional.isEmpty()) {
            throw new IllegalStateException("student with id " + studentId + " does not exist");
        }
        return convertToDTO(studentOptional.get());
    }

    // READ Student by Email
    public StudentDTO getStudentByEmail(String email) {
        Optional<Student> studentOptional = studentRepo.findStudentByEmail(email);
        if (studentOptional.isEmpty()) {
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
        Optional<Student> studentOptional = studentRepo.findStudentByStudentIdCardCardNumber(cardNumber);
        if (studentOptional.isEmpty()) {
            throw new ResourceNotFoundException("Student ID with cardNumber " + cardNumber + " not found");
        }
        return convertToDTO(studentOptional.get());
    }

    // READ Student by Department
    public List<StudentDTO> getStudentByDepartment(String department) {
        List<Student> studentList = studentRepo.findStudentByCoursesDepartment(department);
        return studentList.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // READ Students by Book Name
    public Page<StudentDTO> getStudentsByBookName(String bookName, Pageable pageable) {
        Page<Student> studentPage = studentRepo.findStudentsByBookName(bookName, pageable);
        return studentPage.map(this::convertToDTO);
    }

    // READ Students by Course Name
    public Page<StudentDTO> getStudentsByCourseName(String courseName, Pageable pageable) {
        Page<Student> studentPage = studentRepo.findStudentsByCourseName(courseName, pageable);
        return studentPage.map(this::convertToDTO);
    }
}
