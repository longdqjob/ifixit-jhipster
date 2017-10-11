package com.ifixit.webapp.service.mapper;

import com.ifixit.webapp.domain.*;
import com.ifixit.webapp.service.dto.ManHoursDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ManHours and its DTO ManHoursDTO.
 */
@Mapper(componentModel = "spring", uses = {WorkOrderMapper.class, GroupEngineerMapper.class, })
public interface ManHoursMapper extends EntityMapper <ManHoursDTO, ManHours> {

    @Mapping(source = "workOrder.id", target = "workOrderId")

    @Mapping(source = "groupEngineer.id", target = "groupEngineerId")
    ManHoursDTO toDto(ManHours manHours); 

    @Mapping(source = "workOrderId", target = "workOrder")

    @Mapping(source = "groupEngineerId", target = "groupEngineer")
    ManHours toEntity(ManHoursDTO manHoursDTO); 
    default ManHours fromId(Long id) {
        if (id == null) {
            return null;
        }
        ManHours manHours = new ManHours();
        manHours.setId(id);
        return manHours;
    }
}
