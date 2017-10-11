package com.ifixit.webapp.web.rest;

import com.ifixit.webapp.IfixitApp;

import com.ifixit.webapp.domain.ItemType;
import com.ifixit.webapp.repository.ItemTypeRepository;
import com.ifixit.webapp.service.ItemTypeService;
import com.ifixit.webapp.service.dto.ItemTypeDTO;
import com.ifixit.webapp.service.mapper.ItemTypeMapper;
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
 * Test class for the ItemTypeResource REST controller.
 *
 * @see ItemTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IfixitApp.class)
public class ItemTypeResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_COMPLETE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_COMPLETE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SPECIFICATION = "AAAAAAAAAA";
    private static final String UPDATED_SPECIFICATION = "BBBBBBBBBB";

    @Autowired
    private ItemTypeRepository itemTypeRepository;

    @Autowired
    private ItemTypeMapper itemTypeMapper;

    @Autowired
    private ItemTypeService itemTypeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restItemTypeMockMvc;

    private ItemType itemType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ItemTypeResource itemTypeResource = new ItemTypeResource(itemTypeService);
        this.restItemTypeMockMvc = MockMvcBuilders.standaloneSetup(itemTypeResource)
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
    public static ItemType createEntity(EntityManager em) {
        ItemType itemType = new ItemType()
            .code(DEFAULT_CODE)
            .completeCode(DEFAULT_COMPLETE_CODE)
            .name(DEFAULT_NAME)
            .specification(DEFAULT_SPECIFICATION);
        return itemType;
    }

    @Before
    public void initTest() {
        itemType = createEntity(em);
    }

    @Test
    @Transactional
    public void createItemType() throws Exception {
        int databaseSizeBeforeCreate = itemTypeRepository.findAll().size();

        // Create the ItemType
        ItemTypeDTO itemTypeDTO = itemTypeMapper.toDto(itemType);
        restItemTypeMockMvc.perform(post("/api/item-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the ItemType in the database
        List<ItemType> itemTypeList = itemTypeRepository.findAll();
        assertThat(itemTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ItemType testItemType = itemTypeList.get(itemTypeList.size() - 1);
        assertThat(testItemType.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testItemType.getCompleteCode()).isEqualTo(DEFAULT_COMPLETE_CODE);
        assertThat(testItemType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testItemType.getSpecification()).isEqualTo(DEFAULT_SPECIFICATION);
    }

    @Test
    @Transactional
    public void createItemTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = itemTypeRepository.findAll().size();

        // Create the ItemType with an existing ID
        itemType.setId(1L);
        ItemTypeDTO itemTypeDTO = itemTypeMapper.toDto(itemType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restItemTypeMockMvc.perform(post("/api/item-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ItemType in the database
        List<ItemType> itemTypeList = itemTypeRepository.findAll();
        assertThat(itemTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemTypeRepository.findAll().size();
        // set the field null
        itemType.setCode(null);

        // Create the ItemType, which fails.
        ItemTypeDTO itemTypeDTO = itemTypeMapper.toDto(itemType);

        restItemTypeMockMvc.perform(post("/api/item-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemTypeDTO)))
            .andExpect(status().isBadRequest());

        List<ItemType> itemTypeList = itemTypeRepository.findAll();
        assertThat(itemTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCompleteCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemTypeRepository.findAll().size();
        // set the field null
        itemType.setCompleteCode(null);

        // Create the ItemType, which fails.
        ItemTypeDTO itemTypeDTO = itemTypeMapper.toDto(itemType);

        restItemTypeMockMvc.perform(post("/api/item-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemTypeDTO)))
            .andExpect(status().isBadRequest());

        List<ItemType> itemTypeList = itemTypeRepository.findAll();
        assertThat(itemTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemTypeRepository.findAll().size();
        // set the field null
        itemType.setName(null);

        // Create the ItemType, which fails.
        ItemTypeDTO itemTypeDTO = itemTypeMapper.toDto(itemType);

        restItemTypeMockMvc.perform(post("/api/item-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemTypeDTO)))
            .andExpect(status().isBadRequest());

        List<ItemType> itemTypeList = itemTypeRepository.findAll();
        assertThat(itemTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllItemTypes() throws Exception {
        // Initialize the database
        itemTypeRepository.saveAndFlush(itemType);

        // Get all the itemTypeList
        restItemTypeMockMvc.perform(get("/api/item-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemType.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].completeCode").value(hasItem(DEFAULT_COMPLETE_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].specification").value(hasItem(DEFAULT_SPECIFICATION.toString())));
    }

    @Test
    @Transactional
    public void getItemType() throws Exception {
        // Initialize the database
        itemTypeRepository.saveAndFlush(itemType);

        // Get the itemType
        restItemTypeMockMvc.perform(get("/api/item-types/{id}", itemType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(itemType.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.completeCode").value(DEFAULT_COMPLETE_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.specification").value(DEFAULT_SPECIFICATION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingItemType() throws Exception {
        // Get the itemType
        restItemTypeMockMvc.perform(get("/api/item-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateItemType() throws Exception {
        // Initialize the database
        itemTypeRepository.saveAndFlush(itemType);
        int databaseSizeBeforeUpdate = itemTypeRepository.findAll().size();

        // Update the itemType
        ItemType updatedItemType = itemTypeRepository.findOne(itemType.getId());
        updatedItemType
            .code(UPDATED_CODE)
            .completeCode(UPDATED_COMPLETE_CODE)
            .name(UPDATED_NAME)
            .specification(UPDATED_SPECIFICATION);
        ItemTypeDTO itemTypeDTO = itemTypeMapper.toDto(updatedItemType);

        restItemTypeMockMvc.perform(put("/api/item-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemTypeDTO)))
            .andExpect(status().isOk());

        // Validate the ItemType in the database
        List<ItemType> itemTypeList = itemTypeRepository.findAll();
        assertThat(itemTypeList).hasSize(databaseSizeBeforeUpdate);
        ItemType testItemType = itemTypeList.get(itemTypeList.size() - 1);
        assertThat(testItemType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testItemType.getCompleteCode()).isEqualTo(UPDATED_COMPLETE_CODE);
        assertThat(testItemType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testItemType.getSpecification()).isEqualTo(UPDATED_SPECIFICATION);
    }

    @Test
    @Transactional
    public void updateNonExistingItemType() throws Exception {
        int databaseSizeBeforeUpdate = itemTypeRepository.findAll().size();

        // Create the ItemType
        ItemTypeDTO itemTypeDTO = itemTypeMapper.toDto(itemType);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restItemTypeMockMvc.perform(put("/api/item-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the ItemType in the database
        List<ItemType> itemTypeList = itemTypeRepository.findAll();
        assertThat(itemTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteItemType() throws Exception {
        // Initialize the database
        itemTypeRepository.saveAndFlush(itemType);
        int databaseSizeBeforeDelete = itemTypeRepository.findAll().size();

        // Get the itemType
        restItemTypeMockMvc.perform(delete("/api/item-types/{id}", itemType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ItemType> itemTypeList = itemTypeRepository.findAll();
        assertThat(itemTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ItemType.class);
        ItemType itemType1 = new ItemType();
        itemType1.setId(1L);
        ItemType itemType2 = new ItemType();
        itemType2.setId(itemType1.getId());
        assertThat(itemType1).isEqualTo(itemType2);
        itemType2.setId(2L);
        assertThat(itemType1).isNotEqualTo(itemType2);
        itemType1.setId(null);
        assertThat(itemType1).isNotEqualTo(itemType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ItemTypeDTO.class);
        ItemTypeDTO itemTypeDTO1 = new ItemTypeDTO();
        itemTypeDTO1.setId(1L);
        ItemTypeDTO itemTypeDTO2 = new ItemTypeDTO();
        assertThat(itemTypeDTO1).isNotEqualTo(itemTypeDTO2);
        itemTypeDTO2.setId(itemTypeDTO1.getId());
        assertThat(itemTypeDTO1).isEqualTo(itemTypeDTO2);
        itemTypeDTO2.setId(2L);
        assertThat(itemTypeDTO1).isNotEqualTo(itemTypeDTO2);
        itemTypeDTO1.setId(null);
        assertThat(itemTypeDTO1).isNotEqualTo(itemTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(itemTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(itemTypeMapper.fromId(null)).isNull();
    }
}
