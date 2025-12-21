package com.neo.payment.config;

import feign.Logger;
import org.springframework.cloud.openfeign.support.JsonFormWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    JsonFormWriter jsonFormWriter() {
        return new JsonFormWriter();
    }
}
