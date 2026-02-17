package com.ebanx.demo.service.strategy.implementations;

import com.ebanx.demo.model.dto.EventRequestDto;
import com.ebanx.demo.model.entity.Account;
import com.ebanx.demo.repository.interfaces.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes da estratégia de depósito")
class EventDepositStrategyTest {

    @Mock
    private AccountRepository repository;

    @InjectMocks
    private EventDepositStrategy strategy;

    private EventRequestDto request;

    @BeforeEach
    void setup() {
        request = new EventRequestDto();
        request.setDestination(100);
        request.setAmount(new BigDecimal("10"));
    }

    @Test
    @DisplayName("Deve criar uma nova conta quando ela não existir")
    void shouldCreateNewAccountWhenNotExists() throws Exception {
        when(repository.getAccountById(100)).thenReturn(null);

        strategy.execute(request);

        ArgumentCaptor<Account> accountCaptor = ArgumentCaptor.forClass(Account.class);
        verify(repository).save(accountCaptor.capture());

        Account savedAccount = accountCaptor.getValue();

        assertEquals(100, savedAccount.getId());
        assertEquals(new BigDecimal("10"), savedAccount.getBalance());
    }

    @Test
    @DisplayName("Deve depositar em conta existente e atualizar o repositório")
    void shouldDepositIntoExistingAccount() throws Exception {
        Account existingAccount = new Account(100, new BigDecimal("20"));

        when(repository.getAccountById(100)).thenReturn(existingAccount);

        strategy.execute(request);

        assertEquals(new BigDecimal("30"), existingAccount.getBalance());

        verify(repository).update(existingAccount);
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando a conta de destino for nula")
    void shouldThrowExceptionWhenDestinationIsNull() throws Exception {
        request.setDestination(null);

        Exception exception = assertThrows(Exception.class, () -> strategy.execute(request));

        assertEquals("A conta de destino não foi recebida", exception.getMessage());

        verify(repository, never()).save(any());
        verify(repository, never()).update(any());
    }
}
