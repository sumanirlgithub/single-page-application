package com.neo.api.order.model;


import com.neo.api.common.enums.OrderStatus;
import com.neo.api.order.entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponsePayload {

    private String orderId;
    private String customerId;
    List<OrderItem> orderItems;
    private String paymentStatus; // "SUCCESS" OR "FAILED"
    private OrderStatus orderStatus; // "COMPLETED" OR "CANCELLED"
    private LocalDate orderDate;


}
