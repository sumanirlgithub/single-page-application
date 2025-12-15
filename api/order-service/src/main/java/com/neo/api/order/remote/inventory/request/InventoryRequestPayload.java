package com.neo.api.order.remote.inventory.request;

import lombok.Data;

import java.util.List;

@Data
public class InventoryRequestPayload {

    private List<InventoryItemModel> items;

}
