package br.com.navdata.auth.mapper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import br.com.navdata.auth.entity.SystemEntity;
import br.com.navdata.auth.entity.SystemProgramEntity;
import br.com.navdata.auth.request.SystemRequest;
import br.com.navdata.auth.response.SystemResponse;

@Mapper(componentModel = "spring")
public interface SystemMapper {   

    @Mapping(target = "systemProgramId", source = "programs", qualifiedByName = "mapProgramsToIds")
    SystemResponse toResponse(SystemEntity entity);    
    
    

    void createFromDTO(SystemRequest request, @MappingTarget SystemEntity entity);

    void updateFromDTO(SystemRequest request, @MappingTarget SystemEntity entity);
    
    /*default Integer getFirstUnitId(SystemEntity entity) {
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
    }*/
    
    @Named("mapProgramsToIds")
    default List<Long> mapProgramsToIds(List<SystemProgramEntity> programs) {
        if (programs == null) return Collections.emptyList();
        return programs.stream().map(SystemProgramEntity::getId).collect(Collectors.toList());
    }
    

}
