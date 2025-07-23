package br.com.navdata.auth.service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.navdata.auth.entity.SystemGroupEntity;
import br.com.navdata.auth.entity.SystemProgramEntity;
import br.com.navdata.auth.entity.SystemUserEntity;
import br.com.navdata.auth.mapper.SystemUserMapper;
import br.com.navdata.auth.repository.SystemGroupRepository;
import br.com.navdata.auth.repository.SystemProgramRepository;
import br.com.navdata.auth.repository.SystemRepository;
import br.com.navdata.auth.repository.SystemUnitRepository;
import br.com.navdata.auth.repository.SystemUserRepository;
import br.com.navdata.auth.request.GroupPermissionRequest;
import br.com.navdata.auth.request.SystemUserRequest;
import br.com.navdata.auth.request.UserGroupRequest;
import br.com.navdata.auth.request.UserPermissionRequest;
import br.com.navdata.auth.response.GroupPermissionResponse;
import br.com.navdata.auth.response.SystemUserResponse;
import br.com.navdata.auth.response.UserGroupResponse;
import br.com.navdata.auth.response.UserPermissionResponse;
import jakarta.transaction.Transactional;

@Service
public class SystemUserService {

	@Autowired
    private SystemUserRepository systemUserRepository;

	@Autowired
    private SystemUnitRepository systemUnitRepository;
	
	@Autowired
    private SystemGroupRepository systemGroupRepository;
	
	@Autowired
    private SystemRepository systemRepository;
	
	@Autowired
    private SystemProgramRepository systemProgramRepository;
	
	@Autowired
	private SystemUserMapper mapper;

	private final BCryptPasswordEncoder passwordEncoder;

    public SystemUserService() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public List<SystemUserResponse> listarTodos(Integer unitId) {
        return systemUserRepository.findAllBySystemUnit_IdAndDeletedAtIsNull(unitId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public SystemUserResponse buscarPorId(Integer id, Integer unitId) throws JsonProcessingException {
        SystemUserEntity entity = systemUserRepository.findByIdAndDeletedAtIsNullAndSystemUnit_Id(id,unitId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário com ID " + id + " não encontrado"));       
        
        return mapper.toResponse(entity);
    }    

    public SystemUserResponse criar(SystemUserRequest request, Integer unitId) {

        if (request.getSystemUnitId() != unitId) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Problema de validade de Empresa. Consulte o Administrador do Sistema!");
        }
    	
    	if (systemUserRepository.existsByEmailAndDeletedAtIsNull(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário já existe");
        }
    	
    	if (!systemUnitRepository.existsByIdAndDeletedAtIsNull(request.getSystemUnitId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Empresa não está ativa ou nao cadastrada!");
        }

        SystemUserEntity entity = new SystemUserEntity();
        request.setPassword(passwordEncoder.encode(request.getPassword())); 
        request.setActive("Y");
        mapper.createFromDTO(request, entity);

        return mapper.toResponse(systemUserRepository.save(entity));
    }

    public SystemUserResponse atualizar(Integer id, SystemUserRequest request, Integer unitId) {
        return systemUserRepository.findByIdAndDeletedAtIsNullAndSystemUnit_Id(id, unitId).map(entity -> {   
        	
        	if (request.getSystemUnitId() != unitId || entity.getSystemUnit().getId() != unitId) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Problema de validade de Empresa. Consulte o Administrador do Sistema!");
            }
        	
            mapper.updateFromDTO(request, entity);
            entity.setUpdatedAt(LocalDateTime.now()); 
            entity = systemUserRepository.save(entity);
            return mapper.toResponse(entity); 
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
    }

    public void deletar(Integer id, Integer unitId) {
        SystemUserEntity userEntity = systemUserRepository.findByIdAndDeletedAtIsNullAndSystemUnit_Id(id, unitId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
        
        if (userEntity.getSystemUnit().getId() != unitId) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Problema de validade de Empresa. Consulte o Administrador do Sistema!");
        }
        
        userEntity.setDeletedAt(LocalDateTime.now());
        userEntity.setActive(false);
        systemUserRepository.save(userEntity); 

        
    }
    
    public List<UserPermissionResponse> getPermissionsByUser(Integer userId) {
        SystemUserEntity userEntity = systemUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        List<SystemProgramEntity> allPrograms = systemProgramRepository.findBySystemUnit_IdAndDeletedAtIsNullAndSystem_Id(
        		userEntity.getSystemUnit().getId(), userEntity.getSystems().get(0).getId()
        );

        Set<Integer> permittedIds = userEntity.getSystemPrograms().stream()
                .map(SystemProgramEntity::getId)
                .collect(Collectors.toSet());

        return allPrograms.stream().map(program -> {
            UserPermissionResponse dto = new UserPermissionResponse();
            dto.setProgramId(program.getId());
            dto.setName(program.getName());
            dto.setPath(program.getPath());
            dto.setMethod(program.getMethod());
            dto.setPermitted(permittedIds.contains(program.getId()));
            return dto;
        }).collect(Collectors.toList());
    }
    
    @Transactional
    public void updatePermissionsByUser(Integer groupId, List<UserPermissionRequest> permissions) {
        SystemUserEntity userEntity = systemUserRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Grupo não encontrado"));

        List<Integer> programIds = permissions.stream()
                .filter(UserPermissionRequest::isPermitted)
                .map(UserPermissionRequest::getProgramId)
                .collect(Collectors.toList());

        List<SystemProgramEntity> permittedPrograms = systemProgramRepository.findAllById(programIds);
        
        if (permittedPrograms.isEmpty()) {
            System.out.println("Nenhum programa encontrado com os IDs: " + programIds);
            throw new RuntimeException("Programas não encontrados.");
        }

        userEntity.setSystemPrograms(new HashSet<>(permittedPrograms));
        systemUserRepository.save(userEntity);
    }
    

    
    public List<UserGroupResponse> getGroupsByUser(Integer userId) {
        SystemUserEntity userEntity = systemUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        List<SystemGroupEntity> allGroups = systemGroupRepository.findBySystemUnit_IdAndDeletedAtIsNullAndSystem_Id(
        		userEntity.getSystemUnit().getId(), userEntity.getSystems().get(0).getId()
        );

        Set<Integer> permittedIds = userEntity.getSystemGroups().stream()
                .map(SystemGroupEntity::getId)
                .collect(Collectors.toSet());

        return allGroups.stream().map(program -> {
            UserGroupResponse dto = new UserGroupResponse();
            dto.setGroupId(program.getId());
            dto.setName(program.getName());
            dto.setPermitted(permittedIds.contains(program.getId()));
            return dto;
        }).collect(Collectors.toList());
    }
    
    @Transactional
    public void updateGroupsByUser(Integer userId, List<UserGroupRequest> groups) {
        SystemUserEntity userEntity = systemUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        List<Integer> groupsIds = groups.stream()
                .filter(UserGroupRequest::isPermitted)
                .map(UserGroupRequest::getGroupId)
                .collect(Collectors.toList());

        List<SystemGroupEntity> permittedGroups = systemGroupRepository.findAllById(groupsIds);
        
        if (permittedGroups.isEmpty()) {
            System.out.println("Nenhum grupo encontrado com os IDs: " + groupsIds);
            throw new RuntimeException("Grupos não encontrados.");
        }

        userEntity.setSystemGroups(new HashSet<>(permittedGroups));
        systemUserRepository.save(userEntity);
    }
}
