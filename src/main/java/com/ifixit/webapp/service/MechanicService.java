package com.ifixit.webapp.service;

import com.ifixit.webapp.service.dto.MaterialDTO;
import com.ifixit.webapp.service.dto.MechanicDTO;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Mechanic.
 */
public interface MechanicService {

    /**
     * Save a mechanic.
     *
     * @param mechanicDTO the entity to save
     * @return the persisted entity
     */
    MechanicDTO save(MechanicDTO mechanicDTO);

    /**
     * Get all the mechanics.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MechanicDTO> findAll(Pageable pageable);

    /**
     * Get the "id" mechanic.
     *
     * @param id the id of the entity
     * @return the entity
     */
    MechanicDTO findOne(Long id);

    /**
     * Delete the "id" mechanic.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    //ThuyetLV Add
    public MechanicDTO getData(Long id);
    Page<MechanicDTO> getMechanics(List<Long> listSys, Pageable pageable);
}
