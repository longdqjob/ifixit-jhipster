package com.ifixit.webapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ifixit.webapp.service.ItemTypeService;
import com.ifixit.webapp.web.rest.util.HeaderUtil;
import com.ifixit.webapp.web.rest.util.PaginationUtil;
import com.ifixit.webapp.service.dto.ItemTypeDTO;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ItemType.
 */
@RestController
@RequestMapping("/api")
public class ItemTypeResource {

    private final Logger log = LoggerFactory.getLogger(ItemTypeResource.class);

    private static final String ENTITY_NAME = "itemType";

    private final ItemTypeService itemTypeService;

    public ItemTypeResource(ItemTypeService itemTypeService) {
        this.itemTypeService = itemTypeService;
    }

    /**
     * POST  /item-types : Create a new itemType.
     *
     * @param itemTypeDTO the itemTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new itemTypeDTO, or with status 400 (Bad Request) if the itemType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/item-types")
    @Timed
    public ResponseEntity<ItemTypeDTO> createItemType(@Valid @RequestBody ItemTypeDTO itemTypeDTO) throws URISyntaxException {
        log.debug("REST request to save ItemType : {}", itemTypeDTO);
        if (itemTypeDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new itemType cannot already have an ID")).body(null);
        }
        ItemTypeDTO result = itemTypeService.save(itemTypeDTO);
        return ResponseEntity.created(new URI("/api/item-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /item-types : Updates an existing itemType.
     *
     * @param itemTypeDTO the itemTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated itemTypeDTO,
     * or with status 400 (Bad Request) if the itemTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the itemTypeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/item-types")
    @Timed
    public ResponseEntity<ItemTypeDTO> updateItemType(@Valid @RequestBody ItemTypeDTO itemTypeDTO) throws URISyntaxException {
        log.debug("REST request to update ItemType : {}", itemTypeDTO);
        if (itemTypeDTO.getId() == null) {
            return createItemType(itemTypeDTO);
        }
        ItemTypeDTO result = itemTypeService.save(itemTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, itemTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /item-types : get all the itemTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of itemTypes in body
     */
    @GetMapping("/item-types")
    @Timed
    public ResponseEntity<List<ItemTypeDTO>> getAllItemTypes(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of ItemTypes");
        Page<ItemTypeDTO> page = itemTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/item-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /item-types/:id : get the "id" itemType.
     *
     * @param id the id of the itemTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the itemTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/item-types/{id}")
    @Timed
    public ResponseEntity<ItemTypeDTO> getItemType(@PathVariable Long id) {
        log.debug("REST request to get ItemType : {}", id);
        ItemTypeDTO itemTypeDTO = itemTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(itemTypeDTO));
    }

    /**
     * DELETE  /item-types/:id : delete the "id" itemType.
     *
     * @param id the id of the itemTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/item-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteItemType(@PathVariable Long id) {
        log.debug("REST request to delete ItemType : {}", id);
        itemTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
