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
@DisplayName("Testes da estratégia de transferência")
class EventTransferStrategyTest {

    @Mock
    private AccountRepository repository;

    @InjectMocks
    private EventTransferStrategy strategy;

    private EventRequestDto request;

    @BeforeEach
    void setup() {
        request = new EventRequestDto();
        request.setOrigin(100);
        request.setDestination(200);
        request.setAmount(new BigDecimal("10"));
    }

    @Test
    @DisplayName("Deve transferir valor entre contas existentes")
    void shouldTransferBetweenExistingAccounts() throws Exception {
        Account origin = new Account(100, new BigDecimal("50"));
        Account destination = new Account(200, new BigDecimal("20"));

        when(repository.getAccountById(100)).thenReturn(origin);
        when(repository.getAccountById(200)).thenReturn(destination);

        strategy.execute(request);

        assertEquals(new BigDecimal("40"), origin.getBalance());
        assertEquals(new BigDecimal("30"), destination.getBalance());

        verify(repository).update(origin);
        verify(repository).update(destination);
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Deve criar conta de destino quando ela não existir")
    void shouldCreateDestinationAccountWhenNotExists() throws Exception {
        Account origin = new Account(100, new BigDecimal("50"));

        when(repository.getAccountById(100)).thenReturn(origin);
        when(repository.getAccountById(200)).thenReturn(null);

        strategy.execute(request);

        assertEquals(new BigDecimal("40"), origin.getBalance());

        ArgumentCaptor<Account> captor = ArgumentCaptor.forClass(Account.class);
        verify(repository).save(captor.capture());

        Account savedDestination = captor.getValue();

        assertEquals(200, savedDestination.getId());
        assertEquals(new BigDecimal("10"), savedDestination.getBalance());

        verify(repository).update(origin);
    }

    @Test
    @DisplayName("Deve lançar exceção quando a conta de origem for nula")
    void shouldThrowExceptionWhenOriginIsNull() throws Exception {
        request.setOrigin(null);

        Exception exception = assertThrows(Exception.class, () -> strategy.execute(request));

        assertEquals("A conta de origem não foi recebida", exception.getMessage());

        verify(repository, never()).save(any());
        verify(repository, never()).update(any());
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

    @Test
    @DisplayName("Deve lançar exceção quando a conta de origem não existir")
    void shouldThrowExceptionWhenOriginAccountNotFound() throws Exception {
        when(repository.getAccountById(100)).thenReturn(null);

        Exception exception = assertThrows(Exception.class, () -> strategy.execute(request));

        assertEquals("Conta de origem não encontrada", exception.getMessage());

        verify(repository, never()).save(any());
        verify(repository, never()).update(any());
    }
}
