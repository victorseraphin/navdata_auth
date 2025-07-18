package br.com.navdata.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import br.com.navdata.auth.entity.SystemEntity;
import br.com.navdata.auth.entity.SystemGroupEntity;
import br.com.navdata.auth.entity.SystemUnitEntity;
import br.com.navdata.auth.mapper.SystemGroupMapper;
import br.com.navdata.auth.repository.SystemGroupRepository;
import br.com.navdata.auth.repository.SystemRepository;
import br.com.navdata.auth.repository.SystemUnitRepository;
import br.com.navdata.auth.request.SystemGroupRequest;
import br.com.navdata.auth.response.SystemGroupResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SystemGroupService {

	@Autowired
    private SystemGroupRepository systemGroupRepository;
	
	@Autowired
    private SystemUnitRepository systemUnitRepository;
	
	@Autowired
    private SystemRepository systemRepository;
	
	@Autowired
	private SystemGroupMapper mapper;
	
	public List<SystemGroupResponse> listarTodos(Integer unitId) {		
        return systemGroupRepository.findAllBySystemUnit_IdAndDeletedAtIsNull(unitId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public SystemGroupResponse buscarPorId(Integer id, Integer unitId) throws JsonProcessingException {
    	SystemGroupEntity entity = systemGroupRepository.findByIdAndDeletedAtIsNullAndSystemUnit_Id(id, unitId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sistema com ID " + id + " não encontrada"));      
        
        return mapper.toResponse(entity);
    }    

    public SystemGroupResponse criar(SystemGroupRequest request, Integer unitId) {

        if (systemGroupRepository.existsByNameAndDeletedAtIsNullAndSystemUnit_Id(request.getName(), unitId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sistema já existe");
        }
        if (request.getSystemUnitId() != unitId) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Problema de validade de Empresa. Consulte o Administrador do Sistema!");
        }
        
        SystemEntity system = systemRepository.findById(request.getSystemId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sistema não encontrado"));
        
        SystemGroupEntity entity = new SystemGroupEntity();      
        entity.setSystem(system);
        
        Optional<SystemUnitEntity> optionalUnit = systemUnitRepository.findByIdAndDeletedAtIsNull(unitId);
              
        if (optionalUnit.isPresent()) {
        	entity.setSystemUnit(optionalUnit.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "SystemUnitEntity não encontrada com id: " + request.getSystemUnitId());
        }
        mapper.createFromDTO(request, entity);

        return mapper.toResponse(systemGroupRepository.save(entity));
    }

    public SystemGroupResponse atualizar(Integer id, SystemGroupRequest request, Integer unitId) {
        return systemGroupRepository.findByIdAndDeletedAtIsNullAndSystemUnit_Id(id, unitId).map(entity -> { 

            if (request.getSystemUnitId() != unitId) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Problema de validade de Empresa. Consulte o Administrador do Sistema!");
            }
            
            SystemEntity system = systemRepository.findById(request.getSystemId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sistema não encontrado"));
            entity.setSystem(system);
            mapper.updateFromDTO(request, entity);
            entity.setUpdatedAt(LocalDateTime.now()); 
            entity = systemGroupRepository.save(entity);
            return mapper.toResponse(entity); 
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sistema não encontrado"));
    }

    public void deletar(Integer id, Integer unitId) {
        SystemGroupEntity unitEntity = systemGroupRepository.findByIdAndDeletedAtIsNullAndSystemUnit_Id(id, unitId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sistema não encontrado"));
        
        /*if (unitEntity.getSystemUnit().getId() != unitId) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Problema de validade de Empresa. Consulte o Administrador do Sistema!");
        }*/
        
        unitEntity.setDeletedAt(LocalDateTime.now());
        systemGroupRepository.save(unitEntity); 
        
    }
    

    
}
