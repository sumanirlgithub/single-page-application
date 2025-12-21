package com.neo.api.order.config;

import io.confluent.kafka.serializers.KafkaAvroSerializerConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
public class KafkaProducerConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Value("${spring.kafka.registry.url}")
    private String registry;

    @Value("${spring.kafka.producer.acks}")
    private String acks;

    @Value("${spring.kafka.producer.properties.enable.idempotence}")
    private String idempotence;

    @Value("${spring.kafka.producer.transaction-id-prefix}")
    private String transactionIdPrefix;


    @Bean
    public ProducerFactory<String, Object> producerFactory()
    {
        Map<String,Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configProps.put(ProducerConfig.CLIENT_ID_CONFIG, "producer-id-place-order"); //a randomly generated UUID as a client ID for tracking the source of requests.
        // Enable retries
        configProps.put(ProducerConfig.RETRIES_CONFIG, 2); // Retry up to 2 times
        configProps.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, 500); // Wait 500ms between retries
        configProps.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 2000); // 2 seconds - The maximum time the broker has to respond to a single request.
        // The total time allowed to deliver a record, including: retries, batching delays (linger.ms), backoff (retry.backoff.ms)
        // key rule: delivery.timeout.ms must be greater than request.timeout.ms + linger.ms
        configProps.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, 5000); // 5 seconds timeout to deliver a message.

        //configProps.put("enable.idempotence", true); // it will avoid sending duplicate message
        configProps.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, idempotence); // it will avoid sending duplicate message

        //  acks- all,  ensures that the Producer only receives acknowledgement of a successful message when the message is written in all broker replicas.
        configProps.put(ProducerConfig.ACKS_CONFIG, acks);

        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        //configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        // When using Avro schema to send a message to Kafka topic - first do Schema Registry
        //configProps.put(KafkaAvroSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081");
        //configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, org.apache.kafka.common.serialization.StringSerializer.class);
        //configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, io.confluent.kafka.serializers.KafkaAvroSerializer.class);

        //return new DefaultKafkaProducerFactory<>(configProps);
        DefaultKafkaProducerFactory<String, Object> factory =
                new DefaultKafkaProducerFactory<>(configProps);
        factory.setTransactionIdPrefix(transactionIdPrefix);
        return factory;
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        KafkaTemplate<String, Object> kafkaTemplate = new KafkaTemplate<>(this.producerFactory());

        // Register custom producer listener
        kafkaTemplate.setProducerListener(new ProducerListener<String, Object>() {

            @Override
            public void onSuccess(ProducerRecord<String, Object> producerRecord,
                                  RecordMetadata recordMetadata) {
                log.info("Kafka send succeeded: key={}, topic={}, partition={}, offset={}",
                        producerRecord.key(),
                        producerRecord.topic(),
                        recordMetadata.partition(),
                        recordMetadata.offset());
            }

            @Override
            public void onError(ProducerRecord<String, Object> producerRecord,
                                RecordMetadata metadata,
                                Exception exception) {
                log.error("Kafka send failed (ProducerListener): key={}, topic={}",
                        producerRecord.key(), producerRecord.topic(), exception);
            }
        });

        return kafkaTemplate;
    }

}
