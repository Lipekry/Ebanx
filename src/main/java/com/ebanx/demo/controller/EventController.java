package com.ebanx.demo.controller;

import com.ebanx.demo.model.dto.EventRequestDto;
import com.ebanx.demo.model.dto.EventResponseDto;
import com.ebanx.demo.service.EventService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventController {

    @Autowired
    EventService eventService;

    @PostMapping("/event")
    public ResponseEntity<?> event(@RequestBody EventRequestDto eventRequestDto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(eventService.process(eventRequestDto));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(0);
        }
    }

}
