package com.tmt.challenge.service;

import com.tmt.challenge.constant.enums.Operator;
import com.tmt.challenge.constant.enums.SearchOperation;
import com.tmt.challenge.dto.BookDTO;
import com.tmt.challenge.dto.CourseDTO;
import com.tmt.challenge.dto.StudentDTO;
import com.tmt.challenge.dto.StudentIdCardDTO;
import com.tmt.challenge.dto.request.StudentRequestDTO;
import com.tmt.challenge.dto.response.DefaultResponseDTO;
import com.tmt.challenge.exception.ResourceNotFoundException;
import com.tmt.challenge.mapper.BookMapper;
import com.tmt.challenge.mapper.CourseMapper;
import com.tmt.challenge.mapper.StudentIdCardMapper;
import com.tmt.challenge.mapper.StudentMapper;
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

@Service
@Transactional
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final BookMapper bookMapper;
    private final StudentIdCardMapper studentIdCardMapper;
    private final CourseMapper courseMapper;

    @Autowired
    public StudentService(StudentRepository studentRepository, StudentMapper studentMapper, BookMapper bookMapper, StudentIdCardMapper studentIdCardMapper, CourseMapper courseMapper) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
        this.bookMapper = bookMapper;
        this.studentIdCardMapper = studentIdCardMapper;
        this.courseMapper = courseMapper;
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
    public DefaultResponseDTO create(StudentRequestDTO studentRequest) {
        // Check Student with existing email
        Optional<Student> studentOptional = studentRepository.findStudentByEmail(studentRequest.getEmail());
        if (studentOptional.isPresent()) {
            throw new IllegalStateException("email already exist");
        }
        // Extract Student from request body
        Student student = studentRequest.getStudent();
        // Extract Collection of Book from request body and set student to it
        List<BookDTO> booksDTOS = studentRequest.getBooks();
        List<Book> books = bookMapper.toBookEntity(booksDTOS);
        books.forEach(x -> {
            x.setStudent(student);
        });
        // Extract StudentIdCard from request body
        StudentIdCardDTO studentIdCardDTO = studentRequest.getStudentIdCard();
        StudentIdCard studentIdCard = studentIdCardMapper.toIdCardEntity(studentIdCardDTO);
        studentIdCard.setStudent(student);
        // Extract Collection of course from request body and set student of it
        List<CourseDTO> coursesDTOS = studentRequest.getCourses();
        List<Course> courses = courseMapper.toCourseEntity(coursesDTOS);
        courses.forEach(x -> {
            x.setStudents(List.of(student));
        });
        // Set all extracted data into student object and save it
        student.setBooks(books);
        student.setStudentIdCard(studentIdCard);
        student.setCourses(courses);
        studentRepository.save(student);
        return new DefaultResponseDTO("resource created successfully", 201);
    }

    // READ All Students
    @Transactional(readOnly = true)
    public List<StudentDTO> getAll() {
        List<Student> students = studentRepository.findAll();
        return studentMapper.toStudentDTO(students);
    }

    // READ Student by ID
    @Transactional(readOnly = true)
    public StudentDTO get(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("student with id " + studentId + " not found"));
        return studentMapper.toStudentDTO(student);
    }

    // READ Student by Email
    @Transactional(readOnly = true)
    public StudentDTO getByEmail(String email) {
        Student student = studentRepository.findStudentByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("student with email " + email + " not found"));
        return studentMapper.toStudentDTO(student);
    }

    // UPDATE Student
    public DefaultResponseDTO update(Long studentId, String firstName, String lastName) {
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
    public DefaultResponseDTO delete(Long studentId) {
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
    public StudentDTO getByCardNumber(String cardNumber) {
        Student student = studentRepository.findStudentByStudentIdCardCardNumber(cardNumber)
                .orElseThrow(() -> new ResourceNotFoundException("student ID with cardNumber " + cardNumber + " not found"));
        return studentMapper.toStudentDTO(student);
    }

    // READ Student by Department
    @Transactional(readOnly = true)
    public List<StudentDTO> getByDepartment(String department) {
        List<Student> studentList = studentRepository.findStudentByCoursesDepartment(department);
        return studentMapper.toStudentDTO(studentList);
    }

    // READ Students by Book Name
    @Transactional(readOnly = true)
    public Page<StudentDTO> getByBookName(String bookName, Pageable pageable) {
        Page<Student> studentPage = studentRepository.findStudentsByBookName(bookName, pageable);
        return studentPage.map(studentMapper::toStudentDTO);
    }

    // READ Students by Course Name
    @Transactional(readOnly = true)
    public Page<StudentDTO> getByCourseName(String courseName, Pageable pageable) {
        Page<Student> studentPage = studentRepository.findStudentsByCourseName(courseName, pageable);
        return studentPage.map(studentMapper::toStudentDTO);
    }

    // SEARCH keyword
    @Transactional(readOnly = true)
    public Page<StudentDTO> search(String keyword, Pageable pageable) {
        return studentRepository.findAll(studentSpecification(keyword), pageable).map(studentMapper::toStudentDTO);
    }
}
