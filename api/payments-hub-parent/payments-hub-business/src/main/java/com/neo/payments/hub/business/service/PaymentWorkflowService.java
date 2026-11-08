package com.neo.payments.hub.business.service;

import com.neo.payments.hub.business.mapper.payment.PaymentMapper;
import com.neo.payments.hub.business.mapper.payment.PaymentMapperRegistry;
import com.neo.payments.hub.business.processor.PaymentProcessor;
import com.neo.payments.hub.business.processor.PaymentProcessorManager;
import com.neo.payments.hub.contract.payment.PaymentMethod;
import com.neo.payments.hub.contract.payment.PaymentRequest;
import com.neo.payments.hub.dao.payment.PaymentRepository;
import com.neo.payments.hub.dao.payment.strategy.PaymentDetailsStrategyRegistry;
import com.neo.payments.hub.model.payment.Payment;
import com.neo.payments.hub.model.payment.PaymentRecord;
import com.neo.payments.hub.model.web.Environment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentWorkflowService {

    private final PaymentServiceManager paymentServiceManager;
    private final PaymentDetailsStrategyRegistry paymentDetailsStrategyRegistry;
    private final PaymentMapperRegistry paymentMapperRegistry;
    private final PaymentRepository paymentRepository;

    @Transactional
    public Payment createPayment(
            PaymentRequest request,
            PaymentMethod paymentMethod,
            String submissionId) {

        // 1. Validate input
        Objects.requireNonNull(
                request,
                "Payment request is required"
        );

        Objects.requireNonNull(
                paymentMethod,
                "Payment method is required"
        );

        // 2. Generate payment ID
        String paymentId = generatePaymentId();

        // 3. Generate audit timestamp
        Instant now = Instant.now();

        // 4. Convert DTO into the appropriate domain object
        PaymentMapper mapper = paymentMapperRegistry.getMapper(request);
        log.info("mapper: " + mapper);

        Payment payment = mapper.toDomain(
                request,
                paymentId,
                request.submissionId(),
                now
        );

        // 5. Select payment-method-specific service
        PaymentService paymentService = paymentServiceManager.getService(payment.getPaymentMethod());
        log.info("paymentService: "+ paymentService);
        paymentService.processPayment(payment, Environment.THIRD_PARTY);

        return getPaymentById(paymentId);
    }

    @Transactional(readOnly = true)
    public Payment getPaymentById(String paymentId) {

        // Retrieve the common persisted information
        PaymentRecord record = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Payment not found: " + paymentId
                ));

        // Reconstruct and return the complete persisted domain object
        return paymentDetailsStrategyRegistry.findById(record);
    }

    private String generatePaymentId() {
        return "PAY" + UUID.randomUUID().toString().replace("-", "");
    }
}