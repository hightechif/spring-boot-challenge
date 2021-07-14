package com.tmt.challenge.service;

import com.tmt.challenge.repository.BookRepo;
import com.tmt.challenge.repository.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
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
}
