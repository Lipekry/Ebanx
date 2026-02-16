package com.ebanx.demo.controller;

import com.ebanx.demo.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApplicationController {

    @Autowired
    ApplicationService applicationService;

    @GetMapping
    public String hello() {
        return "Hello Ebanx";
    }

    @PostMapping("reset")
    public ResponseEntity<?> resetDataBase() {
        return ResponseEntity.ok("Application Controller");
    }

}
