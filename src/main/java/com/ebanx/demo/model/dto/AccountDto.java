package com.ebanx.demo.model.dto;

import com.ebanx.demo.model.entity.Account;

import java.math.BigDecimal;

public class AccountDto {
    public String id;
    public BigDecimal balance;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void assign(Account account) {
        this.id = String.valueOf(account.getId());
        this.balance = account.getBalance();
    }
}
