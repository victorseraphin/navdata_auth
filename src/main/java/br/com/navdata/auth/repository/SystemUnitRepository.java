package br.com.navdata.auth.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.navdata.auth.entity.SystemUnitEntity;
public interface SystemUnitRepository extends JpaRepository<SystemUnitEntity, Integer> {

	boolean existsByDocumento(String documento);
	
	boolean existsByIdAndDeletedAtIsNull(Integer id);

	List<SystemUnitEntity> findAllByDeletedAtIsNull();
	
	Optional<SystemUnitEntity> findByIdAndDeletedAtIsNull(Integer id);
	

}
