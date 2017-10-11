package com.ifixit.webapp.web.rest;

import com.ifixit.webapp.IfixitApp;

import com.ifixit.webapp.domain.ManHours;
import com.ifixit.webapp.repository.ManHoursRepository;
import com.ifixit.webapp.service.ManHoursService;
import com.ifixit.webapp.service.dto.ManHoursDTO;
import com.ifixit.webapp.service.mapper.ManHoursMapper;
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
 * Test class for the ManHoursResource REST controller.
 *
 * @see ManHoursResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IfixitApp.class)
public class ManHoursResourceIntTest {

    private static final Float DEFAULT_MH = 1F;
    private static final Float UPDATED_MH = 2F;

    @Autowired
    private ManHoursRepository manHoursRepository;

    @Autowired
    private ManHoursMapper manHoursMapper;

    @Autowired
    private ManHoursService manHoursService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restManHoursMockMvc;

    private ManHours manHours;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ManHoursResource manHoursResource = new ManHoursResource(manHoursService);
        this.restManHoursMockMvc = MockMvcBuilders.standaloneSetup(manHoursResource)
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
    public static ManHours createEntity(EntityManager em) {
        ManHours manHours = new ManHours()
            .mh(DEFAULT_MH);
        return manHours;
    }

    @Before
    public void initTest() {
        manHours = createEntity(em);
    }

    @Test
    @Transactional
    public void createManHours() throws Exception {
        int databaseSizeBeforeCreate = manHoursRepository.findAll().size();

        // Create the ManHours
        ManHoursDTO manHoursDTO = manHoursMapper.toDto(manHours);
        restManHoursMockMvc.perform(post("/api/man-hours")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(manHoursDTO)))
            .andExpect(status().isCreated());

        // Validate the ManHours in the database
        List<ManHours> manHoursList = manHoursRepository.findAll();
        assertThat(manHoursList).hasSize(databaseSizeBeforeCreate + 1);
        ManHours testManHours = manHoursList.get(manHoursList.size() - 1);
        assertThat(testManHours.getMh()).isEqualTo(DEFAULT_MH);
    }

    @Test
    @Transactional
    public void createManHoursWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = manHoursRepository.findAll().size();

        // Create the ManHours with an existing ID
        manHours.setId(1L);
        ManHoursDTO manHoursDTO = manHoursMapper.toDto(manHours);

        // An entity with an existing ID cannot be created, so this API call must fail
        restManHoursMockMvc.perform(post("/api/man-hours")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(manHoursDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ManHours in the database
        List<ManHours> manHoursList = manHoursRepository.findAll();
        assertThat(manHoursList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkMhIsRequired() throws Exception {
        int databaseSizeBeforeTest = manHoursRepository.findAll().size();
        // set the field null
        manHours.setMh(null);

        // Create the ManHours, which fails.
        ManHoursDTO manHoursDTO = manHoursMapper.toDto(manHours);

        restManHoursMockMvc.perform(post("/api/man-hours")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(manHoursDTO)))
            .andExpect(status().isBadRequest());

        List<ManHours> manHoursList = manHoursRepository.findAll();
        assertThat(manHoursList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllManHours() throws Exception {
        // Initialize the database
        manHoursRepository.saveAndFlush(manHours);

        // Get all the manHoursList
        restManHoursMockMvc.perform(get("/api/man-hours?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(manHours.getId().intValue())))
            .andExpect(jsonPath("$.[*].mh").value(hasItem(DEFAULT_MH.doubleValue())));
    }

    @Test
    @Transactional
    public void getManHours() throws Exception {
        // Initialize the database
        manHoursRepository.saveAndFlush(manHours);

        // Get the manHours
        restManHoursMockMvc.perform(get("/api/man-hours/{id}", manHours.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(manHours.getId().intValue()))
            .andExpect(jsonPath("$.mh").value(DEFAULT_MH.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingManHours() throws Exception {
        // Get the manHours
        restManHoursMockMvc.perform(get("/api/man-hours/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateManHours() throws Exception {
        // Initialize the database
        manHoursRepository.saveAndFlush(manHours);
        int databaseSizeBeforeUpdate = manHoursRepository.findAll().size();

        // Update the manHours
        ManHours updatedManHours = manHoursRepository.findOne(manHours.getId());
        updatedManHours
            .mh(UPDATED_MH);
        ManHoursDTO manHoursDTO = manHoursMapper.toDto(updatedManHours);

        restManHoursMockMvc.perform(put("/api/man-hours")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(manHoursDTO)))
            .andExpect(status().isOk());

        // Validate the ManHours in the database
        List<ManHours> manHoursList = manHoursRepository.findAll();
        assertThat(manHoursList).hasSize(databaseSizeBeforeUpdate);
        ManHours testManHours = manHoursList.get(manHoursList.size() - 1);
        assertThat(testManHours.getMh()).isEqualTo(UPDATED_MH);
    }

    @Test
    @Transactional
    public void updateNonExistingManHours() throws Exception {
        int databaseSizeBeforeUpdate = manHoursRepository.findAll().size();

        // Create the ManHours
        ManHoursDTO manHoursDTO = manHoursMapper.toDto(manHours);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restManHoursMockMvc.perform(put("/api/man-hours")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(manHoursDTO)))
            .andExpect(status().isCreated());

        // Validate the ManHours in the database
        List<ManHours> manHoursList = manHoursRepository.findAll();
        assertThat(manHoursList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteManHours() throws Exception {
        // Initialize the database
        manHoursRepository.saveAndFlush(manHours);
        int databaseSizeBeforeDelete = manHoursRepository.findAll().size();

        // Get the manHours
        restManHoursMockMvc.perform(delete("/api/man-hours/{id}", manHours.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ManHours> manHoursList = manHoursRepository.findAll();
        assertThat(manHoursList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ManHours.class);
        ManHours manHours1 = new ManHours();
        manHours1.setId(1L);
        ManHours manHours2 = new ManHours();
        manHours2.setId(manHours1.getId());
        assertThat(manHours1).isEqualTo(manHours2);
        manHours2.setId(2L);
        assertThat(manHours1).isNotEqualTo(manHours2);
        manHours1.setId(null);
        assertThat(manHours1).isNotEqualTo(manHours2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ManHoursDTO.class);
        ManHoursDTO manHoursDTO1 = new ManHoursDTO();
        manHoursDTO1.setId(1L);
        ManHoursDTO manHoursDTO2 = new ManHoursDTO();
        assertThat(manHoursDTO1).isNotEqualTo(manHoursDTO2);
        manHoursDTO2.setId(manHoursDTO1.getId());
        assertThat(manHoursDTO1).isEqualTo(manHoursDTO2);
        manHoursDTO2.setId(2L);
        assertThat(manHoursDTO1).isNotEqualTo(manHoursDTO2);
        manHoursDTO1.setId(null);
        assertThat(manHoursDTO1).isNotEqualTo(manHoursDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(manHoursMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(manHoursMapper.fromId(null)).isNull();
    }
}
