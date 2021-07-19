package com.tmt.challenge.service;

import com.tmt.challenge.dto.BookDTO;
import com.tmt.challenge.dto.ResponseDTO;
import com.tmt.challenge.exception.ResourceNotFoundException;
import com.tmt.challenge.model.Book;
import com.tmt.challenge.repository.BookRepo;
import com.tmt.challenge.repository.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final BookRepo bookRepo;
    private final StudentRepo studentRepo;

    @Autowired
    public BookService(BookRepo bookRepo, StudentRepo studentRepo) {
        this.bookRepo = bookRepo;
        this.studentRepo = studentRepo;
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
        Page<Book> bookPage = bookRepo.findByStudentId(studentId, pageable);
        return bookPage.map(this::convertToDTO);
    }

    // UPDATE Book
    public BookDTO updateBook(Long studentId, Long bookId, Book bookRequest) {
        if (!studentRepo.existsById(studentId)) {
            throw new ResourceNotFoundException("StudentId " + studentId + " not found");
        }

        Book bookResponse = bookRepo.findById(bookId).map(book -> {
            book.setBookName(bookRequest.getBookName());
            return bookRepo.save(book);
        }).orElseThrow(() -> new ResourceNotFoundException("BookId " + bookId + " not found"));
        return convertToDTO(bookResponse);
    }

    // DELETE Book
    public ResponseDTO deleteBook(Long studentId, Long bookId) {
        return bookRepo.findByIdAndStudentId(bookId, studentId).map(book -> {
            bookRepo.delete(book);
            return new ResponseDTO("resource deleted successfully", 202);
        }).orElseThrow(() -> new ResourceNotFoundException("Book not found with id " + bookId + " and studentId " + studentId));
    }
}
