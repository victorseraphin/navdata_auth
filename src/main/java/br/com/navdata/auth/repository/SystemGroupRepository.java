package br.com.navdata.auth.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.navdata.auth.entity.SystemGroupEntity;
public interface SystemGroupRepository extends JpaRepository<SystemGroupEntity, Integer> {

	//List<SystemGroupEntity> findAllByDeletedAtIsNull();
	
	//Optional<SystemGroupEntity> findByIdAndDeletedAtIsNull(Integer id);
	boolean existsByNameAndDeletedAtIsNullAndSystemUnit_Id(String name, Integer unitId);
	

	List<SystemGroupEntity> findAllBySystemUnit_IdAndDeletedAtIsNull(Integer unitId);
	
	Optional<SystemGroupEntity> findByIdAndDeletedAtIsNullAndSystemUnit_Id(Integer id, Integer unitId);	

}
