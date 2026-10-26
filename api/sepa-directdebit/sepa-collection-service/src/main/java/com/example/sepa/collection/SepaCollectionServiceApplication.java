package com.example.sepa.collection;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SepaCollectionServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(
                SepaCollectionServiceApplication.class,
                args);
    }
}