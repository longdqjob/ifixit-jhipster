package com.ifixit.webapp.service.impl;

import com.ifixit.webapp.service.GroupEngineerService;
import com.ifixit.webapp.domain.GroupEngineer;
import com.ifixit.webapp.repository.GroupEngineerRepository;
import com.ifixit.webapp.service.dto.GroupEngineerDTO;
import com.ifixit.webapp.service.mapper.GroupEngineerMapper;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing GroupEngineer.
 */
@Service
@Transactional
public class GroupEngineerServiceImpl implements GroupEngineerService {

    private final Logger log = LoggerFactory.getLogger(GroupEngineerServiceImpl.class);

    private final GroupEngineerRepository groupEngineerRepository;

    private final GroupEngineerMapper groupEngineerMapper;

    public GroupEngineerServiceImpl(GroupEngineerRepository groupEngineerRepository, GroupEngineerMapper groupEngineerMapper) {
        this.groupEngineerRepository = groupEngineerRepository;
        this.groupEngineerMapper = groupEngineerMapper;
    }

    /**
     * Save a groupEngineer.
     *
     * @param groupEngineerDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public GroupEngineerDTO save(GroupEngineerDTO groupEngineerDTO) {
        log.debug("Request to save GroupEngineer : {}", groupEngineerDTO);
        GroupEngineer groupEngineer = groupEngineerMapper.toEntity(groupEngineerDTO);
        groupEngineer = groupEngineerRepository.save(groupEngineer);
        return groupEngineerMapper.toDto(groupEngineer);
    }

    /**
     * Get all the groupEngineers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<GroupEngineerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all GroupEngineers");
        return groupEngineerRepository.findAll(pageable)
                .map(groupEngineerMapper::toDto);
    }

    /**
     * Get one groupEngineer by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public GroupEngineerDTO findOne(Long id) {
        log.debug("Request to get GroupEngineer : {}", id);
        GroupEngineer groupEngineer = groupEngineerRepository.findOne(id);
        return groupEngineerMapper.toDto(groupEngineer);
    }

    /**
     * Delete the groupEngineer by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete GroupEngineer : {}", id);
        groupEngineerRepository.delete(id);
    }

    //Add
    @Override
    public List<Long> getAllId() {
        return groupEngineerRepository.getAllId();
    }

    @Override
    public String getChild(Long id) {
        log.debug("Request to get getChild: " + id);
        return groupEngineerRepository.getChild(id);
    }

}
