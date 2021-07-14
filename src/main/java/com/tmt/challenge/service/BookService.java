package com.tmt.challenge.service;

import com.tmt.challenge.exception.ResourceNotFoundException;
import com.tmt.challenge.model.Book;
import com.tmt.challenge.repository.BookRepo;
import com.tmt.challenge.repository.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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

    // CREATE Book
    public Book createBook(Long studentId, Book book) {
        return studentRepo.findById(studentId).map(student -> {
            book.setStudent(student);
            return bookRepo.save(book);
        }).orElseThrow(() -> new ResourceNotFoundException("StudentId " + studentId + " not found"));
    }

    // REAL All Book by Student ID
    public Page<Book> getAllBooksByStudentId(Long studentId, Pageable pageable) {
        return bookRepo.findByStudentId(studentId, pageable);
    }

    // UPDATE Book
    public Book updateBook(Long studentId, Long bookId, Book bookRequest) {
        if (!studentRepo.existsById(studentId)) {
            throw new ResourceNotFoundException("StudentId " + studentId + " not found");
        }

        return bookRepo.findById(bookId).map(book -> {
            book.setBookName(bookRequest.getBookName());
            return bookRepo.save(book);
        }).orElseThrow(() -> new ResourceNotFoundException("BookId " + bookId + " not found"));
    }

    // DELETE Book
    public ResponseEntity<?> deleteBook(Long studendId, Long bookId) {
        return bookRepo.findByIdAndStudentId(bookId, studendId).map(book -> {
            bookRepo.delete(book);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Book not found with id " + bookId + " and studentId " + studendId));
    }
}
