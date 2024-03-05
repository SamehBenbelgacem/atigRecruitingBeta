package atig.recruiting.beta.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import atig.recruiting.beta.IntegrationTest;
import atig.recruiting.beta.domain.Candidate;
import atig.recruiting.beta.domain.Certification;
import atig.recruiting.beta.repository.CertificationRepository;
import atig.recruiting.beta.service.dto.CertificationDTO;
import atig.recruiting.beta.service.mapper.CertificationMapper;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link CertificationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CertificationResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE = LocalDate.ofEpochDay(-1L);

    private static final String ENTITY_API_URL = "/api/certifications";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CertificationRepository certificationRepository;

    @Autowired
    private CertificationMapper certificationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCertificationMockMvc;

    private Certification certification;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Certification createEntity(EntityManager em) {
        Certification certification = new Certification().title(DEFAULT_TITLE).date(DEFAULT_DATE);
        return certification;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Certification createUpdatedEntity(EntityManager em) {
        Certification certification = new Certification().title(UPDATED_TITLE).date(UPDATED_DATE);
        return certification;
    }

    @BeforeEach
    public void initTest() {
        certification = createEntity(em);
    }

    @Test
    @Transactional
    void createCertification() throws Exception {
        int databaseSizeBeforeCreate = certificationRepository.findAll().size();
        // Create the Certification
        CertificationDTO certificationDTO = certificationMapper.toDto(certification);
        restCertificationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(certificationDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Certification in the database
        List<Certification> certificationList = certificationRepository.findAll();
        assertThat(certificationList).hasSize(databaseSizeBeforeCreate + 1);
        Certification testCertification = certificationList.get(certificationList.size() - 1);
        assertThat(testCertification.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testCertification.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    void createCertificationWithExistingId() throws Exception {
        // Create the Certification with an existing ID
        certification.setId(1L);
        CertificationDTO certificationDTO = certificationMapper.toDto(certification);

        int databaseSizeBeforeCreate = certificationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCertificationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(certificationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Certification in the database
        List<Certification> certificationList = certificationRepository.findAll();
        assertThat(certificationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCertifications() throws Exception {
        // Initialize the database
        certificationRepository.saveAndFlush(certification);

        // Get all the certificationList
        restCertificationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(certification.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }

    @Test
    @Transactional
    void getCertification() throws Exception {
        // Initialize the database
        certificationRepository.saveAndFlush(certification);

        // Get the certification
        restCertificationMockMvc
            .perform(get(ENTITY_API_URL_ID, certification.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(certification.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    void getCertificationsByIdFiltering() throws Exception {
        // Initialize the database
        certificationRepository.saveAndFlush(certification);

        Long id = certification.getId();

        defaultCertificationShouldBeFound("id.equals=" + id);
        defaultCertificationShouldNotBeFound("id.notEquals=" + id);

        defaultCertificationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCertificationShouldNotBeFound("id.greaterThan=" + id);

        defaultCertificationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCertificationShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCertificationsByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        certificationRepository.saveAndFlush(certification);

        // Get all the certificationList where title equals to DEFAULT_TITLE
        defaultCertificationShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the certificationList where title equals to UPDATED_TITLE
        defaultCertificationShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllCertificationsByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        certificationRepository.saveAndFlush(certification);

        // Get all the certificationList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultCertificationShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the certificationList where title equals to UPDATED_TITLE
        defaultCertificationShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllCertificationsByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        certificationRepository.saveAndFlush(certification);

        // Get all the certificationList where title is not null
        defaultCertificationShouldBeFound("title.specified=true");

        // Get all the certificationList where title is null
        defaultCertificationShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    void getAllCertificationsByTitleContainsSomething() throws Exception {
        // Initialize the database
        certificationRepository.saveAndFlush(certification);

        // Get all the certificationList where title contains DEFAULT_TITLE
        defaultCertificationShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the certificationList where title contains UPDATED_TITLE
        defaultCertificationShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllCertificationsByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        certificationRepository.saveAndFlush(certification);

        // Get all the certificationList where title does not contain DEFAULT_TITLE
        defaultCertificationShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the certificationList where title does not contain UPDATED_TITLE
        defaultCertificationShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllCertificationsByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        certificationRepository.saveAndFlush(certification);

        // Get all the certificationList where date equals to DEFAULT_DATE
        defaultCertificationShouldBeFound("date.equals=" + DEFAULT_DATE);

        // Get all the certificationList where date equals to UPDATED_DATE
        defaultCertificationShouldNotBeFound("date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllCertificationsByDateIsInShouldWork() throws Exception {
        // Initialize the database
        certificationRepository.saveAndFlush(certification);

        // Get all the certificationList where date in DEFAULT_DATE or UPDATED_DATE
        defaultCertificationShouldBeFound("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE);

        // Get all the certificationList where date equals to UPDATED_DATE
        defaultCertificationShouldNotBeFound("date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllCertificationsByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        certificationRepository.saveAndFlush(certification);

        // Get all the certificationList where date is not null
        defaultCertificationShouldBeFound("date.specified=true");

        // Get all the certificationList where date is null
        defaultCertificationShouldNotBeFound("date.specified=false");
    }

    @Test
    @Transactional
    void getAllCertificationsByDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        certificationRepository.saveAndFlush(certification);

        // Get all the certificationList where date is greater than or equal to DEFAULT_DATE
        defaultCertificationShouldBeFound("date.greaterThanOrEqual=" + DEFAULT_DATE);

        // Get all the certificationList where date is greater than or equal to UPDATED_DATE
        defaultCertificationShouldNotBeFound("date.greaterThanOrEqual=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllCertificationsByDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        certificationRepository.saveAndFlush(certification);

        // Get all the certificationList where date is less than or equal to DEFAULT_DATE
        defaultCertificationShouldBeFound("date.lessThanOrEqual=" + DEFAULT_DATE);

        // Get all the certificationList where date is less than or equal to SMALLER_DATE
        defaultCertificationShouldNotBeFound("date.lessThanOrEqual=" + SMALLER_DATE);
    }

    @Test
    @Transactional
    void getAllCertificationsByDateIsLessThanSomething() throws Exception {
        // Initialize the database
        certificationRepository.saveAndFlush(certification);

        // Get all the certificationList where date is less than DEFAULT_DATE
        defaultCertificationShouldNotBeFound("date.lessThan=" + DEFAULT_DATE);

        // Get all the certificationList where date is less than UPDATED_DATE
        defaultCertificationShouldBeFound("date.lessThan=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllCertificationsByDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        certificationRepository.saveAndFlush(certification);

        // Get all the certificationList where date is greater than DEFAULT_DATE
        defaultCertificationShouldNotBeFound("date.greaterThan=" + DEFAULT_DATE);

        // Get all the certificationList where date is greater than SMALLER_DATE
        defaultCertificationShouldBeFound("date.greaterThan=" + SMALLER_DATE);
    }

    @Test
    @Transactional
    void getAllCertificationsByCertificationCandidateIsEqualToSomething() throws Exception {
        Candidate certificationCandidate;
        if (TestUtil.findAll(em, Candidate.class).isEmpty()) {
            certificationRepository.saveAndFlush(certification);
            certificationCandidate = CandidateResourceIT.createEntity(em);
        } else {
            certificationCandidate = TestUtil.findAll(em, Candidate.class).get(0);
        }
        em.persist(certificationCandidate);
        em.flush();
        certification.setCertificationCandidate(certificationCandidate);
        certificationRepository.saveAndFlush(certification);
        Long certificationCandidateId = certificationCandidate.getId();
        // Get all the certificationList where certificationCandidate equals to certificationCandidateId
        defaultCertificationShouldBeFound("certificationCandidateId.equals=" + certificationCandidateId);

        // Get all the certificationList where certificationCandidate equals to (certificationCandidateId + 1)
        defaultCertificationShouldNotBeFound("certificationCandidateId.equals=" + (certificationCandidateId + 1));
    }

    @Test
    @Transactional
    void getAllCertificationsByCandidateIsEqualToSomething() throws Exception {
        Candidate candidate;
        if (TestUtil.findAll(em, Candidate.class).isEmpty()) {
            certificationRepository.saveAndFlush(certification);
            candidate = CandidateResourceIT.createEntity(em);
        } else {
            candidate = TestUtil.findAll(em, Candidate.class).get(0);
        }
        em.persist(candidate);
        em.flush();
        certification.setCandidate(candidate);
        certificationRepository.saveAndFlush(certification);
        Long candidateId = candidate.getId();
        // Get all the certificationList where candidate equals to candidateId
        defaultCertificationShouldBeFound("candidateId.equals=" + candidateId);

        // Get all the certificationList where candidate equals to (candidateId + 1)
        defaultCertificationShouldNotBeFound("candidateId.equals=" + (candidateId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCertificationShouldBeFound(String filter) throws Exception {
        restCertificationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(certification.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));

        // Check, that the count call also returns 1
        restCertificationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCertificationShouldNotBeFound(String filter) throws Exception {
        restCertificationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCertificationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCertification() throws Exception {
        // Get the certification
        restCertificationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCertification() throws Exception {
        // Initialize the database
        certificationRepository.saveAndFlush(certification);

        int databaseSizeBeforeUpdate = certificationRepository.findAll().size();

        // Update the certification
        Certification updatedCertification = certificationRepository.findById(certification.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCertification are not directly saved in db
        em.detach(updatedCertification);
        updatedCertification.title(UPDATED_TITLE).date(UPDATED_DATE);
        CertificationDTO certificationDTO = certificationMapper.toDto(updatedCertification);

        restCertificationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, certificationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(certificationDTO))
            )
            .andExpect(status().isOk());

        // Validate the Certification in the database
        List<Certification> certificationList = certificationRepository.findAll();
        assertThat(certificationList).hasSize(databaseSizeBeforeUpdate);
        Certification testCertification = certificationList.get(certificationList.size() - 1);
        assertThat(testCertification.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testCertification.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingCertification() throws Exception {
        int databaseSizeBeforeUpdate = certificationRepository.findAll().size();
        certification.setId(longCount.incrementAndGet());

        // Create the Certification
        CertificationDTO certificationDTO = certificationMapper.toDto(certification);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCertificationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, certificationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(certificationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Certification in the database
        List<Certification> certificationList = certificationRepository.findAll();
        assertThat(certificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCertification() throws Exception {
        int databaseSizeBeforeUpdate = certificationRepository.findAll().size();
        certification.setId(longCount.incrementAndGet());

        // Create the Certification
        CertificationDTO certificationDTO = certificationMapper.toDto(certification);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCertificationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(certificationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Certification in the database
        List<Certification> certificationList = certificationRepository.findAll();
        assertThat(certificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCertification() throws Exception {
        int databaseSizeBeforeUpdate = certificationRepository.findAll().size();
        certification.setId(longCount.incrementAndGet());

        // Create the Certification
        CertificationDTO certificationDTO = certificationMapper.toDto(certification);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCertificationMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(certificationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Certification in the database
        List<Certification> certificationList = certificationRepository.findAll();
        assertThat(certificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCertificationWithPatch() throws Exception {
        // Initialize the database
        certificationRepository.saveAndFlush(certification);

        int databaseSizeBeforeUpdate = certificationRepository.findAll().size();

        // Update the certification using partial update
        Certification partialUpdatedCertification = new Certification();
        partialUpdatedCertification.setId(certification.getId());

        restCertificationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCertification.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCertification))
            )
            .andExpect(status().isOk());

        // Validate the Certification in the database
        List<Certification> certificationList = certificationRepository.findAll();
        assertThat(certificationList).hasSize(databaseSizeBeforeUpdate);
        Certification testCertification = certificationList.get(certificationList.size() - 1);
        assertThat(testCertification.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testCertification.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    void fullUpdateCertificationWithPatch() throws Exception {
        // Initialize the database
        certificationRepository.saveAndFlush(certification);

        int databaseSizeBeforeUpdate = certificationRepository.findAll().size();

        // Update the certification using partial update
        Certification partialUpdatedCertification = new Certification();
        partialUpdatedCertification.setId(certification.getId());

        partialUpdatedCertification.title(UPDATED_TITLE).date(UPDATED_DATE);

        restCertificationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCertification.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCertification))
            )
            .andExpect(status().isOk());

        // Validate the Certification in the database
        List<Certification> certificationList = certificationRepository.findAll();
        assertThat(certificationList).hasSize(databaseSizeBeforeUpdate);
        Certification testCertification = certificationList.get(certificationList.size() - 1);
        assertThat(testCertification.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testCertification.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingCertification() throws Exception {
        int databaseSizeBeforeUpdate = certificationRepository.findAll().size();
        certification.setId(longCount.incrementAndGet());

        // Create the Certification
        CertificationDTO certificationDTO = certificationMapper.toDto(certification);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCertificationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, certificationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(certificationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Certification in the database
        List<Certification> certificationList = certificationRepository.findAll();
        assertThat(certificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCertification() throws Exception {
        int databaseSizeBeforeUpdate = certificationRepository.findAll().size();
        certification.setId(longCount.incrementAndGet());

        // Create the Certification
        CertificationDTO certificationDTO = certificationMapper.toDto(certification);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCertificationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(certificationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Certification in the database
        List<Certification> certificationList = certificationRepository.findAll();
        assertThat(certificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCertification() throws Exception {
        int databaseSizeBeforeUpdate = certificationRepository.findAll().size();
        certification.setId(longCount.incrementAndGet());

        // Create the Certification
        CertificationDTO certificationDTO = certificationMapper.toDto(certification);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCertificationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(certificationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Certification in the database
        List<Certification> certificationList = certificationRepository.findAll();
        assertThat(certificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCertification() throws Exception {
        // Initialize the database
        certificationRepository.saveAndFlush(certification);

        int databaseSizeBeforeDelete = certificationRepository.findAll().size();

        // Delete the certification
        restCertificationMockMvc
            .perform(delete(ENTITY_API_URL_ID, certification.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Certification> certificationList = certificationRepository.findAll();
        assertThat(certificationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
