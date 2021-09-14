package com.tmt.challenge.repository;

import com.tmt.challenge.model.EmployeeAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeAddressRepository extends JpaRepository<EmployeeAddress, Long> {

    void deleteEmployeeAddressByEmployeeAddressRef(Long addressRef);

}
