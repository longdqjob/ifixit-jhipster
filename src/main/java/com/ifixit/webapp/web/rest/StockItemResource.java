package com.ifixit.webapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ifixit.webapp.service.StockItemService;
import com.ifixit.webapp.web.rest.util.HeaderUtil;
import com.ifixit.webapp.service.dto.StockItemDTO;
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
 * REST controller for managing StockItem.
 */
@RestController
@RequestMapping("/api")
public class StockItemResource {

    private final Logger log = LoggerFactory.getLogger(StockItemResource.class);

    private static final String ENTITY_NAME = "stockItem";

    private final StockItemService stockItemService;

    public StockItemResource(StockItemService stockItemService) {
        this.stockItemService = stockItemService;
    }

    /**
     * POST  /stock-items : Create a new stockItem.
     *
     * @param stockItemDTO the stockItemDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new stockItemDTO, or with status 400 (Bad Request) if the stockItem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/stock-items")
    @Timed
    public ResponseEntity<StockItemDTO> createStockItem(@Valid @RequestBody StockItemDTO stockItemDTO) throws URISyntaxException {
        log.debug("REST request to save StockItem : {}", stockItemDTO);
        if (stockItemDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new stockItem cannot already have an ID")).body(null);
        }
        StockItemDTO result = stockItemService.save(stockItemDTO);
        return ResponseEntity.created(new URI("/api/stock-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /stock-items : Updates an existing stockItem.
     *
     * @param stockItemDTO the stockItemDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated stockItemDTO,
     * or with status 400 (Bad Request) if the stockItemDTO is not valid,
     * or with status 500 (Internal Server Error) if the stockItemDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/stock-items")
    @Timed
    public ResponseEntity<StockItemDTO> updateStockItem(@Valid @RequestBody StockItemDTO stockItemDTO) throws URISyntaxException {
        log.debug("REST request to update StockItem : {}", stockItemDTO);
        if (stockItemDTO.getId() == null) {
            return createStockItem(stockItemDTO);
        }
        StockItemDTO result = stockItemService.save(stockItemDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, stockItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /stock-items : get all the stockItems.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of stockItems in body
     */
    @GetMapping("/stock-items")
    @Timed
    public List<StockItemDTO> getAllStockItems() {
        log.debug("REST request to get all StockItems");
        return stockItemService.findAll();
        }

    /**
     * GET  /stock-items/:id : get the "id" stockItem.
     *
     * @param id the id of the stockItemDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the stockItemDTO, or with status 404 (Not Found)
     */
    @GetMapping("/stock-items/{id}")
    @Timed
    public ResponseEntity<StockItemDTO> getStockItem(@PathVariable Long id) {
        log.debug("REST request to get StockItem : {}", id);
        StockItemDTO stockItemDTO = stockItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(stockItemDTO));
    }

    /**
     * DELETE  /stock-items/:id : delete the "id" stockItem.
     *
     * @param id the id of the stockItemDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/stock-items/{id}")
    @Timed
    public ResponseEntity<Void> deleteStockItem(@PathVariable Long id) {
        log.debug("REST request to delete StockItem : {}", id);
        stockItemService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
