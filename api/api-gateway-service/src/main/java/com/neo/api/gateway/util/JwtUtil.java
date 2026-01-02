package com.neo.api.gateway.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    /**
     * You need to generate JWT token from this secret and send that token through POSTMAN or client application.
     */
    @Value("${jwt.secret}")
    private String secret;

    /**
     * Validate and authenticate call using JWT token supplied by client application.
     * @param token
     * @return Claims
     */
    public Claims validateToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
