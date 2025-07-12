package br.com.navdata.auth.controller;

// AuthController.java
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.navdata.auth.dto.LoginDTO;
import br.com.navdata.auth.dto.SystemUserDTO;
import br.com.navdata.auth.dto.TokenValidationDTO;
import br.com.navdata.auth.repository.TokenRepository;
import br.com.navdata.auth.response.AuthResponse;
import br.com.navdata.auth.response.TokenValidationResponse;
import br.com.navdata.auth.service.AuthService;
import br.com.navdata.auth.service.JwtService;
import br.com.navdata.auth.service.SystemUserService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	@Autowired
    private JwtService jwtService;

	@Autowired
    private SystemUserService systemUserService;
    
	@Autowired
    private AuthService authService;
    
    @Autowired
    private TokenRepository tokenRepository;
    

    // Aqui armazenamos refresh tokens em memória (trocar por DB ou Redis na prática)
    private final Map<String, String> refreshTokens = new ConcurrentHashMap<>();
    
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginDTO loginDTO, HttpServletResponse response) {
        AuthResponse result = authService.login(loginDTO, response);
        return ResponseEntity.ok(result);
    }//*/    

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) return ResponseEntity.status(401).body("Sem refresh token");

        String refreshToken = null;
        for (Cookie cookie : cookies) {
            if ("refreshToken".equals(cookie.getName())) {
                refreshToken = cookie.getValue();
                break;
            }
        }
        if (refreshToken == null || !refreshTokens.containsKey(refreshToken)) {
            return ResponseEntity.status(403).body("Refresh token inválido");
        }

        String username = refreshTokens.get(refreshToken);
        String newAccessToken = jwtService.generateAccessToken(username);

        return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("refreshToken".equals(cookie.getName())) {
                    refreshTokens.remove(cookie.getValue());
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    response.addCookie(cookie);
                }
            }
        }
        return ResponseEntity.ok(Map.of("message", "Logout efetuado"));
    }
    
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody SystemUserDTO request) {
    	systemUserService.criar(request);
        return ResponseEntity.ok("Usuário registrado com sucesso!");
    }
    
    @PostMapping("/validate")
    public ResponseEntity<TokenValidationResponse> validateToken(@RequestBody TokenValidationDTO dto) {
        boolean valido = jwtService.isTokenValid(dto.getToken());

        if (valido) {
            String username = jwtService.extractUsername(dto.getToken());
            return ResponseEntity.ok(new TokenValidationResponse(true, username));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new TokenValidationResponse(false, null));
        }
    }
    
    @PutMapping("/revoke")
    public ResponseEntity<Void> revokeToken(@RequestBody TokenValidationDTO request) {
        tokenRepository.findByTokenAndValidTrue(request.getToken())
            .ifPresent(token -> {
                token.setValid(false);
                tokenRepository.save(token);
            });
        return ResponseEntity.noContent().build();
    }
}

