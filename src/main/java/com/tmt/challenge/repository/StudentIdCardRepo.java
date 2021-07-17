package com.tmt.challenge.repository;

import com.tmt.challenge.model.StudentIdCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentIdCardRepo extends JpaRepository<StudentIdCard, Long> {

}
