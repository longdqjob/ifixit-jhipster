package com.ifixit.webapp.service.impl;

import com.ifixit.webapp.service.WorkOrderService;
import com.ifixit.webapp.domain.WorkOrder;
import com.ifixit.webapp.repository.WorkOrderRepository;
import com.ifixit.webapp.service.dto.WorkOrderDTO;
import com.ifixit.webapp.service.mapper.WorkOrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing WorkOrder.
 */
@Service
@Transactional
public class WorkOrderServiceImpl implements WorkOrderService{

    private final Logger log = LoggerFactory.getLogger(WorkOrderServiceImpl.class);

    private final WorkOrderRepository workOrderRepository;

    private final WorkOrderMapper workOrderMapper;

    public WorkOrderServiceImpl(WorkOrderRepository workOrderRepository, WorkOrderMapper workOrderMapper) {
        this.workOrderRepository = workOrderRepository;
        this.workOrderMapper = workOrderMapper;
    }

    /**
     * Save a workOrder.
     *
     * @param workOrderDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public WorkOrderDTO save(WorkOrderDTO workOrderDTO) {
        log.debug("Request to save WorkOrder : {}", workOrderDTO);
        WorkOrder workOrder = workOrderMapper.toEntity(workOrderDTO);
        workOrder = workOrderRepository.save(workOrder);
        return workOrderMapper.toDto(workOrder);
    }

    /**
     *  Get all the workOrders.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<WorkOrderDTO> findAll(Pageable pageable) {
        log.debug("Request to get all WorkOrders");
        return workOrderRepository.findAll(pageable)
            .map(workOrderMapper::toDto);
    }

    /**
     *  Get one workOrder by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public WorkOrderDTO findOne(Long id) {
        log.debug("Request to get WorkOrder : {}", id);
        WorkOrder workOrder = workOrderRepository.findOne(id);
        return workOrderMapper.toDto(workOrder);
    }

    /**
     *  Delete the  workOrder by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete WorkOrder : {}", id);
        workOrderRepository.delete(id);
    }
}
