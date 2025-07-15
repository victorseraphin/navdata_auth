package br.com.navdata.auth.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.navdata.auth.entity.TokenEntity;
import br.com.navdata.auth.exception.InvalidCredentialsException;
import br.com.navdata.auth.mapper.SystemUserMapper;
import br.com.navdata.auth.entity.SystemEntity;
import br.com.navdata.auth.entity.SystemUnitEntity;
import br.com.navdata.auth.entity.SystemUserEntity;
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

		TokenEntity tokenEntity = new TokenEntity(accessToken, userEntity.getEmail(), inicio, fim, true,
				userEntity.getSystemUnit().getId(), userEntity.getId(), system.getId(), system.getName());
		tokenRepository.save(tokenEntity);

		// Cookie com refreshToken
		Cookie cookie = new Cookie("refreshToken", refreshToken);
		cookie.setHttpOnly(true);
		cookie.setPath("/");
		cookie.setMaxAge(7 * 24 * 60 * 60);
		response.addCookie(cookie);

		return new AuthResponse(accessToken);
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

			SystemUserEntity entity = new SystemUserEntity();
			request.setPassword(passwordEncoder.encode(request.getPassword()));
			request.setActive("Y");
			mapper.createFromDTO(request, entity);

			// Cria automaticamente o System Auth/Core
			if (!systemUnitRepository.existsByDocumento("0000000000")) {
				SystemUnitEntity unit = new SystemUnitEntity();
				unit.setName("NavSystemCore");
				unit.setDocumento("0000000000");
				unit.setInscricao("0000000000");
				unit.setMatricula("0000000000");
				unit.setLocalizacao("Pirassununga");
				systemUnitRepository.save(unit);
				entity.setSystemUnit(unit);
			}

			// Cria automaticamente o System Auth/Core
			if (!systemRepository.existsByNameAndDeletedAtIsNull("System Core")) {
				SystemEntity system = new SystemEntity();
				system.setName("NavSystemCore");
				systemRepository.save(system);
				entity.setSystems(Collections.singletonList(system));
			}
			entity.setIsMaster(true);

			entity = systemUserRepository.save(entity);

			return mapper.toResponse(entity);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					"Apenas o usuário master pode acessar esta funcionalidade.");
		}
	}
}
