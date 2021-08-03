package com.tmt.challenge.service;

import com.tmt.challenge.constant.enums.Operator;
import com.tmt.challenge.constant.enums.SearchOperation;
import com.tmt.challenge.dto.BookDTO;
import com.tmt.challenge.dto.BookWithStudentDTO;
import com.tmt.challenge.dto.StudentOnlyDTO;
import com.tmt.challenge.dto.response.DefaultResponseDTO;
import com.tmt.challenge.exception.ResourceNotFoundException;
import com.tmt.challenge.mapper.BookMapper;
import com.tmt.challenge.mapper.StudentMapper;
import com.tmt.challenge.model.Book;
import com.tmt.challenge.model.Student;
import com.tmt.challenge.repository.BookRepository;
import com.tmt.challenge.repository.StudentRepository;
import com.tmt.challenge.repository.specs.BookSpecification;
import com.tmt.challenge.repository.specs.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookService {

    private final BookRepository bookRepository;
    private final StudentRepository studentRepository;
    private final BookMapper bookMapper;
    private final StudentMapper studentMapper;

    @Autowired
    public BookService(BookRepository bookRepository, StudentRepository studentRepository, BookMapper bookMapper, StudentMapper studentMapper) {
        this.bookRepository = bookRepository;
        this.studentRepository = studentRepository;
        this.bookMapper = bookMapper;
        this.studentMapper = studentMapper;
    }

    // Book Specs private method
    private BookSpecification bookSpecification(String keyword) {
        BookSpecification bookSpecification = new BookSpecification();
        bookSpecification.add(new SearchCriteria("bookName", keyword, SearchOperation.MATCH, null, null, null));
        bookSpecification.add(new SearchCriteria("email", keyword, SearchOperation.MATCH, "student", null, null));
        bookSpecification.add(new SearchCriteria("firstName", keyword, SearchOperation.MATCH, "student", null, null));
        bookSpecification.add(new SearchCriteria("lastName", keyword, SearchOperation.MATCH, "student", null, null));
        bookSpecification.operator(Operator.OR);
        return bookSpecification;
    }

    // READ All Book by Student ID
    @Transactional(readOnly = true)
    public Page<BookDTO> getAllBooksByStudentId(Long studentId, Pageable pageable) {
        Page<Book> bookPage = bookRepository.findByStudentId(studentId, pageable);
        return bookPage.map(bookMapper::toBookDTO);
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
        return bookMapper.toBookDTO(bookResponse);
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
        return booksPage.map(bookMapper::toBookWithStudentDTO);
    }

    // GET Book by ID
    @Transactional(readOnly = true)
    public BookWithStudentDTO getBookById(Long bookId) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (bookOptional.isEmpty()) {
            throw new ResourceNotFoundException("Book with id " + bookId + " not found");
        }
        Book book = bookOptional.get();
        BookWithStudentDTO bookOutput = bookMapper.toBookWithStudentDTO(book);
        return bookOutput;
    }

    // SEARCH Book
    @Transactional(readOnly = true)
    public Page<BookWithStudentDTO> search(String keyword, Pageable pageable) {
        Page<BookWithStudentDTO> bookResult = bookRepository.findAll(bookSpecification(keyword), pageable).map(bookMapper::toBookWithStudentDTO);
        return bookResult;
    }
}
