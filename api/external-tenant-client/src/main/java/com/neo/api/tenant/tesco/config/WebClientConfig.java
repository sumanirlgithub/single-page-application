package com.neo.api.tenant.tesco.config;

import com.neo.api.tenant.util.Base64Util;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${keycloak.base-url:test}")
    private String keycloakBaseUrl;

    @Value("${keycloak.client-id:test}")
    private String clientId;

    @Value("${keycloak.client-secret:test}")
    private String clientSecret;

    /**
     * Configuration for webclient
     *
     * @return WebClient
     */
    @Bean
    public WebClient webClient() {

        String basicAuth = Base64Util.encode(clientId + ":" + clientSecret);

        return WebClient.builder()
                .baseUrl(keycloakBaseUrl)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Basic " + basicAuth)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .build();
    }
}
