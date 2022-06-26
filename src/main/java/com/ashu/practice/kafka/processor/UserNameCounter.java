package com.ashu.practice.kafka.processor;

import com.ashu.practice.kafka.common.Constants;
import com.ashu.practice.kafka.domain.User;
import com.ashu.practice.kafka.domain.UserKey;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

@Component
@Slf4j
public class UserNameCounter{

    @Value("${spring.kafka.properties.schema.registry.url:http://localhost:8081}")
    private String schemaRegistryUrl;

    private static final Serde<String> STRING_SERDE = Serdes.String();

    @Autowired
    public void process(StreamsBuilder streamsBuilder) {

        // When you want to override serdes explicitly/selectively
        final Map<String, String> serdeConfig = Collections.singletonMap("schema.registry.url",schemaRegistryUrl);
        // `Foo` and `Bar` are Java classes generated from Avro schemas
        final Serde<UserKey> keySpecificAvroSerde = new SpecificAvroSerde<>();
        keySpecificAvroSerde.configure(serdeConfig, true); // `true` for record keys
        final Serde<User> valueSpecificAvroSerde = new SpecificAvroSerde<>();
        valueSpecificAvroSerde.configure(serdeConfig, false); // `false` for record values

        log.info("Processing stream for user name count");
        KStream<UserKey, User> messageStream = streamsBuilder
                .stream(Constants.TOPIC_USERS, Consumed.with(keySpecificAvroSerde, valueSpecificAvroSerde));

        KTable<String, Long> wordCounts = messageStream
                .mapValues(User::getName)
                .groupBy((key, value) -> value, Grouped.with(STRING_SERDE, STRING_SERDE))
                .count(Materialized.as(Constants.STATE_STORE_USER_NAME_COUNTS));

        wordCounts.toStream().to(Constants.TOPIC_USER_NAMES_COUNT);
    }
}
