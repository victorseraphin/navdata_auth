package br.com.navdata.auth.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.navdata.auth.entity.SystemEntity;
import br.com.navdata.auth.entity.SystemProgramEntity;
public interface SystemProgramRepository extends JpaRepository<SystemProgramEntity, Integer> {

	//List<SystemProgramEntity> findAllByDeletedAtIsNull();
	
	//Optional<SystemProgramEntity> findByIdAndDeletedAtIsNull(Integer id);
	boolean existsByNameAndDeletedAtIsNullAndSystemUnit_Id(String name, Integer unitId);
	

	List<SystemProgramEntity> findAllBySystemUnit_IdAndDeletedAtIsNull(Integer unitId);
	
	Optional<SystemProgramEntity> findByIdAndDeletedAtIsNullAndSystemUnit_Id(Integer id, Integer unitId);	
	
	List<SystemProgramEntity> findBySystemUnit_IdAndDeletedAtIsNullAndSystem_Id(Integer unitId, Integer systemId);


}
