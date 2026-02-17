package com.ebanx.demo.service;

import com.ebanx.demo.model.entity.Account;
import com.ebanx.demo.repository.interfaces.AccountRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountService {

    private AccountRepository accountRepository;

    public AccountService(@Qualifier("inMemory") AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public BigDecimal getAccountBalance(int accountId) throws Exception {
        Account account = this.accountRepository.getAccountById(accountId);

        if (account == null) {
            throw new Exception("Account not found");
        }

        return account.getBalance();
    }

}
