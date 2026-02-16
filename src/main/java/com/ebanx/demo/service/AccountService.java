package com.ebanx.demo.service;

import com.ebanx.demo.repository.interfaces.AccountRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private AccountRepository accountRepository;

    public AccountService(@Qualifier("inMemory") AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

}
