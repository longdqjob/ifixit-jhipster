package com.ifixit.webapp.service.mapper;

import com.ifixit.webapp.domain.*;
import com.ifixit.webapp.service.dto.MechanicTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MechanicType and its DTO MechanicTypeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MechanicTypeMapper extends EntityMapper <MechanicTypeDTO, MechanicType> {
    
    
    default MechanicType fromId(Long id) {
        if (id == null) {
            return null;
        }
        MechanicType mechanicType = new MechanicType();
        mechanicType.setId(id);
        return mechanicType;
    }
}
