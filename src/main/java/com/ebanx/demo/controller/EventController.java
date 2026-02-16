package com.ebanx.demo.controller;

import com.ebanx.demo.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventController {

    @Autowired
    EventService eventService;

    @PostMapping("/event")
    public ResponseEntity<?> event() {
        return ResponseEntity.ok("Event Controller");
    }

}
