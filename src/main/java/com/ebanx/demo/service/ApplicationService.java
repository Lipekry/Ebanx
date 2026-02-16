package com.ebanx.demo.service;

import com.ebanx.demo.repository.interfaces.AccountRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ApplicationService {

    private AccountRepository accountRepository;

    public ApplicationService(@Qualifier("inMemory") AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void reset() {
        this.accountRepository.clear();
    }

}
