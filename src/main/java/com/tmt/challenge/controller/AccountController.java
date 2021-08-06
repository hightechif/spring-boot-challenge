package com.tmt.challenge.controller;

import com.tmt.challenge.dto.AccountDTO;
import com.tmt.challenge.dto.response.DefaultResponseDTO;
import com.tmt.challenge.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/accounts")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/")
    public ResponseEntity<List<AccountDTO>> getAll() {
        List<AccountDTO> accountDTOS = accountService.getAll();
        return new ResponseEntity<>(accountDTOS, HttpStatus.OK);
    }

    @GetMapping("/{accountNumber}/{accountType}")
    public ResponseEntity<AccountDTO> get(@PathVariable(value = "accountNumber") String accountNumber,
                                              @PathVariable(value = "accountType") String accountType) {
        AccountDTO accountDTO = accountService.get(accountNumber, accountType);
        return new ResponseEntity<>(accountDTO, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<AccountDTO> create(@RequestBody AccountDTO accountDTO) {
        return ResponseEntity.created(URI.create("/create/" + accountDTO.getAccountNumber())).body(accountService.create(accountDTO));
    }

    @PutMapping("/edit/{accountNumber}/{accountType}")
    public ResponseEntity<DefaultResponseDTO> update(@PathVariable(value = "accountNumber") String accountNumber,
                                                         @PathVariable(value = "accountType") String accountType,
                                                         @RequestParam(required = false) Double balance) {
        DefaultResponseDTO response = accountService.update(accountNumber, accountType, balance);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete/{accountNumber}/{accountType}")
    public ResponseEntity<DefaultResponseDTO> delete(@PathVariable(value = "accountNumber") String accountNumber,
                                                @PathVariable(value = "accountType") String accountType) {
        DefaultResponseDTO response = accountService.delete(accountNumber, accountType);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

}
