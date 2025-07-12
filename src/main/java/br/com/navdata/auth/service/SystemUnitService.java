package br.com.navdata.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.navdata.auth.dto.SystemUnitDTO;
import br.com.navdata.auth.entity.SystemUnitEntity;
import br.com.navdata.auth.entity.SystemUserEntity;
import br.com.navdata.auth.mapper.SystemUnitMapper;
import br.com.navdata.auth.repository.SystemUnitRepository;
import br.com.navdata.auth.repository.SystemUserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SystemUnitService {

	@Autowired
    private SystemUnitRepository systemUnitRepository;
	
	@Autowired
    private SystemUserRepository systemUserRepository;
	
	@Autowired
	private SystemUnitMapper mapper;

    public List<SystemUnitDTO> listarTodos() {
        return systemUnitRepository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    public SystemUnitDTO buscarPorId(Integer id) throws JsonProcessingException {
        SystemUnitEntity entity = systemUnitRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empresa com ID " + id + " não encontrada"));       
        
        return mapper.toDTO(entity);
    }

    public SystemUnitDTO criar(SystemUnitDTO systemDTO) {
    	SystemUnitEntity entity = mapper.toEntity(systemDTO);        	
        entity.setCreated_at(LocalDateTime.now());        
        entity = systemUnitRepository.save(entity);
        return mapper.toDTO(entity);
    }

    public SystemUnitDTO atualizar(Integer id, SystemUnitDTO dto) {
        return systemUnitRepository.findById(id).map(entity -> {
            mapper.updateFromDTO(dto, entity); // aplica os dados do DTO na entidade existente
            entity.setUpdated_at(LocalDateTime.now()); // atualiza a data de modificação
            entity = systemUnitRepository.save(entity); // salva a entidade atualizada
            return mapper.toDTO(entity); // converte para DTO de resposta
        }).orElseThrow(() -> new RuntimeException("Empresa não encontrada"));
    }

    public void deletar(Integer id) {
        SystemUnitEntity unit = systemUnitRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empresa não encontrada"));

        // Atualiza o campo deleted_at
        unit.setDeleted_at(LocalDateTime.now());
        systemUnitRepository.save(unit); // salva alteração no system_unit

        // Inativa os usuários vinculados
        List<SystemUserEntity> listUser = systemUserRepository.findBySystemUnitId(id);
        for (SystemUserEntity userEntity : listUser) {
        	userEntity.setActive("N"); // ou setStatus("INATIVO") se preferir
            userEntity.setDeleted_at(LocalDateTime.now());
        }
        systemUserRepository.saveAll(listUser);//*/
    }
}
