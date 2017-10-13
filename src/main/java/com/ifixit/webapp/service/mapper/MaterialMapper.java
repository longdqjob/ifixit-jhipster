package com.ifixit.webapp.service.mapper;

import com.ifixit.webapp.domain.*;
import com.ifixit.webapp.service.dto.MaterialDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Material and its DTO MaterialDTO.
 */
@Mapper(componentModel = "spring", uses = {ItemTypeMapper.class, })
public interface MaterialMapper extends EntityMapper <MaterialDTO, Material> {

    @Mapping(source = "itemType.id", target = "itemTypeId")
    @Mapping(source = "itemType.name", target = "itemTypeName")
    MaterialDTO toDto(Material material); 

    @Mapping(source = "itemTypeId", target = "itemType")
    Material toEntity(MaterialDTO materialDTO); 
    default Material fromId(Long id) {
        if (id == null) {
            return null;
        }
        Material material = new Material();
        material.setId(id);
        return material;
    }
}
