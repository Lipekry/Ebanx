package com.ebanx.demo.service;

import com.ebanx.demo.model.dto.AccountDto;
import com.ebanx.demo.model.dto.EventRequestDto;
import com.ebanx.demo.model.dto.EventResponseDto;
import com.ebanx.demo.model.entity.Account;
import com.ebanx.demo.repository.interfaces.AccountRepository;
import com.ebanx.demo.service.factory.EventStrategyFactory;
import com.ebanx.demo.service.strategy.interfaces.EventStrategy;
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
@DisplayName("Testes do EventService")
class EventServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private EventStrategyFactory factory;

    @Mock
    private EventStrategy strategy;

    @InjectMocks
    private EventService service;

    private EventRequestDto request;

    @BeforeEach
    void setup() {
        request = new EventRequestDto();
        request.setType("deposit");
        request.setDestination(100);
        request.setAmount(new BigDecimal("10"));
    }

    @Test
    @DisplayName("Deve executar a strategy correta e retornar destino preenchido")
    void shouldExecuteStrategyAndReturnDestination() throws Exception {
        Account account = new Account(100, new BigDecimal("10"));

        when(factory.getStrategy("deposit")).thenReturn(strategy);
        when(accountRepository.getAccountById(100)).thenReturn(account);

        EventResponseDto response = service.process(request);

        verify(factory).getStrategy("deposit");
        verify(strategy).execute(request);

        assertNotNull(response);
        assertNull(response.getOrigin());
        assertNotNull(response.getDestination());
        assertEquals("100", response.getDestination().getId());
        assertEquals(new BigDecimal("10"), response.getDestination().getBalance());
    }

    @Test
    @DisplayName("Deve retornar origem e destino quando ambos existirem")
    void shouldReturnOriginAndDestination() throws Exception {
        request.setOrigin(50);

        Account origin = new Account(50, new BigDecimal("40"));
        Account destination = new Account(100, new BigDecimal("60"));

        when(factory.getStrategy("deposit")).thenReturn(strategy);
        when(accountRepository.getAccountById(50)).thenReturn(origin);
        when(accountRepository.getAccountById(100)).thenReturn(destination);

        EventResponseDto response = service.process(request);

        verify(strategy).execute(request);

        assertNotNull(response.getOrigin());
        assertNotNull(response.getDestination());
        assertEquals("50", response.getOrigin().getId());
        assertEquals(new BigDecimal("40"), response.getOrigin().getBalance());
        assertEquals("100", response.getDestination().getId());
        assertEquals(new BigDecimal("60"), response.getDestination().getBalance());
    }

    @Test
    @DisplayName("Deve propagar exceção quando a factory não encontrar strategy")
    void shouldPropagateExceptionWhenStrategyNotFound() throws Exception {
        when(factory.getStrategy("deposit")).thenThrow(new IllegalArgumentException("Strategy não encontrada"));

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> service.process(request));

        assertEquals("Strategy não encontrada", exception.getMessage());

        verify(strategy, never()).execute(any());
    }
}
