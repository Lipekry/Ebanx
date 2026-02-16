package com.ebanx.demo.controller;

import com.ebanx.demo.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@RestController
public class AccountController {

    @Autowired
    AccountService accountService;

    @GetMapping("/balance")
    public ResponseEntity<BigDecimal> balance(@RequestParam int account_id) {
        try {
            return ResponseEntity.ok(this.accountService.getAccountBalance(account_id));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(BigDecimal.ZERO);
        }
    }

}
