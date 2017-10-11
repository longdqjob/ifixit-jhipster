package com.ifixit.webapp.service;

import com.ifixit.webapp.service.dto.WorkTypeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing WorkType.
 */
public interface WorkTypeService {

    /**
     * Save a workType.
     *
     * @param workTypeDTO the entity to save
     * @return the persisted entity
     */
    WorkTypeDTO save(WorkTypeDTO workTypeDTO);

    /**
     *  Get all the workTypes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<WorkTypeDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" workType.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    WorkTypeDTO findOne(Long id);

    /**
     *  Delete the "id" workType.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
