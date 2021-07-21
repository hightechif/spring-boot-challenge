package com.tmt.challenge.service;

import com.tmt.challenge.constant.enums.Operator;
import com.tmt.challenge.constant.enums.SearchOperation;
import com.tmt.challenge.dto.*;
import com.tmt.challenge.exception.ResourceNotFoundException;
import com.tmt.challenge.model.Book;
import com.tmt.challenge.model.Course;
import com.tmt.challenge.model.Student;
import com.tmt.challenge.model.StudentIdCard;
import com.tmt.challenge.repository.StudentRepo;
import com.tmt.challenge.repository.specs.SearchCriteria;
import com.tmt.challenge.repository.specs.StudentSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
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

    // Student Specs private method
    private StudentSpecification studentSpecification(String keyword) {
        StudentSpecification studentSpecification = new StudentSpecification();
        studentSpecification.add(new SearchCriteria("email", keyword, SearchOperation.MATCH, null, null, null));
        studentSpecification.add(new SearchCriteria("firstName", keyword, SearchOperation.MATCH, null, null, null));
        studentSpecification.add(new SearchCriteria("lastName", keyword, SearchOperation.MATCH, null, null, null));
        studentSpecification.operator(Operator.OR);
        return studentSpecification;
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
        return new ResponseDTO("resource created successfully", 201);
    }

    // READ All Students
    @Transactional(readOnly = true)
    public List<StudentDTO> getAllStudents() {
        List<Student> students = studentRepo.findAll();
        return students.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // READ Student by ID
    @Transactional(readOnly = true)
    public StudentDTO getStudentById(Long studentId) {
        Student student = studentRepo.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("student with id " + studentId + " not found"));
        return convertToDTO(student);
    }

    // READ Student by Email
    @Transactional(readOnly = true)
    public StudentDTO getStudentByEmail(String email) {
        Student student = studentRepo.findStudentByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("student with email " + email + " not found"));
        return convertToDTO(student);
    }

    // UPDATE Student
    public ResponseDTO updateStudent(Long studentId, String firstName, String lastName) {
        Student student = studentRepo.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("student with id " + studentId + " not found"));
        ResponseDTO responseDTO = new ResponseDTO();
        String message = "request success. but, nothing changed";
        if (firstName != null && firstName.length() > 0 && !Objects.equals(student.getFirstName(), firstName)) {
            student.setFirstName(firstName);
            message = "resource updated successfully";
        }
        if (lastName != null && lastName.length() > 0 && !Objects.equals(student.getLastName(), lastName)) {
            student.setLastName(lastName);
            message = "resource updated successfully";
        }
        responseDTO.setMessage(message);
        return responseDTO;
    }

    // DELETE Student
    public ResponseDTO deleteStudent(Long studentId) {
        boolean isStudentExist = studentRepo.existsById(studentId);
        ResponseDTO responseDTO = new ResponseDTO();
        if (isStudentExist) {
            studentRepo.deleteById(studentId);
            responseDTO.setStatus(202);
            responseDTO.setMessage("resource deleted successfully");
        } else {
            throw new ResourceNotFoundException("student with id " + studentId + " not found");
        }
        return responseDTO;
    }

    // READ Student by Card Number
    @Transactional(readOnly = true)
    public StudentDTO getStudentByCardNumber(String cardNumber) {
        Student student = studentRepo.findStudentByStudentIdCardCardNumber(cardNumber)
                .orElseThrow(() -> new ResourceNotFoundException("student ID with cardNumber " + cardNumber + " not found"));
        return convertToDTO(student);
    }

    // READ Student by Department
    @Transactional(readOnly = true)
    public List<StudentDTO> getStudentByDepartment(String department) {
        List<Student> studentList = studentRepo.findStudentByCoursesDepartment(department);
        return studentList.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // READ Students by Book Name
    @Transactional(readOnly = true)
    public Page<StudentDTO> getStudentsByBookName(String bookName, Pageable pageable) {
        Page<Student> studentPage = studentRepo.findStudentsByBookName(bookName, pageable);
        return studentPage.map(this::convertToDTO);
    }

    // READ Students by Course Name
    @Transactional(readOnly = true)
    public Page<StudentDTO> getStudentsByCourseName(String courseName, Pageable pageable) {
        Page<Student> studentPage = studentRepo.findStudentsByCourseName(courseName, pageable);
        return studentPage.map(this::convertToDTO);
    }

    // SEARCH keyword
    @Transactional(readOnly = true)
    public Page<StudentDTO> search(String keyword, Pageable pageable) {
        return studentRepo.findAll(studentSpecification(keyword), pageable).map(this::convertToDTO);
    }
}
