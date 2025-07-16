package br.com.navdata.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import br.com.navdata.auth.entity.SystemEntity;
import br.com.navdata.auth.entity.SystemUnitEntity;
import br.com.navdata.auth.mapper.SystemMapper;
import br.com.navdata.auth.repository.SystemRepository;
import br.com.navdata.auth.repository.SystemUnitRepository;
import br.com.navdata.auth.request.SystemRequest;
import br.com.navdata.auth.response.SystemResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SystemService {

	@Autowired
    private SystemRepository systemRepository;
	
	@Autowired
    private SystemUnitRepository systemUnitRepository;
	
	@Autowired
	private SystemMapper mapper;
	
	public List<SystemResponse> listarTodos(Integer unitId) {
		
        return systemRepository.findAllBySystemUnit_IdAndDeletedAtIsNull(unitId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public SystemResponse buscarPorId(Integer id, Integer unitId) throws JsonProcessingException {
        SystemEntity entity = systemRepository.findByIdAndDeletedAtIsNullAndSystemUnit_Id(id, unitId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sistema com ID " + id + " não encontrada"));      
        
        return mapper.toResponse(entity);
    }    

    public SystemResponse criar(SystemRequest request, Integer unitId) {

        if (systemRepository.existsByName(request.getName())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sistema já existe");
        }
        if (request.getSystemUnitId() != unitId) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Problema de validade de Empresa. Consulte o Administrador do Sistema!");
        }
        SystemEntity entity = new SystemEntity();        
        mapper.createFromDTO(request, entity);
        
        Optional<SystemUnitEntity> optionalUnit = systemUnitRepository.findByIdAndDeletedAtIsNull(unitId);
              
        if (optionalUnit.isPresent()) {
            entity.setSystemUnit(List.of(optionalUnit.get()));
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "SystemUnitEntity não encontrada com id: " + request.getSystemUnitId());
        }

        return mapper.toResponse(systemRepository.save(entity));
    }

    public SystemResponse atualizar(Integer id, SystemRequest request, Integer unitId) {
        return systemRepository.findByIdAndDeletedAtIsNullAndSystemUnit_Id(id, unitId).map(entity -> { 

            if (request.getSystemUnitId() != unitId) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Problema de validade de Empresa. Consulte o Administrador do Sistema!");
            }
            
            mapper.updateFromDTO(request, entity);
            entity.setUpdatedAt(LocalDateTime.now()); 
            entity = systemRepository.save(entity);
            return mapper.toResponse(entity); 
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sistema não encontrado"));
    }

    public void deletar(Integer id, Integer unitId) {
        SystemEntity unitEntity = systemRepository.findByIdAndDeletedAtIsNullAndSystemUnit_Id(id, unitId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sistema não encontrado"));
        
        if (unitEntity.getSystemUnit().get(0).getId() != unitId) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Problema de validade de Empresa. Consulte o Administrador do Sistema!");
        }
        
        unitEntity.setDeletedAt(LocalDateTime.now());
        systemRepository.save(unitEntity); 
        
    }
    

    
}
