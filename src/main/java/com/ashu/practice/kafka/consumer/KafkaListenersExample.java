package com.ashu.practice.kafka.consumer;

import com.ashu.practice.kafka.common.Constants;
import com.ashu.practice.kafka.domain.User;
import com.ashu.practice.kafka.domain.UserKey;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaListenersExample {

    @KafkaListener(topics = Constants.KAFKA_TOPIC)
    private void readMessages(ConsumerRecord<UserKey, User> consumerRecord) {
        log.info("Received user={} with key={}", consumerRecord.value(), consumerRecord.key());
    }
}
