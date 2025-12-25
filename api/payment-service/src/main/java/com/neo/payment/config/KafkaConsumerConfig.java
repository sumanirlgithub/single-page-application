package com.neo.payment.config;

import com.fasterxml.jackson.databind.JsonDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Flow -
 * Spring creates a KafkaListenerContainerFactory with ConsumerFactory & related properties as defined below
 * Consumer/Listener class with an annotation @KafkaListener uses this factory
 * Spring does the magic: Creates a KafkaConsumer, Applies all properties from ConsumerFactory, Overrides group.id from annotation
 */
@Configuration
public class KafkaConsumerConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Bean
    public ConsumerFactory<String,String> consumerFactory()
    {
        Map<String,Object> props = new HashMap<>();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        //props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

        props.put(ConsumerConfig.RETRY_BACKOFF_MS_CONFIG, 1000); // when broker is down, consumer waits 1 second before first retry

        /**
         * Heartbeat thread retries with exponential backoff (1s → 10s cap). meaning 2nd retry after 2 seconds, 3rd retry after 4 seconds
         * 4th retry after 8 seconds, 5th and so on retry after 10 seconds until broker is back.
         */
        props.put(ConsumerConfig.RECONNECT_BACKOFF_MS_CONFIG, 1000); // 1 second
        props.put(ConsumerConfig.RECONNECT_BACKOFF_MAX_MS_CONFIG, 10000); // 10 seconds

        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 30000); // after 30 seconds consumer is marked dead. only applies when broker is alive.

        /**
         * Please note that when a consumer processes messages and commits offsets in code, then
         * Kafka resumes from last committed offset and value set in AUTO_OFFSET_RESET_CONFIG is ignored.
         * earliest	- Read from beginning of topic
         * latest	 - Read only new messages
         */
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String,String> kafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.getContainerProperties()
                .setAckMode(ContainerProperties.AckMode.MANUAL);
        return factory;
    }
}
