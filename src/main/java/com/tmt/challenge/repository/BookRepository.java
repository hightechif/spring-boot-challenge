package com.tmt.challenge.repository;

import com.tmt.challenge.model.Book;
import com.tmt.challenge.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

    Page<Book> findByStudentId(Long studentId, Pageable pageable);
    Optional<Book> findByIdAndStudentId(Long id, Long studentId);

}
