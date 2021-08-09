package com.tmt.challenge.repository;

import com.tmt.challenge.model.Book;
import com.tmt.challenge.model.Course;
import com.tmt.challenge.model.Student;
import com.tmt.challenge.model.StudentIdCard;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository underTest;

    @Test
    void itShouldCheckIfStudentExistByEmail() {
        // given
        String email = "ridhan.fadhilah@yopmail.com";
        Student student = new Student(
                "Ridhan",
                "Fadhilah",
                email,
                LocalDate.of(1997, 1, 12)
        );
        Book book = new Book("Atomic Habit", student);
        StudentIdCard idCard = new StudentIdCard("120197001", student);
        Course course = new Course("Quantum Physics", "Physics");
        student.setBooks(List.of(book));
        student.setStudentIdCard(idCard);
        student.setCourses(List.of(course));
        underTest.save(student);

        // when
        boolean exists = underTest.findStudentByEmail(email).isPresent();

        // then
        assertThat(exists).isTrue();
    }
}