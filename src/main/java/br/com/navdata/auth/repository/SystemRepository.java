package br.com.navdata.auth.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.navdata.auth.entity.SystemEntity;

public interface SystemRepository extends JpaRepository<SystemEntity, Integer> {
	
	boolean existsByName(String name);
	
	boolean existsByIdAndDeletedAtIsNull(Integer id);
	
	boolean existsByNameAndDeletedAtIsNull(String name);

	List<SystemEntity> findAllByDeletedAtIsNull();
	
	Optional<SystemEntity> findByIdAndDeletedAtIsNull(Integer id);
	
	SystemEntity findFirstByNameAndDeletedAtIsNull(String name);	

	List<SystemEntity> findAllBySystemUnit_IdAndDeletedAtIsNull(Integer unitId);
	
	Optional<SystemEntity> findByIdAndDeletedAtIsNullAndSystemUnit_Id(Integer id, Integer unitId);	
	

}
