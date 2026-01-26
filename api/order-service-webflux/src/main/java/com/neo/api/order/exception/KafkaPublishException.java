package com.neo.api.order.exception;

public class KafkaPublishException extends RuntimeException {
    public KafkaPublishException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
