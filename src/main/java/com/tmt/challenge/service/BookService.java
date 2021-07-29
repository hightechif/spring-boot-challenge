package com.tmt.challenge.service;

import com.tmt.challenge.dto.BookDTO;
import com.tmt.challenge.dto.BookWithStudentDTO;
import com.tmt.challenge.dto.StudentOnlyDTO;
import com.tmt.challenge.dto.response.DefaultResponseDTO;
import com.tmt.challenge.exception.ResourceNotFoundException;
import com.tmt.challenge.model.Book;
import com.tmt.challenge.model.Student;
import com.tmt.challenge.repository.BookRepository;
import com.tmt.challenge.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
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

    // READ All Book by Student ID
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
    public Page<BookWithStudentDTO> getAllBooks(Pageable pageable) {
        Page<Book> booksPage = bookRepository.findAll(pageable);
        List<BookWithStudentDTO> booksWithStudent = booksPage.stream().map(this::convertToBookDTO).collect(Collectors.toList());
        Page<BookWithStudentDTO> booksOutput = new PageImpl<>(booksWithStudent);
        return booksOutput;
    }

    // GET Book by ID
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
}
