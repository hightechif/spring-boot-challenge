package com.tmt.challenge.mapper;

import com.tmt.challenge.dto.AccountDTO;
import com.tmt.challenge.model.Account;
import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountDTO toAccountDTO(Account account);
    List<AccountDTO> toAccountDTO(Collection<Account> accounts);
    Account toAccountEntity(AccountDTO accountDTO);
    List<Account> toAccountEntity(Collection<AccountDTO> accountDTOs);

}
