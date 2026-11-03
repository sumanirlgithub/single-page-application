package com.example.mandate.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

@Component
public class RequestHashGenerator {

    private final ObjectMapper objectMapper;

    public RequestHashGenerator(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String generate(Object request) {

        try {
            String json = objectMapper.writeValueAsString(request);

            MessageDigest digest =
                    MessageDigest.getInstance("SHA-256");

            byte[] hash = digest.digest(
                    json.getBytes(StandardCharsets.UTF_8)
            );

            return HexFormat.of().formatHex(hash);

        } catch (JsonProcessingException |
                 NoSuchAlgorithmException ex) {

            throw new IllegalStateException(
                    "Unable to generate request hash", ex
            );
        }
    }
}