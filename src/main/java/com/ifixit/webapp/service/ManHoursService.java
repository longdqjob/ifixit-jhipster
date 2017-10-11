package com.ifixit.webapp.service;

import com.ifixit.webapp.service.dto.ManHoursDTO;
import java.util.List;

/**
 * Service Interface for managing ManHours.
 */
public interface ManHoursService {

    /**
     * Save a manHours.
     *
     * @param manHoursDTO the entity to save
     * @return the persisted entity
     */
    ManHoursDTO save(ManHoursDTO manHoursDTO);

    /**
     *  Get all the manHours.
     *
     *  @return the list of entities
     */
    List<ManHoursDTO> findAll();

    /**
     *  Get the "id" manHours.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ManHoursDTO findOne(Long id);

    /**
     *  Delete the "id" manHours.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
