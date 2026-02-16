package com.ebanx.demo.controller;

import com.ebanx.demo.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    @Autowired
    AccountService accountService;

    @GetMapping("/balance")
    public ResponseEntity<Integer> balance() {
        return ResponseEntity.ok(0);
    }

}
