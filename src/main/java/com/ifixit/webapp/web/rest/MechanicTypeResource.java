package com.ifixit.webapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ifixit.webapp.service.MechanicTypeService;
import com.ifixit.webapp.web.rest.util.HeaderUtil;
import com.ifixit.webapp.web.rest.util.PaginationUtil;
import com.ifixit.webapp.service.dto.MechanicTypeDTO;
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
 * REST controller for managing MechanicType.
 */
@RestController
@RequestMapping("/api")
public class MechanicTypeResource {

    private final Logger log = LoggerFactory.getLogger(MechanicTypeResource.class);

    private static final String ENTITY_NAME = "mechanicType";

    private final MechanicTypeService mechanicTypeService;

    public MechanicTypeResource(MechanicTypeService mechanicTypeService) {
        this.mechanicTypeService = mechanicTypeService;
    }

    /**
     * POST  /mechanic-types : Create a new mechanicType.
     *
     * @param mechanicTypeDTO the mechanicTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mechanicTypeDTO, or with status 400 (Bad Request) if the mechanicType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mechanic-types")
    @Timed
    public ResponseEntity<MechanicTypeDTO> createMechanicType(@Valid @RequestBody MechanicTypeDTO mechanicTypeDTO) throws URISyntaxException {
        log.debug("REST request to save MechanicType : {}", mechanicTypeDTO);
        if (mechanicTypeDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new mechanicType cannot already have an ID")).body(null);
        }
        MechanicTypeDTO result = mechanicTypeService.save(mechanicTypeDTO);
        return ResponseEntity.created(new URI("/api/mechanic-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mechanic-types : Updates an existing mechanicType.
     *
     * @param mechanicTypeDTO the mechanicTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mechanicTypeDTO,
     * or with status 400 (Bad Request) if the mechanicTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the mechanicTypeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mechanic-types")
    @Timed
    public ResponseEntity<MechanicTypeDTO> updateMechanicType(@Valid @RequestBody MechanicTypeDTO mechanicTypeDTO) throws URISyntaxException {
        log.debug("REST request to update MechanicType : {}", mechanicTypeDTO);
        if (mechanicTypeDTO.getId() == null) {
            return createMechanicType(mechanicTypeDTO);
        }
        MechanicTypeDTO result = mechanicTypeService.save(mechanicTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mechanicTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /mechanic-types : get all the mechanicTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mechanicTypes in body
     */
    @GetMapping("/mechanic-types")
    @Timed
    public ResponseEntity<List<MechanicTypeDTO>> getAllMechanicTypes(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of MechanicTypes");
        Page<MechanicTypeDTO> page = mechanicTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mechanic-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /mechanic-types/:id : get the "id" mechanicType.
     *
     * @param id the id of the mechanicTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mechanicTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/mechanic-types/{id}")
    @Timed
    public ResponseEntity<MechanicTypeDTO> getMechanicType(@PathVariable Long id) {
        log.debug("REST request to get MechanicType : {}", id);
        MechanicTypeDTO mechanicTypeDTO = mechanicTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mechanicTypeDTO));
    }

    /**
     * DELETE  /mechanic-types/:id : delete the "id" mechanicType.
     *
     * @param id the id of the mechanicTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mechanic-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteMechanicType(@PathVariable Long id) {
        log.debug("REST request to delete MechanicType : {}", id);
        mechanicTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
