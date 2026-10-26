package com.example.sepa.processor.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClient;

@Configuration
public class MandateServiceClientConfig {

    private static final Logger log =
            LoggerFactory.getLogger(MandateServiceClientConfig.class);

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
}