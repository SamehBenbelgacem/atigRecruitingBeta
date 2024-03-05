package atig.recruiting.beta.web.rest;

import static atig.recruiting.beta.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import atig.recruiting.beta.IntegrationTest;
import atig.recruiting.beta.domain.Candidate;
import atig.recruiting.beta.domain.Company;
import atig.recruiting.beta.domain.Email;
import atig.recruiting.beta.domain.Emailcredentials;
import atig.recruiting.beta.domain.ObjectContainingFile;
import atig.recruiting.beta.domain.SubEmail;
import atig.recruiting.beta.domain.enumeration.EnumEmailType;
import atig.recruiting.beta.repository.EmailRepository;
import atig.recruiting.beta.service.dto.EmailDTO;
import atig.recruiting.beta.service.mapper.EmailMapper;
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
 * Integration tests for the {@link EmailResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EmailResourceIT {

    private static final String DEFAULT_FROM = "AAAAAAAAAA";
    private static final String UPDATED_FROM = "BBBBBBBBBB";

    private static final String DEFAULT_RECIPIENTS = "AAAAAAAAAA";
    private static final String UPDATED_RECIPIENTS = "BBBBBBBBBB";

    private static final String DEFAULT_SUBJECT = "AAAAAAAAAA";
    private static final String UPDATED_SUBJECT = "BBBBBBBBBB";

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

    private static final String DEFAULT_FOLDER = "AAAAAAAAAA";
    private static final String UPDATED_FOLDER = "BBBBBBBBBB";

    private static final String DEFAULT_SIGNATURE_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_SIGNATURE_TEXT = "BBBBBBBBBB";

    private static final byte[] DEFAULT_SIGNATURE_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_SIGNATURE_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_SIGNATURE_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_SIGNATURE_IMAGE_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/emails";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private EmailMapper emailMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmailMockMvc;

    private Email email;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Email createEntity(EntityManager em) {
        Email email = new Email()
            .from(DEFAULT_FROM)
            .recipients(DEFAULT_RECIPIENTS)
            .subject(DEFAULT_SUBJECT)
            .text(DEFAULT_TEXT)
            .type(DEFAULT_TYPE)
            .date(DEFAULT_DATE)
            .snoozedTo(DEFAULT_SNOOZED_TO)
            .folder(DEFAULT_FOLDER)
            .signatureText(DEFAULT_SIGNATURE_TEXT)
            .signatureImage(DEFAULT_SIGNATURE_IMAGE)
            .signatureImageContentType(DEFAULT_SIGNATURE_IMAGE_CONTENT_TYPE);
        return email;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Email createUpdatedEntity(EntityManager em) {
        Email email = new Email()
            .from(UPDATED_FROM)
            .recipients(UPDATED_RECIPIENTS)
            .subject(UPDATED_SUBJECT)
            .text(UPDATED_TEXT)
            .type(UPDATED_TYPE)
            .date(UPDATED_DATE)
            .snoozedTo(UPDATED_SNOOZED_TO)
            .folder(UPDATED_FOLDER)
            .signatureText(UPDATED_SIGNATURE_TEXT)
            .signatureImage(UPDATED_SIGNATURE_IMAGE)
            .signatureImageContentType(UPDATED_SIGNATURE_IMAGE_CONTENT_TYPE);
        return email;
    }

    @BeforeEach
    public void initTest() {
        email = createEntity(em);
    }

    @Test
    @Transactional
    void createEmail() throws Exception {
        int databaseSizeBeforeCreate = emailRepository.findAll().size();
        // Create the Email
        EmailDTO emailDTO = emailMapper.toDto(email);
        restEmailMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(emailDTO)))
            .andExpect(status().isCreated());

        // Validate the Email in the database
        List<Email> emailList = emailRepository.findAll();
        assertThat(emailList).hasSize(databaseSizeBeforeCreate + 1);
        Email testEmail = emailList.get(emailList.size() - 1);
        assertThat(testEmail.getFrom()).isEqualTo(DEFAULT_FROM);
        assertThat(testEmail.getRecipients()).isEqualTo(DEFAULT_RECIPIENTS);
        assertThat(testEmail.getSubject()).isEqualTo(DEFAULT_SUBJECT);
        assertThat(testEmail.getText()).isEqualTo(DEFAULT_TEXT);
        assertThat(testEmail.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testEmail.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testEmail.getSnoozedTo()).isEqualTo(DEFAULT_SNOOZED_TO);
        assertThat(testEmail.getFolder()).isEqualTo(DEFAULT_FOLDER);
        assertThat(testEmail.getSignatureText()).isEqualTo(DEFAULT_SIGNATURE_TEXT);
        assertThat(testEmail.getSignatureImage()).isEqualTo(DEFAULT_SIGNATURE_IMAGE);
        assertThat(testEmail.getSignatureImageContentType()).isEqualTo(DEFAULT_SIGNATURE_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createEmailWithExistingId() throws Exception {
        // Create the Email with an existing ID
        email.setId(1L);
        EmailDTO emailDTO = emailMapper.toDto(email);

        int databaseSizeBeforeCreate = emailRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmailMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(emailDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Email in the database
        List<Email> emailList = emailRepository.findAll();
        assertThat(emailList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEmails() throws Exception {
        // Initialize the database
        emailRepository.saveAndFlush(email);

        // Get all the emailList
        restEmailMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(email.getId().intValue())))
            .andExpect(jsonPath("$.[*].from").value(hasItem(DEFAULT_FROM)))
            .andExpect(jsonPath("$.[*].recipients").value(hasItem(DEFAULT_RECIPIENTS)))
            .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT)))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))))
            .andExpect(jsonPath("$.[*].snoozedTo").value(hasItem(sameInstant(DEFAULT_SNOOZED_TO))))
            .andExpect(jsonPath("$.[*].folder").value(hasItem(DEFAULT_FOLDER)))
            .andExpect(jsonPath("$.[*].signatureText").value(hasItem(DEFAULT_SIGNATURE_TEXT)))
            .andExpect(jsonPath("$.[*].signatureImageContentType").value(hasItem(DEFAULT_SIGNATURE_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].signatureImage").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_SIGNATURE_IMAGE))));
    }

    @Test
    @Transactional
    void getEmail() throws Exception {
        // Initialize the database
        emailRepository.saveAndFlush(email);

        // Get the email
        restEmailMockMvc
            .perform(get(ENTITY_API_URL_ID, email.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(email.getId().intValue()))
            .andExpect(jsonPath("$.from").value(DEFAULT_FROM))
            .andExpect(jsonPath("$.recipients").value(DEFAULT_RECIPIENTS))
            .andExpect(jsonPath("$.subject").value(DEFAULT_SUBJECT))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)))
            .andExpect(jsonPath("$.snoozedTo").value(sameInstant(DEFAULT_SNOOZED_TO)))
            .andExpect(jsonPath("$.folder").value(DEFAULT_FOLDER))
            .andExpect(jsonPath("$.signatureText").value(DEFAULT_SIGNATURE_TEXT))
            .andExpect(jsonPath("$.signatureImageContentType").value(DEFAULT_SIGNATURE_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.signatureImage").value(Base64.getEncoder().encodeToString(DEFAULT_SIGNATURE_IMAGE)));
    }

    @Test
    @Transactional
    void getEmailsByIdFiltering() throws Exception {
        // Initialize the database
        emailRepository.saveAndFlush(email);

        Long id = email.getId();

        defaultEmailShouldBeFound("id.equals=" + id);
        defaultEmailShouldNotBeFound("id.notEquals=" + id);

        defaultEmailShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmailShouldNotBeFound("id.greaterThan=" + id);

        defaultEmailShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmailShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEmailsByFromIsEqualToSomething() throws Exception {
        // Initialize the database
        emailRepository.saveAndFlush(email);

        // Get all the emailList where from equals to DEFAULT_FROM
        defaultEmailShouldBeFound("from.equals=" + DEFAULT_FROM);

        // Get all the emailList where from equals to UPDATED_FROM
        defaultEmailShouldNotBeFound("from.equals=" + UPDATED_FROM);
    }

    @Test
    @Transactional
    void getAllEmailsByFromIsInShouldWork() throws Exception {
        // Initialize the database
        emailRepository.saveAndFlush(email);

        // Get all the emailList where from in DEFAULT_FROM or UPDATED_FROM
        defaultEmailShouldBeFound("from.in=" + DEFAULT_FROM + "," + UPDATED_FROM);

        // Get all the emailList where from equals to UPDATED_FROM
        defaultEmailShouldNotBeFound("from.in=" + UPDATED_FROM);
    }

    @Test
    @Transactional
    void getAllEmailsByFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        emailRepository.saveAndFlush(email);

        // Get all the emailList where from is not null
        defaultEmailShouldBeFound("from.specified=true");

        // Get all the emailList where from is null
        defaultEmailShouldNotBeFound("from.specified=false");
    }

    @Test
    @Transactional
    void getAllEmailsByFromContainsSomething() throws Exception {
        // Initialize the database
        emailRepository.saveAndFlush(email);

        // Get all the emailList where from contains DEFAULT_FROM
        defaultEmailShouldBeFound("from.contains=" + DEFAULT_FROM);

        // Get all the emailList where from contains UPDATED_FROM
        defaultEmailShouldNotBeFound("from.contains=" + UPDATED_FROM);
    }

    @Test
    @Transactional
    void getAllEmailsByFromNotContainsSomething() throws Exception {
        // Initialize the database
        emailRepository.saveAndFlush(email);

        // Get all the emailList where from does not contain DEFAULT_FROM
        defaultEmailShouldNotBeFound("from.doesNotContain=" + DEFAULT_FROM);

        // Get all the emailList where from does not contain UPDATED_FROM
        defaultEmailShouldBeFound("from.doesNotContain=" + UPDATED_FROM);
    }

    @Test
    @Transactional
    void getAllEmailsByRecipientsIsEqualToSomething() throws Exception {
        // Initialize the database
        emailRepository.saveAndFlush(email);

        // Get all the emailList where recipients equals to DEFAULT_RECIPIENTS
        defaultEmailShouldBeFound("recipients.equals=" + DEFAULT_RECIPIENTS);

        // Get all the emailList where recipients equals to UPDATED_RECIPIENTS
        defaultEmailShouldNotBeFound("recipients.equals=" + UPDATED_RECIPIENTS);
    }

    @Test
    @Transactional
    void getAllEmailsByRecipientsIsInShouldWork() throws Exception {
        // Initialize the database
        emailRepository.saveAndFlush(email);

        // Get all the emailList where recipients in DEFAULT_RECIPIENTS or UPDATED_RECIPIENTS
        defaultEmailShouldBeFound("recipients.in=" + DEFAULT_RECIPIENTS + "," + UPDATED_RECIPIENTS);

        // Get all the emailList where recipients equals to UPDATED_RECIPIENTS
        defaultEmailShouldNotBeFound("recipients.in=" + UPDATED_RECIPIENTS);
    }

    @Test
    @Transactional
    void getAllEmailsByRecipientsIsNullOrNotNull() throws Exception {
        // Initialize the database
        emailRepository.saveAndFlush(email);

        // Get all the emailList where recipients is not null
        defaultEmailShouldBeFound("recipients.specified=true");

        // Get all the emailList where recipients is null
        defaultEmailShouldNotBeFound("recipients.specified=false");
    }

    @Test
    @Transactional
    void getAllEmailsByRecipientsContainsSomething() throws Exception {
        // Initialize the database
        emailRepository.saveAndFlush(email);

        // Get all the emailList where recipients contains DEFAULT_RECIPIENTS
        defaultEmailShouldBeFound("recipients.contains=" + DEFAULT_RECIPIENTS);

        // Get all the emailList where recipients contains UPDATED_RECIPIENTS
        defaultEmailShouldNotBeFound("recipients.contains=" + UPDATED_RECIPIENTS);
    }

    @Test
    @Transactional
    void getAllEmailsByRecipientsNotContainsSomething() throws Exception {
        // Initialize the database
        emailRepository.saveAndFlush(email);

        // Get all the emailList where recipients does not contain DEFAULT_RECIPIENTS
        defaultEmailShouldNotBeFound("recipients.doesNotContain=" + DEFAULT_RECIPIENTS);

        // Get all the emailList where recipients does not contain UPDATED_RECIPIENTS
        defaultEmailShouldBeFound("recipients.doesNotContain=" + UPDATED_RECIPIENTS);
    }

    @Test
    @Transactional
    void getAllEmailsBySubjectIsEqualToSomething() throws Exception {
        // Initialize the database
        emailRepository.saveAndFlush(email);

        // Get all the emailList where subject equals to DEFAULT_SUBJECT
        defaultEmailShouldBeFound("subject.equals=" + DEFAULT_SUBJECT);

        // Get all the emailList where subject equals to UPDATED_SUBJECT
        defaultEmailShouldNotBeFound("subject.equals=" + UPDATED_SUBJECT);
    }

    @Test
    @Transactional
    void getAllEmailsBySubjectIsInShouldWork() throws Exception {
        // Initialize the database
        emailRepository.saveAndFlush(email);

        // Get all the emailList where subject in DEFAULT_SUBJECT or UPDATED_SUBJECT
        defaultEmailShouldBeFound("subject.in=" + DEFAULT_SUBJECT + "," + UPDATED_SUBJECT);

        // Get all the emailList where subject equals to UPDATED_SUBJECT
        defaultEmailShouldNotBeFound("subject.in=" + UPDATED_SUBJECT);
    }

    @Test
    @Transactional
    void getAllEmailsBySubjectIsNullOrNotNull() throws Exception {
        // Initialize the database
        emailRepository.saveAndFlush(email);

        // Get all the emailList where subject is not null
        defaultEmailShouldBeFound("subject.specified=true");

        // Get all the emailList where subject is null
        defaultEmailShouldNotBeFound("subject.specified=false");
    }

    @Test
    @Transactional
    void getAllEmailsBySubjectContainsSomething() throws Exception {
        // Initialize the database
        emailRepository.saveAndFlush(email);

        // Get all the emailList where subject contains DEFAULT_SUBJECT
        defaultEmailShouldBeFound("subject.contains=" + DEFAULT_SUBJECT);

        // Get all the emailList where subject contains UPDATED_SUBJECT
        defaultEmailShouldNotBeFound("subject.contains=" + UPDATED_SUBJECT);
    }

    @Test
    @Transactional
    void getAllEmailsBySubjectNotContainsSomething() throws Exception {
        // Initialize the database
        emailRepository.saveAndFlush(email);

        // Get all the emailList where subject does not contain DEFAULT_SUBJECT
        defaultEmailShouldNotBeFound("subject.doesNotContain=" + DEFAULT_SUBJECT);

        // Get all the emailList where subject does not contain UPDATED_SUBJECT
        defaultEmailShouldBeFound("subject.doesNotContain=" + UPDATED_SUBJECT);
    }

    @Test
    @Transactional
    void getAllEmailsByTextIsEqualToSomething() throws Exception {
        // Initialize the database
        emailRepository.saveAndFlush(email);

        // Get all the emailList where text equals to DEFAULT_TEXT
        defaultEmailShouldBeFound("text.equals=" + DEFAULT_TEXT);

        // Get all the emailList where text equals to UPDATED_TEXT
        defaultEmailShouldNotBeFound("text.equals=" + UPDATED_TEXT);
    }

    @Test
    @Transactional
    void getAllEmailsByTextIsInShouldWork() throws Exception {
        // Initialize the database
        emailRepository.saveAndFlush(email);

        // Get all the emailList where text in DEFAULT_TEXT or UPDATED_TEXT
        defaultEmailShouldBeFound("text.in=" + DEFAULT_TEXT + "," + UPDATED_TEXT);

        // Get all the emailList where text equals to UPDATED_TEXT
        defaultEmailShouldNotBeFound("text.in=" + UPDATED_TEXT);
    }

    @Test
    @Transactional
    void getAllEmailsByTextIsNullOrNotNull() throws Exception {
        // Initialize the database
        emailRepository.saveAndFlush(email);

        // Get all the emailList where text is not null
        defaultEmailShouldBeFound("text.specified=true");

        // Get all the emailList where text is null
        defaultEmailShouldNotBeFound("text.specified=false");
    }

    @Test
    @Transactional
    void getAllEmailsByTextContainsSomething() throws Exception {
        // Initialize the database
        emailRepository.saveAndFlush(email);

        // Get all the emailList where text contains DEFAULT_TEXT
        defaultEmailShouldBeFound("text.contains=" + DEFAULT_TEXT);

        // Get all the emailList where text contains UPDATED_TEXT
        defaultEmailShouldNotBeFound("text.contains=" + UPDATED_TEXT);
    }

    @Test
    @Transactional
    void getAllEmailsByTextNotContainsSomething() throws Exception {
        // Initialize the database
        emailRepository.saveAndFlush(email);

        // Get all the emailList where text does not contain DEFAULT_TEXT
        defaultEmailShouldNotBeFound("text.doesNotContain=" + DEFAULT_TEXT);

        // Get all the emailList where text does not contain UPDATED_TEXT
        defaultEmailShouldBeFound("text.doesNotContain=" + UPDATED_TEXT);
    }

    @Test
    @Transactional
    void getAllEmailsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        emailRepository.saveAndFlush(email);

        // Get all the emailList where type equals to DEFAULT_TYPE
        defaultEmailShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the emailList where type equals to UPDATED_TYPE
        defaultEmailShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllEmailsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        emailRepository.saveAndFlush(email);

        // Get all the emailList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultEmailShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the emailList where type equals to UPDATED_TYPE
        defaultEmailShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllEmailsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        emailRepository.saveAndFlush(email);

        // Get all the emailList where type is not null
        defaultEmailShouldBeFound("type.specified=true");

        // Get all the emailList where type is null
        defaultEmailShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllEmailsByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        emailRepository.saveAndFlush(email);

        // Get all the emailList where date equals to DEFAULT_DATE
        defaultEmailShouldBeFound("date.equals=" + DEFAULT_DATE);

        // Get all the emailList where date equals to UPDATED_DATE
        defaultEmailShouldNotBeFound("date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllEmailsByDateIsInShouldWork() throws Exception {
        // Initialize the database
        emailRepository.saveAndFlush(email);

        // Get all the emailList where date in DEFAULT_DATE or UPDATED_DATE
        defaultEmailShouldBeFound("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE);

        // Get all the emailList where date equals to UPDATED_DATE
        defaultEmailShouldNotBeFound("date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllEmailsByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        emailRepository.saveAndFlush(email);

        // Get all the emailList where date is not null
        defaultEmailShouldBeFound("date.specified=true");

        // Get all the emailList where date is null
        defaultEmailShouldNotBeFound("date.specified=false");
    }

    @Test
    @Transactional
    void getAllEmailsByDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        emailRepository.saveAndFlush(email);

        // Get all the emailList where date is greater than or equal to DEFAULT_DATE
        defaultEmailShouldBeFound("date.greaterThanOrEqual=" + DEFAULT_DATE);

        // Get all the emailList where date is greater than or equal to UPDATED_DATE
        defaultEmailShouldNotBeFound("date.greaterThanOrEqual=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllEmailsByDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        emailRepository.saveAndFlush(email);

        // Get all the emailList where date is less than or equal to DEFAULT_DATE
        defaultEmailShouldBeFound("date.lessThanOrEqual=" + DEFAULT_DATE);

        // Get all the emailList where date is less than or equal to SMALLER_DATE
        defaultEmailShouldNotBeFound("date.lessThanOrEqual=" + SMALLER_DATE);
    }

    @Test
    @Transactional
    void getAllEmailsByDateIsLessThanSomething() throws Exception {
        // Initialize the database
        emailRepository.saveAndFlush(email);

        // Get all the emailList where date is less than DEFAULT_DATE
        defaultEmailShouldNotBeFound("date.lessThan=" + DEFAULT_DATE);

        // Get all the emailList where date is less than UPDATED_DATE
        defaultEmailShouldBeFound("date.lessThan=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllEmailsByDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        emailRepository.saveAndFlush(email);

        // Get all the emailList where date is greater than DEFAULT_DATE
        defaultEmailShouldNotBeFound("date.greaterThan=" + DEFAULT_DATE);

        // Get all the emailList where date is greater than SMALLER_DATE
        defaultEmailShouldBeFound("date.greaterThan=" + SMALLER_DATE);
    }

    @Test
    @Transactional
    void getAllEmailsBySnoozedToIsEqualToSomething() throws Exception {
        // Initialize the database
        emailRepository.saveAndFlush(email);

        // Get all the emailList where snoozedTo equals to DEFAULT_SNOOZED_TO
        defaultEmailShouldBeFound("snoozedTo.equals=" + DEFAULT_SNOOZED_TO);

        // Get all the emailList where snoozedTo equals to UPDATED_SNOOZED_TO
        defaultEmailShouldNotBeFound("snoozedTo.equals=" + UPDATED_SNOOZED_TO);
    }

    @Test
    @Transactional
    void getAllEmailsBySnoozedToIsInShouldWork() throws Exception {
        // Initialize the database
        emailRepository.saveAndFlush(email);

        // Get all the emailList where snoozedTo in DEFAULT_SNOOZED_TO or UPDATED_SNOOZED_TO
        defaultEmailShouldBeFound("snoozedTo.in=" + DEFAULT_SNOOZED_TO + "," + UPDATED_SNOOZED_TO);

        // Get all the emailList where snoozedTo equals to UPDATED_SNOOZED_TO
        defaultEmailShouldNotBeFound("snoozedTo.in=" + UPDATED_SNOOZED_TO);
    }

    @Test
    @Transactional
    void getAllEmailsBySnoozedToIsNullOrNotNull() throws Exception {
        // Initialize the database
        emailRepository.saveAndFlush(email);

        // Get all the emailList where snoozedTo is not null
        defaultEmailShouldBeFound("snoozedTo.specified=true");

        // Get all the emailList where snoozedTo is null
        defaultEmailShouldNotBeFound("snoozedTo.specified=false");
    }

    @Test
    @Transactional
    void getAllEmailsBySnoozedToIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        emailRepository.saveAndFlush(email);

        // Get all the emailList where snoozedTo is greater than or equal to DEFAULT_SNOOZED_TO
        defaultEmailShouldBeFound("snoozedTo.greaterThanOrEqual=" + DEFAULT_SNOOZED_TO);

        // Get all the emailList where snoozedTo is greater than or equal to UPDATED_SNOOZED_TO
        defaultEmailShouldNotBeFound("snoozedTo.greaterThanOrEqual=" + UPDATED_SNOOZED_TO);
    }

    @Test
    @Transactional
    void getAllEmailsBySnoozedToIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        emailRepository.saveAndFlush(email);

        // Get all the emailList where snoozedTo is less than or equal to DEFAULT_SNOOZED_TO
        defaultEmailShouldBeFound("snoozedTo.lessThanOrEqual=" + DEFAULT_SNOOZED_TO);

        // Get all the emailList where snoozedTo is less than or equal to SMALLER_SNOOZED_TO
        defaultEmailShouldNotBeFound("snoozedTo.lessThanOrEqual=" + SMALLER_SNOOZED_TO);
    }

    @Test
    @Transactional
    void getAllEmailsBySnoozedToIsLessThanSomething() throws Exception {
        // Initialize the database
        emailRepository.saveAndFlush(email);

        // Get all the emailList where snoozedTo is less than DEFAULT_SNOOZED_TO
        defaultEmailShouldNotBeFound("snoozedTo.lessThan=" + DEFAULT_SNOOZED_TO);

        // Get all the emailList where snoozedTo is less than UPDATED_SNOOZED_TO
        defaultEmailShouldBeFound("snoozedTo.lessThan=" + UPDATED_SNOOZED_TO);
    }

    @Test
    @Transactional
    void getAllEmailsBySnoozedToIsGreaterThanSomething() throws Exception {
        // Initialize the database
        emailRepository.saveAndFlush(email);

        // Get all the emailList where snoozedTo is greater than DEFAULT_SNOOZED_TO
        defaultEmailShouldNotBeFound("snoozedTo.greaterThan=" + DEFAULT_SNOOZED_TO);

        // Get all the emailList where snoozedTo is greater than SMALLER_SNOOZED_TO
        defaultEmailShouldBeFound("snoozedTo.greaterThan=" + SMALLER_SNOOZED_TO);
    }

    @Test
    @Transactional
    void getAllEmailsByFolderIsEqualToSomething() throws Exception {
        // Initialize the database
        emailRepository.saveAndFlush(email);

        // Get all the emailList where folder equals to DEFAULT_FOLDER
        defaultEmailShouldBeFound("folder.equals=" + DEFAULT_FOLDER);

        // Get all the emailList where folder equals to UPDATED_FOLDER
        defaultEmailShouldNotBeFound("folder.equals=" + UPDATED_FOLDER);
    }

    @Test
    @Transactional
    void getAllEmailsByFolderIsInShouldWork() throws Exception {
        // Initialize the database
        emailRepository.saveAndFlush(email);

        // Get all the emailList where folder in DEFAULT_FOLDER or UPDATED_FOLDER
        defaultEmailShouldBeFound("folder.in=" + DEFAULT_FOLDER + "," + UPDATED_FOLDER);

        // Get all the emailList where folder equals to UPDATED_FOLDER
        defaultEmailShouldNotBeFound("folder.in=" + UPDATED_FOLDER);
    }

    @Test
    @Transactional
    void getAllEmailsByFolderIsNullOrNotNull() throws Exception {
        // Initialize the database
        emailRepository.saveAndFlush(email);

        // Get all the emailList where folder is not null
        defaultEmailShouldBeFound("folder.specified=true");

        // Get all the emailList where folder is null
        defaultEmailShouldNotBeFound("folder.specified=false");
    }

    @Test
    @Transactional
    void getAllEmailsByFolderContainsSomething() throws Exception {
        // Initialize the database
        emailRepository.saveAndFlush(email);

        // Get all the emailList where folder contains DEFAULT_FOLDER
        defaultEmailShouldBeFound("folder.contains=" + DEFAULT_FOLDER);

        // Get all the emailList where folder contains UPDATED_FOLDER
        defaultEmailShouldNotBeFound("folder.contains=" + UPDATED_FOLDER);
    }

    @Test
    @Transactional
    void getAllEmailsByFolderNotContainsSomething() throws Exception {
        // Initialize the database
        emailRepository.saveAndFlush(email);

        // Get all the emailList where folder does not contain DEFAULT_FOLDER
        defaultEmailShouldNotBeFound("folder.doesNotContain=" + DEFAULT_FOLDER);

        // Get all the emailList where folder does not contain UPDATED_FOLDER
        defaultEmailShouldBeFound("folder.doesNotContain=" + UPDATED_FOLDER);
    }

    @Test
    @Transactional
    void getAllEmailsBySignatureTextIsEqualToSomething() throws Exception {
        // Initialize the database
        emailRepository.saveAndFlush(email);

        // Get all the emailList where signatureText equals to DEFAULT_SIGNATURE_TEXT
        defaultEmailShouldBeFound("signatureText.equals=" + DEFAULT_SIGNATURE_TEXT);

        // Get all the emailList where signatureText equals to UPDATED_SIGNATURE_TEXT
        defaultEmailShouldNotBeFound("signatureText.equals=" + UPDATED_SIGNATURE_TEXT);
    }

    @Test
    @Transactional
    void getAllEmailsBySignatureTextIsInShouldWork() throws Exception {
        // Initialize the database
        emailRepository.saveAndFlush(email);

        // Get all the emailList where signatureText in DEFAULT_SIGNATURE_TEXT or UPDATED_SIGNATURE_TEXT
        defaultEmailShouldBeFound("signatureText.in=" + DEFAULT_SIGNATURE_TEXT + "," + UPDATED_SIGNATURE_TEXT);

        // Get all the emailList where signatureText equals to UPDATED_SIGNATURE_TEXT
        defaultEmailShouldNotBeFound("signatureText.in=" + UPDATED_SIGNATURE_TEXT);
    }

    @Test
    @Transactional
    void getAllEmailsBySignatureTextIsNullOrNotNull() throws Exception {
        // Initialize the database
        emailRepository.saveAndFlush(email);

        // Get all the emailList where signatureText is not null
        defaultEmailShouldBeFound("signatureText.specified=true");

        // Get all the emailList where signatureText is null
        defaultEmailShouldNotBeFound("signatureText.specified=false");
    }

    @Test
    @Transactional
    void getAllEmailsBySignatureTextContainsSomething() throws Exception {
        // Initialize the database
        emailRepository.saveAndFlush(email);

        // Get all the emailList where signatureText contains DEFAULT_SIGNATURE_TEXT
        defaultEmailShouldBeFound("signatureText.contains=" + DEFAULT_SIGNATURE_TEXT);

        // Get all the emailList where signatureText contains UPDATED_SIGNATURE_TEXT
        defaultEmailShouldNotBeFound("signatureText.contains=" + UPDATED_SIGNATURE_TEXT);
    }

    @Test
    @Transactional
    void getAllEmailsBySignatureTextNotContainsSomething() throws Exception {
        // Initialize the database
        emailRepository.saveAndFlush(email);

        // Get all the emailList where signatureText does not contain DEFAULT_SIGNATURE_TEXT
        defaultEmailShouldNotBeFound("signatureText.doesNotContain=" + DEFAULT_SIGNATURE_TEXT);

        // Get all the emailList where signatureText does not contain UPDATED_SIGNATURE_TEXT
        defaultEmailShouldBeFound("signatureText.doesNotContain=" + UPDATED_SIGNATURE_TEXT);
    }

    @Test
    @Transactional
    void getAllEmailsByJoinedFilesIsEqualToSomething() throws Exception {
        ObjectContainingFile joinedFiles;
        if (TestUtil.findAll(em, ObjectContainingFile.class).isEmpty()) {
            emailRepository.saveAndFlush(email);
            joinedFiles = ObjectContainingFileResourceIT.createEntity(em);
        } else {
            joinedFiles = TestUtil.findAll(em, ObjectContainingFile.class).get(0);
        }
        em.persist(joinedFiles);
        em.flush();
        email.addJoinedFiles(joinedFiles);
        emailRepository.saveAndFlush(email);
        Long joinedFilesId = joinedFiles.getId();
        // Get all the emailList where joinedFiles equals to joinedFilesId
        defaultEmailShouldBeFound("joinedFilesId.equals=" + joinedFilesId);

        // Get all the emailList where joinedFiles equals to (joinedFilesId + 1)
        defaultEmailShouldNotBeFound("joinedFilesId.equals=" + (joinedFilesId + 1));
    }

    @Test
    @Transactional
    void getAllEmailsBySubEmailsIsEqualToSomething() throws Exception {
        SubEmail subEmails;
        if (TestUtil.findAll(em, SubEmail.class).isEmpty()) {
            emailRepository.saveAndFlush(email);
            subEmails = SubEmailResourceIT.createEntity(em);
        } else {
            subEmails = TestUtil.findAll(em, SubEmail.class).get(0);
        }
        em.persist(subEmails);
        em.flush();
        email.addSubEmails(subEmails);
        emailRepository.saveAndFlush(email);
        Long subEmailsId = subEmails.getId();
        // Get all the emailList where subEmails equals to subEmailsId
        defaultEmailShouldBeFound("subEmailsId.equals=" + subEmailsId);

        // Get all the emailList where subEmails equals to (subEmailsId + 1)
        defaultEmailShouldNotBeFound("subEmailsId.equals=" + (subEmailsId + 1));
    }

    @Test
    @Transactional
    void getAllEmailsByEmailEmailcredentialsIsEqualToSomething() throws Exception {
        Emailcredentials emailEmailcredentials;
        if (TestUtil.findAll(em, Emailcredentials.class).isEmpty()) {
            emailRepository.saveAndFlush(email);
            emailEmailcredentials = EmailcredentialsResourceIT.createEntity(em);
        } else {
            emailEmailcredentials = TestUtil.findAll(em, Emailcredentials.class).get(0);
        }
        em.persist(emailEmailcredentials);
        em.flush();
        email.setEmailEmailcredentials(emailEmailcredentials);
        emailRepository.saveAndFlush(email);
        Long emailEmailcredentialsId = emailEmailcredentials.getId();
        // Get all the emailList where emailEmailcredentials equals to emailEmailcredentialsId
        defaultEmailShouldBeFound("emailEmailcredentialsId.equals=" + emailEmailcredentialsId);

        // Get all the emailList where emailEmailcredentials equals to (emailEmailcredentialsId + 1)
        defaultEmailShouldNotBeFound("emailEmailcredentialsId.equals=" + (emailEmailcredentialsId + 1));
    }

    @Test
    @Transactional
    void getAllEmailsByEmailCandidateIsEqualToSomething() throws Exception {
        Candidate emailCandidate;
        if (TestUtil.findAll(em, Candidate.class).isEmpty()) {
            emailRepository.saveAndFlush(email);
            emailCandidate = CandidateResourceIT.createEntity(em);
        } else {
            emailCandidate = TestUtil.findAll(em, Candidate.class).get(0);
        }
        em.persist(emailCandidate);
        em.flush();
        email.setEmailCandidate(emailCandidate);
        emailRepository.saveAndFlush(email);
        Long emailCandidateId = emailCandidate.getId();
        // Get all the emailList where emailCandidate equals to emailCandidateId
        defaultEmailShouldBeFound("emailCandidateId.equals=" + emailCandidateId);

        // Get all the emailList where emailCandidate equals to (emailCandidateId + 1)
        defaultEmailShouldNotBeFound("emailCandidateId.equals=" + (emailCandidateId + 1));
    }

    @Test
    @Transactional
    void getAllEmailsByEmailCompanyIsEqualToSomething() throws Exception {
        Company emailCompany;
        if (TestUtil.findAll(em, Company.class).isEmpty()) {
            emailRepository.saveAndFlush(email);
            emailCompany = CompanyResourceIT.createEntity(em);
        } else {
            emailCompany = TestUtil.findAll(em, Company.class).get(0);
        }
        em.persist(emailCompany);
        em.flush();
        email.setEmailCompany(emailCompany);
        emailRepository.saveAndFlush(email);
        Long emailCompanyId = emailCompany.getId();
        // Get all the emailList where emailCompany equals to emailCompanyId
        defaultEmailShouldBeFound("emailCompanyId.equals=" + emailCompanyId);

        // Get all the emailList where emailCompany equals to (emailCompanyId + 1)
        defaultEmailShouldNotBeFound("emailCompanyId.equals=" + (emailCompanyId + 1));
    }

    @Test
    @Transactional
    void getAllEmailsByCandidateIsEqualToSomething() throws Exception {
        Candidate candidate;
        if (TestUtil.findAll(em, Candidate.class).isEmpty()) {
            emailRepository.saveAndFlush(email);
            candidate = CandidateResourceIT.createEntity(em);
        } else {
            candidate = TestUtil.findAll(em, Candidate.class).get(0);
        }
        em.persist(candidate);
        em.flush();
        email.setCandidate(candidate);
        emailRepository.saveAndFlush(email);
        Long candidateId = candidate.getId();
        // Get all the emailList where candidate equals to candidateId
        defaultEmailShouldBeFound("candidateId.equals=" + candidateId);

        // Get all the emailList where candidate equals to (candidateId + 1)
        defaultEmailShouldNotBeFound("candidateId.equals=" + (candidateId + 1));
    }

    @Test
    @Transactional
    void getAllEmailsByCompanyIsEqualToSomething() throws Exception {
        Company company;
        if (TestUtil.findAll(em, Company.class).isEmpty()) {
            emailRepository.saveAndFlush(email);
            company = CompanyResourceIT.createEntity(em);
        } else {
            company = TestUtil.findAll(em, Company.class).get(0);
        }
        em.persist(company);
        em.flush();
        email.setCompany(company);
        emailRepository.saveAndFlush(email);
        Long companyId = company.getId();
        // Get all the emailList where company equals to companyId
        defaultEmailShouldBeFound("companyId.equals=" + companyId);

        // Get all the emailList where company equals to (companyId + 1)
        defaultEmailShouldNotBeFound("companyId.equals=" + (companyId + 1));
    }

    @Test
    @Transactional
    void getAllEmailsByEmailcredentialsIsEqualToSomething() throws Exception {
        Emailcredentials emailcredentials;
        if (TestUtil.findAll(em, Emailcredentials.class).isEmpty()) {
            emailRepository.saveAndFlush(email);
            emailcredentials = EmailcredentialsResourceIT.createEntity(em);
        } else {
            emailcredentials = TestUtil.findAll(em, Emailcredentials.class).get(0);
        }
        em.persist(emailcredentials);
        em.flush();
        email.setEmailcredentials(emailcredentials);
        emailRepository.saveAndFlush(email);
        Long emailcredentialsId = emailcredentials.getId();
        // Get all the emailList where emailcredentials equals to emailcredentialsId
        defaultEmailShouldBeFound("emailcredentialsId.equals=" + emailcredentialsId);

        // Get all the emailList where emailcredentials equals to (emailcredentialsId + 1)
        defaultEmailShouldNotBeFound("emailcredentialsId.equals=" + (emailcredentialsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmailShouldBeFound(String filter) throws Exception {
        restEmailMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(email.getId().intValue())))
            .andExpect(jsonPath("$.[*].from").value(hasItem(DEFAULT_FROM)))
            .andExpect(jsonPath("$.[*].recipients").value(hasItem(DEFAULT_RECIPIENTS)))
            .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT)))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))))
            .andExpect(jsonPath("$.[*].snoozedTo").value(hasItem(sameInstant(DEFAULT_SNOOZED_TO))))
            .andExpect(jsonPath("$.[*].folder").value(hasItem(DEFAULT_FOLDER)))
            .andExpect(jsonPath("$.[*].signatureText").value(hasItem(DEFAULT_SIGNATURE_TEXT)))
            .andExpect(jsonPath("$.[*].signatureImageContentType").value(hasItem(DEFAULT_SIGNATURE_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].signatureImage").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_SIGNATURE_IMAGE))));

        // Check, that the count call also returns 1
        restEmailMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmailShouldNotBeFound(String filter) throws Exception {
        restEmailMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmailMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEmail() throws Exception {
        // Get the email
        restEmailMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEmail() throws Exception {
        // Initialize the database
        emailRepository.saveAndFlush(email);

        int databaseSizeBeforeUpdate = emailRepository.findAll().size();

        // Update the email
        Email updatedEmail = emailRepository.findById(email.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEmail are not directly saved in db
        em.detach(updatedEmail);
        updatedEmail
            .from(UPDATED_FROM)
            .recipients(UPDATED_RECIPIENTS)
            .subject(UPDATED_SUBJECT)
            .text(UPDATED_TEXT)
            .type(UPDATED_TYPE)
            .date(UPDATED_DATE)
            .snoozedTo(UPDATED_SNOOZED_TO)
            .folder(UPDATED_FOLDER)
            .signatureText(UPDATED_SIGNATURE_TEXT)
            .signatureImage(UPDATED_SIGNATURE_IMAGE)
            .signatureImageContentType(UPDATED_SIGNATURE_IMAGE_CONTENT_TYPE);
        EmailDTO emailDTO = emailMapper.toDto(updatedEmail);

        restEmailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, emailDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(emailDTO))
            )
            .andExpect(status().isOk());

        // Validate the Email in the database
        List<Email> emailList = emailRepository.findAll();
        assertThat(emailList).hasSize(databaseSizeBeforeUpdate);
        Email testEmail = emailList.get(emailList.size() - 1);
        assertThat(testEmail.getFrom()).isEqualTo(UPDATED_FROM);
        assertThat(testEmail.getRecipients()).isEqualTo(UPDATED_RECIPIENTS);
        assertThat(testEmail.getSubject()).isEqualTo(UPDATED_SUBJECT);
        assertThat(testEmail.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testEmail.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testEmail.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testEmail.getSnoozedTo()).isEqualTo(UPDATED_SNOOZED_TO);
        assertThat(testEmail.getFolder()).isEqualTo(UPDATED_FOLDER);
        assertThat(testEmail.getSignatureText()).isEqualTo(UPDATED_SIGNATURE_TEXT);
        assertThat(testEmail.getSignatureImage()).isEqualTo(UPDATED_SIGNATURE_IMAGE);
        assertThat(testEmail.getSignatureImageContentType()).isEqualTo(UPDATED_SIGNATURE_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingEmail() throws Exception {
        int databaseSizeBeforeUpdate = emailRepository.findAll().size();
        email.setId(longCount.incrementAndGet());

        // Create the Email
        EmailDTO emailDTO = emailMapper.toDto(email);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, emailDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(emailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Email in the database
        List<Email> emailList = emailRepository.findAll();
        assertThat(emailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmail() throws Exception {
        int databaseSizeBeforeUpdate = emailRepository.findAll().size();
        email.setId(longCount.incrementAndGet());

        // Create the Email
        EmailDTO emailDTO = emailMapper.toDto(email);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(emailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Email in the database
        List<Email> emailList = emailRepository.findAll();
        assertThat(emailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmail() throws Exception {
        int databaseSizeBeforeUpdate = emailRepository.findAll().size();
        email.setId(longCount.incrementAndGet());

        // Create the Email
        EmailDTO emailDTO = emailMapper.toDto(email);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmailMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(emailDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Email in the database
        List<Email> emailList = emailRepository.findAll();
        assertThat(emailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmailWithPatch() throws Exception {
        // Initialize the database
        emailRepository.saveAndFlush(email);

        int databaseSizeBeforeUpdate = emailRepository.findAll().size();

        // Update the email using partial update
        Email partialUpdatedEmail = new Email();
        partialUpdatedEmail.setId(email.getId());

        partialUpdatedEmail
            .subject(UPDATED_SUBJECT)
            .text(UPDATED_TEXT)
            .type(UPDATED_TYPE)
            .date(UPDATED_DATE)
            .snoozedTo(UPDATED_SNOOZED_TO)
            .folder(UPDATED_FOLDER);

        restEmailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmail.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmail))
            )
            .andExpect(status().isOk());

        // Validate the Email in the database
        List<Email> emailList = emailRepository.findAll();
        assertThat(emailList).hasSize(databaseSizeBeforeUpdate);
        Email testEmail = emailList.get(emailList.size() - 1);
        assertThat(testEmail.getFrom()).isEqualTo(DEFAULT_FROM);
        assertThat(testEmail.getRecipients()).isEqualTo(DEFAULT_RECIPIENTS);
        assertThat(testEmail.getSubject()).isEqualTo(UPDATED_SUBJECT);
        assertThat(testEmail.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testEmail.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testEmail.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testEmail.getSnoozedTo()).isEqualTo(UPDATED_SNOOZED_TO);
        assertThat(testEmail.getFolder()).isEqualTo(UPDATED_FOLDER);
        assertThat(testEmail.getSignatureText()).isEqualTo(DEFAULT_SIGNATURE_TEXT);
        assertThat(testEmail.getSignatureImage()).isEqualTo(DEFAULT_SIGNATURE_IMAGE);
        assertThat(testEmail.getSignatureImageContentType()).isEqualTo(DEFAULT_SIGNATURE_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateEmailWithPatch() throws Exception {
        // Initialize the database
        emailRepository.saveAndFlush(email);

        int databaseSizeBeforeUpdate = emailRepository.findAll().size();

        // Update the email using partial update
        Email partialUpdatedEmail = new Email();
        partialUpdatedEmail.setId(email.getId());

        partialUpdatedEmail
            .from(UPDATED_FROM)
            .recipients(UPDATED_RECIPIENTS)
            .subject(UPDATED_SUBJECT)
            .text(UPDATED_TEXT)
            .type(UPDATED_TYPE)
            .date(UPDATED_DATE)
            .snoozedTo(UPDATED_SNOOZED_TO)
            .folder(UPDATED_FOLDER)
            .signatureText(UPDATED_SIGNATURE_TEXT)
            .signatureImage(UPDATED_SIGNATURE_IMAGE)
            .signatureImageContentType(UPDATED_SIGNATURE_IMAGE_CONTENT_TYPE);

        restEmailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmail.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmail))
            )
            .andExpect(status().isOk());

        // Validate the Email in the database
        List<Email> emailList = emailRepository.findAll();
        assertThat(emailList).hasSize(databaseSizeBeforeUpdate);
        Email testEmail = emailList.get(emailList.size() - 1);
        assertThat(testEmail.getFrom()).isEqualTo(UPDATED_FROM);
        assertThat(testEmail.getRecipients()).isEqualTo(UPDATED_RECIPIENTS);
        assertThat(testEmail.getSubject()).isEqualTo(UPDATED_SUBJECT);
        assertThat(testEmail.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testEmail.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testEmail.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testEmail.getSnoozedTo()).isEqualTo(UPDATED_SNOOZED_TO);
        assertThat(testEmail.getFolder()).isEqualTo(UPDATED_FOLDER);
        assertThat(testEmail.getSignatureText()).isEqualTo(UPDATED_SIGNATURE_TEXT);
        assertThat(testEmail.getSignatureImage()).isEqualTo(UPDATED_SIGNATURE_IMAGE);
        assertThat(testEmail.getSignatureImageContentType()).isEqualTo(UPDATED_SIGNATURE_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingEmail() throws Exception {
        int databaseSizeBeforeUpdate = emailRepository.findAll().size();
        email.setId(longCount.incrementAndGet());

        // Create the Email
        EmailDTO emailDTO = emailMapper.toDto(email);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, emailDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(emailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Email in the database
        List<Email> emailList = emailRepository.findAll();
        assertThat(emailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmail() throws Exception {
        int databaseSizeBeforeUpdate = emailRepository.findAll().size();
        email.setId(longCount.incrementAndGet());

        // Create the Email
        EmailDTO emailDTO = emailMapper.toDto(email);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(emailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Email in the database
        List<Email> emailList = emailRepository.findAll();
        assertThat(emailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmail() throws Exception {
        int databaseSizeBeforeUpdate = emailRepository.findAll().size();
        email.setId(longCount.incrementAndGet());

        // Create the Email
        EmailDTO emailDTO = emailMapper.toDto(email);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmailMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(emailDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Email in the database
        List<Email> emailList = emailRepository.findAll();
        assertThat(emailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmail() throws Exception {
        // Initialize the database
        emailRepository.saveAndFlush(email);

        int databaseSizeBeforeDelete = emailRepository.findAll().size();

        // Delete the email
        restEmailMockMvc
            .perform(delete(ENTITY_API_URL_ID, email.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Email> emailList = emailRepository.findAll();
        assertThat(emailList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
