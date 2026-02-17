package com.ebanx.demo.repository.implementations;

import com.ebanx.demo.model.entity.Account;
import com.ebanx.demo.repository.interfaces.AccountRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository("inMemory")
public class AccountRepositoryInMemory implements AccountRepository {

    private final Map<Integer, Account> accounts = new ConcurrentHashMap<>();

    @Override
    public Account getAccountById(int id) {
        return accounts.get(id);
    }

    @Override
    public void save(Account account) throws Exception {

        if (account == null) {
            throw new Exception("Account não pode ser null");
        }

        if (accounts.containsKey(account.getId())) {
            throw new Exception("Conta já existe");
        }

        accounts.put(account.getId(), account);
    }

    @Override
    public void update(Account account) throws Exception {

        if (account == null) {
            throw new Exception("Account não pode ser null");
        }

        if (this.getAccountById(account.getId()) == null) {
            throw new Exception("Conta não encontrada");
        }

        accounts.put(account.getId(), account);
    }

    public void clear() {
        accounts.clear();
    }

}
