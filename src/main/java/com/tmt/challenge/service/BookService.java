package com.tmt.challenge.service;

import com.tmt.challenge.dto.BookDTO;
import com.tmt.challenge.dto.ResponseDTO;
import com.tmt.challenge.exception.ResourceNotFoundException;
import com.tmt.challenge.model.Book;
import com.tmt.challenge.repository.BookRepository;
import com.tmt.challenge.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
    public ResponseDTO deleteBook(Long studentId, Long bookId) {
        return bookRepository.findByIdAndStudentId(bookId, studentId).map(book -> {
            bookRepository.delete(book);
            return new ResponseDTO("resource deleted successfully", 202);
        }).orElseThrow(() -> new ResourceNotFoundException("Book not found with id " + bookId + " and studentId " + studentId));
    }
}
