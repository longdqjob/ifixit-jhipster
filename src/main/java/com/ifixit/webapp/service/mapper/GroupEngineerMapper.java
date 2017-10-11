package com.ifixit.webapp.service.mapper;

import com.ifixit.webapp.domain.*;
import com.ifixit.webapp.service.dto.GroupEngineerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity GroupEngineer and its DTO GroupEngineerDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GroupEngineerMapper extends EntityMapper <GroupEngineerDTO, GroupEngineer> {

    @Mapping(source = "parent.id", target = "parentId")
    GroupEngineerDTO toDto(GroupEngineer groupEngineer); 

    @Mapping(source = "parentId", target = "parent")
    GroupEngineer toEntity(GroupEngineerDTO groupEngineerDTO); 
    default GroupEngineer fromId(Long id) {
        if (id == null) {
            return null;
        }
        GroupEngineer groupEngineer = new GroupEngineer();
        groupEngineer.setId(id);
        return groupEngineer;
    }
}
