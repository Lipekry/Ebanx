package com.ebanx.demo.service.strategy.implementations;

import com.ebanx.demo.model.dto.EventRequestDto;
import com.ebanx.demo.model.entity.Account;
import com.ebanx.demo.repository.interfaces.AccountRepository;
import com.ebanx.demo.service.strategy.interfaces.EventStrategy;
import org.springframework.stereotype.Component;

@Component
public class EventTransferStrategy implements EventStrategy {

    private final AccountRepository repository;

    public EventTransferStrategy(AccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute(EventRequestDto request) throws Exception {
        if (request.getOrigin() == null) {
            throw new Exception("A conta de origem não foi recebida");
        }

        if (request.getDestination() == null) {
            throw new Exception("A conta de destino não foi recebida");
        }

        Account accountOrigin = repository.getAccountById(request.getOrigin());
        if (accountOrigin == null) {
            throw new Exception("Conta de origem não encontrada");
        }
        accountOrigin.withdraw(request.getAmount());
        repository.update(accountOrigin);

        Account accountDestination = repository.getAccountById(request.getDestination());
        if (accountDestination == null) {
            accountDestination = new Account(request.getDestination());
            accountDestination.deposit(request.getAmount());
            repository.save(accountDestination);
            return;
        }

        accountDestination.deposit(request.getAmount());
        repository.update(accountDestination);
    }

    @Override
    public String getName() {
        return "transfer";
    }

}
