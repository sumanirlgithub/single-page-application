package com.neo.payment.common;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Component
public class JwtTokenUtil {

    private String secretKey = "your_private_secret_key"; // Securely store this key
    String encodedString = Base64.getEncoder().encodeToString(secretKey.getBytes());
    byte[] decodedKey = Base64.getDecoder().decode(encodedString);
    SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");

    public Claims validateToken(String token) {
        return Jwts.parser().verifyWith(originalKey).build().parseSignedClaims(token).getPayload();
    }
}
