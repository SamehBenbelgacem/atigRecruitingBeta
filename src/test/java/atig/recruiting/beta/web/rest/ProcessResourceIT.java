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
import atig.recruiting.beta.domain.enumeration.EnumProsessType;
import atig.recruiting.beta.repository.ProcessRepository;
import atig.recruiting.beta.service.dto.ProcessDTO;
import atig.recruiting.beta.service.mapper.ProcessMapper;
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
 * Integration tests for the {@link ProcessResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProcessResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final EnumProsessType DEFAULT_TYPE = EnumProsessType.FORCOMPANY;
    private static final EnumProsessType UPDATED_TYPE = EnumProsessType.FORCANDIDATE;

    private static final String ENTITY_API_URL = "/api/processes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProcessRepository processRepository;

    @Autowired
    private ProcessMapper processMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProcessMockMvc;

    private Process process;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Process createEntity(EntityManager em) {
        Process process = new Process().title(DEFAULT_TITLE).type(DEFAULT_TYPE);
        return process;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Process createUpdatedEntity(EntityManager em) {
        Process process = new Process().title(UPDATED_TITLE).type(UPDATED_TYPE);
        return process;
    }

    @BeforeEach
    public void initTest() {
        process = createEntity(em);
    }

    @Test
    @Transactional
    void createProcess() throws Exception {
        int databaseSizeBeforeCreate = processRepository.findAll().size();
        // Create the Process
        ProcessDTO processDTO = processMapper.toDto(process);
        restProcessMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(processDTO)))
            .andExpect(status().isCreated());

        // Validate the Process in the database
        List<Process> processList = processRepository.findAll();
        assertThat(processList).hasSize(databaseSizeBeforeCreate + 1);
        Process testProcess = processList.get(processList.size() - 1);
        assertThat(testProcess.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testProcess.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    void createProcessWithExistingId() throws Exception {
        // Create the Process with an existing ID
        process.setId(1L);
        ProcessDTO processDTO = processMapper.toDto(process);

        int databaseSizeBeforeCreate = processRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProcessMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(processDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Process in the database
        List<Process> processList = processRepository.findAll();
        assertThat(processList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProcesses() throws Exception {
        // Initialize the database
        processRepository.saveAndFlush(process);

        // Get all the processList
        restProcessMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(process.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    @Transactional
    void getProcess() throws Exception {
        // Initialize the database
        processRepository.saveAndFlush(process);

        // Get the process
        restProcessMockMvc
            .perform(get(ENTITY_API_URL_ID, process.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(process.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    void getProcessesByIdFiltering() throws Exception {
        // Initialize the database
        processRepository.saveAndFlush(process);

        Long id = process.getId();

        defaultProcessShouldBeFound("id.equals=" + id);
        defaultProcessShouldNotBeFound("id.notEquals=" + id);

        defaultProcessShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProcessShouldNotBeFound("id.greaterThan=" + id);

        defaultProcessShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProcessShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllProcessesByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        processRepository.saveAndFlush(process);

        // Get all the processList where title equals to DEFAULT_TITLE
        defaultProcessShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the processList where title equals to UPDATED_TITLE
        defaultProcessShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllProcessesByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        processRepository.saveAndFlush(process);

        // Get all the processList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultProcessShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the processList where title equals to UPDATED_TITLE
        defaultProcessShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllProcessesByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        processRepository.saveAndFlush(process);

        // Get all the processList where title is not null
        defaultProcessShouldBeFound("title.specified=true");

        // Get all the processList where title is null
        defaultProcessShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessesByTitleContainsSomething() throws Exception {
        // Initialize the database
        processRepository.saveAndFlush(process);

        // Get all the processList where title contains DEFAULT_TITLE
        defaultProcessShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the processList where title contains UPDATED_TITLE
        defaultProcessShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllProcessesByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        processRepository.saveAndFlush(process);

        // Get all the processList where title does not contain DEFAULT_TITLE
        defaultProcessShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the processList where title does not contain UPDATED_TITLE
        defaultProcessShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllProcessesByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        processRepository.saveAndFlush(process);

        // Get all the processList where type equals to DEFAULT_TYPE
        defaultProcessShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the processList where type equals to UPDATED_TYPE
        defaultProcessShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllProcessesByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        processRepository.saveAndFlush(process);

        // Get all the processList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultProcessShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the processList where type equals to UPDATED_TYPE
        defaultProcessShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllProcessesByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        processRepository.saveAndFlush(process);

        // Get all the processList where type is not null
        defaultProcessShouldBeFound("type.specified=true");

        // Get all the processList where type is null
        defaultProcessShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessesByStepsIsEqualToSomething() throws Exception {
        ProcessStep steps;
        if (TestUtil.findAll(em, ProcessStep.class).isEmpty()) {
            processRepository.saveAndFlush(process);
            steps = ProcessStepResourceIT.createEntity(em);
        } else {
            steps = TestUtil.findAll(em, ProcessStep.class).get(0);
        }
        em.persist(steps);
        em.flush();
        process.addSteps(steps);
        processRepository.saveAndFlush(process);
        Long stepsId = steps.getId();
        // Get all the processList where steps equals to stepsId
        defaultProcessShouldBeFound("stepsId.equals=" + stepsId);

        // Get all the processList where steps equals to (stepsId + 1)
        defaultProcessShouldNotBeFound("stepsId.equals=" + (stepsId + 1));
    }

    @Test
    @Transactional
    void getAllProcessesByCandidatesIsEqualToSomething() throws Exception {
        Candidate candidates;
        if (TestUtil.findAll(em, Candidate.class).isEmpty()) {
            processRepository.saveAndFlush(process);
            candidates = CandidateResourceIT.createEntity(em);
        } else {
            candidates = TestUtil.findAll(em, Candidate.class).get(0);
        }
        em.persist(candidates);
        em.flush();
        process.addCandidates(candidates);
        processRepository.saveAndFlush(process);
        Long candidatesId = candidates.getId();
        // Get all the processList where candidates equals to candidatesId
        defaultProcessShouldBeFound("candidatesId.equals=" + candidatesId);

        // Get all the processList where candidates equals to (candidatesId + 1)
        defaultProcessShouldNotBeFound("candidatesId.equals=" + (candidatesId + 1));
    }

    @Test
    @Transactional
    void getAllProcessesByCompaniesIsEqualToSomething() throws Exception {
        Company companies;
        if (TestUtil.findAll(em, Company.class).isEmpty()) {
            processRepository.saveAndFlush(process);
            companies = CompanyResourceIT.createEntity(em);
        } else {
            companies = TestUtil.findAll(em, Company.class).get(0);
        }
        em.persist(companies);
        em.flush();
        process.addCompanies(companies);
        processRepository.saveAndFlush(process);
        Long companiesId = companies.getId();
        // Get all the processList where companies equals to companiesId
        defaultProcessShouldBeFound("companiesId.equals=" + companiesId);

        // Get all the processList where companies equals to (companiesId + 1)
        defaultProcessShouldNotBeFound("companiesId.equals=" + (companiesId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProcessShouldBeFound(String filter) throws Exception {
        restProcessMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(process.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));

        // Check, that the count call also returns 1
        restProcessMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProcessShouldNotBeFound(String filter) throws Exception {
        restProcessMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProcessMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingProcess() throws Exception {
        // Get the process
        restProcessMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProcess() throws Exception {
        // Initialize the database
        processRepository.saveAndFlush(process);

        int databaseSizeBeforeUpdate = processRepository.findAll().size();

        // Update the process
        Process updatedProcess = processRepository.findById(process.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedProcess are not directly saved in db
        em.detach(updatedProcess);
        updatedProcess.title(UPDATED_TITLE).type(UPDATED_TYPE);
        ProcessDTO processDTO = processMapper.toDto(updatedProcess);

        restProcessMockMvc
            .perform(
                put(ENTITY_API_URL_ID, processDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(processDTO))
            )
            .andExpect(status().isOk());

        // Validate the Process in the database
        List<Process> processList = processRepository.findAll();
        assertThat(processList).hasSize(databaseSizeBeforeUpdate);
        Process testProcess = processList.get(processList.size() - 1);
        assertThat(testProcess.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testProcess.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingProcess() throws Exception {
        int databaseSizeBeforeUpdate = processRepository.findAll().size();
        process.setId(longCount.incrementAndGet());

        // Create the Process
        ProcessDTO processDTO = processMapper.toDto(process);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProcessMockMvc
            .perform(
                put(ENTITY_API_URL_ID, processDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(processDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Process in the database
        List<Process> processList = processRepository.findAll();
        assertThat(processList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProcess() throws Exception {
        int databaseSizeBeforeUpdate = processRepository.findAll().size();
        process.setId(longCount.incrementAndGet());

        // Create the Process
        ProcessDTO processDTO = processMapper.toDto(process);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProcessMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(processDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Process in the database
        List<Process> processList = processRepository.findAll();
        assertThat(processList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProcess() throws Exception {
        int databaseSizeBeforeUpdate = processRepository.findAll().size();
        process.setId(longCount.incrementAndGet());

        // Create the Process
        ProcessDTO processDTO = processMapper.toDto(process);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProcessMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(processDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Process in the database
        List<Process> processList = processRepository.findAll();
        assertThat(processList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProcessWithPatch() throws Exception {
        // Initialize the database
        processRepository.saveAndFlush(process);

        int databaseSizeBeforeUpdate = processRepository.findAll().size();

        // Update the process using partial update
        Process partialUpdatedProcess = new Process();
        partialUpdatedProcess.setId(process.getId());

        partialUpdatedProcess.title(UPDATED_TITLE).type(UPDATED_TYPE);

        restProcessMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProcess.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProcess))
            )
            .andExpect(status().isOk());

        // Validate the Process in the database
        List<Process> processList = processRepository.findAll();
        assertThat(processList).hasSize(databaseSizeBeforeUpdate);
        Process testProcess = processList.get(processList.size() - 1);
        assertThat(testProcess.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testProcess.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateProcessWithPatch() throws Exception {
        // Initialize the database
        processRepository.saveAndFlush(process);

        int databaseSizeBeforeUpdate = processRepository.findAll().size();

        // Update the process using partial update
        Process partialUpdatedProcess = new Process();
        partialUpdatedProcess.setId(process.getId());

        partialUpdatedProcess.title(UPDATED_TITLE).type(UPDATED_TYPE);

        restProcessMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProcess.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProcess))
            )
            .andExpect(status().isOk());

        // Validate the Process in the database
        List<Process> processList = processRepository.findAll();
        assertThat(processList).hasSize(databaseSizeBeforeUpdate);
        Process testProcess = processList.get(processList.size() - 1);
        assertThat(testProcess.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testProcess.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingProcess() throws Exception {
        int databaseSizeBeforeUpdate = processRepository.findAll().size();
        process.setId(longCount.incrementAndGet());

        // Create the Process
        ProcessDTO processDTO = processMapper.toDto(process);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProcessMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, processDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(processDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Process in the database
        List<Process> processList = processRepository.findAll();
        assertThat(processList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProcess() throws Exception {
        int databaseSizeBeforeUpdate = processRepository.findAll().size();
        process.setId(longCount.incrementAndGet());

        // Create the Process
        ProcessDTO processDTO = processMapper.toDto(process);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProcessMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(processDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Process in the database
        List<Process> processList = processRepository.findAll();
        assertThat(processList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProcess() throws Exception {
        int databaseSizeBeforeUpdate = processRepository.findAll().size();
        process.setId(longCount.incrementAndGet());

        // Create the Process
        ProcessDTO processDTO = processMapper.toDto(process);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProcessMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(processDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Process in the database
        List<Process> processList = processRepository.findAll();
        assertThat(processList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProcess() throws Exception {
        // Initialize the database
        processRepository.saveAndFlush(process);

        int databaseSizeBeforeDelete = processRepository.findAll().size();

        // Delete the process
        restProcessMockMvc
            .perform(delete(ENTITY_API_URL_ID, process.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Process> processList = processRepository.findAll();
        assertThat(processList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
