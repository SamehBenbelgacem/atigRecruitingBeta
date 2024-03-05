package atig.recruiting.beta.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import atig.recruiting.beta.IntegrationTest;
import atig.recruiting.beta.domain.CandidateAdditionalInfos;
import atig.recruiting.beta.domain.Email;
import atig.recruiting.beta.domain.Note;
import atig.recruiting.beta.domain.ObjectContainingFile;
import atig.recruiting.beta.repository.ObjectContainingFileRepository;
import atig.recruiting.beta.service.dto.ObjectContainingFileDTO;
import atig.recruiting.beta.service.mapper.ObjectContainingFileMapper;
import jakarta.persistence.EntityManager;
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
 * Integration tests for the {@link ObjectContainingFileResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ObjectContainingFileResourceIT {

    private static final byte[] DEFAULT_FILE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FILE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FILE_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/object-containing-files";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectContainingFileRepository objectContainingFileRepository;

    @Autowired
    private ObjectContainingFileMapper objectContainingFileMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restObjectContainingFileMockMvc;

    private ObjectContainingFile objectContainingFile;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ObjectContainingFile createEntity(EntityManager em) {
        ObjectContainingFile objectContainingFile = new ObjectContainingFile()
            .file(DEFAULT_FILE)
            .fileContentType(DEFAULT_FILE_CONTENT_TYPE);
        return objectContainingFile;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ObjectContainingFile createUpdatedEntity(EntityManager em) {
        ObjectContainingFile objectContainingFile = new ObjectContainingFile()
            .file(UPDATED_FILE)
            .fileContentType(UPDATED_FILE_CONTENT_TYPE);
        return objectContainingFile;
    }

    @BeforeEach
    public void initTest() {
        objectContainingFile = createEntity(em);
    }

    @Test
    @Transactional
    void createObjectContainingFile() throws Exception {
        int databaseSizeBeforeCreate = objectContainingFileRepository.findAll().size();
        // Create the ObjectContainingFile
        ObjectContainingFileDTO objectContainingFileDTO = objectContainingFileMapper.toDto(objectContainingFile);
        restObjectContainingFileMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(objectContainingFileDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ObjectContainingFile in the database
        List<ObjectContainingFile> objectContainingFileList = objectContainingFileRepository.findAll();
        assertThat(objectContainingFileList).hasSize(databaseSizeBeforeCreate + 1);
        ObjectContainingFile testObjectContainingFile = objectContainingFileList.get(objectContainingFileList.size() - 1);
        assertThat(testObjectContainingFile.getFile()).isEqualTo(DEFAULT_FILE);
        assertThat(testObjectContainingFile.getFileContentType()).isEqualTo(DEFAULT_FILE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createObjectContainingFileWithExistingId() throws Exception {
        // Create the ObjectContainingFile with an existing ID
        objectContainingFile.setId(1L);
        ObjectContainingFileDTO objectContainingFileDTO = objectContainingFileMapper.toDto(objectContainingFile);

        int databaseSizeBeforeCreate = objectContainingFileRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restObjectContainingFileMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(objectContainingFileDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ObjectContainingFile in the database
        List<ObjectContainingFile> objectContainingFileList = objectContainingFileRepository.findAll();
        assertThat(objectContainingFileList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllObjectContainingFiles() throws Exception {
        // Initialize the database
        objectContainingFileRepository.saveAndFlush(objectContainingFile);

        // Get all the objectContainingFileList
        restObjectContainingFileMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(objectContainingFile.getId().intValue())))
            .andExpect(jsonPath("$.[*].fileContentType").value(hasItem(DEFAULT_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].file").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_FILE))));
    }

    @Test
    @Transactional
    void getObjectContainingFile() throws Exception {
        // Initialize the database
        objectContainingFileRepository.saveAndFlush(objectContainingFile);

        // Get the objectContainingFile
        restObjectContainingFileMockMvc
            .perform(get(ENTITY_API_URL_ID, objectContainingFile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(objectContainingFile.getId().intValue()))
            .andExpect(jsonPath("$.fileContentType").value(DEFAULT_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.file").value(Base64.getEncoder().encodeToString(DEFAULT_FILE)));
    }

    @Test
    @Transactional
    void getObjectContainingFilesByIdFiltering() throws Exception {
        // Initialize the database
        objectContainingFileRepository.saveAndFlush(objectContainingFile);

        Long id = objectContainingFile.getId();

        defaultObjectContainingFileShouldBeFound("id.equals=" + id);
        defaultObjectContainingFileShouldNotBeFound("id.notEquals=" + id);

        defaultObjectContainingFileShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultObjectContainingFileShouldNotBeFound("id.greaterThan=" + id);

        defaultObjectContainingFileShouldBeFound("id.lessThanOrEqual=" + id);
        defaultObjectContainingFileShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllObjectContainingFilesByCandidateDocsIsEqualToSomething() throws Exception {
        CandidateAdditionalInfos candidateDocs;
        if (TestUtil.findAll(em, CandidateAdditionalInfos.class).isEmpty()) {
            objectContainingFileRepository.saveAndFlush(objectContainingFile);
            candidateDocs = CandidateAdditionalInfosResourceIT.createEntity(em);
        } else {
            candidateDocs = TestUtil.findAll(em, CandidateAdditionalInfos.class).get(0);
        }
        em.persist(candidateDocs);
        em.flush();
        objectContainingFile.setCandidateDocs(candidateDocs);
        objectContainingFileRepository.saveAndFlush(objectContainingFile);
        Long candidateDocsId = candidateDocs.getId();
        // Get all the objectContainingFileList where candidateDocs equals to candidateDocsId
        defaultObjectContainingFileShouldBeFound("candidateDocsId.equals=" + candidateDocsId);

        // Get all the objectContainingFileList where candidateDocs equals to (candidateDocsId + 1)
        defaultObjectContainingFileShouldNotBeFound("candidateDocsId.equals=" + (candidateDocsId + 1));
    }

    @Test
    @Transactional
    void getAllObjectContainingFilesByNoteDocsIsEqualToSomething() throws Exception {
        Note noteDocs;
        if (TestUtil.findAll(em, Note.class).isEmpty()) {
            objectContainingFileRepository.saveAndFlush(objectContainingFile);
            noteDocs = NoteResourceIT.createEntity(em);
        } else {
            noteDocs = TestUtil.findAll(em, Note.class).get(0);
        }
        em.persist(noteDocs);
        em.flush();
        objectContainingFile.setNoteDocs(noteDocs);
        objectContainingFileRepository.saveAndFlush(objectContainingFile);
        Long noteDocsId = noteDocs.getId();
        // Get all the objectContainingFileList where noteDocs equals to noteDocsId
        defaultObjectContainingFileShouldBeFound("noteDocsId.equals=" + noteDocsId);

        // Get all the objectContainingFileList where noteDocs equals to (noteDocsId + 1)
        defaultObjectContainingFileShouldNotBeFound("noteDocsId.equals=" + (noteDocsId + 1));
    }

    @Test
    @Transactional
    void getAllObjectContainingFilesByEmailDocsIsEqualToSomething() throws Exception {
        Email emailDocs;
        if (TestUtil.findAll(em, Email.class).isEmpty()) {
            objectContainingFileRepository.saveAndFlush(objectContainingFile);
            emailDocs = EmailResourceIT.createEntity(em);
        } else {
            emailDocs = TestUtil.findAll(em, Email.class).get(0);
        }
        em.persist(emailDocs);
        em.flush();
        objectContainingFile.setEmailDocs(emailDocs);
        objectContainingFileRepository.saveAndFlush(objectContainingFile);
        Long emailDocsId = emailDocs.getId();
        // Get all the objectContainingFileList where emailDocs equals to emailDocsId
        defaultObjectContainingFileShouldBeFound("emailDocsId.equals=" + emailDocsId);

        // Get all the objectContainingFileList where emailDocs equals to (emailDocsId + 1)
        defaultObjectContainingFileShouldNotBeFound("emailDocsId.equals=" + (emailDocsId + 1));
    }

    @Test
    @Transactional
    void getAllObjectContainingFilesByCandidateAdditionalInfosIsEqualToSomething() throws Exception {
        CandidateAdditionalInfos candidateAdditionalInfos;
        if (TestUtil.findAll(em, CandidateAdditionalInfos.class).isEmpty()) {
            objectContainingFileRepository.saveAndFlush(objectContainingFile);
            candidateAdditionalInfos = CandidateAdditionalInfosResourceIT.createEntity(em);
        } else {
            candidateAdditionalInfos = TestUtil.findAll(em, CandidateAdditionalInfos.class).get(0);
        }
        em.persist(candidateAdditionalInfos);
        em.flush();
        objectContainingFile.setCandidateAdditionalInfos(candidateAdditionalInfos);
        objectContainingFileRepository.saveAndFlush(objectContainingFile);
        Long candidateAdditionalInfosId = candidateAdditionalInfos.getId();
        // Get all the objectContainingFileList where candidateAdditionalInfos equals to candidateAdditionalInfosId
        defaultObjectContainingFileShouldBeFound("candidateAdditionalInfosId.equals=" + candidateAdditionalInfosId);

        // Get all the objectContainingFileList where candidateAdditionalInfos equals to (candidateAdditionalInfosId + 1)
        defaultObjectContainingFileShouldNotBeFound("candidateAdditionalInfosId.equals=" + (candidateAdditionalInfosId + 1));
    }

    @Test
    @Transactional
    void getAllObjectContainingFilesByNoteIsEqualToSomething() throws Exception {
        Note note;
        if (TestUtil.findAll(em, Note.class).isEmpty()) {
            objectContainingFileRepository.saveAndFlush(objectContainingFile);
            note = NoteResourceIT.createEntity(em);
        } else {
            note = TestUtil.findAll(em, Note.class).get(0);
        }
        em.persist(note);
        em.flush();
        objectContainingFile.setNote(note);
        objectContainingFileRepository.saveAndFlush(objectContainingFile);
        Long noteId = note.getId();
        // Get all the objectContainingFileList where note equals to noteId
        defaultObjectContainingFileShouldBeFound("noteId.equals=" + noteId);

        // Get all the objectContainingFileList where note equals to (noteId + 1)
        defaultObjectContainingFileShouldNotBeFound("noteId.equals=" + (noteId + 1));
    }

    @Test
    @Transactional
    void getAllObjectContainingFilesByEmailIsEqualToSomething() throws Exception {
        Email email;
        if (TestUtil.findAll(em, Email.class).isEmpty()) {
            objectContainingFileRepository.saveAndFlush(objectContainingFile);
            email = EmailResourceIT.createEntity(em);
        } else {
            email = TestUtil.findAll(em, Email.class).get(0);
        }
        em.persist(email);
        em.flush();
        objectContainingFile.setEmail(email);
        objectContainingFileRepository.saveAndFlush(objectContainingFile);
        Long emailId = email.getId();
        // Get all the objectContainingFileList where email equals to emailId
        defaultObjectContainingFileShouldBeFound("emailId.equals=" + emailId);

        // Get all the objectContainingFileList where email equals to (emailId + 1)
        defaultObjectContainingFileShouldNotBeFound("emailId.equals=" + (emailId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultObjectContainingFileShouldBeFound(String filter) throws Exception {
        restObjectContainingFileMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(objectContainingFile.getId().intValue())))
            .andExpect(jsonPath("$.[*].fileContentType").value(hasItem(DEFAULT_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].file").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_FILE))));

        // Check, that the count call also returns 1
        restObjectContainingFileMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultObjectContainingFileShouldNotBeFound(String filter) throws Exception {
        restObjectContainingFileMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restObjectContainingFileMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingObjectContainingFile() throws Exception {
        // Get the objectContainingFile
        restObjectContainingFileMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingObjectContainingFile() throws Exception {
        // Initialize the database
        objectContainingFileRepository.saveAndFlush(objectContainingFile);

        int databaseSizeBeforeUpdate = objectContainingFileRepository.findAll().size();

        // Update the objectContainingFile
        ObjectContainingFile updatedObjectContainingFile = objectContainingFileRepository
            .findById(objectContainingFile.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedObjectContainingFile are not directly saved in db
        em.detach(updatedObjectContainingFile);
        updatedObjectContainingFile.file(UPDATED_FILE).fileContentType(UPDATED_FILE_CONTENT_TYPE);
        ObjectContainingFileDTO objectContainingFileDTO = objectContainingFileMapper.toDto(updatedObjectContainingFile);

        restObjectContainingFileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, objectContainingFileDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(objectContainingFileDTO))
            )
            .andExpect(status().isOk());

        // Validate the ObjectContainingFile in the database
        List<ObjectContainingFile> objectContainingFileList = objectContainingFileRepository.findAll();
        assertThat(objectContainingFileList).hasSize(databaseSizeBeforeUpdate);
        ObjectContainingFile testObjectContainingFile = objectContainingFileList.get(objectContainingFileList.size() - 1);
        assertThat(testObjectContainingFile.getFile()).isEqualTo(UPDATED_FILE);
        assertThat(testObjectContainingFile.getFileContentType()).isEqualTo(UPDATED_FILE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingObjectContainingFile() throws Exception {
        int databaseSizeBeforeUpdate = objectContainingFileRepository.findAll().size();
        objectContainingFile.setId(longCount.incrementAndGet());

        // Create the ObjectContainingFile
        ObjectContainingFileDTO objectContainingFileDTO = objectContainingFileMapper.toDto(objectContainingFile);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restObjectContainingFileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, objectContainingFileDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(objectContainingFileDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ObjectContainingFile in the database
        List<ObjectContainingFile> objectContainingFileList = objectContainingFileRepository.findAll();
        assertThat(objectContainingFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchObjectContainingFile() throws Exception {
        int databaseSizeBeforeUpdate = objectContainingFileRepository.findAll().size();
        objectContainingFile.setId(longCount.incrementAndGet());

        // Create the ObjectContainingFile
        ObjectContainingFileDTO objectContainingFileDTO = objectContainingFileMapper.toDto(objectContainingFile);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restObjectContainingFileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(objectContainingFileDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ObjectContainingFile in the database
        List<ObjectContainingFile> objectContainingFileList = objectContainingFileRepository.findAll();
        assertThat(objectContainingFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamObjectContainingFile() throws Exception {
        int databaseSizeBeforeUpdate = objectContainingFileRepository.findAll().size();
        objectContainingFile.setId(longCount.incrementAndGet());

        // Create the ObjectContainingFile
        ObjectContainingFileDTO objectContainingFileDTO = objectContainingFileMapper.toDto(objectContainingFile);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restObjectContainingFileMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(objectContainingFileDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ObjectContainingFile in the database
        List<ObjectContainingFile> objectContainingFileList = objectContainingFileRepository.findAll();
        assertThat(objectContainingFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateObjectContainingFileWithPatch() throws Exception {
        // Initialize the database
        objectContainingFileRepository.saveAndFlush(objectContainingFile);

        int databaseSizeBeforeUpdate = objectContainingFileRepository.findAll().size();

        // Update the objectContainingFile using partial update
        ObjectContainingFile partialUpdatedObjectContainingFile = new ObjectContainingFile();
        partialUpdatedObjectContainingFile.setId(objectContainingFile.getId());

        restObjectContainingFileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedObjectContainingFile.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedObjectContainingFile))
            )
            .andExpect(status().isOk());

        // Validate the ObjectContainingFile in the database
        List<ObjectContainingFile> objectContainingFileList = objectContainingFileRepository.findAll();
        assertThat(objectContainingFileList).hasSize(databaseSizeBeforeUpdate);
        ObjectContainingFile testObjectContainingFile = objectContainingFileList.get(objectContainingFileList.size() - 1);
        assertThat(testObjectContainingFile.getFile()).isEqualTo(DEFAULT_FILE);
        assertThat(testObjectContainingFile.getFileContentType()).isEqualTo(DEFAULT_FILE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateObjectContainingFileWithPatch() throws Exception {
        // Initialize the database
        objectContainingFileRepository.saveAndFlush(objectContainingFile);

        int databaseSizeBeforeUpdate = objectContainingFileRepository.findAll().size();

        // Update the objectContainingFile using partial update
        ObjectContainingFile partialUpdatedObjectContainingFile = new ObjectContainingFile();
        partialUpdatedObjectContainingFile.setId(objectContainingFile.getId());

        partialUpdatedObjectContainingFile.file(UPDATED_FILE).fileContentType(UPDATED_FILE_CONTENT_TYPE);

        restObjectContainingFileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedObjectContainingFile.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedObjectContainingFile))
            )
            .andExpect(status().isOk());

        // Validate the ObjectContainingFile in the database
        List<ObjectContainingFile> objectContainingFileList = objectContainingFileRepository.findAll();
        assertThat(objectContainingFileList).hasSize(databaseSizeBeforeUpdate);
        ObjectContainingFile testObjectContainingFile = objectContainingFileList.get(objectContainingFileList.size() - 1);
        assertThat(testObjectContainingFile.getFile()).isEqualTo(UPDATED_FILE);
        assertThat(testObjectContainingFile.getFileContentType()).isEqualTo(UPDATED_FILE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingObjectContainingFile() throws Exception {
        int databaseSizeBeforeUpdate = objectContainingFileRepository.findAll().size();
        objectContainingFile.setId(longCount.incrementAndGet());

        // Create the ObjectContainingFile
        ObjectContainingFileDTO objectContainingFileDTO = objectContainingFileMapper.toDto(objectContainingFile);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restObjectContainingFileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, objectContainingFileDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(objectContainingFileDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ObjectContainingFile in the database
        List<ObjectContainingFile> objectContainingFileList = objectContainingFileRepository.findAll();
        assertThat(objectContainingFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchObjectContainingFile() throws Exception {
        int databaseSizeBeforeUpdate = objectContainingFileRepository.findAll().size();
        objectContainingFile.setId(longCount.incrementAndGet());

        // Create the ObjectContainingFile
        ObjectContainingFileDTO objectContainingFileDTO = objectContainingFileMapper.toDto(objectContainingFile);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restObjectContainingFileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(objectContainingFileDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ObjectContainingFile in the database
        List<ObjectContainingFile> objectContainingFileList = objectContainingFileRepository.findAll();
        assertThat(objectContainingFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamObjectContainingFile() throws Exception {
        int databaseSizeBeforeUpdate = objectContainingFileRepository.findAll().size();
        objectContainingFile.setId(longCount.incrementAndGet());

        // Create the ObjectContainingFile
        ObjectContainingFileDTO objectContainingFileDTO = objectContainingFileMapper.toDto(objectContainingFile);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restObjectContainingFileMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(objectContainingFileDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ObjectContainingFile in the database
        List<ObjectContainingFile> objectContainingFileList = objectContainingFileRepository.findAll();
        assertThat(objectContainingFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteObjectContainingFile() throws Exception {
        // Initialize the database
        objectContainingFileRepository.saveAndFlush(objectContainingFile);

        int databaseSizeBeforeDelete = objectContainingFileRepository.findAll().size();

        // Delete the objectContainingFile
        restObjectContainingFileMockMvc
            .perform(delete(ENTITY_API_URL_ID, objectContainingFile.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ObjectContainingFile> objectContainingFileList = objectContainingFileRepository.findAll();
        assertThat(objectContainingFileList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
