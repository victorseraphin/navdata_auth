package br.com.navdata.auth.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import br.com.navdata.auth.entity.SystemUserEntity;
import br.com.navdata.auth.request.SystemUserRequest;
import br.com.navdata.auth.response.SystemUserResponse;

@Mapper(componentModel = "spring")
public interface SystemUserMapper {

    SystemUserResponse toResponse(SystemUserEntity entity);    

    void createFromDTO(SystemUserRequest request, @MappingTarget SystemUserEntity entity);

    void updateFromDTO(SystemUserRequest request, @MappingTarget SystemUserEntity entity);

}
