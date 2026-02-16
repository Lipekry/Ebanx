package com.ebanx.demo.model.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    @Test
    @DisplayName("Depósito ocorre com sucesso")
    void shouldDepositSuccessfully() throws Exception {
        Account account = new Account(1, BigDecimal.valueOf(100));

        account.deposit(BigDecimal.valueOf(50));

        assertEquals(
                new BigDecimal("150"),
                account.getBalance()
        );
    }

    @Test
    @DisplayName("Saque ocorre com sucesso")
    void shouldWithdrawSuccessfully() throws Exception {
        Account account = new Account(1, BigDecimal.valueOf(100));

        account.withdraw(BigDecimal.valueOf(40));

        assertEquals(
                new BigDecimal("60"),
                account.getBalance()
        );
    }

    @Test
    @DisplayName("Lança excessão quando valor de depósito é inválido")
    void shouldThrowExceptionWhenDepositIsInvalid() {
        Account account = new Account(1, BigDecimal.valueOf(100));

        Exception exception = assertThrows(
                Exception.class,
                () -> account.deposit(BigDecimal.valueOf(0))
        );

        assertEquals("Valor inválido", exception.getMessage());
    }

    @Test
    @DisplayName("Lança excessão quando valor do saque é negativo")
    void shouldThrowExceptionWhenWithdrawIsNegative() {
        Account account = new Account(1, BigDecimal.valueOf(100));

        Exception exception = assertThrows(
                Exception.class,
                () -> account.withdraw(BigDecimal.valueOf(-10))
        );

        assertEquals("Valor inválido", exception.getMessage());
    }

    @Test
    @DisplayName("Lança Excessão quando saldo é insuficiente")
    void shouldThrowExceptionWhenInsufficientBalance() {
        Account account = new Account(1, BigDecimal.valueOf(100));

        Exception exception = assertThrows(
                Exception.class,
                () -> account.withdraw(BigDecimal.valueOf(200))
        );

        assertEquals("Saldo insuficiente", exception.getMessage());
    }
}
