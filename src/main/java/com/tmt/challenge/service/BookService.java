package com.tmt.challenge.service;

import com.tmt.challenge.constant.enums.Operator;
import com.tmt.challenge.constant.enums.SearchOperation;
import com.tmt.challenge.dto.BookDTO;
import com.tmt.challenge.dto.BookWithStudentDTO;
import com.tmt.challenge.dto.response.DefaultResponseDTO;
import com.tmt.challenge.exception.ResourceNotFoundException;
import com.tmt.challenge.mapper.BookMapper;
import com.tmt.challenge.model.Book;
import com.tmt.challenge.repository.BookRepository;
import com.tmt.challenge.repository.StudentRepository;
import com.tmt.challenge.repository.specs.BookSpecification;
import com.tmt.challenge.repository.specs.SearchCriteria;
import org.jetbrains.annotations.NotNull;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class BookService {

    private final BookRepository bookRepository;
    private final StudentRepository studentRepository;
    private final BookMapper bookMapper;

    @Autowired
    public BookService(BookRepository bookRepository, StudentRepository studentRepository) {
        this.bookRepository = bookRepository;
        this.studentRepository = studentRepository;
        this.bookMapper = Mappers.getMapper(BookMapper.class);
    }

    /**
     * {@code Book Specs private method} : create a book specification from a keyword
     *
     * @param keyword the first input string
     * @return return a book specification
     */
    @NotNull
    private BookSpecification bookSpecification(String keyword) {
        BookSpecification bookSpecification = new BookSpecification();
        bookSpecification.add(new SearchCriteria("bookName", keyword, SearchOperation.MATCH, null, null, null));
        bookSpecification.add(new SearchCriteria("email", keyword, SearchOperation.MATCH, "student", null, null));
        bookSpecification.add(new SearchCriteria("firstName", keyword, SearchOperation.MATCH, "student", null, null));
        bookSpecification.add(new SearchCriteria("lastName", keyword, SearchOperation.MATCH, "student", null, null));
        bookSpecification.operator(Operator.OR);
        return bookSpecification;
    }

    /**
     * {@code book service getAllBooksByStudentId method} : READ All Books by Student ID
     *
     * @param studentId the first input long
     * @param pageable  the second input pageable
     * @return return book DTO page
     */
    @Transactional(readOnly = true)
    public Page<BookDTO> getAllBooksByStudentId(Long studentId, Pageable pageable) {
        Page<Book> bookPage = bookRepository.findByStudentId(studentId, pageable);
        return bookPage.map(bookMapper::toBookDTO);
    }

    /**
     * {@code book service updateBook method} : UPDATE Book
     *
     * @param studentId   the first input long
     * @param bookId      the second input long
     * @param bookRequest the third input book
     * @return return a book DTO
     */
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

    /**
     * {@code book service deleteBook method} : DELETE Book
     *
     * @param studentId the first input long
     * @param bookId    the second input long
     * @return return a default response DTO
     */
    public DefaultResponseDTO deleteBook(Long studentId, Long bookId) {
        return bookRepository.findByIdAndStudentId(bookId, studentId).map(book -> {
            bookRepository.delete(book);
            return new DefaultResponseDTO("resource deleted successfully", 202);
        }).orElseThrow(() -> new ResourceNotFoundException("Book not found with id " + bookId + " and studentId " + studentId));
    }

    /**
     * {@code book service getAllBooks method} : GET All Books
     *
     * @param pageable the first input pageable
     * @return return a Book with Student DTO page
     */
    @Transactional(readOnly = true)
    public Page<BookWithStudentDTO> getAllBooks(Pageable pageable) {
        Page<Book> booksPage = bookRepository.findAll(pageable);
        return booksPage.map(bookMapper::toBookWithStudentDTO);
    }

    /**
     * {@code book service getBookById method} : GET Book by ID
     *
     * @param bookId the first input long
     * @return return a Book with Student DTO
     */
    @Transactional(readOnly = true)
    public BookWithStudentDTO getBookById(Long bookId) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (bookOptional.isEmpty()) {
            throw new ResourceNotFoundException("Book with id " + bookId + " not found");
        }
        Book book = bookOptional.get();
        return bookMapper.toBookWithStudentDTO(book);
    }

    /**
     * {@code book service search method} : SEARCH Book
     *
     * @param keyword  the keyword for filter bookName, firstName, lastName, and/or email
     * @param pageable the pagination information
     * @return return a Book with Student DTO page
     */
    @Transactional(readOnly = true)
    public Page<BookWithStudentDTO> search(String keyword, Pageable pageable) {
        Page<Book> books = bookRepository.findAll(bookSpecification(keyword), pageable);
        return books.map(bookMapper::toBookWithStudentDTO);
    }
}
