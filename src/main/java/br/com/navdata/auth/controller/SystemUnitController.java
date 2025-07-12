package br.com.navdata.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.navdata.auth.dto.SystemUnitDTO;
import br.com.navdata.auth.service.SystemUnitService;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/v1/system_units")
public class SystemUnitController {

	@Autowired
    private SystemUnitService service;

    @GetMapping
    public List<SystemUnitDTO> listar() {
    	return service.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SystemUnitDTO> buscar(@PathVariable Integer id) throws JsonProcessingException {
        SystemUnitDTO response = service.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<SystemUnitDTO> criar(@Valid @RequestBody SystemUnitDTO dto) {
    	SystemUnitDTO criado = service.criar(dto);
    	return ResponseEntity.ok(criado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SystemUnitDTO> atualizar(@PathVariable Integer id, @Valid @RequestBody SystemUnitDTO dto) {
    	SystemUnitDTO atualizado = service.atualizar(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
