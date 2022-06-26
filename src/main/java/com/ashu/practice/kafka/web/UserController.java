package com.ashu.practice.kafka.web;

import com.ashu.practice.kafka.common.Constants;
import com.ashu.practice.kafka.domain.User;
import com.ashu.practice.kafka.service.KafkaSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final StreamsBuilderFactoryBean factoryBean;
    @Autowired
    private final KafkaSender kafkaSender;

    @PostMapping
    public String publishMessage(@RequestBody User user) {
        log.info("user={}",user);
        kafkaSender.send(user);
        return "Message sent to the Kafka Topic Successfully";
    }

    @GetMapping("/count/{userName}")
    public Long countUserNames(@PathVariable String userName) {
        KafkaStreams kafkaStreams = factoryBean.getKafkaStreams();
        ReadOnlyKeyValueStore<String, Long> counts = kafkaStreams.store(
                StoreQueryParameters.fromNameAndType(Constants.STATE_STORE_USER_NAME_COUNTS, QueryableStoreTypes.keyValueStore()));
        return counts.get(userName);
    }


}
