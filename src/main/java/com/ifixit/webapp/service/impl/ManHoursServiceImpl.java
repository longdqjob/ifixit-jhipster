package com.ifixit.webapp.service.impl;

import com.ifixit.webapp.service.ManHoursService;
import com.ifixit.webapp.domain.ManHours;
import com.ifixit.webapp.repository.ManHoursRepository;
import com.ifixit.webapp.service.dto.ManHoursDTO;
import com.ifixit.webapp.service.mapper.ManHoursMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing ManHours.
 */
@Service
@Transactional
public class ManHoursServiceImpl implements ManHoursService{

    private final Logger log = LoggerFactory.getLogger(ManHoursServiceImpl.class);

    private final ManHoursRepository manHoursRepository;

    private final ManHoursMapper manHoursMapper;

    public ManHoursServiceImpl(ManHoursRepository manHoursRepository, ManHoursMapper manHoursMapper) {
        this.manHoursRepository = manHoursRepository;
        this.manHoursMapper = manHoursMapper;
    }

    /**
     * Save a manHours.
     *
     * @param manHoursDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ManHoursDTO save(ManHoursDTO manHoursDTO) {
        log.debug("Request to save ManHours : {}", manHoursDTO);
        ManHours manHours = manHoursMapper.toEntity(manHoursDTO);
        manHours = manHoursRepository.save(manHours);
        return manHoursMapper.toDto(manHours);
    }

    /**
     *  Get all the manHours.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ManHoursDTO> findAll() {
        log.debug("Request to get all ManHours");
        return manHoursRepository.findAll().stream()
            .map(manHoursMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one manHours by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ManHoursDTO findOne(Long id) {
        log.debug("Request to get ManHours : {}", id);
        ManHours manHours = manHoursRepository.findOne(id);
        return manHoursMapper.toDto(manHours);
    }

    /**
     *  Delete the  manHours by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ManHours : {}", id);
        manHoursRepository.delete(id);
    }
}
