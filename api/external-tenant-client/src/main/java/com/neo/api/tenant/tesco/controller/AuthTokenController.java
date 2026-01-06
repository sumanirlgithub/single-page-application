package com.neo.api.tenant.tesco.controller;

import com.neo.api.tenant.tesco.KeycloakTokenClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/auth")
public class AuthTokenController {

    private final KeycloakTokenClient keycloakTokenClient;

    /**
     * Endpoint to get Auth JWT token from Keycloak Server
     *
     */
    @PostMapping(value = "/token")
    public ResponseEntity<Mono<String>> getAccessToken(@RequestParam(name = "realm", required = true) String realm) {
        log.info("Received request for get auth token");
        return ResponseEntity.ok(keycloakTokenClient.getAccessToken(realm));
    }
}
