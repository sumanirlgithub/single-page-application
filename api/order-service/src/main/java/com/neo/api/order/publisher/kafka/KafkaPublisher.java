package com.neo.api.order.publisher.kafka;

import com.neo.api.common.avro.model.generated.OrderEventName;
import com.neo.api.common.enums.OrderEventType;
import com.neo.api.order.entity.OutboundEvent;
import com.neo.api.order.repository.OutboundEventJpaRepository;
import com.neo.api.order.service.JsonUtil;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.apache.kafka.common.errors.TimeoutException;
import org.apache.kafka.common.errors.UnknownTopicOrPartitionException;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.neo.api.common.order.event.OrderEvent;
import com.neo.api.common.avro.model.generated.OrderAvroEvent;
import com.neo.api.common.avro.model.generated.OrderEventBody;
import com.neo.api.order.config.KafkaTopicConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static com.neo.api.common.order.event.OrderEvent.ORDER_TOPIC;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaPublisher {
	private final KafkaTopicConfig kafkaTopicConfig;
	private final KafkaTemplate<String, Object> kafkaTemplate;
    private final OutboundEventJpaRepository outboundEventJpaRepository;
    private final KafkaPublishGate kafkaPublishGate;
    private final JsonUtil jsonUtil;

    /**
     * Spring boot scheduler will send order event as JSON as string to KAFKA ORDER-TOPIC.
     * This event will be processed by payment-service for further processing.
     */
    @Scheduled(fixedDelayString = "${outbound.publish.delay:30000}")
    @Transactional
    public void publishEvents() {
        List<OutboundEvent> events = outboundEventJpaRepository.findTop100BySentFalseOrderByCreatedAtAsc();
        List<CompletableFuture<SendResult<String, Object>>> futures = new ArrayList<>();

        // STOP NEXT MESSAGE HERE
        if (!kafkaPublishGate.allowSend()) {
            throw new IllegalStateException("Kafka sending paused");
        }

        for (OutboundEvent event : events) {
            OrderEvent orderEvent = new OrderEvent (event.getEventId(), OrderEventType.valueOf(event.getEventType()),
                    event.getPayload(), LocalDateTime.now());
            CompletableFuture<SendResult<String, Object>> future = sendEventAsyncWithCircuitBreaker(event.getOrderId(), jsonUtil.toJson(orderEvent))
                    .thenApply(result -> {
                        event.setSent(true);
                        log.info("Order event successfully published to Kafka topic");
                        return result;
                    })
                    .exceptionally(ex -> {
                        log.error("Scheduler method - Kafka send failed for eventId={}", event.getEventId(), ex);
                        return null; // fallback is already triggered either via ProducerListener or CircuitBreaker
                    });

            futures.add(future);
        }

        // Optional: Wait for all to complete, without blocking scheduler excessively
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        outboundEventJpaRepository.saveAll(events); // persist updated sent status
    }

    /**
     * Async send - Kafka producer method with Resilience4j CircuitBreaker
     * Certain exceptions, like TimeoutException: Topic … not present in metadata, happen inside the producer thread before the
     * CompletableFuture returned by KafkaTemplate.send() is completed.
     * These exceptions are already handled by the ProducerListener (which logs them) and do not propagate to the CompletableFuture.
     * That’s why your handle() / whenComplete() never sees them, even though the ProducerListener logs them correctly.
     * 🔹 Key points:
     * ProducerListener is the reliable place to guarantee logging for all send failures.
     * CompletableFuture handle/whenComplete only sees exceptions that occur after send is accepted by the producer.
     * Network/IO errors that occur asynchronously may propagate.
     * Broker-side failures that happen early (like topic missing, authentication issues) are not propagated to the CompletableFuture.
     *
     * @param orderEvent
     * @param orderEvent
     * @return
     */

    @CircuitBreaker(name = "kafkaProducerCircuitBreaker", fallbackMethod = "sendEventCircuitBreakerFallback")
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public CompletableFuture<SendResult<String, Object>> sendEventAsyncWithCircuitBreaker(String orderId, String orderEvent) {
        return kafkaTemplate.send(kafkaTopicConfig.topic().name(), orderId, orderEvent)
                .handle((result, ex) -> {
                    if (ex != null) {
                        kafkaPublishGate.onFailure();
                        Throwable cause = ex instanceof ExecutionException ? ex.getCause() : ex;
                        throw new RuntimeException(cause);
                    }
                    kafkaPublishGate.onSuccess();
                    // returned record metadata/acknowledgment (ack) info already has been logged in ProducerListener onSuccess() method.
                    return result;
                });
    }

    /**
     * Fallback method called when CircuitBreaker is OPEN or producer fails after retries
     */
    public SendResult<String, Object> sendEventCircuitBreakerFallback(Long eventId, String orderEvent, Throwable ex) {
        log.error("CircuitBreaker FALLBACK executed- Kafka unavailable for orderId={}, reason={}", eventId, ex.toString());
        // Optional: persist failed event to DB for retry
        // failedEventRepository.save(new FailedEvent(eventId, orderEvent, LocalDateTime.now(), ex.toString()));
        return null; // or throw if you want the scheduler to log/retry
    }

    /**
     * Certain exceptions, like TimeoutException: Topic … not present in metadata, happen inside the producer thread before the
     * CompletableFuture returned by KafkaTemplate.send() is completed.
     * These exceptions are already handled by the ProducerListener (which logs them) and do not propagate to the CompletableFuture.
     * That’s why your handle() / whenComplete() never sees them, even though the ProducerListener logs them correctly.
     * 🔹 Key points:
     * ProducerListener is the reliable place to guarantee logging for all send failures.
     * CompletableFuture handle/whenComplete only sees exceptions that occur after send is accepted by the producer.
     * Network/IO errors that occur asynchronously may propagate.
     * Broker-side failures that happen early (like topic missing, authentication issues) are not propagated to the CompletableFuture.
     * @param orderEvent
     * @param orderEvent
     * @return
     */
    public CompletableFuture<SendResult<String, Object>> sendEventAsync(Long eventId, String orderEvent) {
        final String key = String.valueOf(eventId);

        // Modern KafkaTemplate.send() returns CompletableFuture directly
        CompletableFuture<SendResult<String, Object>> future =
                kafkaTemplate.send(kafkaTopicConfig.topic().name(), key, orderEvent);

        // Attach guaranteed logging for exceptions at CompletableFuture level
        return future.handle((result, ex) -> {
            if (ex != null) {
                if (ex instanceof CallNotPermittedException) {
                    // CIRCUIT IS OPEN — PRODUCER IS STOPPED
                    log.warn("Kafka producer stopped by CircuitBreaker");
                    //failedEventRepository.save(event);
                }
                Throwable cause = ex instanceof CompletionException ? ex.getCause() : ex;
                if (cause instanceof KafkaException && cause.getCause() != null) {
                    cause = cause.getCause(); // unwrap KafkaException
                }

                //onFailure(ex) is called if the send failed (broker down, network, etc.)
                onFailure(cause);
                log.error("Error occurred: unable to write an event to KAFKA failed (CompletableFuture): key={}, topic={}",
                        key, kafkaTopicConfig.topic().name(), cause);
                throw new CompletionException(cause);
            } else {
                //onSuccess(result) is called only after ack - Kafka broker acknowledges the message
                onSuccess(result);
                return result;
            }
        });
    }

    /**
     * Certain exceptions, like TimeoutException: Topic … not present in metadata, happen inside the producer thread before the
     * CompletableFuture returned by KafkaTemplate.send() is completed.
     * These exceptions are already handled by the ProducerListener (which logs them) and do not propagate to the CompletableFuture.
     * That’s why your handle() / whenComplete() never sees them, even though the ProducerListener logs them correctly.
     * 🔹 Key points:
     * ProducerListener is the reliable place to guarantee logging for all send failures.
     * CompletableFuture handle/whenComplete only sees exceptions that occur after send is accepted by the producer.
     * Network/IO errors that occur asynchronously may propagate.
     * Broker-side failures that happen early (like topic missing, authentication issues) are not propagated to the CompletableFuture.
     * @param orderEvent
     * @return
     */
    public CompletableFuture<SendResult<String, Object>> sendEventAsync(String orderId, OrderEvent orderEvent) {
        final String key = orderId;

        // Modern KafkaTemplate.send() returns CompletableFuture directly
        CompletableFuture<SendResult<String, Object>> future =
                kafkaTemplate.send(kafkaTopicConfig.topic().name(), key, orderEvent);

        // Attach guaranteed logging for exceptions at CompletableFuture level
        return future.handle((result, ex) -> {
            if (ex != null) {
                Throwable cause = ex instanceof CompletionException ? ex.getCause() : ex;
                if (cause instanceof KafkaException && cause.getCause() != null) {
                    cause = cause.getCause(); // unwrap KafkaException
                }
                //onFailure(ex) is called if the send failed (broker down, network, etc.)
                onFailure(cause);
                log.error("Error occurred: unable to write an event to KAFKA failed (CompletableFuture): key={}, topic={}",
                        key, kafkaTopicConfig.topic().name(), cause);
                throw new CompletionException(cause);
            } else {
                //onSuccess(result) is called only after ack - Kafka broker acknowledges the message
                onSuccess(result);
                return result;
            }
        });
    }

    public void sendMessage(String message)	{
        CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.
                send(kafkaTopicConfig.topic().name(), message);
        future.whenComplete((result, ex) -> {
            if (ex != null) {
                System.out.println("Unable to send message=["
                        + message + "] due to : " + ex.getMessage());
            } else {
                System.out.println("Sent message=[" + message +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]" + "partition=[" + result.getRecordMetadata().partition() + "]");
            }
        });
    }

    /**
     * onSuccess(result) is called only after ack - Kafka broker acknowledges the message
     * @param result
     */
    private void onSuccess(final SendResult<String, Object> result) {
		log.info("Order '{}' has been written with offset={} to topic:{} partition:{} with ingestion timestamp {}.",
				result.getProducerRecord().key(),
				result.getRecordMetadata().offset(),
				result.getRecordMetadata().topic(),
				result.getRecordMetadata().partition(),
				result.getRecordMetadata().timestamp());
	}

    /**
     * onFailure(ex) is called if send failed (broker down, network, etc.)
     * @param t
     */
	private void onFailure(final Throwable t) {
		log.info("Unable to write Order to topic {}.", ORDER_TOPIC, t);
	}

    //@Scheduled(fixedDelayString = "${outbound.publish.delay:5000}") // 5 seconds interval
    @Transactional
    public void publishEventsUsingAvroSchema() {
        OrderEventBody body = OrderEventBody.newBuilder()
                .setId("101")
                .setUserId("test-user")
                .setOrderEventName(OrderEventName.CREATED)
                .setDate(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
                .build();

        OrderAvroEvent orderAvroEvent = OrderAvroEvent.newBuilder()
                .setBody(body)
                .build();
        List<OrderAvroEvent> events = Arrays.asList(orderAvroEvent);

        for (OrderAvroEvent event : events) {
            //kafkaTemplate.send(event.getTopic(), event.getPayload()).get(); // wait for ack
            CompletableFuture<SendResult<String, Object>> result = sendEventAsyncUsingAvro(event.getHeader().getId(), event.getBody().toString());
            log.info("Order event successfully published to Kafka topic");
            //event.setSent(true);
        }
    }

    public CompletableFuture<SendResult<String, Object>> sendEventAsyncUsingAvro(CharSequence eventId, String orderEvent) {
        final String key = String.valueOf(eventId);

        // Modern KafkaTemplate.send() returns CompletableFuture directly
        CompletableFuture<SendResult<String, Object>> future =
                kafkaTemplate.send(kafkaTopicConfig.topic().name(), key, orderEvent);

        // Attach guaranteed logging for exceptions at CompletableFuture level
        return future.handle((result, ex) -> {
            if (ex != null) {
                Throwable cause = ex instanceof CompletionException ? ex.getCause() : ex;
                if (cause instanceof KafkaException && cause.getCause() != null) {
                    cause = cause.getCause(); // unwrap KafkaException
                }
                //onFailure(ex) is called if the send failed (broker down, network, etc.)
                onFailure(cause);
                log.error("Error occurred: unable to write an event to KAFKA failed (CompletableFuture): key={}, topic={}",
                        key, kafkaTopicConfig.topic().name(), cause);
                throw new CompletionException(cause);
            } else {
                //onSuccess(result) is called only after ack - Kafka broker acknowledges the message
                onSuccess(result);
                return result;
            }
        });
    }

    public String createAndSendOrderAvroEvent(OrderAvroEvent orderEvent) {
        final String key = (String) orderEvent.getBody().getId();
        CompletableFuture<SendResult<String, Object>> future =
                kafkaTemplate.send(kafkaTopicConfig.topic().name(), key, orderEvent);
        future.whenComplete((result, ex) -> {
            if (ex != null) {
                //onFailure(ex) is called if the send failed (broker down, network, etc.)
                onFailure(ex);
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "send event fail.");
            } else {
                //onSuccess(result) is called only after ack - Kafka broker acknowledges the message
                onSuccess(result);
            }
        });
        // Optionally, wait for the result synchronously
        future.join();
        return key;
    }

}
