package com.ebanx.demo.repository.implementations;

import com.ebanx.demo.model.entity.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AccountRepositoryInMemoryTest {

    private AccountRepositoryInMemory repository;

    @BeforeEach
    void setUp() {
        repository = new AccountRepositoryInMemory();
    }

    @Test
    void shouldSaveAccount() throws Exception {
        Account account = new Account(1, new BigDecimal("100"));

        repository.save(account);

        Account found = repository.getAccountById(1);

        assertNotNull(found);
        assertEquals(new BigDecimal("100"), found.getBalance());
    }

    @Test
    void shouldNotAllowDuplicateSave() throws Exception {
        Account account = new Account(1, new BigDecimal("100"));

        repository.save(account);

        Exception exception = assertThrows(
                Exception.class,
                () -> repository.save(account)
        );

        assertEquals("Conta já existe", exception.getMessage());
    }

    @Test
    void shouldReturnNullWhenAccountNotFound() {
        Account result = repository.getAccountById(99);

        assertNull(result);
    }

    @Test
    void shouldUpdateExistingAccount() throws Exception {
        Account account = new Account(1, new BigDecimal("100"));
        repository.save(account);

        account.setBalance(new BigDecimal("300"));
        repository.update(account);

        Account updated = repository.getAccountById(1);

        assertEquals(new BigDecimal("300"), updated.getBalance());
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistingAccount() {
        Account account = new Account(1, new BigDecimal("100"));

        Exception exception = assertThrows(
                Exception.class,
                () -> repository.update(account)
        );

        assertEquals("Conta não encontrada", exception.getMessage());
    }

}