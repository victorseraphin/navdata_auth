package br.com.navdata.auth.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.navdata.auth.entity.SystemProgramEntity;
public interface SystemProgramRepository extends JpaRepository<SystemProgramEntity, Integer> {

	List<SystemProgramEntity> findAllByDeletedAtIsNull();
	
	Optional<SystemProgramEntity> findByIdAndDeletedAtIsNull(Integer id);

}
