package br.com.navdata.auth.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import br.com.navdata.auth.entity.SystemGroupEntity;
import br.com.navdata.auth.entity.SystemUnitEntity;
import br.com.navdata.auth.request.SystemGroupRequest;
import br.com.navdata.auth.response.SystemGroupResponse;


@Mapper(componentModel = "spring")
public interface SystemGroupMapper {   

	@Mapping(source = "systemUnit.id", target = "systemUnitId")
	@Mapping(source = "system.id", target = "systemId")
    SystemGroupResponse toResponse(SystemGroupEntity entity);    

    void createFromDTO(SystemGroupRequest request, @MappingTarget SystemGroupEntity entity);

    void updateFromDTO(SystemGroupRequest request, @MappingTarget SystemGroupEntity entity);
    
    default Integer map(SystemUnitEntity value) {
        return value != null ? value.getId() : null;
    }//*/

}
