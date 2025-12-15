package com.neo.api.order.model;

import lombok.Data;

@Data
public class OrderItemModel {

    private String itemId;
    private String itemCode;
    private String itemName;
    private Integer quantity;
    private Double unitPrice;

}
