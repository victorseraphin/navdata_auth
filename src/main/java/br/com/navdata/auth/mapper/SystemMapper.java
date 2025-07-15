package br.com.navdata.auth.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import br.com.navdata.auth.entity.SystemEntity;
import br.com.navdata.auth.request.SystemRequest;
import br.com.navdata.auth.response.SystemResponse;

@Mapper(componentModel = "spring")
public interface SystemMapper {   

    SystemResponse toResponse(SystemEntity entity);    

    void createFromDTO(SystemRequest request, @MappingTarget SystemEntity entity);

    void updateFromDTO(SystemRequest request, @MappingTarget SystemEntity entity);

}
