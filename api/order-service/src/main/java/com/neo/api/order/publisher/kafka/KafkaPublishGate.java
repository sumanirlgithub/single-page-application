package com.neo.api.order.publisher.kafka;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class KafkaPublishGate {

    private final AtomicInteger failures = new AtomicInteger(0);
    private volatile boolean open = false;

    private static final int FAILURE_THRESHOLD = 5;

    public boolean allowSend() {
        return !open;
    }

    public void onSuccess() {
        failures.set(0);
        open = false;
    }

    public void onFailure() {
        if (failures.incrementAndGet() >= FAILURE_THRESHOLD) {
            open = true;
        }
    }
}
