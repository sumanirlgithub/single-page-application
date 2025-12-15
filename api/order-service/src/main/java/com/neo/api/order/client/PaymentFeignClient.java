package com.neo.api.order.client;

import com.neo.api.order.config.FeignClientConfig;
import com.neo.api.order.config.FeignConfig;
import com.neo.api.order.config.PaymentFeignClientInterceptor;
import com.neo.api.order.model.PaymentInsightRequest;
import com.neo.api.order.model.PaymentInsightResponse;
import feign.Headers;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.Map;

@FeignClient(name = "CONNECT-PAYMENT-SERVICE", url = "${payment.service.url}",
        configuration = { FeignConfig.class,
                PaymentFeignClientInterceptor.class,
                FeignClientConfig.class })
//@LoadBalancerClient(name = "payment-service")
public interface PaymentFeignClient {
    @PostMapping(value = "/status", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Headers("Content-Type: application/json")
    ResponseEntity<List<PaymentInsightResponse>> getPaymentStatusFromPaymentApi(
            @RequestBody PaymentInsightRequest request,
            @RequestHeader Map<String, String> headers);
}
