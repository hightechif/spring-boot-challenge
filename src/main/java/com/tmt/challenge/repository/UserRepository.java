package com.tmt.challenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tmt.challenge.model.DAOUser;


@Repository
public interface UserRepository extends JpaRepository<DAOUser, Long> {

    DAOUser findByUsername(String username);

}
