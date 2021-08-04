package com.tmt.challenge.repository;

import com.tmt.challenge.model.Employee;
import com.tmt.challenge.model.EmployeeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, EmployeeId> {

}
