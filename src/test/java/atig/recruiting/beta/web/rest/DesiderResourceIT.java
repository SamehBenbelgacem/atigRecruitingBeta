package atig.recruiting.beta.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import atig.recruiting.beta.IntegrationTest;
import atig.recruiting.beta.domain.Company;
import atig.recruiting.beta.domain.Desider;
import atig.recruiting.beta.repository.DesiderRepository;
import atig.recruiting.beta.service.dto.DesiderDTO;
import atig.recruiting.beta.service.mapper.DesiderMapper;
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
 * Integration tests for the {@link DesiderResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DesiderResourceIT {

    private static final String DEFAULT_FULL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FULL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE = "BBBBBBBBBB";

    private static final String DEFAULT_ROLE = "AAAAAAAAAA";
    private static final String UPDATED_ROLE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/desiders";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DesiderRepository desiderRepository;

    @Autowired
    private DesiderMapper desiderMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDesiderMockMvc;

    private Desider desider;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Desider createEntity(EntityManager em) {
        Desider desider = new Desider().fullName(DEFAULT_FULL_NAME).email(DEFAULT_EMAIL).mobile(DEFAULT_MOBILE).role(DEFAULT_ROLE);
        return desider;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Desider createUpdatedEntity(EntityManager em) {
        Desider desider = new Desider().fullName(UPDATED_FULL_NAME).email(UPDATED_EMAIL).mobile(UPDATED_MOBILE).role(UPDATED_ROLE);
        return desider;
    }

    @BeforeEach
    public void initTest() {
        desider = createEntity(em);
    }

    @Test
    @Transactional
    void createDesider() throws Exception {
        int databaseSizeBeforeCreate = desiderRepository.findAll().size();
        // Create the Desider
        DesiderDTO desiderDTO = desiderMapper.toDto(desider);
        restDesiderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(desiderDTO)))
            .andExpect(status().isCreated());

        // Validate the Desider in the database
        List<Desider> desiderList = desiderRepository.findAll();
        assertThat(desiderList).hasSize(databaseSizeBeforeCreate + 1);
        Desider testDesider = desiderList.get(desiderList.size() - 1);
        assertThat(testDesider.getFullName()).isEqualTo(DEFAULT_FULL_NAME);
        assertThat(testDesider.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testDesider.getMobile()).isEqualTo(DEFAULT_MOBILE);
        assertThat(testDesider.getRole()).isEqualTo(DEFAULT_ROLE);
    }

    @Test
    @Transactional
    void createDesiderWithExistingId() throws Exception {
        // Create the Desider with an existing ID
        desider.setId(1L);
        DesiderDTO desiderDTO = desiderMapper.toDto(desider);

        int databaseSizeBeforeCreate = desiderRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDesiderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(desiderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Desider in the database
        List<Desider> desiderList = desiderRepository.findAll();
        assertThat(desiderList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDesiders() throws Exception {
        // Initialize the database
        desiderRepository.saveAndFlush(desider);

        // Get all the desiderList
        restDesiderMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(desider.getId().intValue())))
            .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE)))
            .andExpect(jsonPath("$.[*].role").value(hasItem(DEFAULT_ROLE)));
    }

    @Test
    @Transactional
    void getDesider() throws Exception {
        // Initialize the database
        desiderRepository.saveAndFlush(desider);

        // Get the desider
        restDesiderMockMvc
            .perform(get(ENTITY_API_URL_ID, desider.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(desider.getId().intValue()))
            .andExpect(jsonPath("$.fullName").value(DEFAULT_FULL_NAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.mobile").value(DEFAULT_MOBILE))
            .andExpect(jsonPath("$.role").value(DEFAULT_ROLE));
    }

    @Test
    @Transactional
    void getDesidersByIdFiltering() throws Exception {
        // Initialize the database
        desiderRepository.saveAndFlush(desider);

        Long id = desider.getId();

        defaultDesiderShouldBeFound("id.equals=" + id);
        defaultDesiderShouldNotBeFound("id.notEquals=" + id);

        defaultDesiderShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDesiderShouldNotBeFound("id.greaterThan=" + id);

        defaultDesiderShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDesiderShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDesidersByFullNameIsEqualToSomething() throws Exception {
        // Initialize the database
        desiderRepository.saveAndFlush(desider);

        // Get all the desiderList where fullName equals to DEFAULT_FULL_NAME
        defaultDesiderShouldBeFound("fullName.equals=" + DEFAULT_FULL_NAME);

        // Get all the desiderList where fullName equals to UPDATED_FULL_NAME
        defaultDesiderShouldNotBeFound("fullName.equals=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    void getAllDesidersByFullNameIsInShouldWork() throws Exception {
        // Initialize the database
        desiderRepository.saveAndFlush(desider);

        // Get all the desiderList where fullName in DEFAULT_FULL_NAME or UPDATED_FULL_NAME
        defaultDesiderShouldBeFound("fullName.in=" + DEFAULT_FULL_NAME + "," + UPDATED_FULL_NAME);

        // Get all the desiderList where fullName equals to UPDATED_FULL_NAME
        defaultDesiderShouldNotBeFound("fullName.in=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    void getAllDesidersByFullNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        desiderRepository.saveAndFlush(desider);

        // Get all the desiderList where fullName is not null
        defaultDesiderShouldBeFound("fullName.specified=true");

        // Get all the desiderList where fullName is null
        defaultDesiderShouldNotBeFound("fullName.specified=false");
    }

    @Test
    @Transactional
    void getAllDesidersByFullNameContainsSomething() throws Exception {
        // Initialize the database
        desiderRepository.saveAndFlush(desider);

        // Get all the desiderList where fullName contains DEFAULT_FULL_NAME
        defaultDesiderShouldBeFound("fullName.contains=" + DEFAULT_FULL_NAME);

        // Get all the desiderList where fullName contains UPDATED_FULL_NAME
        defaultDesiderShouldNotBeFound("fullName.contains=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    void getAllDesidersByFullNameNotContainsSomething() throws Exception {
        // Initialize the database
        desiderRepository.saveAndFlush(desider);

        // Get all the desiderList where fullName does not contain DEFAULT_FULL_NAME
        defaultDesiderShouldNotBeFound("fullName.doesNotContain=" + DEFAULT_FULL_NAME);

        // Get all the desiderList where fullName does not contain UPDATED_FULL_NAME
        defaultDesiderShouldBeFound("fullName.doesNotContain=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    void getAllDesidersByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        desiderRepository.saveAndFlush(desider);

        // Get all the desiderList where email equals to DEFAULT_EMAIL
        defaultDesiderShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the desiderList where email equals to UPDATED_EMAIL
        defaultDesiderShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllDesidersByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        desiderRepository.saveAndFlush(desider);

        // Get all the desiderList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultDesiderShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the desiderList where email equals to UPDATED_EMAIL
        defaultDesiderShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllDesidersByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        desiderRepository.saveAndFlush(desider);

        // Get all the desiderList where email is not null
        defaultDesiderShouldBeFound("email.specified=true");

        // Get all the desiderList where email is null
        defaultDesiderShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    void getAllDesidersByEmailContainsSomething() throws Exception {
        // Initialize the database
        desiderRepository.saveAndFlush(desider);

        // Get all the desiderList where email contains DEFAULT_EMAIL
        defaultDesiderShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the desiderList where email contains UPDATED_EMAIL
        defaultDesiderShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllDesidersByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        desiderRepository.saveAndFlush(desider);

        // Get all the desiderList where email does not contain DEFAULT_EMAIL
        defaultDesiderShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the desiderList where email does not contain UPDATED_EMAIL
        defaultDesiderShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllDesidersByMobileIsEqualToSomething() throws Exception {
        // Initialize the database
        desiderRepository.saveAndFlush(desider);

        // Get all the desiderList where mobile equals to DEFAULT_MOBILE
        defaultDesiderShouldBeFound("mobile.equals=" + DEFAULT_MOBILE);

        // Get all the desiderList where mobile equals to UPDATED_MOBILE
        defaultDesiderShouldNotBeFound("mobile.equals=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    void getAllDesidersByMobileIsInShouldWork() throws Exception {
        // Initialize the database
        desiderRepository.saveAndFlush(desider);

        // Get all the desiderList where mobile in DEFAULT_MOBILE or UPDATED_MOBILE
        defaultDesiderShouldBeFound("mobile.in=" + DEFAULT_MOBILE + "," + UPDATED_MOBILE);

        // Get all the desiderList where mobile equals to UPDATED_MOBILE
        defaultDesiderShouldNotBeFound("mobile.in=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    void getAllDesidersByMobileIsNullOrNotNull() throws Exception {
        // Initialize the database
        desiderRepository.saveAndFlush(desider);

        // Get all the desiderList where mobile is not null
        defaultDesiderShouldBeFound("mobile.specified=true");

        // Get all the desiderList where mobile is null
        defaultDesiderShouldNotBeFound("mobile.specified=false");
    }

    @Test
    @Transactional
    void getAllDesidersByMobileContainsSomething() throws Exception {
        // Initialize the database
        desiderRepository.saveAndFlush(desider);

        // Get all the desiderList where mobile contains DEFAULT_MOBILE
        defaultDesiderShouldBeFound("mobile.contains=" + DEFAULT_MOBILE);

        // Get all the desiderList where mobile contains UPDATED_MOBILE
        defaultDesiderShouldNotBeFound("mobile.contains=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    void getAllDesidersByMobileNotContainsSomething() throws Exception {
        // Initialize the database
        desiderRepository.saveAndFlush(desider);

        // Get all the desiderList where mobile does not contain DEFAULT_MOBILE
        defaultDesiderShouldNotBeFound("mobile.doesNotContain=" + DEFAULT_MOBILE);

        // Get all the desiderList where mobile does not contain UPDATED_MOBILE
        defaultDesiderShouldBeFound("mobile.doesNotContain=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    void getAllDesidersByRoleIsEqualToSomething() throws Exception {
        // Initialize the database
        desiderRepository.saveAndFlush(desider);

        // Get all the desiderList where role equals to DEFAULT_ROLE
        defaultDesiderShouldBeFound("role.equals=" + DEFAULT_ROLE);

        // Get all the desiderList where role equals to UPDATED_ROLE
        defaultDesiderShouldNotBeFound("role.equals=" + UPDATED_ROLE);
    }

    @Test
    @Transactional
    void getAllDesidersByRoleIsInShouldWork() throws Exception {
        // Initialize the database
        desiderRepository.saveAndFlush(desider);

        // Get all the desiderList where role in DEFAULT_ROLE or UPDATED_ROLE
        defaultDesiderShouldBeFound("role.in=" + DEFAULT_ROLE + "," + UPDATED_ROLE);

        // Get all the desiderList where role equals to UPDATED_ROLE
        defaultDesiderShouldNotBeFound("role.in=" + UPDATED_ROLE);
    }

    @Test
    @Transactional
    void getAllDesidersByRoleIsNullOrNotNull() throws Exception {
        // Initialize the database
        desiderRepository.saveAndFlush(desider);

        // Get all the desiderList where role is not null
        defaultDesiderShouldBeFound("role.specified=true");

        // Get all the desiderList where role is null
        defaultDesiderShouldNotBeFound("role.specified=false");
    }

    @Test
    @Transactional
    void getAllDesidersByRoleContainsSomething() throws Exception {
        // Initialize the database
        desiderRepository.saveAndFlush(desider);

        // Get all the desiderList where role contains DEFAULT_ROLE
        defaultDesiderShouldBeFound("role.contains=" + DEFAULT_ROLE);

        // Get all the desiderList where role contains UPDATED_ROLE
        defaultDesiderShouldNotBeFound("role.contains=" + UPDATED_ROLE);
    }

    @Test
    @Transactional
    void getAllDesidersByRoleNotContainsSomething() throws Exception {
        // Initialize the database
        desiderRepository.saveAndFlush(desider);

        // Get all the desiderList where role does not contain DEFAULT_ROLE
        defaultDesiderShouldNotBeFound("role.doesNotContain=" + DEFAULT_ROLE);

        // Get all the desiderList where role does not contain UPDATED_ROLE
        defaultDesiderShouldBeFound("role.doesNotContain=" + UPDATED_ROLE);
    }

    @Test
    @Transactional
    void getAllDesidersByDesiderCompanyIsEqualToSomething() throws Exception {
        Company desiderCompany;
        if (TestUtil.findAll(em, Company.class).isEmpty()) {
            desiderRepository.saveAndFlush(desider);
            desiderCompany = CompanyResourceIT.createEntity(em);
        } else {
            desiderCompany = TestUtil.findAll(em, Company.class).get(0);
        }
        em.persist(desiderCompany);
        em.flush();
        desider.setDesiderCompany(desiderCompany);
        desiderRepository.saveAndFlush(desider);
        Long desiderCompanyId = desiderCompany.getId();
        // Get all the desiderList where desiderCompany equals to desiderCompanyId
        defaultDesiderShouldBeFound("desiderCompanyId.equals=" + desiderCompanyId);

        // Get all the desiderList where desiderCompany equals to (desiderCompanyId + 1)
        defaultDesiderShouldNotBeFound("desiderCompanyId.equals=" + (desiderCompanyId + 1));
    }

    @Test
    @Transactional
    void getAllDesidersByCompanyIsEqualToSomething() throws Exception {
        Company company;
        if (TestUtil.findAll(em, Company.class).isEmpty()) {
            desiderRepository.saveAndFlush(desider);
            company = CompanyResourceIT.createEntity(em);
        } else {
            company = TestUtil.findAll(em, Company.class).get(0);
        }
        em.persist(company);
        em.flush();
        desider.setCompany(company);
        desiderRepository.saveAndFlush(desider);
        Long companyId = company.getId();
        // Get all the desiderList where company equals to companyId
        defaultDesiderShouldBeFound("companyId.equals=" + companyId);

        // Get all the desiderList where company equals to (companyId + 1)
        defaultDesiderShouldNotBeFound("companyId.equals=" + (companyId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDesiderShouldBeFound(String filter) throws Exception {
        restDesiderMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(desider.getId().intValue())))
            .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE)))
            .andExpect(jsonPath("$.[*].role").value(hasItem(DEFAULT_ROLE)));

        // Check, that the count call also returns 1
        restDesiderMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDesiderShouldNotBeFound(String filter) throws Exception {
        restDesiderMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDesiderMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDesider() throws Exception {
        // Get the desider
        restDesiderMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDesider() throws Exception {
        // Initialize the database
        desiderRepository.saveAndFlush(desider);

        int databaseSizeBeforeUpdate = desiderRepository.findAll().size();

        // Update the desider
        Desider updatedDesider = desiderRepository.findById(desider.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDesider are not directly saved in db
        em.detach(updatedDesider);
        updatedDesider.fullName(UPDATED_FULL_NAME).email(UPDATED_EMAIL).mobile(UPDATED_MOBILE).role(UPDATED_ROLE);
        DesiderDTO desiderDTO = desiderMapper.toDto(updatedDesider);

        restDesiderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, desiderDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(desiderDTO))
            )
            .andExpect(status().isOk());

        // Validate the Desider in the database
        List<Desider> desiderList = desiderRepository.findAll();
        assertThat(desiderList).hasSize(databaseSizeBeforeUpdate);
        Desider testDesider = desiderList.get(desiderList.size() - 1);
        assertThat(testDesider.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testDesider.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testDesider.getMobile()).isEqualTo(UPDATED_MOBILE);
        assertThat(testDesider.getRole()).isEqualTo(UPDATED_ROLE);
    }

    @Test
    @Transactional
    void putNonExistingDesider() throws Exception {
        int databaseSizeBeforeUpdate = desiderRepository.findAll().size();
        desider.setId(longCount.incrementAndGet());

        // Create the Desider
        DesiderDTO desiderDTO = desiderMapper.toDto(desider);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDesiderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, desiderDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(desiderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Desider in the database
        List<Desider> desiderList = desiderRepository.findAll();
        assertThat(desiderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDesider() throws Exception {
        int databaseSizeBeforeUpdate = desiderRepository.findAll().size();
        desider.setId(longCount.incrementAndGet());

        // Create the Desider
        DesiderDTO desiderDTO = desiderMapper.toDto(desider);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDesiderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(desiderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Desider in the database
        List<Desider> desiderList = desiderRepository.findAll();
        assertThat(desiderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDesider() throws Exception {
        int databaseSizeBeforeUpdate = desiderRepository.findAll().size();
        desider.setId(longCount.incrementAndGet());

        // Create the Desider
        DesiderDTO desiderDTO = desiderMapper.toDto(desider);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDesiderMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(desiderDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Desider in the database
        List<Desider> desiderList = desiderRepository.findAll();
        assertThat(desiderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDesiderWithPatch() throws Exception {
        // Initialize the database
        desiderRepository.saveAndFlush(desider);

        int databaseSizeBeforeUpdate = desiderRepository.findAll().size();

        // Update the desider using partial update
        Desider partialUpdatedDesider = new Desider();
        partialUpdatedDesider.setId(desider.getId());

        partialUpdatedDesider.email(UPDATED_EMAIL).role(UPDATED_ROLE);

        restDesiderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDesider.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDesider))
            )
            .andExpect(status().isOk());

        // Validate the Desider in the database
        List<Desider> desiderList = desiderRepository.findAll();
        assertThat(desiderList).hasSize(databaseSizeBeforeUpdate);
        Desider testDesider = desiderList.get(desiderList.size() - 1);
        assertThat(testDesider.getFullName()).isEqualTo(DEFAULT_FULL_NAME);
        assertThat(testDesider.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testDesider.getMobile()).isEqualTo(DEFAULT_MOBILE);
        assertThat(testDesider.getRole()).isEqualTo(UPDATED_ROLE);
    }

    @Test
    @Transactional
    void fullUpdateDesiderWithPatch() throws Exception {
        // Initialize the database
        desiderRepository.saveAndFlush(desider);

        int databaseSizeBeforeUpdate = desiderRepository.findAll().size();

        // Update the desider using partial update
        Desider partialUpdatedDesider = new Desider();
        partialUpdatedDesider.setId(desider.getId());

        partialUpdatedDesider.fullName(UPDATED_FULL_NAME).email(UPDATED_EMAIL).mobile(UPDATED_MOBILE).role(UPDATED_ROLE);

        restDesiderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDesider.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDesider))
            )
            .andExpect(status().isOk());

        // Validate the Desider in the database
        List<Desider> desiderList = desiderRepository.findAll();
        assertThat(desiderList).hasSize(databaseSizeBeforeUpdate);
        Desider testDesider = desiderList.get(desiderList.size() - 1);
        assertThat(testDesider.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testDesider.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testDesider.getMobile()).isEqualTo(UPDATED_MOBILE);
        assertThat(testDesider.getRole()).isEqualTo(UPDATED_ROLE);
    }

    @Test
    @Transactional
    void patchNonExistingDesider() throws Exception {
        int databaseSizeBeforeUpdate = desiderRepository.findAll().size();
        desider.setId(longCount.incrementAndGet());

        // Create the Desider
        DesiderDTO desiderDTO = desiderMapper.toDto(desider);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDesiderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, desiderDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(desiderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Desider in the database
        List<Desider> desiderList = desiderRepository.findAll();
        assertThat(desiderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDesider() throws Exception {
        int databaseSizeBeforeUpdate = desiderRepository.findAll().size();
        desider.setId(longCount.incrementAndGet());

        // Create the Desider
        DesiderDTO desiderDTO = desiderMapper.toDto(desider);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDesiderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(desiderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Desider in the database
        List<Desider> desiderList = desiderRepository.findAll();
        assertThat(desiderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDesider() throws Exception {
        int databaseSizeBeforeUpdate = desiderRepository.findAll().size();
        desider.setId(longCount.incrementAndGet());

        // Create the Desider
        DesiderDTO desiderDTO = desiderMapper.toDto(desider);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDesiderMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(desiderDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Desider in the database
        List<Desider> desiderList = desiderRepository.findAll();
        assertThat(desiderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDesider() throws Exception {
        // Initialize the database
        desiderRepository.saveAndFlush(desider);

        int databaseSizeBeforeDelete = desiderRepository.findAll().size();

        // Delete the desider
        restDesiderMockMvc
            .perform(delete(ENTITY_API_URL_ID, desider.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Desider> desiderList = desiderRepository.findAll();
        assertThat(desiderList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
