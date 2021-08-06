package com.tmt.challenge.service;

import com.tmt.challenge.dto.AccountDTO;
import com.tmt.challenge.dto.response.DefaultResponseDTO;
import com.tmt.challenge.exception.ResourceNotFoundException;
import com.tmt.challenge.mapper.AccountMapper;
import com.tmt.challenge.model.Account;
import com.tmt.challenge.model.AccountId;
import com.tmt.challenge.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Autowired
    public AccountService(AccountRepository accountRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    public AccountDTO create(AccountDTO accountDTO) {
        Account account = accountRepository.save(accountMapper.toAccountEntity(accountDTO));
        return accountMapper.toAccountDTO(account);
    }

    @Transactional(readOnly = true)
    public List<AccountDTO> getAll() {
        List<Account> accounts = accountRepository.findAll();
        return accountMapper.toAccountDTO(accounts);
    }

    @Transactional(readOnly = true)
    public AccountDTO get(String accountNumber, String accountType) {
        Optional<Account> accountOptional = accountRepository.findById(new AccountId(accountNumber, accountType));
        if (accountOptional.isEmpty()) {
            throw new ResourceNotFoundException("account not found");
        }
        Account account = accountOptional.get();
        return accountMapper.toAccountDTO(account);
    }

    public DefaultResponseDTO update(String accountNumber, String accountType, Double balance) {
        Account account = accountRepository.findById(new AccountId(accountNumber, accountType))
                .orElseThrow(() -> new ResourceNotFoundException("account not found"));
        DefaultResponseDTO defaultResponseDTO = new DefaultResponseDTO();
        String message = "request success. but, nothing changed";
        if (balance != null && balance >= 0 && !Objects.equals(account.getBalance(), balance)) {
            account.setBalance(balance);
            message = "resource updated successfully";
        }
        defaultResponseDTO.setStatus(HttpStatus.ACCEPTED.value());
        defaultResponseDTO.setMessage(message);
        return defaultResponseDTO;
    }

    public DefaultResponseDTO delete(String accountNumber, String accountType) {
        AccountId accountId = new AccountId(accountNumber, accountType);
        boolean isAccountExist = accountRepository.existsById(accountId);
        DefaultResponseDTO defaultResponseDTO = new DefaultResponseDTO();
        if (isAccountExist) {
            accountRepository.deleteById(accountId);
            defaultResponseDTO.setStatus(HttpStatus.ACCEPTED.value());
            defaultResponseDTO.setMessage("resource deleted successfully");
        } else {
            throw new ResourceNotFoundException("account not found");
        }
        return defaultResponseDTO;
    }
}
