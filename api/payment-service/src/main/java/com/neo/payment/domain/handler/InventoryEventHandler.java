package com.neo.payment.domain.handler;

import com.neo.api.common.order.event.InventoryReservedEvent;

import java.net.SocketException;

public interface InventoryEventHandler {

    void onEvent(InventoryReservedEvent event) throws SocketException;
    void onRequeueEvent(InventoryReservedEvent event);
}