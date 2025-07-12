package br.com.navdata.auth.service;

// JwtService.java
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.navdata.auth.entity.TokenEntity;
import br.com.navdata.auth.repository.TokenRepository;
import br.com.navdata.auth.response.TokenValidationResponse;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import javax.crypto.SecretKey;

@Service
public class JwtService {

	@Value("${jwt.secret}")
    private String jwtSecret;
	
	private final SecretKey secretKey;
	
	@Autowired
    private TokenRepository tokenRepository;
	
    private final long accessTokenValidity = 15 * 60 * 1000; // 15 min
    private final long refreshTokenValidity = 7 * 24 * 60 * 60 * 1000; // 7 dias
    
    public JwtService(SecretKey secretKey) {
        this.secretKey = secretKey;
    }

    public String generateAccessToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenValidity))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    public String generateRefreshToken() {
        // Pode ser UUID aleatório
        return UUID.randomUUID().toString();
    }
    
    public boolean isTokenValid(String token) {
    	Optional<TokenEntity> tokenOpt = tokenRepository.findByTokenAndValidTrueAndFimVigenciaAfter(token, LocalDateTime.now());

        if (tokenOpt.isEmpty()) {
            return false; // Token não encontrado, inválido ou expirado no banco
        }

        try {
            // Aqui valida a assinatura e validade do JWT
            Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
    
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
