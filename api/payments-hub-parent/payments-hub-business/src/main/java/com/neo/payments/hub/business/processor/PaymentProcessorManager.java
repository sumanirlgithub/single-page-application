package com.neo.payments.hub.business.processor;

import com.neo.payments.hub.model.web.Environment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentProcessorManager {

    private final List<PaymentProcessor> paymentProcessorList;

    public PaymentProcessor getRelevantProcessor(Environment environment) {
        return paymentProcessorList.stream()
                .filter(paymentProcessor -> paymentProcessor.isApplicable(environment)).findFirst()
                .orElseThrow( () -> new IllegalStateException("Payment processor could not be found"));

    }
}
