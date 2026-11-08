package com.neo.payments.hub.business.mapper.payment;

import com.neo.payments.hub.contract.payment.DirectDebitPaymentRequest;
import com.neo.payments.hub.contract.payment.PaymentRequest;
import com.neo.payments.hub.contract.payment.PaymentMethod;
import com.neo.payments.hub.model.payment.DirectDebitPayment;
import com.neo.payments.hub.model.payment.Payment;
import com.neo.payments.hub.model.payment.PaymentStatus;

import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class DirectDebitPaymentMapper implements PaymentMapper {

    @Override
    public boolean isApplicable(PaymentRequest request) {
        return request instanceof DirectDebitPaymentRequest;
    }

    @Override
    public Payment toDomain(
            PaymentRequest request,
            String paymentId,
            String submissionId,
            Instant now) {

        DirectDebitPaymentRequest directDebitRequest =
                (DirectDebitPaymentRequest) request;

        return new DirectDebitPayment(
                paymentId,
                submissionId,
                directDebitRequest.amountRemit(),
                directDebitRequest.currency(),
                directDebitRequest.paymentDate(),
                directDebitRequest.idInternal(),
                directDebitRequest.paymentAction(),
                PaymentStatus.RECEIVED,
                now,
                now,
                directDebitRequest.debtorName(),
                directDebitRequest.debtorIban(),
                directDebitRequest.debtorBic(),
                directDebitRequest.creditorName(),
                directDebitRequest.creditorId(),
                directDebitRequest.creditorIban(),
                directDebitRequest.creditorBic(),
                directDebitRequest.mandateId(),
                directDebitRequest.idUmr(),
                directDebitRequest.mandateSignatureDate(),
                directDebitRequest.sequenceType()
        );
    }
}