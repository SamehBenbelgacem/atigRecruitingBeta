package atig.recruiting.beta.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import atig.recruiting.beta.IntegrationTest;
import atig.recruiting.beta.domain.Candidate;
import atig.recruiting.beta.domain.Company;
import atig.recruiting.beta.domain.Process;
import atig.recruiting.beta.domain.ProcessStep;
import atig.recruiting.beta.domain.enumeration.EnumPriority;
import atig.recruiting.beta.repository.ProcessStepRepository;
import atig.recruiting.beta.service.dto.ProcessStepDTO;
import atig.recruiting.beta.service.mapper.ProcessStepMapper;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ProcessStepResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProcessStepResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_ORDER = "AAAAAAAAAA";
    private static final String UPDATED_ORDER = "BBBBBBBBBB";

    private static final EnumPriority DEFAULT_PRIORITY = EnumPriority.LOW;
    private static final EnumPriority UPDATED_PRIORITY = EnumPriority.MEDUIM;

    private static final String ENTITY_API_URL = "/api/process-steps";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProcessStepRepository processStepRepository;

    @Autowired
    private ProcessStepMapper processStepMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProcessStepMockMvc;

    private ProcessStep processStep;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProcessStep createEntity(EntityManager em) {
        ProcessStep processStep = new ProcessStep().title(DEFAULT_TITLE).order(DEFAULT_ORDER).priority(DEFAULT_PRIORITY);
        return processStep;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProcessStep createUpdatedEntity(EntityManager em) {
        ProcessStep processStep = new ProcessStep().title(UPDATED_TITLE).order(UPDATED_ORDER).priority(UPDATED_PRIORITY);
        return processStep;
    }

    @BeforeEach
    public void initTest() {
        processStep = createEntity(em);
    }

    @Test
    @Transactional
    void createProcessStep() throws Exception {
        int databaseSizeBeforeCreate = processStepRepository.findAll().size();
        // Create the ProcessStep
        ProcessStepDTO processStepDTO = processStepMapper.toDto(processStep);
        restProcessStepMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(processStepDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProcessStep in the database
        List<ProcessStep> processStepList = processStepRepository.findAll();
        assertThat(processStepList).hasSize(databaseSizeBeforeCreate + 1);
        ProcessStep testProcessStep = processStepList.get(processStepList.size() - 1);
        assertThat(testProcessStep.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testProcessStep.getOrder()).isEqualTo(DEFAULT_ORDER);
        assertThat(testProcessStep.getPriority()).isEqualTo(DEFAULT_PRIORITY);
    }

    @Test
    @Transactional
    void createProcessStepWithExistingId() throws Exception {
        // Create the ProcessStep with an existing ID
        processStep.setId(1L);
        ProcessStepDTO processStepDTO = processStepMapper.toDto(processStep);

        int databaseSizeBeforeCreate = processStepRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProcessStepMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(processStepDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProcessStep in the database
        List<ProcessStep> processStepList = processStepRepository.findAll();
        assertThat(processStepList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProcessSteps() throws Exception {
        // Initialize the database
        processStepRepository.saveAndFlush(processStep);

        // Get all the processStepList
        restProcessStepMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(processStep.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].order").value(hasItem(DEFAULT_ORDER)))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY.toString())));
    }

    @Test
    @Transactional
    void getProcessStep() throws Exception {
        // Initialize the database
        processStepRepository.saveAndFlush(processStep);

        // Get the processStep
        restProcessStepMockMvc
            .perform(get(ENTITY_API_URL_ID, processStep.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(processStep.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.order").value(DEFAULT_ORDER))
            .andExpect(jsonPath("$.priority").value(DEFAULT_PRIORITY.toString()));
    }

    @Test
    @Transactional
    void getProcessStepsByIdFiltering() throws Exception {
        // Initialize the database
        processStepRepository.saveAndFlush(processStep);

        Long id = processStep.getId();

        defaultProcessStepShouldBeFound("id.equals=" + id);
        defaultProcessStepShouldNotBeFound("id.notEquals=" + id);

        defaultProcessStepShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProcessStepShouldNotBeFound("id.greaterThan=" + id);

        defaultProcessStepShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProcessStepShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllProcessStepsByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        processStepRepository.saveAndFlush(processStep);

        // Get all the processStepList where title equals to DEFAULT_TITLE
        defaultProcessStepShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the processStepList where title equals to UPDATED_TITLE
        defaultProcessStepShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllProcessStepsByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        processStepRepository.saveAndFlush(processStep);

        // Get all the processStepList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultProcessStepShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the processStepList where title equals to UPDATED_TITLE
        defaultProcessStepShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllProcessStepsByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        processStepRepository.saveAndFlush(processStep);

        // Get all the processStepList where title is not null
        defaultProcessStepShouldBeFound("title.specified=true");

        // Get all the processStepList where title is null
        defaultProcessStepShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessStepsByTitleContainsSomething() throws Exception {
        // Initialize the database
        processStepRepository.saveAndFlush(processStep);

        // Get all the processStepList where title contains DEFAULT_TITLE
        defaultProcessStepShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the processStepList where title contains UPDATED_TITLE
        defaultProcessStepShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllProcessStepsByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        processStepRepository.saveAndFlush(processStep);

        // Get all the processStepList where title does not contain DEFAULT_TITLE
        defaultProcessStepShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the processStepList where title does not contain UPDATED_TITLE
        defaultProcessStepShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllProcessStepsByOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        processStepRepository.saveAndFlush(processStep);

        // Get all the processStepList where order equals to DEFAULT_ORDER
        defaultProcessStepShouldBeFound("order.equals=" + DEFAULT_ORDER);

        // Get all the processStepList where order equals to UPDATED_ORDER
        defaultProcessStepShouldNotBeFound("order.equals=" + UPDATED_ORDER);
    }

    @Test
    @Transactional
    void getAllProcessStepsByOrderIsInShouldWork() throws Exception {
        // Initialize the database
        processStepRepository.saveAndFlush(processStep);

        // Get all the processStepList where order in DEFAULT_ORDER or UPDATED_ORDER
        defaultProcessStepShouldBeFound("order.in=" + DEFAULT_ORDER + "," + UPDATED_ORDER);

        // Get all the processStepList where order equals to UPDATED_ORDER
        defaultProcessStepShouldNotBeFound("order.in=" + UPDATED_ORDER);
    }

    @Test
    @Transactional
    void getAllProcessStepsByOrderIsNullOrNotNull() throws Exception {
        // Initialize the database
        processStepRepository.saveAndFlush(processStep);

        // Get all the processStepList where order is not null
        defaultProcessStepShouldBeFound("order.specified=true");

        // Get all the processStepList where order is null
        defaultProcessStepShouldNotBeFound("order.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessStepsByOrderContainsSomething() throws Exception {
        // Initialize the database
        processStepRepository.saveAndFlush(processStep);

        // Get all the processStepList where order contains DEFAULT_ORDER
        defaultProcessStepShouldBeFound("order.contains=" + DEFAULT_ORDER);

        // Get all the processStepList where order contains UPDATED_ORDER
        defaultProcessStepShouldNotBeFound("order.contains=" + UPDATED_ORDER);
    }

    @Test
    @Transactional
    void getAllProcessStepsByOrderNotContainsSomething() throws Exception {
        // Initialize the database
        processStepRepository.saveAndFlush(processStep);

        // Get all the processStepList where order does not contain DEFAULT_ORDER
        defaultProcessStepShouldNotBeFound("order.doesNotContain=" + DEFAULT_ORDER);

        // Get all the processStepList where order does not contain UPDATED_ORDER
        defaultProcessStepShouldBeFound("order.doesNotContain=" + UPDATED_ORDER);
    }

    @Test
    @Transactional
    void getAllProcessStepsByPriorityIsEqualToSomething() throws Exception {
        // Initialize the database
        processStepRepository.saveAndFlush(processStep);

        // Get all the processStepList where priority equals to DEFAULT_PRIORITY
        defaultProcessStepShouldBeFound("priority.equals=" + DEFAULT_PRIORITY);

        // Get all the processStepList where priority equals to UPDATED_PRIORITY
        defaultProcessStepShouldNotBeFound("priority.equals=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    void getAllProcessStepsByPriorityIsInShouldWork() throws Exception {
        // Initialize the database
        processStepRepository.saveAndFlush(processStep);

        // Get all the processStepList where priority in DEFAULT_PRIORITY or UPDATED_PRIORITY
        defaultProcessStepShouldBeFound("priority.in=" + DEFAULT_PRIORITY + "," + UPDATED_PRIORITY);

        // Get all the processStepList where priority equals to UPDATED_PRIORITY
        defaultProcessStepShouldNotBeFound("priority.in=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    void getAllProcessStepsByPriorityIsNullOrNotNull() throws Exception {
        // Initialize the database
        processStepRepository.saveAndFlush(processStep);

        // Get all the processStepList where priority is not null
        defaultProcessStepShouldBeFound("priority.specified=true");

        // Get all the processStepList where priority is null
        defaultProcessStepShouldNotBeFound("priority.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessStepsByCandidatesIsEqualToSomething() throws Exception {
        Candidate candidates;
        if (TestUtil.findAll(em, Candidate.class).isEmpty()) {
            processStepRepository.saveAndFlush(processStep);
            candidates = CandidateResourceIT.createEntity(em);
        } else {
            candidates = TestUtil.findAll(em, Candidate.class).get(0);
        }
        em.persist(candidates);
        em.flush();
        processStep.addCandidates(candidates);
        processStepRepository.saveAndFlush(processStep);
        Long candidatesId = candidates.getId();
        // Get all the processStepList where candidates equals to candidatesId
        defaultProcessStepShouldBeFound("candidatesId.equals=" + candidatesId);

        // Get all the processStepList where candidates equals to (candidatesId + 1)
        defaultProcessStepShouldNotBeFound("candidatesId.equals=" + (candidatesId + 1));
    }

    @Test
    @Transactional
    void getAllProcessStepsByCompaniesIsEqualToSomething() throws Exception {
        Company companies;
        if (TestUtil.findAll(em, Company.class).isEmpty()) {
            processStepRepository.saveAndFlush(processStep);
            companies = CompanyResourceIT.createEntity(em);
        } else {
            companies = TestUtil.findAll(em, Company.class).get(0);
        }
        em.persist(companies);
        em.flush();
        processStep.addCompanies(companies);
        processStepRepository.saveAndFlush(processStep);
        Long companiesId = companies.getId();
        // Get all the processStepList where companies equals to companiesId
        defaultProcessStepShouldBeFound("companiesId.equals=" + companiesId);

        // Get all the processStepList where companies equals to (companiesId + 1)
        defaultProcessStepShouldNotBeFound("companiesId.equals=" + (companiesId + 1));
    }

    @Test
    @Transactional
    void getAllProcessStepsByProcessStepProcessIsEqualToSomething() throws Exception {
        Process processStepProcess;
        if (TestUtil.findAll(em, Process.class).isEmpty()) {
            processStepRepository.saveAndFlush(processStep);
            processStepProcess = ProcessResourceIT.createEntity(em);
        } else {
            processStepProcess = TestUtil.findAll(em, Process.class).get(0);
        }
        em.persist(processStepProcess);
        em.flush();
        processStep.setProcessStepProcess(processStepProcess);
        processStepRepository.saveAndFlush(processStep);
        Long processStepProcessId = processStepProcess.getId();
        // Get all the processStepList where processStepProcess equals to processStepProcessId
        defaultProcessStepShouldBeFound("processStepProcessId.equals=" + processStepProcessId);

        // Get all the processStepList where processStepProcess equals to (processStepProcessId + 1)
        defaultProcessStepShouldNotBeFound("processStepProcessId.equals=" + (processStepProcessId + 1));
    }

    @Test
    @Transactional
    void getAllProcessStepsByProcessIsEqualToSomething() throws Exception {
        Process process;
        if (TestUtil.findAll(em, Process.class).isEmpty()) {
            processStepRepository.saveAndFlush(processStep);
            process = ProcessResourceIT.createEntity(em);
        } else {
            process = TestUtil.findAll(em, Process.class).get(0);
        }
        em.persist(process);
        em.flush();
        processStep.setProcess(process);
        processStepRepository.saveAndFlush(processStep);
        Long processId = process.getId();
        // Get all the processStepList where process equals to processId
        defaultProcessStepShouldBeFound("processId.equals=" + processId);

        // Get all the processStepList where process equals to (processId + 1)
        defaultProcessStepShouldNotBeFound("processId.equals=" + (processId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProcessStepShouldBeFound(String filter) throws Exception {
        restProcessStepMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(processStep.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].order").value(hasItem(DEFAULT_ORDER)))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY.toString())));

        // Check, that the count call also returns 1
        restProcessStepMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProcessStepShouldNotBeFound(String filter) throws Exception {
        restProcessStepMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProcessStepMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingProcessStep() throws Exception {
        // Get the processStep
        restProcessStepMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProcessStep() throws Exception {
        // Initialize the database
        processStepRepository.saveAndFlush(processStep);

        int databaseSizeBeforeUpdate = processStepRepository.findAll().size();

        // Update the processStep
        ProcessStep updatedProcessStep = processStepRepository.findById(processStep.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedProcessStep are not directly saved in db
        em.detach(updatedProcessStep);
        updatedProcessStep.title(UPDATED_TITLE).order(UPDATED_ORDER).priority(UPDATED_PRIORITY);
        ProcessStepDTO processStepDTO = processStepMapper.toDto(updatedProcessStep);

        restProcessStepMockMvc
            .perform(
                put(ENTITY_API_URL_ID, processStepDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(processStepDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProcessStep in the database
        List<ProcessStep> processStepList = processStepRepository.findAll();
        assertThat(processStepList).hasSize(databaseSizeBeforeUpdate);
        ProcessStep testProcessStep = processStepList.get(processStepList.size() - 1);
        assertThat(testProcessStep.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testProcessStep.getOrder()).isEqualTo(UPDATED_ORDER);
        assertThat(testProcessStep.getPriority()).isEqualTo(UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    void putNonExistingProcessStep() throws Exception {
        int databaseSizeBeforeUpdate = processStepRepository.findAll().size();
        processStep.setId(longCount.incrementAndGet());

        // Create the ProcessStep
        ProcessStepDTO processStepDTO = processStepMapper.toDto(processStep);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProcessStepMockMvc
            .perform(
                put(ENTITY_API_URL_ID, processStepDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(processStepDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProcessStep in the database
        List<ProcessStep> processStepList = processStepRepository.findAll();
        assertThat(processStepList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProcessStep() throws Exception {
        int databaseSizeBeforeUpdate = processStepRepository.findAll().size();
        processStep.setId(longCount.incrementAndGet());

        // Create the ProcessStep
        ProcessStepDTO processStepDTO = processStepMapper.toDto(processStep);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProcessStepMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(processStepDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProcessStep in the database
        List<ProcessStep> processStepList = processStepRepository.findAll();
        assertThat(processStepList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProcessStep() throws Exception {
        int databaseSizeBeforeUpdate = processStepRepository.findAll().size();
        processStep.setId(longCount.incrementAndGet());

        // Create the ProcessStep
        ProcessStepDTO processStepDTO = processStepMapper.toDto(processStep);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProcessStepMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(processStepDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProcessStep in the database
        List<ProcessStep> processStepList = processStepRepository.findAll();
        assertThat(processStepList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProcessStepWithPatch() throws Exception {
        // Initialize the database
        processStepRepository.saveAndFlush(processStep);

        int databaseSizeBeforeUpdate = processStepRepository.findAll().size();

        // Update the processStep using partial update
        ProcessStep partialUpdatedProcessStep = new ProcessStep();
        partialUpdatedProcessStep.setId(processStep.getId());

        restProcessStepMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProcessStep.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProcessStep))
            )
            .andExpect(status().isOk());

        // Validate the ProcessStep in the database
        List<ProcessStep> processStepList = processStepRepository.findAll();
        assertThat(processStepList).hasSize(databaseSizeBeforeUpdate);
        ProcessStep testProcessStep = processStepList.get(processStepList.size() - 1);
        assertThat(testProcessStep.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testProcessStep.getOrder()).isEqualTo(DEFAULT_ORDER);
        assertThat(testProcessStep.getPriority()).isEqualTo(DEFAULT_PRIORITY);
    }

    @Test
    @Transactional
    void fullUpdateProcessStepWithPatch() throws Exception {
        // Initialize the database
        processStepRepository.saveAndFlush(processStep);

        int databaseSizeBeforeUpdate = processStepRepository.findAll().size();

        // Update the processStep using partial update
        ProcessStep partialUpdatedProcessStep = new ProcessStep();
        partialUpdatedProcessStep.setId(processStep.getId());

        partialUpdatedProcessStep.title(UPDATED_TITLE).order(UPDATED_ORDER).priority(UPDATED_PRIORITY);

        restProcessStepMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProcessStep.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProcessStep))
            )
            .andExpect(status().isOk());

        // Validate the ProcessStep in the database
        List<ProcessStep> processStepList = processStepRepository.findAll();
        assertThat(processStepList).hasSize(databaseSizeBeforeUpdate);
        ProcessStep testProcessStep = processStepList.get(processStepList.size() - 1);
        assertThat(testProcessStep.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testProcessStep.getOrder()).isEqualTo(UPDATED_ORDER);
        assertThat(testProcessStep.getPriority()).isEqualTo(UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    void patchNonExistingProcessStep() throws Exception {
        int databaseSizeBeforeUpdate = processStepRepository.findAll().size();
        processStep.setId(longCount.incrementAndGet());

        // Create the ProcessStep
        ProcessStepDTO processStepDTO = processStepMapper.toDto(processStep);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProcessStepMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, processStepDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(processStepDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProcessStep in the database
        List<ProcessStep> processStepList = processStepRepository.findAll();
        assertThat(processStepList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProcessStep() throws Exception {
        int databaseSizeBeforeUpdate = processStepRepository.findAll().size();
        processStep.setId(longCount.incrementAndGet());

        // Create the ProcessStep
        ProcessStepDTO processStepDTO = processStepMapper.toDto(processStep);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProcessStepMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(processStepDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProcessStep in the database
        List<ProcessStep> processStepList = processStepRepository.findAll();
        assertThat(processStepList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProcessStep() throws Exception {
        int databaseSizeBeforeUpdate = processStepRepository.findAll().size();
        processStep.setId(longCount.incrementAndGet());

        // Create the ProcessStep
        ProcessStepDTO processStepDTO = processStepMapper.toDto(processStep);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProcessStepMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(processStepDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProcessStep in the database
        List<ProcessStep> processStepList = processStepRepository.findAll();
        assertThat(processStepList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProcessStep() throws Exception {
        // Initialize the database
        processStepRepository.saveAndFlush(processStep);

        int databaseSizeBeforeDelete = processStepRepository.findAll().size();

        // Delete the processStep
        restProcessStepMockMvc
            .perform(delete(ENTITY_API_URL_ID, processStep.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProcessStep> processStepList = processStepRepository.findAll();
        assertThat(processStepList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
