package com.ifixit.webapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ifixit.webapp.service.GroupEngineerService;
import com.ifixit.webapp.web.rest.util.HeaderUtil;
import com.ifixit.webapp.web.rest.util.PaginationUtil;
import com.ifixit.webapp.service.dto.GroupEngineerDTO;
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
 * REST controller for managing GroupEngineer.
 */
@RestController
@RequestMapping("/api")
public class GroupEngineerResource {

    private final Logger log = LoggerFactory.getLogger(GroupEngineerResource.class);

    private static final String ENTITY_NAME = "groupEngineer";

    private final GroupEngineerService groupEngineerService;

    public GroupEngineerResource(GroupEngineerService groupEngineerService) {
        this.groupEngineerService = groupEngineerService;
    }

    /**
     * POST  /group-engineers : Create a new groupEngineer.
     *
     * @param groupEngineerDTO the groupEngineerDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new groupEngineerDTO, or with status 400 (Bad Request) if the groupEngineer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/group-engineers")
    @Timed
    public ResponseEntity<GroupEngineerDTO> createGroupEngineer(@Valid @RequestBody GroupEngineerDTO groupEngineerDTO) throws URISyntaxException {
        log.debug("REST request to save GroupEngineer : {}", groupEngineerDTO);
        if (groupEngineerDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new groupEngineer cannot already have an ID")).body(null);
        }
        GroupEngineerDTO result = groupEngineerService.save(groupEngineerDTO);
        return ResponseEntity.created(new URI("/api/group-engineers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /group-engineers : Updates an existing groupEngineer.
     *
     * @param groupEngineerDTO the groupEngineerDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated groupEngineerDTO,
     * or with status 400 (Bad Request) if the groupEngineerDTO is not valid,
     * or with status 500 (Internal Server Error) if the groupEngineerDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/group-engineers")
    @Timed
    public ResponseEntity<GroupEngineerDTO> updateGroupEngineer(@Valid @RequestBody GroupEngineerDTO groupEngineerDTO) throws URISyntaxException {
        log.debug("REST request to update GroupEngineer : {}", groupEngineerDTO);
        if (groupEngineerDTO.getId() == null) {
            return createGroupEngineer(groupEngineerDTO);
        }
        GroupEngineerDTO result = groupEngineerService.save(groupEngineerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, groupEngineerDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /group-engineers : get all the groupEngineers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of groupEngineers in body
     */
    @GetMapping("/group-engineers")
    @Timed
    public ResponseEntity<List<GroupEngineerDTO>> getAllGroupEngineers(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of GroupEngineers");
        Page<GroupEngineerDTO> page = groupEngineerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/group-engineers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /group-engineers/:id : get the "id" groupEngineer.
     *
     * @param id the id of the groupEngineerDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the groupEngineerDTO, or with status 404 (Not Found)
     */
    @GetMapping("/group-engineers/{id}")
    @Timed
    public ResponseEntity<GroupEngineerDTO> getGroupEngineer(@PathVariable Long id) {
        log.debug("REST request to get GroupEngineer : {}", id);
        GroupEngineerDTO groupEngineerDTO = groupEngineerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(groupEngineerDTO));
    }

    /**
     * DELETE  /group-engineers/:id : delete the "id" groupEngineer.
     *
     * @param id the id of the groupEngineerDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/group-engineers/{id}")
    @Timed
    public ResponseEntity<Void> deleteGroupEngineer(@PathVariable Long id) {
        log.debug("REST request to delete GroupEngineer : {}", id);
        groupEngineerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    
    //Add
    @GetMapping("/group-engineers/all")
    @Timed
    public ResponseEntity<List<GroupEngineerDTO>> getAllGrpEngineers(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of GroupEngineers");
        Page<GroupEngineerDTO> page = groupEngineerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/group-engineers/all");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
}
