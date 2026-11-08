package com.neo.payments.hub.business.service;

import com.neo.payments.hub.business.processor.PaymentProcessor;
import com.neo.payments.hub.business.processor.PaymentProcessorManager;
import com.neo.payments.hub.business.strategy.bankaccount.BankAccountLookupManager;
import com.neo.payments.hub.business.strategy.bankaccount.BankAccountLookupStrategy;
import com.neo.payments.hub.contract.payment.PaymentMethod;
import com.neo.payments.hub.dao.payment.PaymentDocumentRepository;
import com.neo.payments.hub.dao.payment.PaymentRepository;
import com.neo.payments.hub.dao.payment.strategy.PaymentDetailsStrategyRegistry;
import com.neo.payments.hub.dao.submission.BankSubmissionOutboxRepository;
import com.neo.payments.hub.model.bankaccount.BankAccount;
import com.neo.payments.hub.model.payment.DirectDebitPayment;
import com.neo.payments.hub.model.payment.Payment;
import com.neo.payments.hub.model.payment.PaymentDocument;
import com.neo.payments.hub.model.payment.PaymentProcessingResult;
import com.neo.payments.hub.model.submission.BankSubmissionOutboxEvent;
import com.neo.payments.hub.model.web.Environment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@Slf4j
@RequiredArgsConstructor
public class DirectDebitPaymentService implements PaymentService {

    private final PaymentDocumentRepository paymentDocumentRepository;
    private final BankSubmissionOutboxRepository bankSubmissionOutboxRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentDetailsStrategyRegistry paymentDetailsStrategyRegistry;
    private final BankAccountLookupManager bankAccountLookupManager;
    private final PaymentProcessorManager paymentProcessorManager;

    @Override
    public boolean isApplicable(PaymentMethod paymentMethod) {

        return PaymentMethod.DIRECT_DEBIT.equals(paymentMethod);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void processPayment(Payment payment, Environment environment) {
        DirectDebitPayment directDebitPayment = (DirectDebitPayment) payment;

        // Retrieve mandate from mandate-service including bank account details, there could be multiple ways to retrieve
        // bank account, so use strategy patterns here.
        BankAccountLookupStrategy strategy =
                bankAccountLookupManager.getStrategy(environment);
        BankAccount bankAccount =
                strategy.getBankAccount(directDebitPayment.getIdUmr());

        // Create a new enrich payment with the retrieved bank account.
        Payment paymentWithDebitorBankAccount = directDebitPayment.withBankAccount(bankAccount);

        // Select payment processor based on the environment from where request coming
        PaymentProcessor paymentProcessor = paymentProcessorManager.getRelevantProcessor(Environment.THIRD_PARTY);
        log.info("paymentProcessor: "+ paymentProcessor);
        PaymentProcessingResult result = paymentProcessor.process(paymentWithDebitorBankAccount);
        log.info("Generated SEPA XML: {}", result.generatedDocument());

        log.info("Going to store payment in Database");
        try {
            // Save common payment information
            paymentRepository.save(paymentWithDebitorBankAccount);

            // Save payment-specific information
            paymentDetailsStrategyRegistry.save(paymentWithDebitorBankAccount);

            PaymentDocument document = new PaymentDocument(
                    null,
                    payment.getPaymentId(),
                    "PAIN.008.001.02",
                    result.generatedDocument(),
                    "GENERATED",
                    Instant.now()
            );
            PaymentDocument savedDocument =
                    paymentDocumentRepository.save(document);

            BankSubmissionOutboxEvent outboxEvent =
                    new BankSubmissionOutboxEvent(
                            null,
                            payment.getPaymentId(),
                            "BANK_SUBMISSION_REQUESTED",
                            "PENDING",
                            0,
                            result.generatedDocument(),
                            Instant.now(),
                            null
                    );
            bankSubmissionOutboxRepository.save(outboxEvent);
            log.info("Payment record successfully saved into Database");

        } catch(Exception ex) {
            log.info("Rollback for payment with id: {} and payment method: {}.", payment.getPaymentId(), payment.getPaymentMethod());
            throw  ex;
        }
    }

}