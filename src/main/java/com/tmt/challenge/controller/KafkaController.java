package com.tmt.challenge.controller;

import com.tmt.challenge.service.kafka.KafkaSenderService;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
public class KafkaController {

    private final KafkaSenderService kafkaSenderService;

    @Autowired
    public KafkaController(KafkaSenderService kafkaSenderService) {
        this.kafkaSenderService = kafkaSenderService;
    }

    @PostMapping("/publish")
    public String sendMessage(@RequestParam("message") String message) {
        kafkaSenderService.send(message);
        return "Published successfully";
    }

    @Bean
    public NewTopic adviceTopic() {
        return new NewTopic("user", 3, (short)1);
    }
}
