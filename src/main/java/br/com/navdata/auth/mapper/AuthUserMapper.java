package br.com.navdata.auth.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import br.com.navdata.auth.entity.SystemEntity;
import br.com.navdata.auth.entity.SystemUnitEntity;
import br.com.navdata.auth.entity.SystemUserEntity;
import br.com.navdata.auth.request.AuthUserRequest;
import br.com.navdata.auth.request.SystemUserRequest;
import br.com.navdata.auth.response.AuthUserResponse;
import br.com.navdata.auth.response.SystemResponse;
import br.com.navdata.auth.response.SystemUnitResponse;
import br.com.navdata.auth.response.SystemUserResponse;

@Mapper(componentModel = "spring")
public interface AuthUserMapper {

	//@Mapping(target = "systemUnitId", expression = "java(getUnit(entity))")
	@Mapping(source = "systemUnit.id", target = "systemUnitId")
	AuthUserResponse toResponse(SystemUserEntity entity);    
    
    SystemUnitResponse toSystemUnitResponse(SystemUnitEntity unit);
    
    //SystemResponse toSystemResponse(SystemEntity system);

    void createFromDTO(AuthUserRequest request, @MappingTarget SystemUserEntity entity);

    void updateFromDTO(AuthUserRequest request, @MappingTarget SystemUserEntity entity);

    /*default SystemUnitResponse getFirstUnit(SystemUserEntity entity) {
        if (entity.getSystemUnit() != null && !entity.getSystemUnit().isEmpty()) {
            return toSystemUnitResponse(entity.getSystemUnit().get(0));
        }
        return null;
    }
    default Integer getUnit(SystemUnitEntity value) {
        return value != null ? value.getId() : null;
    }//*/
    

    

}
