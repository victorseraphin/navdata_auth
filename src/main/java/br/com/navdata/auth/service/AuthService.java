package br.com.navdata.auth.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import br.com.navdata.auth.dto.LoginDTO;
import br.com.navdata.auth.entity.TokenEntity;
import br.com.navdata.auth.entity.UserEntity;
import br.com.navdata.auth.repository.TokenRepository;
import br.com.navdata.auth.repository.UserRepository;
import br.com.navdata.auth.response.AuthResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	@Autowired
    private AuthenticationManager authenticationManager;
	@Autowired
    private JwtService jwtService;
	@Autowired
    private UserService userService;
	@Autowired
    private TokenRepository tokenRepository;

	public AuthResponse login(LoginDTO loginDTO, HttpServletResponse response) {
	    UserEntity user = userService.findByUsername(loginDTO.getUsername());

	    if (user == null || !userService.checkPassword(user, loginDTO.getPassword())) {
	        throw new RuntimeException("Usuário ou senha inválidos");
	    }

	    String accessToken = jwtService.generateAccessToken(user.getUsername());
	    String refreshToken = jwtService.generateRefreshToken();

	    // Salva token no banco com validade de 24h
	    LocalDateTime inicio = LocalDateTime.now();
	    LocalDateTime fim = inicio.plusHours(24);
	    TokenEntity tokenEntity = new TokenEntity(accessToken, user.getUsername(), inicio, fim, true);
	    tokenRepository.save(tokenEntity);

	    // Cookie com refreshToken
	    Cookie cookie = new Cookie("refreshToken", refreshToken);
	    cookie.setHttpOnly(true);
	    cookie.setPath("/");
	    cookie.setMaxAge(7 * 24 * 60 * 60); // 7 dias
	    response.addCookie(cookie);

	    return new AuthResponse(accessToken);
	}
}
