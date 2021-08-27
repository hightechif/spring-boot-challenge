package com.tmt.challenge.repository;

import com.tmt.challenge.model.Account;
import com.tmt.challenge.model.composite.AccountId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, AccountId> {

}
