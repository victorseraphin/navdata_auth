package br.com.navdata.auth.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import br.com.navdata.auth.entity.SystemUnitEntity;
import br.com.navdata.auth.entity.SystemUserEntity;
import br.com.navdata.auth.request.SystemUserRequest;
import br.com.navdata.auth.response.SystemUnitResponse;
import br.com.navdata.auth.response.SystemUserResponse;

@Mapper(componentModel = "spring")
public interface SystemUserMapper {

	@Mapping(target = "systemUnit", expression = "java(getFirstUnit(entity))")
    SystemUserResponse toResponse(SystemUserEntity entity);    
    
    SystemUnitResponse toSystemUnitResponse(SystemUnitEntity unit);

    void createFromDTO(SystemUserRequest request, @MappingTarget SystemUserEntity entity);

    void updateFromDTO(SystemUserRequest request, @MappingTarget SystemUserEntity entity);
    
    default SystemUnitResponse getFirstUnit(SystemUserEntity entity) {
        if (entity.getSystemUnit() != null && !entity.getSystemUnit().isEmpty()) {
            return toSystemUnitResponse(entity.getSystemUnit().get(0));
        }
        return null;
    }
    

}
