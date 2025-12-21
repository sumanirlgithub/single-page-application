package com.neo.payment.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PaymentFeignClientInterceptor implements RequestInterceptor {

    public static final String CONNECT_PAYMENT_SERVICE = "CONNECT-PAYMENT-SERVICE";

    @Value("{citiconnect.payment.service.client.id:test}")
    private String clientId;

    @Value("{citiconnect.payment.service.client.secret:test}")
    private String clientSecret;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        String serviceName = requestTemplate.feignTarget().name();
        if (CONNECT_PAYMENT_SERVICE.equals(serviceName)) {
            requestTemplate.header("X_IBM_CLIENT_ID", clientId);
            requestTemplate.header("X_IBM_CLIENT_SECRET", clientSecret);
            //requestTemplate.header("Authorization", authorization);
        } else {
            log.info("Interceptor called for other service");
        }
    }
}
