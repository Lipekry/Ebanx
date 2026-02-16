package com.ebanx.demo.repository.interfaces;

import com.ebanx.demo.model.entity.Account;

import java.util.List;

public interface AccountRepository {
    public Account getAccountById(int id);
    public List<Account> getAllAccounts();
    public void save(Account account);
    public void update(Account account);
}
