package atig.recruiting.beta.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import atig.recruiting.beta.IntegrationTest;
import atig.recruiting.beta.domain.Candidate;
import atig.recruiting.beta.domain.CandidateAdditionalInfos;
import atig.recruiting.beta.domain.ObjectContainingFile;
import atig.recruiting.beta.repository.CandidateAdditionalInfosRepository;
import atig.recruiting.beta.service.dto.CandidateAdditionalInfosDTO;
import atig.recruiting.beta.service.mapper.CandidateAdditionalInfosMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link CandidateAdditionalInfosResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CandidateAdditionalInfosResourceIT {

    private static final Instant DEFAULT_BIRTHDAY = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BIRTHDAY = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_ACTUAL_SALARY = 1;
    private static final Integer UPDATED_ACTUAL_SALARY = 2;
    private static final Integer SMALLER_ACTUAL_SALARY = 1 - 1;

    private static final Integer DEFAULT_EXPECTED_SALARY = 1;
    private static final Integer UPDATED_EXPECTED_SALARY = 2;
    private static final Integer SMALLER_EXPECTED_SALARY = 1 - 1;

    private static final Instant DEFAULT_FIRST_CONTACT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FIRST_CONTACT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE = "BBBBBBBBBB";

    private static final String DEFAULT_DISPONIBILITY = "AAAAAAAAAA";
    private static final String UPDATED_DISPONIBILITY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/candidate-additional-infos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CandidateAdditionalInfosRepository candidateAdditionalInfosRepository;

    @Autowired
    private CandidateAdditionalInfosMapper candidateAdditionalInfosMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCandidateAdditionalInfosMockMvc;

    private CandidateAdditionalInfos candidateAdditionalInfos;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CandidateAdditionalInfos createEntity(EntityManager em) {
        CandidateAdditionalInfos candidateAdditionalInfos = new CandidateAdditionalInfos()
            .birthday(DEFAULT_BIRTHDAY)
            .actualSalary(DEFAULT_ACTUAL_SALARY)
            .expectedSalary(DEFAULT_EXPECTED_SALARY)
            .firstContact(DEFAULT_FIRST_CONTACT)
            .location(DEFAULT_LOCATION)
            .mobile(DEFAULT_MOBILE)
            .disponibility(DEFAULT_DISPONIBILITY);
        return candidateAdditionalInfos;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CandidateAdditionalInfos createUpdatedEntity(EntityManager em) {
        CandidateAdditionalInfos candidateAdditionalInfos = new CandidateAdditionalInfos()
            .birthday(UPDATED_BIRTHDAY)
            .actualSalary(UPDATED_ACTUAL_SALARY)
            .expectedSalary(UPDATED_EXPECTED_SALARY)
            .firstContact(UPDATED_FIRST_CONTACT)
            .location(UPDATED_LOCATION)
            .mobile(UPDATED_MOBILE)
            .disponibility(UPDATED_DISPONIBILITY);
        return candidateAdditionalInfos;
    }

    @BeforeEach
    public void initTest() {
        candidateAdditionalInfos = createEntity(em);
    }

    @Test
    @Transactional
    void createCandidateAdditionalInfos() throws Exception {
        int databaseSizeBeforeCreate = candidateAdditionalInfosRepository.findAll().size();
        // Create the CandidateAdditionalInfos
        CandidateAdditionalInfosDTO candidateAdditionalInfosDTO = candidateAdditionalInfosMapper.toDto(candidateAdditionalInfos);
        restCandidateAdditionalInfosMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(candidateAdditionalInfosDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CandidateAdditionalInfos in the database
        List<CandidateAdditionalInfos> candidateAdditionalInfosList = candidateAdditionalInfosRepository.findAll();
        assertThat(candidateAdditionalInfosList).hasSize(databaseSizeBeforeCreate + 1);
        CandidateAdditionalInfos testCandidateAdditionalInfos = candidateAdditionalInfosList.get(candidateAdditionalInfosList.size() - 1);
        assertThat(testCandidateAdditionalInfos.getBirthday()).isEqualTo(DEFAULT_BIRTHDAY);
        assertThat(testCandidateAdditionalInfos.getActualSalary()).isEqualTo(DEFAULT_ACTUAL_SALARY);
        assertThat(testCandidateAdditionalInfos.getExpectedSalary()).isEqualTo(DEFAULT_EXPECTED_SALARY);
        assertThat(testCandidateAdditionalInfos.getFirstContact()).isEqualTo(DEFAULT_FIRST_CONTACT);
        assertThat(testCandidateAdditionalInfos.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testCandidateAdditionalInfos.getMobile()).isEqualTo(DEFAULT_MOBILE);
        assertThat(testCandidateAdditionalInfos.getDisponibility()).isEqualTo(DEFAULT_DISPONIBILITY);
    }

    @Test
    @Transactional
    void createCandidateAdditionalInfosWithExistingId() throws Exception {
        // Create the CandidateAdditionalInfos with an existing ID
        candidateAdditionalInfos.setId(1L);
        CandidateAdditionalInfosDTO candidateAdditionalInfosDTO = candidateAdditionalInfosMapper.toDto(candidateAdditionalInfos);

        int databaseSizeBeforeCreate = candidateAdditionalInfosRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCandidateAdditionalInfosMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(candidateAdditionalInfosDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CandidateAdditionalInfos in the database
        List<CandidateAdditionalInfos> candidateAdditionalInfosList = candidateAdditionalInfosRepository.findAll();
        assertThat(candidateAdditionalInfosList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCandidateAdditionalInfos() throws Exception {
        // Initialize the database
        candidateAdditionalInfosRepository.saveAndFlush(candidateAdditionalInfos);

        // Get all the candidateAdditionalInfosList
        restCandidateAdditionalInfosMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(candidateAdditionalInfos.getId().intValue())))
            .andExpect(jsonPath("$.[*].birthday").value(hasItem(DEFAULT_BIRTHDAY.toString())))
            .andExpect(jsonPath("$.[*].actualSalary").value(hasItem(DEFAULT_ACTUAL_SALARY)))
            .andExpect(jsonPath("$.[*].expectedSalary").value(hasItem(DEFAULT_EXPECTED_SALARY)))
            .andExpect(jsonPath("$.[*].firstContact").value(hasItem(DEFAULT_FIRST_CONTACT.toString())))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)))
            .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE)))
            .andExpect(jsonPath("$.[*].disponibility").value(hasItem(DEFAULT_DISPONIBILITY)));
    }

    @Test
    @Transactional
    void getCandidateAdditionalInfos() throws Exception {
        // Initialize the database
        candidateAdditionalInfosRepository.saveAndFlush(candidateAdditionalInfos);

        // Get the candidateAdditionalInfos
        restCandidateAdditionalInfosMockMvc
            .perform(get(ENTITY_API_URL_ID, candidateAdditionalInfos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(candidateAdditionalInfos.getId().intValue()))
            .andExpect(jsonPath("$.birthday").value(DEFAULT_BIRTHDAY.toString()))
            .andExpect(jsonPath("$.actualSalary").value(DEFAULT_ACTUAL_SALARY))
            .andExpect(jsonPath("$.expectedSalary").value(DEFAULT_EXPECTED_SALARY))
            .andExpect(jsonPath("$.firstContact").value(DEFAULT_FIRST_CONTACT.toString()))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION))
            .andExpect(jsonPath("$.mobile").value(DEFAULT_MOBILE))
            .andExpect(jsonPath("$.disponibility").value(DEFAULT_DISPONIBILITY));
    }

    @Test
    @Transactional
    void getCandidateAdditionalInfosByIdFiltering() throws Exception {
        // Initialize the database
        candidateAdditionalInfosRepository.saveAndFlush(candidateAdditionalInfos);

        Long id = candidateAdditionalInfos.getId();

        defaultCandidateAdditionalInfosShouldBeFound("id.equals=" + id);
        defaultCandidateAdditionalInfosShouldNotBeFound("id.notEquals=" + id);

        defaultCandidateAdditionalInfosShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCandidateAdditionalInfosShouldNotBeFound("id.greaterThan=" + id);

        defaultCandidateAdditionalInfosShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCandidateAdditionalInfosShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCandidateAdditionalInfosByBirthdayIsEqualToSomething() throws Exception {
        // Initialize the database
        candidateAdditionalInfosRepository.saveAndFlush(candidateAdditionalInfos);

        // Get all the candidateAdditionalInfosList where birthday equals to DEFAULT_BIRTHDAY
        defaultCandidateAdditionalInfosShouldBeFound("birthday.equals=" + DEFAULT_BIRTHDAY);

        // Get all the candidateAdditionalInfosList where birthday equals to UPDATED_BIRTHDAY
        defaultCandidateAdditionalInfosShouldNotBeFound("birthday.equals=" + UPDATED_BIRTHDAY);
    }

    @Test
    @Transactional
    void getAllCandidateAdditionalInfosByBirthdayIsInShouldWork() throws Exception {
        // Initialize the database
        candidateAdditionalInfosRepository.saveAndFlush(candidateAdditionalInfos);

        // Get all the candidateAdditionalInfosList where birthday in DEFAULT_BIRTHDAY or UPDATED_BIRTHDAY
        defaultCandidateAdditionalInfosShouldBeFound("birthday.in=" + DEFAULT_BIRTHDAY + "," + UPDATED_BIRTHDAY);

        // Get all the candidateAdditionalInfosList where birthday equals to UPDATED_BIRTHDAY
        defaultCandidateAdditionalInfosShouldNotBeFound("birthday.in=" + UPDATED_BIRTHDAY);
    }

    @Test
    @Transactional
    void getAllCandidateAdditionalInfosByBirthdayIsNullOrNotNull() throws Exception {
        // Initialize the database
        candidateAdditionalInfosRepository.saveAndFlush(candidateAdditionalInfos);

        // Get all the candidateAdditionalInfosList where birthday is not null
        defaultCandidateAdditionalInfosShouldBeFound("birthday.specified=true");

        // Get all the candidateAdditionalInfosList where birthday is null
        defaultCandidateAdditionalInfosShouldNotBeFound("birthday.specified=false");
    }

    @Test
    @Transactional
    void getAllCandidateAdditionalInfosByActualSalaryIsEqualToSomething() throws Exception {
        // Initialize the database
        candidateAdditionalInfosRepository.saveAndFlush(candidateAdditionalInfos);

        // Get all the candidateAdditionalInfosList where actualSalary equals to DEFAULT_ACTUAL_SALARY
        defaultCandidateAdditionalInfosShouldBeFound("actualSalary.equals=" + DEFAULT_ACTUAL_SALARY);

        // Get all the candidateAdditionalInfosList where actualSalary equals to UPDATED_ACTUAL_SALARY
        defaultCandidateAdditionalInfosShouldNotBeFound("actualSalary.equals=" + UPDATED_ACTUAL_SALARY);
    }

    @Test
    @Transactional
    void getAllCandidateAdditionalInfosByActualSalaryIsInShouldWork() throws Exception {
        // Initialize the database
        candidateAdditionalInfosRepository.saveAndFlush(candidateAdditionalInfos);

        // Get all the candidateAdditionalInfosList where actualSalary in DEFAULT_ACTUAL_SALARY or UPDATED_ACTUAL_SALARY
        defaultCandidateAdditionalInfosShouldBeFound("actualSalary.in=" + DEFAULT_ACTUAL_SALARY + "," + UPDATED_ACTUAL_SALARY);

        // Get all the candidateAdditionalInfosList where actualSalary equals to UPDATED_ACTUAL_SALARY
        defaultCandidateAdditionalInfosShouldNotBeFound("actualSalary.in=" + UPDATED_ACTUAL_SALARY);
    }

    @Test
    @Transactional
    void getAllCandidateAdditionalInfosByActualSalaryIsNullOrNotNull() throws Exception {
        // Initialize the database
        candidateAdditionalInfosRepository.saveAndFlush(candidateAdditionalInfos);

        // Get all the candidateAdditionalInfosList where actualSalary is not null
        defaultCandidateAdditionalInfosShouldBeFound("actualSalary.specified=true");

        // Get all the candidateAdditionalInfosList where actualSalary is null
        defaultCandidateAdditionalInfosShouldNotBeFound("actualSalary.specified=false");
    }

    @Test
    @Transactional
    void getAllCandidateAdditionalInfosByActualSalaryIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        candidateAdditionalInfosRepository.saveAndFlush(candidateAdditionalInfos);

        // Get all the candidateAdditionalInfosList where actualSalary is greater than or equal to DEFAULT_ACTUAL_SALARY
        defaultCandidateAdditionalInfosShouldBeFound("actualSalary.greaterThanOrEqual=" + DEFAULT_ACTUAL_SALARY);

        // Get all the candidateAdditionalInfosList where actualSalary is greater than or equal to UPDATED_ACTUAL_SALARY
        defaultCandidateAdditionalInfosShouldNotBeFound("actualSalary.greaterThanOrEqual=" + UPDATED_ACTUAL_SALARY);
    }

    @Test
    @Transactional
    void getAllCandidateAdditionalInfosByActualSalaryIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        candidateAdditionalInfosRepository.saveAndFlush(candidateAdditionalInfos);

        // Get all the candidateAdditionalInfosList where actualSalary is less than or equal to DEFAULT_ACTUAL_SALARY
        defaultCandidateAdditionalInfosShouldBeFound("actualSalary.lessThanOrEqual=" + DEFAULT_ACTUAL_SALARY);

        // Get all the candidateAdditionalInfosList where actualSalary is less than or equal to SMALLER_ACTUAL_SALARY
        defaultCandidateAdditionalInfosShouldNotBeFound("actualSalary.lessThanOrEqual=" + SMALLER_ACTUAL_SALARY);
    }

    @Test
    @Transactional
    void getAllCandidateAdditionalInfosByActualSalaryIsLessThanSomething() throws Exception {
        // Initialize the database
        candidateAdditionalInfosRepository.saveAndFlush(candidateAdditionalInfos);

        // Get all the candidateAdditionalInfosList where actualSalary is less than DEFAULT_ACTUAL_SALARY
        defaultCandidateAdditionalInfosShouldNotBeFound("actualSalary.lessThan=" + DEFAULT_ACTUAL_SALARY);

        // Get all the candidateAdditionalInfosList where actualSalary is less than UPDATED_ACTUAL_SALARY
        defaultCandidateAdditionalInfosShouldBeFound("actualSalary.lessThan=" + UPDATED_ACTUAL_SALARY);
    }

    @Test
    @Transactional
    void getAllCandidateAdditionalInfosByActualSalaryIsGreaterThanSomething() throws Exception {
        // Initialize the database
        candidateAdditionalInfosRepository.saveAndFlush(candidateAdditionalInfos);

        // Get all the candidateAdditionalInfosList where actualSalary is greater than DEFAULT_ACTUAL_SALARY
        defaultCandidateAdditionalInfosShouldNotBeFound("actualSalary.greaterThan=" + DEFAULT_ACTUAL_SALARY);

        // Get all the candidateAdditionalInfosList where actualSalary is greater than SMALLER_ACTUAL_SALARY
        defaultCandidateAdditionalInfosShouldBeFound("actualSalary.greaterThan=" + SMALLER_ACTUAL_SALARY);
    }

    @Test
    @Transactional
    void getAllCandidateAdditionalInfosByExpectedSalaryIsEqualToSomething() throws Exception {
        // Initialize the database
        candidateAdditionalInfosRepository.saveAndFlush(candidateAdditionalInfos);

        // Get all the candidateAdditionalInfosList where expectedSalary equals to DEFAULT_EXPECTED_SALARY
        defaultCandidateAdditionalInfosShouldBeFound("expectedSalary.equals=" + DEFAULT_EXPECTED_SALARY);

        // Get all the candidateAdditionalInfosList where expectedSalary equals to UPDATED_EXPECTED_SALARY
        defaultCandidateAdditionalInfosShouldNotBeFound("expectedSalary.equals=" + UPDATED_EXPECTED_SALARY);
    }

    @Test
    @Transactional
    void getAllCandidateAdditionalInfosByExpectedSalaryIsInShouldWork() throws Exception {
        // Initialize the database
        candidateAdditionalInfosRepository.saveAndFlush(candidateAdditionalInfos);

        // Get all the candidateAdditionalInfosList where expectedSalary in DEFAULT_EXPECTED_SALARY or UPDATED_EXPECTED_SALARY
        defaultCandidateAdditionalInfosShouldBeFound("expectedSalary.in=" + DEFAULT_EXPECTED_SALARY + "," + UPDATED_EXPECTED_SALARY);

        // Get all the candidateAdditionalInfosList where expectedSalary equals to UPDATED_EXPECTED_SALARY
        defaultCandidateAdditionalInfosShouldNotBeFound("expectedSalary.in=" + UPDATED_EXPECTED_SALARY);
    }

    @Test
    @Transactional
    void getAllCandidateAdditionalInfosByExpectedSalaryIsNullOrNotNull() throws Exception {
        // Initialize the database
        candidateAdditionalInfosRepository.saveAndFlush(candidateAdditionalInfos);

        // Get all the candidateAdditionalInfosList where expectedSalary is not null
        defaultCandidateAdditionalInfosShouldBeFound("expectedSalary.specified=true");

        // Get all the candidateAdditionalInfosList where expectedSalary is null
        defaultCandidateAdditionalInfosShouldNotBeFound("expectedSalary.specified=false");
    }

    @Test
    @Transactional
    void getAllCandidateAdditionalInfosByExpectedSalaryIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        candidateAdditionalInfosRepository.saveAndFlush(candidateAdditionalInfos);

        // Get all the candidateAdditionalInfosList where expectedSalary is greater than or equal to DEFAULT_EXPECTED_SALARY
        defaultCandidateAdditionalInfosShouldBeFound("expectedSalary.greaterThanOrEqual=" + DEFAULT_EXPECTED_SALARY);

        // Get all the candidateAdditionalInfosList where expectedSalary is greater than or equal to UPDATED_EXPECTED_SALARY
        defaultCandidateAdditionalInfosShouldNotBeFound("expectedSalary.greaterThanOrEqual=" + UPDATED_EXPECTED_SALARY);
    }

    @Test
    @Transactional
    void getAllCandidateAdditionalInfosByExpectedSalaryIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        candidateAdditionalInfosRepository.saveAndFlush(candidateAdditionalInfos);

        // Get all the candidateAdditionalInfosList where expectedSalary is less than or equal to DEFAULT_EXPECTED_SALARY
        defaultCandidateAdditionalInfosShouldBeFound("expectedSalary.lessThanOrEqual=" + DEFAULT_EXPECTED_SALARY);

        // Get all the candidateAdditionalInfosList where expectedSalary is less than or equal to SMALLER_EXPECTED_SALARY
        defaultCandidateAdditionalInfosShouldNotBeFound("expectedSalary.lessThanOrEqual=" + SMALLER_EXPECTED_SALARY);
    }

    @Test
    @Transactional
    void getAllCandidateAdditionalInfosByExpectedSalaryIsLessThanSomething() throws Exception {
        // Initialize the database
        candidateAdditionalInfosRepository.saveAndFlush(candidateAdditionalInfos);

        // Get all the candidateAdditionalInfosList where expectedSalary is less than DEFAULT_EXPECTED_SALARY
        defaultCandidateAdditionalInfosShouldNotBeFound("expectedSalary.lessThan=" + DEFAULT_EXPECTED_SALARY);

        // Get all the candidateAdditionalInfosList where expectedSalary is less than UPDATED_EXPECTED_SALARY
        defaultCandidateAdditionalInfosShouldBeFound("expectedSalary.lessThan=" + UPDATED_EXPECTED_SALARY);
    }

    @Test
    @Transactional
    void getAllCandidateAdditionalInfosByExpectedSalaryIsGreaterThanSomething() throws Exception {
        // Initialize the database
        candidateAdditionalInfosRepository.saveAndFlush(candidateAdditionalInfos);

        // Get all the candidateAdditionalInfosList where expectedSalary is greater than DEFAULT_EXPECTED_SALARY
        defaultCandidateAdditionalInfosShouldNotBeFound("expectedSalary.greaterThan=" + DEFAULT_EXPECTED_SALARY);

        // Get all the candidateAdditionalInfosList where expectedSalary is greater than SMALLER_EXPECTED_SALARY
        defaultCandidateAdditionalInfosShouldBeFound("expectedSalary.greaterThan=" + SMALLER_EXPECTED_SALARY);
    }

    @Test
    @Transactional
    void getAllCandidateAdditionalInfosByFirstContactIsEqualToSomething() throws Exception {
        // Initialize the database
        candidateAdditionalInfosRepository.saveAndFlush(candidateAdditionalInfos);

        // Get all the candidateAdditionalInfosList where firstContact equals to DEFAULT_FIRST_CONTACT
        defaultCandidateAdditionalInfosShouldBeFound("firstContact.equals=" + DEFAULT_FIRST_CONTACT);

        // Get all the candidateAdditionalInfosList where firstContact equals to UPDATED_FIRST_CONTACT
        defaultCandidateAdditionalInfosShouldNotBeFound("firstContact.equals=" + UPDATED_FIRST_CONTACT);
    }

    @Test
    @Transactional
    void getAllCandidateAdditionalInfosByFirstContactIsInShouldWork() throws Exception {
        // Initialize the database
        candidateAdditionalInfosRepository.saveAndFlush(candidateAdditionalInfos);

        // Get all the candidateAdditionalInfosList where firstContact in DEFAULT_FIRST_CONTACT or UPDATED_FIRST_CONTACT
        defaultCandidateAdditionalInfosShouldBeFound("firstContact.in=" + DEFAULT_FIRST_CONTACT + "," + UPDATED_FIRST_CONTACT);

        // Get all the candidateAdditionalInfosList where firstContact equals to UPDATED_FIRST_CONTACT
        defaultCandidateAdditionalInfosShouldNotBeFound("firstContact.in=" + UPDATED_FIRST_CONTACT);
    }

    @Test
    @Transactional
    void getAllCandidateAdditionalInfosByFirstContactIsNullOrNotNull() throws Exception {
        // Initialize the database
        candidateAdditionalInfosRepository.saveAndFlush(candidateAdditionalInfos);

        // Get all the candidateAdditionalInfosList where firstContact is not null
        defaultCandidateAdditionalInfosShouldBeFound("firstContact.specified=true");

        // Get all the candidateAdditionalInfosList where firstContact is null
        defaultCandidateAdditionalInfosShouldNotBeFound("firstContact.specified=false");
    }

    @Test
    @Transactional
    void getAllCandidateAdditionalInfosByLocationIsEqualToSomething() throws Exception {
        // Initialize the database
        candidateAdditionalInfosRepository.saveAndFlush(candidateAdditionalInfos);

        // Get all the candidateAdditionalInfosList where location equals to DEFAULT_LOCATION
        defaultCandidateAdditionalInfosShouldBeFound("location.equals=" + DEFAULT_LOCATION);

        // Get all the candidateAdditionalInfosList where location equals to UPDATED_LOCATION
        defaultCandidateAdditionalInfosShouldNotBeFound("location.equals=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    void getAllCandidateAdditionalInfosByLocationIsInShouldWork() throws Exception {
        // Initialize the database
        candidateAdditionalInfosRepository.saveAndFlush(candidateAdditionalInfos);

        // Get all the candidateAdditionalInfosList where location in DEFAULT_LOCATION or UPDATED_LOCATION
        defaultCandidateAdditionalInfosShouldBeFound("location.in=" + DEFAULT_LOCATION + "," + UPDATED_LOCATION);

        // Get all the candidateAdditionalInfosList where location equals to UPDATED_LOCATION
        defaultCandidateAdditionalInfosShouldNotBeFound("location.in=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    void getAllCandidateAdditionalInfosByLocationIsNullOrNotNull() throws Exception {
        // Initialize the database
        candidateAdditionalInfosRepository.saveAndFlush(candidateAdditionalInfos);

        // Get all the candidateAdditionalInfosList where location is not null
        defaultCandidateAdditionalInfosShouldBeFound("location.specified=true");

        // Get all the candidateAdditionalInfosList where location is null
        defaultCandidateAdditionalInfosShouldNotBeFound("location.specified=false");
    }

    @Test
    @Transactional
    void getAllCandidateAdditionalInfosByLocationContainsSomething() throws Exception {
        // Initialize the database
        candidateAdditionalInfosRepository.saveAndFlush(candidateAdditionalInfos);

        // Get all the candidateAdditionalInfosList where location contains DEFAULT_LOCATION
        defaultCandidateAdditionalInfosShouldBeFound("location.contains=" + DEFAULT_LOCATION);

        // Get all the candidateAdditionalInfosList where location contains UPDATED_LOCATION
        defaultCandidateAdditionalInfosShouldNotBeFound("location.contains=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    void getAllCandidateAdditionalInfosByLocationNotContainsSomething() throws Exception {
        // Initialize the database
        candidateAdditionalInfosRepository.saveAndFlush(candidateAdditionalInfos);

        // Get all the candidateAdditionalInfosList where location does not contain DEFAULT_LOCATION
        defaultCandidateAdditionalInfosShouldNotBeFound("location.doesNotContain=" + DEFAULT_LOCATION);

        // Get all the candidateAdditionalInfosList where location does not contain UPDATED_LOCATION
        defaultCandidateAdditionalInfosShouldBeFound("location.doesNotContain=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    void getAllCandidateAdditionalInfosByMobileIsEqualToSomething() throws Exception {
        // Initialize the database
        candidateAdditionalInfosRepository.saveAndFlush(candidateAdditionalInfos);

        // Get all the candidateAdditionalInfosList where mobile equals to DEFAULT_MOBILE
        defaultCandidateAdditionalInfosShouldBeFound("mobile.equals=" + DEFAULT_MOBILE);

        // Get all the candidateAdditionalInfosList where mobile equals to UPDATED_MOBILE
        defaultCandidateAdditionalInfosShouldNotBeFound("mobile.equals=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    void getAllCandidateAdditionalInfosByMobileIsInShouldWork() throws Exception {
        // Initialize the database
        candidateAdditionalInfosRepository.saveAndFlush(candidateAdditionalInfos);

        // Get all the candidateAdditionalInfosList where mobile in DEFAULT_MOBILE or UPDATED_MOBILE
        defaultCandidateAdditionalInfosShouldBeFound("mobile.in=" + DEFAULT_MOBILE + "," + UPDATED_MOBILE);

        // Get all the candidateAdditionalInfosList where mobile equals to UPDATED_MOBILE
        defaultCandidateAdditionalInfosShouldNotBeFound("mobile.in=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    void getAllCandidateAdditionalInfosByMobileIsNullOrNotNull() throws Exception {
        // Initialize the database
        candidateAdditionalInfosRepository.saveAndFlush(candidateAdditionalInfos);

        // Get all the candidateAdditionalInfosList where mobile is not null
        defaultCandidateAdditionalInfosShouldBeFound("mobile.specified=true");

        // Get all the candidateAdditionalInfosList where mobile is null
        defaultCandidateAdditionalInfosShouldNotBeFound("mobile.specified=false");
    }

    @Test
    @Transactional
    void getAllCandidateAdditionalInfosByMobileContainsSomething() throws Exception {
        // Initialize the database
        candidateAdditionalInfosRepository.saveAndFlush(candidateAdditionalInfos);

        // Get all the candidateAdditionalInfosList where mobile contains DEFAULT_MOBILE
        defaultCandidateAdditionalInfosShouldBeFound("mobile.contains=" + DEFAULT_MOBILE);

        // Get all the candidateAdditionalInfosList where mobile contains UPDATED_MOBILE
        defaultCandidateAdditionalInfosShouldNotBeFound("mobile.contains=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    void getAllCandidateAdditionalInfosByMobileNotContainsSomething() throws Exception {
        // Initialize the database
        candidateAdditionalInfosRepository.saveAndFlush(candidateAdditionalInfos);

        // Get all the candidateAdditionalInfosList where mobile does not contain DEFAULT_MOBILE
        defaultCandidateAdditionalInfosShouldNotBeFound("mobile.doesNotContain=" + DEFAULT_MOBILE);

        // Get all the candidateAdditionalInfosList where mobile does not contain UPDATED_MOBILE
        defaultCandidateAdditionalInfosShouldBeFound("mobile.doesNotContain=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    void getAllCandidateAdditionalInfosByDisponibilityIsEqualToSomething() throws Exception {
        // Initialize the database
        candidateAdditionalInfosRepository.saveAndFlush(candidateAdditionalInfos);

        // Get all the candidateAdditionalInfosList where disponibility equals to DEFAULT_DISPONIBILITY
        defaultCandidateAdditionalInfosShouldBeFound("disponibility.equals=" + DEFAULT_DISPONIBILITY);

        // Get all the candidateAdditionalInfosList where disponibility equals to UPDATED_DISPONIBILITY
        defaultCandidateAdditionalInfosShouldNotBeFound("disponibility.equals=" + UPDATED_DISPONIBILITY);
    }

    @Test
    @Transactional
    void getAllCandidateAdditionalInfosByDisponibilityIsInShouldWork() throws Exception {
        // Initialize the database
        candidateAdditionalInfosRepository.saveAndFlush(candidateAdditionalInfos);

        // Get all the candidateAdditionalInfosList where disponibility in DEFAULT_DISPONIBILITY or UPDATED_DISPONIBILITY
        defaultCandidateAdditionalInfosShouldBeFound("disponibility.in=" + DEFAULT_DISPONIBILITY + "," + UPDATED_DISPONIBILITY);

        // Get all the candidateAdditionalInfosList where disponibility equals to UPDATED_DISPONIBILITY
        defaultCandidateAdditionalInfosShouldNotBeFound("disponibility.in=" + UPDATED_DISPONIBILITY);
    }

    @Test
    @Transactional
    void getAllCandidateAdditionalInfosByDisponibilityIsNullOrNotNull() throws Exception {
        // Initialize the database
        candidateAdditionalInfosRepository.saveAndFlush(candidateAdditionalInfos);

        // Get all the candidateAdditionalInfosList where disponibility is not null
        defaultCandidateAdditionalInfosShouldBeFound("disponibility.specified=true");

        // Get all the candidateAdditionalInfosList where disponibility is null
        defaultCandidateAdditionalInfosShouldNotBeFound("disponibility.specified=false");
    }

    @Test
    @Transactional
    void getAllCandidateAdditionalInfosByDisponibilityContainsSomething() throws Exception {
        // Initialize the database
        candidateAdditionalInfosRepository.saveAndFlush(candidateAdditionalInfos);

        // Get all the candidateAdditionalInfosList where disponibility contains DEFAULT_DISPONIBILITY
        defaultCandidateAdditionalInfosShouldBeFound("disponibility.contains=" + DEFAULT_DISPONIBILITY);

        // Get all the candidateAdditionalInfosList where disponibility contains UPDATED_DISPONIBILITY
        defaultCandidateAdditionalInfosShouldNotBeFound("disponibility.contains=" + UPDATED_DISPONIBILITY);
    }

    @Test
    @Transactional
    void getAllCandidateAdditionalInfosByDisponibilityNotContainsSomething() throws Exception {
        // Initialize the database
        candidateAdditionalInfosRepository.saveAndFlush(candidateAdditionalInfos);

        // Get all the candidateAdditionalInfosList where disponibility does not contain DEFAULT_DISPONIBILITY
        defaultCandidateAdditionalInfosShouldNotBeFound("disponibility.doesNotContain=" + DEFAULT_DISPONIBILITY);

        // Get all the candidateAdditionalInfosList where disponibility does not contain UPDATED_DISPONIBILITY
        defaultCandidateAdditionalInfosShouldBeFound("disponibility.doesNotContain=" + UPDATED_DISPONIBILITY);
    }

    @Test
    @Transactional
    void getAllCandidateAdditionalInfosByDocumentsIsEqualToSomething() throws Exception {
        ObjectContainingFile documents;
        if (TestUtil.findAll(em, ObjectContainingFile.class).isEmpty()) {
            candidateAdditionalInfosRepository.saveAndFlush(candidateAdditionalInfos);
            documents = ObjectContainingFileResourceIT.createEntity(em);
        } else {
            documents = TestUtil.findAll(em, ObjectContainingFile.class).get(0);
        }
        em.persist(documents);
        em.flush();
        candidateAdditionalInfos.addDocuments(documents);
        candidateAdditionalInfosRepository.saveAndFlush(candidateAdditionalInfos);
        Long documentsId = documents.getId();
        // Get all the candidateAdditionalInfosList where documents equals to documentsId
        defaultCandidateAdditionalInfosShouldBeFound("documentsId.equals=" + documentsId);

        // Get all the candidateAdditionalInfosList where documents equals to (documentsId + 1)
        defaultCandidateAdditionalInfosShouldNotBeFound("documentsId.equals=" + (documentsId + 1));
    }

    @Test
    @Transactional
    void getAllCandidateAdditionalInfosByCandidateIsEqualToSomething() throws Exception {
        Candidate candidate;
        if (TestUtil.findAll(em, Candidate.class).isEmpty()) {
            candidateAdditionalInfosRepository.saveAndFlush(candidateAdditionalInfos);
            candidate = CandidateResourceIT.createEntity(em);
        } else {
            candidate = TestUtil.findAll(em, Candidate.class).get(0);
        }
        em.persist(candidate);
        em.flush();
        candidateAdditionalInfos.setCandidate(candidate);
        candidate.setAdditionalInfos(candidateAdditionalInfos);
        candidateAdditionalInfosRepository.saveAndFlush(candidateAdditionalInfos);
        Long candidateId = candidate.getId();
        // Get all the candidateAdditionalInfosList where candidate equals to candidateId
        defaultCandidateAdditionalInfosShouldBeFound("candidateId.equals=" + candidateId);

        // Get all the candidateAdditionalInfosList where candidate equals to (candidateId + 1)
        defaultCandidateAdditionalInfosShouldNotBeFound("candidateId.equals=" + (candidateId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCandidateAdditionalInfosShouldBeFound(String filter) throws Exception {
        restCandidateAdditionalInfosMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(candidateAdditionalInfos.getId().intValue())))
            .andExpect(jsonPath("$.[*].birthday").value(hasItem(DEFAULT_BIRTHDAY.toString())))
            .andExpect(jsonPath("$.[*].actualSalary").value(hasItem(DEFAULT_ACTUAL_SALARY)))
            .andExpect(jsonPath("$.[*].expectedSalary").value(hasItem(DEFAULT_EXPECTED_SALARY)))
            .andExpect(jsonPath("$.[*].firstContact").value(hasItem(DEFAULT_FIRST_CONTACT.toString())))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)))
            .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE)))
            .andExpect(jsonPath("$.[*].disponibility").value(hasItem(DEFAULT_DISPONIBILITY)));

        // Check, that the count call also returns 1
        restCandidateAdditionalInfosMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCandidateAdditionalInfosShouldNotBeFound(String filter) throws Exception {
        restCandidateAdditionalInfosMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCandidateAdditionalInfosMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCandidateAdditionalInfos() throws Exception {
        // Get the candidateAdditionalInfos
        restCandidateAdditionalInfosMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCandidateAdditionalInfos() throws Exception {
        // Initialize the database
        candidateAdditionalInfosRepository.saveAndFlush(candidateAdditionalInfos);

        int databaseSizeBeforeUpdate = candidateAdditionalInfosRepository.findAll().size();

        // Update the candidateAdditionalInfos
        CandidateAdditionalInfos updatedCandidateAdditionalInfos = candidateAdditionalInfosRepository
            .findById(candidateAdditionalInfos.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedCandidateAdditionalInfos are not directly saved in db
        em.detach(updatedCandidateAdditionalInfos);
        updatedCandidateAdditionalInfos
            .birthday(UPDATED_BIRTHDAY)
            .actualSalary(UPDATED_ACTUAL_SALARY)
            .expectedSalary(UPDATED_EXPECTED_SALARY)
            .firstContact(UPDATED_FIRST_CONTACT)
            .location(UPDATED_LOCATION)
            .mobile(UPDATED_MOBILE)
            .disponibility(UPDATED_DISPONIBILITY);
        CandidateAdditionalInfosDTO candidateAdditionalInfosDTO = candidateAdditionalInfosMapper.toDto(updatedCandidateAdditionalInfos);

        restCandidateAdditionalInfosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, candidateAdditionalInfosDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(candidateAdditionalInfosDTO))
            )
            .andExpect(status().isOk());

        // Validate the CandidateAdditionalInfos in the database
        List<CandidateAdditionalInfos> candidateAdditionalInfosList = candidateAdditionalInfosRepository.findAll();
        assertThat(candidateAdditionalInfosList).hasSize(databaseSizeBeforeUpdate);
        CandidateAdditionalInfos testCandidateAdditionalInfos = candidateAdditionalInfosList.get(candidateAdditionalInfosList.size() - 1);
        assertThat(testCandidateAdditionalInfos.getBirthday()).isEqualTo(UPDATED_BIRTHDAY);
        assertThat(testCandidateAdditionalInfos.getActualSalary()).isEqualTo(UPDATED_ACTUAL_SALARY);
        assertThat(testCandidateAdditionalInfos.getExpectedSalary()).isEqualTo(UPDATED_EXPECTED_SALARY);
        assertThat(testCandidateAdditionalInfos.getFirstContact()).isEqualTo(UPDATED_FIRST_CONTACT);
        assertThat(testCandidateAdditionalInfos.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testCandidateAdditionalInfos.getMobile()).isEqualTo(UPDATED_MOBILE);
        assertThat(testCandidateAdditionalInfos.getDisponibility()).isEqualTo(UPDATED_DISPONIBILITY);
    }

    @Test
    @Transactional
    void putNonExistingCandidateAdditionalInfos() throws Exception {
        int databaseSizeBeforeUpdate = candidateAdditionalInfosRepository.findAll().size();
        candidateAdditionalInfos.setId(longCount.incrementAndGet());

        // Create the CandidateAdditionalInfos
        CandidateAdditionalInfosDTO candidateAdditionalInfosDTO = candidateAdditionalInfosMapper.toDto(candidateAdditionalInfos);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCandidateAdditionalInfosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, candidateAdditionalInfosDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(candidateAdditionalInfosDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CandidateAdditionalInfos in the database
        List<CandidateAdditionalInfos> candidateAdditionalInfosList = candidateAdditionalInfosRepository.findAll();
        assertThat(candidateAdditionalInfosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCandidateAdditionalInfos() throws Exception {
        int databaseSizeBeforeUpdate = candidateAdditionalInfosRepository.findAll().size();
        candidateAdditionalInfos.setId(longCount.incrementAndGet());

        // Create the CandidateAdditionalInfos
        CandidateAdditionalInfosDTO candidateAdditionalInfosDTO = candidateAdditionalInfosMapper.toDto(candidateAdditionalInfos);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCandidateAdditionalInfosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(candidateAdditionalInfosDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CandidateAdditionalInfos in the database
        List<CandidateAdditionalInfos> candidateAdditionalInfosList = candidateAdditionalInfosRepository.findAll();
        assertThat(candidateAdditionalInfosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCandidateAdditionalInfos() throws Exception {
        int databaseSizeBeforeUpdate = candidateAdditionalInfosRepository.findAll().size();
        candidateAdditionalInfos.setId(longCount.incrementAndGet());

        // Create the CandidateAdditionalInfos
        CandidateAdditionalInfosDTO candidateAdditionalInfosDTO = candidateAdditionalInfosMapper.toDto(candidateAdditionalInfos);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCandidateAdditionalInfosMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(candidateAdditionalInfosDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CandidateAdditionalInfos in the database
        List<CandidateAdditionalInfos> candidateAdditionalInfosList = candidateAdditionalInfosRepository.findAll();
        assertThat(candidateAdditionalInfosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCandidateAdditionalInfosWithPatch() throws Exception {
        // Initialize the database
        candidateAdditionalInfosRepository.saveAndFlush(candidateAdditionalInfos);

        int databaseSizeBeforeUpdate = candidateAdditionalInfosRepository.findAll().size();

        // Update the candidateAdditionalInfos using partial update
        CandidateAdditionalInfos partialUpdatedCandidateAdditionalInfos = new CandidateAdditionalInfos();
        partialUpdatedCandidateAdditionalInfos.setId(candidateAdditionalInfos.getId());

        partialUpdatedCandidateAdditionalInfos
            .birthday(UPDATED_BIRTHDAY)
            .expectedSalary(UPDATED_EXPECTED_SALARY)
            .firstContact(UPDATED_FIRST_CONTACT)
            .location(UPDATED_LOCATION);

        restCandidateAdditionalInfosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCandidateAdditionalInfos.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCandidateAdditionalInfos))
            )
            .andExpect(status().isOk());

        // Validate the CandidateAdditionalInfos in the database
        List<CandidateAdditionalInfos> candidateAdditionalInfosList = candidateAdditionalInfosRepository.findAll();
        assertThat(candidateAdditionalInfosList).hasSize(databaseSizeBeforeUpdate);
        CandidateAdditionalInfos testCandidateAdditionalInfos = candidateAdditionalInfosList.get(candidateAdditionalInfosList.size() - 1);
        assertThat(testCandidateAdditionalInfos.getBirthday()).isEqualTo(UPDATED_BIRTHDAY);
        assertThat(testCandidateAdditionalInfos.getActualSalary()).isEqualTo(DEFAULT_ACTUAL_SALARY);
        assertThat(testCandidateAdditionalInfos.getExpectedSalary()).isEqualTo(UPDATED_EXPECTED_SALARY);
        assertThat(testCandidateAdditionalInfos.getFirstContact()).isEqualTo(UPDATED_FIRST_CONTACT);
        assertThat(testCandidateAdditionalInfos.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testCandidateAdditionalInfos.getMobile()).isEqualTo(DEFAULT_MOBILE);
        assertThat(testCandidateAdditionalInfos.getDisponibility()).isEqualTo(DEFAULT_DISPONIBILITY);
    }

    @Test
    @Transactional
    void fullUpdateCandidateAdditionalInfosWithPatch() throws Exception {
        // Initialize the database
        candidateAdditionalInfosRepository.saveAndFlush(candidateAdditionalInfos);

        int databaseSizeBeforeUpdate = candidateAdditionalInfosRepository.findAll().size();

        // Update the candidateAdditionalInfos using partial update
        CandidateAdditionalInfos partialUpdatedCandidateAdditionalInfos = new CandidateAdditionalInfos();
        partialUpdatedCandidateAdditionalInfos.setId(candidateAdditionalInfos.getId());

        partialUpdatedCandidateAdditionalInfos
            .birthday(UPDATED_BIRTHDAY)
            .actualSalary(UPDATED_ACTUAL_SALARY)
            .expectedSalary(UPDATED_EXPECTED_SALARY)
            .firstContact(UPDATED_FIRST_CONTACT)
            .location(UPDATED_LOCATION)
            .mobile(UPDATED_MOBILE)
            .disponibility(UPDATED_DISPONIBILITY);

        restCandidateAdditionalInfosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCandidateAdditionalInfos.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCandidateAdditionalInfos))
            )
            .andExpect(status().isOk());

        // Validate the CandidateAdditionalInfos in the database
        List<CandidateAdditionalInfos> candidateAdditionalInfosList = candidateAdditionalInfosRepository.findAll();
        assertThat(candidateAdditionalInfosList).hasSize(databaseSizeBeforeUpdate);
        CandidateAdditionalInfos testCandidateAdditionalInfos = candidateAdditionalInfosList.get(candidateAdditionalInfosList.size() - 1);
        assertThat(testCandidateAdditionalInfos.getBirthday()).isEqualTo(UPDATED_BIRTHDAY);
        assertThat(testCandidateAdditionalInfos.getActualSalary()).isEqualTo(UPDATED_ACTUAL_SALARY);
        assertThat(testCandidateAdditionalInfos.getExpectedSalary()).isEqualTo(UPDATED_EXPECTED_SALARY);
        assertThat(testCandidateAdditionalInfos.getFirstContact()).isEqualTo(UPDATED_FIRST_CONTACT);
        assertThat(testCandidateAdditionalInfos.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testCandidateAdditionalInfos.getMobile()).isEqualTo(UPDATED_MOBILE);
        assertThat(testCandidateAdditionalInfos.getDisponibility()).isEqualTo(UPDATED_DISPONIBILITY);
    }

    @Test
    @Transactional
    void patchNonExistingCandidateAdditionalInfos() throws Exception {
        int databaseSizeBeforeUpdate = candidateAdditionalInfosRepository.findAll().size();
        candidateAdditionalInfos.setId(longCount.incrementAndGet());

        // Create the CandidateAdditionalInfos
        CandidateAdditionalInfosDTO candidateAdditionalInfosDTO = candidateAdditionalInfosMapper.toDto(candidateAdditionalInfos);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCandidateAdditionalInfosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, candidateAdditionalInfosDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(candidateAdditionalInfosDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CandidateAdditionalInfos in the database
        List<CandidateAdditionalInfos> candidateAdditionalInfosList = candidateAdditionalInfosRepository.findAll();
        assertThat(candidateAdditionalInfosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCandidateAdditionalInfos() throws Exception {
        int databaseSizeBeforeUpdate = candidateAdditionalInfosRepository.findAll().size();
        candidateAdditionalInfos.setId(longCount.incrementAndGet());

        // Create the CandidateAdditionalInfos
        CandidateAdditionalInfosDTO candidateAdditionalInfosDTO = candidateAdditionalInfosMapper.toDto(candidateAdditionalInfos);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCandidateAdditionalInfosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(candidateAdditionalInfosDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CandidateAdditionalInfos in the database
        List<CandidateAdditionalInfos> candidateAdditionalInfosList = candidateAdditionalInfosRepository.findAll();
        assertThat(candidateAdditionalInfosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCandidateAdditionalInfos() throws Exception {
        int databaseSizeBeforeUpdate = candidateAdditionalInfosRepository.findAll().size();
        candidateAdditionalInfos.setId(longCount.incrementAndGet());

        // Create the CandidateAdditionalInfos
        CandidateAdditionalInfosDTO candidateAdditionalInfosDTO = candidateAdditionalInfosMapper.toDto(candidateAdditionalInfos);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCandidateAdditionalInfosMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(candidateAdditionalInfosDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CandidateAdditionalInfos in the database
        List<CandidateAdditionalInfos> candidateAdditionalInfosList = candidateAdditionalInfosRepository.findAll();
        assertThat(candidateAdditionalInfosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCandidateAdditionalInfos() throws Exception {
        // Initialize the database
        candidateAdditionalInfosRepository.saveAndFlush(candidateAdditionalInfos);

        int databaseSizeBeforeDelete = candidateAdditionalInfosRepository.findAll().size();

        // Delete the candidateAdditionalInfos
        restCandidateAdditionalInfosMockMvc
            .perform(delete(ENTITY_API_URL_ID, candidateAdditionalInfos.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CandidateAdditionalInfos> candidateAdditionalInfosList = candidateAdditionalInfosRepository.findAll();
        assertThat(candidateAdditionalInfosList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
