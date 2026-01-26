package com.neo.api.order.dto;

import java.time.Instant;

public record ErrorResponse(
        String code,
        String message,
        Instant timestamp,
        String path
) {
}
