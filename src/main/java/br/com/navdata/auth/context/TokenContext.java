package br.com.navdata.auth.context;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import br.com.navdata.auth.entity.TokenEntity;
import br.com.navdata.auth.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class TokenContext {

    private final TokenRepository tokenRepository;

    public TokenContext(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public TokenEntity getTokenEntity(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token não fornecido");
        }

        String token = authHeader.substring(7);

        return tokenRepository
            .findByTokenAndValidTrueAndFimVigenciaAfter(token, LocalDateTime.now())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token inválido ou expirado"));
    }

    public Integer getUnitId(HttpServletRequest request) {
        return getTokenEntity(request).getSystemUnitId();
    }

    public Integer getUserId(HttpServletRequest request) {
        return getTokenEntity(request).getSystemUserId();
    }

    public Integer getSystemId(HttpServletRequest request) {
        return getTokenEntity(request).getSystemId();
    }

    public String getSystemName(HttpServletRequest request) {
        return getTokenEntity(request).getSystemName();
    }
}
