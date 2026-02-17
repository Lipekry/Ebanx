package com.ebanx.demo.service;

import com.ebanx.demo.model.dto.AccountDto;
import com.ebanx.demo.model.dto.EventRequestDto;
import com.ebanx.demo.model.dto.EventResponseDto;
import com.ebanx.demo.model.entity.Account;
import com.ebanx.demo.repository.implementations.AccountRepositoryInMemory;
import com.ebanx.demo.repository.interfaces.AccountRepository;
import com.ebanx.demo.service.factory.EventStrategyFactory;
import com.ebanx.demo.service.strategy.interfaces.EventStrategy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    private AccountRepository accountRepository;
    private EventStrategyFactory factory;

    public EventService(@Qualifier("inMemory") AccountRepository accountRepository, EventStrategyFactory factory) {
        this.accountRepository = accountRepository;
        this.factory = factory;
    }

    private EventResponseDto reponseHandler(EventRequestDto eventRequestDto) {
        AccountDto accountOrigin = null;
        AccountDto accountDestination = null;

        if (eventRequestDto.getOrigin() != null && eventRequestDto.getOrigin() > 0) {
            Account origin = this.accountRepository.getAccountById(eventRequestDto.getOrigin());
            accountOrigin = new AccountDto();
            accountOrigin.assign(origin);
        }

        if (eventRequestDto.getDestination() != null && eventRequestDto.getDestination() > 0) {
            Account destination = this.accountRepository.getAccountById(eventRequestDto.getDestination());
            accountDestination = new AccountDto();
            accountDestination.assign(destination);
        }

        return new EventResponseDto(accountOrigin, accountDestination);
    }

    public EventResponseDto process(EventRequestDto request) throws Exception {
        EventStrategy strategy =  this.factory.getStrategy(request.getType());
        strategy.execute(request);
        return reponseHandler(request);
    }

}
