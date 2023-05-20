package com.tmt.challenge.service.kafka;

import com.tmt.challenge.constant.KafkaConstant;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class KafkaSenderService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaSenderService.class);
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaSenderService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(Object message) {
        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(KafkaConstant.USER, message);
        future.addCallback(new ListenableFutureCallback<>() {

            @Override
            public void onFailure(@NotNull Throwable ex) {
                logger.error("unable to send message=[" + message + "] due to : " + ex.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, Object> result) {
                logger.info("Sent message=[" + message + "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }
        });
    }
}
