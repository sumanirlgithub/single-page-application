package com.neo.api.gateway.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.util.Date;
import java.security.Key;

public class JwtTokenGenerator {
    public static void main(String[] args) {

        String secret = "mySecretKeyForJwtValidation123456"; // same as Gateway

        Key key = Keys.hmacShaKeyFor(secret.getBytes());

        String jwt = Jwts.builder()
                .setSubject("user1")
                .claim("roles", "ROLE_USER")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600_000)) // 1 hour
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        // A proper JWT has 3 parts separated by dots (.): header.payload.signature
        // Use this token when sending a request through POSTMAN
        // Authorization -> Type: Bearer Token -> Token <jwt_token
        System.out.println("JWT Token: " + jwt);
    }
}