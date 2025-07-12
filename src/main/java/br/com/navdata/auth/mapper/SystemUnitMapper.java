package br.com.navdata.auth.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import br.com.navdata.auth.dto.SystemUnitDTO;
import br.com.navdata.auth.entity.SystemUnitEntity;

@Mapper(componentModel = "spring")
public interface SystemUnitMapper {

    SystemUnitEntity toEntity(SystemUnitDTO dto);

    void updateFromDTO(SystemUnitDTO dto, @MappingTarget SystemUnitEntity entity);

    SystemUnitDTO toDTO(SystemUnitEntity entity);

}
