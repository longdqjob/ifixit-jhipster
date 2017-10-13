package com.ifixit.webapp.service.impl;

import com.google.gson.Gson;
import com.ifixit.webapp.service.MechanicService;
import com.ifixit.webapp.domain.Mechanic;
import com.ifixit.webapp.repository.MechanicRepository;
import com.ifixit.webapp.service.dto.MaterialDTO;
import com.ifixit.webapp.service.dto.MechanicDTO;
import com.ifixit.webapp.service.mapper.MechanicMapper;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing Mechanic.
 */
@Service
@Transactional
public class MechanicServiceImpl implements MechanicService {

    private final Logger log = LoggerFactory.getLogger(MechanicServiceImpl.class);

    private final MechanicRepository mechanicRepository;

    private final MechanicMapper mechanicMapper;

    public MechanicServiceImpl(MechanicRepository mechanicRepository, MechanicMapper mechanicMapper) {
        this.mechanicRepository = mechanicRepository;
        this.mechanicMapper = mechanicMapper;
    }

    /**
     * Save a mechanic.
     *
     * @param mechanicDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MechanicDTO save(MechanicDTO mechanicDTO) {
        log.debug("Request to save Mechanic : {}", mechanicDTO);
        Mechanic mechanic = mechanicMapper.toEntity(mechanicDTO);
        mechanic = mechanicRepository.save(mechanic);
        return mechanicMapper.toDto(mechanic);
    }

    /**
     * Get all the mechanics.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MechanicDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Mechanics");
        return mechanicRepository.findAll(pageable)
                .map(mechanicMapper::toDto);
    }

    /**
     * Get one mechanic by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MechanicDTO findOne(Long id) {
        log.debug("Request to get Mechanic : {}", id);
        Mechanic mechanic = mechanicRepository.findOne(id);
        return mechanicMapper.toDto(mechanic);
    }

    /**
     * Delete the mechanic by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Mechanic : {}", id);
        mechanicRepository.delete(id);
    }

    //ThuyetLV Add
    @Override
    public MechanicDTO getData(Long id) {
        log.debug("Request to getData Material : {}", id);
        MechanicDTO material = mechanicRepository.getData(id);
        Gson gson = new Gson();

        log.info("findOne: " + id + " - " + gson.toJson(material));

        return material;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MechanicDTO> getMechanics(List<Long> listSys, Pageable pageable) {
        log.debug("Request to get all getMechanics");
        return mechanicRepository.getMechanics(listSys, pageable);
    }
}
