package com.ifixit.webapp.web.rest;

import com.ifixit.webapp.IfixitApp;

import com.ifixit.webapp.domain.WorkOrder;
import com.ifixit.webapp.repository.WorkOrderRepository;
import com.ifixit.webapp.service.WorkOrderService;
import com.ifixit.webapp.service.dto.WorkOrderDTO;
import com.ifixit.webapp.service.mapper.WorkOrderMapper;
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

import com.ifixit.webapp.domain.enumeration.WOStatus;
/**
 * Test class for the WorkOrderResource REST controller.
 *
 * @see WorkOrderResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IfixitApp.class)
public class WorkOrderResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_TIME = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_TIME = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_I_INTERVAL = 1;
    private static final Integer UPDATED_I_INTERVAL = 2;

    private static final Integer DEFAULT_IS_REPEAT = 1;
    private static final Integer UPDATED_IS_REPEAT = 2;

    private static final String DEFAULT_TASK = "AAAAAAAAAA";
    private static final String UPDATED_TASK = "BBBBBBBBBB";

    private static final String DEFAULT_REASON = "AAAAAAAAAA";
    private static final String UPDATED_REASON = "BBBBBBBBBB";

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    private static final Float DEFAULT_MH_TOTAL = 1F;
    private static final Float UPDATED_MH_TOTAL = 2F;

    private static final Float DEFAULT_MH_TOTAL_COST = 1F;
    private static final Float UPDATED_MH_TOTAL_COST = 2F;

    private static final Float DEFAULT_STOCK_TOTAL_COST = 1F;
    private static final Float UPDATED_STOCK_TOTAL_COST = 2F;

    private static final LocalDate DEFAULT_LAST_UPDATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_UPDATE = LocalDate.now(ZoneId.systemDefault());

    private static final WOStatus DEFAULT_STATUS = WOStatus.OPEN;
    private static final WOStatus UPDATED_STATUS = WOStatus.OVER_DUE;

    @Autowired
    private WorkOrderRepository workOrderRepository;

    @Autowired
    private WorkOrderMapper workOrderMapper;

    @Autowired
    private WorkOrderService workOrderService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restWorkOrderMockMvc;

    private WorkOrder workOrder;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WorkOrderResource workOrderResource = new WorkOrderResource(workOrderService);
        this.restWorkOrderMockMvc = MockMvcBuilders.standaloneSetup(workOrderResource)
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
    public static WorkOrder createEntity(EntityManager em) {
        WorkOrder workOrder = new WorkOrder()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .startTime(DEFAULT_START_TIME)
            .endTime(DEFAULT_END_TIME)
            .iInterval(DEFAULT_I_INTERVAL)
            .isRepeat(DEFAULT_IS_REPEAT)
            .task(DEFAULT_TASK)
            .reason(DEFAULT_REASON)
            .note(DEFAULT_NOTE)
            .mhTotal(DEFAULT_MH_TOTAL)
            .mhTotalCost(DEFAULT_MH_TOTAL_COST)
            .stockTotalCost(DEFAULT_STOCK_TOTAL_COST)
            .lastUpdate(DEFAULT_LAST_UPDATE)
            .status(DEFAULT_STATUS);
        return workOrder;
    }

    @Before
    public void initTest() {
        workOrder = createEntity(em);
    }

    @Test
    @Transactional
    public void createWorkOrder() throws Exception {
        int databaseSizeBeforeCreate = workOrderRepository.findAll().size();

        // Create the WorkOrder
        WorkOrderDTO workOrderDTO = workOrderMapper.toDto(workOrder);
        restWorkOrderMockMvc.perform(post("/api/work-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workOrderDTO)))
            .andExpect(status().isCreated());

        // Validate the WorkOrder in the database
        List<WorkOrder> workOrderList = workOrderRepository.findAll();
        assertThat(workOrderList).hasSize(databaseSizeBeforeCreate + 1);
        WorkOrder testWorkOrder = workOrderList.get(workOrderList.size() - 1);
        assertThat(testWorkOrder.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testWorkOrder.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testWorkOrder.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testWorkOrder.getEndTime()).isEqualTo(DEFAULT_END_TIME);
        assertThat(testWorkOrder.getiInterval()).isEqualTo(DEFAULT_I_INTERVAL);
        assertThat(testWorkOrder.getIsRepeat()).isEqualTo(DEFAULT_IS_REPEAT);
        assertThat(testWorkOrder.getTask()).isEqualTo(DEFAULT_TASK);
        assertThat(testWorkOrder.getReason()).isEqualTo(DEFAULT_REASON);
        assertThat(testWorkOrder.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testWorkOrder.getMhTotal()).isEqualTo(DEFAULT_MH_TOTAL);
        assertThat(testWorkOrder.getMhTotalCost()).isEqualTo(DEFAULT_MH_TOTAL_COST);
        assertThat(testWorkOrder.getStockTotalCost()).isEqualTo(DEFAULT_STOCK_TOTAL_COST);
        assertThat(testWorkOrder.getLastUpdate()).isEqualTo(DEFAULT_LAST_UPDATE);
        assertThat(testWorkOrder.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createWorkOrderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = workOrderRepository.findAll().size();

        // Create the WorkOrder with an existing ID
        workOrder.setId(1L);
        WorkOrderDTO workOrderDTO = workOrderMapper.toDto(workOrder);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkOrderMockMvc.perform(post("/api/work-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workOrderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WorkOrder in the database
        List<WorkOrder> workOrderList = workOrderRepository.findAll();
        assertThat(workOrderList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = workOrderRepository.findAll().size();
        // set the field null
        workOrder.setCode(null);

        // Create the WorkOrder, which fails.
        WorkOrderDTO workOrderDTO = workOrderMapper.toDto(workOrder);

        restWorkOrderMockMvc.perform(post("/api/work-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workOrderDTO)))
            .andExpect(status().isBadRequest());

        List<WorkOrder> workOrderList = workOrderRepository.findAll();
        assertThat(workOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = workOrderRepository.findAll().size();
        // set the field null
        workOrder.setName(null);

        // Create the WorkOrder, which fails.
        WorkOrderDTO workOrderDTO = workOrderMapper.toDto(workOrder);

        restWorkOrderMockMvc.perform(post("/api/work-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workOrderDTO)))
            .andExpect(status().isBadRequest());

        List<WorkOrder> workOrderList = workOrderRepository.findAll();
        assertThat(workOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = workOrderRepository.findAll().size();
        // set the field null
        workOrder.setStartTime(null);

        // Create the WorkOrder, which fails.
        WorkOrderDTO workOrderDTO = workOrderMapper.toDto(workOrder);

        restWorkOrderMockMvc.perform(post("/api/work-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workOrderDTO)))
            .andExpect(status().isBadRequest());

        List<WorkOrder> workOrderList = workOrderRepository.findAll();
        assertThat(workOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = workOrderRepository.findAll().size();
        // set the field null
        workOrder.setEndTime(null);

        // Create the WorkOrder, which fails.
        WorkOrderDTO workOrderDTO = workOrderMapper.toDto(workOrder);

        restWorkOrderMockMvc.perform(post("/api/work-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workOrderDTO)))
            .andExpect(status().isBadRequest());

        List<WorkOrder> workOrderList = workOrderRepository.findAll();
        assertThat(workOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsRepeatIsRequired() throws Exception {
        int databaseSizeBeforeTest = workOrderRepository.findAll().size();
        // set the field null
        workOrder.setIsRepeat(null);

        // Create the WorkOrder, which fails.
        WorkOrderDTO workOrderDTO = workOrderMapper.toDto(workOrder);

        restWorkOrderMockMvc.perform(post("/api/work-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workOrderDTO)))
            .andExpect(status().isBadRequest());

        List<WorkOrder> workOrderList = workOrderRepository.findAll();
        assertThat(workOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllWorkOrders() throws Exception {
        // Initialize the database
        workOrderRepository.saveAndFlush(workOrder);

        // Get all the workOrderList
        restWorkOrderMockMvc.perform(get("/api/work-orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())))
            .andExpect(jsonPath("$.[*].iInterval").value(hasItem(DEFAULT_I_INTERVAL)))
            .andExpect(jsonPath("$.[*].isRepeat").value(hasItem(DEFAULT_IS_REPEAT)))
            .andExpect(jsonPath("$.[*].task").value(hasItem(DEFAULT_TASK.toString())))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON.toString())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())))
            .andExpect(jsonPath("$.[*].mhTotal").value(hasItem(DEFAULT_MH_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].mhTotalCost").value(hasItem(DEFAULT_MH_TOTAL_COST.doubleValue())))
            .andExpect(jsonPath("$.[*].stockTotalCost").value(hasItem(DEFAULT_STOCK_TOTAL_COST.doubleValue())))
            .andExpect(jsonPath("$.[*].lastUpdate").value(hasItem(DEFAULT_LAST_UPDATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getWorkOrder() throws Exception {
        // Initialize the database
        workOrderRepository.saveAndFlush(workOrder);

        // Get the workOrder
        restWorkOrderMockMvc.perform(get("/api/work-orders/{id}", workOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(workOrder.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME.toString()))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME.toString()))
            .andExpect(jsonPath("$.iInterval").value(DEFAULT_I_INTERVAL))
            .andExpect(jsonPath("$.isRepeat").value(DEFAULT_IS_REPEAT))
            .andExpect(jsonPath("$.task").value(DEFAULT_TASK.toString()))
            .andExpect(jsonPath("$.reason").value(DEFAULT_REASON.toString()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE.toString()))
            .andExpect(jsonPath("$.mhTotal").value(DEFAULT_MH_TOTAL.doubleValue()))
            .andExpect(jsonPath("$.mhTotalCost").value(DEFAULT_MH_TOTAL_COST.doubleValue()))
            .andExpect(jsonPath("$.stockTotalCost").value(DEFAULT_STOCK_TOTAL_COST.doubleValue()))
            .andExpect(jsonPath("$.lastUpdate").value(DEFAULT_LAST_UPDATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingWorkOrder() throws Exception {
        // Get the workOrder
        restWorkOrderMockMvc.perform(get("/api/work-orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWorkOrder() throws Exception {
        // Initialize the database
        workOrderRepository.saveAndFlush(workOrder);
        int databaseSizeBeforeUpdate = workOrderRepository.findAll().size();

        // Update the workOrder
        WorkOrder updatedWorkOrder = workOrderRepository.findOne(workOrder.getId());
        updatedWorkOrder
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME)
            .iInterval(UPDATED_I_INTERVAL)
            .isRepeat(UPDATED_IS_REPEAT)
            .task(UPDATED_TASK)
            .reason(UPDATED_REASON)
            .note(UPDATED_NOTE)
            .mhTotal(UPDATED_MH_TOTAL)
            .mhTotalCost(UPDATED_MH_TOTAL_COST)
            .stockTotalCost(UPDATED_STOCK_TOTAL_COST)
            .lastUpdate(UPDATED_LAST_UPDATE)
            .status(UPDATED_STATUS);
        WorkOrderDTO workOrderDTO = workOrderMapper.toDto(updatedWorkOrder);

        restWorkOrderMockMvc.perform(put("/api/work-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workOrderDTO)))
            .andExpect(status().isOk());

        // Validate the WorkOrder in the database
        List<WorkOrder> workOrderList = workOrderRepository.findAll();
        assertThat(workOrderList).hasSize(databaseSizeBeforeUpdate);
        WorkOrder testWorkOrder = workOrderList.get(workOrderList.size() - 1);
        assertThat(testWorkOrder.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testWorkOrder.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testWorkOrder.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testWorkOrder.getEndTime()).isEqualTo(UPDATED_END_TIME);
        assertThat(testWorkOrder.getiInterval()).isEqualTo(UPDATED_I_INTERVAL);
        assertThat(testWorkOrder.getIsRepeat()).isEqualTo(UPDATED_IS_REPEAT);
        assertThat(testWorkOrder.getTask()).isEqualTo(UPDATED_TASK);
        assertThat(testWorkOrder.getReason()).isEqualTo(UPDATED_REASON);
        assertThat(testWorkOrder.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testWorkOrder.getMhTotal()).isEqualTo(UPDATED_MH_TOTAL);
        assertThat(testWorkOrder.getMhTotalCost()).isEqualTo(UPDATED_MH_TOTAL_COST);
        assertThat(testWorkOrder.getStockTotalCost()).isEqualTo(UPDATED_STOCK_TOTAL_COST);
        assertThat(testWorkOrder.getLastUpdate()).isEqualTo(UPDATED_LAST_UPDATE);
        assertThat(testWorkOrder.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingWorkOrder() throws Exception {
        int databaseSizeBeforeUpdate = workOrderRepository.findAll().size();

        // Create the WorkOrder
        WorkOrderDTO workOrderDTO = workOrderMapper.toDto(workOrder);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restWorkOrderMockMvc.perform(put("/api/work-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workOrderDTO)))
            .andExpect(status().isCreated());

        // Validate the WorkOrder in the database
        List<WorkOrder> workOrderList = workOrderRepository.findAll();
        assertThat(workOrderList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteWorkOrder() throws Exception {
        // Initialize the database
        workOrderRepository.saveAndFlush(workOrder);
        int databaseSizeBeforeDelete = workOrderRepository.findAll().size();

        // Get the workOrder
        restWorkOrderMockMvc.perform(delete("/api/work-orders/{id}", workOrder.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<WorkOrder> workOrderList = workOrderRepository.findAll();
        assertThat(workOrderList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkOrder.class);
        WorkOrder workOrder1 = new WorkOrder();
        workOrder1.setId(1L);
        WorkOrder workOrder2 = new WorkOrder();
        workOrder2.setId(workOrder1.getId());
        assertThat(workOrder1).isEqualTo(workOrder2);
        workOrder2.setId(2L);
        assertThat(workOrder1).isNotEqualTo(workOrder2);
        workOrder1.setId(null);
        assertThat(workOrder1).isNotEqualTo(workOrder2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkOrderDTO.class);
        WorkOrderDTO workOrderDTO1 = new WorkOrderDTO();
        workOrderDTO1.setId(1L);
        WorkOrderDTO workOrderDTO2 = new WorkOrderDTO();
        assertThat(workOrderDTO1).isNotEqualTo(workOrderDTO2);
        workOrderDTO2.setId(workOrderDTO1.getId());
        assertThat(workOrderDTO1).isEqualTo(workOrderDTO2);
        workOrderDTO2.setId(2L);
        assertThat(workOrderDTO1).isNotEqualTo(workOrderDTO2);
        workOrderDTO1.setId(null);
        assertThat(workOrderDTO1).isNotEqualTo(workOrderDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(workOrderMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(workOrderMapper.fromId(null)).isNull();
    }
}
