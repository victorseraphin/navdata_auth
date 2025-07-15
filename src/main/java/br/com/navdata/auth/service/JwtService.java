package br.com.navdata.auth.service;

// JwtService.java
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.JwtException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.navdata.auth.entity.TokenEntity;
import br.com.navdata.auth.repository.TokenRepository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.crypto.SecretKey;

@Service
public class JwtService {

	@Value("${jwt.secret}")
    private String jwtSecret;
	
	private final SecretKey secretKey;
	
	@Autowired
    private TokenRepository tokenRepository;
	
    private final long accessTokenValidity = TimeUnit.HOURS.toMillis(24); //15 * 60 * 1000; // 15 min
    
    public JwtService(SecretKey secretKey) {
        this.secretKey = secretKey;
    }

    public String generateAccessToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenValidity))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    public String generateRefreshToken() {
        // Pode ser UUID aleatório
        return UUID.randomUUID().toString();
    }
    
    public boolean isTokenValid(String token, String systemName) {
    	Optional<TokenEntity> tokenOpt = tokenRepository.findByTokenAndValidTrueAndFimVigenciaAfter(token, LocalDateTime.now());

        if (tokenOpt.isEmpty()) {
            return false; // Token não encontrado, inválido ou expirado no banco
        }
        
        TokenEntity tokenEntity = tokenOpt.get();

        // Verifica se o token pertence ao sistema correto
        if (!systemName.equalsIgnoreCase(tokenEntity.getSystemName())) {
            return false; // Token não pertence ao sistema informado
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
