package com.neo.payment.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaDlqTopicConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Value(value = "${kafka.topics.order-events--dlq}")
    private String topicName;

    @Value(value = "${kafka.topics.partitions}")
    private int partitions;

    @Value(value = "${kafka.topics.replication.factor}")
    private int replicationFactor;

    @Bean
    public KafkaAdmin dLqKafkaAdmin()
    {
        Map<String,Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic dLqTopic()
    {
        return new NewTopic(topicName, partitions, (short) replicationFactor);
    }

}
