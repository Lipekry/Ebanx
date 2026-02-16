package com.ebanx.demo.model.entity;

import java.math.BigDecimal;

public class Account {

    private int id;
    private BigDecimal balance;

    public Account() {}

    public Account(int id, double balance) {
        this.id = id;
        this.balance = BigDecimal.valueOf(balance);
    }

    public synchronized void deposit(int amount) throws Exception {
        if (amount <= 0) {
            throw new Exception("Valor inválido");
        }
        this.balance = this.balance.add(BigDecimal.valueOf(amount));
    }

    public synchronized void withdraw(int amount) throws Exception {
        if (amount < 0) {
            throw new Exception("Valor inválido");
        }

        if (balance.compareTo(BigDecimal.valueOf(amount)) < 0) {
            throw new Exception("Saldo insuficiente");
        }

        balance = balance.subtract(BigDecimal.valueOf(amount));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
