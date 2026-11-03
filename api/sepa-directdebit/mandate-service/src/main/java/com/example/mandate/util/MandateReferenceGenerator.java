package com.example.mandate.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class MandateReferenceGenerator {

    public String generate() {

        // 33 characters, including the M prefix.
        return "M" + UUID.randomUUID()
                .toString()
                .replace("-", "")
                .toUpperCase();
    }
}