package com.neo.api.order.model;

import com.neo.api.order.entity.OrderItem;
import jakarta.persistence.Column;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderRequestPayload {

    private String customerId;
    private String orderDetails;
    private List<OrderItemModel> items;

    private Integer cardNumber;
    private String cardHolder;
    private BigDecimal amount;
    private String cardExpiry;
    private Integer cvc;
}
