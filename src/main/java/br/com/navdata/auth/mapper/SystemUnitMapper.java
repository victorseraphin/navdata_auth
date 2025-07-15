package br.com.navdata.auth.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import br.com.navdata.auth.entity.SystemUnitEntity;
import br.com.navdata.auth.request.SystemUnitRequest;
import br.com.navdata.auth.response.SystemUnitResponse;

@Mapper(componentModel = "spring")
public interface SystemUnitMapper {   

    SystemUnitResponse toResponse(SystemUnitEntity entity);    

    void createFromDTO(SystemUnitRequest request, @MappingTarget SystemUnitEntity entity);

    void updateFromDTO(SystemUnitRequest request, @MappingTarget SystemUnitEntity entity);

}
