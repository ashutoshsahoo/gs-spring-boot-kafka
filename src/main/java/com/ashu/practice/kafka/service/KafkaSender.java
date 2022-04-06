package com.ashu.practice.kafka.service;

import com.ashu.practice.kafka.common.Constants;
import com.ashu.practice.kafka.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
@Slf4j
public record KafkaSender(KafkaTemplate<String, User> kafkaTemplate) {

    public void send(User user) {
        ListenableFuture<SendResult<String, User>> future = kafkaTemplate.send(Constants.KAFKA_TOPIC,user.getId(), user);

        future.addCallback(new ListenableFutureCallback<SendResult<String, User>>() {
            @Override
            public void onSuccess(SendResult<String, User> result) {
                log.info("Message [{}] delivered with offset {}", user, result.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(Throwable ex) {
                log.warn("Unable to deliver message [{}]. {}", user, ex.getMessage());
            }
        });

    }

}
