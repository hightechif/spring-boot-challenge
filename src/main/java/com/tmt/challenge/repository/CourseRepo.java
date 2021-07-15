package com.tmt.challenge.repository;

import com.tmt.challenge.model.Course;
import com.tmt.challenge.model.StudentIdCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepo extends JpaRepository<Course, Long> {

    Optional<Course> findCourseByDepartment(String cardNumber);

}
