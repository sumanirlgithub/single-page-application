package com.neo.api.order.config;

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

import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
public class KafkaProducerConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Value("${spring.kafka.properties.schema.registry.url}")
    private String registry;

    @Value("${spring.kafka.producer.acks}")
    private String acks;


    @Value("${spring.kafka.producer.properties.enable.idempotence}")
    private String idempotence;

    @Value("${spring.kafka.producer.transaction-id-prefix:tx-}")
    private String transactionIdPrefix;




    @Bean
    public ProducerFactory<String, Object> producerFactory()
    {
        Map<String,Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configProps.put(ProducerConfig.CLIENT_ID_CONFIG, "producer-id-place-order"); //a randomly generated UUID as a client ID for tracking the source of requests.
        // Enable retries
        configProps.put(ProducerConfig.RETRIES_CONFIG, 1); // Retry up to 3 times

        configProps.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, 2000); // wait max 2 seconds

        configProps.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, 500); // Wait 500ms between retries
        configProps.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 2000); // 2 seconds - The maximum time the broker has to respond to a single request.
        // The total time allowed to deliver a record, including: retries, batching delays (linger.ms), backoff (retry.backoff.ms)
        // key rule: delivery.timeout.ms must be greater than request.timeout.ms + linger.ms
        configProps.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, 3000); // 5 seconds timeout to deliver a message.

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
        //factory.setTransactionIdPrefix(transactionIdPrefix);
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
                log.info("Kafka send succeeded for: key={}, topic={}, partition={}, offset={}",
                        producerRecord.key(),
                        producerRecord.topic(),
                        recordMetadata.partition(),
                        recordMetadata.offset());
            }

            /**
             * Single reliable place for handling all Kafka send failures that happen asynchronously,
             * Exceptions thrown before calling KafkaTemplate.send() (e.g., null pointer, DB access errors). will not be caught.
             * @param producerRecord
             * @param metadata
             * @param exception
             */
            @Override
            public void onError(ProducerRecord<String, Object> producerRecord,
                                RecordMetadata metadata,
                                Exception exception) {
                log.error("(ProducerListener - Kafka send failed  for: key={}, topic={}",
                        producerRecord.key(), producerRecord.topic(), exception);
                // call fallback or persist event for retry
                sendEventFallback(Long.valueOf(producerRecord.key()), producerRecord.value().toString(), exception);

            }
        });

        return kafkaTemplate;
    }

    /**
     * Fallback method called when
     * 1. Network / Broker Failures
     * These happen after the producer sends the message to Kafka but before it’s acknowledged:
     * TimeoutException – when the message could not be sent in time (delivery.timeout.ms exceeded).
     * NetworkException – issues with the TCP connection to the broker.
     * DisconnectException – the broker disconnected while sending.
     * NotEnoughReplicasException – not enough in-sync replicas to acknowledge the write.
     * NotLeaderForPartitionException – the broker is no longer the leader for that partition.
     * ✅ These are all asynchronous errors and always reach onError().
     * 2. Serialization / Record Construction Exceptions
     * Happen before sending the record:
     * SerializationException – if the key or value cannot be serialized.
     * RecordTooLargeException – if the message exceeds max.request.size.
     * ✅ These exceptions also trigger onError().
     * 3. Transaction / Producer State Exceptions
     * If you are using transactional Kafka producers:
     * ProducerFencedException – another producer with the same transactional ID is active.
     * KafkaException – for generic transactional errors, e.g., commit/abort failure.
     * ⚠️ These only apply if transactions are enabled.
     * 4. What onError() will NOT catch
     * Exceptions thrown before calling KafkaTemplate.send() (e.g., null pointer, DB access errors).
     */
    public void sendEventFallback(Long eventId, String orderEvent, Throwable ex) {
        log.error("FALLBACK (ProducerListener) - Kafka unavailable for eventId={}, reason={}", eventId, ex.toString());
        // Optional: persist failed event to DB for retry
        // failedEventRepository.save(new FailedEvent(eventId, orderEvent, LocalDateTime.now(), ex.toString()));
    }
}
