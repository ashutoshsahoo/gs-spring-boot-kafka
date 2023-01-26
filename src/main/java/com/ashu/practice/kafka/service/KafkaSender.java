package com.ashu.practice.kafka.service;

import com.ashu.practice.kafka.common.Constants;
import com.ashu.practice.kafka.domain.User;
import com.ashu.practice.kafka.domain.UserKey;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public record KafkaSender(KafkaTemplate<UserKey, User> kafkaTemplate) {

    public void send(User user) {
        UserKey key= new UserKey(user.getId());
        var  producerRecord= new ProducerRecord<>(Constants.TOPIC_USERS, key, user);
        producerRecord.headers().add("CITY",user.getCity() != null ?
                user.getCity().getBytes(StandardCharsets.UTF_8):
                "NA".getBytes(StandardCharsets.UTF_8));
        CompletableFuture<SendResult<UserKey, User>> future = kafkaTemplate.send(producerRecord);
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
