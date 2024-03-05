package atig.recruiting.beta.web.rest;

import static atig.recruiting.beta.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import atig.recruiting.beta.IntegrationTest;
import atig.recruiting.beta.domain.Email;
import atig.recruiting.beta.domain.SubEmail;
import atig.recruiting.beta.domain.enumeration.EnumEmailType;
import atig.recruiting.beta.repository.SubEmailRepository;
import atig.recruiting.beta.service.dto.SubEmailDTO;
import atig.recruiting.beta.service.mapper.SubEmailMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Base64;
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
 * Integration tests for the {@link SubEmailResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SubEmailResourceIT {

    private static final String DEFAULT_FROM = "AAAAAAAAAA";
    private static final String UPDATED_FROM = "BBBBBBBBBB";

    private static final String DEFAULT_RECIPIENTS = "AAAAAAAAAA";
    private static final String UPDATED_RECIPIENTS = "BBBBBBBBBB";

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    private static final EnumEmailType DEFAULT_TYPE = EnumEmailType.INBOX;
    private static final EnumEmailType UPDATED_TYPE = EnumEmailType.SENT;

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_SNOOZED_TO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_SNOOZED_TO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_SNOOZED_TO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String DEFAULT_SIGNATURE_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_SIGNATURE_TEXT = "BBBBBBBBBB";

    private static final byte[] DEFAULT_SIGNATURE_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_SIGNATURE_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_SIGNATURE_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_SIGNATURE_IMAGE_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/sub-emails";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SubEmailRepository subEmailRepository;

    @Autowired
    private SubEmailMapper subEmailMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSubEmailMockMvc;

    private SubEmail subEmail;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SubEmail createEntity(EntityManager em) {
        SubEmail subEmail = new SubEmail()
            .from(DEFAULT_FROM)
            .recipients(DEFAULT_RECIPIENTS)
            .text(DEFAULT_TEXT)
            .type(DEFAULT_TYPE)
            .date(DEFAULT_DATE)
            .snoozedTo(DEFAULT_SNOOZED_TO)
            .signatureText(DEFAULT_SIGNATURE_TEXT)
            .signatureImage(DEFAULT_SIGNATURE_IMAGE)
            .signatureImageContentType(DEFAULT_SIGNATURE_IMAGE_CONTENT_TYPE);
        return subEmail;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SubEmail createUpdatedEntity(EntityManager em) {
        SubEmail subEmail = new SubEmail()
            .from(UPDATED_FROM)
            .recipients(UPDATED_RECIPIENTS)
            .text(UPDATED_TEXT)
            .type(UPDATED_TYPE)
            .date(UPDATED_DATE)
            .snoozedTo(UPDATED_SNOOZED_TO)
            .signatureText(UPDATED_SIGNATURE_TEXT)
            .signatureImage(UPDATED_SIGNATURE_IMAGE)
            .signatureImageContentType(UPDATED_SIGNATURE_IMAGE_CONTENT_TYPE);
        return subEmail;
    }

    @BeforeEach
    public void initTest() {
        subEmail = createEntity(em);
    }

    @Test
    @Transactional
    void createSubEmail() throws Exception {
        int databaseSizeBeforeCreate = subEmailRepository.findAll().size();
        // Create the SubEmail
        SubEmailDTO subEmailDTO = subEmailMapper.toDto(subEmail);
        restSubEmailMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subEmailDTO)))
            .andExpect(status().isCreated());

        // Validate the SubEmail in the database
        List<SubEmail> subEmailList = subEmailRepository.findAll();
        assertThat(subEmailList).hasSize(databaseSizeBeforeCreate + 1);
        SubEmail testSubEmail = subEmailList.get(subEmailList.size() - 1);
        assertThat(testSubEmail.getFrom()).isEqualTo(DEFAULT_FROM);
        assertThat(testSubEmail.getRecipients()).isEqualTo(DEFAULT_RECIPIENTS);
        assertThat(testSubEmail.getText()).isEqualTo(DEFAULT_TEXT);
        assertThat(testSubEmail.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testSubEmail.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testSubEmail.getSnoozedTo()).isEqualTo(DEFAULT_SNOOZED_TO);
        assertThat(testSubEmail.getSignatureText()).isEqualTo(DEFAULT_SIGNATURE_TEXT);
        assertThat(testSubEmail.getSignatureImage()).isEqualTo(DEFAULT_SIGNATURE_IMAGE);
        assertThat(testSubEmail.getSignatureImageContentType()).isEqualTo(DEFAULT_SIGNATURE_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createSubEmailWithExistingId() throws Exception {
        // Create the SubEmail with an existing ID
        subEmail.setId(1L);
        SubEmailDTO subEmailDTO = subEmailMapper.toDto(subEmail);

        int databaseSizeBeforeCreate = subEmailRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubEmailMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subEmailDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SubEmail in the database
        List<SubEmail> subEmailList = subEmailRepository.findAll();
        assertThat(subEmailList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSubEmails() throws Exception {
        // Initialize the database
        subEmailRepository.saveAndFlush(subEmail);

        // Get all the subEmailList
        restSubEmailMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subEmail.getId().intValue())))
            .andExpect(jsonPath("$.[*].from").value(hasItem(DEFAULT_FROM)))
            .andExpect(jsonPath("$.[*].recipients").value(hasItem(DEFAULT_RECIPIENTS)))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))))
            .andExpect(jsonPath("$.[*].snoozedTo").value(hasItem(sameInstant(DEFAULT_SNOOZED_TO))))
            .andExpect(jsonPath("$.[*].signatureText").value(hasItem(DEFAULT_SIGNATURE_TEXT)))
            .andExpect(jsonPath("$.[*].signatureImageContentType").value(hasItem(DEFAULT_SIGNATURE_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].signatureImage").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_SIGNATURE_IMAGE))));
    }

    @Test
    @Transactional
    void getSubEmail() throws Exception {
        // Initialize the database
        subEmailRepository.saveAndFlush(subEmail);

        // Get the subEmail
        restSubEmailMockMvc
            .perform(get(ENTITY_API_URL_ID, subEmail.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(subEmail.getId().intValue()))
            .andExpect(jsonPath("$.from").value(DEFAULT_FROM))
            .andExpect(jsonPath("$.recipients").value(DEFAULT_RECIPIENTS))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)))
            .andExpect(jsonPath("$.snoozedTo").value(sameInstant(DEFAULT_SNOOZED_TO)))
            .andExpect(jsonPath("$.signatureText").value(DEFAULT_SIGNATURE_TEXT))
            .andExpect(jsonPath("$.signatureImageContentType").value(DEFAULT_SIGNATURE_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.signatureImage").value(Base64.getEncoder().encodeToString(DEFAULT_SIGNATURE_IMAGE)));
    }

    @Test
    @Transactional
    void getSubEmailsByIdFiltering() throws Exception {
        // Initialize the database
        subEmailRepository.saveAndFlush(subEmail);

        Long id = subEmail.getId();

        defaultSubEmailShouldBeFound("id.equals=" + id);
        defaultSubEmailShouldNotBeFound("id.notEquals=" + id);

        defaultSubEmailShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSubEmailShouldNotBeFound("id.greaterThan=" + id);

        defaultSubEmailShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSubEmailShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSubEmailsByFromIsEqualToSomething() throws Exception {
        // Initialize the database
        subEmailRepository.saveAndFlush(subEmail);

        // Get all the subEmailList where from equals to DEFAULT_FROM
        defaultSubEmailShouldBeFound("from.equals=" + DEFAULT_FROM);

        // Get all the subEmailList where from equals to UPDATED_FROM
        defaultSubEmailShouldNotBeFound("from.equals=" + UPDATED_FROM);
    }

    @Test
    @Transactional
    void getAllSubEmailsByFromIsInShouldWork() throws Exception {
        // Initialize the database
        subEmailRepository.saveAndFlush(subEmail);

        // Get all the subEmailList where from in DEFAULT_FROM or UPDATED_FROM
        defaultSubEmailShouldBeFound("from.in=" + DEFAULT_FROM + "," + UPDATED_FROM);

        // Get all the subEmailList where from equals to UPDATED_FROM
        defaultSubEmailShouldNotBeFound("from.in=" + UPDATED_FROM);
    }

    @Test
    @Transactional
    void getAllSubEmailsByFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        subEmailRepository.saveAndFlush(subEmail);

        // Get all the subEmailList where from is not null
        defaultSubEmailShouldBeFound("from.specified=true");

        // Get all the subEmailList where from is null
        defaultSubEmailShouldNotBeFound("from.specified=false");
    }

    @Test
    @Transactional
    void getAllSubEmailsByFromContainsSomething() throws Exception {
        // Initialize the database
        subEmailRepository.saveAndFlush(subEmail);

        // Get all the subEmailList where from contains DEFAULT_FROM
        defaultSubEmailShouldBeFound("from.contains=" + DEFAULT_FROM);

        // Get all the subEmailList where from contains UPDATED_FROM
        defaultSubEmailShouldNotBeFound("from.contains=" + UPDATED_FROM);
    }

    @Test
    @Transactional
    void getAllSubEmailsByFromNotContainsSomething() throws Exception {
        // Initialize the database
        subEmailRepository.saveAndFlush(subEmail);

        // Get all the subEmailList where from does not contain DEFAULT_FROM
        defaultSubEmailShouldNotBeFound("from.doesNotContain=" + DEFAULT_FROM);

        // Get all the subEmailList where from does not contain UPDATED_FROM
        defaultSubEmailShouldBeFound("from.doesNotContain=" + UPDATED_FROM);
    }

    @Test
    @Transactional
    void getAllSubEmailsByRecipientsIsEqualToSomething() throws Exception {
        // Initialize the database
        subEmailRepository.saveAndFlush(subEmail);

        // Get all the subEmailList where recipients equals to DEFAULT_RECIPIENTS
        defaultSubEmailShouldBeFound("recipients.equals=" + DEFAULT_RECIPIENTS);

        // Get all the subEmailList where recipients equals to UPDATED_RECIPIENTS
        defaultSubEmailShouldNotBeFound("recipients.equals=" + UPDATED_RECIPIENTS);
    }

    @Test
    @Transactional
    void getAllSubEmailsByRecipientsIsInShouldWork() throws Exception {
        // Initialize the database
        subEmailRepository.saveAndFlush(subEmail);

        // Get all the subEmailList where recipients in DEFAULT_RECIPIENTS or UPDATED_RECIPIENTS
        defaultSubEmailShouldBeFound("recipients.in=" + DEFAULT_RECIPIENTS + "," + UPDATED_RECIPIENTS);

        // Get all the subEmailList where recipients equals to UPDATED_RECIPIENTS
        defaultSubEmailShouldNotBeFound("recipients.in=" + UPDATED_RECIPIENTS);
    }

    @Test
    @Transactional
    void getAllSubEmailsByRecipientsIsNullOrNotNull() throws Exception {
        // Initialize the database
        subEmailRepository.saveAndFlush(subEmail);

        // Get all the subEmailList where recipients is not null
        defaultSubEmailShouldBeFound("recipients.specified=true");

        // Get all the subEmailList where recipients is null
        defaultSubEmailShouldNotBeFound("recipients.specified=false");
    }

    @Test
    @Transactional
    void getAllSubEmailsByRecipientsContainsSomething() throws Exception {
        // Initialize the database
        subEmailRepository.saveAndFlush(subEmail);

        // Get all the subEmailList where recipients contains DEFAULT_RECIPIENTS
        defaultSubEmailShouldBeFound("recipients.contains=" + DEFAULT_RECIPIENTS);

        // Get all the subEmailList where recipients contains UPDATED_RECIPIENTS
        defaultSubEmailShouldNotBeFound("recipients.contains=" + UPDATED_RECIPIENTS);
    }

    @Test
    @Transactional
    void getAllSubEmailsByRecipientsNotContainsSomething() throws Exception {
        // Initialize the database
        subEmailRepository.saveAndFlush(subEmail);

        // Get all the subEmailList where recipients does not contain DEFAULT_RECIPIENTS
        defaultSubEmailShouldNotBeFound("recipients.doesNotContain=" + DEFAULT_RECIPIENTS);

        // Get all the subEmailList where recipients does not contain UPDATED_RECIPIENTS
        defaultSubEmailShouldBeFound("recipients.doesNotContain=" + UPDATED_RECIPIENTS);
    }

    @Test
    @Transactional
    void getAllSubEmailsByTextIsEqualToSomething() throws Exception {
        // Initialize the database
        subEmailRepository.saveAndFlush(subEmail);

        // Get all the subEmailList where text equals to DEFAULT_TEXT
        defaultSubEmailShouldBeFound("text.equals=" + DEFAULT_TEXT);

        // Get all the subEmailList where text equals to UPDATED_TEXT
        defaultSubEmailShouldNotBeFound("text.equals=" + UPDATED_TEXT);
    }

    @Test
    @Transactional
    void getAllSubEmailsByTextIsInShouldWork() throws Exception {
        // Initialize the database
        subEmailRepository.saveAndFlush(subEmail);

        // Get all the subEmailList where text in DEFAULT_TEXT or UPDATED_TEXT
        defaultSubEmailShouldBeFound("text.in=" + DEFAULT_TEXT + "," + UPDATED_TEXT);

        // Get all the subEmailList where text equals to UPDATED_TEXT
        defaultSubEmailShouldNotBeFound("text.in=" + UPDATED_TEXT);
    }

    @Test
    @Transactional
    void getAllSubEmailsByTextIsNullOrNotNull() throws Exception {
        // Initialize the database
        subEmailRepository.saveAndFlush(subEmail);

        // Get all the subEmailList where text is not null
        defaultSubEmailShouldBeFound("text.specified=true");

        // Get all the subEmailList where text is null
        defaultSubEmailShouldNotBeFound("text.specified=false");
    }

    @Test
    @Transactional
    void getAllSubEmailsByTextContainsSomething() throws Exception {
        // Initialize the database
        subEmailRepository.saveAndFlush(subEmail);

        // Get all the subEmailList where text contains DEFAULT_TEXT
        defaultSubEmailShouldBeFound("text.contains=" + DEFAULT_TEXT);

        // Get all the subEmailList where text contains UPDATED_TEXT
        defaultSubEmailShouldNotBeFound("text.contains=" + UPDATED_TEXT);
    }

    @Test
    @Transactional
    void getAllSubEmailsByTextNotContainsSomething() throws Exception {
        // Initialize the database
        subEmailRepository.saveAndFlush(subEmail);

        // Get all the subEmailList where text does not contain DEFAULT_TEXT
        defaultSubEmailShouldNotBeFound("text.doesNotContain=" + DEFAULT_TEXT);

        // Get all the subEmailList where text does not contain UPDATED_TEXT
        defaultSubEmailShouldBeFound("text.doesNotContain=" + UPDATED_TEXT);
    }

    @Test
    @Transactional
    void getAllSubEmailsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        subEmailRepository.saveAndFlush(subEmail);

        // Get all the subEmailList where type equals to DEFAULT_TYPE
        defaultSubEmailShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the subEmailList where type equals to UPDATED_TYPE
        defaultSubEmailShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllSubEmailsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        subEmailRepository.saveAndFlush(subEmail);

        // Get all the subEmailList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultSubEmailShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the subEmailList where type equals to UPDATED_TYPE
        defaultSubEmailShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllSubEmailsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        subEmailRepository.saveAndFlush(subEmail);

        // Get all the subEmailList where type is not null
        defaultSubEmailShouldBeFound("type.specified=true");

        // Get all the subEmailList where type is null
        defaultSubEmailShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllSubEmailsByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        subEmailRepository.saveAndFlush(subEmail);

        // Get all the subEmailList where date equals to DEFAULT_DATE
        defaultSubEmailShouldBeFound("date.equals=" + DEFAULT_DATE);

        // Get all the subEmailList where date equals to UPDATED_DATE
        defaultSubEmailShouldNotBeFound("date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllSubEmailsByDateIsInShouldWork() throws Exception {
        // Initialize the database
        subEmailRepository.saveAndFlush(subEmail);

        // Get all the subEmailList where date in DEFAULT_DATE or UPDATED_DATE
        defaultSubEmailShouldBeFound("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE);

        // Get all the subEmailList where date equals to UPDATED_DATE
        defaultSubEmailShouldNotBeFound("date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllSubEmailsByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        subEmailRepository.saveAndFlush(subEmail);

        // Get all the subEmailList where date is not null
        defaultSubEmailShouldBeFound("date.specified=true");

        // Get all the subEmailList where date is null
        defaultSubEmailShouldNotBeFound("date.specified=false");
    }

    @Test
    @Transactional
    void getAllSubEmailsByDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        subEmailRepository.saveAndFlush(subEmail);

        // Get all the subEmailList where date is greater than or equal to DEFAULT_DATE
        defaultSubEmailShouldBeFound("date.greaterThanOrEqual=" + DEFAULT_DATE);

        // Get all the subEmailList where date is greater than or equal to UPDATED_DATE
        defaultSubEmailShouldNotBeFound("date.greaterThanOrEqual=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllSubEmailsByDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        subEmailRepository.saveAndFlush(subEmail);

        // Get all the subEmailList where date is less than or equal to DEFAULT_DATE
        defaultSubEmailShouldBeFound("date.lessThanOrEqual=" + DEFAULT_DATE);

        // Get all the subEmailList where date is less than or equal to SMALLER_DATE
        defaultSubEmailShouldNotBeFound("date.lessThanOrEqual=" + SMALLER_DATE);
    }

    @Test
    @Transactional
    void getAllSubEmailsByDateIsLessThanSomething() throws Exception {
        // Initialize the database
        subEmailRepository.saveAndFlush(subEmail);

        // Get all the subEmailList where date is less than DEFAULT_DATE
        defaultSubEmailShouldNotBeFound("date.lessThan=" + DEFAULT_DATE);

        // Get all the subEmailList where date is less than UPDATED_DATE
        defaultSubEmailShouldBeFound("date.lessThan=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllSubEmailsByDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        subEmailRepository.saveAndFlush(subEmail);

        // Get all the subEmailList where date is greater than DEFAULT_DATE
        defaultSubEmailShouldNotBeFound("date.greaterThan=" + DEFAULT_DATE);

        // Get all the subEmailList where date is greater than SMALLER_DATE
        defaultSubEmailShouldBeFound("date.greaterThan=" + SMALLER_DATE);
    }

    @Test
    @Transactional
    void getAllSubEmailsBySnoozedToIsEqualToSomething() throws Exception {
        // Initialize the database
        subEmailRepository.saveAndFlush(subEmail);

        // Get all the subEmailList where snoozedTo equals to DEFAULT_SNOOZED_TO
        defaultSubEmailShouldBeFound("snoozedTo.equals=" + DEFAULT_SNOOZED_TO);

        // Get all the subEmailList where snoozedTo equals to UPDATED_SNOOZED_TO
        defaultSubEmailShouldNotBeFound("snoozedTo.equals=" + UPDATED_SNOOZED_TO);
    }

    @Test
    @Transactional
    void getAllSubEmailsBySnoozedToIsInShouldWork() throws Exception {
        // Initialize the database
        subEmailRepository.saveAndFlush(subEmail);

        // Get all the subEmailList where snoozedTo in DEFAULT_SNOOZED_TO or UPDATED_SNOOZED_TO
        defaultSubEmailShouldBeFound("snoozedTo.in=" + DEFAULT_SNOOZED_TO + "," + UPDATED_SNOOZED_TO);

        // Get all the subEmailList where snoozedTo equals to UPDATED_SNOOZED_TO
        defaultSubEmailShouldNotBeFound("snoozedTo.in=" + UPDATED_SNOOZED_TO);
    }

    @Test
    @Transactional
    void getAllSubEmailsBySnoozedToIsNullOrNotNull() throws Exception {
        // Initialize the database
        subEmailRepository.saveAndFlush(subEmail);

        // Get all the subEmailList where snoozedTo is not null
        defaultSubEmailShouldBeFound("snoozedTo.specified=true");

        // Get all the subEmailList where snoozedTo is null
        defaultSubEmailShouldNotBeFound("snoozedTo.specified=false");
    }

    @Test
    @Transactional
    void getAllSubEmailsBySnoozedToIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        subEmailRepository.saveAndFlush(subEmail);

        // Get all the subEmailList where snoozedTo is greater than or equal to DEFAULT_SNOOZED_TO
        defaultSubEmailShouldBeFound("snoozedTo.greaterThanOrEqual=" + DEFAULT_SNOOZED_TO);

        // Get all the subEmailList where snoozedTo is greater than or equal to UPDATED_SNOOZED_TO
        defaultSubEmailShouldNotBeFound("snoozedTo.greaterThanOrEqual=" + UPDATED_SNOOZED_TO);
    }

    @Test
    @Transactional
    void getAllSubEmailsBySnoozedToIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        subEmailRepository.saveAndFlush(subEmail);

        // Get all the subEmailList where snoozedTo is less than or equal to DEFAULT_SNOOZED_TO
        defaultSubEmailShouldBeFound("snoozedTo.lessThanOrEqual=" + DEFAULT_SNOOZED_TO);

        // Get all the subEmailList where snoozedTo is less than or equal to SMALLER_SNOOZED_TO
        defaultSubEmailShouldNotBeFound("snoozedTo.lessThanOrEqual=" + SMALLER_SNOOZED_TO);
    }

    @Test
    @Transactional
    void getAllSubEmailsBySnoozedToIsLessThanSomething() throws Exception {
        // Initialize the database
        subEmailRepository.saveAndFlush(subEmail);

        // Get all the subEmailList where snoozedTo is less than DEFAULT_SNOOZED_TO
        defaultSubEmailShouldNotBeFound("snoozedTo.lessThan=" + DEFAULT_SNOOZED_TO);

        // Get all the subEmailList where snoozedTo is less than UPDATED_SNOOZED_TO
        defaultSubEmailShouldBeFound("snoozedTo.lessThan=" + UPDATED_SNOOZED_TO);
    }

    @Test
    @Transactional
    void getAllSubEmailsBySnoozedToIsGreaterThanSomething() throws Exception {
        // Initialize the database
        subEmailRepository.saveAndFlush(subEmail);

        // Get all the subEmailList where snoozedTo is greater than DEFAULT_SNOOZED_TO
        defaultSubEmailShouldNotBeFound("snoozedTo.greaterThan=" + DEFAULT_SNOOZED_TO);

        // Get all the subEmailList where snoozedTo is greater than SMALLER_SNOOZED_TO
        defaultSubEmailShouldBeFound("snoozedTo.greaterThan=" + SMALLER_SNOOZED_TO);
    }

    @Test
    @Transactional
    void getAllSubEmailsBySignatureTextIsEqualToSomething() throws Exception {
        // Initialize the database
        subEmailRepository.saveAndFlush(subEmail);

        // Get all the subEmailList where signatureText equals to DEFAULT_SIGNATURE_TEXT
        defaultSubEmailShouldBeFound("signatureText.equals=" + DEFAULT_SIGNATURE_TEXT);

        // Get all the subEmailList where signatureText equals to UPDATED_SIGNATURE_TEXT
        defaultSubEmailShouldNotBeFound("signatureText.equals=" + UPDATED_SIGNATURE_TEXT);
    }

    @Test
    @Transactional
    void getAllSubEmailsBySignatureTextIsInShouldWork() throws Exception {
        // Initialize the database
        subEmailRepository.saveAndFlush(subEmail);

        // Get all the subEmailList where signatureText in DEFAULT_SIGNATURE_TEXT or UPDATED_SIGNATURE_TEXT
        defaultSubEmailShouldBeFound("signatureText.in=" + DEFAULT_SIGNATURE_TEXT + "," + UPDATED_SIGNATURE_TEXT);

        // Get all the subEmailList where signatureText equals to UPDATED_SIGNATURE_TEXT
        defaultSubEmailShouldNotBeFound("signatureText.in=" + UPDATED_SIGNATURE_TEXT);
    }

    @Test
    @Transactional
    void getAllSubEmailsBySignatureTextIsNullOrNotNull() throws Exception {
        // Initialize the database
        subEmailRepository.saveAndFlush(subEmail);

        // Get all the subEmailList where signatureText is not null
        defaultSubEmailShouldBeFound("signatureText.specified=true");

        // Get all the subEmailList where signatureText is null
        defaultSubEmailShouldNotBeFound("signatureText.specified=false");
    }

    @Test
    @Transactional
    void getAllSubEmailsBySignatureTextContainsSomething() throws Exception {
        // Initialize the database
        subEmailRepository.saveAndFlush(subEmail);

        // Get all the subEmailList where signatureText contains DEFAULT_SIGNATURE_TEXT
        defaultSubEmailShouldBeFound("signatureText.contains=" + DEFAULT_SIGNATURE_TEXT);

        // Get all the subEmailList where signatureText contains UPDATED_SIGNATURE_TEXT
        defaultSubEmailShouldNotBeFound("signatureText.contains=" + UPDATED_SIGNATURE_TEXT);
    }

    @Test
    @Transactional
    void getAllSubEmailsBySignatureTextNotContainsSomething() throws Exception {
        // Initialize the database
        subEmailRepository.saveAndFlush(subEmail);

        // Get all the subEmailList where signatureText does not contain DEFAULT_SIGNATURE_TEXT
        defaultSubEmailShouldNotBeFound("signatureText.doesNotContain=" + DEFAULT_SIGNATURE_TEXT);

        // Get all the subEmailList where signatureText does not contain UPDATED_SIGNATURE_TEXT
        defaultSubEmailShouldBeFound("signatureText.doesNotContain=" + UPDATED_SIGNATURE_TEXT);
    }

    @Test
    @Transactional
    void getAllSubEmailsBySubEmailEmailIsEqualToSomething() throws Exception {
        Email subEmailEmail;
        if (TestUtil.findAll(em, Email.class).isEmpty()) {
            subEmailRepository.saveAndFlush(subEmail);
            subEmailEmail = EmailResourceIT.createEntity(em);
        } else {
            subEmailEmail = TestUtil.findAll(em, Email.class).get(0);
        }
        em.persist(subEmailEmail);
        em.flush();
        subEmail.setSubEmailEmail(subEmailEmail);
        subEmailRepository.saveAndFlush(subEmail);
        Long subEmailEmailId = subEmailEmail.getId();
        // Get all the subEmailList where subEmailEmail equals to subEmailEmailId
        defaultSubEmailShouldBeFound("subEmailEmailId.equals=" + subEmailEmailId);

        // Get all the subEmailList where subEmailEmail equals to (subEmailEmailId + 1)
        defaultSubEmailShouldNotBeFound("subEmailEmailId.equals=" + (subEmailEmailId + 1));
    }

    @Test
    @Transactional
    void getAllSubEmailsByEmailIsEqualToSomething() throws Exception {
        Email email;
        if (TestUtil.findAll(em, Email.class).isEmpty()) {
            subEmailRepository.saveAndFlush(subEmail);
            email = EmailResourceIT.createEntity(em);
        } else {
            email = TestUtil.findAll(em, Email.class).get(0);
        }
        em.persist(email);
        em.flush();
        subEmail.setEmail(email);
        subEmailRepository.saveAndFlush(subEmail);
        Long emailId = email.getId();
        // Get all the subEmailList where email equals to emailId
        defaultSubEmailShouldBeFound("emailId.equals=" + emailId);

        // Get all the subEmailList where email equals to (emailId + 1)
        defaultSubEmailShouldNotBeFound("emailId.equals=" + (emailId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSubEmailShouldBeFound(String filter) throws Exception {
        restSubEmailMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subEmail.getId().intValue())))
            .andExpect(jsonPath("$.[*].from").value(hasItem(DEFAULT_FROM)))
            .andExpect(jsonPath("$.[*].recipients").value(hasItem(DEFAULT_RECIPIENTS)))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))))
            .andExpect(jsonPath("$.[*].snoozedTo").value(hasItem(sameInstant(DEFAULT_SNOOZED_TO))))
            .andExpect(jsonPath("$.[*].signatureText").value(hasItem(DEFAULT_SIGNATURE_TEXT)))
            .andExpect(jsonPath("$.[*].signatureImageContentType").value(hasItem(DEFAULT_SIGNATURE_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].signatureImage").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_SIGNATURE_IMAGE))));

        // Check, that the count call also returns 1
        restSubEmailMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSubEmailShouldNotBeFound(String filter) throws Exception {
        restSubEmailMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSubEmailMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSubEmail() throws Exception {
        // Get the subEmail
        restSubEmailMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSubEmail() throws Exception {
        // Initialize the database
        subEmailRepository.saveAndFlush(subEmail);

        int databaseSizeBeforeUpdate = subEmailRepository.findAll().size();

        // Update the subEmail
        SubEmail updatedSubEmail = subEmailRepository.findById(subEmail.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSubEmail are not directly saved in db
        em.detach(updatedSubEmail);
        updatedSubEmail
            .from(UPDATED_FROM)
            .recipients(UPDATED_RECIPIENTS)
            .text(UPDATED_TEXT)
            .type(UPDATED_TYPE)
            .date(UPDATED_DATE)
            .snoozedTo(UPDATED_SNOOZED_TO)
            .signatureText(UPDATED_SIGNATURE_TEXT)
            .signatureImage(UPDATED_SIGNATURE_IMAGE)
            .signatureImageContentType(UPDATED_SIGNATURE_IMAGE_CONTENT_TYPE);
        SubEmailDTO subEmailDTO = subEmailMapper.toDto(updatedSubEmail);

        restSubEmailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, subEmailDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(subEmailDTO))
            )
            .andExpect(status().isOk());

        // Validate the SubEmail in the database
        List<SubEmail> subEmailList = subEmailRepository.findAll();
        assertThat(subEmailList).hasSize(databaseSizeBeforeUpdate);
        SubEmail testSubEmail = subEmailList.get(subEmailList.size() - 1);
        assertThat(testSubEmail.getFrom()).isEqualTo(UPDATED_FROM);
        assertThat(testSubEmail.getRecipients()).isEqualTo(UPDATED_RECIPIENTS);
        assertThat(testSubEmail.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testSubEmail.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testSubEmail.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testSubEmail.getSnoozedTo()).isEqualTo(UPDATED_SNOOZED_TO);
        assertThat(testSubEmail.getSignatureText()).isEqualTo(UPDATED_SIGNATURE_TEXT);
        assertThat(testSubEmail.getSignatureImage()).isEqualTo(UPDATED_SIGNATURE_IMAGE);
        assertThat(testSubEmail.getSignatureImageContentType()).isEqualTo(UPDATED_SIGNATURE_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingSubEmail() throws Exception {
        int databaseSizeBeforeUpdate = subEmailRepository.findAll().size();
        subEmail.setId(longCount.incrementAndGet());

        // Create the SubEmail
        SubEmailDTO subEmailDTO = subEmailMapper.toDto(subEmail);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubEmailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, subEmailDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(subEmailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubEmail in the database
        List<SubEmail> subEmailList = subEmailRepository.findAll();
        assertThat(subEmailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSubEmail() throws Exception {
        int databaseSizeBeforeUpdate = subEmailRepository.findAll().size();
        subEmail.setId(longCount.incrementAndGet());

        // Create the SubEmail
        SubEmailDTO subEmailDTO = subEmailMapper.toDto(subEmail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubEmailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(subEmailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubEmail in the database
        List<SubEmail> subEmailList = subEmailRepository.findAll();
        assertThat(subEmailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSubEmail() throws Exception {
        int databaseSizeBeforeUpdate = subEmailRepository.findAll().size();
        subEmail.setId(longCount.incrementAndGet());

        // Create the SubEmail
        SubEmailDTO subEmailDTO = subEmailMapper.toDto(subEmail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubEmailMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subEmailDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SubEmail in the database
        List<SubEmail> subEmailList = subEmailRepository.findAll();
        assertThat(subEmailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSubEmailWithPatch() throws Exception {
        // Initialize the database
        subEmailRepository.saveAndFlush(subEmail);

        int databaseSizeBeforeUpdate = subEmailRepository.findAll().size();

        // Update the subEmail using partial update
        SubEmail partialUpdatedSubEmail = new SubEmail();
        partialUpdatedSubEmail.setId(subEmail.getId());

        partialUpdatedSubEmail
            .from(UPDATED_FROM)
            .type(UPDATED_TYPE)
            .signatureText(UPDATED_SIGNATURE_TEXT)
            .signatureImage(UPDATED_SIGNATURE_IMAGE)
            .signatureImageContentType(UPDATED_SIGNATURE_IMAGE_CONTENT_TYPE);

        restSubEmailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSubEmail.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSubEmail))
            )
            .andExpect(status().isOk());

        // Validate the SubEmail in the database
        List<SubEmail> subEmailList = subEmailRepository.findAll();
        assertThat(subEmailList).hasSize(databaseSizeBeforeUpdate);
        SubEmail testSubEmail = subEmailList.get(subEmailList.size() - 1);
        assertThat(testSubEmail.getFrom()).isEqualTo(UPDATED_FROM);
        assertThat(testSubEmail.getRecipients()).isEqualTo(DEFAULT_RECIPIENTS);
        assertThat(testSubEmail.getText()).isEqualTo(DEFAULT_TEXT);
        assertThat(testSubEmail.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testSubEmail.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testSubEmail.getSnoozedTo()).isEqualTo(DEFAULT_SNOOZED_TO);
        assertThat(testSubEmail.getSignatureText()).isEqualTo(UPDATED_SIGNATURE_TEXT);
        assertThat(testSubEmail.getSignatureImage()).isEqualTo(UPDATED_SIGNATURE_IMAGE);
        assertThat(testSubEmail.getSignatureImageContentType()).isEqualTo(UPDATED_SIGNATURE_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateSubEmailWithPatch() throws Exception {
        // Initialize the database
        subEmailRepository.saveAndFlush(subEmail);

        int databaseSizeBeforeUpdate = subEmailRepository.findAll().size();

        // Update the subEmail using partial update
        SubEmail partialUpdatedSubEmail = new SubEmail();
        partialUpdatedSubEmail.setId(subEmail.getId());

        partialUpdatedSubEmail
            .from(UPDATED_FROM)
            .recipients(UPDATED_RECIPIENTS)
            .text(UPDATED_TEXT)
            .type(UPDATED_TYPE)
            .date(UPDATED_DATE)
            .snoozedTo(UPDATED_SNOOZED_TO)
            .signatureText(UPDATED_SIGNATURE_TEXT)
            .signatureImage(UPDATED_SIGNATURE_IMAGE)
            .signatureImageContentType(UPDATED_SIGNATURE_IMAGE_CONTENT_TYPE);

        restSubEmailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSubEmail.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSubEmail))
            )
            .andExpect(status().isOk());

        // Validate the SubEmail in the database
        List<SubEmail> subEmailList = subEmailRepository.findAll();
        assertThat(subEmailList).hasSize(databaseSizeBeforeUpdate);
        SubEmail testSubEmail = subEmailList.get(subEmailList.size() - 1);
        assertThat(testSubEmail.getFrom()).isEqualTo(UPDATED_FROM);
        assertThat(testSubEmail.getRecipients()).isEqualTo(UPDATED_RECIPIENTS);
        assertThat(testSubEmail.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testSubEmail.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testSubEmail.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testSubEmail.getSnoozedTo()).isEqualTo(UPDATED_SNOOZED_TO);
        assertThat(testSubEmail.getSignatureText()).isEqualTo(UPDATED_SIGNATURE_TEXT);
        assertThat(testSubEmail.getSignatureImage()).isEqualTo(UPDATED_SIGNATURE_IMAGE);
        assertThat(testSubEmail.getSignatureImageContentType()).isEqualTo(UPDATED_SIGNATURE_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingSubEmail() throws Exception {
        int databaseSizeBeforeUpdate = subEmailRepository.findAll().size();
        subEmail.setId(longCount.incrementAndGet());

        // Create the SubEmail
        SubEmailDTO subEmailDTO = subEmailMapper.toDto(subEmail);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubEmailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, subEmailDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(subEmailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubEmail in the database
        List<SubEmail> subEmailList = subEmailRepository.findAll();
        assertThat(subEmailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSubEmail() throws Exception {
        int databaseSizeBeforeUpdate = subEmailRepository.findAll().size();
        subEmail.setId(longCount.incrementAndGet());

        // Create the SubEmail
        SubEmailDTO subEmailDTO = subEmailMapper.toDto(subEmail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubEmailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(subEmailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubEmail in the database
        List<SubEmail> subEmailList = subEmailRepository.findAll();
        assertThat(subEmailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSubEmail() throws Exception {
        int databaseSizeBeforeUpdate = subEmailRepository.findAll().size();
        subEmail.setId(longCount.incrementAndGet());

        // Create the SubEmail
        SubEmailDTO subEmailDTO = subEmailMapper.toDto(subEmail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubEmailMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(subEmailDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SubEmail in the database
        List<SubEmail> subEmailList = subEmailRepository.findAll();
        assertThat(subEmailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSubEmail() throws Exception {
        // Initialize the database
        subEmailRepository.saveAndFlush(subEmail);

        int databaseSizeBeforeDelete = subEmailRepository.findAll().size();

        // Delete the subEmail
        restSubEmailMockMvc
            .perform(delete(ENTITY_API_URL_ID, subEmail.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SubEmail> subEmailList = subEmailRepository.findAll();
        assertThat(subEmailList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
