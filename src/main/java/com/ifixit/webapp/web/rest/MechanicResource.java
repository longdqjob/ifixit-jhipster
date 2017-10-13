package com.ifixit.webapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ifixit.webapp.domain.User;
import com.ifixit.webapp.service.CompanyService;
import com.ifixit.webapp.service.MechanicService;
import com.ifixit.webapp.service.UserService;
import com.ifixit.webapp.service.dto.MaterialDTO;
import com.ifixit.webapp.web.rest.util.HeaderUtil;
import com.ifixit.webapp.web.rest.util.PaginationUtil;
import com.ifixit.webapp.service.dto.MechanicDTO;
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
import java.util.ArrayList;

import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * REST controller for managing Mechanic.
 */
@RestController
@RequestMapping("/api")
public class MechanicResource {

    private final Logger log = LoggerFactory.getLogger(MechanicResource.class);

    private static final String ENTITY_NAME = "mechanic";

    private final MechanicService mechanicService;

    public MechanicResource(MechanicService mechanicService, CompanyService companyService) {
        this.mechanicService = mechanicService;
        this.companyService = companyService;
    }

    /**
     * POST /mechanics : Create a new mechanic.
     *
     * @param mechanicDTO the mechanicDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the
     * new mechanicDTO, or with status 400 (Bad Request) if the mechanic has
     * already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mechanics")
    @Timed
    public ResponseEntity<MechanicDTO> createMechanic(@Valid @RequestBody MechanicDTO mechanicDTO) throws URISyntaxException {
        log.debug("REST request to save Mechanic : {}", mechanicDTO);
        if (mechanicDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new mechanic cannot already have an ID")).body(null);
        }
        MechanicDTO result = mechanicService.save(mechanicDTO);
        return ResponseEntity.created(new URI("/api/mechanics/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * PUT /mechanics : Updates an existing mechanic.
     *
     * @param mechanicDTO the mechanicDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated
     * mechanicDTO, or with status 400 (Bad Request) if the mechanicDTO is not
     * valid, or with status 500 (Internal Server Error) if the mechanicDTO
     * couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mechanics")
    @Timed
    public ResponseEntity<MechanicDTO> updateMechanic(@Valid @RequestBody MechanicDTO mechanicDTO) throws URISyntaxException {
        log.debug("REST request to update Mechanic : {}", mechanicDTO);
        if (mechanicDTO.getId() == null) {
            return createMechanic(mechanicDTO);
        }
        MechanicDTO result = mechanicService.save(mechanicDTO);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mechanicDTO.getId().toString()))
                .body(result);
    }

    /**
     * GET /mechanics : get all the mechanics.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mechanics
     * in body
     */
    @GetMapping("/mechanics")
    @Timed
    public ResponseEntity<List<MechanicDTO>> getAllMechanics(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Mechanics");
//        Page<MechanicDTO> page = mechanicService.findAll(pageable);
        Long engId = 1L;
        List<Long> listSys = new ArrayList<>();
        String lstTmp = "";
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.debug("-------getAuthentication: " + auth.getPrincipal());
        try {
            org.springframework.security.core.userdetails.User users = (org.springframework.security.core.userdetails.User) auth.getPrincipal();
            log.debug("-------userdetails: " + users.getUsername());
            Optional<User> lstUser = userService.getUserWithAuthoritiesByLogin(users.getUsername());
            User user = lstUser.get();
            if (user.getCompany() == null) {
                log.debug("-------companyService null: ");
                listSys = companyService.getAllId();
                log.debug("-------companyService getAllId: " + StringUtils.join(listSys, ","));
            } else {
                log.debug("-------companyService: " + user.getCompany().getId() + " - " + user.getCompany().getName());
                engId = user.getCompany().getId();
            }
        } catch (Exception ex) {
            log.error("ERROR getAllMechanics: ", ex);
        }
        if (listSys.isEmpty()) {
            lstTmp = companyService.getChild(engId);
            if (StringUtils.isBlank(lstTmp)) {
                listSys = new ArrayList<>(1);
                listSys.add(engId);
            } else {
                lstTmp += "," + String.valueOf(engId);
                String[] tmp = lstTmp.split(",");

                listSys = new ArrayList<>(tmp.length);
                for (String eng : tmp) {
                    listSys.add(Long.parseLong(eng));
                }
            }
        }

        Page<MechanicDTO> page = mechanicService.getMechanics(listSys, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mechanics");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /mechanics/:id : get the "id" mechanic.
     *
     * @param id the id of the mechanicDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the
     * mechanicDTO, or with status 404 (Not Found)
     */
    @GetMapping("/mechanics/{id}")
    @Timed
    public ResponseEntity<MechanicDTO> getMechanic(@PathVariable Long id) {
        log.debug("REST request to get Mechanic : {}", id);
        MechanicDTO mechanicDTO = mechanicService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mechanicDTO));
    }

    /**
     * DELETE /mechanics/:id : delete the "id" mechanic.
     *
     * @param id the id of the mechanicDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mechanics/{id}")
    @Timed
    public ResponseEntity<Void> deleteMechanic(@PathVariable Long id) {
        log.debug("REST request to delete Mechanic : {}", id);
        mechanicService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    //Add thuyetLV
    @Inject
    private UserService userService;

    private final CompanyService companyService;

    @GetMapping("/mechanics/all/{id}")
    @Timed
    public ResponseEntity<List<MechanicDTO>> getMechanics(@PathVariable("id") Optional<String> id, @ApiParam Pageable pageable) {
        log.debug("REST request to get a page of getMechanics");
        Long engId = 1L;
        List<Long> listSys = new ArrayList<>();
        String lstTmp = "";
        if (id == null || StringUtils.isBlank(id.get()) || !StringUtils.isNumeric(id.get())) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            log.debug("-------getAuthentication: " + auth.getPrincipal());
            try {
                org.springframework.security.core.userdetails.User users = (org.springframework.security.core.userdetails.User) auth.getPrincipal();
                log.debug("-------userdetails: " + users.getUsername());
                Optional<User> lstUser = userService.getUserWithAuthoritiesByLogin(users.getUsername());
                User user = lstUser.get();
                if (user.getCompany() == null) {
                    log.debug("-------companyService null: ");
                    listSys = companyService.getAllId();
                    log.debug("-------companyService getAllId: " + StringUtils.join(listSys, ","));
                } else {
                    log.debug("-------companyService: " + user.getGroupEngineer().getId() + " - " + user.getGroupEngineer().getName());
                    engId = user.getGroupEngineer().getId();
                }
            } catch (Exception ex) {
                log.error("ERROR findTopProducts: ", ex);
            }
        } else {
            engId = Long.parseLong(id.get());
        }

        if (listSys.isEmpty()) {
            lstTmp = companyService.getChild(engId);
            if (StringUtils.isBlank(lstTmp)) {
                listSys = new ArrayList<>(1);
                listSys.add(engId);
            } else {
                lstTmp += "," + String.valueOf(engId);
                String[] tmp = lstTmp.split(",");

                listSys = new ArrayList<>(tmp.length);
                for (String eng : tmp) {
                    listSys.add(Long.parseLong(eng));
                }
            }
        }

        Page<MechanicDTO> page = mechanicService.getMechanics(listSys, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/work-orders/all");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
}
