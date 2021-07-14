package com.tmt.challenge.repository;

import com.tmt.challenge.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepo extends JpaRepository<Book, Long> {

    Page<Book> findByStudentId(Long studentId, Pageable pageable);
    Optional<Book> findByIdAndStudentId(Long id, Long studentId);
}
