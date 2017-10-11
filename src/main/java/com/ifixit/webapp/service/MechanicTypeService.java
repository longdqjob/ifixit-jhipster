package com.ifixit.webapp.service;

import com.ifixit.webapp.service.dto.MechanicTypeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing MechanicType.
 */
public interface MechanicTypeService {

    /**
     * Save a mechanicType.
     *
     * @param mechanicTypeDTO the entity to save
     * @return the persisted entity
     */
    MechanicTypeDTO save(MechanicTypeDTO mechanicTypeDTO);

    /**
     *  Get all the mechanicTypes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<MechanicTypeDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" mechanicType.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    MechanicTypeDTO findOne(Long id);

    /**
     *  Delete the "id" mechanicType.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
