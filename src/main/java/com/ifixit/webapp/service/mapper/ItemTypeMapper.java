package com.ifixit.webapp.service.mapper;

import com.ifixit.webapp.domain.*;
import com.ifixit.webapp.service.dto.ItemTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ItemType and its DTO ItemTypeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ItemTypeMapper extends EntityMapper <ItemTypeDTO, ItemType> {

    @Mapping(source = "parent.id", target = "parentId")
    ItemTypeDTO toDto(ItemType itemType); 
    @Mapping(target = "itemTypes", ignore = true)

    @Mapping(source = "parentId", target = "parent")
    ItemType toEntity(ItemTypeDTO itemTypeDTO); 
    default ItemType fromId(Long id) {
        if (id == null) {
            return null;
        }
        ItemType itemType = new ItemType();
        itemType.setId(id);
        return itemType;
    }
}
