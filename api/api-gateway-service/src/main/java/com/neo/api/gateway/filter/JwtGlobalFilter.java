package com.neo.api.gateway.filter;

import com.neo.api.gateway.util.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@Slf4j
public class JwtGlobalFilter implements GlobalFilter, Ordered {

    private final JwtUtil jwtUtil;

    // Public endpoints (no JWT required)
    private static final List<String> EXCLUDED_PATHS = List.of(
            "/auth",
            "/actuator",
            "/swagger",
            "/v3/api-docs"
    );

    public JwtGlobalFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();

        if (isExcluded(path)) {
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return unauthorized(exchange, "Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);

        Claims claims;
        try {
            claims = jwtUtil.validateToken(token);
        } catch (Exception e) {
            return unauthorized(exchange, "Invalid or expired JWT");
        }

        // 👉 Propagate user info to downstream services
        ServerHttpRequest mutatedRequest = exchange.getRequest()
                .mutate()
                .header("X-User-Id", claims.getSubject())
                .header("X-User-Roles", claims.get("roles", String.class))
                .build();

        return chain.filter(exchange.mutate().request(mutatedRequest).build());
    }

    private boolean isExcluded(String path) {
        return EXCLUDED_PATHS.stream().anyMatch(path::startsWith);
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange, String message) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }

    @Override
    public int getOrder() {
        return -1; // run before other filters
    }
}
