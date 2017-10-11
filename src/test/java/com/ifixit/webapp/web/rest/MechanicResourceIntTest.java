package com.ifixit.webapp.web.rest;

import com.ifixit.webapp.IfixitApp;

import com.ifixit.webapp.domain.Mechanic;
import com.ifixit.webapp.repository.MechanicRepository;
import com.ifixit.webapp.service.MechanicService;
import com.ifixit.webapp.service.dto.MechanicDTO;
import com.ifixit.webapp.service.mapper.MechanicMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MechanicResource REST controller.
 *
 * @see MechanicResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IfixitApp.class)
public class MechanicResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_COMPLETE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_COMPLETE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_SINCE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SINCE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_SPECIFICATION = "AAAAAAAAAA";
    private static final String UPDATED_SPECIFICATION = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_IMG_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMG_URL = "BBBBBBBBBB";

    private static final String DEFAULT_IMG_PATH = "AAAAAAAAAA";
    private static final String UPDATED_IMG_PATH = "BBBBBBBBBB";

    @Autowired
    private MechanicRepository mechanicRepository;

    @Autowired
    private MechanicMapper mechanicMapper;

    @Autowired
    private MechanicService mechanicService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMechanicMockMvc;

    private Mechanic mechanic;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MechanicResource mechanicResource = new MechanicResource(mechanicService);
        this.restMechanicMockMvc = MockMvcBuilders.standaloneSetup(mechanicResource)
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
    public static Mechanic createEntity(EntityManager em) {
        Mechanic mechanic = new Mechanic()
            .code(DEFAULT_CODE)
            .completeCode(DEFAULT_COMPLETE_CODE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .note(DEFAULT_NOTE)
            .since(DEFAULT_SINCE)
            .specification(DEFAULT_SPECIFICATION)
            .location(DEFAULT_LOCATION)
            .imgUrl(DEFAULT_IMG_URL)
            .imgPath(DEFAULT_IMG_PATH);
        return mechanic;
    }

    @Before
    public void initTest() {
        mechanic = createEntity(em);
    }

    @Test
    @Transactional
    public void createMechanic() throws Exception {
        int databaseSizeBeforeCreate = mechanicRepository.findAll().size();

        // Create the Mechanic
        MechanicDTO mechanicDTO = mechanicMapper.toDto(mechanic);
        restMechanicMockMvc.perform(post("/api/mechanics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mechanicDTO)))
            .andExpect(status().isCreated());

        // Validate the Mechanic in the database
        List<Mechanic> mechanicList = mechanicRepository.findAll();
        assertThat(mechanicList).hasSize(databaseSizeBeforeCreate + 1);
        Mechanic testMechanic = mechanicList.get(mechanicList.size() - 1);
        assertThat(testMechanic.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testMechanic.getCompleteCode()).isEqualTo(DEFAULT_COMPLETE_CODE);
        assertThat(testMechanic.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMechanic.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMechanic.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testMechanic.getSince()).isEqualTo(DEFAULT_SINCE);
        assertThat(testMechanic.getSpecification()).isEqualTo(DEFAULT_SPECIFICATION);
        assertThat(testMechanic.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testMechanic.getImgUrl()).isEqualTo(DEFAULT_IMG_URL);
        assertThat(testMechanic.getImgPath()).isEqualTo(DEFAULT_IMG_PATH);
    }

    @Test
    @Transactional
    public void createMechanicWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mechanicRepository.findAll().size();

        // Create the Mechanic with an existing ID
        mechanic.setId(1L);
        MechanicDTO mechanicDTO = mechanicMapper.toDto(mechanic);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMechanicMockMvc.perform(post("/api/mechanics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mechanicDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Mechanic in the database
        List<Mechanic> mechanicList = mechanicRepository.findAll();
        assertThat(mechanicList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = mechanicRepository.findAll().size();
        // set the field null
        mechanic.setCode(null);

        // Create the Mechanic, which fails.
        MechanicDTO mechanicDTO = mechanicMapper.toDto(mechanic);

        restMechanicMockMvc.perform(post("/api/mechanics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mechanicDTO)))
            .andExpect(status().isBadRequest());

        List<Mechanic> mechanicList = mechanicRepository.findAll();
        assertThat(mechanicList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCompleteCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = mechanicRepository.findAll().size();
        // set the field null
        mechanic.setCompleteCode(null);

        // Create the Mechanic, which fails.
        MechanicDTO mechanicDTO = mechanicMapper.toDto(mechanic);

        restMechanicMockMvc.perform(post("/api/mechanics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mechanicDTO)))
            .andExpect(status().isBadRequest());

        List<Mechanic> mechanicList = mechanicRepository.findAll();
        assertThat(mechanicList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = mechanicRepository.findAll().size();
        // set the field null
        mechanic.setName(null);

        // Create the Mechanic, which fails.
        MechanicDTO mechanicDTO = mechanicMapper.toDto(mechanic);

        restMechanicMockMvc.perform(post("/api/mechanics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mechanicDTO)))
            .andExpect(status().isBadRequest());

        List<Mechanic> mechanicList = mechanicRepository.findAll();
        assertThat(mechanicList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSinceIsRequired() throws Exception {
        int databaseSizeBeforeTest = mechanicRepository.findAll().size();
        // set the field null
        mechanic.setSince(null);

        // Create the Mechanic, which fails.
        MechanicDTO mechanicDTO = mechanicMapper.toDto(mechanic);

        restMechanicMockMvc.perform(post("/api/mechanics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mechanicDTO)))
            .andExpect(status().isBadRequest());

        List<Mechanic> mechanicList = mechanicRepository.findAll();
        assertThat(mechanicList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMechanics() throws Exception {
        // Initialize the database
        mechanicRepository.saveAndFlush(mechanic);

        // Get all the mechanicList
        restMechanicMockMvc.perform(get("/api/mechanics?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mechanic.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].completeCode").value(hasItem(DEFAULT_COMPLETE_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())))
            .andExpect(jsonPath("$.[*].since").value(hasItem(DEFAULT_SINCE.toString())))
            .andExpect(jsonPath("$.[*].specification").value(hasItem(DEFAULT_SPECIFICATION.toString())))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION.toString())))
            .andExpect(jsonPath("$.[*].imgUrl").value(hasItem(DEFAULT_IMG_URL.toString())))
            .andExpect(jsonPath("$.[*].imgPath").value(hasItem(DEFAULT_IMG_PATH.toString())));
    }

    @Test
    @Transactional
    public void getMechanic() throws Exception {
        // Initialize the database
        mechanicRepository.saveAndFlush(mechanic);

        // Get the mechanic
        restMechanicMockMvc.perform(get("/api/mechanics/{id}", mechanic.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mechanic.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.completeCode").value(DEFAULT_COMPLETE_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE.toString()))
            .andExpect(jsonPath("$.since").value(DEFAULT_SINCE.toString()))
            .andExpect(jsonPath("$.specification").value(DEFAULT_SPECIFICATION.toString()))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION.toString()))
            .andExpect(jsonPath("$.imgUrl").value(DEFAULT_IMG_URL.toString()))
            .andExpect(jsonPath("$.imgPath").value(DEFAULT_IMG_PATH.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMechanic() throws Exception {
        // Get the mechanic
        restMechanicMockMvc.perform(get("/api/mechanics/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMechanic() throws Exception {
        // Initialize the database
        mechanicRepository.saveAndFlush(mechanic);
        int databaseSizeBeforeUpdate = mechanicRepository.findAll().size();

        // Update the mechanic
        Mechanic updatedMechanic = mechanicRepository.findOne(mechanic.getId());
        updatedMechanic
            .code(UPDATED_CODE)
            .completeCode(UPDATED_COMPLETE_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .note(UPDATED_NOTE)
            .since(UPDATED_SINCE)
            .specification(UPDATED_SPECIFICATION)
            .location(UPDATED_LOCATION)
            .imgUrl(UPDATED_IMG_URL)
            .imgPath(UPDATED_IMG_PATH);
        MechanicDTO mechanicDTO = mechanicMapper.toDto(updatedMechanic);

        restMechanicMockMvc.perform(put("/api/mechanics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mechanicDTO)))
            .andExpect(status().isOk());

        // Validate the Mechanic in the database
        List<Mechanic> mechanicList = mechanicRepository.findAll();
        assertThat(mechanicList).hasSize(databaseSizeBeforeUpdate);
        Mechanic testMechanic = mechanicList.get(mechanicList.size() - 1);
        assertThat(testMechanic.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testMechanic.getCompleteCode()).isEqualTo(UPDATED_COMPLETE_CODE);
        assertThat(testMechanic.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMechanic.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMechanic.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testMechanic.getSince()).isEqualTo(UPDATED_SINCE);
        assertThat(testMechanic.getSpecification()).isEqualTo(UPDATED_SPECIFICATION);
        assertThat(testMechanic.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testMechanic.getImgUrl()).isEqualTo(UPDATED_IMG_URL);
        assertThat(testMechanic.getImgPath()).isEqualTo(UPDATED_IMG_PATH);
    }

    @Test
    @Transactional
    public void updateNonExistingMechanic() throws Exception {
        int databaseSizeBeforeUpdate = mechanicRepository.findAll().size();

        // Create the Mechanic
        MechanicDTO mechanicDTO = mechanicMapper.toDto(mechanic);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMechanicMockMvc.perform(put("/api/mechanics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mechanicDTO)))
            .andExpect(status().isCreated());

        // Validate the Mechanic in the database
        List<Mechanic> mechanicList = mechanicRepository.findAll();
        assertThat(mechanicList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMechanic() throws Exception {
        // Initialize the database
        mechanicRepository.saveAndFlush(mechanic);
        int databaseSizeBeforeDelete = mechanicRepository.findAll().size();

        // Get the mechanic
        restMechanicMockMvc.perform(delete("/api/mechanics/{id}", mechanic.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Mechanic> mechanicList = mechanicRepository.findAll();
        assertThat(mechanicList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Mechanic.class);
        Mechanic mechanic1 = new Mechanic();
        mechanic1.setId(1L);
        Mechanic mechanic2 = new Mechanic();
        mechanic2.setId(mechanic1.getId());
        assertThat(mechanic1).isEqualTo(mechanic2);
        mechanic2.setId(2L);
        assertThat(mechanic1).isNotEqualTo(mechanic2);
        mechanic1.setId(null);
        assertThat(mechanic1).isNotEqualTo(mechanic2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MechanicDTO.class);
        MechanicDTO mechanicDTO1 = new MechanicDTO();
        mechanicDTO1.setId(1L);
        MechanicDTO mechanicDTO2 = new MechanicDTO();
        assertThat(mechanicDTO1).isNotEqualTo(mechanicDTO2);
        mechanicDTO2.setId(mechanicDTO1.getId());
        assertThat(mechanicDTO1).isEqualTo(mechanicDTO2);
        mechanicDTO2.setId(2L);
        assertThat(mechanicDTO1).isNotEqualTo(mechanicDTO2);
        mechanicDTO1.setId(null);
        assertThat(mechanicDTO1).isNotEqualTo(mechanicDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(mechanicMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(mechanicMapper.fromId(null)).isNull();
    }
}
