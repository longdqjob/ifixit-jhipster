package com.ifixit.webapp.service.impl;

import com.google.gson.Gson;
import com.ifixit.webapp.service.MaterialService;
import com.ifixit.webapp.domain.Material;
import com.ifixit.webapp.repository.MaterialRepository;
import com.ifixit.webapp.service.dto.MaterialDTO;
import com.ifixit.webapp.service.dto.WorkOrderDTO;
import com.ifixit.webapp.service.mapper.MaterialMapper;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing Material.
 */
@Service
@Transactional
public class MaterialServiceImpl implements MaterialService {

    private final Logger log = LoggerFactory.getLogger(MaterialServiceImpl.class);

    private final MaterialRepository materialRepository;

    private final MaterialMapper materialMapper;

    public MaterialServiceImpl(MaterialRepository materialRepository, MaterialMapper materialMapper) {
        this.materialRepository = materialRepository;
        this.materialMapper = materialMapper;
    }

    /**
     * Save a material.
     *
     * @param materialDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MaterialDTO save(MaterialDTO materialDTO) {
        log.debug("Request to save Material : {}", materialDTO);
        Material material = materialMapper.toEntity(materialDTO);
        material = materialRepository.save(material);
        return materialMapper.toDto(material);
    }

    /**
     * Get all the materials.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MaterialDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Materials");
        return materialRepository.findAll(pageable)
                .map(materialMapper::toDto);
    }

    /**
     * Get one material by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MaterialDTO findOne(Long id) {
        log.debug("Request to get Material : {}", id);
        Material material = materialRepository.findOne(id);
//        Gson gson = new Gson();
//
//        log.info("findOne: " + id + " - " + gson.toJson(material));

        return materialMapper.toDto(material);
    }

    /**
     * Delete the material by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Material : {}", id);
        materialRepository.delete(id);
    }

    //ThuyetLV Add
    @Override
    public MaterialDTO getData(Long id) {
        log.debug("Request to getData Material : {}", id);
        MaterialDTO material = materialRepository.getData(id);
        Gson gson = new Gson();

        log.info("findOne: " + id + " - " + gson.toJson(material));

        return material;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MaterialDTO> getMaterials(Pageable pageable) {
        log.debug("Request to get all Materials");
        return materialRepository.getMaterials(pageable);
    }
}
