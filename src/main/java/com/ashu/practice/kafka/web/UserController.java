package com.ashu.practice.kafka.web;

import com.ashu.practice.kafka.domain.User;
import com.ashu.practice.kafka.service.KafkaSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController{

    private final KafkaSender kafkaSender;
    @PostMapping
    @Async
    public void publishMessage() {
        for (int i = 1; i < 100; i++) {
            User user = new User();
            user.setId(1000 + i);
            user.setName("user-" + i);
            user.setAge(20 + i);
            user.setCity("city-"+i);
            log.info("user={}", user);
            kafkaSender.send(user);
            log.info("Message sent to the Kafka Topic Successfully");
            if (i % 10 == 0) {
                log.info("Sleeping for 10 seconds");
                try {
                    Thread.sleep(10 * 1000L);
                } catch (InterruptedException e) {
                    log.error("Error occurred", e);
                }
            }
        }
    }
}
