package br.com.navdata.auth.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.navdata.auth.context.TokenContext;
import br.com.navdata.auth.request.SystemUserRequest;
import br.com.navdata.auth.response.SystemUserResponse;
import br.com.navdata.auth.service.SystemUserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/system_users")
public class SystemUserController {

	@Autowired
    private SystemUserService systemUserService;   
	
	@Autowired
    private TokenContext tokenContext;
	
	@GetMapping
    public List<SystemUserResponse> listar(HttpServletRequest serverRequest) {
		Integer unitId = tokenContext.getUnitId(serverRequest);
    	return systemUserService.listarTodos(unitId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SystemUserResponse> buscar(HttpServletRequest serverRequest, @PathVariable Integer id) throws JsonProcessingException {
		Integer unitId = tokenContext.getUnitId(serverRequest);
    	SystemUserResponse response = systemUserService.buscarPorId(id,unitId);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<SystemUserResponse> criar(HttpServletRequest serverRequest, @Valid @RequestBody SystemUserRequest request) {
		Integer unitId = tokenContext.getUnitId(serverRequest);
    	SystemUserResponse criado = systemUserService.criar(request,unitId);
    	return ResponseEntity.ok(criado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SystemUserResponse> atualizar(HttpServletRequest serverRequest, @PathVariable Integer id, @Valid @RequestBody SystemUserRequest request) {
		Integer unitId = tokenContext.getUnitId(serverRequest);
    	SystemUserResponse atualizado = systemUserService.atualizar(id, request,unitId);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(HttpServletRequest serverRequest, @PathVariable Integer id) {
		Integer unitId = tokenContext.getUnitId(serverRequest);
    	systemUserService.deletar(id,unitId);
        return ResponseEntity.noContent().build();
    }
}

