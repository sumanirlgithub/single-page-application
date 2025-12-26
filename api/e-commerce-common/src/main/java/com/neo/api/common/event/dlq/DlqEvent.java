package com.neo.api.common.event.dlq;

import com.neo.api.common.enums.OrderEventType;
import com.neo.api.common.order.event.OrderEvent;

import java.io.Serializable;
import java.time.LocalDateTime;

public record DlqEvent(OrderEvent event, String reason, LocalDateTime createdDate) implements Serializable {

}