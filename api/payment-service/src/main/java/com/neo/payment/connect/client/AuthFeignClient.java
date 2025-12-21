package com.neo.payment.connect.client;

import com.neo.payment.config.FeignClientConfig;
import com.neo.payment.config.FeignConfig;
import com.neo.payment.config.PaymentFeignClientInterceptor;
import com.neo.payment.connect.response.ConnectAuthResponse;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@FeignClient(name = "CONNECT-AUTH-SERVICE", url = "${connect.service.auth.url}",
        configuration = { FeignConfig.class,
                PaymentFeignClientInterceptor.class,
                FeignClientConfig.class })
public interface AuthFeignClient {

    @PostMapping(value = "/token", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Headers("Content-Type: application/json")
    ResponseEntity<ConnectAuthResponse> getAuthTokenFromConnectApi(
            @RequestBody String requestBody,
            @RequestHeader Map<String, String> headers);
}
