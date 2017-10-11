package com.ifixit.webapp.service.impl;

import com.ifixit.webapp.service.StockItemService;
import com.ifixit.webapp.domain.StockItem;
import com.ifixit.webapp.repository.StockItemRepository;
import com.ifixit.webapp.service.dto.StockItemDTO;
import com.ifixit.webapp.service.mapper.StockItemMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing StockItem.
 */
@Service
@Transactional
public class StockItemServiceImpl implements StockItemService{

    private final Logger log = LoggerFactory.getLogger(StockItemServiceImpl.class);

    private final StockItemRepository stockItemRepository;

    private final StockItemMapper stockItemMapper;

    public StockItemServiceImpl(StockItemRepository stockItemRepository, StockItemMapper stockItemMapper) {
        this.stockItemRepository = stockItemRepository;
        this.stockItemMapper = stockItemMapper;
    }

    /**
     * Save a stockItem.
     *
     * @param stockItemDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public StockItemDTO save(StockItemDTO stockItemDTO) {
        log.debug("Request to save StockItem : {}", stockItemDTO);
        StockItem stockItem = stockItemMapper.toEntity(stockItemDTO);
        stockItem = stockItemRepository.save(stockItem);
        return stockItemMapper.toDto(stockItem);
    }

    /**
     *  Get all the stockItems.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<StockItemDTO> findAll() {
        log.debug("Request to get all StockItems");
        return stockItemRepository.findAll().stream()
            .map(stockItemMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one stockItem by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public StockItemDTO findOne(Long id) {
        log.debug("Request to get StockItem : {}", id);
        StockItem stockItem = stockItemRepository.findOne(id);
        return stockItemMapper.toDto(stockItem);
    }

    /**
     *  Delete the  stockItem by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete StockItem : {}", id);
        stockItemRepository.delete(id);
    }
}
