package br.com.navdata.auth.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.navdata.auth.entity.SystemUserEntity;

public interface SystemUserRepository extends JpaRepository<SystemUserEntity, Integer> {
	
	@EntityGraph(attributePaths = "systemUnit")
    SystemUserEntity findByEmail(String email);
    
    boolean existsByEmailAndDeletedAtIsNull(String email);
    
    List<SystemUserEntity> findFirstByIdAndDeletedAtIsNull(Integer systemUnitId);
    
    List<SystemUserEntity> findAllByDeletedAtIsNull();
    
	Optional<SystemUserEntity> findByIdAndDeletedAtIsNull(Integer id);


	List<SystemUserEntity> findAllBySystemUnit_IdAndDeletedAtIsNull(Integer unitId);
	
	Optional<SystemUserEntity> findByIdAndDeletedAtIsNullAndSystemUnit_Id(Integer id, Integer unitId);
}