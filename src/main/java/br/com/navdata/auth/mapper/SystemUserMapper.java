package br.com.navdata.auth.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import br.com.navdata.auth.dto.SystemUserDTO;
import br.com.navdata.auth.entity.SystemUserEntity;
import br.com.navdata.auth.response.SystemUserResponse;

@Mapper(componentModel = "spring")
public interface SystemUserMapper {

    SystemUserEntity toEntity(SystemUserDTO dto);

    SystemUserDTO toDTO(SystemUserEntity entity);

    SystemUserResponse toResponse(SystemUserEntity entity);    

    void createFromDTO(SystemUserDTO dto, @MappingTarget SystemUserEntity entity);

    void updateFromDTO(SystemUserResponse dto, @MappingTarget SystemUserEntity entity);

}
