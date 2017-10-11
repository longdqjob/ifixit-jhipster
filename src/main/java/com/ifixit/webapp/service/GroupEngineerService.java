package com.ifixit.webapp.service;

import com.ifixit.webapp.service.dto.GroupEngineerDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing GroupEngineer.
 */
public interface GroupEngineerService {

    /**
     * Save a groupEngineer.
     *
     * @param groupEngineerDTO the entity to save
     * @return the persisted entity
     */
    GroupEngineerDTO save(GroupEngineerDTO groupEngineerDTO);

    /**
     *  Get all the groupEngineers.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<GroupEngineerDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" groupEngineer.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    GroupEngineerDTO findOne(Long id);

    /**
     *  Delete the "id" groupEngineer.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
