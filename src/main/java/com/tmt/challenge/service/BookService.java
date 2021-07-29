package com.tmt.challenge.service;

import com.tmt.challenge.constant.enums.Operator;
import com.tmt.challenge.constant.enums.SearchOperation;
import com.tmt.challenge.dto.BookDTO;
import com.tmt.challenge.dto.BookWithStudentDTO;
import com.tmt.challenge.dto.StudentDTO;
import com.tmt.challenge.dto.StudentOnlyDTO;
import com.tmt.challenge.dto.response.DefaultResponseDTO;
import com.tmt.challenge.exception.ResourceNotFoundException;
import com.tmt.challenge.model.Book;
import com.tmt.challenge.model.Student;
import com.tmt.challenge.repository.BookRepository;
import com.tmt.challenge.repository.StudentRepository;
import com.tmt.challenge.repository.specs.BookSpecification;
import com.tmt.challenge.repository.specs.SearchCriteria;
import com.tmt.challenge.repository.specs.StudentSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
public class BookService {

    private final BookRepository bookRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public BookService(BookRepository bookRepository, StudentRepository studentRepository) {
        this.bookRepository = bookRepository;
        this.studentRepository = studentRepository;
    }

    // Convert Book to BookDTO
    private BookDTO convertToDTO(Book book) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(book.getId());
        bookDTO.setBookName(book.getBookName());
        bookDTO.setCreatedAt(book.getCreatedAt());
        return bookDTO;
    }

    // Convert Book to BookWithStudentDTO
    private BookWithStudentDTO convertToBookDTO(Book book) {
        // Instantiate new BookWithStudentDTO
        BookWithStudentDTO bookWithStudentDTO = new BookWithStudentDTO();
        bookWithStudentDTO.setId(book.getId());
        bookWithStudentDTO.setBookName(book.getBookName());
        bookWithStudentDTO.setCreatedAt(book.getCreatedAt());
        StudentOnlyDTO studentOnly = this.convertToStudentOnlyDTO(book.getStudent());
        bookWithStudentDTO.setStudents(List.of(studentOnly));
        return bookWithStudentDTO;
    }

    // Convert Student to StudentOnlyDTO
    private StudentOnlyDTO convertToStudentOnlyDTO(Student student) {
        StudentOnlyDTO studentOnlyDTO = new StudentOnlyDTO();
        studentOnlyDTO.setId(student.getId());
        studentOnlyDTO.setFirstName(student.getFirstName());
        studentOnlyDTO.setLastName(student.getLastName());
        studentOnlyDTO.setEmail(student.getEmail());
        studentOnlyDTO.setAge(student.getAge());
        return studentOnlyDTO;
    }

    // Book Specs private method
    private BookSpecification bookSpecification(String keyword) {
        BookSpecification bookSpecification = new BookSpecification();
        bookSpecification.add(new SearchCriteria("bookName", keyword, SearchOperation.MATCH, null, null, null));
        bookSpecification.operator(Operator.OR);
        return bookSpecification;
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

    // READ All Book by Student ID
    @Transactional(readOnly = true)
    public Page<BookDTO> getAllBooksByStudentId(Long studentId, Pageable pageable) {
        Page<Book> bookPage = bookRepository.findByStudentId(studentId, pageable);
        return bookPage.map(this::convertToDTO);
    }

    // UPDATE Book
    public BookDTO updateBook(Long studentId, Long bookId, Book bookRequest) {
        if (!studentRepository.existsById(studentId)) {
            throw new ResourceNotFoundException("StudentId " + studentId + " not found");
        }

        Book bookResponse = bookRepository.findById(bookId).map(book -> {
            book.setBookName(bookRequest.getBookName());
            return bookRepository.save(book);
        }).orElseThrow(() -> new ResourceNotFoundException("BookId " + bookId + " not found"));
        return convertToDTO(bookResponse);
    }

    // DELETE Book
    public DefaultResponseDTO deleteBook(Long studentId, Long bookId) {
        return bookRepository.findByIdAndStudentId(bookId, studentId).map(book -> {
            bookRepository.delete(book);
            return new DefaultResponseDTO("resource deleted successfully", 202);
        }).orElseThrow(() -> new ResourceNotFoundException("Book not found with id " + bookId + " and studentId " + studentId));
    }

    // GET All Books
    @Transactional(readOnly = true)
    public Page<BookWithStudentDTO> getAllBooks(Pageable pageable) {
        Page<Book> booksPage = bookRepository.findAll(pageable);
        List<BookWithStudentDTO> booksWithStudent = booksPage.stream().map(this::convertToBookDTO).collect(Collectors.toList());
        Page<BookWithStudentDTO> booksOutput = new PageImpl<>(booksWithStudent);
        return booksOutput;
    }

    // GET Book by ID
    @Transactional(readOnly = true)
    public BookWithStudentDTO getBookById(Long bookId) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (bookOptional.isEmpty()) {
            throw new ResourceNotFoundException("Book with id " + bookId + " not found");
        }
        Book book = bookOptional.get();
        Student student = book.getStudent();
        BookWithStudentDTO bookOutput = this.convertToBookDTO(book);
        StudentOnlyDTO studentOutput = this.convertToStudentOnlyDTO(student);
        bookOutput.setStudents(List.of(studentOutput));
        return bookOutput;
    }

    // SEARCH Book
    @Transactional(readOnly = true)
    public Page<BookWithStudentDTO> search(String keyword, Pageable pageable) {
        List<BookWithStudentDTO> bookResult = bookRepository.findAll(bookSpecification(keyword), pageable).stream().map(this::convertToBookDTO).collect(Collectors.toList());
        List<Student> studentResult = studentRepository.findAll(studentSpecification(keyword), pageable).toList();

        for (Student s : studentResult) {
            StudentOnlyDTO studentOnly = this.convertToStudentOnlyDTO(s);
            for (Book b : s.getBooks()) {
                BookWithStudentDTO book2ndResult = new BookWithStudentDTO();
                book2ndResult.setId(b.getId());
                book2ndResult.setBookName(b.getBookName());
                if (b.getCreatedAt() != null) {
                    book2ndResult.setCreatedAt(b.getCreatedAt());
                }
                book2ndResult.setStudents(List.of(studentOnly));
                // Add to result list
                bookResult.add(book2ndResult);
            }
        }

        PageImpl<BookWithStudentDTO> finalResult = new PageImpl<>(bookResult);
        return finalResult;
    }
}
