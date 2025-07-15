package br.com.navdata.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.navdata.auth.request.SystemRequest;
import br.com.navdata.auth.response.SystemResponse;
import br.com.navdata.auth.service.SystemService;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/v1/systems")
public class SystemController {

	@Autowired
    private SystemService systemService;
	
	@GetMapping    
    public List<SystemResponse> listar() {
    	return systemService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SystemResponse> buscar(@PathVariable Integer id) throws JsonProcessingException {
    	SystemResponse response = systemService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<SystemResponse> criar(@Valid @RequestBody SystemRequest request) {    	
    	SystemResponse criado = systemService.criar(request);
    	return ResponseEntity.ok(criado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SystemResponse> atualizar(@PathVariable Integer id, @Valid @RequestBody SystemRequest request) {
    	SystemResponse atualizado = systemService.atualizar(id, request);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
    	systemService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
