package br.com.navdata.auth.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import br.com.navdata.auth.entity.SystemProgramEntity;
import br.com.navdata.auth.request.SystemProgramRequest;
import br.com.navdata.auth.response.SystemProgramResponse;
import br.com.navdata.auth.response.SystemResponse;

@Mapper(componentModel = "spring")
public interface SystemProgramMapper {   
	
	//@Mapping(expression = "java(getFirstUnitId(entity))", target = "systemUnit")
    SystemProgramResponse toResponse(SystemProgramEntity entity);    

    void createFromDTO(SystemProgramRequest request, @MappingTarget SystemProgramEntity entity);

    void updateFromDTO(SystemProgramRequest request, @MappingTarget SystemProgramEntity entity);
    
    /*default Integer getFirstUnitId(SystemProgramEntity entity) {
        if (entity.getSystemUnit() != null && entity.getSystemUnit().getId() != null) {
            return entity.getSystemUnit().getId();
        }
        return null;
    }

    default String getFirstUnitName(SystemProgramEntity entity) {
        if (entity.getSystemUnit() != null && entity.getSystemUnit().getId() != null) {
            return entity.getSystemUnit().getName(); // supondo que tenha `getName()`
        }
        return null;
    }*/
    

}
