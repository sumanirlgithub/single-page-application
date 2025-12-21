package com.neo.payment.connect.client;

import com.neo.payment.connect.request.ConnectPaymentStatusRequest;
import com.neo.payment.config.FeignClientConfig;
import com.neo.payment.config.FeignConfig;
import com.neo.payment.config.PaymentFeignClientInterceptor;
import com.neo.payment.connect.response.ConnectPaymentStatusResponse;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@FeignClient(name = "CONNECT-PAYMENT-SERVICE", url = "${connect.service.payment.url}",
        configuration = { FeignConfig.class,
                PaymentFeignClientInterceptor.class,
                FeignClientConfig.class })
public interface PaymentFeignClient {
    @PostMapping(value = "/enhancedinquiry", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Headers("Content-Type: application/json")
    ConnectPaymentStatusResponse getPaymentStatusFromCitiConnectApi(
            @RequestBody ConnectPaymentStatusRequest request,
            @RequestHeader Map<String, String> headers);


}
