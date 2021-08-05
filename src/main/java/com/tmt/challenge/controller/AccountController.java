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
@RequestMapping(value = "/account")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/create")
    public ResponseEntity<AccountDTO> create(@RequestBody AccountDTO accountDTO) {
        return ResponseEntity.created(URI.create("/create/" + accountDTO.getAccountNumber())).body(accountService.create(accountDTO));
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<AccountDTO>> getAll() {
        List<AccountDTO> accountDTOS = accountService.getAll();
        return new ResponseEntity<>(accountDTOS, HttpStatus.OK);
    }

    @GetMapping("/get-by-id/{accountNumber}/{accountType}")
    public ResponseEntity<AccountDTO> getById(@PathVariable(value = "accountNumber") String accountNumber,
                                              @PathVariable(value = "accountType") String accountType) {
        AccountDTO accountDTO = accountService.getById(accountNumber, accountType);
        return new ResponseEntity<>(accountDTO, HttpStatus.OK);
    }

    @PutMapping("/edit/{accountNumber}/{accountType}")
    public ResponseEntity<DefaultResponseDTO> updateById(@PathVariable(value = "accountNumber") String accountNumber,
                                                         @PathVariable(value = "accountType") String accountType,
                                                         @RequestParam(required = false) Double balance) {
        DefaultResponseDTO response = accountService.updateById(accountNumber, accountType, balance);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete/{accountNumber}/{accountType}")
    public ResponseEntity<DefaultResponseDTO> deleteById(@PathVariable(value = "accountNumber") String accountNumber,
                                                @PathVariable(value = "accountType") String accountType) {
        DefaultResponseDTO response = accountService.deleteById(accountNumber, accountType);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

}
