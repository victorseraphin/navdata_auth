package br.com.navdata.auth.repository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.navdata.auth.entity.RefreshTokenEntity;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {
	
	Optional<RefreshTokenEntity> findByTokenAndValidTrueAndSystemUnitId(String token, Integer unitId);
	
    Optional<RefreshTokenEntity> findByTokenAndValidTrueAndExpiryDateAfter(String token, Instant now);
    

    Optional<RefreshTokenEntity> findByToken(String token);
	
}
