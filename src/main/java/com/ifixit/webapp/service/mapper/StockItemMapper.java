package com.ifixit.webapp.service.mapper;

import com.ifixit.webapp.domain.*;
import com.ifixit.webapp.service.dto.StockItemDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity StockItem and its DTO StockItemDTO.
 */
@Mapper(componentModel = "spring", uses = {WorkOrderMapper.class, })
public interface StockItemMapper extends EntityMapper <StockItemDTO, StockItem> {

    @Mapping(source = "workOrder.id", target = "workOrderId")
    StockItemDTO toDto(StockItem stockItem); 

    @Mapping(source = "workOrderId", target = "workOrder")
    StockItem toEntity(StockItemDTO stockItemDTO); 
    default StockItem fromId(Long id) {
        if (id == null) {
            return null;
        }
        StockItem stockItem = new StockItem();
        stockItem.setId(id);
        return stockItem;
    }
}
