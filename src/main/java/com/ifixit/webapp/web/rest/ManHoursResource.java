package com.ifixit.webapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ifixit.webapp.service.ManHoursService;
import com.ifixit.webapp.web.rest.util.HeaderUtil;
import com.ifixit.webapp.service.dto.ManHoursDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ManHours.
 */
@RestController
@RequestMapping("/api")
public class ManHoursResource {

    private final Logger log = LoggerFactory.getLogger(ManHoursResource.class);

    private static final String ENTITY_NAME = "manHours";

    private final ManHoursService manHoursService;

    public ManHoursResource(ManHoursService manHoursService) {
        this.manHoursService = manHoursService;
    }

    /**
     * POST  /man-hours : Create a new manHours.
     *
     * @param manHoursDTO the manHoursDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new manHoursDTO, or with status 400 (Bad Request) if the manHours has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/man-hours")
    @Timed
    public ResponseEntity<ManHoursDTO> createManHours(@Valid @RequestBody ManHoursDTO manHoursDTO) throws URISyntaxException {
        log.debug("REST request to save ManHours : {}", manHoursDTO);
        if (manHoursDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new manHours cannot already have an ID")).body(null);
        }
        ManHoursDTO result = manHoursService.save(manHoursDTO);
        return ResponseEntity.created(new URI("/api/man-hours/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /man-hours : Updates an existing manHours.
     *
     * @param manHoursDTO the manHoursDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated manHoursDTO,
     * or with status 400 (Bad Request) if the manHoursDTO is not valid,
     * or with status 500 (Internal Server Error) if the manHoursDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/man-hours")
    @Timed
    public ResponseEntity<ManHoursDTO> updateManHours(@Valid @RequestBody ManHoursDTO manHoursDTO) throws URISyntaxException {
        log.debug("REST request to update ManHours : {}", manHoursDTO);
        if (manHoursDTO.getId() == null) {
            return createManHours(manHoursDTO);
        }
        ManHoursDTO result = manHoursService.save(manHoursDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, manHoursDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /man-hours : get all the manHours.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of manHours in body
     */
    @GetMapping("/man-hours")
    @Timed
    public List<ManHoursDTO> getAllManHours() {
        log.debug("REST request to get all ManHours");
        return manHoursService.findAll();
        }

    /**
     * GET  /man-hours/:id : get the "id" manHours.
     *
     * @param id the id of the manHoursDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the manHoursDTO, or with status 404 (Not Found)
     */
    @GetMapping("/man-hours/{id}")
    @Timed
    public ResponseEntity<ManHoursDTO> getManHours(@PathVariable Long id) {
        log.debug("REST request to get ManHours : {}", id);
        ManHoursDTO manHoursDTO = manHoursService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(manHoursDTO));
    }

    /**
     * DELETE  /man-hours/:id : delete the "id" manHours.
     *
     * @param id the id of the manHoursDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/man-hours/{id}")
    @Timed
    public ResponseEntity<Void> deleteManHours(@PathVariable Long id) {
        log.debug("REST request to delete ManHours : {}", id);
        manHoursService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
