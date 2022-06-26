package com.ashu.practice.kafka.service;

import com.ashu.practice.kafka.common.Constants;
import com.ashu.practice.kafka.domain.User;
import com.ashu.practice.kafka.domain.UserKey;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.nio.charset.StandardCharsets;

@Service
@Slf4j
public record KafkaSender(KafkaTemplate<UserKey, User> kafkaTemplate) {

    public void send(User user) {
        UserKey key= new UserKey(user.getId());
        var  producerRecord= new ProducerRecord<>(Constants.TOPIC_USERS, key, user);
        producerRecord.headers().add("CITY",user.getCity() != null ? user.getCity().getBytes(StandardCharsets.UTF_8): "NA".getBytes(StandardCharsets.UTF_8));
        ListenableFuture<SendResult<UserKey, User>> future = kafkaTemplate.send(producerRecord);

        future.addCallback(new ListenableFutureCallback<SendResult<UserKey, User>>() {
            @Override
            public void onSuccess(SendResult<UserKey, User> result) {
                log.info("Message [{}] delivered with offset {}", user, result.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(Throwable ex) {
                log.warn("Unable to deliver message [{}]. {}", user, ex.getMessage());
            }
        });

    }

}
