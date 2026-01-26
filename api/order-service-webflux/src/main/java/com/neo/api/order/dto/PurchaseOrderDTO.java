package com.neo.api.order.dto;


import lombok.Data;

@Data
public class PurchaseOrderDTO {

    private String orderId;
    private String orderName;
    private double amount;
    private String status;

    public PurchaseOrderDTO(String orderId, String orderName, double amount, String status) {
        this.orderId = orderId;
        this.orderName = orderName;
        this.amount = amount;
        this.status = status;
    }
}
