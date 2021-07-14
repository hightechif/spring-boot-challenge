package com.tmt.challenge.controller;

import com.tmt.challenge.model.Book;
import com.tmt.challenge.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/students/{studentId}/books")
    public Book createBook(@PathVariable(value = "studentId") Long studentId,
                           @Valid @RequestBody Book book) {
        return bookService.createBook(studentId, book);
    }

    @GetMapping("/students/{studentId}/books")
    public Page<Book> getAllBooksByStudentId(@PathVariable(value = "studentId") Long studentId,
                                             Pageable pageable) {
        return bookService.getAllBooksByStudentId(studentId, pageable);
    }

    @PutMapping("/students/{studentId}/books/{bookId}")
    public Book updateBook(@PathVariable(value = "studentId") Long studentId,
                           @PathVariable(value = "bookId") Long bookId,
                           @Valid @RequestBody Book book) {
        return bookService.updateBook(studentId, bookId, book);
    }

    @DeleteMapping("/posts/{postId}/books/{bookId}")
    public ResponseEntity<?> deleteBook(@PathVariable(value = "studentId") Long studendId,
                                        @PathVariable(value = "bookId") Long bookId) {
        return bookService.deleteBook(studendId, bookId);
    }
}
