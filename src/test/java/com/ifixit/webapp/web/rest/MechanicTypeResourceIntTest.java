package com.ifixit.webapp.web.rest;

import com.ifixit.webapp.IfixitApp;

import com.ifixit.webapp.domain.MechanicType;
import com.ifixit.webapp.repository.MechanicTypeRepository;
import com.ifixit.webapp.service.MechanicTypeService;
import com.ifixit.webapp.service.dto.MechanicTypeDTO;
import com.ifixit.webapp.service.mapper.MechanicTypeMapper;
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
 * Test class for the MechanicTypeResource REST controller.
 *
 * @see MechanicTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IfixitApp.class)
public class MechanicTypeResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    private static final String DEFAULT_SPECIFICATION = "AAAAAAAAAA";
    private static final String UPDATED_SPECIFICATION = "BBBBBBBBBB";

    @Autowired
    private MechanicTypeRepository mechanicTypeRepository;

    @Autowired
    private MechanicTypeMapper mechanicTypeMapper;

    @Autowired
    private MechanicTypeService mechanicTypeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMechanicTypeMockMvc;

    private MechanicType mechanicType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MechanicTypeResource mechanicTypeResource = new MechanicTypeResource(mechanicTypeService);
        this.restMechanicTypeMockMvc = MockMvcBuilders.standaloneSetup(mechanicTypeResource)
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
    public static MechanicType createEntity(EntityManager em) {
        MechanicType mechanicType = new MechanicType()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .note(DEFAULT_NOTE)
            .specification(DEFAULT_SPECIFICATION);
        return mechanicType;
    }

    @Before
    public void initTest() {
        mechanicType = createEntity(em);
    }

    @Test
    @Transactional
    public void createMechanicType() throws Exception {
        int databaseSizeBeforeCreate = mechanicTypeRepository.findAll().size();

        // Create the MechanicType
        MechanicTypeDTO mechanicTypeDTO = mechanicTypeMapper.toDto(mechanicType);
        restMechanicTypeMockMvc.perform(post("/api/mechanic-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mechanicTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the MechanicType in the database
        List<MechanicType> mechanicTypeList = mechanicTypeRepository.findAll();
        assertThat(mechanicTypeList).hasSize(databaseSizeBeforeCreate + 1);
        MechanicType testMechanicType = mechanicTypeList.get(mechanicTypeList.size() - 1);
        assertThat(testMechanicType.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testMechanicType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMechanicType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMechanicType.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testMechanicType.getSpecification()).isEqualTo(DEFAULT_SPECIFICATION);
    }

    @Test
    @Transactional
    public void createMechanicTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mechanicTypeRepository.findAll().size();

        // Create the MechanicType with an existing ID
        mechanicType.setId(1L);
        MechanicTypeDTO mechanicTypeDTO = mechanicTypeMapper.toDto(mechanicType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMechanicTypeMockMvc.perform(post("/api/mechanic-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mechanicTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MechanicType in the database
        List<MechanicType> mechanicTypeList = mechanicTypeRepository.findAll();
        assertThat(mechanicTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = mechanicTypeRepository.findAll().size();
        // set the field null
        mechanicType.setCode(null);

        // Create the MechanicType, which fails.
        MechanicTypeDTO mechanicTypeDTO = mechanicTypeMapper.toDto(mechanicType);

        restMechanicTypeMockMvc.perform(post("/api/mechanic-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mechanicTypeDTO)))
            .andExpect(status().isBadRequest());

        List<MechanicType> mechanicTypeList = mechanicTypeRepository.findAll();
        assertThat(mechanicTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = mechanicTypeRepository.findAll().size();
        // set the field null
        mechanicType.setName(null);

        // Create the MechanicType, which fails.
        MechanicTypeDTO mechanicTypeDTO = mechanicTypeMapper.toDto(mechanicType);

        restMechanicTypeMockMvc.perform(post("/api/mechanic-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mechanicTypeDTO)))
            .andExpect(status().isBadRequest());

        List<MechanicType> mechanicTypeList = mechanicTypeRepository.findAll();
        assertThat(mechanicTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMechanicTypes() throws Exception {
        // Initialize the database
        mechanicTypeRepository.saveAndFlush(mechanicType);

        // Get all the mechanicTypeList
        restMechanicTypeMockMvc.perform(get("/api/mechanic-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mechanicType.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())))
            .andExpect(jsonPath("$.[*].specification").value(hasItem(DEFAULT_SPECIFICATION.toString())));
    }

    @Test
    @Transactional
    public void getMechanicType() throws Exception {
        // Initialize the database
        mechanicTypeRepository.saveAndFlush(mechanicType);

        // Get the mechanicType
        restMechanicTypeMockMvc.perform(get("/api/mechanic-types/{id}", mechanicType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mechanicType.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE.toString()))
            .andExpect(jsonPath("$.specification").value(DEFAULT_SPECIFICATION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMechanicType() throws Exception {
        // Get the mechanicType
        restMechanicTypeMockMvc.perform(get("/api/mechanic-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMechanicType() throws Exception {
        // Initialize the database
        mechanicTypeRepository.saveAndFlush(mechanicType);
        int databaseSizeBeforeUpdate = mechanicTypeRepository.findAll().size();

        // Update the mechanicType
        MechanicType updatedMechanicType = mechanicTypeRepository.findOne(mechanicType.getId());
        updatedMechanicType
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .note(UPDATED_NOTE)
            .specification(UPDATED_SPECIFICATION);
        MechanicTypeDTO mechanicTypeDTO = mechanicTypeMapper.toDto(updatedMechanicType);

        restMechanicTypeMockMvc.perform(put("/api/mechanic-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mechanicTypeDTO)))
            .andExpect(status().isOk());

        // Validate the MechanicType in the database
        List<MechanicType> mechanicTypeList = mechanicTypeRepository.findAll();
        assertThat(mechanicTypeList).hasSize(databaseSizeBeforeUpdate);
        MechanicType testMechanicType = mechanicTypeList.get(mechanicTypeList.size() - 1);
        assertThat(testMechanicType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testMechanicType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMechanicType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMechanicType.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testMechanicType.getSpecification()).isEqualTo(UPDATED_SPECIFICATION);
    }

    @Test
    @Transactional
    public void updateNonExistingMechanicType() throws Exception {
        int databaseSizeBeforeUpdate = mechanicTypeRepository.findAll().size();

        // Create the MechanicType
        MechanicTypeDTO mechanicTypeDTO = mechanicTypeMapper.toDto(mechanicType);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMechanicTypeMockMvc.perform(put("/api/mechanic-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mechanicTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the MechanicType in the database
        List<MechanicType> mechanicTypeList = mechanicTypeRepository.findAll();
        assertThat(mechanicTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMechanicType() throws Exception {
        // Initialize the database
        mechanicTypeRepository.saveAndFlush(mechanicType);
        int databaseSizeBeforeDelete = mechanicTypeRepository.findAll().size();

        // Get the mechanicType
        restMechanicTypeMockMvc.perform(delete("/api/mechanic-types/{id}", mechanicType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MechanicType> mechanicTypeList = mechanicTypeRepository.findAll();
        assertThat(mechanicTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MechanicType.class);
        MechanicType mechanicType1 = new MechanicType();
        mechanicType1.setId(1L);
        MechanicType mechanicType2 = new MechanicType();
        mechanicType2.setId(mechanicType1.getId());
        assertThat(mechanicType1).isEqualTo(mechanicType2);
        mechanicType2.setId(2L);
        assertThat(mechanicType1).isNotEqualTo(mechanicType2);
        mechanicType1.setId(null);
        assertThat(mechanicType1).isNotEqualTo(mechanicType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MechanicTypeDTO.class);
        MechanicTypeDTO mechanicTypeDTO1 = new MechanicTypeDTO();
        mechanicTypeDTO1.setId(1L);
        MechanicTypeDTO mechanicTypeDTO2 = new MechanicTypeDTO();
        assertThat(mechanicTypeDTO1).isNotEqualTo(mechanicTypeDTO2);
        mechanicTypeDTO2.setId(mechanicTypeDTO1.getId());
        assertThat(mechanicTypeDTO1).isEqualTo(mechanicTypeDTO2);
        mechanicTypeDTO2.setId(2L);
        assertThat(mechanicTypeDTO1).isNotEqualTo(mechanicTypeDTO2);
        mechanicTypeDTO1.setId(null);
        assertThat(mechanicTypeDTO1).isNotEqualTo(mechanicTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(mechanicTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(mechanicTypeMapper.fromId(null)).isNull();
    }
}
