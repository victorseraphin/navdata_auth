package br.com.navdata.auth.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.navdata.auth.entity.TokenEntity;
import br.com.navdata.auth.exception.InvalidCredentialsException;
import br.com.navdata.auth.mapper.SystemUserMapper;
import br.com.navdata.auth.entity.RefreshTokenEntity;
import br.com.navdata.auth.entity.SystemEntity;
import br.com.navdata.auth.entity.SystemUnitEntity;
import br.com.navdata.auth.entity.SystemUserEntity;
import br.com.navdata.auth.repository.RefreshTokenRepository;
import br.com.navdata.auth.repository.SystemRepository;
import br.com.navdata.auth.repository.SystemUnitRepository;
import br.com.navdata.auth.repository.SystemUserRepository;
import br.com.navdata.auth.repository.TokenRepository;
import br.com.navdata.auth.request.LoginRequest;
import br.com.navdata.auth.request.SystemUserRequest;
import br.com.navdata.auth.response.AuthResponse;
import br.com.navdata.auth.response.SystemUserResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	@Autowired
	private JwtService jwtService;

	@Autowired
	private SystemRepository systemRepository;

	@Autowired
	private SystemUnitRepository systemUnitRepository;

	@Autowired
	private SystemUserRepository systemUserRepository;

	@Autowired
	private TokenRepository tokenRepository;

	@Autowired
	private RefreshTokenRepository refreshTokenRepository;

	@Autowired
	private SystemUserMapper mapper;

	private final BCryptPasswordEncoder passwordEncoder;

	public AuthService() {
		this.passwordEncoder = new BCryptPasswordEncoder();
	}

	public AuthResponse login(LoginRequest loginDTO, HttpServletResponse response, String systemName) {
		SystemUserEntity userEntity = findByEmail(loginDTO.getEmail());

		if (userEntity == null || !checkPassword(userEntity, loginDTO.getPassword())) {
			throw new InvalidCredentialsException("Usuário ou senha inválidos");
		}

		if (!"Y".equals(userEntity.getActive())) {
			throw new InvalidCredentialsException("Usuário está inativo");
		}

		// Busca o sistema pelo nome
		SystemEntity system = systemRepository.findFirstByNameAndDeletedAtIsNull(systemName);
		if (system == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sistema '" + systemName + "' não encontrado");
		}

		// Verifica se o usuário tem permissão para o sistema
		boolean temAcesso = userEntity.getSystems().stream().anyMatch(s -> s.getId().equals(system.getId()));

		if (!temAcesso) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN,
					"Usuário não tem acesso ao sistema '" + systemName + "'");
		}

		// Geração de tokens
		String accessToken = jwtService.generateAccessToken(userEntity.getEmail());
		String refreshToken = jwtService.generateRefreshToken();

		LocalDateTime inicio = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));
		LocalDateTime fim = inicio.plusHours(24);
		
		// Expiração daqui a 7 dias
		Instant expiryDate = Instant.now().plus(7, ChronoUnit.DAYS);
		
		RefreshTokenEntity refreshTokenEntity = new RefreshTokenEntity();
		refreshTokenEntity.setToken(refreshToken);
		refreshTokenEntity.setUserEmail(userEntity.getEmail());
		refreshTokenEntity.setExpiryDate(expiryDate);

		refreshTokenEntity.setSystemUnitId(userEntity.getSystemUnit().get(0).getId());
		refreshTokenEntity.setSystemUserId(userEntity.getId());
		refreshTokenEntity.setSystemId(system.getId());
		refreshTokenEntity.setSystemName(system.getName());
		refreshTokenEntity.setValid(true);

		refreshTokenEntity = refreshTokenRepository.save(refreshTokenEntity);

		TokenEntity tokenEntity = new TokenEntity(accessToken, userEntity.getEmail(), inicio, fim, true,
				userEntity.getSystemUnit().get(0).getId(), userEntity.getId(), system.getId(), system.getName(), refreshTokenEntity);
		tokenRepository.save(tokenEntity);

		// Cookie com refreshToken
		Cookie cookie = new Cookie("refreshToken", refreshToken);
		cookie.setHttpOnly(true);
		cookie.setPath("/");
		cookie.setMaxAge(7 * 24 * 60 * 60);
		cookie.setSecure(false); // só coloque true se estiver usando HTTPS
		response.addCookie(cookie);		

		

		return new AuthResponse(accessToken/*, userResponse*/);
	}

	public boolean checkPassword(SystemUserEntity user, String rawPassword) {
		return passwordEncoder.matches(rawPassword, user.getPassword());
	}

	public SystemUserEntity findByEmail(String email) {
		return systemUserRepository.findByEmail(email);
	}

	public SystemUserResponse registrar(SystemUserRequest request) {
		boolean isFirstUser = systemUserRepository.count() == 0;
		if (isFirstUser) {
			if (systemUserRepository.existsByEmailAndDeletedAtIsNull(request.getEmail())) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário já existe");
			}

			SystemUserEntity systemUserEntity = new SystemUserEntity();
			request.setPassword(passwordEncoder.encode(request.getPassword()));
			request.setActive("Y");

			// Cria automaticamente o System Auth/Core
			if (!systemUnitRepository.existsByDocumento("0000000000")) {
				SystemUnitEntity unit = new SystemUnitEntity();
				unit.setName("NavSystemCore");
				unit.setDocumento("0000000000");
				unit.setInscricao("0000000000");
				unit.setMatricula("0000000000");
				unit.setLocalizacao("Pirassununga");
				//unit.setSystems(entity.getSystems());
				systemUnitRepository.save(unit);
				systemUserEntity.setSystemUnit(Collections.singletonList(unit));
			}

			// Cria automaticamente o System Auth/Core
			if (!systemRepository.existsByNameAndDeletedAtIsNull("NavSystemCore")) {
				SystemEntity system = new SystemEntity();
				system.setName("NavSystemCore");
				system.setSystemUnit(systemUserEntity.getSystemUnit().get(0));
				systemRepository.save(system);
				systemUserEntity.setSystems(Collections.singletonList(system));
			}
			
			systemUserEntity.setIsMaster(true);
			mapper.createFromDTO(request, systemUserEntity);

			systemUserEntity = systemUserRepository.save(systemUserEntity);

			return mapper.toResponse(systemUserEntity);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					"Apenas o usuário master pode acessar esta funcionalidade.");
		}
	}
	
	public SystemUserResponse getAuthenticatedUser(String token) {
        TokenEntity tokenEntity = tokenRepository.findByTokenAndValidTrue(token)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token inválido ou expirado"));

        Integer userId = tokenEntity.getSystemUserId();
        SystemUserEntity user = systemUserRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado")); 

        return mapper.toResponse(user);
    }
	
	public AuthResponse refreshAccessToken(String refreshToken, Integer unitId) {
        if (refreshToken == null || refreshToken.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Refresh token não encontrado");
        }

        Optional<RefreshTokenEntity> tokenOpt = refreshTokenRepository.findByTokenAndValidTrueAndSystemUnitId(refreshToken, unitId);

        if (tokenOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Refresh token inválido");
        }

        RefreshTokenEntity refreshTokenEntity = tokenOpt.get();

        if (refreshTokenEntity.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(refreshTokenEntity); // remove token expirado
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Refresh token expirado");
        }

        String email = refreshTokenEntity.getUserEmail();

        String novoAccessToken = jwtService.generateAccessToken(email);
        LocalDateTime inicio = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));
		LocalDateTime fim = inicio.plusHours(24);
        
        TokenEntity tokenEntity = new TokenEntity(novoAccessToken, refreshTokenEntity.getUserEmail(), inicio, fim, true,
        		refreshTokenEntity.getSystemId(), refreshTokenEntity.getSystemUserId(), refreshTokenEntity.getSystemId(), refreshTokenEntity.getSystemName(), refreshTokenEntity);
		
        tokenRepository.save(tokenEntity);

        return new AuthResponse(novoAccessToken);
    }
	
	public void logout(String refreshTokenStr) {
        refreshTokenRepository.findByToken(refreshTokenStr).ifPresent(refreshToken -> {
            // Invalida o refresh token
            refreshToken.setValid(false);
            refreshToken.setExpiryDate(Instant.now());

            // Invalida todos os tokens associados
            if (refreshToken.getWsTokens() != null) {
                for (TokenEntity token : refreshToken.getWsTokens()) {
                    token.setValid(false);
                    token.setFimVigencia(LocalDateTime.now());
                }
            }

            refreshTokenRepository.save(refreshToken);
        });
    }
}
