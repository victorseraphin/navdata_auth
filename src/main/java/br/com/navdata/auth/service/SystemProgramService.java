package br.com.navdata.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import br.com.navdata.auth.entity.SystemEntity;
import br.com.navdata.auth.entity.SystemProgramEntity;
import br.com.navdata.auth.entity.SystemUnitEntity;
import br.com.navdata.auth.mapper.SystemMapper;
import br.com.navdata.auth.mapper.SystemProgramMapper;
import br.com.navdata.auth.repository.SystemProgramRepository;
import br.com.navdata.auth.repository.SystemRepository;
import br.com.navdata.auth.repository.SystemUnitRepository;
import br.com.navdata.auth.request.SystemProgramRequest;
import br.com.navdata.auth.response.SystemProgramResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SystemProgramService {

	@Autowired
    private SystemProgramRepository systemProgramRepository;
	
	@Autowired
    private SystemUnitRepository systemUnitRepository;
	
	@Autowired
    private SystemRepository systemRepository;
	
	@Autowired
	private SystemProgramMapper mapper;
	
	public List<SystemProgramResponse> listarTodos(Integer unitId) {
		
        return systemProgramRepository.findAllBySystemUnit_IdAndDeletedAtIsNull(unitId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public SystemProgramResponse buscarPorId(Integer id, Integer unitId) throws JsonProcessingException {
    	SystemProgramEntity entity = systemProgramRepository.findByIdAndDeletedAtIsNullAndSystemUnit_Id(id, unitId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sistema com ID " + id + " não encontrada"));      
        
        return mapper.toResponse(entity);
    }    

    public SystemProgramResponse criar(SystemProgramRequest request, Integer unitId) {

        if (systemProgramRepository.existsByNameAndDeletedAtIsNullAndSystemUnit_Id(request.getName(), unitId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sistema já existe");
        }
        if (request.getSystemUnit().getId() != unitId) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Problema de validade de Empresa. Consulte o Administrador do Sistema!");
        }
        
        SystemEntity system = systemRepository.findById(request.getSystemId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sistema não encontrado"));
        
        SystemProgramEntity entity = new SystemProgramEntity();      
        entity.setSystem(system);
        
        /*Optional<SystemUnitEntity> optionalUnit = systemUnitRepository.findByIdAndDeletedAtIsNull(unitId);
              
        if (optionalUnit.isPresent()) {
        	entity.setSystemUnit(optionalUnit);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "SystemUnitEntity não encontrada com id: " + request.getSystemUnit().getId());
        }*/
        mapper.createFromDTO(request, entity);

        return mapper.toResponse(systemProgramRepository.save(entity));
    }

    public SystemProgramResponse atualizar(Integer id, SystemProgramRequest request, Integer unitId) {
        return systemProgramRepository.findByIdAndDeletedAtIsNullAndSystemUnit_Id(id, unitId).map(entity -> { 

            if (request.getSystemUnit().getId() != unitId) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Problema de validade de Empresa. Consulte o Administrador do Sistema!");
            }
            
            SystemEntity system = systemRepository.findById(request.getSystemId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sistema não encontrado"));
            entity.setSystem(system);
            mapper.updateFromDTO(request, entity);
            entity.setUpdatedAt(LocalDateTime.now()); 
            entity = systemProgramRepository.save(entity);
            return mapper.toResponse(entity); 
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sistema não encontrado"));
    }

    public void deletar(Integer id, Integer unitId) {
        SystemProgramEntity unitEntity = systemProgramRepository.findByIdAndDeletedAtIsNullAndSystemUnit_Id(id, unitId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sistema não encontrado"));
        
        /*if (unitEntity.getSystemUnit().getId() != unitId) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Problema de validade de Empresa. Consulte o Administrador do Sistema!");
        }*/
        
        unitEntity.setDeletedAt(LocalDateTime.now());
        systemProgramRepository.save(unitEntity); 
        
    }
    

    
}
