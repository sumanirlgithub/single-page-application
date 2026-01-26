package com.neo.api.order.remote.inventory.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryItemModel {

    private String skuCode;

    private Long quantity;
}
