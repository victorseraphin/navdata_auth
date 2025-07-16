package br.com.navdata.auth.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import br.com.navdata.auth.entity.SystemEntity;
import br.com.navdata.auth.request.SystemRequest;
import br.com.navdata.auth.response.SystemResponse;

@Mapper(componentModel = "spring")
public interface SystemMapper {   
	
	@Mapping(expression = "java(getFirstUnitId(entity))", target = "systemUnitId")
    @Mapping(expression = "java(getFirstUnitName(entity))", target = "systemUnitDesc")
    SystemResponse toResponse(SystemEntity entity);    

    void createFromDTO(SystemRequest request, @MappingTarget SystemEntity entity);

    void updateFromDTO(SystemRequest request, @MappingTarget SystemEntity entity);
    
    default Integer getFirstUnitId(SystemEntity entity) {
        if (entity.getSystemUnit() != null && !entity.getSystemUnit().isEmpty()) {
            return entity.getSystemUnit().get(0).getId();
        }
        return null;
    }

    default String getFirstUnitName(SystemEntity entity) {
        if (entity.getSystemUnit() != null && !entity.getSystemUnit().isEmpty()) {
            return entity.getSystemUnit().get(0).getName(); // supondo que tenha `getName()`
        }
        return null;
    }
    

}
