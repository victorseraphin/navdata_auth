package br.com.navdata.auth.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import br.com.navdata.auth.entity.SystemProgramEntity;
import br.com.navdata.auth.entity.SystemUnitEntity;
import br.com.navdata.auth.request.SystemProgramRequest;
import br.com.navdata.auth.response.SystemProgramResponse;


@Mapper(componentModel = "spring")
public interface SystemProgramMapper {   

	@Mapping(source = "systemUnit.id", target = "systemUnitId")
	@Mapping(source = "system.id", target = "systemId")
    SystemProgramResponse toResponse(SystemProgramEntity entity);    

    void createFromDTO(SystemProgramRequest request, @MappingTarget SystemProgramEntity entity);

    void updateFromDTO(SystemProgramRequest request, @MappingTarget SystemProgramEntity entity);
    
    default Integer map(SystemUnitEntity value) {
        return value != null ? value.getId() : null;
    }//*/

}
