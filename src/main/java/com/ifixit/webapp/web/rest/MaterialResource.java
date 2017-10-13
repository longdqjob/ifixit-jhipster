package com.ifixit.webapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.google.gson.Gson;
import com.ifixit.webapp.service.CompanyService;
import com.ifixit.webapp.service.MaterialService;
import com.ifixit.webapp.web.rest.util.HeaderUtil;
import com.ifixit.webapp.web.rest.util.PaginationUtil;
import com.ifixit.webapp.service.dto.MaterialDTO;
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
 * REST controller for managing Material.
 */
@RestController
@RequestMapping("/api")
public class MaterialResource {

    private final Logger log = LoggerFactory.getLogger(MaterialResource.class);

    private static final String ENTITY_NAME = "material";

    private final MaterialService materialService;

    public MaterialResource(MaterialService materialService) {
        this.materialService = materialService;
    }

    /**
     * POST /materials : Create a new material.
     *
     * @param materialDTO the materialDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the
     * new materialDTO, or with status 400 (Bad Request) if the material has
     * already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/materials")
    @Timed
    public ResponseEntity<MaterialDTO> createMaterial(@Valid @RequestBody MaterialDTO materialDTO) throws URISyntaxException {
        log.debug("REST request to save Material : {}", materialDTO);
        if (materialDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new material cannot already have an ID")).body(null);
        }
        MaterialDTO result = materialService.save(materialDTO);
        return ResponseEntity.created(new URI("/api/materials/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * PUT /materials : Updates an existing material.
     *
     * @param materialDTO the materialDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated
     * materialDTO, or with status 400 (Bad Request) if the materialDTO is not
     * valid, or with status 500 (Internal Server Error) if the materialDTO
     * couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/materials")
    @Timed
    public ResponseEntity<MaterialDTO> updateMaterial(@Valid @RequestBody MaterialDTO materialDTO) throws URISyntaxException {
        log.debug("REST request to update Material : {}", materialDTO);
        if (materialDTO.getId() == null) {
            return createMaterial(materialDTO);
        }
        MaterialDTO result = materialService.save(materialDTO);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, materialDTO.getId().toString()))
                .body(result);
    }

    /**
     * GET /materials : get all the materials.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of materials
     * in body
     */
    @GetMapping("/materials")
    @Timed
    public ResponseEntity<List<MaterialDTO>> getAllMaterials(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Materials");
        Page<MaterialDTO> page = materialService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/materials");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /materials/:id : get the "id" material.
     *
     * @param id the id of the materialDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the
     * materialDTO, or with status 404 (Not Found)
     */
    @GetMapping("/materials/{id}")
    @Timed
    public ResponseEntity<MaterialDTO> getMaterial(@PathVariable Long id) {
        log.debug("REST request to get Material : {}", id);
        MaterialDTO materialDTO = materialService.findOne(id);
//        MaterialDTO materialDTO = materialService.getData(id);
        Gson gson = new Gson();
        log.info("materialDTO: " + id + " - " + gson.toJson(materialDTO));
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(materialDTO));
    }

    /**
     * DELETE /materials/:id : delete the "id" material.
     *
     * @param id the id of the materialDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/materials/{id}")
    @Timed
    public ResponseEntity<Void> deleteMaterial(@PathVariable Long id) {
        log.debug("REST request to delete Material : {}", id);
        materialService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/materials/data/{id}")
    @Timed
    public ResponseEntity<MaterialDTO> getData(@PathVariable Long id) {
        log.debug("REST request to get Material : {}", id);
        MaterialDTO materialDTO = materialService.getData(id);
        Gson gson = new Gson();
        log.info("materialDTO: " + id + " - " + gson.toJson(materialDTO));
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(materialDTO));
    }

    @GetMapping("/materials/all")
    @Timed
    public ResponseEntity<List<MaterialDTO>> getAll(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Materials");
        Page<MaterialDTO> page = materialService.getMaterials(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/materials/all");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

//    //Add thuyetLV
//    @Inject
//    private UserService userService;
//    
//    private final CompanyService companyService;
//    
//    @GetMapping("/materials/all/{id}")
//    @Timed
//    public ResponseEntity<List<MaterialDTO>> getMaterials(@PathVariable("id") Optional<String> id, @ApiParam Pageable pageable) {
//        log.debug("REST request to get a page of getMaterials");
//        Long engId = 1L;
//        List<Long> listEng = new ArrayList<>();
//        String lstTmp = "";
//        if (id == null || StringUtils.isBlank(id.get()) || !StringUtils.isNumeric(id.get())) {
//            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//            log.debug("-------getAuthentication: " + auth.getPrincipal());
//            try {
//                org.springframework.security.core.userdetails.User users = (org.springframework.security.core.userdetails.User) auth.getPrincipal();
//                log.debug("-------userdetails: " + users.getUsername());
//                Optional<User> lstUser = userService.getUserWithAuthoritiesByLogin(users.getUsername());
//                User user = lstUser.get();
//                if (user.getCompany()== null) {
//                    log.debug("-------companyService null: ");
//                    listEng = companyService.getAllId();
//                    log.debug("-------companyService getAllId: " + StringUtils.join(listEng, ","));
//                } else {
//                    log.debug("-------companyService: " + user.getGroupEngineer().getId() + " - " + user.getGroupEngineer().getName());
//                    engId = user.getGroupEngineer().getId();
//                }
//            } catch (Exception ex) {
//                log.error("ERROR findTopProducts: ", ex);
//            }
//        } else {
//            engId = Long.parseLong(id.get());
//        }
//
//        if (listEng.isEmpty()) {
//            lstTmp = companyService.getChild(engId);
//            if (StringUtils.isBlank(lstTmp)) {
//                listEng = new ArrayList<>(1);
//                listEng.add(engId);
//            } else {
//                lstTmp += "," + String.valueOf(engId);
//                String[] tmp = lstTmp.split(",");
//
//                listEng = new ArrayList<>(tmp.length);
//                for (String eng : tmp) {
//                    listEng.add(Long.parseLong(eng));
//                }
//            }
//        }
//
//        Page<MaterialDTO> page = companyService.getWorkOrders(listEng, pageable);
//        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/work-orders/all");
//        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
//    }
}
