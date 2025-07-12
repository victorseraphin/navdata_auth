package br.com.navdata.auth.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class JwtUtil {

	@Value("${jwt.secret}")
    private String jwtSecret;
    private final long accessTokenValidity = 15 * 60 * 1000; // 15 min

    public String generateAccessToken(String username) {
        return Jwts.builder()
            .setSubject(username)
            .setExpiration(new Date(System.currentTimeMillis() + accessTokenValidity))
            .signWith(SignatureAlgorithm.HS512, jwtSecret.getBytes())
            .compact();
    }

    public String generateRefreshToken() {
        return UUID.randomUUID().toString();
    }

    public Claims extractClaims(String token) {
        return Jwts.parser()
            .setSigningKey(jwtSecret.getBytes())
            .parseClaimsJws(token)
            .getBody();
    }

    public boolean validateToken(String token) {
        try {
            extractClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        return extractClaims(token).getSubject();
    }
}
