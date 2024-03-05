package atig.recruiting.beta.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import atig.recruiting.beta.IntegrationTest;
import atig.recruiting.beta.domain.Candidate;
import atig.recruiting.beta.domain.Experience;
import atig.recruiting.beta.domain.Tool;
import atig.recruiting.beta.repository.ExperienceRepository;
import atig.recruiting.beta.service.dto.ExperienceDTO;
import atig.recruiting.beta.service.mapper.ExperienceMapper;
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
 * Integration tests for the {@link ExperienceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ExperienceResourceIT {

    private static final String DEFAULT_COMPANY = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_SITE = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_SITE = "BBBBBBBBBB";

    private static final String DEFAULT_ROLE = "AAAAAAAAAA";
    private static final String UPDATED_ROLE = "BBBBBBBBBB";

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

    private static final String DEFAULT_TASKS = "AAAAAAAAAA";
    private static final String UPDATED_TASKS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/experiences";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ExperienceRepository experienceRepository;

    @Autowired
    private ExperienceMapper experienceMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restExperienceMockMvc;

    private Experience experience;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Experience createEntity(EntityManager em) {
        Experience experience = new Experience()
            .company(DEFAULT_COMPANY)
            .companySite(DEFAULT_COMPANY_SITE)
            .role(DEFAULT_ROLE)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .duration(DEFAULT_DURATION)
            .location(DEFAULT_LOCATION)
            .tasks(DEFAULT_TASKS);
        return experience;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Experience createUpdatedEntity(EntityManager em) {
        Experience experience = new Experience()
            .company(UPDATED_COMPANY)
            .companySite(UPDATED_COMPANY_SITE)
            .role(UPDATED_ROLE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .duration(UPDATED_DURATION)
            .location(UPDATED_LOCATION)
            .tasks(UPDATED_TASKS);
        return experience;
    }

    @BeforeEach
    public void initTest() {
        experience = createEntity(em);
    }

    @Test
    @Transactional
    void createExperience() throws Exception {
        int databaseSizeBeforeCreate = experienceRepository.findAll().size();
        // Create the Experience
        ExperienceDTO experienceDTO = experienceMapper.toDto(experience);
        restExperienceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(experienceDTO)))
            .andExpect(status().isCreated());

        // Validate the Experience in the database
        List<Experience> experienceList = experienceRepository.findAll();
        assertThat(experienceList).hasSize(databaseSizeBeforeCreate + 1);
        Experience testExperience = experienceList.get(experienceList.size() - 1);
        assertThat(testExperience.getCompany()).isEqualTo(DEFAULT_COMPANY);
        assertThat(testExperience.getCompanySite()).isEqualTo(DEFAULT_COMPANY_SITE);
        assertThat(testExperience.getRole()).isEqualTo(DEFAULT_ROLE);
        assertThat(testExperience.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testExperience.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testExperience.getDuration()).isEqualTo(DEFAULT_DURATION);
        assertThat(testExperience.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testExperience.getTasks()).isEqualTo(DEFAULT_TASKS);
    }

    @Test
    @Transactional
    void createExperienceWithExistingId() throws Exception {
        // Create the Experience with an existing ID
        experience.setId(1L);
        ExperienceDTO experienceDTO = experienceMapper.toDto(experience);

        int databaseSizeBeforeCreate = experienceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restExperienceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(experienceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Experience in the database
        List<Experience> experienceList = experienceRepository.findAll();
        assertThat(experienceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllExperiences() throws Exception {
        // Initialize the database
        experienceRepository.saveAndFlush(experience);

        // Get all the experienceList
        restExperienceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(experience.getId().intValue())))
            .andExpect(jsonPath("$.[*].company").value(hasItem(DEFAULT_COMPANY)))
            .andExpect(jsonPath("$.[*].companySite").value(hasItem(DEFAULT_COMPANY_SITE)))
            .andExpect(jsonPath("$.[*].role").value(hasItem(DEFAULT_ROLE)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION.doubleValue())))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)))
            .andExpect(jsonPath("$.[*].tasks").value(hasItem(DEFAULT_TASKS)));
    }

    @Test
    @Transactional
    void getExperience() throws Exception {
        // Initialize the database
        experienceRepository.saveAndFlush(experience);

        // Get the experience
        restExperienceMockMvc
            .perform(get(ENTITY_API_URL_ID, experience.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(experience.getId().intValue()))
            .andExpect(jsonPath("$.company").value(DEFAULT_COMPANY))
            .andExpect(jsonPath("$.companySite").value(DEFAULT_COMPANY_SITE))
            .andExpect(jsonPath("$.role").value(DEFAULT_ROLE))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION.doubleValue()))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION))
            .andExpect(jsonPath("$.tasks").value(DEFAULT_TASKS));
    }

    @Test
    @Transactional
    void getExperiencesByIdFiltering() throws Exception {
        // Initialize the database
        experienceRepository.saveAndFlush(experience);

        Long id = experience.getId();

        defaultExperienceShouldBeFound("id.equals=" + id);
        defaultExperienceShouldNotBeFound("id.notEquals=" + id);

        defaultExperienceShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultExperienceShouldNotBeFound("id.greaterThan=" + id);

        defaultExperienceShouldBeFound("id.lessThanOrEqual=" + id);
        defaultExperienceShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllExperiencesByCompanyIsEqualToSomething() throws Exception {
        // Initialize the database
        experienceRepository.saveAndFlush(experience);

        // Get all the experienceList where company equals to DEFAULT_COMPANY
        defaultExperienceShouldBeFound("company.equals=" + DEFAULT_COMPANY);

        // Get all the experienceList where company equals to UPDATED_COMPANY
        defaultExperienceShouldNotBeFound("company.equals=" + UPDATED_COMPANY);
    }

    @Test
    @Transactional
    void getAllExperiencesByCompanyIsInShouldWork() throws Exception {
        // Initialize the database
        experienceRepository.saveAndFlush(experience);

        // Get all the experienceList where company in DEFAULT_COMPANY or UPDATED_COMPANY
        defaultExperienceShouldBeFound("company.in=" + DEFAULT_COMPANY + "," + UPDATED_COMPANY);

        // Get all the experienceList where company equals to UPDATED_COMPANY
        defaultExperienceShouldNotBeFound("company.in=" + UPDATED_COMPANY);
    }

    @Test
    @Transactional
    void getAllExperiencesByCompanyIsNullOrNotNull() throws Exception {
        // Initialize the database
        experienceRepository.saveAndFlush(experience);

        // Get all the experienceList where company is not null
        defaultExperienceShouldBeFound("company.specified=true");

        // Get all the experienceList where company is null
        defaultExperienceShouldNotBeFound("company.specified=false");
    }

    @Test
    @Transactional
    void getAllExperiencesByCompanyContainsSomething() throws Exception {
        // Initialize the database
        experienceRepository.saveAndFlush(experience);

        // Get all the experienceList where company contains DEFAULT_COMPANY
        defaultExperienceShouldBeFound("company.contains=" + DEFAULT_COMPANY);

        // Get all the experienceList where company contains UPDATED_COMPANY
        defaultExperienceShouldNotBeFound("company.contains=" + UPDATED_COMPANY);
    }

    @Test
    @Transactional
    void getAllExperiencesByCompanyNotContainsSomething() throws Exception {
        // Initialize the database
        experienceRepository.saveAndFlush(experience);

        // Get all the experienceList where company does not contain DEFAULT_COMPANY
        defaultExperienceShouldNotBeFound("company.doesNotContain=" + DEFAULT_COMPANY);

        // Get all the experienceList where company does not contain UPDATED_COMPANY
        defaultExperienceShouldBeFound("company.doesNotContain=" + UPDATED_COMPANY);
    }

    @Test
    @Transactional
    void getAllExperiencesByCompanySiteIsEqualToSomething() throws Exception {
        // Initialize the database
        experienceRepository.saveAndFlush(experience);

        // Get all the experienceList where companySite equals to DEFAULT_COMPANY_SITE
        defaultExperienceShouldBeFound("companySite.equals=" + DEFAULT_COMPANY_SITE);

        // Get all the experienceList where companySite equals to UPDATED_COMPANY_SITE
        defaultExperienceShouldNotBeFound("companySite.equals=" + UPDATED_COMPANY_SITE);
    }

    @Test
    @Transactional
    void getAllExperiencesByCompanySiteIsInShouldWork() throws Exception {
        // Initialize the database
        experienceRepository.saveAndFlush(experience);

        // Get all the experienceList where companySite in DEFAULT_COMPANY_SITE or UPDATED_COMPANY_SITE
        defaultExperienceShouldBeFound("companySite.in=" + DEFAULT_COMPANY_SITE + "," + UPDATED_COMPANY_SITE);

        // Get all the experienceList where companySite equals to UPDATED_COMPANY_SITE
        defaultExperienceShouldNotBeFound("companySite.in=" + UPDATED_COMPANY_SITE);
    }

    @Test
    @Transactional
    void getAllExperiencesByCompanySiteIsNullOrNotNull() throws Exception {
        // Initialize the database
        experienceRepository.saveAndFlush(experience);

        // Get all the experienceList where companySite is not null
        defaultExperienceShouldBeFound("companySite.specified=true");

        // Get all the experienceList where companySite is null
        defaultExperienceShouldNotBeFound("companySite.specified=false");
    }

    @Test
    @Transactional
    void getAllExperiencesByCompanySiteContainsSomething() throws Exception {
        // Initialize the database
        experienceRepository.saveAndFlush(experience);

        // Get all the experienceList where companySite contains DEFAULT_COMPANY_SITE
        defaultExperienceShouldBeFound("companySite.contains=" + DEFAULT_COMPANY_SITE);

        // Get all the experienceList where companySite contains UPDATED_COMPANY_SITE
        defaultExperienceShouldNotBeFound("companySite.contains=" + UPDATED_COMPANY_SITE);
    }

    @Test
    @Transactional
    void getAllExperiencesByCompanySiteNotContainsSomething() throws Exception {
        // Initialize the database
        experienceRepository.saveAndFlush(experience);

        // Get all the experienceList where companySite does not contain DEFAULT_COMPANY_SITE
        defaultExperienceShouldNotBeFound("companySite.doesNotContain=" + DEFAULT_COMPANY_SITE);

        // Get all the experienceList where companySite does not contain UPDATED_COMPANY_SITE
        defaultExperienceShouldBeFound("companySite.doesNotContain=" + UPDATED_COMPANY_SITE);
    }

    @Test
    @Transactional
    void getAllExperiencesByRoleIsEqualToSomething() throws Exception {
        // Initialize the database
        experienceRepository.saveAndFlush(experience);

        // Get all the experienceList where role equals to DEFAULT_ROLE
        defaultExperienceShouldBeFound("role.equals=" + DEFAULT_ROLE);

        // Get all the experienceList where role equals to UPDATED_ROLE
        defaultExperienceShouldNotBeFound("role.equals=" + UPDATED_ROLE);
    }

    @Test
    @Transactional
    void getAllExperiencesByRoleIsInShouldWork() throws Exception {
        // Initialize the database
        experienceRepository.saveAndFlush(experience);

        // Get all the experienceList where role in DEFAULT_ROLE or UPDATED_ROLE
        defaultExperienceShouldBeFound("role.in=" + DEFAULT_ROLE + "," + UPDATED_ROLE);

        // Get all the experienceList where role equals to UPDATED_ROLE
        defaultExperienceShouldNotBeFound("role.in=" + UPDATED_ROLE);
    }

    @Test
    @Transactional
    void getAllExperiencesByRoleIsNullOrNotNull() throws Exception {
        // Initialize the database
        experienceRepository.saveAndFlush(experience);

        // Get all the experienceList where role is not null
        defaultExperienceShouldBeFound("role.specified=true");

        // Get all the experienceList where role is null
        defaultExperienceShouldNotBeFound("role.specified=false");
    }

    @Test
    @Transactional
    void getAllExperiencesByRoleContainsSomething() throws Exception {
        // Initialize the database
        experienceRepository.saveAndFlush(experience);

        // Get all the experienceList where role contains DEFAULT_ROLE
        defaultExperienceShouldBeFound("role.contains=" + DEFAULT_ROLE);

        // Get all the experienceList where role contains UPDATED_ROLE
        defaultExperienceShouldNotBeFound("role.contains=" + UPDATED_ROLE);
    }

    @Test
    @Transactional
    void getAllExperiencesByRoleNotContainsSomething() throws Exception {
        // Initialize the database
        experienceRepository.saveAndFlush(experience);

        // Get all the experienceList where role does not contain DEFAULT_ROLE
        defaultExperienceShouldNotBeFound("role.doesNotContain=" + DEFAULT_ROLE);

        // Get all the experienceList where role does not contain UPDATED_ROLE
        defaultExperienceShouldBeFound("role.doesNotContain=" + UPDATED_ROLE);
    }

    @Test
    @Transactional
    void getAllExperiencesByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        experienceRepository.saveAndFlush(experience);

        // Get all the experienceList where startDate equals to DEFAULT_START_DATE
        defaultExperienceShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);

        // Get all the experienceList where startDate equals to UPDATED_START_DATE
        defaultExperienceShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllExperiencesByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        experienceRepository.saveAndFlush(experience);

        // Get all the experienceList where startDate in DEFAULT_START_DATE or UPDATED_START_DATE
        defaultExperienceShouldBeFound("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

        // Get all the experienceList where startDate equals to UPDATED_START_DATE
        defaultExperienceShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllExperiencesByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        experienceRepository.saveAndFlush(experience);

        // Get all the experienceList where startDate is not null
        defaultExperienceShouldBeFound("startDate.specified=true");

        // Get all the experienceList where startDate is null
        defaultExperienceShouldNotBeFound("startDate.specified=false");
    }

    @Test
    @Transactional
    void getAllExperiencesByStartDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        experienceRepository.saveAndFlush(experience);

        // Get all the experienceList where startDate is greater than or equal to DEFAULT_START_DATE
        defaultExperienceShouldBeFound("startDate.greaterThanOrEqual=" + DEFAULT_START_DATE);

        // Get all the experienceList where startDate is greater than or equal to UPDATED_START_DATE
        defaultExperienceShouldNotBeFound("startDate.greaterThanOrEqual=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllExperiencesByStartDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        experienceRepository.saveAndFlush(experience);

        // Get all the experienceList where startDate is less than or equal to DEFAULT_START_DATE
        defaultExperienceShouldBeFound("startDate.lessThanOrEqual=" + DEFAULT_START_DATE);

        // Get all the experienceList where startDate is less than or equal to SMALLER_START_DATE
        defaultExperienceShouldNotBeFound("startDate.lessThanOrEqual=" + SMALLER_START_DATE);
    }

    @Test
    @Transactional
    void getAllExperiencesByStartDateIsLessThanSomething() throws Exception {
        // Initialize the database
        experienceRepository.saveAndFlush(experience);

        // Get all the experienceList where startDate is less than DEFAULT_START_DATE
        defaultExperienceShouldNotBeFound("startDate.lessThan=" + DEFAULT_START_DATE);

        // Get all the experienceList where startDate is less than UPDATED_START_DATE
        defaultExperienceShouldBeFound("startDate.lessThan=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllExperiencesByStartDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        experienceRepository.saveAndFlush(experience);

        // Get all the experienceList where startDate is greater than DEFAULT_START_DATE
        defaultExperienceShouldNotBeFound("startDate.greaterThan=" + DEFAULT_START_DATE);

        // Get all the experienceList where startDate is greater than SMALLER_START_DATE
        defaultExperienceShouldBeFound("startDate.greaterThan=" + SMALLER_START_DATE);
    }

    @Test
    @Transactional
    void getAllExperiencesByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        experienceRepository.saveAndFlush(experience);

        // Get all the experienceList where endDate equals to DEFAULT_END_DATE
        defaultExperienceShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the experienceList where endDate equals to UPDATED_END_DATE
        defaultExperienceShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllExperiencesByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        experienceRepository.saveAndFlush(experience);

        // Get all the experienceList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultExperienceShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the experienceList where endDate equals to UPDATED_END_DATE
        defaultExperienceShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllExperiencesByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        experienceRepository.saveAndFlush(experience);

        // Get all the experienceList where endDate is not null
        defaultExperienceShouldBeFound("endDate.specified=true");

        // Get all the experienceList where endDate is null
        defaultExperienceShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    void getAllExperiencesByEndDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        experienceRepository.saveAndFlush(experience);

        // Get all the experienceList where endDate is greater than or equal to DEFAULT_END_DATE
        defaultExperienceShouldBeFound("endDate.greaterThanOrEqual=" + DEFAULT_END_DATE);

        // Get all the experienceList where endDate is greater than or equal to UPDATED_END_DATE
        defaultExperienceShouldNotBeFound("endDate.greaterThanOrEqual=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllExperiencesByEndDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        experienceRepository.saveAndFlush(experience);

        // Get all the experienceList where endDate is less than or equal to DEFAULT_END_DATE
        defaultExperienceShouldBeFound("endDate.lessThanOrEqual=" + DEFAULT_END_DATE);

        // Get all the experienceList where endDate is less than or equal to SMALLER_END_DATE
        defaultExperienceShouldNotBeFound("endDate.lessThanOrEqual=" + SMALLER_END_DATE);
    }

    @Test
    @Transactional
    void getAllExperiencesByEndDateIsLessThanSomething() throws Exception {
        // Initialize the database
        experienceRepository.saveAndFlush(experience);

        // Get all the experienceList where endDate is less than DEFAULT_END_DATE
        defaultExperienceShouldNotBeFound("endDate.lessThan=" + DEFAULT_END_DATE);

        // Get all the experienceList where endDate is less than UPDATED_END_DATE
        defaultExperienceShouldBeFound("endDate.lessThan=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllExperiencesByEndDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        experienceRepository.saveAndFlush(experience);

        // Get all the experienceList where endDate is greater than DEFAULT_END_DATE
        defaultExperienceShouldNotBeFound("endDate.greaterThan=" + DEFAULT_END_DATE);

        // Get all the experienceList where endDate is greater than SMALLER_END_DATE
        defaultExperienceShouldBeFound("endDate.greaterThan=" + SMALLER_END_DATE);
    }

    @Test
    @Transactional
    void getAllExperiencesByDurationIsEqualToSomething() throws Exception {
        // Initialize the database
        experienceRepository.saveAndFlush(experience);

        // Get all the experienceList where duration equals to DEFAULT_DURATION
        defaultExperienceShouldBeFound("duration.equals=" + DEFAULT_DURATION);

        // Get all the experienceList where duration equals to UPDATED_DURATION
        defaultExperienceShouldNotBeFound("duration.equals=" + UPDATED_DURATION);
    }

    @Test
    @Transactional
    void getAllExperiencesByDurationIsInShouldWork() throws Exception {
        // Initialize the database
        experienceRepository.saveAndFlush(experience);

        // Get all the experienceList where duration in DEFAULT_DURATION or UPDATED_DURATION
        defaultExperienceShouldBeFound("duration.in=" + DEFAULT_DURATION + "," + UPDATED_DURATION);

        // Get all the experienceList where duration equals to UPDATED_DURATION
        defaultExperienceShouldNotBeFound("duration.in=" + UPDATED_DURATION);
    }

    @Test
    @Transactional
    void getAllExperiencesByDurationIsNullOrNotNull() throws Exception {
        // Initialize the database
        experienceRepository.saveAndFlush(experience);

        // Get all the experienceList where duration is not null
        defaultExperienceShouldBeFound("duration.specified=true");

        // Get all the experienceList where duration is null
        defaultExperienceShouldNotBeFound("duration.specified=false");
    }

    @Test
    @Transactional
    void getAllExperiencesByDurationIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        experienceRepository.saveAndFlush(experience);

        // Get all the experienceList where duration is greater than or equal to DEFAULT_DURATION
        defaultExperienceShouldBeFound("duration.greaterThanOrEqual=" + DEFAULT_DURATION);

        // Get all the experienceList where duration is greater than or equal to UPDATED_DURATION
        defaultExperienceShouldNotBeFound("duration.greaterThanOrEqual=" + UPDATED_DURATION);
    }

    @Test
    @Transactional
    void getAllExperiencesByDurationIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        experienceRepository.saveAndFlush(experience);

        // Get all the experienceList where duration is less than or equal to DEFAULT_DURATION
        defaultExperienceShouldBeFound("duration.lessThanOrEqual=" + DEFAULT_DURATION);

        // Get all the experienceList where duration is less than or equal to SMALLER_DURATION
        defaultExperienceShouldNotBeFound("duration.lessThanOrEqual=" + SMALLER_DURATION);
    }

    @Test
    @Transactional
    void getAllExperiencesByDurationIsLessThanSomething() throws Exception {
        // Initialize the database
        experienceRepository.saveAndFlush(experience);

        // Get all the experienceList where duration is less than DEFAULT_DURATION
        defaultExperienceShouldNotBeFound("duration.lessThan=" + DEFAULT_DURATION);

        // Get all the experienceList where duration is less than UPDATED_DURATION
        defaultExperienceShouldBeFound("duration.lessThan=" + UPDATED_DURATION);
    }

    @Test
    @Transactional
    void getAllExperiencesByDurationIsGreaterThanSomething() throws Exception {
        // Initialize the database
        experienceRepository.saveAndFlush(experience);

        // Get all the experienceList where duration is greater than DEFAULT_DURATION
        defaultExperienceShouldNotBeFound("duration.greaterThan=" + DEFAULT_DURATION);

        // Get all the experienceList where duration is greater than SMALLER_DURATION
        defaultExperienceShouldBeFound("duration.greaterThan=" + SMALLER_DURATION);
    }

    @Test
    @Transactional
    void getAllExperiencesByLocationIsEqualToSomething() throws Exception {
        // Initialize the database
        experienceRepository.saveAndFlush(experience);

        // Get all the experienceList where location equals to DEFAULT_LOCATION
        defaultExperienceShouldBeFound("location.equals=" + DEFAULT_LOCATION);

        // Get all the experienceList where location equals to UPDATED_LOCATION
        defaultExperienceShouldNotBeFound("location.equals=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    void getAllExperiencesByLocationIsInShouldWork() throws Exception {
        // Initialize the database
        experienceRepository.saveAndFlush(experience);

        // Get all the experienceList where location in DEFAULT_LOCATION or UPDATED_LOCATION
        defaultExperienceShouldBeFound("location.in=" + DEFAULT_LOCATION + "," + UPDATED_LOCATION);

        // Get all the experienceList where location equals to UPDATED_LOCATION
        defaultExperienceShouldNotBeFound("location.in=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    void getAllExperiencesByLocationIsNullOrNotNull() throws Exception {
        // Initialize the database
        experienceRepository.saveAndFlush(experience);

        // Get all the experienceList where location is not null
        defaultExperienceShouldBeFound("location.specified=true");

        // Get all the experienceList where location is null
        defaultExperienceShouldNotBeFound("location.specified=false");
    }

    @Test
    @Transactional
    void getAllExperiencesByLocationContainsSomething() throws Exception {
        // Initialize the database
        experienceRepository.saveAndFlush(experience);

        // Get all the experienceList where location contains DEFAULT_LOCATION
        defaultExperienceShouldBeFound("location.contains=" + DEFAULT_LOCATION);

        // Get all the experienceList where location contains UPDATED_LOCATION
        defaultExperienceShouldNotBeFound("location.contains=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    void getAllExperiencesByLocationNotContainsSomething() throws Exception {
        // Initialize the database
        experienceRepository.saveAndFlush(experience);

        // Get all the experienceList where location does not contain DEFAULT_LOCATION
        defaultExperienceShouldNotBeFound("location.doesNotContain=" + DEFAULT_LOCATION);

        // Get all the experienceList where location does not contain UPDATED_LOCATION
        defaultExperienceShouldBeFound("location.doesNotContain=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    void getAllExperiencesByTasksIsEqualToSomething() throws Exception {
        // Initialize the database
        experienceRepository.saveAndFlush(experience);

        // Get all the experienceList where tasks equals to DEFAULT_TASKS
        defaultExperienceShouldBeFound("tasks.equals=" + DEFAULT_TASKS);

        // Get all the experienceList where tasks equals to UPDATED_TASKS
        defaultExperienceShouldNotBeFound("tasks.equals=" + UPDATED_TASKS);
    }

    @Test
    @Transactional
    void getAllExperiencesByTasksIsInShouldWork() throws Exception {
        // Initialize the database
        experienceRepository.saveAndFlush(experience);

        // Get all the experienceList where tasks in DEFAULT_TASKS or UPDATED_TASKS
        defaultExperienceShouldBeFound("tasks.in=" + DEFAULT_TASKS + "," + UPDATED_TASKS);

        // Get all the experienceList where tasks equals to UPDATED_TASKS
        defaultExperienceShouldNotBeFound("tasks.in=" + UPDATED_TASKS);
    }

    @Test
    @Transactional
    void getAllExperiencesByTasksIsNullOrNotNull() throws Exception {
        // Initialize the database
        experienceRepository.saveAndFlush(experience);

        // Get all the experienceList where tasks is not null
        defaultExperienceShouldBeFound("tasks.specified=true");

        // Get all the experienceList where tasks is null
        defaultExperienceShouldNotBeFound("tasks.specified=false");
    }

    @Test
    @Transactional
    void getAllExperiencesByTasksContainsSomething() throws Exception {
        // Initialize the database
        experienceRepository.saveAndFlush(experience);

        // Get all the experienceList where tasks contains DEFAULT_TASKS
        defaultExperienceShouldBeFound("tasks.contains=" + DEFAULT_TASKS);

        // Get all the experienceList where tasks contains UPDATED_TASKS
        defaultExperienceShouldNotBeFound("tasks.contains=" + UPDATED_TASKS);
    }

    @Test
    @Transactional
    void getAllExperiencesByTasksNotContainsSomething() throws Exception {
        // Initialize the database
        experienceRepository.saveAndFlush(experience);

        // Get all the experienceList where tasks does not contain DEFAULT_TASKS
        defaultExperienceShouldNotBeFound("tasks.doesNotContain=" + DEFAULT_TASKS);

        // Get all the experienceList where tasks does not contain UPDATED_TASKS
        defaultExperienceShouldBeFound("tasks.doesNotContain=" + UPDATED_TASKS);
    }

    @Test
    @Transactional
    void getAllExperiencesByToolsIsEqualToSomething() throws Exception {
        Tool tools;
        if (TestUtil.findAll(em, Tool.class).isEmpty()) {
            experienceRepository.saveAndFlush(experience);
            tools = ToolResourceIT.createEntity(em);
        } else {
            tools = TestUtil.findAll(em, Tool.class).get(0);
        }
        em.persist(tools);
        em.flush();
        experience.addTools(tools);
        experienceRepository.saveAndFlush(experience);
        Long toolsId = tools.getId();
        // Get all the experienceList where tools equals to toolsId
        defaultExperienceShouldBeFound("toolsId.equals=" + toolsId);

        // Get all the experienceList where tools equals to (toolsId + 1)
        defaultExperienceShouldNotBeFound("toolsId.equals=" + (toolsId + 1));
    }

    @Test
    @Transactional
    void getAllExperiencesByExperienceCandidateIsEqualToSomething() throws Exception {
        Candidate experienceCandidate;
        if (TestUtil.findAll(em, Candidate.class).isEmpty()) {
            experienceRepository.saveAndFlush(experience);
            experienceCandidate = CandidateResourceIT.createEntity(em);
        } else {
            experienceCandidate = TestUtil.findAll(em, Candidate.class).get(0);
        }
        em.persist(experienceCandidate);
        em.flush();
        experience.setExperienceCandidate(experienceCandidate);
        experienceRepository.saveAndFlush(experience);
        Long experienceCandidateId = experienceCandidate.getId();
        // Get all the experienceList where experienceCandidate equals to experienceCandidateId
        defaultExperienceShouldBeFound("experienceCandidateId.equals=" + experienceCandidateId);

        // Get all the experienceList where experienceCandidate equals to (experienceCandidateId + 1)
        defaultExperienceShouldNotBeFound("experienceCandidateId.equals=" + (experienceCandidateId + 1));
    }

    @Test
    @Transactional
    void getAllExperiencesByCandidateIsEqualToSomething() throws Exception {
        Candidate candidate;
        if (TestUtil.findAll(em, Candidate.class).isEmpty()) {
            experienceRepository.saveAndFlush(experience);
            candidate = CandidateResourceIT.createEntity(em);
        } else {
            candidate = TestUtil.findAll(em, Candidate.class).get(0);
        }
        em.persist(candidate);
        em.flush();
        experience.setCandidate(candidate);
        experienceRepository.saveAndFlush(experience);
        Long candidateId = candidate.getId();
        // Get all the experienceList where candidate equals to candidateId
        defaultExperienceShouldBeFound("candidateId.equals=" + candidateId);

        // Get all the experienceList where candidate equals to (candidateId + 1)
        defaultExperienceShouldNotBeFound("candidateId.equals=" + (candidateId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultExperienceShouldBeFound(String filter) throws Exception {
        restExperienceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(experience.getId().intValue())))
            .andExpect(jsonPath("$.[*].company").value(hasItem(DEFAULT_COMPANY)))
            .andExpect(jsonPath("$.[*].companySite").value(hasItem(DEFAULT_COMPANY_SITE)))
            .andExpect(jsonPath("$.[*].role").value(hasItem(DEFAULT_ROLE)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION.doubleValue())))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)))
            .andExpect(jsonPath("$.[*].tasks").value(hasItem(DEFAULT_TASKS)));

        // Check, that the count call also returns 1
        restExperienceMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultExperienceShouldNotBeFound(String filter) throws Exception {
        restExperienceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restExperienceMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingExperience() throws Exception {
        // Get the experience
        restExperienceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingExperience() throws Exception {
        // Initialize the database
        experienceRepository.saveAndFlush(experience);

        int databaseSizeBeforeUpdate = experienceRepository.findAll().size();

        // Update the experience
        Experience updatedExperience = experienceRepository.findById(experience.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedExperience are not directly saved in db
        em.detach(updatedExperience);
        updatedExperience
            .company(UPDATED_COMPANY)
            .companySite(UPDATED_COMPANY_SITE)
            .role(UPDATED_ROLE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .duration(UPDATED_DURATION)
            .location(UPDATED_LOCATION)
            .tasks(UPDATED_TASKS);
        ExperienceDTO experienceDTO = experienceMapper.toDto(updatedExperience);

        restExperienceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, experienceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(experienceDTO))
            )
            .andExpect(status().isOk());

        // Validate the Experience in the database
        List<Experience> experienceList = experienceRepository.findAll();
        assertThat(experienceList).hasSize(databaseSizeBeforeUpdate);
        Experience testExperience = experienceList.get(experienceList.size() - 1);
        assertThat(testExperience.getCompany()).isEqualTo(UPDATED_COMPANY);
        assertThat(testExperience.getCompanySite()).isEqualTo(UPDATED_COMPANY_SITE);
        assertThat(testExperience.getRole()).isEqualTo(UPDATED_ROLE);
        assertThat(testExperience.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testExperience.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testExperience.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testExperience.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testExperience.getTasks()).isEqualTo(UPDATED_TASKS);
    }

    @Test
    @Transactional
    void putNonExistingExperience() throws Exception {
        int databaseSizeBeforeUpdate = experienceRepository.findAll().size();
        experience.setId(longCount.incrementAndGet());

        // Create the Experience
        ExperienceDTO experienceDTO = experienceMapper.toDto(experience);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExperienceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, experienceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(experienceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Experience in the database
        List<Experience> experienceList = experienceRepository.findAll();
        assertThat(experienceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchExperience() throws Exception {
        int databaseSizeBeforeUpdate = experienceRepository.findAll().size();
        experience.setId(longCount.incrementAndGet());

        // Create the Experience
        ExperienceDTO experienceDTO = experienceMapper.toDto(experience);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExperienceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(experienceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Experience in the database
        List<Experience> experienceList = experienceRepository.findAll();
        assertThat(experienceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamExperience() throws Exception {
        int databaseSizeBeforeUpdate = experienceRepository.findAll().size();
        experience.setId(longCount.incrementAndGet());

        // Create the Experience
        ExperienceDTO experienceDTO = experienceMapper.toDto(experience);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExperienceMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(experienceDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Experience in the database
        List<Experience> experienceList = experienceRepository.findAll();
        assertThat(experienceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateExperienceWithPatch() throws Exception {
        // Initialize the database
        experienceRepository.saveAndFlush(experience);

        int databaseSizeBeforeUpdate = experienceRepository.findAll().size();

        // Update the experience using partial update
        Experience partialUpdatedExperience = new Experience();
        partialUpdatedExperience.setId(experience.getId());

        partialUpdatedExperience.companySite(UPDATED_COMPANY_SITE).role(UPDATED_ROLE);

        restExperienceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedExperience.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedExperience))
            )
            .andExpect(status().isOk());

        // Validate the Experience in the database
        List<Experience> experienceList = experienceRepository.findAll();
        assertThat(experienceList).hasSize(databaseSizeBeforeUpdate);
        Experience testExperience = experienceList.get(experienceList.size() - 1);
        assertThat(testExperience.getCompany()).isEqualTo(DEFAULT_COMPANY);
        assertThat(testExperience.getCompanySite()).isEqualTo(UPDATED_COMPANY_SITE);
        assertThat(testExperience.getRole()).isEqualTo(UPDATED_ROLE);
        assertThat(testExperience.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testExperience.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testExperience.getDuration()).isEqualTo(DEFAULT_DURATION);
        assertThat(testExperience.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testExperience.getTasks()).isEqualTo(DEFAULT_TASKS);
    }

    @Test
    @Transactional
    void fullUpdateExperienceWithPatch() throws Exception {
        // Initialize the database
        experienceRepository.saveAndFlush(experience);

        int databaseSizeBeforeUpdate = experienceRepository.findAll().size();

        // Update the experience using partial update
        Experience partialUpdatedExperience = new Experience();
        partialUpdatedExperience.setId(experience.getId());

        partialUpdatedExperience
            .company(UPDATED_COMPANY)
            .companySite(UPDATED_COMPANY_SITE)
            .role(UPDATED_ROLE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .duration(UPDATED_DURATION)
            .location(UPDATED_LOCATION)
            .tasks(UPDATED_TASKS);

        restExperienceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedExperience.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedExperience))
            )
            .andExpect(status().isOk());

        // Validate the Experience in the database
        List<Experience> experienceList = experienceRepository.findAll();
        assertThat(experienceList).hasSize(databaseSizeBeforeUpdate);
        Experience testExperience = experienceList.get(experienceList.size() - 1);
        assertThat(testExperience.getCompany()).isEqualTo(UPDATED_COMPANY);
        assertThat(testExperience.getCompanySite()).isEqualTo(UPDATED_COMPANY_SITE);
        assertThat(testExperience.getRole()).isEqualTo(UPDATED_ROLE);
        assertThat(testExperience.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testExperience.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testExperience.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testExperience.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testExperience.getTasks()).isEqualTo(UPDATED_TASKS);
    }

    @Test
    @Transactional
    void patchNonExistingExperience() throws Exception {
        int databaseSizeBeforeUpdate = experienceRepository.findAll().size();
        experience.setId(longCount.incrementAndGet());

        // Create the Experience
        ExperienceDTO experienceDTO = experienceMapper.toDto(experience);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExperienceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, experienceDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(experienceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Experience in the database
        List<Experience> experienceList = experienceRepository.findAll();
        assertThat(experienceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchExperience() throws Exception {
        int databaseSizeBeforeUpdate = experienceRepository.findAll().size();
        experience.setId(longCount.incrementAndGet());

        // Create the Experience
        ExperienceDTO experienceDTO = experienceMapper.toDto(experience);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExperienceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(experienceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Experience in the database
        List<Experience> experienceList = experienceRepository.findAll();
        assertThat(experienceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamExperience() throws Exception {
        int databaseSizeBeforeUpdate = experienceRepository.findAll().size();
        experience.setId(longCount.incrementAndGet());

        // Create the Experience
        ExperienceDTO experienceDTO = experienceMapper.toDto(experience);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExperienceMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(experienceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Experience in the database
        List<Experience> experienceList = experienceRepository.findAll();
        assertThat(experienceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteExperience() throws Exception {
        // Initialize the database
        experienceRepository.saveAndFlush(experience);

        int databaseSizeBeforeDelete = experienceRepository.findAll().size();

        // Delete the experience
        restExperienceMockMvc
            .perform(delete(ENTITY_API_URL_ID, experience.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Experience> experienceList = experienceRepository.findAll();
        assertThat(experienceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
