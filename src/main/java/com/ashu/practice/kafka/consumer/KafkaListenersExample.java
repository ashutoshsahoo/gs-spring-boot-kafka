package com.ashu.practice.kafka.consumer;

import com.ashu.practice.kafka.common.Constants;
import com.ashu.practice.kafka.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.List;

@Component
@Slf4j
public class KafkaListenersExample {

    @KafkaListener(topics = Constants.TOPIC_USERS, batch = "true")
    private void readMessages(List<ConsumerRecord<Integer, User>> consumerRecords) {
        var random = new SecureRandom();
        var batchNo = random.nextInt() & Integer.MAX_VALUE;
        consumerRecords.forEach(consumerRecord -> log.info("Received user={} with key={}, batchNo={}",
                consumerRecord.value(), consumerRecord.key(), batchNo));
    }
}
