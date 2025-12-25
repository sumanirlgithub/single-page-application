package com.neo.payment.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;
@Configuration
public class KafkaProducerConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Value("${spring.kafka.properties.schema.registry.url}")
    private String registry;


    @Bean
    public ProducerFactory<String, Object> dLqProducerFactory()
    {
        Map<String,Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        // Enable retries
        configProps.put(ProducerConfig.RETRIES_CONFIG, 3); // Retry up to 3 times
        configProps.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, 500); // Wait 500ms between retries
        configProps.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 2000); // 2 seconds timeout for each request
        configProps.put(ProducerConfig.ACKS_CONFIG, "all"); //  ensures that the Producer only receives acknowledgement of a successful message
                                                            // write once all the current in-sync replicas have received the message.
        configProps.put("enable.idempotence", true); // it will avoid sending duplicate message

        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        //configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, io.confluent.kafka.serializers.KafkaAvroSerializer.class);
        //configProps.put(KafkaAvroSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG, registry);
        return new DefaultKafkaProducerFactory<>(configProps);
    }
    @Bean
    public KafkaTemplate<String, Object> dLqKafkaTemplate(){
        return new KafkaTemplate<>(this.dLqProducerFactory());
    }
}
