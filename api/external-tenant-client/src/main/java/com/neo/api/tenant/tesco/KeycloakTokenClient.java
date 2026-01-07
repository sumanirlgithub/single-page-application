package com.neo.api.tenant.tesco;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Optional;

@Builder
@RequiredArgsConstructor
@Service
@Slf4j
public class KeycloakTokenClient {

    private final WebClient webClient;

    public Mono<String> getAccessToken(String realm) {

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "client_credentials");

        return webClient.post()
                .uri("/realms/{realm}/protocol/openid-connect/token", realm)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(TokenResponse.class)
                .map(resp -> {
                    String token = resp.getAccessToken();
                    if (token == null) {
                        throw new IllegalStateException("Keycloak returned null access_token");
                    }
                    return token;
                });


    }

}

