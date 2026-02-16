package com.ebanx.demo.model.entity;

public class Account {

    private int id;
    private double balance;

    public Account() {}

    public Account(int id, double balance) {
        this.id = id;
        this.balance = balance;
    }

    public void deposit(int amount) throws Exception {
        if (amount <= 0) {
            throw new Exception("Valor inválido");
        }
        this.balance += amount;
    }

    public void withdraw(int amount) throws Exception {
        if (amount <= 0) {
            throw new Exception("Valor inválido");
        }

        if (this.balance < amount) {
            throw new Exception("Saldo insuficiente");
        }

        this.balance -= amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
