package br.com.navdata.auth.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.navdata.auth.entity.TokenEntity;

public interface TokenRepository extends JpaRepository<TokenEntity, Long> {
    Optional<TokenEntity> findByTokenAndValidTrueAndFimVigenciaAfter(String token, LocalDateTime now);

    Optional<TokenEntity> findByTokenAndValidTrue(String token);    

    Optional<TokenEntity> findByToken(String token);
    
}
