package com.neo.api.order.exception;

import com.neo.api.order.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


import java.nio.file.AccessDeniedException;
import java.time.Instant;
import java.util.concurrent.TimeoutException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleNotFound(
            EmptyResultDataAccessException ex,
            ServerWebExchange exchange
    ) {
        return Mono.just(
                ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(error("NOT_FOUND", ex.getMessage(), exchange))
        );
    }

    @ExceptionHandler(WebClientResponseException.Unauthorized.class)
    public Mono<ResponseEntity<ErrorResponse>> handleUnauthorized(
            WebClientResponseException.Unauthorized ex,
            ServerWebExchange exchange
    ) {
        return Mono.just(
                ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(error("UNAUTHORIZED", "Authentication failed", exchange))
        );
    }

    @ExceptionHandler(TimeoutException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleTimeout(
            TimeoutException ex,
            ServerWebExchange exchange
    ) {
        return Mono.just(
                ResponseEntity.status(HttpStatus.ACCEPTED)
                        .body(error("PROCESSING", "Request is still being processed", exchange))
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleBadRequest(
            IllegalArgumentException ex,
            ServerWebExchange exchange
    ) {
        return Mono.just(
                ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(error("BAD_REQUEST", ex.getMessage(), exchange))
        );
    }

    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<ErrorResponse>> handleGeneric(
            Exception ex,
            ServerWebExchange exchange
    ) {
        return Mono.just(
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(error("INTERNAL_ERROR", "Unexpected error occurred", exchange))
        );
    }

    private ErrorResponse error(
            String code,
            String message,
            ServerWebExchange exchange
    ) {
        return new ErrorResponse(
                code,
                message,
                Instant.now(),
                exchange.getRequest().getPath().value()
        );
    }
}
