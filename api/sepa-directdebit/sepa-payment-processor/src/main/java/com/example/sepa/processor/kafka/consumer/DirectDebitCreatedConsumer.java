package com.example.sepa.processor.kafka.consumer;

import com.example.sepa.processor.domain.DirectDebitCreatedEvent;
import com.example.sepa.processor.service.SepaPaymentProcessorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class DirectDebitCreatedConsumer {

    private final ObjectMapper objectMapper;
    private final SepaPaymentProcessorService processorService;

    public DirectDebitCreatedConsumer(
            ObjectMapper objectMapper,
            SepaPaymentProcessorService processorService) {

        this.objectMapper = objectMapper;
        this.processorService = processorService;
    }

    @KafkaListener(
            topics = "DIRECT-DEBIT-CREATED",
            groupId = "sepa-payment-processor"
    )
    public void consume(String message) {

        try {

            System.out.println(
                    "Received Kafka message: "
                            + message
            );

            DirectDebitCreatedEvent event =
                    objectMapper.readValue(
                            message,
                            DirectDebitCreatedEvent.class
                    );

            processorService.process(event);

        } catch (Exception e) {

            System.err.println(
                    "Failed to process direct debit event: "
                            + e.getMessage()
            );

            throw new RuntimeException(
                    "SEPA payment processing failed",
                    e
            );
        }
    }
}