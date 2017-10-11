package com.ifixit.webapp.service;

import com.ifixit.webapp.service.dto.ItemTypeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing ItemType.
 */
public interface ItemTypeService {

    /**
     * Save a itemType.
     *
     * @param itemTypeDTO the entity to save
     * @return the persisted entity
     */
    ItemTypeDTO save(ItemTypeDTO itemTypeDTO);

    /**
     *  Get all the itemTypes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ItemTypeDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" itemType.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ItemTypeDTO findOne(Long id);

    /**
     *  Delete the "id" itemType.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
