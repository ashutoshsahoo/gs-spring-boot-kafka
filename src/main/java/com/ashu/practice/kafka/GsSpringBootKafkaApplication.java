package com.ashu.practice.kafka;

import com.ashu.practice.kafka.common.Constants;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class GsSpringBootKafkaApplication {

    public static void main(String[] args) {
        SpringApplication.run(GsSpringBootKafkaApplication.class, args);
    }

    @Bean
    public NewTopic topicUsers() {
        return TopicBuilder.name(Constants.TOPIC_USERS).build();
    }
}
