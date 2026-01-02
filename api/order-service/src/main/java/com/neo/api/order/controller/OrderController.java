package com.neo.api.order.controller;

import com.neo.api.order.model.Order;
import com.neo.api.order.model.OrderRequestPayload;
import com.neo.api.order.model.OrderResponsePayload;
import com.neo.api.order.model.PaymentInsightResponse;
import com.neo.api.order.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
@Slf4j
@RefreshScope
@RequestMapping("/v1")
public class OrderController {
    private final OrderService orderService;

    @Value("${message2}") // this property is defined in Spring Cloud Config Server (configuration-service)
    private String message2Property;

    /**
     *
     * Endpoint display order items.
     */
    @GetMapping("/orders")
    public List<String> getOrderItems(
            @RequestParam(name = "order-id", required = true) String orderId) {
        log.info("Request received from client application");
        log.info(message2Property);
        return orderService.getOrderItemsForOrder(orderId);
    }

    @GetMapping("/orders/{id}")
    public OrderResponsePayload getOrderById(@PathVariable String id) {
        return orderService.getOrderById(id);
    }

    /**
     *
     * Endpoint save order items.
     */
    @PostMapping("/orders")
    public Order saveOrderItems(
            @Valid
            @RequestBody
            OrderRequestPayload orderRequest) throws JsonProcessingException, InterruptedException, ExecutionException {
        log.info("Request received from client application for placing an order");
        return orderService.placeOrder(orderRequest);
    }

}
