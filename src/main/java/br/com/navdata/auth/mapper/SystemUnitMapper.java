package br.com.navdata.auth.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import br.com.navdata.auth.entity.SystemEntity;
import br.com.navdata.auth.entity.SystemUnitEntity;
import br.com.navdata.auth.request.SystemUnitRequest;
import br.com.navdata.auth.response.SystemUnitResponse;
import br.com.navdata.auth.response.SystemResponse;

@Mapper(componentModel = "spring")
public interface SystemUnitMapper {   

    SystemUnitResponse toResponse(SystemUnitEntity entity);    
    
    SystemResponse toSystemResponse(SystemEntity entity);
    
    List<SystemResponse> toSystemResponseList(List<SystemEntity> systems);

    void createFromDTO(SystemUnitRequest request, @MappingTarget SystemUnitEntity entity);

    void updateFromDTO(SystemUnitRequest request, @MappingTarget SystemUnitEntity entity);   

}
