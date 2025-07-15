package br.com.navdata.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import br.com.navdata.auth.entity.SystemEntity;
import br.com.navdata.auth.mapper.SystemMapper;
import br.com.navdata.auth.repository.SystemRepository;
import br.com.navdata.auth.request.SystemRequest;
import br.com.navdata.auth.response.SystemResponse;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SystemService {

	@Autowired
    private SystemRepository systemRepository;
	
	@Autowired
	private SystemMapper mapper;
	
	public List<SystemResponse> listarTodos() {
        return systemRepository.findAllByDeletedAtIsNull()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public SystemResponse buscarPorId(Integer id) throws JsonProcessingException {
        SystemEntity entity = systemRepository.findByIdAndDeletedAtIsNull(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empresa com ID " + id + " não encontrada"));      
        
        return mapper.toResponse(entity);
    }    

    public SystemResponse criar(SystemRequest request) {
    	
        if (systemRepository.existsByName(request.getName())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Empresa já existe");
        }
        SystemEntity entity = new SystemEntity();        
        mapper.createFromDTO(request, entity);

        return mapper.toResponse(systemRepository.save(entity));
    }

    public SystemResponse atualizar(Integer id, SystemRequest request) {
        return systemRepository.findById(id).map(entity -> {            
            mapper.updateFromDTO(request, entity);
            entity.setUpdatedAt(LocalDateTime.now()); 
            entity = systemRepository.save(entity);
            return mapper.toResponse(entity); 
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
    }

    public void deletar(Integer id) {
        SystemEntity unitEntity = systemRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
        
        unitEntity.setDeletedAt(LocalDateTime.now());
        systemRepository.save(unitEntity); 
        
    }
    

    
}
