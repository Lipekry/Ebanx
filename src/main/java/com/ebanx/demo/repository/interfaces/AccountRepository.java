package com.ebanx.demo.repository.interfaces;

import com.ebanx.demo.model.entity.Account;

public interface AccountRepository {
    Account getAccountById(int id);
    void save(Account account) throws Exception;
    void update(Account account) throws Exception;
}
