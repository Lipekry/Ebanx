package com.ebanx.demo.service.strategy.implementations;

import com.ebanx.demo.model.dto.EventRequestDto;
import com.ebanx.demo.model.entity.Account;
import com.ebanx.demo.repository.interfaces.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes da estratégia de saque")
class EventWithdrawStrategyTest {

    @Mock
    private AccountRepository repository;

    @InjectMocks
    private EventWithdrawStrategy strategy;

    private EventRequestDto request;

    @BeforeEach
    void setup() {
        request = new EventRequestDto();
        request.setOrigin(100);
        request.setAmount(new BigDecimal("10"));
    }

    @Test
    @DisplayName("Deve realizar saque quando a conta existir")
    void shouldWithdrawWhenAccountExists() throws Exception {
        Account account = new Account(100, new BigDecimal("50"));

        when(repository.getAccountById(100)).thenReturn(account);

        strategy.execute(request);

        assertEquals(new BigDecimal("40"), account.getBalance());
        verify(repository).update(account);
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando a conta de origem for nula")
    void shouldThrowExceptionWhenOriginIsNull() throws Exception {
        request.setOrigin(null);

        Exception exception = assertThrows(Exception.class, () -> strategy.execute(request));

        assertEquals("A conta de origem não foi recebida", exception.getMessage());

        verify(repository, never()).update(any());
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando a conta não for encontrada")
    void shouldThrowExceptionWhenAccountNotFound() throws Exception {
        when(repository.getAccountById(100)).thenReturn(null);

        Exception exception = assertThrows(Exception.class, () -> strategy.execute(request));

        assertEquals("Conta não encontrada", exception.getMessage());

        verify(repository, never()).update(any());
        verify(repository, never()).save(any());
    }
}
