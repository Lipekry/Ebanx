package com.ebanx.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventController {
    
    @PostMapping("/event")
    public ResponseEntity<?> event() {
        return ResponseEntity.ok("Event Controller");
    }

}
