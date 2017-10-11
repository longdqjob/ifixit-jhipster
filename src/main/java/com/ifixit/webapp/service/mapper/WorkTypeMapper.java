package com.ifixit.webapp.service.mapper;

import com.ifixit.webapp.domain.*;
import com.ifixit.webapp.service.dto.WorkTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity WorkType and its DTO WorkTypeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WorkTypeMapper extends EntityMapper <WorkTypeDTO, WorkType> {
    
    
    default WorkType fromId(Long id) {
        if (id == null) {
            return null;
        }
        WorkType workType = new WorkType();
        workType.setId(id);
        return workType;
    }
}
