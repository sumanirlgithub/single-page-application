package com.neo.payment.domain.handler;

import com.neo.api.common.order.event.OrderEvent;

import java.net.SocketException;

public interface OrderEventHandler {

    void onEvent(OrderEvent event) throws SocketException;
    void onRequeueEvent(OrderEvent event);
    void onEvent(com.neo.api.common.avro.model.generated.OrderAvroEvent event) throws SocketException;
    void onRequeueEvent(com.neo.api.common.avro.model.generated.OrderAvroEvent event);
}