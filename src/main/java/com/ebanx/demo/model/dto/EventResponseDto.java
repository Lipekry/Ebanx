package com.ebanx.demo.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EventResponseDto {
    AccountDto origin;

    public EventResponseDto(AccountDto origin, AccountDto destination) {
        this.origin = origin;
        this.destination = destination;
    }

    AccountDto destination;

    public AccountDto getOrigin() {
        return origin;
    }

    public void setOrigin(AccountDto origin) {
        this.origin = origin;
    }

    public AccountDto getDestination() {
        return destination;
    }

    public void setDestination(AccountDto destination) {
        this.destination = destination;
    }
}
