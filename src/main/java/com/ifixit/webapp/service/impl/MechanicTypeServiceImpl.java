package com.ifixit.webapp.service.impl;

import com.ifixit.webapp.service.MechanicTypeService;
import com.ifixit.webapp.domain.MechanicType;
import com.ifixit.webapp.repository.MechanicTypeRepository;
import com.ifixit.webapp.service.dto.MechanicTypeDTO;
import com.ifixit.webapp.service.mapper.MechanicTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing MechanicType.
 */
@Service
@Transactional
public class MechanicTypeServiceImpl implements MechanicTypeService{

    private final Logger log = LoggerFactory.getLogger(MechanicTypeServiceImpl.class);

    private final MechanicTypeRepository mechanicTypeRepository;

    private final MechanicTypeMapper mechanicTypeMapper;

    public MechanicTypeServiceImpl(MechanicTypeRepository mechanicTypeRepository, MechanicTypeMapper mechanicTypeMapper) {
        this.mechanicTypeRepository = mechanicTypeRepository;
        this.mechanicTypeMapper = mechanicTypeMapper;
    }

    /**
     * Save a mechanicType.
     *
     * @param mechanicTypeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MechanicTypeDTO save(MechanicTypeDTO mechanicTypeDTO) {
        log.debug("Request to save MechanicType : {}", mechanicTypeDTO);
        MechanicType mechanicType = mechanicTypeMapper.toEntity(mechanicTypeDTO);
        mechanicType = mechanicTypeRepository.save(mechanicType);
        return mechanicTypeMapper.toDto(mechanicType);
    }

    /**
     *  Get all the mechanicTypes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MechanicTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MechanicTypes");
        return mechanicTypeRepository.findAll(pageable)
            .map(mechanicTypeMapper::toDto);
    }

    /**
     *  Get one mechanicType by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MechanicTypeDTO findOne(Long id) {
        log.debug("Request to get MechanicType : {}", id);
        MechanicType mechanicType = mechanicTypeRepository.findOne(id);
        return mechanicTypeMapper.toDto(mechanicType);
    }

    /**
     *  Delete the  mechanicType by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MechanicType : {}", id);
        mechanicTypeRepository.delete(id);
    }
}
