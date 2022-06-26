package com.ashu.practice.kafka;

import com.ashu.practice.kafka.common.Constants;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.config.TopicBuilder;

@SpringBootApplication
@EnableKafka
@EnableKafkaStreams
public class GsSpringBootKafkaApplication {

    public static void main(String[] args) {
        SpringApplication.run(GsSpringBootKafkaApplication.class, args);
    }

    @Bean
    public NewTopic topicUsers() {
        return TopicBuilder.name(Constants.TOPIC_USERS).partitions(6).replicas(3).build();
    }

    @Bean
    public NewTopic topicUserNamesCount() {
        return TopicBuilder.name(Constants.TOPIC_USERS).partitions(6).replicas(3).build();
    }

    @Bean
    public NewTopic topicUserUpperCase() {
        return TopicBuilder.name(Constants.TOPIC_USER_UPPER_CASE).partitions(6).replicas(3).build();
    }
}
