package com.neo.payments.hub.business.processor;

import com.neo.payments.hub.business.service.PaymentServiceManager;
import com.neo.payments.hub.contract.payment.PaymentRequest;
import com.neo.payments.hub.model.payment.Payment;
import com.neo.payments.hub.model.web.Environment;
import org.springframework.stereotype.Service;

@Service
public class RosPaymentProcessor extends AbstractPaymentProcessor {

    @Override
    public boolean isApplicable(Environment environment) {
        return Environment.ROS.equals(environment);
    }

}
