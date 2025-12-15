package com.neo.api.order.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Value(value = "${kafka.topics.order}")
    private String topicName;

    @Value(value = "${kafka.topics.partitions}")
    private int partitions;

    @Value(value = "${kafka.topics.replication.factor}")
    private int replicationFactor;

    @Bean
    public KafkaAdmin kafkaAdmin()
    {
        Map<String,Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic topic()
    {
        return new NewTopic(topicName, partitions, (short) replicationFactor);
    }

}
