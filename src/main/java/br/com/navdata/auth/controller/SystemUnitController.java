package br.com.navdata.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.navdata.auth.request.SystemUnitRequest;
import br.com.navdata.auth.response.SystemUnitResponse;
import br.com.navdata.auth.service.SystemUnitService;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/v1/system_units")
public class SystemUnitController {

	@Autowired
    private SystemUnitService systemUnitService;
	
	@GetMapping    
    public List<SystemUnitResponse> listar() {
    	return systemUnitService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SystemUnitResponse> buscar(@PathVariable Integer id) throws JsonProcessingException {
    	SystemUnitResponse response = systemUnitService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<SystemUnitResponse> criar(@Valid @RequestBody SystemUnitRequest request) {    	
    	SystemUnitResponse criado = systemUnitService.criar(request);
    	return ResponseEntity.ok(criado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SystemUnitResponse> atualizar(@PathVariable Integer id, @Valid @RequestBody SystemUnitRequest request) {
    	SystemUnitResponse atualizado = systemUnitService.atualizar(id, request);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
    	systemUnitService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
