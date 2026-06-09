package com.ai360.ecommerce.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * JWT UTILITY
 * ============
 * Tao va xac thuc JSON Web Token.
 *
 * JWT gom 3 phan ngan cach boi dau cham:
 * header.payload.signature
 *
 * Header: {"alg":"HS256","typ":"JWT"}
 * Payload: {"sub":"user@email.com","iat":...,"exp":...}
 * Signature: HMACSHA256(base64(header)+"."+base64(payload), secret)
 *
 * @Component: dang ky voi Spring DI container
 * @Value: doc gia tri tu application.properties
 */
@Component
public class JwtUtil {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.expiration}")
    private long jwtExpiration; // milliseconds

    // Lay SecretKey tu chuoi secret da cau hinh
    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(
            java.util.Base64.getEncoder().encodeToString(jwtSecret.getBytes())
        );
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Tao JWT token tu email nguoi dung
    public String generateToken(String email) {
        return Jwts.builder()
                .subject(email)                                // payload: email la subject
                .issuedAt(new Date())                          // thoi gian tao
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration)) // het han
                .signWith(getSigningKey())                     // ky bang secret key
                .compact();                                    // tra ve chuoi JWT
    }

    // Lay email tu token
    public String getEmailFromToken(String token) {
        return parseClaims(token).getSubject();
    }

    // Kiem tra token con hop le khong
    public boolean validateToken(String token) {
        try {
            parseClaims(token); // nem exception neu het han hoac sai chu ky
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // Parse token, lay Claims (payload)
    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
