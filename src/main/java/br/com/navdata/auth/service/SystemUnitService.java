package br.com.navdata.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import br.com.navdata.auth.entity.SystemUnitEntity;
import br.com.navdata.auth.mapper.SystemUnitMapper;
import br.com.navdata.auth.repository.SystemUnitRepository;
import br.com.navdata.auth.request.SystemUnitRequest;
import br.com.navdata.auth.response.SystemUnitResponse;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SystemUnitService {

	@Autowired
    private SystemUnitRepository systemUnitRepository;
	
	@Autowired
	private SystemUnitMapper mapper;
	
	public List<SystemUnitResponse> listarTodos() {
        return systemUnitRepository.findAllByDeletedAtIsNull()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public SystemUnitResponse buscarPorId(Integer id) throws JsonProcessingException {
        SystemUnitEntity entity = systemUnitRepository.findByIdAndDeletedAtIsNull(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empresa com ID " + id + " não encontrada"));      
        
        return mapper.toResponse(entity);
    }    

    public SystemUnitResponse criar(SystemUnitRequest request) {
    	
        if (systemUnitRepository.existsByDocumento(request.getDocumento())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Empresa já existe");
        }
        SystemUnitEntity entity = new SystemUnitEntity();        
        mapper.createFromDTO(request, entity);

        return mapper.toResponse(systemUnitRepository.save(entity));
    }

    public SystemUnitResponse atualizar(Integer id, SystemUnitRequest request) {
        return systemUnitRepository.findById(id).map(entity -> {            
            mapper.updateFromDTO(request, entity);
            entity.setUpdatedAt(LocalDateTime.now()); 
            entity = systemUnitRepository.save(entity);
            return mapper.toResponse(entity); 
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
    }

    public void deletar(Integer id) {
        SystemUnitEntity unitEntity = systemUnitRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
        
        unitEntity.setDeletedAt(LocalDateTime.now());
        systemUnitRepository.save(unitEntity); 
        
    }
    

    
}
