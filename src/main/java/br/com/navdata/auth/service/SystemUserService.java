package br.com.navdata.auth.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.navdata.auth.entity.SystemUserEntity;
import br.com.navdata.auth.mapper.SystemUserMapper;
import br.com.navdata.auth.repository.SystemUnitRepository;
import br.com.navdata.auth.repository.SystemUserRepository;
import br.com.navdata.auth.request.SystemUserRequest;
import br.com.navdata.auth.response.SystemUserResponse;

@Service
public class SystemUserService {

	@Autowired
    private SystemUserRepository systemUserRepository;

	@Autowired
    private SystemUnitRepository systemUnitRepository;
	
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
        	
        	if (request.getSystemUnitId() != unitId || entity.getSystemUnit().get(0).getId() != unitId) {
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
        
        if (userEntity.getSystemUnit().get(0).getId() != unitId) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Problema de validade de Empresa. Consulte o Administrador do Sistema!");
        }
        
        userEntity.setDeletedAt(LocalDateTime.now());
        userEntity.setActive("N");
        systemUserRepository.save(userEntity); 

        
    }
}
