package com.tmt.challenge.service;

import com.tmt.challenge.model.Book;
import com.tmt.challenge.repository.BookRepo;
import com.tmt.challenge.repository.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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

    public Book createBook(Long studentId, Book book) {
        return new Book();
    }

    public Page<Book> getAllBooksByStudentId(Long studentId, Pageable pageable) {
        return Page.empty();
    }


    public Book updateBook(Long studentId, Long bookId, Book book) {
        return new Book();
    }

    public ResponseEntity<?> deleteBook(Long studendId, Long bookId) {
        return new ResponseEntity<>("Not Implemented Yet", HttpStatus.OK);
    }
}
