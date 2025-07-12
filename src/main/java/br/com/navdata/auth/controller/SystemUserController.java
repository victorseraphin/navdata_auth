package br.com.navdata.auth.controller;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.navdata.auth.dto.SystemUserDTO;
import br.com.navdata.auth.response.SystemUserResponse;
import br.com.navdata.auth.service.SystemUserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/system_users")
public class SystemUserController {

	@Autowired
    private SystemUserService systemUserService;   
	
	@GetMapping
    public List<SystemUserResponse> listar() {
    	return systemUserService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SystemUserResponse> buscar(@PathVariable Integer id) throws JsonProcessingException {
    	SystemUserResponse response = systemUserService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<String> criar(@Valid @RequestBody SystemUserDTO dto) {
    	systemUserService.criar(dto);
    	return ResponseEntity.ok("Usuário registrado com sucesso!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<SystemUserResponse> atualizar(@PathVariable Integer id, @Valid @RequestBody SystemUserResponse response) {
    	SystemUserResponse atualizado = systemUserService.atualizar(id, response);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
    	systemUserService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

