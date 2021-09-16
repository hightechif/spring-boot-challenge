package com.tmt.challenge.controller;

import com.tmt.challenge.dto.BookDTO;
import com.tmt.challenge.dto.BookWithStudentDTO;
import com.tmt.challenge.dto.response.DefaultResponseDTO;
import com.tmt.challenge.model.Book;
import com.tmt.challenge.service.BookService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * {@code GET /api/v1/students/{studentId}/books} : get a list of all books
     *
     * @param studentId the first input long
     * @param pageable  the second input pageable
     * @return return a response entity of book DTO list
     */
    @GetMapping("/students/{studentId}/books")
    public ResponseEntity<List<BookDTO>> getAllBooksByStudentId(@PathVariable(value = "studentId") Long studentId,
                                                                Pageable pageable) {
        List<BookDTO> bookDTOs = bookService.getAllBooksByStudentId(studentId, pageable).getContent();
        return new ResponseEntity<>(bookDTOs, HttpStatus.OK);
    }

    /**
     * {@code PUT /api/v1/students/{studentId}/books/{bookId}} : update book of student
     *
     * @param studentId   the first input long
     * @param bookId      the second input long
     * @param bookRequest the third input book
     * @return return a response entity of book DTO
     */
    @PutMapping("/students/{studentId}/books/{bookId}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable(value = "studentId") Long studentId,
                                              @PathVariable(value = "bookId") Long bookId,
                                              @Valid @RequestBody Book bookRequest) {
        BookDTO bookDTO = bookService.updateBook(studentId, bookId, bookRequest);
        return new ResponseEntity<>(bookDTO, HttpStatus.OK);
    }

    /**
     * {@code DELETE /api/v1/students/{studentId}/books/delete/{bookId}} : delete book
     *
     * @param studentId the first input long
     * @param bookId    the second input long
     * @return return a response entity of default response DTO
     */
    @DeleteMapping("/students/{studentId}/books/delete/{bookId}")
    public ResponseEntity<DefaultResponseDTO> deleteBook(@PathVariable(value = "studentId") Long studentId,
                                                         @PathVariable(value = "bookId") Long bookId) {
        DefaultResponseDTO response = bookService.deleteBook(studentId, bookId);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    /**
     * {@code GET /api/v1/books} : get a list of all books with student
     *
     * @param pageable the first input pageable
     * @return return a response entity of Book with Student DTO page
     */
    @GetMapping("/books")
    public ResponseEntity<Page<BookWithStudentDTO>> getAllBooks(Pageable pageable) {
        Page<BookWithStudentDTO> response = bookService.getAllBooks(pageable);
        return ResponseEntity.ok(response);
    }

    /**
     * {@code GET /api/v1/books/{bookId}} : get a Book by Book ID
     *
     * @param bookId the first input long
     * @return return a response entity of Book with Student DTO
     */
    @GetMapping("/books/{bookId}")
    public ResponseEntity<BookWithStudentDTO> getBookById(@PathVariable(value = "bookId") Long bookId) {
        BookWithStudentDTO response = bookService.getBookById(bookId);
        return ResponseEntity.ok(response);
    }

    /**
     * {@code GET /books/search} : search all books from related keyword
     *
     * @param keyword  the keyword for filter bookName, firstName, lastName, and/or email
     * @param pageable the pagination information
     * @return return a response entity of Book with Student DTO page
     */
    @GetMapping("/books/search")
    public ResponseEntity<Page<BookWithStudentDTO>> search(@NotNull @RequestParam Optional<String> keyword, Pageable pageable) {
        Page<BookWithStudentDTO> response = bookService.search(keyword.orElse(""), pageable);
        return ResponseEntity.ok().body(response);
    }
}
