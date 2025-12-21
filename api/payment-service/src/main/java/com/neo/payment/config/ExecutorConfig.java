package com.neo.payment.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ExecutorConfig {

    @Value("${citi-connect.payment-status.thread-pool-size:5}")
    private int numberOfThreads;

    @Bean
    public ExecutorService paymentStatusExecutor() {
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
        return executor;
    }
}
