package com.ifixit.webapp.web.rest;

import com.ifixit.webapp.IfixitApp;

import com.ifixit.webapp.domain.GroupEngineer;
import com.ifixit.webapp.repository.GroupEngineerRepository;
import com.ifixit.webapp.service.GroupEngineerService;
import com.ifixit.webapp.service.dto.GroupEngineerDTO;
import com.ifixit.webapp.service.mapper.GroupEngineerMapper;
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
 * Test class for the GroupEngineerResource REST controller.
 *
 * @see GroupEngineerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IfixitApp.class)
public class GroupEngineerResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_COMPLETE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_COMPLETE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Float DEFAULT_COST = 1F;
    private static final Float UPDATED_COST = 2F;

    @Autowired
    private GroupEngineerRepository groupEngineerRepository;

    @Autowired
    private GroupEngineerMapper groupEngineerMapper;

    @Autowired
    private GroupEngineerService groupEngineerService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGroupEngineerMockMvc;

    private GroupEngineer groupEngineer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GroupEngineerResource groupEngineerResource = new GroupEngineerResource(groupEngineerService);
        this.restGroupEngineerMockMvc = MockMvcBuilders.standaloneSetup(groupEngineerResource)
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
    public static GroupEngineer createEntity(EntityManager em) {
        GroupEngineer groupEngineer = new GroupEngineer()
            .code(DEFAULT_CODE)
            .completeCode(DEFAULT_COMPLETE_CODE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .cost(DEFAULT_COST);
        return groupEngineer;
    }

    @Before
    public void initTest() {
        groupEngineer = createEntity(em);
    }

    @Test
    @Transactional
    public void createGroupEngineer() throws Exception {
        int databaseSizeBeforeCreate = groupEngineerRepository.findAll().size();

        // Create the GroupEngineer
        GroupEngineerDTO groupEngineerDTO = groupEngineerMapper.toDto(groupEngineer);
        restGroupEngineerMockMvc.perform(post("/api/group-engineers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(groupEngineerDTO)))
            .andExpect(status().isCreated());

        // Validate the GroupEngineer in the database
        List<GroupEngineer> groupEngineerList = groupEngineerRepository.findAll();
        assertThat(groupEngineerList).hasSize(databaseSizeBeforeCreate + 1);
        GroupEngineer testGroupEngineer = groupEngineerList.get(groupEngineerList.size() - 1);
        assertThat(testGroupEngineer.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testGroupEngineer.getCompleteCode()).isEqualTo(DEFAULT_COMPLETE_CODE);
        assertThat(testGroupEngineer.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGroupEngineer.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testGroupEngineer.getCost()).isEqualTo(DEFAULT_COST);
    }

    @Test
    @Transactional
    public void createGroupEngineerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = groupEngineerRepository.findAll().size();

        // Create the GroupEngineer with an existing ID
        groupEngineer.setId(1L);
        GroupEngineerDTO groupEngineerDTO = groupEngineerMapper.toDto(groupEngineer);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGroupEngineerMockMvc.perform(post("/api/group-engineers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(groupEngineerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GroupEngineer in the database
        List<GroupEngineer> groupEngineerList = groupEngineerRepository.findAll();
        assertThat(groupEngineerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = groupEngineerRepository.findAll().size();
        // set the field null
        groupEngineer.setCode(null);

        // Create the GroupEngineer, which fails.
        GroupEngineerDTO groupEngineerDTO = groupEngineerMapper.toDto(groupEngineer);

        restGroupEngineerMockMvc.perform(post("/api/group-engineers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(groupEngineerDTO)))
            .andExpect(status().isBadRequest());

        List<GroupEngineer> groupEngineerList = groupEngineerRepository.findAll();
        assertThat(groupEngineerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCompleteCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = groupEngineerRepository.findAll().size();
        // set the field null
        groupEngineer.setCompleteCode(null);

        // Create the GroupEngineer, which fails.
        GroupEngineerDTO groupEngineerDTO = groupEngineerMapper.toDto(groupEngineer);

        restGroupEngineerMockMvc.perform(post("/api/group-engineers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(groupEngineerDTO)))
            .andExpect(status().isBadRequest());

        List<GroupEngineer> groupEngineerList = groupEngineerRepository.findAll();
        assertThat(groupEngineerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = groupEngineerRepository.findAll().size();
        // set the field null
        groupEngineer.setName(null);

        // Create the GroupEngineer, which fails.
        GroupEngineerDTO groupEngineerDTO = groupEngineerMapper.toDto(groupEngineer);

        restGroupEngineerMockMvc.perform(post("/api/group-engineers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(groupEngineerDTO)))
            .andExpect(status().isBadRequest());

        List<GroupEngineer> groupEngineerList = groupEngineerRepository.findAll();
        assertThat(groupEngineerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCostIsRequired() throws Exception {
        int databaseSizeBeforeTest = groupEngineerRepository.findAll().size();
        // set the field null
        groupEngineer.setCost(null);

        // Create the GroupEngineer, which fails.
        GroupEngineerDTO groupEngineerDTO = groupEngineerMapper.toDto(groupEngineer);

        restGroupEngineerMockMvc.perform(post("/api/group-engineers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(groupEngineerDTO)))
            .andExpect(status().isBadRequest());

        List<GroupEngineer> groupEngineerList = groupEngineerRepository.findAll();
        assertThat(groupEngineerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGroupEngineers() throws Exception {
        // Initialize the database
        groupEngineerRepository.saveAndFlush(groupEngineer);

        // Get all the groupEngineerList
        restGroupEngineerMockMvc.perform(get("/api/group-engineers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(groupEngineer.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].completeCode").value(hasItem(DEFAULT_COMPLETE_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].cost").value(hasItem(DEFAULT_COST.doubleValue())));
    }

    @Test
    @Transactional
    public void getGroupEngineer() throws Exception {
        // Initialize the database
        groupEngineerRepository.saveAndFlush(groupEngineer);

        // Get the groupEngineer
        restGroupEngineerMockMvc.perform(get("/api/group-engineers/{id}", groupEngineer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(groupEngineer.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.completeCode").value(DEFAULT_COMPLETE_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.cost").value(DEFAULT_COST.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingGroupEngineer() throws Exception {
        // Get the groupEngineer
        restGroupEngineerMockMvc.perform(get("/api/group-engineers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGroupEngineer() throws Exception {
        // Initialize the database
        groupEngineerRepository.saveAndFlush(groupEngineer);
        int databaseSizeBeforeUpdate = groupEngineerRepository.findAll().size();

        // Update the groupEngineer
        GroupEngineer updatedGroupEngineer = groupEngineerRepository.findOne(groupEngineer.getId());
        updatedGroupEngineer
            .code(UPDATED_CODE)
            .completeCode(UPDATED_COMPLETE_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .cost(UPDATED_COST);
        GroupEngineerDTO groupEngineerDTO = groupEngineerMapper.toDto(updatedGroupEngineer);

        restGroupEngineerMockMvc.perform(put("/api/group-engineers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(groupEngineerDTO)))
            .andExpect(status().isOk());

        // Validate the GroupEngineer in the database
        List<GroupEngineer> groupEngineerList = groupEngineerRepository.findAll();
        assertThat(groupEngineerList).hasSize(databaseSizeBeforeUpdate);
        GroupEngineer testGroupEngineer = groupEngineerList.get(groupEngineerList.size() - 1);
        assertThat(testGroupEngineer.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testGroupEngineer.getCompleteCode()).isEqualTo(UPDATED_COMPLETE_CODE);
        assertThat(testGroupEngineer.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGroupEngineer.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testGroupEngineer.getCost()).isEqualTo(UPDATED_COST);
    }

    @Test
    @Transactional
    public void updateNonExistingGroupEngineer() throws Exception {
        int databaseSizeBeforeUpdate = groupEngineerRepository.findAll().size();

        // Create the GroupEngineer
        GroupEngineerDTO groupEngineerDTO = groupEngineerMapper.toDto(groupEngineer);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGroupEngineerMockMvc.perform(put("/api/group-engineers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(groupEngineerDTO)))
            .andExpect(status().isCreated());

        // Validate the GroupEngineer in the database
        List<GroupEngineer> groupEngineerList = groupEngineerRepository.findAll();
        assertThat(groupEngineerList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteGroupEngineer() throws Exception {
        // Initialize the database
        groupEngineerRepository.saveAndFlush(groupEngineer);
        int databaseSizeBeforeDelete = groupEngineerRepository.findAll().size();

        // Get the groupEngineer
        restGroupEngineerMockMvc.perform(delete("/api/group-engineers/{id}", groupEngineer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<GroupEngineer> groupEngineerList = groupEngineerRepository.findAll();
        assertThat(groupEngineerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GroupEngineer.class);
        GroupEngineer groupEngineer1 = new GroupEngineer();
        groupEngineer1.setId(1L);
        GroupEngineer groupEngineer2 = new GroupEngineer();
        groupEngineer2.setId(groupEngineer1.getId());
        assertThat(groupEngineer1).isEqualTo(groupEngineer2);
        groupEngineer2.setId(2L);
        assertThat(groupEngineer1).isNotEqualTo(groupEngineer2);
        groupEngineer1.setId(null);
        assertThat(groupEngineer1).isNotEqualTo(groupEngineer2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GroupEngineerDTO.class);
        GroupEngineerDTO groupEngineerDTO1 = new GroupEngineerDTO();
        groupEngineerDTO1.setId(1L);
        GroupEngineerDTO groupEngineerDTO2 = new GroupEngineerDTO();
        assertThat(groupEngineerDTO1).isNotEqualTo(groupEngineerDTO2);
        groupEngineerDTO2.setId(groupEngineerDTO1.getId());
        assertThat(groupEngineerDTO1).isEqualTo(groupEngineerDTO2);
        groupEngineerDTO2.setId(2L);
        assertThat(groupEngineerDTO1).isNotEqualTo(groupEngineerDTO2);
        groupEngineerDTO1.setId(null);
        assertThat(groupEngineerDTO1).isNotEqualTo(groupEngineerDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(groupEngineerMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(groupEngineerMapper.fromId(null)).isNull();
    }
}
