package br.com.navdata.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.navdata.auth.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    UserEntity findByUsername(String username);
    boolean existsByUsername(String username);
    //boolean existsByEmail(String email);
}