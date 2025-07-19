package br.com.navdata.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import br.com.navdata.auth.entity.SystemEntity;
import br.com.navdata.auth.entity.SystemGroupEntity;
import br.com.navdata.auth.entity.SystemProgramEntity;
import br.com.navdata.auth.entity.SystemUnitEntity;
import br.com.navdata.auth.mapper.SystemGroupMapper;
import br.com.navdata.auth.repository.SystemGroupRepository;
import br.com.navdata.auth.repository.SystemProgramRepository;
import br.com.navdata.auth.repository.SystemRepository;
import br.com.navdata.auth.repository.SystemUnitRepository;
import br.com.navdata.auth.request.ProgramPermissionRequest;
import br.com.navdata.auth.request.SystemGroupRequest;
import br.com.navdata.auth.response.ProgramPermissionResponse;
import br.com.navdata.auth.response.SystemGroupResponse;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SystemGroupService {

	@Autowired
    private SystemGroupRepository systemGroupRepository;
	
	@Autowired
    private SystemUnitRepository systemUnitRepository;
	
	@Autowired
    private SystemRepository systemRepository;
	
	@Autowired
    private SystemProgramRepository systemProgramRepository;
	
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
    
    public List<ProgramPermissionResponse> getPermissionsByGroup(Integer groupId) {
        SystemGroupEntity group = systemGroupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Grupo não encontrado"));

        List<SystemProgramEntity> allPrograms = systemProgramRepository.findBySystemUnit_IdAndDeletedAtIsNullAndSystem_Id(
            group.getSystemUnit().getId(), group.getSystem().getId()
        );

        Set<Long> permittedIds = group.getSystemPrograms().stream()
                .map(SystemProgramEntity::getId)
                .collect(Collectors.toSet());

        return allPrograms.stream().map(program -> {
            ProgramPermissionResponse dto = new ProgramPermissionResponse();
            dto.setProgramId(program.getId());
            dto.setName(program.getName());
            dto.setPath(program.getPath());
            dto.setMethod(program.getMethod());
            dto.setPermitted(permittedIds.contains(program.getId()));
            return dto;
        }).collect(Collectors.toList());
    }
    
    @Transactional
    public void updatePermissions(Integer groupId, List<ProgramPermissionRequest> permissions) {
        SystemGroupEntity group = systemGroupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Grupo não encontrado"));

        List<Integer> programIds = permissions.stream()
                .filter(ProgramPermissionRequest::isPermitted)
                .map(ProgramPermissionRequest::getProgramId)
                .collect(Collectors.toList());
        
        System.out.println("programIds:"+programIds);

        List<SystemProgramEntity> permittedPrograms = systemProgramRepository.findAllById(programIds);

        group.setSystemPrograms(new HashSet<>(permittedPrograms));
        systemGroupRepository.save(group);
    }

    

    
}
