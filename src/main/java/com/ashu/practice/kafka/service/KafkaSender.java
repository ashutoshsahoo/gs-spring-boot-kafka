package com.ashu.practice.kafka.service;

import com.ashu.practice.kafka.common.Constants;
import com.ashu.practice.kafka.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public record KafkaSender(KafkaTemplate<Integer, User> kafkaTemplate) {

    public void send(User user) {
        CompletableFuture<SendResult<Integer, User>> future = kafkaTemplate.send(Constants.TOPIC_USERS, user.getId(), user);
        future.whenCompleteAsync((value, ex) -> {
            if (value != null) {
                log.info("Message {} delivered with offset {}", user, value.getRecordMetadata().offset());
            }
            if (ex != null) {
                log.warn("Unable to deliver message {}. Reason: {}", user, ex.getMessage());
            }
        });
    }
}
