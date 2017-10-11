package com.ifixit.webapp.service.mapper;

import com.ifixit.webapp.domain.*;
import com.ifixit.webapp.service.dto.WorkOrderDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity WorkOrder and its DTO WorkOrderDTO.
 */
@Mapper(componentModel = "spring", uses = {WorkTypeMapper.class, GroupEngineerMapper.class, MechanicMapper.class, })
public interface WorkOrderMapper extends EntityMapper <WorkOrderDTO, WorkOrder> {

    @Mapping(source = "workType.id", target = "workTypeId")

    @Mapping(source = "groupEngineer.id", target = "groupEngineerId")

    @Mapping(source = "mechanic.id", target = "mechanicId")
    WorkOrderDTO toDto(WorkOrder workOrder); 

    @Mapping(source = "workTypeId", target = "workType")

    @Mapping(source = "groupEngineerId", target = "groupEngineer")

    @Mapping(source = "mechanicId", target = "mechanic")
    WorkOrder toEntity(WorkOrderDTO workOrderDTO); 
    default WorkOrder fromId(Long id) {
        if (id == null) {
            return null;
        }
        WorkOrder workOrder = new WorkOrder();
        workOrder.setId(id);
        return workOrder;
    }
}
