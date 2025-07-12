package br.com.navdata.auth.service;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.navdata.auth.dto.LoginDTO;
import br.com.navdata.auth.entity.TokenEntity;
import br.com.navdata.auth.exception.InvalidCredentialsException;
import br.com.navdata.auth.entity.SystemUserEntity;
import br.com.navdata.auth.repository.SystemUserRepository;
import br.com.navdata.auth.repository.TokenRepository;
import br.com.navdata.auth.response.AuthResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
	
	@Autowired
    private JwtService jwtService;
	@Autowired
    private SystemUserRepository systemUserRepository;
	@Autowired
    private TokenRepository tokenRepository;
	
	private final BCryptPasswordEncoder passwordEncoder;

    public AuthService() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

	public AuthResponse login(LoginDTO loginDTO, HttpServletResponse response) {
	    SystemUserEntity userEntity = findByEmail(loginDTO.getEmail());

	    if (userEntity == null || !checkPassword(userEntity, loginDTO.getPassword())) {
	        throw new InvalidCredentialsException("Usuário ou senha inválidos");
	    }
	    
	    if (!"Y".equals(userEntity.getActive())) {
	        throw new InvalidCredentialsException("Usuário está inativo");
	    }
	    

	    String accessToken = jwtService.generateAccessToken(userEntity.getEmail());
	    String refreshToken = jwtService.generateRefreshToken();

	    // Salva token no banco com validade de 24h
	    LocalDateTime inicio = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));
	    LocalDateTime fim = inicio.plusHours(24);
	    TokenEntity tokenEntity = new TokenEntity(accessToken, userEntity.getEmail(), inicio, fim, true);
	    tokenRepository.save(tokenEntity);

	    // Cookie com refreshToken
	    Cookie cookie = new Cookie("refreshToken", refreshToken);
	    cookie.setHttpOnly(true);
	    cookie.setPath("/");
	    cookie.setMaxAge(7 * 24 * 60 * 60); // 7 dias
	    response.addCookie(cookie);

	    return new AuthResponse(accessToken);
	}

    public boolean checkPassword(SystemUserEntity user, String rawPassword) {
        return passwordEncoder.matches(rawPassword, user.getPassword());
    }

    public SystemUserEntity findByEmail(String email) {
        return systemUserRepository.findByEmail(email);
    }
}
