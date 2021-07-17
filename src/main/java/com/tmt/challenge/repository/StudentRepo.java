package com.tmt.challenge.repository;

import com.tmt.challenge.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepo extends JpaRepository<Student, Long> {

    @Query("SELECT s FROM Student s WHERE s.email = ?1")
    Optional<Student> findStudentByEmail(String email);
    Optional<Student> findStudentByStudentIdCardCardNumber(String cardNumber);
    List<Student> findStudentByCoursesDepartment(String department);
    @Query("SELECT s FROM Student s LEFT JOIN Book b ON s.id = b.student.id WHERE b.bookName = :bookName")
    Page<Student> findStudentsByBookName(@Param("bookName") String bookName, Pageable pageable);
    @Query(value = "SELECT * " +
                   "FROM student as s " +
                   "JOIN student_courses as sc ON s.id = sc.student_id " +
                   "JOIN course as c ON sc.course_id = c.id " +
                   "WHERE c.name = :courseName", nativeQuery = true)
    Page<Student> findStudentsByCourseName(@Param("courseName") String courseName, Pageable pageable);

}
