package com.neo.payments.hub.business.processor;

import com.neo.payments.hub.business.processor.sepa.Pain008Generator;
import com.neo.payments.hub.business.processor.sepa.Pain008Validator;
import com.neo.payments.hub.model.payment.DirectDebitPayment;
import com.neo.payments.hub.model.payment.Payment;
import com.neo.payments.hub.model.payment.PaymentProcessingResult;
import com.neo.payments.hub.model.web.Environment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@Slf4j
@RequiredArgsConstructor
public class ThirdPartyPaymentProcessor extends AbstractPaymentProcessor {

    private final Pain008Generator pain008Generator;
    private final Pain008Validator pain008Validator;

    @Override
    public boolean isApplicable(Environment environment) {
        return Environment.THIRD_PARTY.equals(environment);
    }

    @Override
    public PaymentProcessingResult process(Payment payment) {

        DirectDebitPayment directDebitPayment =
                (DirectDebitPayment) payment;

        String pain008Xml =
                pain008Generator.generate(directDebitPayment);

        pain008Validator.validate(pain008Xml);

        return new PaymentProcessingResult(
                directDebitPayment,
                pain008Xml
        );
    }

}
