package atig.recruiting.beta.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import atig.recruiting.beta.IntegrationTest;
import atig.recruiting.beta.domain.Candidate;
import atig.recruiting.beta.domain.Education;
import atig.recruiting.beta.repository.EducationRepository;
import atig.recruiting.beta.service.dto.EducationDTO;
import atig.recruiting.beta.service.mapper.EducationMapper;
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
 * Integration tests for the {@link EducationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EducationResourceIT {

    private static final String DEFAULT_DIPLOMA = "AAAAAAAAAA";
    private static final String UPDATED_DIPLOMA = "BBBBBBBBBB";

    private static final String DEFAULT_ESTABLISHMENT = "AAAAAAAAAA";
    private static final String UPDATED_ESTABLISHMENT = "BBBBBBBBBB";

    private static final String DEFAULT_MENTION = "AAAAAAAAAA";
    private static final String UPDATED_MENTION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_START_DATE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_END_DATE = LocalDate.ofEpochDay(-1L);

    private static final Double DEFAULT_DURATION = 1D;
    private static final Double UPDATED_DURATION = 2D;
    private static final Double SMALLER_DURATION = 1D - 1D;

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/educations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EducationRepository educationRepository;

    @Autowired
    private EducationMapper educationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEducationMockMvc;

    private Education education;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Education createEntity(EntityManager em) {
        Education education = new Education()
            .diploma(DEFAULT_DIPLOMA)
            .establishment(DEFAULT_ESTABLISHMENT)
            .mention(DEFAULT_MENTION)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .duration(DEFAULT_DURATION)
            .location(DEFAULT_LOCATION);
        return education;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Education createUpdatedEntity(EntityManager em) {
        Education education = new Education()
            .diploma(UPDATED_DIPLOMA)
            .establishment(UPDATED_ESTABLISHMENT)
            .mention(UPDATED_MENTION)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .duration(UPDATED_DURATION)
            .location(UPDATED_LOCATION);
        return education;
    }

    @BeforeEach
    public void initTest() {
        education = createEntity(em);
    }

    @Test
    @Transactional
    void createEducation() throws Exception {
        int databaseSizeBeforeCreate = educationRepository.findAll().size();
        // Create the Education
        EducationDTO educationDTO = educationMapper.toDto(education);
        restEducationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(educationDTO)))
            .andExpect(status().isCreated());

        // Validate the Education in the database
        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeCreate + 1);
        Education testEducation = educationList.get(educationList.size() - 1);
        assertThat(testEducation.getDiploma()).isEqualTo(DEFAULT_DIPLOMA);
        assertThat(testEducation.getEstablishment()).isEqualTo(DEFAULT_ESTABLISHMENT);
        assertThat(testEducation.getMention()).isEqualTo(DEFAULT_MENTION);
        assertThat(testEducation.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testEducation.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testEducation.getDuration()).isEqualTo(DEFAULT_DURATION);
        assertThat(testEducation.getLocation()).isEqualTo(DEFAULT_LOCATION);
    }

    @Test
    @Transactional
    void createEducationWithExistingId() throws Exception {
        // Create the Education with an existing ID
        education.setId(1L);
        EducationDTO educationDTO = educationMapper.toDto(education);

        int databaseSizeBeforeCreate = educationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEducationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(educationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Education in the database
        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEducations() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList
        restEducationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(education.getId().intValue())))
            .andExpect(jsonPath("$.[*].diploma").value(hasItem(DEFAULT_DIPLOMA)))
            .andExpect(jsonPath("$.[*].establishment").value(hasItem(DEFAULT_ESTABLISHMENT)))
            .andExpect(jsonPath("$.[*].mention").value(hasItem(DEFAULT_MENTION)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION.doubleValue())))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)));
    }

    @Test
    @Transactional
    void getEducation() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get the education
        restEducationMockMvc
            .perform(get(ENTITY_API_URL_ID, education.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(education.getId().intValue()))
            .andExpect(jsonPath("$.diploma").value(DEFAULT_DIPLOMA))
            .andExpect(jsonPath("$.establishment").value(DEFAULT_ESTABLISHMENT))
            .andExpect(jsonPath("$.mention").value(DEFAULT_MENTION))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION.doubleValue()))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION));
    }

    @Test
    @Transactional
    void getEducationsByIdFiltering() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        Long id = education.getId();

        defaultEducationShouldBeFound("id.equals=" + id);
        defaultEducationShouldNotBeFound("id.notEquals=" + id);

        defaultEducationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEducationShouldNotBeFound("id.greaterThan=" + id);

        defaultEducationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEducationShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEducationsByDiplomaIsEqualToSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where diploma equals to DEFAULT_DIPLOMA
        defaultEducationShouldBeFound("diploma.equals=" + DEFAULT_DIPLOMA);

        // Get all the educationList where diploma equals to UPDATED_DIPLOMA
        defaultEducationShouldNotBeFound("diploma.equals=" + UPDATED_DIPLOMA);
    }

    @Test
    @Transactional
    void getAllEducationsByDiplomaIsInShouldWork() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where diploma in DEFAULT_DIPLOMA or UPDATED_DIPLOMA
        defaultEducationShouldBeFound("diploma.in=" + DEFAULT_DIPLOMA + "," + UPDATED_DIPLOMA);

        // Get all the educationList where diploma equals to UPDATED_DIPLOMA
        defaultEducationShouldNotBeFound("diploma.in=" + UPDATED_DIPLOMA);
    }

    @Test
    @Transactional
    void getAllEducationsByDiplomaIsNullOrNotNull() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where diploma is not null
        defaultEducationShouldBeFound("diploma.specified=true");

        // Get all the educationList where diploma is null
        defaultEducationShouldNotBeFound("diploma.specified=false");
    }

    @Test
    @Transactional
    void getAllEducationsByDiplomaContainsSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where diploma contains DEFAULT_DIPLOMA
        defaultEducationShouldBeFound("diploma.contains=" + DEFAULT_DIPLOMA);

        // Get all the educationList where diploma contains UPDATED_DIPLOMA
        defaultEducationShouldNotBeFound("diploma.contains=" + UPDATED_DIPLOMA);
    }

    @Test
    @Transactional
    void getAllEducationsByDiplomaNotContainsSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where diploma does not contain DEFAULT_DIPLOMA
        defaultEducationShouldNotBeFound("diploma.doesNotContain=" + DEFAULT_DIPLOMA);

        // Get all the educationList where diploma does not contain UPDATED_DIPLOMA
        defaultEducationShouldBeFound("diploma.doesNotContain=" + UPDATED_DIPLOMA);
    }

    @Test
    @Transactional
    void getAllEducationsByEstablishmentIsEqualToSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where establishment equals to DEFAULT_ESTABLISHMENT
        defaultEducationShouldBeFound("establishment.equals=" + DEFAULT_ESTABLISHMENT);

        // Get all the educationList where establishment equals to UPDATED_ESTABLISHMENT
        defaultEducationShouldNotBeFound("establishment.equals=" + UPDATED_ESTABLISHMENT);
    }

    @Test
    @Transactional
    void getAllEducationsByEstablishmentIsInShouldWork() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where establishment in DEFAULT_ESTABLISHMENT or UPDATED_ESTABLISHMENT
        defaultEducationShouldBeFound("establishment.in=" + DEFAULT_ESTABLISHMENT + "," + UPDATED_ESTABLISHMENT);

        // Get all the educationList where establishment equals to UPDATED_ESTABLISHMENT
        defaultEducationShouldNotBeFound("establishment.in=" + UPDATED_ESTABLISHMENT);
    }

    @Test
    @Transactional
    void getAllEducationsByEstablishmentIsNullOrNotNull() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where establishment is not null
        defaultEducationShouldBeFound("establishment.specified=true");

        // Get all the educationList where establishment is null
        defaultEducationShouldNotBeFound("establishment.specified=false");
    }

    @Test
    @Transactional
    void getAllEducationsByEstablishmentContainsSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where establishment contains DEFAULT_ESTABLISHMENT
        defaultEducationShouldBeFound("establishment.contains=" + DEFAULT_ESTABLISHMENT);

        // Get all the educationList where establishment contains UPDATED_ESTABLISHMENT
        defaultEducationShouldNotBeFound("establishment.contains=" + UPDATED_ESTABLISHMENT);
    }

    @Test
    @Transactional
    void getAllEducationsByEstablishmentNotContainsSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where establishment does not contain DEFAULT_ESTABLISHMENT
        defaultEducationShouldNotBeFound("establishment.doesNotContain=" + DEFAULT_ESTABLISHMENT);

        // Get all the educationList where establishment does not contain UPDATED_ESTABLISHMENT
        defaultEducationShouldBeFound("establishment.doesNotContain=" + UPDATED_ESTABLISHMENT);
    }

    @Test
    @Transactional
    void getAllEducationsByMentionIsEqualToSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where mention equals to DEFAULT_MENTION
        defaultEducationShouldBeFound("mention.equals=" + DEFAULT_MENTION);

        // Get all the educationList where mention equals to UPDATED_MENTION
        defaultEducationShouldNotBeFound("mention.equals=" + UPDATED_MENTION);
    }

    @Test
    @Transactional
    void getAllEducationsByMentionIsInShouldWork() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where mention in DEFAULT_MENTION or UPDATED_MENTION
        defaultEducationShouldBeFound("mention.in=" + DEFAULT_MENTION + "," + UPDATED_MENTION);

        // Get all the educationList where mention equals to UPDATED_MENTION
        defaultEducationShouldNotBeFound("mention.in=" + UPDATED_MENTION);
    }

    @Test
    @Transactional
    void getAllEducationsByMentionIsNullOrNotNull() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where mention is not null
        defaultEducationShouldBeFound("mention.specified=true");

        // Get all the educationList where mention is null
        defaultEducationShouldNotBeFound("mention.specified=false");
    }

    @Test
    @Transactional
    void getAllEducationsByMentionContainsSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where mention contains DEFAULT_MENTION
        defaultEducationShouldBeFound("mention.contains=" + DEFAULT_MENTION);

        // Get all the educationList where mention contains UPDATED_MENTION
        defaultEducationShouldNotBeFound("mention.contains=" + UPDATED_MENTION);
    }

    @Test
    @Transactional
    void getAllEducationsByMentionNotContainsSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where mention does not contain DEFAULT_MENTION
        defaultEducationShouldNotBeFound("mention.doesNotContain=" + DEFAULT_MENTION);

        // Get all the educationList where mention does not contain UPDATED_MENTION
        defaultEducationShouldBeFound("mention.doesNotContain=" + UPDATED_MENTION);
    }

    @Test
    @Transactional
    void getAllEducationsByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where startDate equals to DEFAULT_START_DATE
        defaultEducationShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);

        // Get all the educationList where startDate equals to UPDATED_START_DATE
        defaultEducationShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllEducationsByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where startDate in DEFAULT_START_DATE or UPDATED_START_DATE
        defaultEducationShouldBeFound("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

        // Get all the educationList where startDate equals to UPDATED_START_DATE
        defaultEducationShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllEducationsByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where startDate is not null
        defaultEducationShouldBeFound("startDate.specified=true");

        // Get all the educationList where startDate is null
        defaultEducationShouldNotBeFound("startDate.specified=false");
    }

    @Test
    @Transactional
    void getAllEducationsByStartDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where startDate is greater than or equal to DEFAULT_START_DATE
        defaultEducationShouldBeFound("startDate.greaterThanOrEqual=" + DEFAULT_START_DATE);

        // Get all the educationList where startDate is greater than or equal to UPDATED_START_DATE
        defaultEducationShouldNotBeFound("startDate.greaterThanOrEqual=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllEducationsByStartDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where startDate is less than or equal to DEFAULT_START_DATE
        defaultEducationShouldBeFound("startDate.lessThanOrEqual=" + DEFAULT_START_DATE);

        // Get all the educationList where startDate is less than or equal to SMALLER_START_DATE
        defaultEducationShouldNotBeFound("startDate.lessThanOrEqual=" + SMALLER_START_DATE);
    }

    @Test
    @Transactional
    void getAllEducationsByStartDateIsLessThanSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where startDate is less than DEFAULT_START_DATE
        defaultEducationShouldNotBeFound("startDate.lessThan=" + DEFAULT_START_DATE);

        // Get all the educationList where startDate is less than UPDATED_START_DATE
        defaultEducationShouldBeFound("startDate.lessThan=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllEducationsByStartDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where startDate is greater than DEFAULT_START_DATE
        defaultEducationShouldNotBeFound("startDate.greaterThan=" + DEFAULT_START_DATE);

        // Get all the educationList where startDate is greater than SMALLER_START_DATE
        defaultEducationShouldBeFound("startDate.greaterThan=" + SMALLER_START_DATE);
    }

    @Test
    @Transactional
    void getAllEducationsByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where endDate equals to DEFAULT_END_DATE
        defaultEducationShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the educationList where endDate equals to UPDATED_END_DATE
        defaultEducationShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllEducationsByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultEducationShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the educationList where endDate equals to UPDATED_END_DATE
        defaultEducationShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllEducationsByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where endDate is not null
        defaultEducationShouldBeFound("endDate.specified=true");

        // Get all the educationList where endDate is null
        defaultEducationShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    void getAllEducationsByEndDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where endDate is greater than or equal to DEFAULT_END_DATE
        defaultEducationShouldBeFound("endDate.greaterThanOrEqual=" + DEFAULT_END_DATE);

        // Get all the educationList where endDate is greater than or equal to UPDATED_END_DATE
        defaultEducationShouldNotBeFound("endDate.greaterThanOrEqual=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllEducationsByEndDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where endDate is less than or equal to DEFAULT_END_DATE
        defaultEducationShouldBeFound("endDate.lessThanOrEqual=" + DEFAULT_END_DATE);

        // Get all the educationList where endDate is less than or equal to SMALLER_END_DATE
        defaultEducationShouldNotBeFound("endDate.lessThanOrEqual=" + SMALLER_END_DATE);
    }

    @Test
    @Transactional
    void getAllEducationsByEndDateIsLessThanSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where endDate is less than DEFAULT_END_DATE
        defaultEducationShouldNotBeFound("endDate.lessThan=" + DEFAULT_END_DATE);

        // Get all the educationList where endDate is less than UPDATED_END_DATE
        defaultEducationShouldBeFound("endDate.lessThan=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllEducationsByEndDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where endDate is greater than DEFAULT_END_DATE
        defaultEducationShouldNotBeFound("endDate.greaterThan=" + DEFAULT_END_DATE);

        // Get all the educationList where endDate is greater than SMALLER_END_DATE
        defaultEducationShouldBeFound("endDate.greaterThan=" + SMALLER_END_DATE);
    }

    @Test
    @Transactional
    void getAllEducationsByDurationIsEqualToSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where duration equals to DEFAULT_DURATION
        defaultEducationShouldBeFound("duration.equals=" + DEFAULT_DURATION);

        // Get all the educationList where duration equals to UPDATED_DURATION
        defaultEducationShouldNotBeFound("duration.equals=" + UPDATED_DURATION);
    }

    @Test
    @Transactional
    void getAllEducationsByDurationIsInShouldWork() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where duration in DEFAULT_DURATION or UPDATED_DURATION
        defaultEducationShouldBeFound("duration.in=" + DEFAULT_DURATION + "," + UPDATED_DURATION);

        // Get all the educationList where duration equals to UPDATED_DURATION
        defaultEducationShouldNotBeFound("duration.in=" + UPDATED_DURATION);
    }

    @Test
    @Transactional
    void getAllEducationsByDurationIsNullOrNotNull() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where duration is not null
        defaultEducationShouldBeFound("duration.specified=true");

        // Get all the educationList where duration is null
        defaultEducationShouldNotBeFound("duration.specified=false");
    }

    @Test
    @Transactional
    void getAllEducationsByDurationIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where duration is greater than or equal to DEFAULT_DURATION
        defaultEducationShouldBeFound("duration.greaterThanOrEqual=" + DEFAULT_DURATION);

        // Get all the educationList where duration is greater than or equal to UPDATED_DURATION
        defaultEducationShouldNotBeFound("duration.greaterThanOrEqual=" + UPDATED_DURATION);
    }

    @Test
    @Transactional
    void getAllEducationsByDurationIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where duration is less than or equal to DEFAULT_DURATION
        defaultEducationShouldBeFound("duration.lessThanOrEqual=" + DEFAULT_DURATION);

        // Get all the educationList where duration is less than or equal to SMALLER_DURATION
        defaultEducationShouldNotBeFound("duration.lessThanOrEqual=" + SMALLER_DURATION);
    }

    @Test
    @Transactional
    void getAllEducationsByDurationIsLessThanSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where duration is less than DEFAULT_DURATION
        defaultEducationShouldNotBeFound("duration.lessThan=" + DEFAULT_DURATION);

        // Get all the educationList where duration is less than UPDATED_DURATION
        defaultEducationShouldBeFound("duration.lessThan=" + UPDATED_DURATION);
    }

    @Test
    @Transactional
    void getAllEducationsByDurationIsGreaterThanSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where duration is greater than DEFAULT_DURATION
        defaultEducationShouldNotBeFound("duration.greaterThan=" + DEFAULT_DURATION);

        // Get all the educationList where duration is greater than SMALLER_DURATION
        defaultEducationShouldBeFound("duration.greaterThan=" + SMALLER_DURATION);
    }

    @Test
    @Transactional
    void getAllEducationsByLocationIsEqualToSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where location equals to DEFAULT_LOCATION
        defaultEducationShouldBeFound("location.equals=" + DEFAULT_LOCATION);

        // Get all the educationList where location equals to UPDATED_LOCATION
        defaultEducationShouldNotBeFound("location.equals=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    void getAllEducationsByLocationIsInShouldWork() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where location in DEFAULT_LOCATION or UPDATED_LOCATION
        defaultEducationShouldBeFound("location.in=" + DEFAULT_LOCATION + "," + UPDATED_LOCATION);

        // Get all the educationList where location equals to UPDATED_LOCATION
        defaultEducationShouldNotBeFound("location.in=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    void getAllEducationsByLocationIsNullOrNotNull() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where location is not null
        defaultEducationShouldBeFound("location.specified=true");

        // Get all the educationList where location is null
        defaultEducationShouldNotBeFound("location.specified=false");
    }

    @Test
    @Transactional
    void getAllEducationsByLocationContainsSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where location contains DEFAULT_LOCATION
        defaultEducationShouldBeFound("location.contains=" + DEFAULT_LOCATION);

        // Get all the educationList where location contains UPDATED_LOCATION
        defaultEducationShouldNotBeFound("location.contains=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    void getAllEducationsByLocationNotContainsSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where location does not contain DEFAULT_LOCATION
        defaultEducationShouldNotBeFound("location.doesNotContain=" + DEFAULT_LOCATION);

        // Get all the educationList where location does not contain UPDATED_LOCATION
        defaultEducationShouldBeFound("location.doesNotContain=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    void getAllEducationsByEducationCandidateIsEqualToSomething() throws Exception {
        Candidate educationCandidate;
        if (TestUtil.findAll(em, Candidate.class).isEmpty()) {
            educationRepository.saveAndFlush(education);
            educationCandidate = CandidateResourceIT.createEntity(em);
        } else {
            educationCandidate = TestUtil.findAll(em, Candidate.class).get(0);
        }
        em.persist(educationCandidate);
        em.flush();
        education.setEducationCandidate(educationCandidate);
        educationRepository.saveAndFlush(education);
        Long educationCandidateId = educationCandidate.getId();
        // Get all the educationList where educationCandidate equals to educationCandidateId
        defaultEducationShouldBeFound("educationCandidateId.equals=" + educationCandidateId);

        // Get all the educationList where educationCandidate equals to (educationCandidateId + 1)
        defaultEducationShouldNotBeFound("educationCandidateId.equals=" + (educationCandidateId + 1));
    }

    @Test
    @Transactional
    void getAllEducationsByCandidateIsEqualToSomething() throws Exception {
        Candidate candidate;
        if (TestUtil.findAll(em, Candidate.class).isEmpty()) {
            educationRepository.saveAndFlush(education);
            candidate = CandidateResourceIT.createEntity(em);
        } else {
            candidate = TestUtil.findAll(em, Candidate.class).get(0);
        }
        em.persist(candidate);
        em.flush();
        education.setCandidate(candidate);
        educationRepository.saveAndFlush(education);
        Long candidateId = candidate.getId();
        // Get all the educationList where candidate equals to candidateId
        defaultEducationShouldBeFound("candidateId.equals=" + candidateId);

        // Get all the educationList where candidate equals to (candidateId + 1)
        defaultEducationShouldNotBeFound("candidateId.equals=" + (candidateId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEducationShouldBeFound(String filter) throws Exception {
        restEducationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(education.getId().intValue())))
            .andExpect(jsonPath("$.[*].diploma").value(hasItem(DEFAULT_DIPLOMA)))
            .andExpect(jsonPath("$.[*].establishment").value(hasItem(DEFAULT_ESTABLISHMENT)))
            .andExpect(jsonPath("$.[*].mention").value(hasItem(DEFAULT_MENTION)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION.doubleValue())))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)));

        // Check, that the count call also returns 1
        restEducationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEducationShouldNotBeFound(String filter) throws Exception {
        restEducationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEducationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEducation() throws Exception {
        // Get the education
        restEducationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEducation() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        int databaseSizeBeforeUpdate = educationRepository.findAll().size();

        // Update the education
        Education updatedEducation = educationRepository.findById(education.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEducation are not directly saved in db
        em.detach(updatedEducation);
        updatedEducation
            .diploma(UPDATED_DIPLOMA)
            .establishment(UPDATED_ESTABLISHMENT)
            .mention(UPDATED_MENTION)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .duration(UPDATED_DURATION)
            .location(UPDATED_LOCATION);
        EducationDTO educationDTO = educationMapper.toDto(updatedEducation);

        restEducationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, educationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(educationDTO))
            )
            .andExpect(status().isOk());

        // Validate the Education in the database
        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeUpdate);
        Education testEducation = educationList.get(educationList.size() - 1);
        assertThat(testEducation.getDiploma()).isEqualTo(UPDATED_DIPLOMA);
        assertThat(testEducation.getEstablishment()).isEqualTo(UPDATED_ESTABLISHMENT);
        assertThat(testEducation.getMention()).isEqualTo(UPDATED_MENTION);
        assertThat(testEducation.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testEducation.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testEducation.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testEducation.getLocation()).isEqualTo(UPDATED_LOCATION);
    }

    @Test
    @Transactional
    void putNonExistingEducation() throws Exception {
        int databaseSizeBeforeUpdate = educationRepository.findAll().size();
        education.setId(longCount.incrementAndGet());

        // Create the Education
        EducationDTO educationDTO = educationMapper.toDto(education);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEducationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, educationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(educationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Education in the database
        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEducation() throws Exception {
        int databaseSizeBeforeUpdate = educationRepository.findAll().size();
        education.setId(longCount.incrementAndGet());

        // Create the Education
        EducationDTO educationDTO = educationMapper.toDto(education);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEducationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(educationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Education in the database
        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEducation() throws Exception {
        int databaseSizeBeforeUpdate = educationRepository.findAll().size();
        education.setId(longCount.incrementAndGet());

        // Create the Education
        EducationDTO educationDTO = educationMapper.toDto(education);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEducationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(educationDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Education in the database
        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEducationWithPatch() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        int databaseSizeBeforeUpdate = educationRepository.findAll().size();

        // Update the education using partial update
        Education partialUpdatedEducation = new Education();
        partialUpdatedEducation.setId(education.getId());

        partialUpdatedEducation
            .diploma(UPDATED_DIPLOMA)
            .establishment(UPDATED_ESTABLISHMENT)
            .startDate(UPDATED_START_DATE)
            .location(UPDATED_LOCATION);

        restEducationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEducation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEducation))
            )
            .andExpect(status().isOk());

        // Validate the Education in the database
        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeUpdate);
        Education testEducation = educationList.get(educationList.size() - 1);
        assertThat(testEducation.getDiploma()).isEqualTo(UPDATED_DIPLOMA);
        assertThat(testEducation.getEstablishment()).isEqualTo(UPDATED_ESTABLISHMENT);
        assertThat(testEducation.getMention()).isEqualTo(DEFAULT_MENTION);
        assertThat(testEducation.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testEducation.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testEducation.getDuration()).isEqualTo(DEFAULT_DURATION);
        assertThat(testEducation.getLocation()).isEqualTo(UPDATED_LOCATION);
    }

    @Test
    @Transactional
    void fullUpdateEducationWithPatch() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        int databaseSizeBeforeUpdate = educationRepository.findAll().size();

        // Update the education using partial update
        Education partialUpdatedEducation = new Education();
        partialUpdatedEducation.setId(education.getId());

        partialUpdatedEducation
            .diploma(UPDATED_DIPLOMA)
            .establishment(UPDATED_ESTABLISHMENT)
            .mention(UPDATED_MENTION)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .duration(UPDATED_DURATION)
            .location(UPDATED_LOCATION);

        restEducationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEducation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEducation))
            )
            .andExpect(status().isOk());

        // Validate the Education in the database
        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeUpdate);
        Education testEducation = educationList.get(educationList.size() - 1);
        assertThat(testEducation.getDiploma()).isEqualTo(UPDATED_DIPLOMA);
        assertThat(testEducation.getEstablishment()).isEqualTo(UPDATED_ESTABLISHMENT);
        assertThat(testEducation.getMention()).isEqualTo(UPDATED_MENTION);
        assertThat(testEducation.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testEducation.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testEducation.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testEducation.getLocation()).isEqualTo(UPDATED_LOCATION);
    }

    @Test
    @Transactional
    void patchNonExistingEducation() throws Exception {
        int databaseSizeBeforeUpdate = educationRepository.findAll().size();
        education.setId(longCount.incrementAndGet());

        // Create the Education
        EducationDTO educationDTO = educationMapper.toDto(education);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEducationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, educationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(educationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Education in the database
        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEducation() throws Exception {
        int databaseSizeBeforeUpdate = educationRepository.findAll().size();
        education.setId(longCount.incrementAndGet());

        // Create the Education
        EducationDTO educationDTO = educationMapper.toDto(education);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEducationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(educationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Education in the database
        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEducation() throws Exception {
        int databaseSizeBeforeUpdate = educationRepository.findAll().size();
        education.setId(longCount.incrementAndGet());

        // Create the Education
        EducationDTO educationDTO = educationMapper.toDto(education);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEducationMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(educationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Education in the database
        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEducation() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        int databaseSizeBeforeDelete = educationRepository.findAll().size();

        // Delete the education
        restEducationMockMvc
            .perform(delete(ENTITY_API_URL_ID, education.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
