package com.ifixit.webapp.service.mapper;

import com.ifixit.webapp.domain.*;
import com.ifixit.webapp.service.dto.MechanicDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Mechanic and its DTO MechanicDTO.
 */
@Mapper(componentModel = "spring", uses = {ItemTypeMapper.class, MechanicTypeMapper.class, CompanyMapper.class, })
public interface MechanicMapper extends EntityMapper <MechanicDTO, Mechanic> {

    @Mapping(source = "itemType.id", target = "itemTypeId")

    @Mapping(source = "mechanicType.id", target = "mechanicTypeId")

    @Mapping(source = "company.id", target = "companyId")
    MechanicDTO toDto(Mechanic mechanic); 

    @Mapping(source = "itemTypeId", target = "itemType")

    @Mapping(source = "mechanicTypeId", target = "mechanicType")

    @Mapping(source = "companyId", target = "company")
    Mechanic toEntity(MechanicDTO mechanicDTO); 
    default Mechanic fromId(Long id) {
        if (id == null) {
            return null;
        }
        Mechanic mechanic = new Mechanic();
        mechanic.setId(id);
        return mechanic;
    }
}
