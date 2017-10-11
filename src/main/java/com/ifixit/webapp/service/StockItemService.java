package com.ifixit.webapp.service;

import com.ifixit.webapp.service.dto.StockItemDTO;
import java.util.List;

/**
 * Service Interface for managing StockItem.
 */
public interface StockItemService {

    /**
     * Save a stockItem.
     *
     * @param stockItemDTO the entity to save
     * @return the persisted entity
     */
    StockItemDTO save(StockItemDTO stockItemDTO);

    /**
     *  Get all the stockItems.
     *
     *  @return the list of entities
     */
    List<StockItemDTO> findAll();

    /**
     *  Get the "id" stockItem.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    StockItemDTO findOne(Long id);

    /**
     *  Delete the "id" stockItem.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
