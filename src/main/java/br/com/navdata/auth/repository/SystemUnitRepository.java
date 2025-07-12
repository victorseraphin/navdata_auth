package br.com.navdata.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.navdata.auth.entity.SystemUnitEntity;
public interface SystemUnitRepository extends JpaRepository<SystemUnitEntity, Integer> {
	
	@Query(value = "SELECT MAX(e.id) FROM system_unit e", nativeQuery = true)
	Integer findMaxId();
}
