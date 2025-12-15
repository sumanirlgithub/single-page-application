package com.neo.api.order.controller;

import com.neo.api.order.model.CustomerRequestPayload;
import com.neo.api.order.model.OrderRequestPayload;
import com.neo.api.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CustomerController {
    private final OrderService orderService;

    /**
     *
     * Endpoint save Customer.
     */
    @PostMapping("/customers")
    public String saveOrderItems(
            @Valid
            @RequestBody
            CustomerRequestPayload customerRequest) {
        log.info("CustomerRequestPayload: " + customerRequest.toString());
        return orderService.addCustomer(customerRequest);
    }

}
