package com.ifixit.webapp.service.impl;

import com.ifixit.webapp.service.WorkTypeService;
import com.ifixit.webapp.domain.WorkType;
import com.ifixit.webapp.repository.WorkTypeRepository;
import com.ifixit.webapp.service.dto.WorkTypeDTO;
import com.ifixit.webapp.service.mapper.WorkTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing WorkType.
 */
@Service
@Transactional
public class WorkTypeServiceImpl implements WorkTypeService{

    private final Logger log = LoggerFactory.getLogger(WorkTypeServiceImpl.class);

    private final WorkTypeRepository workTypeRepository;

    private final WorkTypeMapper workTypeMapper;

    public WorkTypeServiceImpl(WorkTypeRepository workTypeRepository, WorkTypeMapper workTypeMapper) {
        this.workTypeRepository = workTypeRepository;
        this.workTypeMapper = workTypeMapper;
    }

    /**
     * Save a workType.
     *
     * @param workTypeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public WorkTypeDTO save(WorkTypeDTO workTypeDTO) {
        log.debug("Request to save WorkType : {}", workTypeDTO);
        WorkType workType = workTypeMapper.toEntity(workTypeDTO);
        workType = workTypeRepository.save(workType);
        return workTypeMapper.toDto(workType);
    }

    /**
     *  Get all the workTypes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<WorkTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all WorkTypes");
        return workTypeRepository.findAll(pageable)
            .map(workTypeMapper::toDto);
    }

    /**
     *  Get one workType by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public WorkTypeDTO findOne(Long id) {
        log.debug("Request to get WorkType : {}", id);
        WorkType workType = workTypeRepository.findOne(id);
        return workTypeMapper.toDto(workType);
    }

    /**
     *  Delete the  workType by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete WorkType : {}", id);
        workTypeRepository.delete(id);
    }
}
