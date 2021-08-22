package com.tmt.challenge.service.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumerClass {

    private static final Logger logger = LoggerFactory.getLogger(ProducerClass.class);

    @KafkaListener(topics = "user")
    public void consume(String message) {
        logger.info(String.format("*************** Consumer just receive the message -> %s", message));
    }
}
