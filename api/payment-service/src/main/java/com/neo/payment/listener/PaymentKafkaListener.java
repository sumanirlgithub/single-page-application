package com.neo.payment.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.neo.api.common.order.event.InventoryReservedEvent;
import com.neo.api.common.order.event.OrderEvent;
import com.neo.payment.domain.handler.InventoryEventHandler;
import com.neo.payment.domain.handler.OrderEventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.listener.ConsumerSeekAware;
import org.springframework.kafka.retrytopic.TopicSuffixingStrategy;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.converter.ConversionException;
import org.springframework.kafka.support.serializer.DeserializationException;
import org.springframework.messaging.converter.MessageConversionException;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.invocation.MethodArgumentResolutionException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.net.SocketException;


@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentKafkaListener implements ConsumerSeekAware {

    private final OrderEventHandler orderEventHandler;
    private final InventoryEventHandler inventoryEventHandler;
    private final ObjectMapper mapper;

    @RetryableTopic(kafkaTemplate = "kafkaTemplate",
            exclude = {DeserializationException.class,
                    MessageConversionException.class,
                    ConversionException.class,
                    MethodArgumentResolutionException.class,
                    NoSuchMethodException.class,
                    ClassCastException.class},
            attempts = "4",
            backoff = @Backoff(delay = 3000, multiplier = 1.5, maxDelay = 15000),
            timeout = "90000",
            topicSuffixingStrategy = TopicSuffixingStrategy.SUFFIX_WITH_INDEX_VALUE
    )
    @KafkaListener(id = "PaymentListener1" , topics = "INVENTORY-RESERVED-TOPIC", groupId = "payment")
    @Transactional
    public void listenInventoryReservedEventWithRetry(@Header(KafkaHeaders.RECEIVED_TOPIC) String receivedTopic,
                                                      InventoryReservedEvent event, Acknowledgment ack) throws SocketException {
        log.info("Message received from Topic({}), receive data = {}", receivedTopic, event);
        try {
            inventoryEventHandler.onEvent(event);
            ack.acknowledge(); // commit offset only after success
        } catch (Exception e) {
            log.warn("Fail to handle event {}.", event);
            throw e;
        }
    }

    //@KafkaListener(id = "PaymentConsumer1" , topics = "ORDER-CREATED-TOPIC", groupId = "1")
    public void listenOrderEvent(String message) {
        log.info("Message received from ORDER-CREATED-TOPIC: "+ message);
    }

    @RetryableTopic(kafkaTemplate = "kafkaTemplate",
            exclude = {DeserializationException.class,
                    MessageConversionException.class,
                    ConversionException.class,
                    MethodArgumentResolutionException.class,
                    NoSuchMethodException.class,
                    ClassCastException.class},
            attempts = "4",
            backoff = @Backoff(delay = 3000, multiplier = 1.5, maxDelay = 15000),
            timeout = "90000",
            topicSuffixingStrategy = TopicSuffixingStrategy.SUFFIX_WITH_INDEX_VALUE
    )
    @KafkaListener(id = "PaymentListener2" , topics = "ORDER-CREATED-TOPIC", groupId = "saga-group")
    @Transactional
    public void listenOrderEventWithRetry(@Header(KafkaHeaders.RECEIVED_TOPIC) String receivedTopic,
                                          String message, Acknowledgment ack) throws SocketException, JsonProcessingException {
        log.info("Message received from Topic ({}), receive data = {}", receivedTopic, message);
        mapper.registerModule(new JavaTimeModule()); // for Java 8 DateTime feature
        OrderEvent event = mapper.readValue(message, OrderEvent.class);
        try {
            orderEventHandler.onEvent(event);
            ack.acknowledge(); // commit offset only after success
            log.info("offset commited after successful order event processing");

        } catch (Exception e) {
            log.warn("Fail to handle event {}.", event);
            throw e;
        }
    }

    @DltHandler
    public void processMessage(OrderEvent message) {
        log.error("{} DltHandler processMessage = {}", "ORDER-CREATED-TOPIC", message);
    }

    @RetryableTopic(kafkaTemplate = "kafkaTemplate",
            exclude = {DeserializationException.class,
                    MessageConversionException.class,
                    ConversionException.class,
                    MethodArgumentResolutionException.class,
                    NoSuchMethodException.class,
                    ClassCastException.class},
            attempts = "4",
            backoff = @Backoff(delay = 3000, multiplier = 1.5, maxDelay = 15000)
    )
    //@KafkaListener(id = "PaymentConsumer1" , topics = "ORDER-TOPIC", groupId = "1")
    @Transactional
    public void listenOrderEventAvroWithRetry(@Header(KafkaHeaders.RECEIVED_TOPIC) String receivedTopic,
                                             com.neo.api.common.avro.model.generated.OrderAvroEvent orderEvent, Acknowledgment ack) throws SocketException {
        log.info("Message received from Topic({}), receive data = {}", receivedTopic, orderEvent);
        try {
            orderEventHandler.onEvent(orderEvent);
            ack.acknowledge();
        } catch (Exception e) {
            log.warn("Fail to handle event {}.", orderEvent);
            throw e;
        }
    }

}