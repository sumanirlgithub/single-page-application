package com.neo.api.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@Component
@Slf4j
public class LoggingFilter implements GlobalFilter, Ordered {

    //Implementing the filter() Method (Pre & Post Logging)
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        // Pre-Filter Execution (Before Passing to Microservices)
        log.info("Logging from global pre: {}",exchange.getRequest().getURI());

        //Calling the Next Filter in the Chain
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            // Post-Filter Execution (After Receiving Response from Microservices)
            log.info("Logging from global post: {}",exchange.getResponse().getStatusCode());
        }));
    }

    @Override
    public int getOrder() {  // to set execution priority (5)
        return 5;
    }
}