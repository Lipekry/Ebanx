package com.ebanx.demo.model.entity;

import java.math.BigDecimal;

public class Account {

    private int id;
    private BigDecimal balance;

    public Account() {}

    public Account(int id, BigDecimal balance) {
        this.id = id;
        this.balance = balance;
    }

    public synchronized void deposit(BigDecimal amount) throws Exception {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor inválido");
        }

        this.balance = this.balance.add(amount);
    }

    public synchronized void withdraw(BigDecimal amount) throws Exception {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Valor inválido");
        }

        if (balance.compareTo(amount) < 0) {
            throw new Exception("Saldo insuficiente");
        }

        balance = balance.subtract(amount);
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
