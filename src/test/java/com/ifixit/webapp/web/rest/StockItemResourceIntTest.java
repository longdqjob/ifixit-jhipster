package com.ifixit.webapp.web.rest;

import com.ifixit.webapp.IfixitApp;

import com.ifixit.webapp.domain.StockItem;
import com.ifixit.webapp.repository.StockItemRepository;
import com.ifixit.webapp.service.StockItemService;
import com.ifixit.webapp.service.dto.StockItemDTO;
import com.ifixit.webapp.service.mapper.StockItemMapper;
import com.ifixit.webapp.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the StockItemResource REST controller.
 *
 * @see StockItemResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IfixitApp.class)
public class StockItemResourceIntTest {

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    @Autowired
    private StockItemRepository stockItemRepository;

    @Autowired
    private StockItemMapper stockItemMapper;

    @Autowired
    private StockItemService stockItemService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restStockItemMockMvc;

    private StockItem stockItem;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StockItemResource stockItemResource = new StockItemResource(stockItemService);
        this.restStockItemMockMvc = MockMvcBuilders.standaloneSetup(stockItemResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StockItem createEntity(EntityManager em) {
        StockItem stockItem = new StockItem()
            .quantity(DEFAULT_QUANTITY);
        return stockItem;
    }

    @Before
    public void initTest() {
        stockItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createStockItem() throws Exception {
        int databaseSizeBeforeCreate = stockItemRepository.findAll().size();

        // Create the StockItem
        StockItemDTO stockItemDTO = stockItemMapper.toDto(stockItem);
        restStockItemMockMvc.perform(post("/api/stock-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockItemDTO)))
            .andExpect(status().isCreated());

        // Validate the StockItem in the database
        List<StockItem> stockItemList = stockItemRepository.findAll();
        assertThat(stockItemList).hasSize(databaseSizeBeforeCreate + 1);
        StockItem testStockItem = stockItemList.get(stockItemList.size() - 1);
        assertThat(testStockItem.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
    }

    @Test
    @Transactional
    public void createStockItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = stockItemRepository.findAll().size();

        // Create the StockItem with an existing ID
        stockItem.setId(1L);
        StockItemDTO stockItemDTO = stockItemMapper.toDto(stockItem);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStockItemMockMvc.perform(post("/api/stock-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StockItem in the database
        List<StockItem> stockItemList = stockItemRepository.findAll();
        assertThat(stockItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockItemRepository.findAll().size();
        // set the field null
        stockItem.setQuantity(null);

        // Create the StockItem, which fails.
        StockItemDTO stockItemDTO = stockItemMapper.toDto(stockItem);

        restStockItemMockMvc.perform(post("/api/stock-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockItemDTO)))
            .andExpect(status().isBadRequest());

        List<StockItem> stockItemList = stockItemRepository.findAll();
        assertThat(stockItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStockItems() throws Exception {
        // Initialize the database
        stockItemRepository.saveAndFlush(stockItem);

        // Get all the stockItemList
        restStockItemMockMvc.perform(get("/api/stock-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stockItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)));
    }

    @Test
    @Transactional
    public void getStockItem() throws Exception {
        // Initialize the database
        stockItemRepository.saveAndFlush(stockItem);

        // Get the stockItem
        restStockItemMockMvc.perform(get("/api/stock-items/{id}", stockItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(stockItem.getId().intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY));
    }

    @Test
    @Transactional
    public void getNonExistingStockItem() throws Exception {
        // Get the stockItem
        restStockItemMockMvc.perform(get("/api/stock-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStockItem() throws Exception {
        // Initialize the database
        stockItemRepository.saveAndFlush(stockItem);
        int databaseSizeBeforeUpdate = stockItemRepository.findAll().size();

        // Update the stockItem
        StockItem updatedStockItem = stockItemRepository.findOne(stockItem.getId());
        updatedStockItem
            .quantity(UPDATED_QUANTITY);
        StockItemDTO stockItemDTO = stockItemMapper.toDto(updatedStockItem);

        restStockItemMockMvc.perform(put("/api/stock-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockItemDTO)))
            .andExpect(status().isOk());

        // Validate the StockItem in the database
        List<StockItem> stockItemList = stockItemRepository.findAll();
        assertThat(stockItemList).hasSize(databaseSizeBeforeUpdate);
        StockItem testStockItem = stockItemList.get(stockItemList.size() - 1);
        assertThat(testStockItem.getQuantity()).isEqualTo(UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void updateNonExistingStockItem() throws Exception {
        int databaseSizeBeforeUpdate = stockItemRepository.findAll().size();

        // Create the StockItem
        StockItemDTO stockItemDTO = stockItemMapper.toDto(stockItem);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restStockItemMockMvc.perform(put("/api/stock-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockItemDTO)))
            .andExpect(status().isCreated());

        // Validate the StockItem in the database
        List<StockItem> stockItemList = stockItemRepository.findAll();
        assertThat(stockItemList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteStockItem() throws Exception {
        // Initialize the database
        stockItemRepository.saveAndFlush(stockItem);
        int databaseSizeBeforeDelete = stockItemRepository.findAll().size();

        // Get the stockItem
        restStockItemMockMvc.perform(delete("/api/stock-items/{id}", stockItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<StockItem> stockItemList = stockItemRepository.findAll();
        assertThat(stockItemList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StockItem.class);
        StockItem stockItem1 = new StockItem();
        stockItem1.setId(1L);
        StockItem stockItem2 = new StockItem();
        stockItem2.setId(stockItem1.getId());
        assertThat(stockItem1).isEqualTo(stockItem2);
        stockItem2.setId(2L);
        assertThat(stockItem1).isNotEqualTo(stockItem2);
        stockItem1.setId(null);
        assertThat(stockItem1).isNotEqualTo(stockItem2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StockItemDTO.class);
        StockItemDTO stockItemDTO1 = new StockItemDTO();
        stockItemDTO1.setId(1L);
        StockItemDTO stockItemDTO2 = new StockItemDTO();
        assertThat(stockItemDTO1).isNotEqualTo(stockItemDTO2);
        stockItemDTO2.setId(stockItemDTO1.getId());
        assertThat(stockItemDTO1).isEqualTo(stockItemDTO2);
        stockItemDTO2.setId(2L);
        assertThat(stockItemDTO1).isNotEqualTo(stockItemDTO2);
        stockItemDTO1.setId(null);
        assertThat(stockItemDTO1).isNotEqualTo(stockItemDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(stockItemMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(stockItemMapper.fromId(null)).isNull();
    }
}
