package com.ashu.practice.kafka.web;

import com.ashu.practice.kafka.domain.User;
import com.ashu.practice.kafka.service.KafkaSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@Slf4j
public class UserController {

    @Autowired
    private KafkaSender kafkaSender;

    @PostMapping
    public void publishMessage(@RequestBody User user) {
        log.info("user={}", user);
        kafkaSender.send(user);
        log.info("Message sent to the Kafka Topic Successfully");
    }
}
