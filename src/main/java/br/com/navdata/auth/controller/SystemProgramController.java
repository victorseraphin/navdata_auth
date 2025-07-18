package br.com.navdata.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.navdata.auth.context.TokenContext;
import br.com.navdata.auth.request.SystemProgramRequest;
import br.com.navdata.auth.response.SystemProgramResponse;
import br.com.navdata.auth.service.SystemProgramService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/v1/system_programs")
public class SystemProgramController {

	@Autowired
    private SystemProgramService systemProgramService;
	
	@Autowired
    private TokenContext tokenContext;
	
	@GetMapping    
    public List<SystemProgramResponse> listar(HttpServletRequest serverRequest) {
		Integer unitId = tokenContext.getUnitId(serverRequest);
    	return systemProgramService.listarTodos(unitId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SystemProgramResponse> buscar(HttpServletRequest serverRequest, @PathVariable Integer id) throws JsonProcessingException {
    	Integer unitId = tokenContext.getUnitId(serverRequest);
    	SystemProgramResponse response = systemProgramService.buscarPorId(id, unitId);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<SystemProgramResponse> criar(HttpServletRequest serverRequest, @Valid @RequestBody SystemProgramRequest request) { 
    	Integer unitId = tokenContext.getUnitId(serverRequest);
    	SystemProgramResponse criado = systemProgramService.criar(request, unitId);
    	return ResponseEntity.ok(criado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SystemProgramResponse> atualizar(HttpServletRequest serverRequest, @PathVariable Integer id, @Valid @RequestBody SystemProgramRequest request) {
    	Integer unitId = tokenContext.getUnitId(serverRequest);
    	SystemProgramResponse atualizado = systemProgramService.atualizar(id, request, unitId);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(HttpServletRequest serverRequest, @PathVariable Integer id) {
    	Integer unitId = tokenContext.getUnitId(serverRequest);
    	systemProgramService.deletar(id, unitId);
        return ResponseEntity.noContent().build();
    }
}
