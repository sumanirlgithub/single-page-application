package com.neo.api.order.client;

import com.neo.api.order.config.FeignClientConfig;
import com.neo.api.order.config.FeignConfig;
import com.neo.api.order.config.PaymentFeignClientInterceptor;
import feign.Headers;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name = "INVENTORY-SERVICE", url = "${inventory.service.url}",
        configuration = { FeignConfig.class,
                PaymentFeignClientInterceptor.class,
                FeignClientConfig.class })
@CircuitBreaker(name = "inventoryCircuitBreaker", fallbackMethod = "fallbackMethod")
@Retry(name = "inventoryRetry")
@TimeLimiter(name = "inventoryTimeLimiter")
public interface InventoryFeignClient {
    private Logger log() {
        return LoggerFactory.getLogger(InventoryFeignClient.class);
    }

    @GetMapping(value = "/in-stock", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Headers("Content-Type: application/json")
    boolean isInStock(
            @RequestParam String skuCode, @RequestParam Integer quantity,
            @RequestHeader Map<String, String> headers);

    default boolean fallbackMethod(String skuCode, Integer quantity, Throwable throwable){
        log().info("Cannot get inventory for skuCode {} failure reason: {}", skuCode, throwable.getMessage());
        return false;
    }
}
