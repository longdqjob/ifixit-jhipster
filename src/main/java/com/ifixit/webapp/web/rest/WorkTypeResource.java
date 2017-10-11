package com.ifixit.webapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ifixit.webapp.service.WorkTypeService;
import com.ifixit.webapp.web.rest.util.HeaderUtil;
import com.ifixit.webapp.web.rest.util.PaginationUtil;
import com.ifixit.webapp.service.dto.WorkTypeDTO;
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
 * REST controller for managing WorkType.
 */
@RestController
@RequestMapping("/api")
public class WorkTypeResource {

    private final Logger log = LoggerFactory.getLogger(WorkTypeResource.class);

    private static final String ENTITY_NAME = "workType";

    private final WorkTypeService workTypeService;

    public WorkTypeResource(WorkTypeService workTypeService) {
        this.workTypeService = workTypeService;
    }

    /**
     * POST  /work-types : Create a new workType.
     *
     * @param workTypeDTO the workTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new workTypeDTO, or with status 400 (Bad Request) if the workType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/work-types")
    @Timed
    public ResponseEntity<WorkTypeDTO> createWorkType(@Valid @RequestBody WorkTypeDTO workTypeDTO) throws URISyntaxException {
        log.debug("REST request to save WorkType : {}", workTypeDTO);
        if (workTypeDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new workType cannot already have an ID")).body(null);
        }
        WorkTypeDTO result = workTypeService.save(workTypeDTO);
        return ResponseEntity.created(new URI("/api/work-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /work-types : Updates an existing workType.
     *
     * @param workTypeDTO the workTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated workTypeDTO,
     * or with status 400 (Bad Request) if the workTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the workTypeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/work-types")
    @Timed
    public ResponseEntity<WorkTypeDTO> updateWorkType(@Valid @RequestBody WorkTypeDTO workTypeDTO) throws URISyntaxException {
        log.debug("REST request to update WorkType : {}", workTypeDTO);
        if (workTypeDTO.getId() == null) {
            return createWorkType(workTypeDTO);
        }
        WorkTypeDTO result = workTypeService.save(workTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, workTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /work-types : get all the workTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of workTypes in body
     */
    @GetMapping("/work-types")
    @Timed
    public ResponseEntity<List<WorkTypeDTO>> getAllWorkTypes(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of WorkTypes");
        Page<WorkTypeDTO> page = workTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/work-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /work-types/:id : get the "id" workType.
     *
     * @param id the id of the workTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the workTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/work-types/{id}")
    @Timed
    public ResponseEntity<WorkTypeDTO> getWorkType(@PathVariable Long id) {
        log.debug("REST request to get WorkType : {}", id);
        WorkTypeDTO workTypeDTO = workTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(workTypeDTO));
    }

    /**
     * DELETE  /work-types/:id : delete the "id" workType.
     *
     * @param id the id of the workTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/work-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteWorkType(@PathVariable Long id) {
        log.debug("REST request to delete WorkType : {}", id);
        workTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
