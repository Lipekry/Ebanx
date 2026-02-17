package com.ebanx.demo.service.strategy.implementations;

import com.ebanx.demo.model.dto.EventRequestDto;
import com.ebanx.demo.model.entity.Account;
import com.ebanx.demo.repository.interfaces.AccountRepository;
import com.ebanx.demo.service.strategy.interfaces.EventStrategy;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class EventDepositStrategy implements EventStrategy {

    private final AccountRepository repository;

    public EventDepositStrategy(AccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public String getName() {
        return "deposit";
    }

    @Override
    public void execute(EventRequestDto request) throws Exception {
        if (request.getDestination() == null) {
            throw new Exception("A conta de destino não foi recebida");
        }

        Account account = repository.getAccountById(request.getDestination());

        if (account == null) {
            account = new Account(request.getDestination(), BigDecimal.ZERO);
            account.deposit(request.getAmount());
            repository.save(account);
            return;
        }

        account.deposit(request.getAmount());
        repository.update(account);
    }

}
