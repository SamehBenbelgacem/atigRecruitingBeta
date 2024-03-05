package atig.recruiting.beta.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import atig.recruiting.beta.IntegrationTest;
import atig.recruiting.beta.domain.Category;
import atig.recruiting.beta.domain.Company;
import atig.recruiting.beta.domain.Desider;
import atig.recruiting.beta.domain.Email;
import atig.recruiting.beta.domain.Note;
import atig.recruiting.beta.domain.Notification;
import atig.recruiting.beta.domain.Offer;
import atig.recruiting.beta.domain.Process;
import atig.recruiting.beta.domain.ProcessStep;
import atig.recruiting.beta.domain.SubCategory;
import atig.recruiting.beta.domain.Tag;
import atig.recruiting.beta.repository.CompanyRepository;
import atig.recruiting.beta.service.CompanyService;
import atig.recruiting.beta.service.dto.CompanyDTO;
import atig.recruiting.beta.service.mapper.CompanyMapper;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link CompanyResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CompanyResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SPECIALITY = "AAAAAAAAAA";
    private static final String UPDATED_SPECIALITY = "BBBBBBBBBB";

    private static final byte[] DEFAULT_LOGO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_LOGO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_LOGO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_LOGO_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_WEBSITE = "AAAAAAAAAA";
    private static final String UPDATED_WEBSITE = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_INFO_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_INFO_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FIRST_CONTACT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FIRST_CONTACT_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FIRST_CONTACT_DATE = LocalDate.ofEpochDay(-1L);

    private static final String ENTITY_API_URL = "/api/companies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CompanyRepository companyRepository;

    @Mock
    private CompanyRepository companyRepositoryMock;

    @Autowired
    private CompanyMapper companyMapper;

    @Mock
    private CompanyService companyServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCompanyMockMvc;

    private Company company;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Company createEntity(EntityManager em) {
        Company company = new Company()
            .name(DEFAULT_NAME)
            .speciality(DEFAULT_SPECIALITY)
            .logo(DEFAULT_LOGO)
            .logoContentType(DEFAULT_LOGO_CONTENT_TYPE)
            .description(DEFAULT_DESCRIPTION)
            .website(DEFAULT_WEBSITE)
            .location(DEFAULT_LOCATION)
            .infoEmail(DEFAULT_INFO_EMAIL)
            .phone(DEFAULT_PHONE)
            .firstContactDate(DEFAULT_FIRST_CONTACT_DATE);
        return company;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Company createUpdatedEntity(EntityManager em) {
        Company company = new Company()
            .name(UPDATED_NAME)
            .speciality(UPDATED_SPECIALITY)
            .logo(UPDATED_LOGO)
            .logoContentType(UPDATED_LOGO_CONTENT_TYPE)
            .description(UPDATED_DESCRIPTION)
            .website(UPDATED_WEBSITE)
            .location(UPDATED_LOCATION)
            .infoEmail(UPDATED_INFO_EMAIL)
            .phone(UPDATED_PHONE)
            .firstContactDate(UPDATED_FIRST_CONTACT_DATE);
        return company;
    }

    @BeforeEach
    public void initTest() {
        company = createEntity(em);
    }

    @Test
    @Transactional
    void createCompany() throws Exception {
        int databaseSizeBeforeCreate = companyRepository.findAll().size();
        // Create the Company
        CompanyDTO companyDTO = companyMapper.toDto(company);
        restCompanyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companyDTO)))
            .andExpect(status().isCreated());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeCreate + 1);
        Company testCompany = companyList.get(companyList.size() - 1);
        assertThat(testCompany.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCompany.getSpeciality()).isEqualTo(DEFAULT_SPECIALITY);
        assertThat(testCompany.getLogo()).isEqualTo(DEFAULT_LOGO);
        assertThat(testCompany.getLogoContentType()).isEqualTo(DEFAULT_LOGO_CONTENT_TYPE);
        assertThat(testCompany.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCompany.getWebsite()).isEqualTo(DEFAULT_WEBSITE);
        assertThat(testCompany.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testCompany.getInfoEmail()).isEqualTo(DEFAULT_INFO_EMAIL);
        assertThat(testCompany.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testCompany.getFirstContactDate()).isEqualTo(DEFAULT_FIRST_CONTACT_DATE);
    }

    @Test
    @Transactional
    void createCompanyWithExistingId() throws Exception {
        // Create the Company with an existing ID
        company.setId(1L);
        CompanyDTO companyDTO = companyMapper.toDto(company);

        int databaseSizeBeforeCreate = companyRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCompanies() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList
        restCompanyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(company.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].speciality").value(hasItem(DEFAULT_SPECIALITY)))
            .andExpect(jsonPath("$.[*].logoContentType").value(hasItem(DEFAULT_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].logo").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_LOGO))))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].website").value(hasItem(DEFAULT_WEBSITE)))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)))
            .andExpect(jsonPath("$.[*].infoEmail").value(hasItem(DEFAULT_INFO_EMAIL)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].firstContactDate").value(hasItem(DEFAULT_FIRST_CONTACT_DATE.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCompaniesWithEagerRelationshipsIsEnabled() throws Exception {
        when(companyServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCompanyMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(companyServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCompaniesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(companyServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCompanyMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(companyRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getCompany() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get the company
        restCompanyMockMvc
            .perform(get(ENTITY_API_URL_ID, company.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(company.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.speciality").value(DEFAULT_SPECIALITY))
            .andExpect(jsonPath("$.logoContentType").value(DEFAULT_LOGO_CONTENT_TYPE))
            .andExpect(jsonPath("$.logo").value(Base64.getEncoder().encodeToString(DEFAULT_LOGO)))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.website").value(DEFAULT_WEBSITE))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION))
            .andExpect(jsonPath("$.infoEmail").value(DEFAULT_INFO_EMAIL))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.firstContactDate").value(DEFAULT_FIRST_CONTACT_DATE.toString()));
    }

    @Test
    @Transactional
    void getCompaniesByIdFiltering() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        Long id = company.getId();

        defaultCompanyShouldBeFound("id.equals=" + id);
        defaultCompanyShouldNotBeFound("id.notEquals=" + id);

        defaultCompanyShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCompanyShouldNotBeFound("id.greaterThan=" + id);

        defaultCompanyShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCompanyShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCompaniesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where name equals to DEFAULT_NAME
        defaultCompanyShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the companyList where name equals to UPDATED_NAME
        defaultCompanyShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCompaniesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCompanyShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the companyList where name equals to UPDATED_NAME
        defaultCompanyShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCompaniesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where name is not null
        defaultCompanyShouldBeFound("name.specified=true");

        // Get all the companyList where name is null
        defaultCompanyShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllCompaniesByNameContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where name contains DEFAULT_NAME
        defaultCompanyShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the companyList where name contains UPDATED_NAME
        defaultCompanyShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCompaniesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where name does not contain DEFAULT_NAME
        defaultCompanyShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the companyList where name does not contain UPDATED_NAME
        defaultCompanyShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCompaniesBySpecialityIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where speciality equals to DEFAULT_SPECIALITY
        defaultCompanyShouldBeFound("speciality.equals=" + DEFAULT_SPECIALITY);

        // Get all the companyList where speciality equals to UPDATED_SPECIALITY
        defaultCompanyShouldNotBeFound("speciality.equals=" + UPDATED_SPECIALITY);
    }

    @Test
    @Transactional
    void getAllCompaniesBySpecialityIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where speciality in DEFAULT_SPECIALITY or UPDATED_SPECIALITY
        defaultCompanyShouldBeFound("speciality.in=" + DEFAULT_SPECIALITY + "," + UPDATED_SPECIALITY);

        // Get all the companyList where speciality equals to UPDATED_SPECIALITY
        defaultCompanyShouldNotBeFound("speciality.in=" + UPDATED_SPECIALITY);
    }

    @Test
    @Transactional
    void getAllCompaniesBySpecialityIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where speciality is not null
        defaultCompanyShouldBeFound("speciality.specified=true");

        // Get all the companyList where speciality is null
        defaultCompanyShouldNotBeFound("speciality.specified=false");
    }

    @Test
    @Transactional
    void getAllCompaniesBySpecialityContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where speciality contains DEFAULT_SPECIALITY
        defaultCompanyShouldBeFound("speciality.contains=" + DEFAULT_SPECIALITY);

        // Get all the companyList where speciality contains UPDATED_SPECIALITY
        defaultCompanyShouldNotBeFound("speciality.contains=" + UPDATED_SPECIALITY);
    }

    @Test
    @Transactional
    void getAllCompaniesBySpecialityNotContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where speciality does not contain DEFAULT_SPECIALITY
        defaultCompanyShouldNotBeFound("speciality.doesNotContain=" + DEFAULT_SPECIALITY);

        // Get all the companyList where speciality does not contain UPDATED_SPECIALITY
        defaultCompanyShouldBeFound("speciality.doesNotContain=" + UPDATED_SPECIALITY);
    }

    @Test
    @Transactional
    void getAllCompaniesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where description equals to DEFAULT_DESCRIPTION
        defaultCompanyShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the companyList where description equals to UPDATED_DESCRIPTION
        defaultCompanyShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCompaniesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultCompanyShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the companyList where description equals to UPDATED_DESCRIPTION
        defaultCompanyShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCompaniesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where description is not null
        defaultCompanyShouldBeFound("description.specified=true");

        // Get all the companyList where description is null
        defaultCompanyShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllCompaniesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where description contains DEFAULT_DESCRIPTION
        defaultCompanyShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the companyList where description contains UPDATED_DESCRIPTION
        defaultCompanyShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCompaniesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where description does not contain DEFAULT_DESCRIPTION
        defaultCompanyShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the companyList where description does not contain UPDATED_DESCRIPTION
        defaultCompanyShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCompaniesByWebsiteIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where website equals to DEFAULT_WEBSITE
        defaultCompanyShouldBeFound("website.equals=" + DEFAULT_WEBSITE);

        // Get all the companyList where website equals to UPDATED_WEBSITE
        defaultCompanyShouldNotBeFound("website.equals=" + UPDATED_WEBSITE);
    }

    @Test
    @Transactional
    void getAllCompaniesByWebsiteIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where website in DEFAULT_WEBSITE or UPDATED_WEBSITE
        defaultCompanyShouldBeFound("website.in=" + DEFAULT_WEBSITE + "," + UPDATED_WEBSITE);

        // Get all the companyList where website equals to UPDATED_WEBSITE
        defaultCompanyShouldNotBeFound("website.in=" + UPDATED_WEBSITE);
    }

    @Test
    @Transactional
    void getAllCompaniesByWebsiteIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where website is not null
        defaultCompanyShouldBeFound("website.specified=true");

        // Get all the companyList where website is null
        defaultCompanyShouldNotBeFound("website.specified=false");
    }

    @Test
    @Transactional
    void getAllCompaniesByWebsiteContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where website contains DEFAULT_WEBSITE
        defaultCompanyShouldBeFound("website.contains=" + DEFAULT_WEBSITE);

        // Get all the companyList where website contains UPDATED_WEBSITE
        defaultCompanyShouldNotBeFound("website.contains=" + UPDATED_WEBSITE);
    }

    @Test
    @Transactional
    void getAllCompaniesByWebsiteNotContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where website does not contain DEFAULT_WEBSITE
        defaultCompanyShouldNotBeFound("website.doesNotContain=" + DEFAULT_WEBSITE);

        // Get all the companyList where website does not contain UPDATED_WEBSITE
        defaultCompanyShouldBeFound("website.doesNotContain=" + UPDATED_WEBSITE);
    }

    @Test
    @Transactional
    void getAllCompaniesByLocationIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where location equals to DEFAULT_LOCATION
        defaultCompanyShouldBeFound("location.equals=" + DEFAULT_LOCATION);

        // Get all the companyList where location equals to UPDATED_LOCATION
        defaultCompanyShouldNotBeFound("location.equals=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    void getAllCompaniesByLocationIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where location in DEFAULT_LOCATION or UPDATED_LOCATION
        defaultCompanyShouldBeFound("location.in=" + DEFAULT_LOCATION + "," + UPDATED_LOCATION);

        // Get all the companyList where location equals to UPDATED_LOCATION
        defaultCompanyShouldNotBeFound("location.in=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    void getAllCompaniesByLocationIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where location is not null
        defaultCompanyShouldBeFound("location.specified=true");

        // Get all the companyList where location is null
        defaultCompanyShouldNotBeFound("location.specified=false");
    }

    @Test
    @Transactional
    void getAllCompaniesByLocationContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where location contains DEFAULT_LOCATION
        defaultCompanyShouldBeFound("location.contains=" + DEFAULT_LOCATION);

        // Get all the companyList where location contains UPDATED_LOCATION
        defaultCompanyShouldNotBeFound("location.contains=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    void getAllCompaniesByLocationNotContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where location does not contain DEFAULT_LOCATION
        defaultCompanyShouldNotBeFound("location.doesNotContain=" + DEFAULT_LOCATION);

        // Get all the companyList where location does not contain UPDATED_LOCATION
        defaultCompanyShouldBeFound("location.doesNotContain=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    void getAllCompaniesByInfoEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where infoEmail equals to DEFAULT_INFO_EMAIL
        defaultCompanyShouldBeFound("infoEmail.equals=" + DEFAULT_INFO_EMAIL);

        // Get all the companyList where infoEmail equals to UPDATED_INFO_EMAIL
        defaultCompanyShouldNotBeFound("infoEmail.equals=" + UPDATED_INFO_EMAIL);
    }

    @Test
    @Transactional
    void getAllCompaniesByInfoEmailIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where infoEmail in DEFAULT_INFO_EMAIL or UPDATED_INFO_EMAIL
        defaultCompanyShouldBeFound("infoEmail.in=" + DEFAULT_INFO_EMAIL + "," + UPDATED_INFO_EMAIL);

        // Get all the companyList where infoEmail equals to UPDATED_INFO_EMAIL
        defaultCompanyShouldNotBeFound("infoEmail.in=" + UPDATED_INFO_EMAIL);
    }

    @Test
    @Transactional
    void getAllCompaniesByInfoEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where infoEmail is not null
        defaultCompanyShouldBeFound("infoEmail.specified=true");

        // Get all the companyList where infoEmail is null
        defaultCompanyShouldNotBeFound("infoEmail.specified=false");
    }

    @Test
    @Transactional
    void getAllCompaniesByInfoEmailContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where infoEmail contains DEFAULT_INFO_EMAIL
        defaultCompanyShouldBeFound("infoEmail.contains=" + DEFAULT_INFO_EMAIL);

        // Get all the companyList where infoEmail contains UPDATED_INFO_EMAIL
        defaultCompanyShouldNotBeFound("infoEmail.contains=" + UPDATED_INFO_EMAIL);
    }

    @Test
    @Transactional
    void getAllCompaniesByInfoEmailNotContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where infoEmail does not contain DEFAULT_INFO_EMAIL
        defaultCompanyShouldNotBeFound("infoEmail.doesNotContain=" + DEFAULT_INFO_EMAIL);

        // Get all the companyList where infoEmail does not contain UPDATED_INFO_EMAIL
        defaultCompanyShouldBeFound("infoEmail.doesNotContain=" + UPDATED_INFO_EMAIL);
    }

    @Test
    @Transactional
    void getAllCompaniesByPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where phone equals to DEFAULT_PHONE
        defaultCompanyShouldBeFound("phone.equals=" + DEFAULT_PHONE);

        // Get all the companyList where phone equals to UPDATED_PHONE
        defaultCompanyShouldNotBeFound("phone.equals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllCompaniesByPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where phone in DEFAULT_PHONE or UPDATED_PHONE
        defaultCompanyShouldBeFound("phone.in=" + DEFAULT_PHONE + "," + UPDATED_PHONE);

        // Get all the companyList where phone equals to UPDATED_PHONE
        defaultCompanyShouldNotBeFound("phone.in=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllCompaniesByPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where phone is not null
        defaultCompanyShouldBeFound("phone.specified=true");

        // Get all the companyList where phone is null
        defaultCompanyShouldNotBeFound("phone.specified=false");
    }

    @Test
    @Transactional
    void getAllCompaniesByPhoneContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where phone contains DEFAULT_PHONE
        defaultCompanyShouldBeFound("phone.contains=" + DEFAULT_PHONE);

        // Get all the companyList where phone contains UPDATED_PHONE
        defaultCompanyShouldNotBeFound("phone.contains=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllCompaniesByPhoneNotContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where phone does not contain DEFAULT_PHONE
        defaultCompanyShouldNotBeFound("phone.doesNotContain=" + DEFAULT_PHONE);

        // Get all the companyList where phone does not contain UPDATED_PHONE
        defaultCompanyShouldBeFound("phone.doesNotContain=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllCompaniesByFirstContactDateIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where firstContactDate equals to DEFAULT_FIRST_CONTACT_DATE
        defaultCompanyShouldBeFound("firstContactDate.equals=" + DEFAULT_FIRST_CONTACT_DATE);

        // Get all the companyList where firstContactDate equals to UPDATED_FIRST_CONTACT_DATE
        defaultCompanyShouldNotBeFound("firstContactDate.equals=" + UPDATED_FIRST_CONTACT_DATE);
    }

    @Test
    @Transactional
    void getAllCompaniesByFirstContactDateIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where firstContactDate in DEFAULT_FIRST_CONTACT_DATE or UPDATED_FIRST_CONTACT_DATE
        defaultCompanyShouldBeFound("firstContactDate.in=" + DEFAULT_FIRST_CONTACT_DATE + "," + UPDATED_FIRST_CONTACT_DATE);

        // Get all the companyList where firstContactDate equals to UPDATED_FIRST_CONTACT_DATE
        defaultCompanyShouldNotBeFound("firstContactDate.in=" + UPDATED_FIRST_CONTACT_DATE);
    }

    @Test
    @Transactional
    void getAllCompaniesByFirstContactDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where firstContactDate is not null
        defaultCompanyShouldBeFound("firstContactDate.specified=true");

        // Get all the companyList where firstContactDate is null
        defaultCompanyShouldNotBeFound("firstContactDate.specified=false");
    }

    @Test
    @Transactional
    void getAllCompaniesByFirstContactDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where firstContactDate is greater than or equal to DEFAULT_FIRST_CONTACT_DATE
        defaultCompanyShouldBeFound("firstContactDate.greaterThanOrEqual=" + DEFAULT_FIRST_CONTACT_DATE);

        // Get all the companyList where firstContactDate is greater than or equal to UPDATED_FIRST_CONTACT_DATE
        defaultCompanyShouldNotBeFound("firstContactDate.greaterThanOrEqual=" + UPDATED_FIRST_CONTACT_DATE);
    }

    @Test
    @Transactional
    void getAllCompaniesByFirstContactDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where firstContactDate is less than or equal to DEFAULT_FIRST_CONTACT_DATE
        defaultCompanyShouldBeFound("firstContactDate.lessThanOrEqual=" + DEFAULT_FIRST_CONTACT_DATE);

        // Get all the companyList where firstContactDate is less than or equal to SMALLER_FIRST_CONTACT_DATE
        defaultCompanyShouldNotBeFound("firstContactDate.lessThanOrEqual=" + SMALLER_FIRST_CONTACT_DATE);
    }

    @Test
    @Transactional
    void getAllCompaniesByFirstContactDateIsLessThanSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where firstContactDate is less than DEFAULT_FIRST_CONTACT_DATE
        defaultCompanyShouldNotBeFound("firstContactDate.lessThan=" + DEFAULT_FIRST_CONTACT_DATE);

        // Get all the companyList where firstContactDate is less than UPDATED_FIRST_CONTACT_DATE
        defaultCompanyShouldBeFound("firstContactDate.lessThan=" + UPDATED_FIRST_CONTACT_DATE);
    }

    @Test
    @Transactional
    void getAllCompaniesByFirstContactDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where firstContactDate is greater than DEFAULT_FIRST_CONTACT_DATE
        defaultCompanyShouldNotBeFound("firstContactDate.greaterThan=" + DEFAULT_FIRST_CONTACT_DATE);

        // Get all the companyList where firstContactDate is greater than SMALLER_FIRST_CONTACT_DATE
        defaultCompanyShouldBeFound("firstContactDate.greaterThan=" + SMALLER_FIRST_CONTACT_DATE);
    }

    @Test
    @Transactional
    void getAllCompaniesByNotificationsIsEqualToSomething() throws Exception {
        Notification notifications;
        if (TestUtil.findAll(em, Notification.class).isEmpty()) {
            companyRepository.saveAndFlush(company);
            notifications = NotificationResourceIT.createEntity(em);
        } else {
            notifications = TestUtil.findAll(em, Notification.class).get(0);
        }
        em.persist(notifications);
        em.flush();
        company.addNotifications(notifications);
        companyRepository.saveAndFlush(company);
        Long notificationsId = notifications.getId();
        // Get all the companyList where notifications equals to notificationsId
        defaultCompanyShouldBeFound("notificationsId.equals=" + notificationsId);

        // Get all the companyList where notifications equals to (notificationsId + 1)
        defaultCompanyShouldNotBeFound("notificationsId.equals=" + (notificationsId + 1));
    }

    @Test
    @Transactional
    void getAllCompaniesByNotesIsEqualToSomething() throws Exception {
        Note notes;
        if (TestUtil.findAll(em, Note.class).isEmpty()) {
            companyRepository.saveAndFlush(company);
            notes = NoteResourceIT.createEntity(em);
        } else {
            notes = TestUtil.findAll(em, Note.class).get(0);
        }
        em.persist(notes);
        em.flush();
        company.addNotes(notes);
        companyRepository.saveAndFlush(company);
        Long notesId = notes.getId();
        // Get all the companyList where notes equals to notesId
        defaultCompanyShouldBeFound("notesId.equals=" + notesId);

        // Get all the companyList where notes equals to (notesId + 1)
        defaultCompanyShouldNotBeFound("notesId.equals=" + (notesId + 1));
    }

    @Test
    @Transactional
    void getAllCompaniesByDesidersIsEqualToSomething() throws Exception {
        Desider desiders;
        if (TestUtil.findAll(em, Desider.class).isEmpty()) {
            companyRepository.saveAndFlush(company);
            desiders = DesiderResourceIT.createEntity(em);
        } else {
            desiders = TestUtil.findAll(em, Desider.class).get(0);
        }
        em.persist(desiders);
        em.flush();
        company.addDesiders(desiders);
        companyRepository.saveAndFlush(company);
        Long desidersId = desiders.getId();
        // Get all the companyList where desiders equals to desidersId
        defaultCompanyShouldBeFound("desidersId.equals=" + desidersId);

        // Get all the companyList where desiders equals to (desidersId + 1)
        defaultCompanyShouldNotBeFound("desidersId.equals=" + (desidersId + 1));
    }

    @Test
    @Transactional
    void getAllCompaniesByOffersIsEqualToSomething() throws Exception {
        Offer offers;
        if (TestUtil.findAll(em, Offer.class).isEmpty()) {
            companyRepository.saveAndFlush(company);
            offers = OfferResourceIT.createEntity(em);
        } else {
            offers = TestUtil.findAll(em, Offer.class).get(0);
        }
        em.persist(offers);
        em.flush();
        company.addOffers(offers);
        companyRepository.saveAndFlush(company);
        Long offersId = offers.getId();
        // Get all the companyList where offers equals to offersId
        defaultCompanyShouldBeFound("offersId.equals=" + offersId);

        // Get all the companyList where offers equals to (offersId + 1)
        defaultCompanyShouldNotBeFound("offersId.equals=" + (offersId + 1));
    }

    @Test
    @Transactional
    void getAllCompaniesByEmailsIsEqualToSomething() throws Exception {
        Email emails;
        if (TestUtil.findAll(em, Email.class).isEmpty()) {
            companyRepository.saveAndFlush(company);
            emails = EmailResourceIT.createEntity(em);
        } else {
            emails = TestUtil.findAll(em, Email.class).get(0);
        }
        em.persist(emails);
        em.flush();
        company.addEmails(emails);
        companyRepository.saveAndFlush(company);
        Long emailsId = emails.getId();
        // Get all the companyList where emails equals to emailsId
        defaultCompanyShouldBeFound("emailsId.equals=" + emailsId);

        // Get all the companyList where emails equals to (emailsId + 1)
        defaultCompanyShouldNotBeFound("emailsId.equals=" + (emailsId + 1));
    }

    @Test
    @Transactional
    void getAllCompaniesByCompanyCategoryIsEqualToSomething() throws Exception {
        Category companyCategory;
        if (TestUtil.findAll(em, Category.class).isEmpty()) {
            companyRepository.saveAndFlush(company);
            companyCategory = CategoryResourceIT.createEntity(em);
        } else {
            companyCategory = TestUtil.findAll(em, Category.class).get(0);
        }
        em.persist(companyCategory);
        em.flush();
        company.setCompanyCategory(companyCategory);
        companyRepository.saveAndFlush(company);
        Long companyCategoryId = companyCategory.getId();
        // Get all the companyList where companyCategory equals to companyCategoryId
        defaultCompanyShouldBeFound("companyCategoryId.equals=" + companyCategoryId);

        // Get all the companyList where companyCategory equals to (companyCategoryId + 1)
        defaultCompanyShouldNotBeFound("companyCategoryId.equals=" + (companyCategoryId + 1));
    }

    @Test
    @Transactional
    void getAllCompaniesByCompanySubCategoryIsEqualToSomething() throws Exception {
        SubCategory companySubCategory;
        if (TestUtil.findAll(em, SubCategory.class).isEmpty()) {
            companyRepository.saveAndFlush(company);
            companySubCategory = SubCategoryResourceIT.createEntity(em);
        } else {
            companySubCategory = TestUtil.findAll(em, SubCategory.class).get(0);
        }
        em.persist(companySubCategory);
        em.flush();
        company.setCompanySubCategory(companySubCategory);
        companyRepository.saveAndFlush(company);
        Long companySubCategoryId = companySubCategory.getId();
        // Get all the companyList where companySubCategory equals to companySubCategoryId
        defaultCompanyShouldBeFound("companySubCategoryId.equals=" + companySubCategoryId);

        // Get all the companyList where companySubCategory equals to (companySubCategoryId + 1)
        defaultCompanyShouldNotBeFound("companySubCategoryId.equals=" + (companySubCategoryId + 1));
    }

    @Test
    @Transactional
    void getAllCompaniesByCompanyProcessIsEqualToSomething() throws Exception {
        Process companyProcess;
        if (TestUtil.findAll(em, Process.class).isEmpty()) {
            companyRepository.saveAndFlush(company);
            companyProcess = ProcessResourceIT.createEntity(em);
        } else {
            companyProcess = TestUtil.findAll(em, Process.class).get(0);
        }
        em.persist(companyProcess);
        em.flush();
        company.setCompanyProcess(companyProcess);
        companyRepository.saveAndFlush(company);
        Long companyProcessId = companyProcess.getId();
        // Get all the companyList where companyProcess equals to companyProcessId
        defaultCompanyShouldBeFound("companyProcessId.equals=" + companyProcessId);

        // Get all the companyList where companyProcess equals to (companyProcessId + 1)
        defaultCompanyShouldNotBeFound("companyProcessId.equals=" + (companyProcessId + 1));
    }

    @Test
    @Transactional
    void getAllCompaniesByCompanyProcessStepIsEqualToSomething() throws Exception {
        ProcessStep companyProcessStep;
        if (TestUtil.findAll(em, ProcessStep.class).isEmpty()) {
            companyRepository.saveAndFlush(company);
            companyProcessStep = ProcessStepResourceIT.createEntity(em);
        } else {
            companyProcessStep = TestUtil.findAll(em, ProcessStep.class).get(0);
        }
        em.persist(companyProcessStep);
        em.flush();
        company.setCompanyProcessStep(companyProcessStep);
        companyRepository.saveAndFlush(company);
        Long companyProcessStepId = companyProcessStep.getId();
        // Get all the companyList where companyProcessStep equals to companyProcessStepId
        defaultCompanyShouldBeFound("companyProcessStepId.equals=" + companyProcessStepId);

        // Get all the companyList where companyProcessStep equals to (companyProcessStepId + 1)
        defaultCompanyShouldNotBeFound("companyProcessStepId.equals=" + (companyProcessStepId + 1));
    }

    @Test
    @Transactional
    void getAllCompaniesByTagsIsEqualToSomething() throws Exception {
        Tag tags;
        if (TestUtil.findAll(em, Tag.class).isEmpty()) {
            companyRepository.saveAndFlush(company);
            tags = TagResourceIT.createEntity(em);
        } else {
            tags = TestUtil.findAll(em, Tag.class).get(0);
        }
        em.persist(tags);
        em.flush();
        company.addTags(tags);
        companyRepository.saveAndFlush(company);
        Long tagsId = tags.getId();
        // Get all the companyList where tags equals to tagsId
        defaultCompanyShouldBeFound("tagsId.equals=" + tagsId);

        // Get all the companyList where tags equals to (tagsId + 1)
        defaultCompanyShouldNotBeFound("tagsId.equals=" + (tagsId + 1));
    }

    @Test
    @Transactional
    void getAllCompaniesByCategoryIsEqualToSomething() throws Exception {
        Category category;
        if (TestUtil.findAll(em, Category.class).isEmpty()) {
            companyRepository.saveAndFlush(company);
            category = CategoryResourceIT.createEntity(em);
        } else {
            category = TestUtil.findAll(em, Category.class).get(0);
        }
        em.persist(category);
        em.flush();
        company.setCategory(category);
        companyRepository.saveAndFlush(company);
        Long categoryId = category.getId();
        // Get all the companyList where category equals to categoryId
        defaultCompanyShouldBeFound("categoryId.equals=" + categoryId);

        // Get all the companyList where category equals to (categoryId + 1)
        defaultCompanyShouldNotBeFound("categoryId.equals=" + (categoryId + 1));
    }

    @Test
    @Transactional
    void getAllCompaniesBySubCategoryIsEqualToSomething() throws Exception {
        SubCategory subCategory;
        if (TestUtil.findAll(em, SubCategory.class).isEmpty()) {
            companyRepository.saveAndFlush(company);
            subCategory = SubCategoryResourceIT.createEntity(em);
        } else {
            subCategory = TestUtil.findAll(em, SubCategory.class).get(0);
        }
        em.persist(subCategory);
        em.flush();
        company.setSubCategory(subCategory);
        companyRepository.saveAndFlush(company);
        Long subCategoryId = subCategory.getId();
        // Get all the companyList where subCategory equals to subCategoryId
        defaultCompanyShouldBeFound("subCategoryId.equals=" + subCategoryId);

        // Get all the companyList where subCategory equals to (subCategoryId + 1)
        defaultCompanyShouldNotBeFound("subCategoryId.equals=" + (subCategoryId + 1));
    }

    @Test
    @Transactional
    void getAllCompaniesByProcessIsEqualToSomething() throws Exception {
        Process process;
        if (TestUtil.findAll(em, Process.class).isEmpty()) {
            companyRepository.saveAndFlush(company);
            process = ProcessResourceIT.createEntity(em);
        } else {
            process = TestUtil.findAll(em, Process.class).get(0);
        }
        em.persist(process);
        em.flush();
        company.setProcess(process);
        companyRepository.saveAndFlush(company);
        Long processId = process.getId();
        // Get all the companyList where process equals to processId
        defaultCompanyShouldBeFound("processId.equals=" + processId);

        // Get all the companyList where process equals to (processId + 1)
        defaultCompanyShouldNotBeFound("processId.equals=" + (processId + 1));
    }

    @Test
    @Transactional
    void getAllCompaniesByProcessStepIsEqualToSomething() throws Exception {
        ProcessStep processStep;
        if (TestUtil.findAll(em, ProcessStep.class).isEmpty()) {
            companyRepository.saveAndFlush(company);
            processStep = ProcessStepResourceIT.createEntity(em);
        } else {
            processStep = TestUtil.findAll(em, ProcessStep.class).get(0);
        }
        em.persist(processStep);
        em.flush();
        company.setProcessStep(processStep);
        companyRepository.saveAndFlush(company);
        Long processStepId = processStep.getId();
        // Get all the companyList where processStep equals to processStepId
        defaultCompanyShouldBeFound("processStepId.equals=" + processStepId);

        // Get all the companyList where processStep equals to (processStepId + 1)
        defaultCompanyShouldNotBeFound("processStepId.equals=" + (processStepId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCompanyShouldBeFound(String filter) throws Exception {
        restCompanyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(company.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].speciality").value(hasItem(DEFAULT_SPECIALITY)))
            .andExpect(jsonPath("$.[*].logoContentType").value(hasItem(DEFAULT_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].logo").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_LOGO))))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].website").value(hasItem(DEFAULT_WEBSITE)))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)))
            .andExpect(jsonPath("$.[*].infoEmail").value(hasItem(DEFAULT_INFO_EMAIL)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].firstContactDate").value(hasItem(DEFAULT_FIRST_CONTACT_DATE.toString())));

        // Check, that the count call also returns 1
        restCompanyMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCompanyShouldNotBeFound(String filter) throws Exception {
        restCompanyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCompanyMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCompany() throws Exception {
        // Get the company
        restCompanyMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCompany() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        int databaseSizeBeforeUpdate = companyRepository.findAll().size();

        // Update the company
        Company updatedCompany = companyRepository.findById(company.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCompany are not directly saved in db
        em.detach(updatedCompany);
        updatedCompany
            .name(UPDATED_NAME)
            .speciality(UPDATED_SPECIALITY)
            .logo(UPDATED_LOGO)
            .logoContentType(UPDATED_LOGO_CONTENT_TYPE)
            .description(UPDATED_DESCRIPTION)
            .website(UPDATED_WEBSITE)
            .location(UPDATED_LOCATION)
            .infoEmail(UPDATED_INFO_EMAIL)
            .phone(UPDATED_PHONE)
            .firstContactDate(UPDATED_FIRST_CONTACT_DATE);
        CompanyDTO companyDTO = companyMapper.toDto(updatedCompany);

        restCompanyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, companyDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(companyDTO))
            )
            .andExpect(status().isOk());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeUpdate);
        Company testCompany = companyList.get(companyList.size() - 1);
        assertThat(testCompany.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCompany.getSpeciality()).isEqualTo(UPDATED_SPECIALITY);
        assertThat(testCompany.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testCompany.getLogoContentType()).isEqualTo(UPDATED_LOGO_CONTENT_TYPE);
        assertThat(testCompany.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCompany.getWebsite()).isEqualTo(UPDATED_WEBSITE);
        assertThat(testCompany.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testCompany.getInfoEmail()).isEqualTo(UPDATED_INFO_EMAIL);
        assertThat(testCompany.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testCompany.getFirstContactDate()).isEqualTo(UPDATED_FIRST_CONTACT_DATE);
    }

    @Test
    @Transactional
    void putNonExistingCompany() throws Exception {
        int databaseSizeBeforeUpdate = companyRepository.findAll().size();
        company.setId(longCount.incrementAndGet());

        // Create the Company
        CompanyDTO companyDTO = companyMapper.toDto(company);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompanyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, companyDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(companyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCompany() throws Exception {
        int databaseSizeBeforeUpdate = companyRepository.findAll().size();
        company.setId(longCount.incrementAndGet());

        // Create the Company
        CompanyDTO companyDTO = companyMapper.toDto(company);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(companyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCompany() throws Exception {
        int databaseSizeBeforeUpdate = companyRepository.findAll().size();
        company.setId(longCount.incrementAndGet());

        // Create the Company
        CompanyDTO companyDTO = companyMapper.toDto(company);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companyDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCompanyWithPatch() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        int databaseSizeBeforeUpdate = companyRepository.findAll().size();

        // Update the company using partial update
        Company partialUpdatedCompany = new Company();
        partialUpdatedCompany.setId(company.getId());

        partialUpdatedCompany
            .name(UPDATED_NAME)
            .speciality(UPDATED_SPECIALITY)
            .description(UPDATED_DESCRIPTION)
            .location(UPDATED_LOCATION)
            .infoEmail(UPDATED_INFO_EMAIL)
            .phone(UPDATED_PHONE)
            .firstContactDate(UPDATED_FIRST_CONTACT_DATE);

        restCompanyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompany.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompany))
            )
            .andExpect(status().isOk());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeUpdate);
        Company testCompany = companyList.get(companyList.size() - 1);
        assertThat(testCompany.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCompany.getSpeciality()).isEqualTo(UPDATED_SPECIALITY);
        assertThat(testCompany.getLogo()).isEqualTo(DEFAULT_LOGO);
        assertThat(testCompany.getLogoContentType()).isEqualTo(DEFAULT_LOGO_CONTENT_TYPE);
        assertThat(testCompany.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCompany.getWebsite()).isEqualTo(DEFAULT_WEBSITE);
        assertThat(testCompany.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testCompany.getInfoEmail()).isEqualTo(UPDATED_INFO_EMAIL);
        assertThat(testCompany.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testCompany.getFirstContactDate()).isEqualTo(UPDATED_FIRST_CONTACT_DATE);
    }

    @Test
    @Transactional
    void fullUpdateCompanyWithPatch() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        int databaseSizeBeforeUpdate = companyRepository.findAll().size();

        // Update the company using partial update
        Company partialUpdatedCompany = new Company();
        partialUpdatedCompany.setId(company.getId());

        partialUpdatedCompany
            .name(UPDATED_NAME)
            .speciality(UPDATED_SPECIALITY)
            .logo(UPDATED_LOGO)
            .logoContentType(UPDATED_LOGO_CONTENT_TYPE)
            .description(UPDATED_DESCRIPTION)
            .website(UPDATED_WEBSITE)
            .location(UPDATED_LOCATION)
            .infoEmail(UPDATED_INFO_EMAIL)
            .phone(UPDATED_PHONE)
            .firstContactDate(UPDATED_FIRST_CONTACT_DATE);

        restCompanyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompany.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompany))
            )
            .andExpect(status().isOk());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeUpdate);
        Company testCompany = companyList.get(companyList.size() - 1);
        assertThat(testCompany.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCompany.getSpeciality()).isEqualTo(UPDATED_SPECIALITY);
        assertThat(testCompany.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testCompany.getLogoContentType()).isEqualTo(UPDATED_LOGO_CONTENT_TYPE);
        assertThat(testCompany.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCompany.getWebsite()).isEqualTo(UPDATED_WEBSITE);
        assertThat(testCompany.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testCompany.getInfoEmail()).isEqualTo(UPDATED_INFO_EMAIL);
        assertThat(testCompany.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testCompany.getFirstContactDate()).isEqualTo(UPDATED_FIRST_CONTACT_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingCompany() throws Exception {
        int databaseSizeBeforeUpdate = companyRepository.findAll().size();
        company.setId(longCount.incrementAndGet());

        // Create the Company
        CompanyDTO companyDTO = companyMapper.toDto(company);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompanyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, companyDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(companyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCompany() throws Exception {
        int databaseSizeBeforeUpdate = companyRepository.findAll().size();
        company.setId(longCount.incrementAndGet());

        // Create the Company
        CompanyDTO companyDTO = companyMapper.toDto(company);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(companyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCompany() throws Exception {
        int databaseSizeBeforeUpdate = companyRepository.findAll().size();
        company.setId(longCount.incrementAndGet());

        // Create the Company
        CompanyDTO companyDTO = companyMapper.toDto(company);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(companyDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCompany() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        int databaseSizeBeforeDelete = companyRepository.findAll().size();

        // Delete the company
        restCompanyMockMvc
            .perform(delete(ENTITY_API_URL_ID, company.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
