
package com.example.sepa.processor.service;

import com.example.sepa.processor.client.MandateClient;
import com.example.sepa.processor.domain.DirectDebitCreatedEvent;
import com.example.sepa.processor.domain.MandateResponse;
import com.example.sepa.processor.domain.SepaPayment;
import com.example.sepa.processor.pain008.Pain008Generator;
import com.example.sepa.processor.pain008.Pain008Request;
import com.example.sepa.processor.pain008.Pain008Validator;
import com.example.sepa.processor.repository.SepaPaymentRepository;
import java.time.Instant;
import java.util.Locale;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
@Slf4j
public class SepaPaymentProcessorService {

    private final SepaPaymentRepository paymentRepository;
    private final MandateClient mandateClient;
    private final Pain008Generator pain008Generator;
    private final Pain008Validator pain008Validator;
    private final BankSubmissionService bankSubmissionService;

    public SepaPaymentProcessorService(
            SepaPaymentRepository paymentRepository,
            MandateClient mandateClient,
            Pain008Generator pain008Generator,
            Pain008Validator pain008Validator,
            BankSubmissionService bankSubmissionService) {

        this.paymentRepository = paymentRepository;
        this.mandateClient = mandateClient;
        this.pain008Generator = pain008Generator;
        this.pain008Validator = pain008Validator;
        this.bankSubmissionService = bankSubmissionService;
    }

    @Transactional
    public void process(DirectDebitCreatedEvent event) {

        validateEvent(event);

        log.info(
                "Processing direct debit collectionId={}",
                event.collectionId()
        );

        // 1. Idempotency check
        if (paymentRepository.existsByCollectionId(event.collectionId())) {
            log.info(
                    "Payment already exists for collectionId={}",
                    event.collectionId()
            );
            return;
        }

        // 2. Retrieve mandate
        MandateResponse mandate =
                mandateClient.getMandate(event.mandateId());

        validateMandate(mandate);

        log.info(
                "Mandate retrieved successfully. mandateId={}",
                mandate.mandateId()
        );

        // 3. Create and persist the initial payment
        String paymentId = UUID.randomUUID().toString();
        Instant now = Instant.now();

        String currency =
                event.currency().trim().toUpperCase(Locale.ROOT);

        SepaPayment payment = new SepaPayment(
                paymentId,
                event.collectionId(),
                event.mandateId(),
                event.amount(),
                currency,
                event.requestedCollectionDate(),
                "RECEIVED",
                null,
                "NOT_SUBMITTED",
                null,
                now,
                now
        );

        paymentRepository.save(payment);

        // 4. Build pain.008 request
        Pain008Request pain008Request = new Pain008Request(
                "MSG-" + paymentId,
                event.requestedCollectionDate(),

                // Creditor
                mandate.creditor().name(),
                mandate.creditor().creditorId(),
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
                currency,

                // Sequence
                event.sequenceType().trim().toUpperCase(Locale.ROOT),

                // Collection
                event.collectionId()
        );

        // 5. Validate pain.008 request
        pain008Validator.validate(pain008Request);

        // 6. Generate pain.008 XML
        String pain008Xml =
                pain008Generator.generate(pain008Request);

        if (pain008Xml == null || pain008Xml.isBlank()) {
            throw new IllegalStateException(
                    "pain.008 XML generation returned an empty document"
            );
        }

        log.info(
                "pain.008 XML generated successfully. paymentId={}",
                paymentId
        );

        // 7. Save XML and update status to XML_GENERATED
        paymentRepository.saveGeneratedXml(paymentId, pain008Xml);

        log.info(
                "pain.008 XML saved. paymentId={}, status=XML_GENERATED",
                paymentId
        );

        // 8. Trigger bank submission after the transaction commits
        scheduleBankSubmissionAfterCommit(paymentId);
    }

    private void scheduleBankSubmissionAfterCommit(String paymentId) {

        if (!TransactionSynchronizationManager
                .isSynchronizationActive()) {
            throw new IllegalStateException(
                    "Transaction synchronization is not active"
            );
        }

        TransactionSynchronizationManager.registerSynchronization(
                new TransactionSynchronization() {

                    @Override
                    public void afterCommit() {
                        log.info(
                                "Payment committed. Initiating bank submission. "
                                        + "paymentId={}",
                                paymentId
                        );

                        try {
                            bankSubmissionService.submit(paymentId);
                        } catch (RuntimeException ex) {
                            log.error(
                                    "Bank submission workflow failed. "
                                            + "paymentId={}. "
                                            + "Payment requires recovery/reconciliation.",
                                    paymentId,
                                    ex
                            );
                        }
                    }
                }
        );
    }

    private void validateEvent(DirectDebitCreatedEvent event) {

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

        if (event.amount() == null || event.amount().signum() <= 0) {
            throw new IllegalArgumentException(
                    "Amount must be greater than zero"
            );
        }

        if (!"EUR".equalsIgnoreCase(event.currency())) {
            throw new IllegalArgumentException(
                    "SEPA Direct Debit must use EUR"
            );
        }

        if (event.requestedCollectionDate() == null) {
            throw new IllegalArgumentException(
                    "Collection date is required"
            );
        }

        if (isBlank(event.sequenceType())
                || !event.sequenceType().trim().matches(
                "(?i)FRST|RCUR|OOFF|FNAL")) {
            throw new IllegalArgumentException(
                    "Sequence type must be FRST, RCUR, OOFF or FNAL"
            );
        }
    }

    private void validateMandate(MandateResponse mandate) {

        if (mandate == null) {
            throw new IllegalArgumentException("Mandate not found");
        }

        if (!"ACTIVE".equalsIgnoreCase(mandate.status())) {
            throw new IllegalArgumentException(
                    "Mandate is not active. Mandate ID: "
                            + mandate.mandateId()
            );
        }

        if (mandate.debtor() == null) {
            throw new IllegalArgumentException(
                    "Mandate debtor details are missing"
            );
        }

        if (mandate.creditor() == null) {
            throw new IllegalArgumentException(
                    "Mandate creditor details are missing"
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

        if (isBlank(mandate.creditor().name())) {
            throw new IllegalArgumentException(
                    "Mandate creditor name is missing"
            );
        }

        if (isBlank(mandate.creditor().creditorId())) {
            throw new IllegalArgumentException(
                    "Mandate creditor ID is missing"
            );
        }

        if (isBlank(mandate.creditor().iban())) {
            throw new IllegalArgumentException(
                    "Mandate creditor IBAN is missing"
            );
        }

        if (mandate.signatureDate() == null) {
            throw new IllegalArgumentException(
                    "Mandate signature date is missing"
            );
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}