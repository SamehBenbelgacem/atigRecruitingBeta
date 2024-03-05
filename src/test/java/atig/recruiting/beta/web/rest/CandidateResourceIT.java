package atig.recruiting.beta.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import atig.recruiting.beta.IntegrationTest;
import atig.recruiting.beta.domain.Candidate;
import atig.recruiting.beta.domain.CandidateAdditionalInfos;
import atig.recruiting.beta.domain.Category;
import atig.recruiting.beta.domain.Certification;
import atig.recruiting.beta.domain.Education;
import atig.recruiting.beta.domain.Email;
import atig.recruiting.beta.domain.Experience;
import atig.recruiting.beta.domain.Language;
import atig.recruiting.beta.domain.Note;
import atig.recruiting.beta.domain.Notification;
import atig.recruiting.beta.domain.Process;
import atig.recruiting.beta.domain.ProcessStep;
import atig.recruiting.beta.domain.Skill;
import atig.recruiting.beta.domain.SubCategory;
import atig.recruiting.beta.domain.Tag;
import atig.recruiting.beta.repository.CandidateRepository;
import atig.recruiting.beta.service.CandidateService;
import atig.recruiting.beta.service.dto.CandidateDTO;
import atig.recruiting.beta.service.mapper.CandidateMapper;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CandidateResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CandidateResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final byte[] DEFAULT_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PHOTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PHOTO_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_PROFESSION = "AAAAAAAAAA";
    private static final String UPDATED_PROFESSION = "BBBBBBBBBB";

    private static final Integer DEFAULT_NB_EXPERIENCE = 1;
    private static final Integer UPDATED_NB_EXPERIENCE = 2;
    private static final Integer SMALLER_NB_EXPERIENCE = 1 - 1;

    private static final String DEFAULT_PERSONAL_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_PERSONAL_EMAIL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/candidates";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CandidateRepository candidateRepository;

    @Mock
    private CandidateRepository candidateRepositoryMock;

    @Autowired
    private CandidateMapper candidateMapper;

    @Mock
    private CandidateService candidateServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCandidateMockMvc;

    private Candidate candidate;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Candidate createEntity(EntityManager em) {
        Candidate candidate = new Candidate()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .photo(DEFAULT_PHOTO)
            .photoContentType(DEFAULT_PHOTO_CONTENT_TYPE)
            .profession(DEFAULT_PROFESSION)
            .nbExperience(DEFAULT_NB_EXPERIENCE)
            .personalEmail(DEFAULT_PERSONAL_EMAIL);
        return candidate;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Candidate createUpdatedEntity(EntityManager em) {
        Candidate candidate = new Candidate()
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE)
            .profession(UPDATED_PROFESSION)
            .nbExperience(UPDATED_NB_EXPERIENCE)
            .personalEmail(UPDATED_PERSONAL_EMAIL);
        return candidate;
    }

    @BeforeEach
    public void initTest() {
        candidate = createEntity(em);
    }

    @Test
    @Transactional
    void createCandidate() throws Exception {
        int databaseSizeBeforeCreate = candidateRepository.findAll().size();
        // Create the Candidate
        CandidateDTO candidateDTO = candidateMapper.toDto(candidate);
        restCandidateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(candidateDTO)))
            .andExpect(status().isCreated());

        // Validate the Candidate in the database
        List<Candidate> candidateList = candidateRepository.findAll();
        assertThat(candidateList).hasSize(databaseSizeBeforeCreate + 1);
        Candidate testCandidate = candidateList.get(candidateList.size() - 1);
        assertThat(testCandidate.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testCandidate.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testCandidate.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testCandidate.getPhotoContentType()).isEqualTo(DEFAULT_PHOTO_CONTENT_TYPE);
        assertThat(testCandidate.getProfession()).isEqualTo(DEFAULT_PROFESSION);
        assertThat(testCandidate.getNbExperience()).isEqualTo(DEFAULT_NB_EXPERIENCE);
        assertThat(testCandidate.getPersonalEmail()).isEqualTo(DEFAULT_PERSONAL_EMAIL);
    }

    @Test
    @Transactional
    void createCandidateWithExistingId() throws Exception {
        // Create the Candidate with an existing ID
        candidate.setId(1L);
        CandidateDTO candidateDTO = candidateMapper.toDto(candidate);

        int databaseSizeBeforeCreate = candidateRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCandidateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(candidateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Candidate in the database
        List<Candidate> candidateList = candidateRepository.findAll();
        assertThat(candidateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = candidateRepository.findAll().size();
        // set the field null
        candidate.setFirstName(null);

        // Create the Candidate, which fails.
        CandidateDTO candidateDTO = candidateMapper.toDto(candidate);

        restCandidateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(candidateDTO)))
            .andExpect(status().isBadRequest());

        List<Candidate> candidateList = candidateRepository.findAll();
        assertThat(candidateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = candidateRepository.findAll().size();
        // set the field null
        candidate.setLastName(null);

        // Create the Candidate, which fails.
        CandidateDTO candidateDTO = candidateMapper.toDto(candidate);

        restCandidateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(candidateDTO)))
            .andExpect(status().isBadRequest());

        List<Candidate> candidateList = candidateRepository.findAll();
        assertThat(candidateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkProfessionIsRequired() throws Exception {
        int databaseSizeBeforeTest = candidateRepository.findAll().size();
        // set the field null
        candidate.setProfession(null);

        // Create the Candidate, which fails.
        CandidateDTO candidateDTO = candidateMapper.toDto(candidate);

        restCandidateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(candidateDTO)))
            .andExpect(status().isBadRequest());

        List<Candidate> candidateList = candidateRepository.findAll();
        assertThat(candidateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPersonalEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = candidateRepository.findAll().size();
        // set the field null
        candidate.setPersonalEmail(null);

        // Create the Candidate, which fails.
        CandidateDTO candidateDTO = candidateMapper.toDto(candidate);

        restCandidateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(candidateDTO)))
            .andExpect(status().isBadRequest());

        List<Candidate> candidateList = candidateRepository.findAll();
        assertThat(candidateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCandidates() throws Exception {
        // Initialize the database
        candidateRepository.saveAndFlush(candidate);

        // Get all the candidateList
        restCandidateMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(candidate.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].photoContentType").value(hasItem(DEFAULT_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_PHOTO))))
            .andExpect(jsonPath("$.[*].profession").value(hasItem(DEFAULT_PROFESSION)))
            .andExpect(jsonPath("$.[*].nbExperience").value(hasItem(DEFAULT_NB_EXPERIENCE)))
            .andExpect(jsonPath("$.[*].personalEmail").value(hasItem(DEFAULT_PERSONAL_EMAIL)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCandidatesWithEagerRelationshipsIsEnabled() throws Exception {
        when(candidateServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCandidateMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(candidateServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCandidatesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(candidateServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCandidateMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(candidateRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getCandidate() throws Exception {
        // Initialize the database
        candidateRepository.saveAndFlush(candidate);

        // Get the candidate
        restCandidateMockMvc
            .perform(get(ENTITY_API_URL_ID, candidate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(candidate.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.photoContentType").value(DEFAULT_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.photo").value(Base64.getEncoder().encodeToString(DEFAULT_PHOTO)))
            .andExpect(jsonPath("$.profession").value(DEFAULT_PROFESSION))
            .andExpect(jsonPath("$.nbExperience").value(DEFAULT_NB_EXPERIENCE))
            .andExpect(jsonPath("$.personalEmail").value(DEFAULT_PERSONAL_EMAIL));
    }

    @Test
    @Transactional
    void getCandidatesByIdFiltering() throws Exception {
        // Initialize the database
        candidateRepository.saveAndFlush(candidate);

        Long id = candidate.getId();

        defaultCandidateShouldBeFound("id.equals=" + id);
        defaultCandidateShouldNotBeFound("id.notEquals=" + id);

        defaultCandidateShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCandidateShouldNotBeFound("id.greaterThan=" + id);

        defaultCandidateShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCandidateShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCandidatesByFirstNameIsEqualToSomething() throws Exception {
        // Initialize the database
        candidateRepository.saveAndFlush(candidate);

        // Get all the candidateList where firstName equals to DEFAULT_FIRST_NAME
        defaultCandidateShouldBeFound("firstName.equals=" + DEFAULT_FIRST_NAME);

        // Get all the candidateList where firstName equals to UPDATED_FIRST_NAME
        defaultCandidateShouldNotBeFound("firstName.equals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllCandidatesByFirstNameIsInShouldWork() throws Exception {
        // Initialize the database
        candidateRepository.saveAndFlush(candidate);

        // Get all the candidateList where firstName in DEFAULT_FIRST_NAME or UPDATED_FIRST_NAME
        defaultCandidateShouldBeFound("firstName.in=" + DEFAULT_FIRST_NAME + "," + UPDATED_FIRST_NAME);

        // Get all the candidateList where firstName equals to UPDATED_FIRST_NAME
        defaultCandidateShouldNotBeFound("firstName.in=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllCandidatesByFirstNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        candidateRepository.saveAndFlush(candidate);

        // Get all the candidateList where firstName is not null
        defaultCandidateShouldBeFound("firstName.specified=true");

        // Get all the candidateList where firstName is null
        defaultCandidateShouldNotBeFound("firstName.specified=false");
    }

    @Test
    @Transactional
    void getAllCandidatesByFirstNameContainsSomething() throws Exception {
        // Initialize the database
        candidateRepository.saveAndFlush(candidate);

        // Get all the candidateList where firstName contains DEFAULT_FIRST_NAME
        defaultCandidateShouldBeFound("firstName.contains=" + DEFAULT_FIRST_NAME);

        // Get all the candidateList where firstName contains UPDATED_FIRST_NAME
        defaultCandidateShouldNotBeFound("firstName.contains=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllCandidatesByFirstNameNotContainsSomething() throws Exception {
        // Initialize the database
        candidateRepository.saveAndFlush(candidate);

        // Get all the candidateList where firstName does not contain DEFAULT_FIRST_NAME
        defaultCandidateShouldNotBeFound("firstName.doesNotContain=" + DEFAULT_FIRST_NAME);

        // Get all the candidateList where firstName does not contain UPDATED_FIRST_NAME
        defaultCandidateShouldBeFound("firstName.doesNotContain=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllCandidatesByLastNameIsEqualToSomething() throws Exception {
        // Initialize the database
        candidateRepository.saveAndFlush(candidate);

        // Get all the candidateList where lastName equals to DEFAULT_LAST_NAME
        defaultCandidateShouldBeFound("lastName.equals=" + DEFAULT_LAST_NAME);

        // Get all the candidateList where lastName equals to UPDATED_LAST_NAME
        defaultCandidateShouldNotBeFound("lastName.equals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllCandidatesByLastNameIsInShouldWork() throws Exception {
        // Initialize the database
        candidateRepository.saveAndFlush(candidate);

        // Get all the candidateList where lastName in DEFAULT_LAST_NAME or UPDATED_LAST_NAME
        defaultCandidateShouldBeFound("lastName.in=" + DEFAULT_LAST_NAME + "," + UPDATED_LAST_NAME);

        // Get all the candidateList where lastName equals to UPDATED_LAST_NAME
        defaultCandidateShouldNotBeFound("lastName.in=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllCandidatesByLastNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        candidateRepository.saveAndFlush(candidate);

        // Get all the candidateList where lastName is not null
        defaultCandidateShouldBeFound("lastName.specified=true");

        // Get all the candidateList where lastName is null
        defaultCandidateShouldNotBeFound("lastName.specified=false");
    }

    @Test
    @Transactional
    void getAllCandidatesByLastNameContainsSomething() throws Exception {
        // Initialize the database
        candidateRepository.saveAndFlush(candidate);

        // Get all the candidateList where lastName contains DEFAULT_LAST_NAME
        defaultCandidateShouldBeFound("lastName.contains=" + DEFAULT_LAST_NAME);

        // Get all the candidateList where lastName contains UPDATED_LAST_NAME
        defaultCandidateShouldNotBeFound("lastName.contains=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllCandidatesByLastNameNotContainsSomething() throws Exception {
        // Initialize the database
        candidateRepository.saveAndFlush(candidate);

        // Get all the candidateList where lastName does not contain DEFAULT_LAST_NAME
        defaultCandidateShouldNotBeFound("lastName.doesNotContain=" + DEFAULT_LAST_NAME);

        // Get all the candidateList where lastName does not contain UPDATED_LAST_NAME
        defaultCandidateShouldBeFound("lastName.doesNotContain=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllCandidatesByProfessionIsEqualToSomething() throws Exception {
        // Initialize the database
        candidateRepository.saveAndFlush(candidate);

        // Get all the candidateList where profession equals to DEFAULT_PROFESSION
        defaultCandidateShouldBeFound("profession.equals=" + DEFAULT_PROFESSION);

        // Get all the candidateList where profession equals to UPDATED_PROFESSION
        defaultCandidateShouldNotBeFound("profession.equals=" + UPDATED_PROFESSION);
    }

    @Test
    @Transactional
    void getAllCandidatesByProfessionIsInShouldWork() throws Exception {
        // Initialize the database
        candidateRepository.saveAndFlush(candidate);

        // Get all the candidateList where profession in DEFAULT_PROFESSION or UPDATED_PROFESSION
        defaultCandidateShouldBeFound("profession.in=" + DEFAULT_PROFESSION + "," + UPDATED_PROFESSION);

        // Get all the candidateList where profession equals to UPDATED_PROFESSION
        defaultCandidateShouldNotBeFound("profession.in=" + UPDATED_PROFESSION);
    }

    @Test
    @Transactional
    void getAllCandidatesByProfessionIsNullOrNotNull() throws Exception {
        // Initialize the database
        candidateRepository.saveAndFlush(candidate);

        // Get all the candidateList where profession is not null
        defaultCandidateShouldBeFound("profession.specified=true");

        // Get all the candidateList where profession is null
        defaultCandidateShouldNotBeFound("profession.specified=false");
    }

    @Test
    @Transactional
    void getAllCandidatesByProfessionContainsSomething() throws Exception {
        // Initialize the database
        candidateRepository.saveAndFlush(candidate);

        // Get all the candidateList where profession contains DEFAULT_PROFESSION
        defaultCandidateShouldBeFound("profession.contains=" + DEFAULT_PROFESSION);

        // Get all the candidateList where profession contains UPDATED_PROFESSION
        defaultCandidateShouldNotBeFound("profession.contains=" + UPDATED_PROFESSION);
    }

    @Test
    @Transactional
    void getAllCandidatesByProfessionNotContainsSomething() throws Exception {
        // Initialize the database
        candidateRepository.saveAndFlush(candidate);

        // Get all the candidateList where profession does not contain DEFAULT_PROFESSION
        defaultCandidateShouldNotBeFound("profession.doesNotContain=" + DEFAULT_PROFESSION);

        // Get all the candidateList where profession does not contain UPDATED_PROFESSION
        defaultCandidateShouldBeFound("profession.doesNotContain=" + UPDATED_PROFESSION);
    }

    @Test
    @Transactional
    void getAllCandidatesByNbExperienceIsEqualToSomething() throws Exception {
        // Initialize the database
        candidateRepository.saveAndFlush(candidate);

        // Get all the candidateList where nbExperience equals to DEFAULT_NB_EXPERIENCE
        defaultCandidateShouldBeFound("nbExperience.equals=" + DEFAULT_NB_EXPERIENCE);

        // Get all the candidateList where nbExperience equals to UPDATED_NB_EXPERIENCE
        defaultCandidateShouldNotBeFound("nbExperience.equals=" + UPDATED_NB_EXPERIENCE);
    }

    @Test
    @Transactional
    void getAllCandidatesByNbExperienceIsInShouldWork() throws Exception {
        // Initialize the database
        candidateRepository.saveAndFlush(candidate);

        // Get all the candidateList where nbExperience in DEFAULT_NB_EXPERIENCE or UPDATED_NB_EXPERIENCE
        defaultCandidateShouldBeFound("nbExperience.in=" + DEFAULT_NB_EXPERIENCE + "," + UPDATED_NB_EXPERIENCE);

        // Get all the candidateList where nbExperience equals to UPDATED_NB_EXPERIENCE
        defaultCandidateShouldNotBeFound("nbExperience.in=" + UPDATED_NB_EXPERIENCE);
    }

    @Test
    @Transactional
    void getAllCandidatesByNbExperienceIsNullOrNotNull() throws Exception {
        // Initialize the database
        candidateRepository.saveAndFlush(candidate);

        // Get all the candidateList where nbExperience is not null
        defaultCandidateShouldBeFound("nbExperience.specified=true");

        // Get all the candidateList where nbExperience is null
        defaultCandidateShouldNotBeFound("nbExperience.specified=false");
    }

    @Test
    @Transactional
    void getAllCandidatesByNbExperienceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        candidateRepository.saveAndFlush(candidate);

        // Get all the candidateList where nbExperience is greater than or equal to DEFAULT_NB_EXPERIENCE
        defaultCandidateShouldBeFound("nbExperience.greaterThanOrEqual=" + DEFAULT_NB_EXPERIENCE);

        // Get all the candidateList where nbExperience is greater than or equal to UPDATED_NB_EXPERIENCE
        defaultCandidateShouldNotBeFound("nbExperience.greaterThanOrEqual=" + UPDATED_NB_EXPERIENCE);
    }

    @Test
    @Transactional
    void getAllCandidatesByNbExperienceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        candidateRepository.saveAndFlush(candidate);

        // Get all the candidateList where nbExperience is less than or equal to DEFAULT_NB_EXPERIENCE
        defaultCandidateShouldBeFound("nbExperience.lessThanOrEqual=" + DEFAULT_NB_EXPERIENCE);

        // Get all the candidateList where nbExperience is less than or equal to SMALLER_NB_EXPERIENCE
        defaultCandidateShouldNotBeFound("nbExperience.lessThanOrEqual=" + SMALLER_NB_EXPERIENCE);
    }

    @Test
    @Transactional
    void getAllCandidatesByNbExperienceIsLessThanSomething() throws Exception {
        // Initialize the database
        candidateRepository.saveAndFlush(candidate);

        // Get all the candidateList where nbExperience is less than DEFAULT_NB_EXPERIENCE
        defaultCandidateShouldNotBeFound("nbExperience.lessThan=" + DEFAULT_NB_EXPERIENCE);

        // Get all the candidateList where nbExperience is less than UPDATED_NB_EXPERIENCE
        defaultCandidateShouldBeFound("nbExperience.lessThan=" + UPDATED_NB_EXPERIENCE);
    }

    @Test
    @Transactional
    void getAllCandidatesByNbExperienceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        candidateRepository.saveAndFlush(candidate);

        // Get all the candidateList where nbExperience is greater than DEFAULT_NB_EXPERIENCE
        defaultCandidateShouldNotBeFound("nbExperience.greaterThan=" + DEFAULT_NB_EXPERIENCE);

        // Get all the candidateList where nbExperience is greater than SMALLER_NB_EXPERIENCE
        defaultCandidateShouldBeFound("nbExperience.greaterThan=" + SMALLER_NB_EXPERIENCE);
    }

    @Test
    @Transactional
    void getAllCandidatesByPersonalEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        candidateRepository.saveAndFlush(candidate);

        // Get all the candidateList where personalEmail equals to DEFAULT_PERSONAL_EMAIL
        defaultCandidateShouldBeFound("personalEmail.equals=" + DEFAULT_PERSONAL_EMAIL);

        // Get all the candidateList where personalEmail equals to UPDATED_PERSONAL_EMAIL
        defaultCandidateShouldNotBeFound("personalEmail.equals=" + UPDATED_PERSONAL_EMAIL);
    }

    @Test
    @Transactional
    void getAllCandidatesByPersonalEmailIsInShouldWork() throws Exception {
        // Initialize the database
        candidateRepository.saveAndFlush(candidate);

        // Get all the candidateList where personalEmail in DEFAULT_PERSONAL_EMAIL or UPDATED_PERSONAL_EMAIL
        defaultCandidateShouldBeFound("personalEmail.in=" + DEFAULT_PERSONAL_EMAIL + "," + UPDATED_PERSONAL_EMAIL);

        // Get all the candidateList where personalEmail equals to UPDATED_PERSONAL_EMAIL
        defaultCandidateShouldNotBeFound("personalEmail.in=" + UPDATED_PERSONAL_EMAIL);
    }

    @Test
    @Transactional
    void getAllCandidatesByPersonalEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        candidateRepository.saveAndFlush(candidate);

        // Get all the candidateList where personalEmail is not null
        defaultCandidateShouldBeFound("personalEmail.specified=true");

        // Get all the candidateList where personalEmail is null
        defaultCandidateShouldNotBeFound("personalEmail.specified=false");
    }

    @Test
    @Transactional
    void getAllCandidatesByPersonalEmailContainsSomething() throws Exception {
        // Initialize the database
        candidateRepository.saveAndFlush(candidate);

        // Get all the candidateList where personalEmail contains DEFAULT_PERSONAL_EMAIL
        defaultCandidateShouldBeFound("personalEmail.contains=" + DEFAULT_PERSONAL_EMAIL);

        // Get all the candidateList where personalEmail contains UPDATED_PERSONAL_EMAIL
        defaultCandidateShouldNotBeFound("personalEmail.contains=" + UPDATED_PERSONAL_EMAIL);
    }

    @Test
    @Transactional
    void getAllCandidatesByPersonalEmailNotContainsSomething() throws Exception {
        // Initialize the database
        candidateRepository.saveAndFlush(candidate);

        // Get all the candidateList where personalEmail does not contain DEFAULT_PERSONAL_EMAIL
        defaultCandidateShouldNotBeFound("personalEmail.doesNotContain=" + DEFAULT_PERSONAL_EMAIL);

        // Get all the candidateList where personalEmail does not contain UPDATED_PERSONAL_EMAIL
        defaultCandidateShouldBeFound("personalEmail.doesNotContain=" + UPDATED_PERSONAL_EMAIL);
    }

    @Test
    @Transactional
    void getAllCandidatesByAdditionalInfosIsEqualToSomething() throws Exception {
        CandidateAdditionalInfos additionalInfos;
        if (TestUtil.findAll(em, CandidateAdditionalInfos.class).isEmpty()) {
            candidateRepository.saveAndFlush(candidate);
            additionalInfos = CandidateAdditionalInfosResourceIT.createEntity(em);
        } else {
            additionalInfos = TestUtil.findAll(em, CandidateAdditionalInfos.class).get(0);
        }
        em.persist(additionalInfos);
        em.flush();
        candidate.setAdditionalInfos(additionalInfos);
        candidateRepository.saveAndFlush(candidate);
        Long additionalInfosId = additionalInfos.getId();
        // Get all the candidateList where additionalInfos equals to additionalInfosId
        defaultCandidateShouldBeFound("additionalInfosId.equals=" + additionalInfosId);

        // Get all the candidateList where additionalInfos equals to (additionalInfosId + 1)
        defaultCandidateShouldNotBeFound("additionalInfosId.equals=" + (additionalInfosId + 1));
    }

    @Test
    @Transactional
    void getAllCandidatesByExperiencesIsEqualToSomething() throws Exception {
        Experience experiences;
        if (TestUtil.findAll(em, Experience.class).isEmpty()) {
            candidateRepository.saveAndFlush(candidate);
            experiences = ExperienceResourceIT.createEntity(em);
        } else {
            experiences = TestUtil.findAll(em, Experience.class).get(0);
        }
        em.persist(experiences);
        em.flush();
        candidate.addExperiences(experiences);
        candidateRepository.saveAndFlush(candidate);
        Long experiencesId = experiences.getId();
        // Get all the candidateList where experiences equals to experiencesId
        defaultCandidateShouldBeFound("experiencesId.equals=" + experiencesId);

        // Get all the candidateList where experiences equals to (experiencesId + 1)
        defaultCandidateShouldNotBeFound("experiencesId.equals=" + (experiencesId + 1));
    }

    @Test
    @Transactional
    void getAllCandidatesByEducationsIsEqualToSomething() throws Exception {
        Education educations;
        if (TestUtil.findAll(em, Education.class).isEmpty()) {
            candidateRepository.saveAndFlush(candidate);
            educations = EducationResourceIT.createEntity(em);
        } else {
            educations = TestUtil.findAll(em, Education.class).get(0);
        }
        em.persist(educations);
        em.flush();
        candidate.addEducations(educations);
        candidateRepository.saveAndFlush(candidate);
        Long educationsId = educations.getId();
        // Get all the candidateList where educations equals to educationsId
        defaultCandidateShouldBeFound("educationsId.equals=" + educationsId);

        // Get all the candidateList where educations equals to (educationsId + 1)
        defaultCandidateShouldNotBeFound("educationsId.equals=" + (educationsId + 1));
    }

    @Test
    @Transactional
    void getAllCandidatesByCertificationsIsEqualToSomething() throws Exception {
        Certification certifications;
        if (TestUtil.findAll(em, Certification.class).isEmpty()) {
            candidateRepository.saveAndFlush(candidate);
            certifications = CertificationResourceIT.createEntity(em);
        } else {
            certifications = TestUtil.findAll(em, Certification.class).get(0);
        }
        em.persist(certifications);
        em.flush();
        candidate.addCertifications(certifications);
        candidateRepository.saveAndFlush(candidate);
        Long certificationsId = certifications.getId();
        // Get all the candidateList where certifications equals to certificationsId
        defaultCandidateShouldBeFound("certificationsId.equals=" + certificationsId);

        // Get all the candidateList where certifications equals to (certificationsId + 1)
        defaultCandidateShouldNotBeFound("certificationsId.equals=" + (certificationsId + 1));
    }

    @Test
    @Transactional
    void getAllCandidatesBySkillsIsEqualToSomething() throws Exception {
        Skill skills;
        if (TestUtil.findAll(em, Skill.class).isEmpty()) {
            candidateRepository.saveAndFlush(candidate);
            skills = SkillResourceIT.createEntity(em);
        } else {
            skills = TestUtil.findAll(em, Skill.class).get(0);
        }
        em.persist(skills);
        em.flush();
        candidate.addSkills(skills);
        candidateRepository.saveAndFlush(candidate);
        Long skillsId = skills.getId();
        // Get all the candidateList where skills equals to skillsId
        defaultCandidateShouldBeFound("skillsId.equals=" + skillsId);

        // Get all the candidateList where skills equals to (skillsId + 1)
        defaultCandidateShouldNotBeFound("skillsId.equals=" + (skillsId + 1));
    }

    @Test
    @Transactional
    void getAllCandidatesByLanguagesIsEqualToSomething() throws Exception {
        Language languages;
        if (TestUtil.findAll(em, Language.class).isEmpty()) {
            candidateRepository.saveAndFlush(candidate);
            languages = LanguageResourceIT.createEntity(em);
        } else {
            languages = TestUtil.findAll(em, Language.class).get(0);
        }
        em.persist(languages);
        em.flush();
        candidate.addLanguages(languages);
        candidateRepository.saveAndFlush(candidate);
        Long languagesId = languages.getId();
        // Get all the candidateList where languages equals to languagesId
        defaultCandidateShouldBeFound("languagesId.equals=" + languagesId);

        // Get all the candidateList where languages equals to (languagesId + 1)
        defaultCandidateShouldNotBeFound("languagesId.equals=" + (languagesId + 1));
    }

    @Test
    @Transactional
    void getAllCandidatesByNotificationsIsEqualToSomething() throws Exception {
        Notification notifications;
        if (TestUtil.findAll(em, Notification.class).isEmpty()) {
            candidateRepository.saveAndFlush(candidate);
            notifications = NotificationResourceIT.createEntity(em);
        } else {
            notifications = TestUtil.findAll(em, Notification.class).get(0);
        }
        em.persist(notifications);
        em.flush();
        candidate.addNotifications(notifications);
        candidateRepository.saveAndFlush(candidate);
        Long notificationsId = notifications.getId();
        // Get all the candidateList where notifications equals to notificationsId
        defaultCandidateShouldBeFound("notificationsId.equals=" + notificationsId);

        // Get all the candidateList where notifications equals to (notificationsId + 1)
        defaultCandidateShouldNotBeFound("notificationsId.equals=" + (notificationsId + 1));
    }

    @Test
    @Transactional
    void getAllCandidatesByNotesIsEqualToSomething() throws Exception {
        Note notes;
        if (TestUtil.findAll(em, Note.class).isEmpty()) {
            candidateRepository.saveAndFlush(candidate);
            notes = NoteResourceIT.createEntity(em);
        } else {
            notes = TestUtil.findAll(em, Note.class).get(0);
        }
        em.persist(notes);
        em.flush();
        candidate.addNotes(notes);
        candidateRepository.saveAndFlush(candidate);
        Long notesId = notes.getId();
        // Get all the candidateList where notes equals to notesId
        defaultCandidateShouldBeFound("notesId.equals=" + notesId);

        // Get all the candidateList where notes equals to (notesId + 1)
        defaultCandidateShouldNotBeFound("notesId.equals=" + (notesId + 1));
    }

    @Test
    @Transactional
    void getAllCandidatesByEmailsIsEqualToSomething() throws Exception {
        Email emails;
        if (TestUtil.findAll(em, Email.class).isEmpty()) {
            candidateRepository.saveAndFlush(candidate);
            emails = EmailResourceIT.createEntity(em);
        } else {
            emails = TestUtil.findAll(em, Email.class).get(0);
        }
        em.persist(emails);
        em.flush();
        candidate.addEmails(emails);
        candidateRepository.saveAndFlush(candidate);
        Long emailsId = emails.getId();
        // Get all the candidateList where emails equals to emailsId
        defaultCandidateShouldBeFound("emailsId.equals=" + emailsId);

        // Get all the candidateList where emails equals to (emailsId + 1)
        defaultCandidateShouldNotBeFound("emailsId.equals=" + (emailsId + 1));
    }

    @Test
    @Transactional
    void getAllCandidatesByCandidateCategoryIsEqualToSomething() throws Exception {
        Category candidateCategory;
        if (TestUtil.findAll(em, Category.class).isEmpty()) {
            candidateRepository.saveAndFlush(candidate);
            candidateCategory = CategoryResourceIT.createEntity(em);
        } else {
            candidateCategory = TestUtil.findAll(em, Category.class).get(0);
        }
        em.persist(candidateCategory);
        em.flush();
        candidate.setCandidateCategory(candidateCategory);
        candidateRepository.saveAndFlush(candidate);
        Long candidateCategoryId = candidateCategory.getId();
        // Get all the candidateList where candidateCategory equals to candidateCategoryId
        defaultCandidateShouldBeFound("candidateCategoryId.equals=" + candidateCategoryId);

        // Get all the candidateList where candidateCategory equals to (candidateCategoryId + 1)
        defaultCandidateShouldNotBeFound("candidateCategoryId.equals=" + (candidateCategoryId + 1));
    }

    @Test
    @Transactional
    void getAllCandidatesByCandidateSubCategoryIsEqualToSomething() throws Exception {
        SubCategory candidateSubCategory;
        if (TestUtil.findAll(em, SubCategory.class).isEmpty()) {
            candidateRepository.saveAndFlush(candidate);
            candidateSubCategory = SubCategoryResourceIT.createEntity(em);
        } else {
            candidateSubCategory = TestUtil.findAll(em, SubCategory.class).get(0);
        }
        em.persist(candidateSubCategory);
        em.flush();
        candidate.setCandidateSubCategory(candidateSubCategory);
        candidateRepository.saveAndFlush(candidate);
        Long candidateSubCategoryId = candidateSubCategory.getId();
        // Get all the candidateList where candidateSubCategory equals to candidateSubCategoryId
        defaultCandidateShouldBeFound("candidateSubCategoryId.equals=" + candidateSubCategoryId);

        // Get all the candidateList where candidateSubCategory equals to (candidateSubCategoryId + 1)
        defaultCandidateShouldNotBeFound("candidateSubCategoryId.equals=" + (candidateSubCategoryId + 1));
    }

    @Test
    @Transactional
    void getAllCandidatesByCandidateProcessIsEqualToSomething() throws Exception {
        Process candidateProcess;
        if (TestUtil.findAll(em, Process.class).isEmpty()) {
            candidateRepository.saveAndFlush(candidate);
            candidateProcess = ProcessResourceIT.createEntity(em);
        } else {
            candidateProcess = TestUtil.findAll(em, Process.class).get(0);
        }
        em.persist(candidateProcess);
        em.flush();
        candidate.setCandidateProcess(candidateProcess);
        candidateRepository.saveAndFlush(candidate);
        Long candidateProcessId = candidateProcess.getId();
        // Get all the candidateList where candidateProcess equals to candidateProcessId
        defaultCandidateShouldBeFound("candidateProcessId.equals=" + candidateProcessId);

        // Get all the candidateList where candidateProcess equals to (candidateProcessId + 1)
        defaultCandidateShouldNotBeFound("candidateProcessId.equals=" + (candidateProcessId + 1));
    }

    @Test
    @Transactional
    void getAllCandidatesByCandidateProcessStepIsEqualToSomething() throws Exception {
        ProcessStep candidateProcessStep;
        if (TestUtil.findAll(em, ProcessStep.class).isEmpty()) {
            candidateRepository.saveAndFlush(candidate);
            candidateProcessStep = ProcessStepResourceIT.createEntity(em);
        } else {
            candidateProcessStep = TestUtil.findAll(em, ProcessStep.class).get(0);
        }
        em.persist(candidateProcessStep);
        em.flush();
        candidate.setCandidateProcessStep(candidateProcessStep);
        candidateRepository.saveAndFlush(candidate);
        Long candidateProcessStepId = candidateProcessStep.getId();
        // Get all the candidateList where candidateProcessStep equals to candidateProcessStepId
        defaultCandidateShouldBeFound("candidateProcessStepId.equals=" + candidateProcessStepId);

        // Get all the candidateList where candidateProcessStep equals to (candidateProcessStepId + 1)
        defaultCandidateShouldNotBeFound("candidateProcessStepId.equals=" + (candidateProcessStepId + 1));
    }

    @Test
    @Transactional
    void getAllCandidatesByTagsIsEqualToSomething() throws Exception {
        Tag tags;
        if (TestUtil.findAll(em, Tag.class).isEmpty()) {
            candidateRepository.saveAndFlush(candidate);
            tags = TagResourceIT.createEntity(em);
        } else {
            tags = TestUtil.findAll(em, Tag.class).get(0);
        }
        em.persist(tags);
        em.flush();
        candidate.addTags(tags);
        candidateRepository.saveAndFlush(candidate);
        Long tagsId = tags.getId();
        // Get all the candidateList where tags equals to tagsId
        defaultCandidateShouldBeFound("tagsId.equals=" + tagsId);

        // Get all the candidateList where tags equals to (tagsId + 1)
        defaultCandidateShouldNotBeFound("tagsId.equals=" + (tagsId + 1));
    }

    @Test
    @Transactional
    void getAllCandidatesByCategoryIsEqualToSomething() throws Exception {
        Category category;
        if (TestUtil.findAll(em, Category.class).isEmpty()) {
            candidateRepository.saveAndFlush(candidate);
            category = CategoryResourceIT.createEntity(em);
        } else {
            category = TestUtil.findAll(em, Category.class).get(0);
        }
        em.persist(category);
        em.flush();
        candidate.setCategory(category);
        candidateRepository.saveAndFlush(candidate);
        Long categoryId = category.getId();
        // Get all the candidateList where category equals to categoryId
        defaultCandidateShouldBeFound("categoryId.equals=" + categoryId);

        // Get all the candidateList where category equals to (categoryId + 1)
        defaultCandidateShouldNotBeFound("categoryId.equals=" + (categoryId + 1));
    }

    @Test
    @Transactional
    void getAllCandidatesBySubCategoryIsEqualToSomething() throws Exception {
        SubCategory subCategory;
        if (TestUtil.findAll(em, SubCategory.class).isEmpty()) {
            candidateRepository.saveAndFlush(candidate);
            subCategory = SubCategoryResourceIT.createEntity(em);
        } else {
            subCategory = TestUtil.findAll(em, SubCategory.class).get(0);
        }
        em.persist(subCategory);
        em.flush();
        candidate.setSubCategory(subCategory);
        candidateRepository.saveAndFlush(candidate);
        Long subCategoryId = subCategory.getId();
        // Get all the candidateList where subCategory equals to subCategoryId
        defaultCandidateShouldBeFound("subCategoryId.equals=" + subCategoryId);

        // Get all the candidateList where subCategory equals to (subCategoryId + 1)
        defaultCandidateShouldNotBeFound("subCategoryId.equals=" + (subCategoryId + 1));
    }

    @Test
    @Transactional
    void getAllCandidatesByProcessIsEqualToSomething() throws Exception {
        Process process;
        if (TestUtil.findAll(em, Process.class).isEmpty()) {
            candidateRepository.saveAndFlush(candidate);
            process = ProcessResourceIT.createEntity(em);
        } else {
            process = TestUtil.findAll(em, Process.class).get(0);
        }
        em.persist(process);
        em.flush();
        candidate.setProcess(process);
        candidateRepository.saveAndFlush(candidate);
        Long processId = process.getId();
        // Get all the candidateList where process equals to processId
        defaultCandidateShouldBeFound("processId.equals=" + processId);

        // Get all the candidateList where process equals to (processId + 1)
        defaultCandidateShouldNotBeFound("processId.equals=" + (processId + 1));
    }

    @Test
    @Transactional
    void getAllCandidatesByProcessStepIsEqualToSomething() throws Exception {
        ProcessStep processStep;
        if (TestUtil.findAll(em, ProcessStep.class).isEmpty()) {
            candidateRepository.saveAndFlush(candidate);
            processStep = ProcessStepResourceIT.createEntity(em);
        } else {
            processStep = TestUtil.findAll(em, ProcessStep.class).get(0);
        }
        em.persist(processStep);
        em.flush();
        candidate.setProcessStep(processStep);
        candidateRepository.saveAndFlush(candidate);
        Long processStepId = processStep.getId();
        // Get all the candidateList where processStep equals to processStepId
        defaultCandidateShouldBeFound("processStepId.equals=" + processStepId);

        // Get all the candidateList where processStep equals to (processStepId + 1)
        defaultCandidateShouldNotBeFound("processStepId.equals=" + (processStepId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCandidateShouldBeFound(String filter) throws Exception {
        restCandidateMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(candidate.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].photoContentType").value(hasItem(DEFAULT_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_PHOTO))))
            .andExpect(jsonPath("$.[*].profession").value(hasItem(DEFAULT_PROFESSION)))
            .andExpect(jsonPath("$.[*].nbExperience").value(hasItem(DEFAULT_NB_EXPERIENCE)))
            .andExpect(jsonPath("$.[*].personalEmail").value(hasItem(DEFAULT_PERSONAL_EMAIL)));

        // Check, that the count call also returns 1
        restCandidateMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCandidateShouldNotBeFound(String filter) throws Exception {
        restCandidateMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCandidateMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCandidate() throws Exception {
        // Get the candidate
        restCandidateMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCandidate() throws Exception {
        // Initialize the database
        candidateRepository.saveAndFlush(candidate);

        int databaseSizeBeforeUpdate = candidateRepository.findAll().size();

        // Update the candidate
        Candidate updatedCandidate = candidateRepository.findById(candidate.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCandidate are not directly saved in db
        em.detach(updatedCandidate);
        updatedCandidate
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE)
            .profession(UPDATED_PROFESSION)
            .nbExperience(UPDATED_NB_EXPERIENCE)
            .personalEmail(UPDATED_PERSONAL_EMAIL);
        CandidateDTO candidateDTO = candidateMapper.toDto(updatedCandidate);

        restCandidateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, candidateDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(candidateDTO))
            )
            .andExpect(status().isOk());

        // Validate the Candidate in the database
        List<Candidate> candidateList = candidateRepository.findAll();
        assertThat(candidateList).hasSize(databaseSizeBeforeUpdate);
        Candidate testCandidate = candidateList.get(candidateList.size() - 1);
        assertThat(testCandidate.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testCandidate.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testCandidate.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testCandidate.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
        assertThat(testCandidate.getProfession()).isEqualTo(UPDATED_PROFESSION);
        assertThat(testCandidate.getNbExperience()).isEqualTo(UPDATED_NB_EXPERIENCE);
        assertThat(testCandidate.getPersonalEmail()).isEqualTo(UPDATED_PERSONAL_EMAIL);
    }

    @Test
    @Transactional
    void putNonExistingCandidate() throws Exception {
        int databaseSizeBeforeUpdate = candidateRepository.findAll().size();
        candidate.setId(longCount.incrementAndGet());

        // Create the Candidate
        CandidateDTO candidateDTO = candidateMapper.toDto(candidate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCandidateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, candidateDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(candidateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Candidate in the database
        List<Candidate> candidateList = candidateRepository.findAll();
        assertThat(candidateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCandidate() throws Exception {
        int databaseSizeBeforeUpdate = candidateRepository.findAll().size();
        candidate.setId(longCount.incrementAndGet());

        // Create the Candidate
        CandidateDTO candidateDTO = candidateMapper.toDto(candidate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCandidateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(candidateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Candidate in the database
        List<Candidate> candidateList = candidateRepository.findAll();
        assertThat(candidateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCandidate() throws Exception {
        int databaseSizeBeforeUpdate = candidateRepository.findAll().size();
        candidate.setId(longCount.incrementAndGet());

        // Create the Candidate
        CandidateDTO candidateDTO = candidateMapper.toDto(candidate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCandidateMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(candidateDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Candidate in the database
        List<Candidate> candidateList = candidateRepository.findAll();
        assertThat(candidateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCandidateWithPatch() throws Exception {
        // Initialize the database
        candidateRepository.saveAndFlush(candidate);

        int databaseSizeBeforeUpdate = candidateRepository.findAll().size();

        // Update the candidate using partial update
        Candidate partialUpdatedCandidate = new Candidate();
        partialUpdatedCandidate.setId(candidate.getId());

        partialUpdatedCandidate.firstName(UPDATED_FIRST_NAME).nbExperience(UPDATED_NB_EXPERIENCE).personalEmail(UPDATED_PERSONAL_EMAIL);

        restCandidateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCandidate.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCandidate))
            )
            .andExpect(status().isOk());

        // Validate the Candidate in the database
        List<Candidate> candidateList = candidateRepository.findAll();
        assertThat(candidateList).hasSize(databaseSizeBeforeUpdate);
        Candidate testCandidate = candidateList.get(candidateList.size() - 1);
        assertThat(testCandidate.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testCandidate.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testCandidate.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testCandidate.getPhotoContentType()).isEqualTo(DEFAULT_PHOTO_CONTENT_TYPE);
        assertThat(testCandidate.getProfession()).isEqualTo(DEFAULT_PROFESSION);
        assertThat(testCandidate.getNbExperience()).isEqualTo(UPDATED_NB_EXPERIENCE);
        assertThat(testCandidate.getPersonalEmail()).isEqualTo(UPDATED_PERSONAL_EMAIL);
    }

    @Test
    @Transactional
    void fullUpdateCandidateWithPatch() throws Exception {
        // Initialize the database
        candidateRepository.saveAndFlush(candidate);

        int databaseSizeBeforeUpdate = candidateRepository.findAll().size();

        // Update the candidate using partial update
        Candidate partialUpdatedCandidate = new Candidate();
        partialUpdatedCandidate.setId(candidate.getId());

        partialUpdatedCandidate
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE)
            .profession(UPDATED_PROFESSION)
            .nbExperience(UPDATED_NB_EXPERIENCE)
            .personalEmail(UPDATED_PERSONAL_EMAIL);

        restCandidateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCandidate.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCandidate))
            )
            .andExpect(status().isOk());

        // Validate the Candidate in the database
        List<Candidate> candidateList = candidateRepository.findAll();
        assertThat(candidateList).hasSize(databaseSizeBeforeUpdate);
        Candidate testCandidate = candidateList.get(candidateList.size() - 1);
        assertThat(testCandidate.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testCandidate.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testCandidate.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testCandidate.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
        assertThat(testCandidate.getProfession()).isEqualTo(UPDATED_PROFESSION);
        assertThat(testCandidate.getNbExperience()).isEqualTo(UPDATED_NB_EXPERIENCE);
        assertThat(testCandidate.getPersonalEmail()).isEqualTo(UPDATED_PERSONAL_EMAIL);
    }

    @Test
    @Transactional
    void patchNonExistingCandidate() throws Exception {
        int databaseSizeBeforeUpdate = candidateRepository.findAll().size();
        candidate.setId(longCount.incrementAndGet());

        // Create the Candidate
        CandidateDTO candidateDTO = candidateMapper.toDto(candidate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCandidateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, candidateDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(candidateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Candidate in the database
        List<Candidate> candidateList = candidateRepository.findAll();
        assertThat(candidateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCandidate() throws Exception {
        int databaseSizeBeforeUpdate = candidateRepository.findAll().size();
        candidate.setId(longCount.incrementAndGet());

        // Create the Candidate
        CandidateDTO candidateDTO = candidateMapper.toDto(candidate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCandidateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(candidateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Candidate in the database
        List<Candidate> candidateList = candidateRepository.findAll();
        assertThat(candidateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCandidate() throws Exception {
        int databaseSizeBeforeUpdate = candidateRepository.findAll().size();
        candidate.setId(longCount.incrementAndGet());

        // Create the Candidate
        CandidateDTO candidateDTO = candidateMapper.toDto(candidate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCandidateMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(candidateDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Candidate in the database
        List<Candidate> candidateList = candidateRepository.findAll();
        assertThat(candidateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCandidate() throws Exception {
        // Initialize the database
        candidateRepository.saveAndFlush(candidate);

        int databaseSizeBeforeDelete = candidateRepository.findAll().size();

        // Delete the candidate
        restCandidateMockMvc
            .perform(delete(ENTITY_API_URL_ID, candidate.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Candidate> candidateList = candidateRepository.findAll();
        assertThat(candidateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
