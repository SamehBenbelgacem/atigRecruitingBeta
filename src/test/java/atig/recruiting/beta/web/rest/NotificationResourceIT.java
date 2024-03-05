package atig.recruiting.beta.web.rest;

import static atig.recruiting.beta.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import atig.recruiting.beta.IntegrationTest;
import atig.recruiting.beta.domain.Candidate;
import atig.recruiting.beta.domain.Company;
import atig.recruiting.beta.domain.Notification;
import atig.recruiting.beta.domain.enumeration.EnumNotificationType;
import atig.recruiting.beta.domain.enumeration.EnumPriority;
import atig.recruiting.beta.repository.NotificationRepository;
import atig.recruiting.beta.service.dto.NotificationDTO;
import atig.recruiting.beta.service.mapper.NotificationMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
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
 * Integration tests for the {@link NotificationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NotificationResourceIT {

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CALL_UP_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CALL_UP_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_CALL_UP_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final Boolean DEFAULT_READ_STATUS = false;
    private static final Boolean UPDATED_READ_STATUS = true;

    private static final EnumPriority DEFAULT_ATTENTION = EnumPriority.LOW;
    private static final EnumPriority UPDATED_ATTENTION = EnumPriority.MEDUIM;

    private static final EnumNotificationType DEFAULT_TYPE = EnumNotificationType.GENERAl;
    private static final EnumNotificationType UPDATED_TYPE = EnumNotificationType.FORCOMPANY;

    private static final String ENTITY_API_URL = "/api/notifications";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNotificationMockMvc;

    private Notification notification;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Notification createEntity(EntityManager em) {
        Notification notification = new Notification()
            .message(DEFAULT_MESSAGE)
            .callUpDate(DEFAULT_CALL_UP_DATE)
            .readStatus(DEFAULT_READ_STATUS)
            .attention(DEFAULT_ATTENTION)
            .type(DEFAULT_TYPE);
        return notification;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Notification createUpdatedEntity(EntityManager em) {
        Notification notification = new Notification()
            .message(UPDATED_MESSAGE)
            .callUpDate(UPDATED_CALL_UP_DATE)
            .readStatus(UPDATED_READ_STATUS)
            .attention(UPDATED_ATTENTION)
            .type(UPDATED_TYPE);
        return notification;
    }

    @BeforeEach
    public void initTest() {
        notification = createEntity(em);
    }

    @Test
    @Transactional
    void createNotification() throws Exception {
        int databaseSizeBeforeCreate = notificationRepository.findAll().size();
        // Create the Notification
        NotificationDTO notificationDTO = notificationMapper.toDto(notification);
        restNotificationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(notificationDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Notification in the database
        List<Notification> notificationList = notificationRepository.findAll();
        assertThat(notificationList).hasSize(databaseSizeBeforeCreate + 1);
        Notification testNotification = notificationList.get(notificationList.size() - 1);
        assertThat(testNotification.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testNotification.getCallUpDate()).isEqualTo(DEFAULT_CALL_UP_DATE);
        assertThat(testNotification.getReadStatus()).isEqualTo(DEFAULT_READ_STATUS);
        assertThat(testNotification.getAttention()).isEqualTo(DEFAULT_ATTENTION);
        assertThat(testNotification.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    void createNotificationWithExistingId() throws Exception {
        // Create the Notification with an existing ID
        notification.setId(1L);
        NotificationDTO notificationDTO = notificationMapper.toDto(notification);

        int databaseSizeBeforeCreate = notificationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNotificationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(notificationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Notification in the database
        List<Notification> notificationList = notificationRepository.findAll();
        assertThat(notificationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAttentionIsRequired() throws Exception {
        int databaseSizeBeforeTest = notificationRepository.findAll().size();
        // set the field null
        notification.setAttention(null);

        // Create the Notification, which fails.
        NotificationDTO notificationDTO = notificationMapper.toDto(notification);

        restNotificationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(notificationDTO))
            )
            .andExpect(status().isBadRequest());

        List<Notification> notificationList = notificationRepository.findAll();
        assertThat(notificationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllNotifications() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList
        restNotificationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(notification.getId().intValue())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].callUpDate").value(hasItem(sameInstant(DEFAULT_CALL_UP_DATE))))
            .andExpect(jsonPath("$.[*].readStatus").value(hasItem(DEFAULT_READ_STATUS.booleanValue())))
            .andExpect(jsonPath("$.[*].attention").value(hasItem(DEFAULT_ATTENTION.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    @Transactional
    void getNotification() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get the notification
        restNotificationMockMvc
            .perform(get(ENTITY_API_URL_ID, notification.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(notification.getId().intValue()))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE))
            .andExpect(jsonPath("$.callUpDate").value(sameInstant(DEFAULT_CALL_UP_DATE)))
            .andExpect(jsonPath("$.readStatus").value(DEFAULT_READ_STATUS.booleanValue()))
            .andExpect(jsonPath("$.attention").value(DEFAULT_ATTENTION.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    void getNotificationsByIdFiltering() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        Long id = notification.getId();

        defaultNotificationShouldBeFound("id.equals=" + id);
        defaultNotificationShouldNotBeFound("id.notEquals=" + id);

        defaultNotificationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultNotificationShouldNotBeFound("id.greaterThan=" + id);

        defaultNotificationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultNotificationShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllNotificationsByMessageIsEqualToSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where message equals to DEFAULT_MESSAGE
        defaultNotificationShouldBeFound("message.equals=" + DEFAULT_MESSAGE);

        // Get all the notificationList where message equals to UPDATED_MESSAGE
        defaultNotificationShouldNotBeFound("message.equals=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    void getAllNotificationsByMessageIsInShouldWork() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where message in DEFAULT_MESSAGE or UPDATED_MESSAGE
        defaultNotificationShouldBeFound("message.in=" + DEFAULT_MESSAGE + "," + UPDATED_MESSAGE);

        // Get all the notificationList where message equals to UPDATED_MESSAGE
        defaultNotificationShouldNotBeFound("message.in=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    void getAllNotificationsByMessageIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where message is not null
        defaultNotificationShouldBeFound("message.specified=true");

        // Get all the notificationList where message is null
        defaultNotificationShouldNotBeFound("message.specified=false");
    }

    @Test
    @Transactional
    void getAllNotificationsByMessageContainsSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where message contains DEFAULT_MESSAGE
        defaultNotificationShouldBeFound("message.contains=" + DEFAULT_MESSAGE);

        // Get all the notificationList where message contains UPDATED_MESSAGE
        defaultNotificationShouldNotBeFound("message.contains=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    void getAllNotificationsByMessageNotContainsSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where message does not contain DEFAULT_MESSAGE
        defaultNotificationShouldNotBeFound("message.doesNotContain=" + DEFAULT_MESSAGE);

        // Get all the notificationList where message does not contain UPDATED_MESSAGE
        defaultNotificationShouldBeFound("message.doesNotContain=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    void getAllNotificationsByCallUpDateIsEqualToSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where callUpDate equals to DEFAULT_CALL_UP_DATE
        defaultNotificationShouldBeFound("callUpDate.equals=" + DEFAULT_CALL_UP_DATE);

        // Get all the notificationList where callUpDate equals to UPDATED_CALL_UP_DATE
        defaultNotificationShouldNotBeFound("callUpDate.equals=" + UPDATED_CALL_UP_DATE);
    }

    @Test
    @Transactional
    void getAllNotificationsByCallUpDateIsInShouldWork() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where callUpDate in DEFAULT_CALL_UP_DATE or UPDATED_CALL_UP_DATE
        defaultNotificationShouldBeFound("callUpDate.in=" + DEFAULT_CALL_UP_DATE + "," + UPDATED_CALL_UP_DATE);

        // Get all the notificationList where callUpDate equals to UPDATED_CALL_UP_DATE
        defaultNotificationShouldNotBeFound("callUpDate.in=" + UPDATED_CALL_UP_DATE);
    }

    @Test
    @Transactional
    void getAllNotificationsByCallUpDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where callUpDate is not null
        defaultNotificationShouldBeFound("callUpDate.specified=true");

        // Get all the notificationList where callUpDate is null
        defaultNotificationShouldNotBeFound("callUpDate.specified=false");
    }

    @Test
    @Transactional
    void getAllNotificationsByCallUpDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where callUpDate is greater than or equal to DEFAULT_CALL_UP_DATE
        defaultNotificationShouldBeFound("callUpDate.greaterThanOrEqual=" + DEFAULT_CALL_UP_DATE);

        // Get all the notificationList where callUpDate is greater than or equal to UPDATED_CALL_UP_DATE
        defaultNotificationShouldNotBeFound("callUpDate.greaterThanOrEqual=" + UPDATED_CALL_UP_DATE);
    }

    @Test
    @Transactional
    void getAllNotificationsByCallUpDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where callUpDate is less than or equal to DEFAULT_CALL_UP_DATE
        defaultNotificationShouldBeFound("callUpDate.lessThanOrEqual=" + DEFAULT_CALL_UP_DATE);

        // Get all the notificationList where callUpDate is less than or equal to SMALLER_CALL_UP_DATE
        defaultNotificationShouldNotBeFound("callUpDate.lessThanOrEqual=" + SMALLER_CALL_UP_DATE);
    }

    @Test
    @Transactional
    void getAllNotificationsByCallUpDateIsLessThanSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where callUpDate is less than DEFAULT_CALL_UP_DATE
        defaultNotificationShouldNotBeFound("callUpDate.lessThan=" + DEFAULT_CALL_UP_DATE);

        // Get all the notificationList where callUpDate is less than UPDATED_CALL_UP_DATE
        defaultNotificationShouldBeFound("callUpDate.lessThan=" + UPDATED_CALL_UP_DATE);
    }

    @Test
    @Transactional
    void getAllNotificationsByCallUpDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where callUpDate is greater than DEFAULT_CALL_UP_DATE
        defaultNotificationShouldNotBeFound("callUpDate.greaterThan=" + DEFAULT_CALL_UP_DATE);

        // Get all the notificationList where callUpDate is greater than SMALLER_CALL_UP_DATE
        defaultNotificationShouldBeFound("callUpDate.greaterThan=" + SMALLER_CALL_UP_DATE);
    }

    @Test
    @Transactional
    void getAllNotificationsByReadStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where readStatus equals to DEFAULT_READ_STATUS
        defaultNotificationShouldBeFound("readStatus.equals=" + DEFAULT_READ_STATUS);

        // Get all the notificationList where readStatus equals to UPDATED_READ_STATUS
        defaultNotificationShouldNotBeFound("readStatus.equals=" + UPDATED_READ_STATUS);
    }

    @Test
    @Transactional
    void getAllNotificationsByReadStatusIsInShouldWork() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where readStatus in DEFAULT_READ_STATUS or UPDATED_READ_STATUS
        defaultNotificationShouldBeFound("readStatus.in=" + DEFAULT_READ_STATUS + "," + UPDATED_READ_STATUS);

        // Get all the notificationList where readStatus equals to UPDATED_READ_STATUS
        defaultNotificationShouldNotBeFound("readStatus.in=" + UPDATED_READ_STATUS);
    }

    @Test
    @Transactional
    void getAllNotificationsByReadStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where readStatus is not null
        defaultNotificationShouldBeFound("readStatus.specified=true");

        // Get all the notificationList where readStatus is null
        defaultNotificationShouldNotBeFound("readStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllNotificationsByAttentionIsEqualToSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where attention equals to DEFAULT_ATTENTION
        defaultNotificationShouldBeFound("attention.equals=" + DEFAULT_ATTENTION);

        // Get all the notificationList where attention equals to UPDATED_ATTENTION
        defaultNotificationShouldNotBeFound("attention.equals=" + UPDATED_ATTENTION);
    }

    @Test
    @Transactional
    void getAllNotificationsByAttentionIsInShouldWork() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where attention in DEFAULT_ATTENTION or UPDATED_ATTENTION
        defaultNotificationShouldBeFound("attention.in=" + DEFAULT_ATTENTION + "," + UPDATED_ATTENTION);

        // Get all the notificationList where attention equals to UPDATED_ATTENTION
        defaultNotificationShouldNotBeFound("attention.in=" + UPDATED_ATTENTION);
    }

    @Test
    @Transactional
    void getAllNotificationsByAttentionIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where attention is not null
        defaultNotificationShouldBeFound("attention.specified=true");

        // Get all the notificationList where attention is null
        defaultNotificationShouldNotBeFound("attention.specified=false");
    }

    @Test
    @Transactional
    void getAllNotificationsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where type equals to DEFAULT_TYPE
        defaultNotificationShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the notificationList where type equals to UPDATED_TYPE
        defaultNotificationShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllNotificationsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultNotificationShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the notificationList where type equals to UPDATED_TYPE
        defaultNotificationShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllNotificationsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where type is not null
        defaultNotificationShouldBeFound("type.specified=true");

        // Get all the notificationList where type is null
        defaultNotificationShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllNotificationsByNotificationCandidateIsEqualToSomething() throws Exception {
        Candidate notificationCandidate;
        if (TestUtil.findAll(em, Candidate.class).isEmpty()) {
            notificationRepository.saveAndFlush(notification);
            notificationCandidate = CandidateResourceIT.createEntity(em);
        } else {
            notificationCandidate = TestUtil.findAll(em, Candidate.class).get(0);
        }
        em.persist(notificationCandidate);
        em.flush();
        notification.setNotificationCandidate(notificationCandidate);
        notificationRepository.saveAndFlush(notification);
        Long notificationCandidateId = notificationCandidate.getId();
        // Get all the notificationList where notificationCandidate equals to notificationCandidateId
        defaultNotificationShouldBeFound("notificationCandidateId.equals=" + notificationCandidateId);

        // Get all the notificationList where notificationCandidate equals to (notificationCandidateId + 1)
        defaultNotificationShouldNotBeFound("notificationCandidateId.equals=" + (notificationCandidateId + 1));
    }

    @Test
    @Transactional
    void getAllNotificationsByNotificationCompanyIsEqualToSomething() throws Exception {
        Company notificationCompany;
        if (TestUtil.findAll(em, Company.class).isEmpty()) {
            notificationRepository.saveAndFlush(notification);
            notificationCompany = CompanyResourceIT.createEntity(em);
        } else {
            notificationCompany = TestUtil.findAll(em, Company.class).get(0);
        }
        em.persist(notificationCompany);
        em.flush();
        notification.setNotificationCompany(notificationCompany);
        notificationRepository.saveAndFlush(notification);
        Long notificationCompanyId = notificationCompany.getId();
        // Get all the notificationList where notificationCompany equals to notificationCompanyId
        defaultNotificationShouldBeFound("notificationCompanyId.equals=" + notificationCompanyId);

        // Get all the notificationList where notificationCompany equals to (notificationCompanyId + 1)
        defaultNotificationShouldNotBeFound("notificationCompanyId.equals=" + (notificationCompanyId + 1));
    }

    @Test
    @Transactional
    void getAllNotificationsByCandidateIsEqualToSomething() throws Exception {
        Candidate candidate;
        if (TestUtil.findAll(em, Candidate.class).isEmpty()) {
            notificationRepository.saveAndFlush(notification);
            candidate = CandidateResourceIT.createEntity(em);
        } else {
            candidate = TestUtil.findAll(em, Candidate.class).get(0);
        }
        em.persist(candidate);
        em.flush();
        notification.setCandidate(candidate);
        notificationRepository.saveAndFlush(notification);
        Long candidateId = candidate.getId();
        // Get all the notificationList where candidate equals to candidateId
        defaultNotificationShouldBeFound("candidateId.equals=" + candidateId);

        // Get all the notificationList where candidate equals to (candidateId + 1)
        defaultNotificationShouldNotBeFound("candidateId.equals=" + (candidateId + 1));
    }

    @Test
    @Transactional
    void getAllNotificationsByCompanyIsEqualToSomething() throws Exception {
        Company company;
        if (TestUtil.findAll(em, Company.class).isEmpty()) {
            notificationRepository.saveAndFlush(notification);
            company = CompanyResourceIT.createEntity(em);
        } else {
            company = TestUtil.findAll(em, Company.class).get(0);
        }
        em.persist(company);
        em.flush();
        notification.setCompany(company);
        notificationRepository.saveAndFlush(notification);
        Long companyId = company.getId();
        // Get all the notificationList where company equals to companyId
        defaultNotificationShouldBeFound("companyId.equals=" + companyId);

        // Get all the notificationList where company equals to (companyId + 1)
        defaultNotificationShouldNotBeFound("companyId.equals=" + (companyId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultNotificationShouldBeFound(String filter) throws Exception {
        restNotificationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(notification.getId().intValue())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].callUpDate").value(hasItem(sameInstant(DEFAULT_CALL_UP_DATE))))
            .andExpect(jsonPath("$.[*].readStatus").value(hasItem(DEFAULT_READ_STATUS.booleanValue())))
            .andExpect(jsonPath("$.[*].attention").value(hasItem(DEFAULT_ATTENTION.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));

        // Check, that the count call also returns 1
        restNotificationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultNotificationShouldNotBeFound(String filter) throws Exception {
        restNotificationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNotificationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingNotification() throws Exception {
        // Get the notification
        restNotificationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNotification() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        int databaseSizeBeforeUpdate = notificationRepository.findAll().size();

        // Update the notification
        Notification updatedNotification = notificationRepository.findById(notification.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedNotification are not directly saved in db
        em.detach(updatedNotification);
        updatedNotification
            .message(UPDATED_MESSAGE)
            .callUpDate(UPDATED_CALL_UP_DATE)
            .readStatus(UPDATED_READ_STATUS)
            .attention(UPDATED_ATTENTION)
            .type(UPDATED_TYPE);
        NotificationDTO notificationDTO = notificationMapper.toDto(updatedNotification);

        restNotificationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, notificationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notificationDTO))
            )
            .andExpect(status().isOk());

        // Validate the Notification in the database
        List<Notification> notificationList = notificationRepository.findAll();
        assertThat(notificationList).hasSize(databaseSizeBeforeUpdate);
        Notification testNotification = notificationList.get(notificationList.size() - 1);
        assertThat(testNotification.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testNotification.getCallUpDate()).isEqualTo(UPDATED_CALL_UP_DATE);
        assertThat(testNotification.getReadStatus()).isEqualTo(UPDATED_READ_STATUS);
        assertThat(testNotification.getAttention()).isEqualTo(UPDATED_ATTENTION);
        assertThat(testNotification.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingNotification() throws Exception {
        int databaseSizeBeforeUpdate = notificationRepository.findAll().size();
        notification.setId(longCount.incrementAndGet());

        // Create the Notification
        NotificationDTO notificationDTO = notificationMapper.toDto(notification);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNotificationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, notificationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notificationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Notification in the database
        List<Notification> notificationList = notificationRepository.findAll();
        assertThat(notificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNotification() throws Exception {
        int databaseSizeBeforeUpdate = notificationRepository.findAll().size();
        notification.setId(longCount.incrementAndGet());

        // Create the Notification
        NotificationDTO notificationDTO = notificationMapper.toDto(notification);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotificationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notificationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Notification in the database
        List<Notification> notificationList = notificationRepository.findAll();
        assertThat(notificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNotification() throws Exception {
        int databaseSizeBeforeUpdate = notificationRepository.findAll().size();
        notification.setId(longCount.incrementAndGet());

        // Create the Notification
        NotificationDTO notificationDTO = notificationMapper.toDto(notification);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotificationMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(notificationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Notification in the database
        List<Notification> notificationList = notificationRepository.findAll();
        assertThat(notificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNotificationWithPatch() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        int databaseSizeBeforeUpdate = notificationRepository.findAll().size();

        // Update the notification using partial update
        Notification partialUpdatedNotification = new Notification();
        partialUpdatedNotification.setId(notification.getId());

        partialUpdatedNotification.message(UPDATED_MESSAGE).callUpDate(UPDATED_CALL_UP_DATE);

        restNotificationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNotification.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNotification))
            )
            .andExpect(status().isOk());

        // Validate the Notification in the database
        List<Notification> notificationList = notificationRepository.findAll();
        assertThat(notificationList).hasSize(databaseSizeBeforeUpdate);
        Notification testNotification = notificationList.get(notificationList.size() - 1);
        assertThat(testNotification.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testNotification.getCallUpDate()).isEqualTo(UPDATED_CALL_UP_DATE);
        assertThat(testNotification.getReadStatus()).isEqualTo(DEFAULT_READ_STATUS);
        assertThat(testNotification.getAttention()).isEqualTo(DEFAULT_ATTENTION);
        assertThat(testNotification.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateNotificationWithPatch() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        int databaseSizeBeforeUpdate = notificationRepository.findAll().size();

        // Update the notification using partial update
        Notification partialUpdatedNotification = new Notification();
        partialUpdatedNotification.setId(notification.getId());

        partialUpdatedNotification
            .message(UPDATED_MESSAGE)
            .callUpDate(UPDATED_CALL_UP_DATE)
            .readStatus(UPDATED_READ_STATUS)
            .attention(UPDATED_ATTENTION)
            .type(UPDATED_TYPE);

        restNotificationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNotification.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNotification))
            )
            .andExpect(status().isOk());

        // Validate the Notification in the database
        List<Notification> notificationList = notificationRepository.findAll();
        assertThat(notificationList).hasSize(databaseSizeBeforeUpdate);
        Notification testNotification = notificationList.get(notificationList.size() - 1);
        assertThat(testNotification.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testNotification.getCallUpDate()).isEqualTo(UPDATED_CALL_UP_DATE);
        assertThat(testNotification.getReadStatus()).isEqualTo(UPDATED_READ_STATUS);
        assertThat(testNotification.getAttention()).isEqualTo(UPDATED_ATTENTION);
        assertThat(testNotification.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingNotification() throws Exception {
        int databaseSizeBeforeUpdate = notificationRepository.findAll().size();
        notification.setId(longCount.incrementAndGet());

        // Create the Notification
        NotificationDTO notificationDTO = notificationMapper.toDto(notification);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNotificationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, notificationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(notificationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Notification in the database
        List<Notification> notificationList = notificationRepository.findAll();
        assertThat(notificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNotification() throws Exception {
        int databaseSizeBeforeUpdate = notificationRepository.findAll().size();
        notification.setId(longCount.incrementAndGet());

        // Create the Notification
        NotificationDTO notificationDTO = notificationMapper.toDto(notification);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotificationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(notificationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Notification in the database
        List<Notification> notificationList = notificationRepository.findAll();
        assertThat(notificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNotification() throws Exception {
        int databaseSizeBeforeUpdate = notificationRepository.findAll().size();
        notification.setId(longCount.incrementAndGet());

        // Create the Notification
        NotificationDTO notificationDTO = notificationMapper.toDto(notification);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotificationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(notificationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Notification in the database
        List<Notification> notificationList = notificationRepository.findAll();
        assertThat(notificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNotification() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        int databaseSizeBeforeDelete = notificationRepository.findAll().size();

        // Delete the notification
        restNotificationMockMvc
            .perform(delete(ENTITY_API_URL_ID, notification.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Notification> notificationList = notificationRepository.findAll();
        assertThat(notificationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
