package com.neo.payments.hub.contract.payment;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DirectDebitPaymentRequest(

        Integer idInternal,

        String submissionId,

        String idUmr,

        BigDecimal amountRemit,

        String currency,

        LocalDate paymentDate,

        PaymentAction paymentAction,

        PaymentMethod paymentMethod,

        String debtorName,

        String debtorIban,

        String debtorBic,

        String creditorName,

        String creditorId,

        String creditorIban,

        String creditorBic,

        String mandateId,

        LocalDate mandateSignatureDate,

        String sequenceType

) implements PaymentRequest {

}