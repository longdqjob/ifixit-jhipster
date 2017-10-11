package com.ifixit.webapp.service.impl;

import com.ifixit.webapp.service.ItemTypeService;
import com.ifixit.webapp.domain.ItemType;
import com.ifixit.webapp.repository.ItemTypeRepository;
import com.ifixit.webapp.service.dto.ItemTypeDTO;
import com.ifixit.webapp.service.mapper.ItemTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing ItemType.
 */
@Service
@Transactional
public class ItemTypeServiceImpl implements ItemTypeService{

    private final Logger log = LoggerFactory.getLogger(ItemTypeServiceImpl.class);

    private final ItemTypeRepository itemTypeRepository;

    private final ItemTypeMapper itemTypeMapper;

    public ItemTypeServiceImpl(ItemTypeRepository itemTypeRepository, ItemTypeMapper itemTypeMapper) {
        this.itemTypeRepository = itemTypeRepository;
        this.itemTypeMapper = itemTypeMapper;
    }

    /**
     * Save a itemType.
     *
     * @param itemTypeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ItemTypeDTO save(ItemTypeDTO itemTypeDTO) {
        log.debug("Request to save ItemType : {}", itemTypeDTO);
        ItemType itemType = itemTypeMapper.toEntity(itemTypeDTO);
        itemType = itemTypeRepository.save(itemType);
        return itemTypeMapper.toDto(itemType);
    }

    /**
     *  Get all the itemTypes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ItemTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ItemTypes");
        return itemTypeRepository.findAll(pageable)
            .map(itemTypeMapper::toDto);
    }

    /**
     *  Get one itemType by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ItemTypeDTO findOne(Long id) {
        log.debug("Request to get ItemType : {}", id);
        ItemType itemType = itemTypeRepository.findOne(id);
        return itemTypeMapper.toDto(itemType);
    }

    /**
     *  Delete the  itemType by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ItemType : {}", id);
        itemTypeRepository.delete(id);
    }
}
