package com.ebanx.demo.service.strategy.implementations;

import com.ebanx.demo.model.dto.EventRequestDto;
import com.ebanx.demo.model.entity.Account;
import com.ebanx.demo.repository.interfaces.AccountRepository;
import com.ebanx.demo.service.strategy.interfaces.EventStrategy;
import org.springframework.stereotype.Component;

@Component
public class EventWithdrawStrategy implements EventStrategy {

    private final AccountRepository repository;

    public EventWithdrawStrategy(AccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute(EventRequestDto request) throws Exception {
        if (request.getOrigin() == null) {
            throw new Exception("A conta de origem não foi recebida");
        }

        Account account = repository.getAccountById(request.getOrigin());

        if (account == null) {
            throw new Exception("Conta não encontrada");
        }

        account.withdraw(request.getAmount());
        repository.update(account);
    }

    @Override
    public String getName() {
        return "withdraw";
    }

}
