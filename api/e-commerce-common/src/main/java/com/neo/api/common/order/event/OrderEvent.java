package com.neo.api.common.order.event;

import com.neo.api.common.enums.OrderEventType;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

public record OrderEvent(Long eventId, OrderEventType eventType, String payload, LocalDateTime createdDate) implements Serializable {
    public static final String ORDER_TOPIC = "ORDER-TOPIC";
    public static final String ORDER_REQUEUE_TOPIC = "ORDER.RE";
    public static final int ORDER_TOPIC_PARTITION = 3;

    public static final String ORDER_EVENT_HANDLER_GROUP_ID = "ORDER-HANDLER";

    public static final String ORDER_STATUS_GROUP_ID_PREFIX = "ORDER-STATUS-";
    public static final String ORDER_LOG_GROUP_ID_PREFIX = "ORDER-LOG-";

}