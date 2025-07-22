package br.com.navdata.auth.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import br.com.navdata.auth.context.TokenContext;
import br.com.navdata.auth.entity.RefreshTokenEntity;
import br.com.navdata.auth.entity.TokenEntity;
import br.com.navdata.auth.repository.RefreshTokenRepository;
import br.com.navdata.auth.repository.TokenRepository;
import br.com.navdata.auth.request.LoginRequest;
import br.com.navdata.auth.request.AuthUserRequest;
import br.com.navdata.auth.request.TokenValidationRequest;
import br.com.navdata.auth.response.AuthResponse;
import br.com.navdata.auth.response.AuthUserResponse;
import br.com.navdata.auth.response.TokenValidationResponse;
import br.com.navdata.auth.service.AuthService;
import br.com.navdata.auth.service.JwtService;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	@Autowired
	private JwtService jwtService;

	@Autowired
	private AuthService authService;

	@Autowired
	private TokenRepository tokenRepository;

	@Autowired
	private RefreshTokenRepository refreshTokenRepository;

	// Aqui armazenamos refresh tokens em memória (trocar por DB ou Redis na
	// prática)
	private final Map<String, String> refreshTokens = new ConcurrentHashMap<>();

	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request, HttpServletResponse response, @RequestHeader("X-System-Name") String systemName) {
		AuthResponse result = authService.login(request, response, systemName);
		return ResponseEntity.ok(result);
	}// */

	@PostMapping("/refresh-token")
	public ResponseEntity<?> refreshToken(@CookieValue(value = "refreshToken", required = false) String refreshToken, HttpServletRequest request, HttpServletResponse response) {

		if (refreshToken == null) {
	        clearRefreshTokenCookie(response); // limpa o cookie
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Token não fornecido");
	    }
		
		RefreshTokenEntity refreshEntity = refreshTokenRepository
	            .findByTokenAndValidTrueAndExpiryDateAfter(refreshToken, Instant.now())
	            .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token inválido ou expirado"));
	
		AuthResponse authResponse = authService.refreshAccessToken(refreshToken, refreshEntity.getSystemUnitId());
		return ResponseEntity.ok(authResponse);		
	}
	
	private void clearRefreshTokenCookie(HttpServletResponse response) {	    
		// Cookie com refreshToken
		Cookie cookie = new Cookie("refreshToken", null);
		cookie.setHttpOnly(true);
		cookie.setPath("/");
		cookie.setMaxAge(0);
		cookie.setSecure(false); // só coloque true se estiver usando HTTPS
		response.addCookie(cookie);	
	}

	@PostMapping("/logout")
	public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {
	    String refreshToken = extractRefreshToken(request);

	    authService.logout(refreshToken);

	    // Limpa o cookie do refreshToken no navegador
	    Cookie cookie = new Cookie("refreshToken", null);
	    cookie.setHttpOnly(true);
	    cookie.setSecure(false); // ou false se não estiver usando HTTPS
	    cookie.setPath("/");
	    cookie.setMaxAge(0); // Expira imediatamente
	    //cookie.setDomain("seu-dominio.com"); // Opcional: coloque o domínio correto

	    response.addCookie(cookie);

	    return ResponseEntity.noContent().build();
	}

	private String extractRefreshToken(HttpServletRequest request) {
	    if (request.getCookies() != null) {
	        for (Cookie cookie : request.getCookies()) {
	            if ("refreshToken".equals(cookie.getName())) {
	                return cookie.getValue();
	            }
	        }
	    }
	    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Refresh token não fornecido");
	}

	@PostMapping("/register")
	public ResponseEntity<AuthUserResponse> register(@RequestBody AuthUserRequest request) {
		AuthUserResponse criado = authService.registrar(request);
		return ResponseEntity.ok(criado);
	}// */

	@PostMapping("/validate")
	public ResponseEntity<TokenValidationResponse> validateToken(@RequestBody TokenValidationRequest request,
			@RequestHeader("X-System-Name") String systemName) {
		boolean valido = jwtService.isTokenValid(request.getToken(), systemName);

		if (valido) {
			String username = jwtService.extractUsername(request.getToken());
			return ResponseEntity.ok(new TokenValidationResponse(true, username));
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new TokenValidationResponse(false, null));
		}
	}

	@PutMapping("/revoke")
	public ResponseEntity<Void> revokeToken(@RequestBody TokenValidationRequest request) {
		tokenRepository.findByTokenAndValidTrue(request.getToken()).ifPresent(token -> {
			token.setValid(false);
			tokenRepository.save(token);
		});
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/me")
	public ResponseEntity<?> me(@RequestHeader("Authorization") String authHeader) {
		String token = authHeader.replace("Bearer ", "").trim();
		AuthUserResponse user = authService.getAuthenticatedUser(token);
		return ResponseEntity.ok(user);

	}
}
