package com.ifixit.webapp.web.rest;

import com.ifixit.webapp.IfixitApp;

import com.ifixit.webapp.domain.WorkType;
import com.ifixit.webapp.repository.WorkTypeRepository;
import com.ifixit.webapp.service.WorkTypeService;
import com.ifixit.webapp.service.dto.WorkTypeDTO;
import com.ifixit.webapp.service.mapper.WorkTypeMapper;
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
 * Test class for the WorkTypeResource REST controller.
 *
 * @see WorkTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IfixitApp.class)
public class WorkTypeResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_I_INTERVAL = 1;
    private static final Integer UPDATED_I_INTERVAL = 2;

    private static final Integer DEFAULT_IS_REPEAT = 1;
    private static final Integer UPDATED_IS_REPEAT = 2;

    private static final String DEFAULT_TASK = "AAAAAAAAAA";
    private static final String UPDATED_TASK = "BBBBBBBBBB";

    @Autowired
    private WorkTypeRepository workTypeRepository;

    @Autowired
    private WorkTypeMapper workTypeMapper;

    @Autowired
    private WorkTypeService workTypeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restWorkTypeMockMvc;

    private WorkType workType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WorkTypeResource workTypeResource = new WorkTypeResource(workTypeService);
        this.restWorkTypeMockMvc = MockMvcBuilders.standaloneSetup(workTypeResource)
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
    public static WorkType createEntity(EntityManager em) {
        WorkType workType = new WorkType()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .iInterval(DEFAULT_I_INTERVAL)
            .isRepeat(DEFAULT_IS_REPEAT)
            .task(DEFAULT_TASK);
        return workType;
    }

    @Before
    public void initTest() {
        workType = createEntity(em);
    }

    @Test
    @Transactional
    public void createWorkType() throws Exception {
        int databaseSizeBeforeCreate = workTypeRepository.findAll().size();

        // Create the WorkType
        WorkTypeDTO workTypeDTO = workTypeMapper.toDto(workType);
        restWorkTypeMockMvc.perform(post("/api/work-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the WorkType in the database
        List<WorkType> workTypeList = workTypeRepository.findAll();
        assertThat(workTypeList).hasSize(databaseSizeBeforeCreate + 1);
        WorkType testWorkType = workTypeList.get(workTypeList.size() - 1);
        assertThat(testWorkType.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testWorkType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testWorkType.getiInterval()).isEqualTo(DEFAULT_I_INTERVAL);
        assertThat(testWorkType.getIsRepeat()).isEqualTo(DEFAULT_IS_REPEAT);
        assertThat(testWorkType.getTask()).isEqualTo(DEFAULT_TASK);
    }

    @Test
    @Transactional
    public void createWorkTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = workTypeRepository.findAll().size();

        // Create the WorkType with an existing ID
        workType.setId(1L);
        WorkTypeDTO workTypeDTO = workTypeMapper.toDto(workType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkTypeMockMvc.perform(post("/api/work-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WorkType in the database
        List<WorkType> workTypeList = workTypeRepository.findAll();
        assertThat(workTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = workTypeRepository.findAll().size();
        // set the field null
        workType.setCode(null);

        // Create the WorkType, which fails.
        WorkTypeDTO workTypeDTO = workTypeMapper.toDto(workType);

        restWorkTypeMockMvc.perform(post("/api/work-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workTypeDTO)))
            .andExpect(status().isBadRequest());

        List<WorkType> workTypeList = workTypeRepository.findAll();
        assertThat(workTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = workTypeRepository.findAll().size();
        // set the field null
        workType.setName(null);

        // Create the WorkType, which fails.
        WorkTypeDTO workTypeDTO = workTypeMapper.toDto(workType);

        restWorkTypeMockMvc.perform(post("/api/work-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workTypeDTO)))
            .andExpect(status().isBadRequest());

        List<WorkType> workTypeList = workTypeRepository.findAll();
        assertThat(workTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsRepeatIsRequired() throws Exception {
        int databaseSizeBeforeTest = workTypeRepository.findAll().size();
        // set the field null
        workType.setIsRepeat(null);

        // Create the WorkType, which fails.
        WorkTypeDTO workTypeDTO = workTypeMapper.toDto(workType);

        restWorkTypeMockMvc.perform(post("/api/work-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workTypeDTO)))
            .andExpect(status().isBadRequest());

        List<WorkType> workTypeList = workTypeRepository.findAll();
        assertThat(workTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllWorkTypes() throws Exception {
        // Initialize the database
        workTypeRepository.saveAndFlush(workType);

        // Get all the workTypeList
        restWorkTypeMockMvc.perform(get("/api/work-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workType.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].iInterval").value(hasItem(DEFAULT_I_INTERVAL)))
            .andExpect(jsonPath("$.[*].isRepeat").value(hasItem(DEFAULT_IS_REPEAT)))
            .andExpect(jsonPath("$.[*].task").value(hasItem(DEFAULT_TASK.toString())));
    }

    @Test
    @Transactional
    public void getWorkType() throws Exception {
        // Initialize the database
        workTypeRepository.saveAndFlush(workType);

        // Get the workType
        restWorkTypeMockMvc.perform(get("/api/work-types/{id}", workType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(workType.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.iInterval").value(DEFAULT_I_INTERVAL))
            .andExpect(jsonPath("$.isRepeat").value(DEFAULT_IS_REPEAT))
            .andExpect(jsonPath("$.task").value(DEFAULT_TASK.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingWorkType() throws Exception {
        // Get the workType
        restWorkTypeMockMvc.perform(get("/api/work-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWorkType() throws Exception {
        // Initialize the database
        workTypeRepository.saveAndFlush(workType);
        int databaseSizeBeforeUpdate = workTypeRepository.findAll().size();

        // Update the workType
        WorkType updatedWorkType = workTypeRepository.findOne(workType.getId());
        updatedWorkType
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .iInterval(UPDATED_I_INTERVAL)
            .isRepeat(UPDATED_IS_REPEAT)
            .task(UPDATED_TASK);
        WorkTypeDTO workTypeDTO = workTypeMapper.toDto(updatedWorkType);

        restWorkTypeMockMvc.perform(put("/api/work-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workTypeDTO)))
            .andExpect(status().isOk());

        // Validate the WorkType in the database
        List<WorkType> workTypeList = workTypeRepository.findAll();
        assertThat(workTypeList).hasSize(databaseSizeBeforeUpdate);
        WorkType testWorkType = workTypeList.get(workTypeList.size() - 1);
        assertThat(testWorkType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testWorkType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testWorkType.getiInterval()).isEqualTo(UPDATED_I_INTERVAL);
        assertThat(testWorkType.getIsRepeat()).isEqualTo(UPDATED_IS_REPEAT);
        assertThat(testWorkType.getTask()).isEqualTo(UPDATED_TASK);
    }

    @Test
    @Transactional
    public void updateNonExistingWorkType() throws Exception {
        int databaseSizeBeforeUpdate = workTypeRepository.findAll().size();

        // Create the WorkType
        WorkTypeDTO workTypeDTO = workTypeMapper.toDto(workType);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restWorkTypeMockMvc.perform(put("/api/work-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the WorkType in the database
        List<WorkType> workTypeList = workTypeRepository.findAll();
        assertThat(workTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteWorkType() throws Exception {
        // Initialize the database
        workTypeRepository.saveAndFlush(workType);
        int databaseSizeBeforeDelete = workTypeRepository.findAll().size();

        // Get the workType
        restWorkTypeMockMvc.perform(delete("/api/work-types/{id}", workType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<WorkType> workTypeList = workTypeRepository.findAll();
        assertThat(workTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkType.class);
        WorkType workType1 = new WorkType();
        workType1.setId(1L);
        WorkType workType2 = new WorkType();
        workType2.setId(workType1.getId());
        assertThat(workType1).isEqualTo(workType2);
        workType2.setId(2L);
        assertThat(workType1).isNotEqualTo(workType2);
        workType1.setId(null);
        assertThat(workType1).isNotEqualTo(workType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkTypeDTO.class);
        WorkTypeDTO workTypeDTO1 = new WorkTypeDTO();
        workTypeDTO1.setId(1L);
        WorkTypeDTO workTypeDTO2 = new WorkTypeDTO();
        assertThat(workTypeDTO1).isNotEqualTo(workTypeDTO2);
        workTypeDTO2.setId(workTypeDTO1.getId());
        assertThat(workTypeDTO1).isEqualTo(workTypeDTO2);
        workTypeDTO2.setId(2L);
        assertThat(workTypeDTO1).isNotEqualTo(workTypeDTO2);
        workTypeDTO1.setId(null);
        assertThat(workTypeDTO1).isNotEqualTo(workTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(workTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(workTypeMapper.fromId(null)).isNull();
    }
}
