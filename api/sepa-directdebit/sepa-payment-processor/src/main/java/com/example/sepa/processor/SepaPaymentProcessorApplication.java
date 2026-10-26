package com.example.sepa.processor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SepaPaymentProcessorApplication {

    public static void main(String[] args) {
        SpringApplication.run(
                SepaPaymentProcessorApplication.class,
                args);
    }
}