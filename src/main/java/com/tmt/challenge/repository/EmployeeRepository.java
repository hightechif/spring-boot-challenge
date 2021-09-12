package com.tmt.challenge.repository;

import com.tmt.challenge.model.Employee;
import com.tmt.challenge.model.composite.EmployeeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, EmployeeId>, JpaSpecificationExecutor<Employee> {

    Optional<Employee> findEmployeeByEmail(String email);
    Optional<Employee> findEmployeeByPhoneNumber(String phone);
    @Query(value = "SELECT MAX(address_ref) FROM employee",
            nativeQuery = true)
    Optional<Long> findLastAddressRefNumber();

}
