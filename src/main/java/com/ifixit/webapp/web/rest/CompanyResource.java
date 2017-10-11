package com.ifixit.webapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.google.gson.Gson;
import com.ifixit.webapp.domain.Company;
import com.ifixit.webapp.domain.User;
import com.ifixit.webapp.obj.TreeObj;
import com.ifixit.webapp.service.CompanyService;
import com.ifixit.webapp.service.UserService;
import com.ifixit.webapp.web.rest.util.HeaderUtil;
import com.ifixit.webapp.web.rest.util.PaginationUtil;
import com.ifixit.webapp.service.dto.CompanyDTO;
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
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * REST controller for managing Company.
 */
@RestController
@RequestMapping("/api")
public class CompanyResource {

    private final Logger log = LoggerFactory.getLogger(CompanyResource.class);

    private static final String ENTITY_NAME = "company";

    private final CompanyService companyService;
    @Inject
    private UserService userService;

    public CompanyResource(CompanyService companyService) {
        this.companyService = companyService;
    }

    /**
     * POST /companies : Create a new company.
     *
     * @param companyDTO the companyDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the
     * new companyDTO, or with status 400 (Bad Request) if the company has
     * already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/companies")
    @Timed
    public ResponseEntity<CompanyDTO> createCompany(@Valid @RequestBody CompanyDTO companyDTO) throws URISyntaxException {
        log.debug("REST request to save Company : {}", companyDTO);
        if (companyDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new company cannot already have an ID")).body(null);
        }
        CompanyDTO result = companyService.save(companyDTO);
        return ResponseEntity.created(new URI("/api/companies/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * PUT /companies : Updates an existing company.
     *
     * @param companyDTO the companyDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated
     * companyDTO, or with status 400 (Bad Request) if the companyDTO is not
     * valid, or with status 500 (Internal Server Error) if the companyDTO
     * couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/companies")
    @Timed
    public ResponseEntity<CompanyDTO> updateCompany(@Valid @RequestBody CompanyDTO companyDTO) throws URISyntaxException {
        log.debug("REST request to update Company : {}", companyDTO);
        if (companyDTO.getId() == null) {
            return createCompany(companyDTO);
        }
        CompanyDTO result = companyService.save(companyDTO);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, companyDTO.getId().toString()))
                .body(result);
    }

    /**
     * GET /companies : get all the companies.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of companies
     * in body
     */
    @GetMapping("/companies")
    @Timed
    public ResponseEntity<List<CompanyDTO>> getAllCompanies(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Companies");
        Page<CompanyDTO> page = companyService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/companies");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /companies/:id : get the "id" company.
     *
     * @param id the id of the companyDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the
     * companyDTO, or with status 404 (Not Found)
     */
    @GetMapping("/companies/{id}")
    @Timed
    public ResponseEntity<CompanyDTO> getCompany(@PathVariable Long id) {
        log.debug("REST request to get Company : {}", id);
        CompanyDTO companyDTO = companyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(companyDTO));
    }

    /**
     * DELETE /companies/:id : delete the "id" company.
     *
     * @param id the id of the companyDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/companies/{id}")
    @Timed
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        log.debug("REST request to delete Company : {}", id);
        companyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/getChild/{id}")
    @Timed
    public ResponseEntity<String> getChild(@PathVariable Long id) {
        log.debug("REST request to get child of Companies");
        String child = companyService.getChild(id);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, child))
                .body(child);
    }

    @RequestMapping(value = "/getListChild/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getListChild(@PathVariable Long id) throws JSONException {
        log.debug("REST request to get list childrent of Companies");
        String child = companyService.getChild(id);
        String[] tmp = child.split(",");
        List<Long> lstChild = new ArrayList<>(tmp.length);
        for (String idCp : tmp) {
            lstChild.add(Long.parseLong(idCp));
        }

        //List<Company>
        List<Company> listCompany = companyService.getListChild(lstChild);
        List<JSONObject> entities = new ArrayList<JSONObject>();

        JSONObject jSONObject;
        for (Company company : listCompany) {
            jSONObject = new JSONObject();
            jSONObject.put("id", "sấdf");
//            iJSONObject.put("name", company.getName());
//            iJSONObject.put("code", company.getCode());
//            iJSONObject.put("completeCode()", company.getCompleteCode());
//            log.info("-------company: " + company.getName());
//            log.info("-------iJSONObject: " + iJSONObject.getString("name"));
            entities.add(jSONObject);
//            entities.add(iJSONObject);
        }
//        return new ResponseEntity<Object>(entities, HttpStatus.OK);
        Gson gson = new Gson();
        ResponseEntity<String> responseEntity = new ResponseEntity<>(gson.toJson(listCompany),
                new HttpHeaders(),
                HttpStatus.OK);
        return responseEntity;
    }

    @RequestMapping(value = "/getTree/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getTree(@PathVariable Long id) throws JSONException {
        log.debug("REST request to get list tree of Companies");
        //List<Company>
        List<Company> listCompany = companyService.getTree(id);
        List<TreeObj> listTree = new ArrayList<>(listCompany.size());

        Gson gson = new Gson();
        TreeObj treeObj;
        for (Company company : listCompany) {
            treeObj = new TreeObj();
            treeObj.setValue(gson.toJson(company));
            treeObj.setText(company.getName());
            listTree.add(treeObj);
        }

        //Convert to String
        ResponseEntity<String> responseEntity = new ResponseEntity<>(gson.toJson(listTree),
                new HttpHeaders(),
                HttpStatus.OK);
        return responseEntity;
    }

    @RequestMapping(value = "/findTopProducts/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> findTopProducts(@PathVariable("id") Optional<String> id) throws JSONException {
        log.debug("REST request to getfindTopProducts of Companies " + id);
        Long idReq = null;
        if (id == null || StringUtils.isBlank(id.get()) || !StringUtils.isNumeric(id.get())) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            log.debug("-------getAuthentication: " + auth.getPrincipal());
            try {
                org.springframework.security.core.userdetails.User users = (org.springframework.security.core.userdetails.User) auth.getPrincipal();
                log.debug("-------userdetails: " + users.getUsername());
                Optional<User> lstUser = userService.getUserWithAuthoritiesByLogin(users.getUsername());
                User user = lstUser.get();
                if (user.getCompany() == null) {
                    log.debug("-------getCompany null: ");
                } else {
                    log.debug("-------getCompany: " + user.getCompany().getId() + " - " + user.getCompany().getName());
                    idReq = user.getCompany().getId();
                }
            } catch (Exception ex) {
                log.error("ERROR findTopProducts: ", ex);
            }
        } else {
            idReq = Long.parseLong(id.get());
        }
        //List<Company>
        List<CompanyDTO> listCompany = companyService.findTopProducts(idReq);
        Gson gson = new Gson();
        ResponseEntity<String> responseEntity = new ResponseEntity<>(gson.toJson(listCompany),
                new HttpHeaders(),
                HttpStatus.OK);
        return responseEntity;
    }
}
