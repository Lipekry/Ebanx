package com.ebanx.demo.service.strategy.interfaces;

import com.ebanx.demo.model.dto.EventRequestDto;
import com.ebanx.demo.model.entity.Account;

public interface EventStrategy {
    void execute(EventRequestDto eventRequest) throws Exception;
    String getName();
}
