package br.com.navdata.auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.navdata.auth.entity.SystemUserEntity;

public interface SystemUserRepository extends JpaRepository<SystemUserEntity, Integer> {
    SystemUserEntity findByEmail(String email);
    boolean existsByEmail(String email);
    List<SystemUserEntity> findBySystemUnitId(Integer systemUnitId);
}