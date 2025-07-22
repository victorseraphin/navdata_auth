package br.com.navdata.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.navdata.auth.context.TokenContext;
import br.com.navdata.auth.request.GroupPermissionRequest;
import br.com.navdata.auth.request.SystemGroupRequest;
import br.com.navdata.auth.response.GroupPermissionResponse;
import br.com.navdata.auth.response.SystemGroupResponse;
import br.com.navdata.auth.service.SystemGroupService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/v1/system_groups")
public class SystemGroupController {

	@Autowired
    private SystemGroupService systemGroupService;
	
	@Autowired
    private TokenContext tokenContext;
	
	@GetMapping    
    public List<SystemGroupResponse> listar(HttpServletRequest serverRequest) {
		Integer unitId = tokenContext.getUnitId(serverRequest);
    	return systemGroupService.listarTodos(unitId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SystemGroupResponse> buscar(HttpServletRequest serverRequest, @PathVariable Integer id) throws JsonProcessingException {
    	Integer unitId = tokenContext.getUnitId(serverRequest);
    	SystemGroupResponse response = systemGroupService.buscarPorId(id, unitId);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<SystemGroupResponse> criar(HttpServletRequest serverRequest, @Valid @RequestBody SystemGroupRequest request) { 
    	Integer unitId = tokenContext.getUnitId(serverRequest);
    	SystemGroupResponse criado = systemGroupService.criar(request, unitId);
    	return ResponseEntity.ok(criado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SystemGroupResponse> atualizar(HttpServletRequest serverRequest, @PathVariable Integer id, @Valid @RequestBody SystemGroupRequest request) {
    	Integer unitId = tokenContext.getUnitId(serverRequest);
    	SystemGroupResponse atualizado = systemGroupService.atualizar(id, request, unitId);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(HttpServletRequest serverRequest, @PathVariable Integer id) {
    	Integer unitId = tokenContext.getUnitId(serverRequest);
    	systemGroupService.deletar(id, unitId);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/{groupId}/permissions")
    public List<GroupPermissionResponse> listPermissions(@PathVariable Integer groupId) {
        return systemGroupService.getPermissionsByGroup(groupId);
    }
    
    @PutMapping("/{groupId}/permissions")
    public void updatePermissions(@PathVariable Integer groupId, @RequestBody List<GroupPermissionRequest> permissions) {
        systemGroupService.updatePermissions(groupId, permissions);//
    }
}
