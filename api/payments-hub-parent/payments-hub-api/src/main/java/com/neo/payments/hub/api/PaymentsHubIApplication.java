package com.neo.payments.hub.api;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.neo.payments.hub")
@Slf4j
public class PaymentsHubIApplication {

    public static void main(String[] args) {
        log.info("Starting Payments Hub Application");
        SpringApplication.run(PaymentsHubIApplication.class, args);
    }
}