package com.ashu.practice.kafka;

import com.ashu.practice.kafka.common.Constants;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;

@SpringBootApplication
public class GsSpringBootKafkaApplication {

    public static void main(String[] args) {
        SpringApplication.run(GsSpringBootKafkaApplication.class, args);
    }

    @Bean
    public NewTopic topicUsers() {
        return TopicBuilder
                .name(Constants.TOPIC_USERS)
//                .partitions(3)
//                .replicas(3)
                .build();
    }
}
