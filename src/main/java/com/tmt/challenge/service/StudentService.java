package com.tmt.challenge.service;

import com.tmt.challenge.constant.enums.Operator;
import com.tmt.challenge.constant.enums.SearchOperation;
import com.tmt.challenge.dto.*;
import com.tmt.challenge.dto.request.StudentRequestDTO;
import com.tmt.challenge.dto.response.DefaultResponseDTO;
import com.tmt.challenge.exception.ResourceNotFoundException;
import com.tmt.challenge.model.Book;
import com.tmt.challenge.model.Course;
import com.tmt.challenge.model.Student;
import com.tmt.challenge.model.StudentIdCard;
import com.tmt.challenge.repository.StudentRepository;
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

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
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
    public DefaultResponseDTO addNewStudent(StudentRequestDTO studentRequest) {
        // Check Student with existing email
        Optional<Student> studentOptional = studentRepository.findStudentByEmail(studentRequest.getEmail());
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
        studentRepository.save(student);
        return new DefaultResponseDTO("resource created successfully", 201);
    }

    // READ All Students
    @Transactional(readOnly = true)
    public List<StudentDTO> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return students.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // READ Student by ID
    @Transactional(readOnly = true)
    public StudentDTO getStudentById(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("student with id " + studentId + " not found"));
        return convertToDTO(student);
    }

    // READ Student by Email
    @Transactional(readOnly = true)
    public StudentDTO getStudentByEmail(String email) {
        Student student = studentRepository.findStudentByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("student with email " + email + " not found"));
        return convertToDTO(student);
    }

    // UPDATE Student
    public DefaultResponseDTO updateStudent(Long studentId, String firstName, String lastName) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("student with id " + studentId + " not found"));
        DefaultResponseDTO defaultResponseDTO = new DefaultResponseDTO();
        String message = "request success. but, nothing changed";
        if (firstName != null && firstName.length() > 0 && !Objects.equals(student.getFirstName(), firstName)) {
            student.setFirstName(firstName);
            message = "resource updated successfully";
        }
        if (lastName != null && lastName.length() > 0 && !Objects.equals(student.getLastName(), lastName)) {
            student.setLastName(lastName);
            message = "resource updated successfully";
        }
        defaultResponseDTO.setMessage(message);
        return defaultResponseDTO;
    }

    // DELETE Student
    public DefaultResponseDTO deleteStudent(Long studentId) {
        boolean isStudentExist = studentRepository.existsById(studentId);
        DefaultResponseDTO defaultResponseDTO = new DefaultResponseDTO();
        if (isStudentExist) {
            studentRepository.deleteById(studentId);
            defaultResponseDTO.setStatus(202);
            defaultResponseDTO.setMessage("resource deleted successfully");
        } else {
            throw new ResourceNotFoundException("student with id " + studentId + " not found");
        }
        return defaultResponseDTO;
    }

    // READ Student by Card Number
    @Transactional(readOnly = true)
    public StudentDTO getStudentByCardNumber(String cardNumber) {
        Student student = studentRepository.findStudentByStudentIdCardCardNumber(cardNumber)
                .orElseThrow(() -> new ResourceNotFoundException("student ID with cardNumber " + cardNumber + " not found"));
        return convertToDTO(student);
    }

    // READ Student by Department
    @Transactional(readOnly = true)
    public List<StudentDTO> getStudentByDepartment(String department) {
        List<Student> studentList = studentRepository.findStudentByCoursesDepartment(department);
        return studentList.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // READ Students by Book Name
    @Transactional(readOnly = true)
    public Page<StudentDTO> getStudentsByBookName(String bookName, Pageable pageable) {
        Page<Student> studentPage = studentRepository.findStudentsByBookName(bookName, pageable);
        return studentPage.map(this::convertToDTO);
    }

    // READ Students by Course Name
    @Transactional(readOnly = true)
    public Page<StudentDTO> getStudentsByCourseName(String courseName, Pageable pageable) {
        Page<Student> studentPage = studentRepository.findStudentsByCourseName(courseName, pageable);
        return studentPage.map(this::convertToDTO);
    }

    // SEARCH keyword
    @Transactional(readOnly = true)
    public Page<StudentDTO> search(String keyword, Pageable pageable) {
        return studentRepository.findAll(studentSpecification(keyword), pageable).map(this::convertToDTO);
    }
}
