package com.tmt.challenge.controller;

import com.tmt.challenge.service.kafka.KafkaSenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/kafka")
public class KafkaController {

    private static final Logger logger = LoggerFactory.getLogger(KafkaController.class);
    private final KafkaSenderService kafkaSenderService;

    @Autowired
    public KafkaController(KafkaSenderService kafkaSenderService) {
        this.kafkaSenderService = kafkaSenderService;
    }

    @PostMapping("/publish")
    public ResponseEntity<Object> sendMessage(@RequestParam("message") String message) {
        kafkaSenderService.send(message);
        Map<String, String> response = new HashMap<>();
        response.put("message", message);
        logger.info(String.format("*************** Published successfully with message -> %s", message));
        return ResponseEntity.ok().body(response);
    }
}
