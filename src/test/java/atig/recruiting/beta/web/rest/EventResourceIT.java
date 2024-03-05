package atig.recruiting.beta.web.rest;

import static atig.recruiting.beta.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import atig.recruiting.beta.IntegrationTest;
import atig.recruiting.beta.domain.Event;
import atig.recruiting.beta.domain.User;
import atig.recruiting.beta.domain.enumeration.EnumPriority;
import atig.recruiting.beta.repository.EventRepository;
import atig.recruiting.beta.service.dto.EventDTO;
import atig.recruiting.beta.service.mapper.EventMapper;
import jakarta.persistence.EntityManager;
import java.time.Duration;
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
 * Integration tests for the {@link EventResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EventResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_EXTERNAL_PARTICIPANTS = "AAAAAAAAAA";
    private static final String UPDATED_EXTERNAL_PARTICIPANTS = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final Duration DEFAULT_DURATION = Duration.ofHours(6);
    private static final Duration UPDATED_DURATION = Duration.ofHours(12);
    private static final Duration SMALLER_DURATION = Duration.ofHours(5);

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final EnumPriority DEFAULT_PRIORITY = EnumPriority.LOW;
    private static final EnumPriority UPDATED_PRIORITY = EnumPriority.MEDUIM;

    private static final String ENTITY_API_URL = "/api/events";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventMapper eventMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEventMockMvc;

    private Event event;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Event createEntity(EntityManager em) {
        Event event = new Event()
            .title(DEFAULT_TITLE)
            .externalParticipants(DEFAULT_EXTERNAL_PARTICIPANTS)
            .date(DEFAULT_DATE)
            .duration(DEFAULT_DURATION)
            .description(DEFAULT_DESCRIPTION)
            .priority(DEFAULT_PRIORITY);
        return event;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Event createUpdatedEntity(EntityManager em) {
        Event event = new Event()
            .title(UPDATED_TITLE)
            .externalParticipants(UPDATED_EXTERNAL_PARTICIPANTS)
            .date(UPDATED_DATE)
            .duration(UPDATED_DURATION)
            .description(UPDATED_DESCRIPTION)
            .priority(UPDATED_PRIORITY);
        return event;
    }

    @BeforeEach
    public void initTest() {
        event = createEntity(em);
    }

    @Test
    @Transactional
    void createEvent() throws Exception {
        int databaseSizeBeforeCreate = eventRepository.findAll().size();
        // Create the Event
        EventDTO eventDTO = eventMapper.toDto(event);
        restEventMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eventDTO)))
            .andExpect(status().isCreated());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeCreate + 1);
        Event testEvent = eventList.get(eventList.size() - 1);
        assertThat(testEvent.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testEvent.getExternalParticipants()).isEqualTo(DEFAULT_EXTERNAL_PARTICIPANTS);
        assertThat(testEvent.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testEvent.getDuration()).isEqualTo(DEFAULT_DURATION);
        assertThat(testEvent.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testEvent.getPriority()).isEqualTo(DEFAULT_PRIORITY);
    }

    @Test
    @Transactional
    void createEventWithExistingId() throws Exception {
        // Create the Event with an existing ID
        event.setId(1L);
        EventDTO eventDTO = eventMapper.toDto(event);

        int databaseSizeBeforeCreate = eventRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEventMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eventDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEvents() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList
        restEventMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(event.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].externalParticipants").value(hasItem(DEFAULT_EXTERNAL_PARTICIPANTS)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY.toString())));
    }

    @Test
    @Transactional
    void getEvent() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get the event
        restEventMockMvc
            .perform(get(ENTITY_API_URL_ID, event.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(event.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.externalParticipants").value(DEFAULT_EXTERNAL_PARTICIPANTS))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.priority").value(DEFAULT_PRIORITY.toString()));
    }

    @Test
    @Transactional
    void getEventsByIdFiltering() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        Long id = event.getId();

        defaultEventShouldBeFound("id.equals=" + id);
        defaultEventShouldNotBeFound("id.notEquals=" + id);

        defaultEventShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEventShouldNotBeFound("id.greaterThan=" + id);

        defaultEventShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEventShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEventsByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where title equals to DEFAULT_TITLE
        defaultEventShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the eventList where title equals to UPDATED_TITLE
        defaultEventShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllEventsByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultEventShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the eventList where title equals to UPDATED_TITLE
        defaultEventShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllEventsByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where title is not null
        defaultEventShouldBeFound("title.specified=true");

        // Get all the eventList where title is null
        defaultEventShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    void getAllEventsByTitleContainsSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where title contains DEFAULT_TITLE
        defaultEventShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the eventList where title contains UPDATED_TITLE
        defaultEventShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllEventsByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where title does not contain DEFAULT_TITLE
        defaultEventShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the eventList where title does not contain UPDATED_TITLE
        defaultEventShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllEventsByExternalParticipantsIsEqualToSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where externalParticipants equals to DEFAULT_EXTERNAL_PARTICIPANTS
        defaultEventShouldBeFound("externalParticipants.equals=" + DEFAULT_EXTERNAL_PARTICIPANTS);

        // Get all the eventList where externalParticipants equals to UPDATED_EXTERNAL_PARTICIPANTS
        defaultEventShouldNotBeFound("externalParticipants.equals=" + UPDATED_EXTERNAL_PARTICIPANTS);
    }

    @Test
    @Transactional
    void getAllEventsByExternalParticipantsIsInShouldWork() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where externalParticipants in DEFAULT_EXTERNAL_PARTICIPANTS or UPDATED_EXTERNAL_PARTICIPANTS
        defaultEventShouldBeFound("externalParticipants.in=" + DEFAULT_EXTERNAL_PARTICIPANTS + "," + UPDATED_EXTERNAL_PARTICIPANTS);

        // Get all the eventList where externalParticipants equals to UPDATED_EXTERNAL_PARTICIPANTS
        defaultEventShouldNotBeFound("externalParticipants.in=" + UPDATED_EXTERNAL_PARTICIPANTS);
    }

    @Test
    @Transactional
    void getAllEventsByExternalParticipantsIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where externalParticipants is not null
        defaultEventShouldBeFound("externalParticipants.specified=true");

        // Get all the eventList where externalParticipants is null
        defaultEventShouldNotBeFound("externalParticipants.specified=false");
    }

    @Test
    @Transactional
    void getAllEventsByExternalParticipantsContainsSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where externalParticipants contains DEFAULT_EXTERNAL_PARTICIPANTS
        defaultEventShouldBeFound("externalParticipants.contains=" + DEFAULT_EXTERNAL_PARTICIPANTS);

        // Get all the eventList where externalParticipants contains UPDATED_EXTERNAL_PARTICIPANTS
        defaultEventShouldNotBeFound("externalParticipants.contains=" + UPDATED_EXTERNAL_PARTICIPANTS);
    }

    @Test
    @Transactional
    void getAllEventsByExternalParticipantsNotContainsSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where externalParticipants does not contain DEFAULT_EXTERNAL_PARTICIPANTS
        defaultEventShouldNotBeFound("externalParticipants.doesNotContain=" + DEFAULT_EXTERNAL_PARTICIPANTS);

        // Get all the eventList where externalParticipants does not contain UPDATED_EXTERNAL_PARTICIPANTS
        defaultEventShouldBeFound("externalParticipants.doesNotContain=" + UPDATED_EXTERNAL_PARTICIPANTS);
    }

    @Test
    @Transactional
    void getAllEventsByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where date equals to DEFAULT_DATE
        defaultEventShouldBeFound("date.equals=" + DEFAULT_DATE);

        // Get all the eventList where date equals to UPDATED_DATE
        defaultEventShouldNotBeFound("date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllEventsByDateIsInShouldWork() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where date in DEFAULT_DATE or UPDATED_DATE
        defaultEventShouldBeFound("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE);

        // Get all the eventList where date equals to UPDATED_DATE
        defaultEventShouldNotBeFound("date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllEventsByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where date is not null
        defaultEventShouldBeFound("date.specified=true");

        // Get all the eventList where date is null
        defaultEventShouldNotBeFound("date.specified=false");
    }

    @Test
    @Transactional
    void getAllEventsByDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where date is greater than or equal to DEFAULT_DATE
        defaultEventShouldBeFound("date.greaterThanOrEqual=" + DEFAULT_DATE);

        // Get all the eventList where date is greater than or equal to UPDATED_DATE
        defaultEventShouldNotBeFound("date.greaterThanOrEqual=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllEventsByDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where date is less than or equal to DEFAULT_DATE
        defaultEventShouldBeFound("date.lessThanOrEqual=" + DEFAULT_DATE);

        // Get all the eventList where date is less than or equal to SMALLER_DATE
        defaultEventShouldNotBeFound("date.lessThanOrEqual=" + SMALLER_DATE);
    }

    @Test
    @Transactional
    void getAllEventsByDateIsLessThanSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where date is less than DEFAULT_DATE
        defaultEventShouldNotBeFound("date.lessThan=" + DEFAULT_DATE);

        // Get all the eventList where date is less than UPDATED_DATE
        defaultEventShouldBeFound("date.lessThan=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllEventsByDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where date is greater than DEFAULT_DATE
        defaultEventShouldNotBeFound("date.greaterThan=" + DEFAULT_DATE);

        // Get all the eventList where date is greater than SMALLER_DATE
        defaultEventShouldBeFound("date.greaterThan=" + SMALLER_DATE);
    }

    @Test
    @Transactional
    void getAllEventsByDurationIsEqualToSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where duration equals to DEFAULT_DURATION
        defaultEventShouldBeFound("duration.equals=" + DEFAULT_DURATION);

        // Get all the eventList where duration equals to UPDATED_DURATION
        defaultEventShouldNotBeFound("duration.equals=" + UPDATED_DURATION);
    }

    @Test
    @Transactional
    void getAllEventsByDurationIsInShouldWork() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where duration in DEFAULT_DURATION or UPDATED_DURATION
        defaultEventShouldBeFound("duration.in=" + DEFAULT_DURATION + "," + UPDATED_DURATION);

        // Get all the eventList where duration equals to UPDATED_DURATION
        defaultEventShouldNotBeFound("duration.in=" + UPDATED_DURATION);
    }

    @Test
    @Transactional
    void getAllEventsByDurationIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where duration is not null
        defaultEventShouldBeFound("duration.specified=true");

        // Get all the eventList where duration is null
        defaultEventShouldNotBeFound("duration.specified=false");
    }

    @Test
    @Transactional
    void getAllEventsByDurationIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where duration is greater than or equal to DEFAULT_DURATION
        defaultEventShouldBeFound("duration.greaterThanOrEqual=" + DEFAULT_DURATION);

        // Get all the eventList where duration is greater than or equal to UPDATED_DURATION
        defaultEventShouldNotBeFound("duration.greaterThanOrEqual=" + UPDATED_DURATION);
    }

    @Test
    @Transactional
    void getAllEventsByDurationIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where duration is less than or equal to DEFAULT_DURATION
        defaultEventShouldBeFound("duration.lessThanOrEqual=" + DEFAULT_DURATION);

        // Get all the eventList where duration is less than or equal to SMALLER_DURATION
        defaultEventShouldNotBeFound("duration.lessThanOrEqual=" + SMALLER_DURATION);
    }

    @Test
    @Transactional
    void getAllEventsByDurationIsLessThanSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where duration is less than DEFAULT_DURATION
        defaultEventShouldNotBeFound("duration.lessThan=" + DEFAULT_DURATION);

        // Get all the eventList where duration is less than UPDATED_DURATION
        defaultEventShouldBeFound("duration.lessThan=" + UPDATED_DURATION);
    }

    @Test
    @Transactional
    void getAllEventsByDurationIsGreaterThanSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where duration is greater than DEFAULT_DURATION
        defaultEventShouldNotBeFound("duration.greaterThan=" + DEFAULT_DURATION);

        // Get all the eventList where duration is greater than SMALLER_DURATION
        defaultEventShouldBeFound("duration.greaterThan=" + SMALLER_DURATION);
    }

    @Test
    @Transactional
    void getAllEventsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where description equals to DEFAULT_DESCRIPTION
        defaultEventShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the eventList where description equals to UPDATED_DESCRIPTION
        defaultEventShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllEventsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultEventShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the eventList where description equals to UPDATED_DESCRIPTION
        defaultEventShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllEventsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where description is not null
        defaultEventShouldBeFound("description.specified=true");

        // Get all the eventList where description is null
        defaultEventShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllEventsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where description contains DEFAULT_DESCRIPTION
        defaultEventShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the eventList where description contains UPDATED_DESCRIPTION
        defaultEventShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllEventsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where description does not contain DEFAULT_DESCRIPTION
        defaultEventShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the eventList where description does not contain UPDATED_DESCRIPTION
        defaultEventShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllEventsByPriorityIsEqualToSomething() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where priority equals to DEFAULT_PRIORITY
        defaultEventShouldBeFound("priority.equals=" + DEFAULT_PRIORITY);

        // Get all the eventList where priority equals to UPDATED_PRIORITY
        defaultEventShouldNotBeFound("priority.equals=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    void getAllEventsByPriorityIsInShouldWork() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where priority in DEFAULT_PRIORITY or UPDATED_PRIORITY
        defaultEventShouldBeFound("priority.in=" + DEFAULT_PRIORITY + "," + UPDATED_PRIORITY);

        // Get all the eventList where priority equals to UPDATED_PRIORITY
        defaultEventShouldNotBeFound("priority.in=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    void getAllEventsByPriorityIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList where priority is not null
        defaultEventShouldBeFound("priority.specified=true");

        // Get all the eventList where priority is null
        defaultEventShouldNotBeFound("priority.specified=false");
    }

    @Test
    @Transactional
    void getAllEventsByUserIsEqualToSomething() throws Exception {
        User user;
        if (TestUtil.findAll(em, User.class).isEmpty()) {
            eventRepository.saveAndFlush(event);
            user = UserResourceIT.createEntity(em);
        } else {
            user = TestUtil.findAll(em, User.class).get(0);
        }
        em.persist(user);
        em.flush();
        event.setUser(user);
        eventRepository.saveAndFlush(event);
        Long userId = user.getId();
        // Get all the eventList where user equals to userId
        defaultEventShouldBeFound("userId.equals=" + userId);

        // Get all the eventList where user equals to (userId + 1)
        defaultEventShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEventShouldBeFound(String filter) throws Exception {
        restEventMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(event.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].externalParticipants").value(hasItem(DEFAULT_EXTERNAL_PARTICIPANTS)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY.toString())));

        // Check, that the count call also returns 1
        restEventMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEventShouldNotBeFound(String filter) throws Exception {
        restEventMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEventMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEvent() throws Exception {
        // Get the event
        restEventMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEvent() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        int databaseSizeBeforeUpdate = eventRepository.findAll().size();

        // Update the event
        Event updatedEvent = eventRepository.findById(event.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEvent are not directly saved in db
        em.detach(updatedEvent);
        updatedEvent
            .title(UPDATED_TITLE)
            .externalParticipants(UPDATED_EXTERNAL_PARTICIPANTS)
            .date(UPDATED_DATE)
            .duration(UPDATED_DURATION)
            .description(UPDATED_DESCRIPTION)
            .priority(UPDATED_PRIORITY);
        EventDTO eventDTO = eventMapper.toDto(updatedEvent);

        restEventMockMvc
            .perform(
                put(ENTITY_API_URL_ID, eventDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eventDTO))
            )
            .andExpect(status().isOk());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeUpdate);
        Event testEvent = eventList.get(eventList.size() - 1);
        assertThat(testEvent.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testEvent.getExternalParticipants()).isEqualTo(UPDATED_EXTERNAL_PARTICIPANTS);
        assertThat(testEvent.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testEvent.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testEvent.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testEvent.getPriority()).isEqualTo(UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    void putNonExistingEvent() throws Exception {
        int databaseSizeBeforeUpdate = eventRepository.findAll().size();
        event.setId(longCount.incrementAndGet());

        // Create the Event
        EventDTO eventDTO = eventMapper.toDto(event);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventMockMvc
            .perform(
                put(ENTITY_API_URL_ID, eventDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eventDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEvent() throws Exception {
        int databaseSizeBeforeUpdate = eventRepository.findAll().size();
        event.setId(longCount.incrementAndGet());

        // Create the Event
        EventDTO eventDTO = eventMapper.toDto(event);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eventDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEvent() throws Exception {
        int databaseSizeBeforeUpdate = eventRepository.findAll().size();
        event.setId(longCount.incrementAndGet());

        // Create the Event
        EventDTO eventDTO = eventMapper.toDto(event);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eventDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEventWithPatch() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        int databaseSizeBeforeUpdate = eventRepository.findAll().size();

        // Update the event using partial update
        Event partialUpdatedEvent = new Event();
        partialUpdatedEvent.setId(event.getId());

        partialUpdatedEvent.date(UPDATED_DATE).duration(UPDATED_DURATION);

        restEventMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEvent.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEvent))
            )
            .andExpect(status().isOk());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeUpdate);
        Event testEvent = eventList.get(eventList.size() - 1);
        assertThat(testEvent.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testEvent.getExternalParticipants()).isEqualTo(DEFAULT_EXTERNAL_PARTICIPANTS);
        assertThat(testEvent.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testEvent.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testEvent.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testEvent.getPriority()).isEqualTo(DEFAULT_PRIORITY);
    }

    @Test
    @Transactional
    void fullUpdateEventWithPatch() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        int databaseSizeBeforeUpdate = eventRepository.findAll().size();

        // Update the event using partial update
        Event partialUpdatedEvent = new Event();
        partialUpdatedEvent.setId(event.getId());

        partialUpdatedEvent
            .title(UPDATED_TITLE)
            .externalParticipants(UPDATED_EXTERNAL_PARTICIPANTS)
            .date(UPDATED_DATE)
            .duration(UPDATED_DURATION)
            .description(UPDATED_DESCRIPTION)
            .priority(UPDATED_PRIORITY);

        restEventMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEvent.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEvent))
            )
            .andExpect(status().isOk());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeUpdate);
        Event testEvent = eventList.get(eventList.size() - 1);
        assertThat(testEvent.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testEvent.getExternalParticipants()).isEqualTo(UPDATED_EXTERNAL_PARTICIPANTS);
        assertThat(testEvent.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testEvent.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testEvent.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testEvent.getPriority()).isEqualTo(UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    void patchNonExistingEvent() throws Exception {
        int databaseSizeBeforeUpdate = eventRepository.findAll().size();
        event.setId(longCount.incrementAndGet());

        // Create the Event
        EventDTO eventDTO = eventMapper.toDto(event);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, eventDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(eventDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEvent() throws Exception {
        int databaseSizeBeforeUpdate = eventRepository.findAll().size();
        event.setId(longCount.incrementAndGet());

        // Create the Event
        EventDTO eventDTO = eventMapper.toDto(event);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(eventDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEvent() throws Exception {
        int databaseSizeBeforeUpdate = eventRepository.findAll().size();
        event.setId(longCount.incrementAndGet());

        // Create the Event
        EventDTO eventDTO = eventMapper.toDto(event);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(eventDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEvent() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        int databaseSizeBeforeDelete = eventRepository.findAll().size();

        // Delete the event
        restEventMockMvc
            .perform(delete(ENTITY_API_URL_ID, event.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
