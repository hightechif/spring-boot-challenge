package com.tmt.challenge.repository;

import com.tmt.challenge.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepo extends JpaRepository<Student, Long> {

    // SELECT * FROM student WHERE email = ?
    // @Query("SELECT s FROM Student s WHERE s.email = ?1")
    Optional<Student> findStudentByEmail(String email);
    Optional<Student> findStudentByStudentIdCardCardNumber(String cardNumber);
    List<Student> findStudentByCoursesDepartment(String department);

}
