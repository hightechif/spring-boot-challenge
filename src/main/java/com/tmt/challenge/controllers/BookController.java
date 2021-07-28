package com.tmt.challenge.controllers;

import com.tmt.challenge.dto.BookDTO;
import com.tmt.challenge.dto.response.DefaultResponseDTO;
import com.tmt.challenge.model.Book;
import com.tmt.challenge.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/students/{studentId}/books")
    public ResponseEntity<List<BookDTO>> getAllBooksByStudentId(@PathVariable(value = "studentId") Long studentId,
                                                                Pageable pageable) {
        List<BookDTO> bookDTOs = bookService.getAllBooksByStudentId(studentId, pageable).getContent();
        return new ResponseEntity<>(bookDTOs, HttpStatus.OK);
    }

    @PutMapping("/students/{studentId}/books/{bookId}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable(value = "studentId") Long studentId,
                                              @PathVariable(value = "bookId") Long bookId,
                                              @Valid @RequestBody Book bookRequest) {
        BookDTO bookDTO = bookService.updateBook(studentId, bookId, bookRequest);
        return new ResponseEntity<>(bookDTO, HttpStatus.OK);
    }

    @DeleteMapping("/students/{studentId}/books/delete/{bookId}")
    public ResponseEntity<DefaultResponseDTO> deleteBook(@PathVariable(value = "studentId") Long studentId,
                                                         @PathVariable(value = "bookId") Long bookId) {
        DefaultResponseDTO defaultResponseDTO = bookService.deleteBook(studentId, bookId);
        return new ResponseEntity<>(defaultResponseDTO, HttpStatus.ACCEPTED);
    }
}
