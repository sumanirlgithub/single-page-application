package com.neo.payment.publisher.kafka;

import com.neo.api.common.payment.event.PaymentEvent;
import com.neo.payment.config.KafkaTopicConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.concurrent.CompletableFuture;

import static com.neo.api.common.payment.event.PaymentProcessingEvent.PAYMENT_PROCESSING_TOPIC;


@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaPublisher {
	private final KafkaTopicConfig kafkaTopicConfig;
	private final KafkaTemplate<String, Object> kafkaTemplate;
	public void createAndSendEvent(PaymentEvent event) {
		final String key = String.valueOf(event.orderId());
		CompletableFuture<SendResult<String, Object>> future =
				kafkaTemplate.send(kafkaTopicConfig.topic().name(), key, event);
		future.whenComplete((result, ex) -> {
			if (ex != null) {
				onFailure(ex);
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "send event fail.");
			} else {
				onSuccess(result);
			}
		});
		// Optionally, wait for the result synchronously
		//future.join();
	}
	private void onSuccess(final SendResult<String, Object> result) {
		log.info("Payment Status  '{}' has been written with offset={} to topic:{} partition:{} with ingestion timestamp {}.",
				result.getProducerRecord().key(),
				result.getRecordMetadata().offset(),
				result.getRecordMetadata().topic(),
				result.getRecordMetadata().partition(),
				result.getRecordMetadata().timestamp());
	}
	private void onFailure(final Throwable t) {
		log.info("Unable to write Payment Status to topic {}.", PAYMENT_PROCESSING_TOPIC, t);
	}
}
