package com.neo.payments.hub.api.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClient;

@Configuration
@Slf4j
public class RestClientConfig {

    @Bean
    public RestClient customerRestClient(
            RestClient.Builder builder,
            @Value("${customer-service.base-url}") String baseUrl,
            @Value("${customer-service.username}") String username,
            @Value("${customer-service.password}") String password) {

        return builder
                .baseUrl(baseUrl)
                .defaultHeaders(headers ->
                        headers.setBasicAuth(username, password))
                .build();
    }

    @Bean
    public RestClient mandateRestClient(
            RestClient.Builder builder,
            @Value("${mandate-service.base-url}") String baseUrl,
            @Value("${mandate-service.username}") String username,
            @Value("${mandate-service.password}") String password) {

        log.info("Mandate Service URL: {}", baseUrl);
        log.info("Mandate Service username configured: {}",
                StringUtils.hasText(username));
        log.info("Mandate Service password configured: {}",
                StringUtils.hasText(password));

        return builder
                .baseUrl(baseUrl)
                .defaultHeaders(headers ->
                        headers.setBasicAuth(username, password))
                .build();
    }

    @Bean
    public RestClient bankRestClient(
            RestClient.Builder builder,
            @Value("${bank-submission.api.base-url}") String baseUrl
            //@Value("${customer-service.username}") String username,
            //@Value("${customer-service.password}") String password
    ) {

        return builder
                .baseUrl(baseUrl)
                //.defaultHeaders(headers ->
                //        headers.setBasicAuth(username, password))
                .build();
    }


}




