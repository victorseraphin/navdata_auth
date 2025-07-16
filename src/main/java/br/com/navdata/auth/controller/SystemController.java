package br.com.navdata.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.navdata.auth.context.TokenContext;
import br.com.navdata.auth.request.SystemRequest;
import br.com.navdata.auth.response.SystemResponse;
import br.com.navdata.auth.service.SystemService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/v1/systems")
public class SystemController {

	@Autowired
    private SystemService systemService;
	
	@Autowired
    private TokenContext tokenContext;
	
	@GetMapping    
    public List<SystemResponse> listar(HttpServletRequest serverRequest) {
		Integer unitId = tokenContext.getUnitId(serverRequest);
    	return systemService.listarTodos(unitId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SystemResponse> buscar(HttpServletRequest serverRequest, @PathVariable Integer id) throws JsonProcessingException {
    	Integer unitId = tokenContext.getUnitId(serverRequest);
    	SystemResponse response = systemService.buscarPorId(id, unitId);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<SystemResponse> criar(HttpServletRequest serverRequest, @Valid @RequestBody SystemRequest request) { 
    	Integer unitId = tokenContext.getUnitId(serverRequest);
    	SystemResponse criado = systemService.criar(request, unitId);
    	return ResponseEntity.ok(criado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SystemResponse> atualizar(HttpServletRequest serverRequest, @PathVariable Integer id, @Valid @RequestBody SystemRequest request) {
    	Integer unitId = tokenContext.getUnitId(serverRequest);
    	SystemResponse atualizado = systemService.atualizar(id, request, unitId);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(HttpServletRequest serverRequest, @PathVariable Integer id) {
    	Integer unitId = tokenContext.getUnitId(serverRequest);
    	systemService.deletar(id, unitId);
        return ResponseEntity.noContent().build();
    }
}
