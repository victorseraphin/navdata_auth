package br.com.navdata.auth.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.navdata.auth.dto.SystemUserDTO;
import br.com.navdata.auth.entity.SystemUserEntity;
import br.com.navdata.auth.mapper.SystemUserMapper;
import br.com.navdata.auth.repository.SystemUserRepository;
import br.com.navdata.auth.response.SystemUserResponse;

@Service
public class SystemUserService {

	@Autowired
    private SystemUserRepository systemUserRepository;
	
	@Autowired
	private SystemUserMapper mapper;

	private final BCryptPasswordEncoder passwordEncoder;

    public SystemUserService() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public List<SystemUserResponse> listarTodos() {
        return systemUserRepository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public SystemUserResponse buscarPorId(Integer id) throws JsonProcessingException {
        SystemUserEntity entity = systemUserRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário com ID " + id + " não encontrado"));       
        
        return mapper.toResponse(entity);
    }    

    public void criar(SystemUserDTO systemUserDTO) {
    	
        if (systemUserRepository.existsByEmail(systemUserDTO.getEmail())) {
            throw new RuntimeException("Usuário já existe");
        }

        SystemUserEntity userEntity = new SystemUserEntity();
        systemUserDTO.setPassword(passwordEncoder.encode(systemUserDTO.getPassword()));
        
        mapper.createFromDTO(systemUserDTO, userEntity);

        systemUserRepository.save(userEntity);
    }

    public SystemUserResponse atualizar(Integer id, SystemUserResponse systemUserDTO) {
        return systemUserRepository.findById(id).map(userEntity -> {            
            mapper.updateFromDTO(systemUserDTO, userEntity);
            userEntity.setUpdated_at(LocalDateTime.now()); 
            userEntity = systemUserRepository.save(userEntity);
            return mapper.toResponse(userEntity); 
        }).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public void deletar(Integer id) {
        SystemUserEntity userEntity = systemUserRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
        
        userEntity.setDeleted_at(LocalDateTime.now());
        userEntity.setActive("N");
        systemUserRepository.save(userEntity); 

        
    }
}
