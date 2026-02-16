package com.ebanx.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApplicationController {

    @GetMapping
    public String hello() {
        return "Hello Ebanx";
    }

    @PostMapping("reset")
    public ResponseEntity<?> resetDataBase() {
        return ResponseEntity.ok("Application Controller");
    }

}
