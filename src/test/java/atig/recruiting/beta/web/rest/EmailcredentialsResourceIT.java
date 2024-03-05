package atig.recruiting.beta.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import atig.recruiting.beta.IntegrationTest;
import atig.recruiting.beta.domain.Email;
import atig.recruiting.beta.domain.Emailcredentials;
import atig.recruiting.beta.repository.EmailcredentialsRepository;
import atig.recruiting.beta.service.dto.EmailcredentialsDTO;
import atig.recruiting.beta.service.mapper.EmailcredentialsMapper;
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
 * Integration tests for the {@link EmailcredentialsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EmailcredentialsResourceIT {

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/emailcredentials";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EmailcredentialsRepository emailcredentialsRepository;

    @Autowired
    private EmailcredentialsMapper emailcredentialsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmailcredentialsMockMvc;

    private Emailcredentials emailcredentials;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Emailcredentials createEntity(EntityManager em) {
        Emailcredentials emailcredentials = new Emailcredentials().username(DEFAULT_USERNAME).password(DEFAULT_PASSWORD);
        return emailcredentials;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Emailcredentials createUpdatedEntity(EntityManager em) {
        Emailcredentials emailcredentials = new Emailcredentials().username(UPDATED_USERNAME).password(UPDATED_PASSWORD);
        return emailcredentials;
    }

    @BeforeEach
    public void initTest() {
        emailcredentials = createEntity(em);
    }

    @Test
    @Transactional
    void createEmailcredentials() throws Exception {
        int databaseSizeBeforeCreate = emailcredentialsRepository.findAll().size();
        // Create the Emailcredentials
        EmailcredentialsDTO emailcredentialsDTO = emailcredentialsMapper.toDto(emailcredentials);
        restEmailcredentialsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(emailcredentialsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Emailcredentials in the database
        List<Emailcredentials> emailcredentialsList = emailcredentialsRepository.findAll();
        assertThat(emailcredentialsList).hasSize(databaseSizeBeforeCreate + 1);
        Emailcredentials testEmailcredentials = emailcredentialsList.get(emailcredentialsList.size() - 1);
        assertThat(testEmailcredentials.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testEmailcredentials.getPassword()).isEqualTo(DEFAULT_PASSWORD);
    }

    @Test
    @Transactional
    void createEmailcredentialsWithExistingId() throws Exception {
        // Create the Emailcredentials with an existing ID
        emailcredentials.setId(1L);
        EmailcredentialsDTO emailcredentialsDTO = emailcredentialsMapper.toDto(emailcredentials);

        int databaseSizeBeforeCreate = emailcredentialsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmailcredentialsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(emailcredentialsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Emailcredentials in the database
        List<Emailcredentials> emailcredentialsList = emailcredentialsRepository.findAll();
        assertThat(emailcredentialsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEmailcredentials() throws Exception {
        // Initialize the database
        emailcredentialsRepository.saveAndFlush(emailcredentials);

        // Get all the emailcredentialsList
        restEmailcredentialsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emailcredentials.getId().intValue())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)));
    }

    @Test
    @Transactional
    void getEmailcredentials() throws Exception {
        // Initialize the database
        emailcredentialsRepository.saveAndFlush(emailcredentials);

        // Get the emailcredentials
        restEmailcredentialsMockMvc
            .perform(get(ENTITY_API_URL_ID, emailcredentials.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(emailcredentials.getId().intValue()))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD));
    }

    @Test
    @Transactional
    void getEmailcredentialsByIdFiltering() throws Exception {
        // Initialize the database
        emailcredentialsRepository.saveAndFlush(emailcredentials);

        Long id = emailcredentials.getId();

        defaultEmailcredentialsShouldBeFound("id.equals=" + id);
        defaultEmailcredentialsShouldNotBeFound("id.notEquals=" + id);

        defaultEmailcredentialsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmailcredentialsShouldNotBeFound("id.greaterThan=" + id);

        defaultEmailcredentialsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmailcredentialsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEmailcredentialsByUsernameIsEqualToSomething() throws Exception {
        // Initialize the database
        emailcredentialsRepository.saveAndFlush(emailcredentials);

        // Get all the emailcredentialsList where username equals to DEFAULT_USERNAME
        defaultEmailcredentialsShouldBeFound("username.equals=" + DEFAULT_USERNAME);

        // Get all the emailcredentialsList where username equals to UPDATED_USERNAME
        defaultEmailcredentialsShouldNotBeFound("username.equals=" + UPDATED_USERNAME);
    }

    @Test
    @Transactional
    void getAllEmailcredentialsByUsernameIsInShouldWork() throws Exception {
        // Initialize the database
        emailcredentialsRepository.saveAndFlush(emailcredentials);

        // Get all the emailcredentialsList where username in DEFAULT_USERNAME or UPDATED_USERNAME
        defaultEmailcredentialsShouldBeFound("username.in=" + DEFAULT_USERNAME + "," + UPDATED_USERNAME);

        // Get all the emailcredentialsList where username equals to UPDATED_USERNAME
        defaultEmailcredentialsShouldNotBeFound("username.in=" + UPDATED_USERNAME);
    }

    @Test
    @Transactional
    void getAllEmailcredentialsByUsernameIsNullOrNotNull() throws Exception {
        // Initialize the database
        emailcredentialsRepository.saveAndFlush(emailcredentials);

        // Get all the emailcredentialsList where username is not null
        defaultEmailcredentialsShouldBeFound("username.specified=true");

        // Get all the emailcredentialsList where username is null
        defaultEmailcredentialsShouldNotBeFound("username.specified=false");
    }

    @Test
    @Transactional
    void getAllEmailcredentialsByUsernameContainsSomething() throws Exception {
        // Initialize the database
        emailcredentialsRepository.saveAndFlush(emailcredentials);

        // Get all the emailcredentialsList where username contains DEFAULT_USERNAME
        defaultEmailcredentialsShouldBeFound("username.contains=" + DEFAULT_USERNAME);

        // Get all the emailcredentialsList where username contains UPDATED_USERNAME
        defaultEmailcredentialsShouldNotBeFound("username.contains=" + UPDATED_USERNAME);
    }

    @Test
    @Transactional
    void getAllEmailcredentialsByUsernameNotContainsSomething() throws Exception {
        // Initialize the database
        emailcredentialsRepository.saveAndFlush(emailcredentials);

        // Get all the emailcredentialsList where username does not contain DEFAULT_USERNAME
        defaultEmailcredentialsShouldNotBeFound("username.doesNotContain=" + DEFAULT_USERNAME);

        // Get all the emailcredentialsList where username does not contain UPDATED_USERNAME
        defaultEmailcredentialsShouldBeFound("username.doesNotContain=" + UPDATED_USERNAME);
    }

    @Test
    @Transactional
    void getAllEmailcredentialsByPasswordIsEqualToSomething() throws Exception {
        // Initialize the database
        emailcredentialsRepository.saveAndFlush(emailcredentials);

        // Get all the emailcredentialsList where password equals to DEFAULT_PASSWORD
        defaultEmailcredentialsShouldBeFound("password.equals=" + DEFAULT_PASSWORD);

        // Get all the emailcredentialsList where password equals to UPDATED_PASSWORD
        defaultEmailcredentialsShouldNotBeFound("password.equals=" + UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    void getAllEmailcredentialsByPasswordIsInShouldWork() throws Exception {
        // Initialize the database
        emailcredentialsRepository.saveAndFlush(emailcredentials);

        // Get all the emailcredentialsList where password in DEFAULT_PASSWORD or UPDATED_PASSWORD
        defaultEmailcredentialsShouldBeFound("password.in=" + DEFAULT_PASSWORD + "," + UPDATED_PASSWORD);

        // Get all the emailcredentialsList where password equals to UPDATED_PASSWORD
        defaultEmailcredentialsShouldNotBeFound("password.in=" + UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    void getAllEmailcredentialsByPasswordIsNullOrNotNull() throws Exception {
        // Initialize the database
        emailcredentialsRepository.saveAndFlush(emailcredentials);

        // Get all the emailcredentialsList where password is not null
        defaultEmailcredentialsShouldBeFound("password.specified=true");

        // Get all the emailcredentialsList where password is null
        defaultEmailcredentialsShouldNotBeFound("password.specified=false");
    }

    @Test
    @Transactional
    void getAllEmailcredentialsByPasswordContainsSomething() throws Exception {
        // Initialize the database
        emailcredentialsRepository.saveAndFlush(emailcredentials);

        // Get all the emailcredentialsList where password contains DEFAULT_PASSWORD
        defaultEmailcredentialsShouldBeFound("password.contains=" + DEFAULT_PASSWORD);

        // Get all the emailcredentialsList where password contains UPDATED_PASSWORD
        defaultEmailcredentialsShouldNotBeFound("password.contains=" + UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    void getAllEmailcredentialsByPasswordNotContainsSomething() throws Exception {
        // Initialize the database
        emailcredentialsRepository.saveAndFlush(emailcredentials);

        // Get all the emailcredentialsList where password does not contain DEFAULT_PASSWORD
        defaultEmailcredentialsShouldNotBeFound("password.doesNotContain=" + DEFAULT_PASSWORD);

        // Get all the emailcredentialsList where password does not contain UPDATED_PASSWORD
        defaultEmailcredentialsShouldBeFound("password.doesNotContain=" + UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    void getAllEmailcredentialsByEmailsIsEqualToSomething() throws Exception {
        Email emails;
        if (TestUtil.findAll(em, Email.class).isEmpty()) {
            emailcredentialsRepository.saveAndFlush(emailcredentials);
            emails = EmailResourceIT.createEntity(em);
        } else {
            emails = TestUtil.findAll(em, Email.class).get(0);
        }
        em.persist(emails);
        em.flush();
        emailcredentials.addEmails(emails);
        emailcredentialsRepository.saveAndFlush(emailcredentials);
        Long emailsId = emails.getId();
        // Get all the emailcredentialsList where emails equals to emailsId
        defaultEmailcredentialsShouldBeFound("emailsId.equals=" + emailsId);

        // Get all the emailcredentialsList where emails equals to (emailsId + 1)
        defaultEmailcredentialsShouldNotBeFound("emailsId.equals=" + (emailsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmailcredentialsShouldBeFound(String filter) throws Exception {
        restEmailcredentialsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emailcredentials.getId().intValue())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)));

        // Check, that the count call also returns 1
        restEmailcredentialsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmailcredentialsShouldNotBeFound(String filter) throws Exception {
        restEmailcredentialsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmailcredentialsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEmailcredentials() throws Exception {
        // Get the emailcredentials
        restEmailcredentialsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEmailcredentials() throws Exception {
        // Initialize the database
        emailcredentialsRepository.saveAndFlush(emailcredentials);

        int databaseSizeBeforeUpdate = emailcredentialsRepository.findAll().size();

        // Update the emailcredentials
        Emailcredentials updatedEmailcredentials = emailcredentialsRepository.findById(emailcredentials.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEmailcredentials are not directly saved in db
        em.detach(updatedEmailcredentials);
        updatedEmailcredentials.username(UPDATED_USERNAME).password(UPDATED_PASSWORD);
        EmailcredentialsDTO emailcredentialsDTO = emailcredentialsMapper.toDto(updatedEmailcredentials);

        restEmailcredentialsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, emailcredentialsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(emailcredentialsDTO))
            )
            .andExpect(status().isOk());

        // Validate the Emailcredentials in the database
        List<Emailcredentials> emailcredentialsList = emailcredentialsRepository.findAll();
        assertThat(emailcredentialsList).hasSize(databaseSizeBeforeUpdate);
        Emailcredentials testEmailcredentials = emailcredentialsList.get(emailcredentialsList.size() - 1);
        assertThat(testEmailcredentials.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testEmailcredentials.getPassword()).isEqualTo(UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    void putNonExistingEmailcredentials() throws Exception {
        int databaseSizeBeforeUpdate = emailcredentialsRepository.findAll().size();
        emailcredentials.setId(longCount.incrementAndGet());

        // Create the Emailcredentials
        EmailcredentialsDTO emailcredentialsDTO = emailcredentialsMapper.toDto(emailcredentials);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmailcredentialsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, emailcredentialsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(emailcredentialsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Emailcredentials in the database
        List<Emailcredentials> emailcredentialsList = emailcredentialsRepository.findAll();
        assertThat(emailcredentialsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmailcredentials() throws Exception {
        int databaseSizeBeforeUpdate = emailcredentialsRepository.findAll().size();
        emailcredentials.setId(longCount.incrementAndGet());

        // Create the Emailcredentials
        EmailcredentialsDTO emailcredentialsDTO = emailcredentialsMapper.toDto(emailcredentials);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmailcredentialsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(emailcredentialsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Emailcredentials in the database
        List<Emailcredentials> emailcredentialsList = emailcredentialsRepository.findAll();
        assertThat(emailcredentialsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmailcredentials() throws Exception {
        int databaseSizeBeforeUpdate = emailcredentialsRepository.findAll().size();
        emailcredentials.setId(longCount.incrementAndGet());

        // Create the Emailcredentials
        EmailcredentialsDTO emailcredentialsDTO = emailcredentialsMapper.toDto(emailcredentials);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmailcredentialsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(emailcredentialsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Emailcredentials in the database
        List<Emailcredentials> emailcredentialsList = emailcredentialsRepository.findAll();
        assertThat(emailcredentialsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmailcredentialsWithPatch() throws Exception {
        // Initialize the database
        emailcredentialsRepository.saveAndFlush(emailcredentials);

        int databaseSizeBeforeUpdate = emailcredentialsRepository.findAll().size();

        // Update the emailcredentials using partial update
        Emailcredentials partialUpdatedEmailcredentials = new Emailcredentials();
        partialUpdatedEmailcredentials.setId(emailcredentials.getId());

        restEmailcredentialsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmailcredentials.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmailcredentials))
            )
            .andExpect(status().isOk());

        // Validate the Emailcredentials in the database
        List<Emailcredentials> emailcredentialsList = emailcredentialsRepository.findAll();
        assertThat(emailcredentialsList).hasSize(databaseSizeBeforeUpdate);
        Emailcredentials testEmailcredentials = emailcredentialsList.get(emailcredentialsList.size() - 1);
        assertThat(testEmailcredentials.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testEmailcredentials.getPassword()).isEqualTo(DEFAULT_PASSWORD);
    }

    @Test
    @Transactional
    void fullUpdateEmailcredentialsWithPatch() throws Exception {
        // Initialize the database
        emailcredentialsRepository.saveAndFlush(emailcredentials);

        int databaseSizeBeforeUpdate = emailcredentialsRepository.findAll().size();

        // Update the emailcredentials using partial update
        Emailcredentials partialUpdatedEmailcredentials = new Emailcredentials();
        partialUpdatedEmailcredentials.setId(emailcredentials.getId());

        partialUpdatedEmailcredentials.username(UPDATED_USERNAME).password(UPDATED_PASSWORD);

        restEmailcredentialsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmailcredentials.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmailcredentials))
            )
            .andExpect(status().isOk());

        // Validate the Emailcredentials in the database
        List<Emailcredentials> emailcredentialsList = emailcredentialsRepository.findAll();
        assertThat(emailcredentialsList).hasSize(databaseSizeBeforeUpdate);
        Emailcredentials testEmailcredentials = emailcredentialsList.get(emailcredentialsList.size() - 1);
        assertThat(testEmailcredentials.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testEmailcredentials.getPassword()).isEqualTo(UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    void patchNonExistingEmailcredentials() throws Exception {
        int databaseSizeBeforeUpdate = emailcredentialsRepository.findAll().size();
        emailcredentials.setId(longCount.incrementAndGet());

        // Create the Emailcredentials
        EmailcredentialsDTO emailcredentialsDTO = emailcredentialsMapper.toDto(emailcredentials);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmailcredentialsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, emailcredentialsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(emailcredentialsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Emailcredentials in the database
        List<Emailcredentials> emailcredentialsList = emailcredentialsRepository.findAll();
        assertThat(emailcredentialsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmailcredentials() throws Exception {
        int databaseSizeBeforeUpdate = emailcredentialsRepository.findAll().size();
        emailcredentials.setId(longCount.incrementAndGet());

        // Create the Emailcredentials
        EmailcredentialsDTO emailcredentialsDTO = emailcredentialsMapper.toDto(emailcredentials);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmailcredentialsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(emailcredentialsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Emailcredentials in the database
        List<Emailcredentials> emailcredentialsList = emailcredentialsRepository.findAll();
        assertThat(emailcredentialsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmailcredentials() throws Exception {
        int databaseSizeBeforeUpdate = emailcredentialsRepository.findAll().size();
        emailcredentials.setId(longCount.incrementAndGet());

        // Create the Emailcredentials
        EmailcredentialsDTO emailcredentialsDTO = emailcredentialsMapper.toDto(emailcredentials);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmailcredentialsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(emailcredentialsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Emailcredentials in the database
        List<Emailcredentials> emailcredentialsList = emailcredentialsRepository.findAll();
        assertThat(emailcredentialsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmailcredentials() throws Exception {
        // Initialize the database
        emailcredentialsRepository.saveAndFlush(emailcredentials);

        int databaseSizeBeforeDelete = emailcredentialsRepository.findAll().size();

        // Delete the emailcredentials
        restEmailcredentialsMockMvc
            .perform(delete(ENTITY_API_URL_ID, emailcredentials.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Emailcredentials> emailcredentialsList = emailcredentialsRepository.findAll();
        assertThat(emailcredentialsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
