package com.ifixit.webapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ifixit.webapp.domain.User;
import com.ifixit.webapp.service.GroupEngineerService;
import com.ifixit.webapp.service.UserService;
import com.ifixit.webapp.service.WorkOrderService;
import com.ifixit.webapp.web.rest.util.HeaderUtil;
import com.ifixit.webapp.web.rest.util.PaginationUtil;
import com.ifixit.webapp.service.dto.WorkOrderDTO;
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
 * REST controller for managing WorkOrder.
 */
@RestController
@RequestMapping("/api")
public class WorkOrderResource {

    private final Logger log = LoggerFactory.getLogger(WorkOrderResource.class);

    private static final String ENTITY_NAME = "workOrder";

    private final WorkOrderService workOrderService;

    public WorkOrderResource(WorkOrderService workOrderService, GroupEngineerService groupEngineerService) {
        this.workOrderService = workOrderService;
        this.groupEngineerService = groupEngineerService;
    }

    /**
     * POST /work-orders : Create a new workOrder.
     *
     * @param workOrderDTO the workOrderDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the
     * new workOrderDTO, or with status 400 (Bad Request) if the workOrder has
     * already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/work-orders")
    @Timed
    public ResponseEntity<WorkOrderDTO> createWorkOrder(@Valid @RequestBody WorkOrderDTO workOrderDTO) throws URISyntaxException {
        log.debug("REST request to save WorkOrder : {}", workOrderDTO);
        if (workOrderDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new workOrder cannot already have an ID")).body(null);
        }
        WorkOrderDTO result = workOrderService.save(workOrderDTO);
        return ResponseEntity.created(new URI("/api/work-orders/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * PUT /work-orders : Updates an existing workOrder.
     *
     * @param workOrderDTO the workOrderDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated
     * workOrderDTO, or with status 400 (Bad Request) if the workOrderDTO is not
     * valid, or with status 500 (Internal Server Error) if the workOrderDTO
     * couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/work-orders")
    @Timed
    public ResponseEntity<WorkOrderDTO> updateWorkOrder(@Valid @RequestBody WorkOrderDTO workOrderDTO) throws URISyntaxException {
        log.debug("REST request to update WorkOrder : {}", workOrderDTO);
        if (workOrderDTO.getId() == null) {
            return createWorkOrder(workOrderDTO);
        }
        WorkOrderDTO result = workOrderService.save(workOrderDTO);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, workOrderDTO.getId().toString()))
                .body(result);
    }

    /**
     * GET /work-orders : get all the workOrders.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of
     * workOrders in body
     */
    @GetMapping("/work-orders")
    @Timed
    public ResponseEntity<List<WorkOrderDTO>> getAllWorkOrders(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of WorkOrders");
        Page<WorkOrderDTO> page = workOrderService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/work-orders");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /work-orders/:id : get the "id" workOrder.
     *
     * @param id the id of the workOrderDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the
     * workOrderDTO, or with status 404 (Not Found)
     */
    @GetMapping("/work-orders/{id}")
    @Timed
    public ResponseEntity<WorkOrderDTO> getWorkOrder(@PathVariable Long id) {
        log.debug("REST request to get WorkOrder : {}", id);
        WorkOrderDTO workOrderDTO = workOrderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(workOrderDTO));
    }

    /**
     * DELETE /work-orders/:id : delete the "id" workOrder.
     *
     * @param id the id of the workOrderDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/work-orders/{id}")
    @Timed
    public ResponseEntity<Void> deleteWorkOrder(@PathVariable Long id) {
        log.debug("REST request to delete WorkOrder : {}", id);
        workOrderService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    //Add
    private final GroupEngineerService groupEngineerService;

    @Inject
    private UserService userService;

    @GetMapping("/work-orders/all/{id}")
    @Timed
    public ResponseEntity<List<WorkOrderDTO>> getWorkOrders(@PathVariable("id") Optional<String> id, @ApiParam Pageable pageable) {
        log.debug("REST request to get a page of WorkOrders");
        Long engId = 1L;
        List<Long> listEng = new ArrayList<>();
        String lstTmp = "";
        if (id == null || StringUtils.isBlank(id.get()) || !StringUtils.isNumeric(id.get())) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            log.debug("-------getAuthentication: " + auth.getPrincipal());
            try {
                org.springframework.security.core.userdetails.User users = (org.springframework.security.core.userdetails.User) auth.getPrincipal();
                log.debug("-------userdetails: " + users.getUsername());
                Optional<User> lstUser = userService.getUserWithAuthoritiesByLogin(users.getUsername());
                User user = lstUser.get();
                if (user.getGroupEngineer() == null) {
                    log.debug("-------getGroupEngineer null: ");
                    listEng = groupEngineerService.getAllId();
                    log.debug("-------getGroupEngineer getAllId: " + StringUtils.join(listEng, ","));
                } else {
                    log.debug("-------getGroupEngineer: " + user.getGroupEngineer().getId() + " - " + user.getGroupEngineer().getName());
                    engId = user.getGroupEngineer().getId();
                }
            } catch (Exception ex) {
                log.error("ERROR findTopProducts: ", ex);
            }
        } else {
            engId = Long.parseLong(id.get());
        }

        if (listEng.isEmpty()) {
            lstTmp = groupEngineerService.getChild(engId);
            if (StringUtils.isBlank(lstTmp)) {
                listEng = new ArrayList<>(1);
                listEng.add(engId);
            } else {
                lstTmp += "," + String.valueOf(engId);
                String[] tmp = lstTmp.split(",");

                listEng = new ArrayList<>(tmp.length);
                for (String eng : tmp) {
                    listEng.add(Long.parseLong(eng));
                }
            }
        }

        Page<WorkOrderDTO> page = workOrderService.getWorkOrders(listEng, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/work-orders/all");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
}
