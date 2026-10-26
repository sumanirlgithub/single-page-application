package com.example.sepa.processor.service;

import com.example.sepa.processor.client.MandateClient;
import com.example.sepa.processor.domain.DirectDebitCreatedEvent;
import com.example.sepa.processor.domain.MandateResponse;
import com.example.sepa.processor.domain.SepaPayment;
import com.example.sepa.processor.repository.SepaPaymentRepository;
import com.example.sepa.processor.pain008.Pain008Generator;
import com.example.sepa.processor.pain008.Pain008Request;
import com.example.sepa.processor.pain008.Pain008Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@Slf4j
public class SepaPaymentProcessorService {

    private final SepaPaymentRepository paymentRepository;
    private final MandateClient mandateClient;
    private final Pain008Generator pain008Generator;
    private final Pain008Validator pain008Validator;

    public SepaPaymentProcessorService(
            SepaPaymentRepository paymentRepository,
            MandateClient mandateClient,
            Pain008Generator pain008Generator,
            Pain008Validator pain008Validator) {

        this.paymentRepository = paymentRepository;
        this.mandateClient = mandateClient;
        this.pain008Generator = pain008Generator;
        this.pain008Validator = pain008Validator;
    }

    @Transactional
    public void process(
            DirectDebitCreatedEvent event) {

        System.out.println(
                "Processing direct debit: "
                        + event.collectionId()
        );

        // -----------------------------------------
        // 1. Idempotency check
        // -----------------------------------------

        if (paymentRepository.existsByCollectionId(
                event.collectionId())) {

            System.out.println(
                    "Direct debit already processed: "
                            + event.collectionId()
            );

            return;
        }

        // -----------------------------------------
        // 2. Validate Kafka event
        // -----------------------------------------

        validateEvent(event);

        // -----------------------------------------
        // 3. Get mandate from Mandate Service
        // -----------------------------------------

        MandateResponse mandate =
                mandateClient.getMandate(
                        event.mandateId()
                );

        log.info("Mandate receive from mandate-service: " + mandate.toString());

        // -----------------------------------------
        // 4. Validate mandate
        // -----------------------------------------

        validateMandate(mandate);

        // -----------------------------------------
        // 5. Create internal SEPA payment
        // -----------------------------------------

        String paymentId =
                UUID.randomUUID().toString();

        Instant now = Instant.now();

        SepaPayment payment =
                new SepaPayment(
                        paymentId,
                        event.collectionId(),
                        event.mandateId(),
                        event.amount(),
                        event.currency(),
                        event.requestedCollectionDate(),
                        "RECEIVED",
                        now,
                        now
                );

        paymentRepository.save(payment);

        // -----------------------------------------
        // 6. Build Pain008Request
        // -----------------------------------------

        Pain008Request pain008Request =
                new Pain008Request(

                        "MSG-" + paymentId,

                        event.requestedCollectionDate(),

                        // Creditor
                        mandate.creditor().name(),
                        mandate.creditor().creditorId(),

                        // NOTE:
                        // Your current MandateResponse
                        // needs creditor IBAN/BIC if these
                        // are required for your pain.008.
                        mandate.creditor().iban(),
                        mandate.creditor().bic(),

                        // Debtor
                        mandate.debtor().name(),
                        mandate.debtor().iban(),
                        mandate.debtor().bic(),

                        // Mandate
                        mandate.mandateId(),
                        mandate.signatureDate(),

                        // Payment
                        event.amount(),
                        event.currency(),

                        // Sequence
                        event.sequenceType(),

                        // Collection
                        event.collectionId()
                );

        // -----------------------------------------
        // 7. Validate pain.008 request
        // -----------------------------------------

        pain008Validator.validate(
                pain008Request
        );

        // -----------------------------------------
        // 8. Generate pain.008 XML
        // -----------------------------------------

        String pain008Xml =
                pain008Generator.generate(
                        pain008Request
                );

        System.out.println(
                "Generated pain.008:"
        );

        System.out.println(pain008Xml);

        // -----------------------------------------
        // 9. Update status
        // -----------------------------------------

        paymentRepository.updateStatus(
                paymentId,
                "XML_GENERATED"
        );

        // -----------------------------------------
        // 10. Next:
        // Send XML to bank / PSP
        // -----------------------------------------

        /*
         * bankClient.submit(pain008Xml);
         *
         * paymentRepository.updateStatus(
         *         paymentId,
         *         "SUBMITTED"
         * );
         */
    }

    private void validateEvent(
            DirectDebitCreatedEvent event) {

        if (event == null) {
            throw new IllegalArgumentException(
                    "Direct debit event cannot be null"
            );
        }

        if (isBlank(event.collectionId())) {
            throw new IllegalArgumentException(
                    "Collection ID is required"
            );
        }

        if (isBlank(event.mandateId())) {
            throw new IllegalArgumentException(
                    "Mandate ID is required"
            );
        }

        if (event.amount() == null
                || event.amount().signum() <= 0) {

            throw new IllegalArgumentException(
                    "Amount must be greater than zero"
            );
        }

        if (!"EUR".equalsIgnoreCase(
                event.currency())) {

            throw new IllegalArgumentException(
                    "SEPA Direct Debit must use EUR"
            );
        }

        if (event.requestedCollectionDate() == null) {
            throw new IllegalArgumentException(
                    "Collection date is required"
            );
        }

        if (isBlank(event.sequenceType())) {
            throw new IllegalArgumentException(
                    "Sequence type is required"
            );
        }
    }

    private void validateMandate(
            MandateResponse mandate) {

        if (mandate == null) {
            throw new IllegalArgumentException(
                    "Mandate not found"
            );
        }

        if (!"ACTIVE".equalsIgnoreCase(
                mandate.status())) {

            throw new IllegalArgumentException(
                    "Mandate is not active. Mandate ID: "
                            + mandate.mandateId()
            );
        }

        if (isBlank(mandate.debtor().name())) {
            throw new IllegalArgumentException(
                    "Mandate debtor name is missing"
            );
        }

        if (isBlank(mandate.debtor().iban())) {
            throw new IllegalArgumentException(
                    "Mandate debtor IBAN is missing"
            );
        }

        if (mandate.signatureDate() == null) {
            throw new IllegalArgumentException(
                    "Mandate signature date is missing"
            );
        }

        if (isBlank(mandate.creditor().creditorId())) {
            throw new IllegalArgumentException(
                    "Mandate creditor ID is missing"
            );
        }

        if (isBlank(mandate.creditor().name())) {
            throw new IllegalArgumentException(
                    "Mandate creditor name is missing"
            );
        }
    }

    private boolean isBlank(String value) {

        return value == null
                || value.isBlank();
    }
}