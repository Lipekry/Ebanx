package com.ebanx.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    @GetMapping("/balance")
    public ResponseEntity<Integer> balance() {
        return ResponseEntity.ok(0);
    }

}
