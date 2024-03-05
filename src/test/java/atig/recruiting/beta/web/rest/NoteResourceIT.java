package atig.recruiting.beta.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import atig.recruiting.beta.IntegrationTest;
import atig.recruiting.beta.domain.Candidate;
import atig.recruiting.beta.domain.Company;
import atig.recruiting.beta.domain.Note;
import atig.recruiting.beta.domain.ObjectContainingFile;
import atig.recruiting.beta.repository.NoteRepository;
import atig.recruiting.beta.service.dto.NoteDTO;
import atig.recruiting.beta.service.mapper.NoteMapper;
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
 * Integration tests for the {@link NoteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NoteResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/notes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private NoteMapper noteMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNoteMockMvc;

    private Note note;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Note createEntity(EntityManager em) {
        Note note = new Note().title(DEFAULT_TITLE).date(DEFAULT_DATE).description(DEFAULT_DESCRIPTION);
        return note;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Note createUpdatedEntity(EntityManager em) {
        Note note = new Note().title(UPDATED_TITLE).date(UPDATED_DATE).description(UPDATED_DESCRIPTION);
        return note;
    }

    @BeforeEach
    public void initTest() {
        note = createEntity(em);
    }

    @Test
    @Transactional
    void createNote() throws Exception {
        int databaseSizeBeforeCreate = noteRepository.findAll().size();
        // Create the Note
        NoteDTO noteDTO = noteMapper.toDto(note);
        restNoteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(noteDTO)))
            .andExpect(status().isCreated());

        // Validate the Note in the database
        List<Note> noteList = noteRepository.findAll();
        assertThat(noteList).hasSize(databaseSizeBeforeCreate + 1);
        Note testNote = noteList.get(noteList.size() - 1);
        assertThat(testNote.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testNote.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testNote.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createNoteWithExistingId() throws Exception {
        // Create the Note with an existing ID
        note.setId(1L);
        NoteDTO noteDTO = noteMapper.toDto(note);

        int databaseSizeBeforeCreate = noteRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNoteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(noteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Note in the database
        List<Note> noteList = noteRepository.findAll();
        assertThat(noteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllNotes() throws Exception {
        // Initialize the database
        noteRepository.saveAndFlush(note);

        // Get all the noteList
        restNoteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(note.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getNote() throws Exception {
        // Initialize the database
        noteRepository.saveAndFlush(note);

        // Get the note
        restNoteMockMvc
            .perform(get(ENTITY_API_URL_ID, note.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(note.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNotesByIdFiltering() throws Exception {
        // Initialize the database
        noteRepository.saveAndFlush(note);

        Long id = note.getId();

        defaultNoteShouldBeFound("id.equals=" + id);
        defaultNoteShouldNotBeFound("id.notEquals=" + id);

        defaultNoteShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultNoteShouldNotBeFound("id.greaterThan=" + id);

        defaultNoteShouldBeFound("id.lessThanOrEqual=" + id);
        defaultNoteShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllNotesByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        noteRepository.saveAndFlush(note);

        // Get all the noteList where title equals to DEFAULT_TITLE
        defaultNoteShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the noteList where title equals to UPDATED_TITLE
        defaultNoteShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllNotesByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        noteRepository.saveAndFlush(note);

        // Get all the noteList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultNoteShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the noteList where title equals to UPDATED_TITLE
        defaultNoteShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllNotesByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        noteRepository.saveAndFlush(note);

        // Get all the noteList where title is not null
        defaultNoteShouldBeFound("title.specified=true");

        // Get all the noteList where title is null
        defaultNoteShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    void getAllNotesByTitleContainsSomething() throws Exception {
        // Initialize the database
        noteRepository.saveAndFlush(note);

        // Get all the noteList where title contains DEFAULT_TITLE
        defaultNoteShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the noteList where title contains UPDATED_TITLE
        defaultNoteShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllNotesByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        noteRepository.saveAndFlush(note);

        // Get all the noteList where title does not contain DEFAULT_TITLE
        defaultNoteShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the noteList where title does not contain UPDATED_TITLE
        defaultNoteShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllNotesByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        noteRepository.saveAndFlush(note);

        // Get all the noteList where date equals to DEFAULT_DATE
        defaultNoteShouldBeFound("date.equals=" + DEFAULT_DATE);

        // Get all the noteList where date equals to UPDATED_DATE
        defaultNoteShouldNotBeFound("date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllNotesByDateIsInShouldWork() throws Exception {
        // Initialize the database
        noteRepository.saveAndFlush(note);

        // Get all the noteList where date in DEFAULT_DATE or UPDATED_DATE
        defaultNoteShouldBeFound("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE);

        // Get all the noteList where date equals to UPDATED_DATE
        defaultNoteShouldNotBeFound("date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllNotesByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        noteRepository.saveAndFlush(note);

        // Get all the noteList where date is not null
        defaultNoteShouldBeFound("date.specified=true");

        // Get all the noteList where date is null
        defaultNoteShouldNotBeFound("date.specified=false");
    }

    @Test
    @Transactional
    void getAllNotesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        noteRepository.saveAndFlush(note);

        // Get all the noteList where description equals to DEFAULT_DESCRIPTION
        defaultNoteShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the noteList where description equals to UPDATED_DESCRIPTION
        defaultNoteShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllNotesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        noteRepository.saveAndFlush(note);

        // Get all the noteList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultNoteShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the noteList where description equals to UPDATED_DESCRIPTION
        defaultNoteShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllNotesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        noteRepository.saveAndFlush(note);

        // Get all the noteList where description is not null
        defaultNoteShouldBeFound("description.specified=true");

        // Get all the noteList where description is null
        defaultNoteShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllNotesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        noteRepository.saveAndFlush(note);

        // Get all the noteList where description contains DEFAULT_DESCRIPTION
        defaultNoteShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the noteList where description contains UPDATED_DESCRIPTION
        defaultNoteShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllNotesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        noteRepository.saveAndFlush(note);

        // Get all the noteList where description does not contain DEFAULT_DESCRIPTION
        defaultNoteShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the noteList where description does not contain UPDATED_DESCRIPTION
        defaultNoteShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllNotesByDocumentsIsEqualToSomething() throws Exception {
        ObjectContainingFile documents;
        if (TestUtil.findAll(em, ObjectContainingFile.class).isEmpty()) {
            noteRepository.saveAndFlush(note);
            documents = ObjectContainingFileResourceIT.createEntity(em);
        } else {
            documents = TestUtil.findAll(em, ObjectContainingFile.class).get(0);
        }
        em.persist(documents);
        em.flush();
        note.addDocuments(documents);
        noteRepository.saveAndFlush(note);
        Long documentsId = documents.getId();
        // Get all the noteList where documents equals to documentsId
        defaultNoteShouldBeFound("documentsId.equals=" + documentsId);

        // Get all the noteList where documents equals to (documentsId + 1)
        defaultNoteShouldNotBeFound("documentsId.equals=" + (documentsId + 1));
    }

    @Test
    @Transactional
    void getAllNotesByNoteCompanyIsEqualToSomething() throws Exception {
        Company noteCompany;
        if (TestUtil.findAll(em, Company.class).isEmpty()) {
            noteRepository.saveAndFlush(note);
            noteCompany = CompanyResourceIT.createEntity(em);
        } else {
            noteCompany = TestUtil.findAll(em, Company.class).get(0);
        }
        em.persist(noteCompany);
        em.flush();
        note.setNoteCompany(noteCompany);
        noteRepository.saveAndFlush(note);
        Long noteCompanyId = noteCompany.getId();
        // Get all the noteList where noteCompany equals to noteCompanyId
        defaultNoteShouldBeFound("noteCompanyId.equals=" + noteCompanyId);

        // Get all the noteList where noteCompany equals to (noteCompanyId + 1)
        defaultNoteShouldNotBeFound("noteCompanyId.equals=" + (noteCompanyId + 1));
    }

    @Test
    @Transactional
    void getAllNotesByNoteCandidateIsEqualToSomething() throws Exception {
        Candidate noteCandidate;
        if (TestUtil.findAll(em, Candidate.class).isEmpty()) {
            noteRepository.saveAndFlush(note);
            noteCandidate = CandidateResourceIT.createEntity(em);
        } else {
            noteCandidate = TestUtil.findAll(em, Candidate.class).get(0);
        }
        em.persist(noteCandidate);
        em.flush();
        note.setNoteCandidate(noteCandidate);
        noteRepository.saveAndFlush(note);
        Long noteCandidateId = noteCandidate.getId();
        // Get all the noteList where noteCandidate equals to noteCandidateId
        defaultNoteShouldBeFound("noteCandidateId.equals=" + noteCandidateId);

        // Get all the noteList where noteCandidate equals to (noteCandidateId + 1)
        defaultNoteShouldNotBeFound("noteCandidateId.equals=" + (noteCandidateId + 1));
    }

    @Test
    @Transactional
    void getAllNotesByCandidateIsEqualToSomething() throws Exception {
        Candidate candidate;
        if (TestUtil.findAll(em, Candidate.class).isEmpty()) {
            noteRepository.saveAndFlush(note);
            candidate = CandidateResourceIT.createEntity(em);
        } else {
            candidate = TestUtil.findAll(em, Candidate.class).get(0);
        }
        em.persist(candidate);
        em.flush();
        note.setCandidate(candidate);
        noteRepository.saveAndFlush(note);
        Long candidateId = candidate.getId();
        // Get all the noteList where candidate equals to candidateId
        defaultNoteShouldBeFound("candidateId.equals=" + candidateId);

        // Get all the noteList where candidate equals to (candidateId + 1)
        defaultNoteShouldNotBeFound("candidateId.equals=" + (candidateId + 1));
    }

    @Test
    @Transactional
    void getAllNotesByCompanyIsEqualToSomething() throws Exception {
        Company company;
        if (TestUtil.findAll(em, Company.class).isEmpty()) {
            noteRepository.saveAndFlush(note);
            company = CompanyResourceIT.createEntity(em);
        } else {
            company = TestUtil.findAll(em, Company.class).get(0);
        }
        em.persist(company);
        em.flush();
        note.setCompany(company);
        noteRepository.saveAndFlush(note);
        Long companyId = company.getId();
        // Get all the noteList where company equals to companyId
        defaultNoteShouldBeFound("companyId.equals=" + companyId);

        // Get all the noteList where company equals to (companyId + 1)
        defaultNoteShouldNotBeFound("companyId.equals=" + (companyId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultNoteShouldBeFound(String filter) throws Exception {
        restNoteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(note.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restNoteMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultNoteShouldNotBeFound(String filter) throws Exception {
        restNoteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNoteMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingNote() throws Exception {
        // Get the note
        restNoteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNote() throws Exception {
        // Initialize the database
        noteRepository.saveAndFlush(note);

        int databaseSizeBeforeUpdate = noteRepository.findAll().size();

        // Update the note
        Note updatedNote = noteRepository.findById(note.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedNote are not directly saved in db
        em.detach(updatedNote);
        updatedNote.title(UPDATED_TITLE).date(UPDATED_DATE).description(UPDATED_DESCRIPTION);
        NoteDTO noteDTO = noteMapper.toDto(updatedNote);

        restNoteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, noteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(noteDTO))
            )
            .andExpect(status().isOk());

        // Validate the Note in the database
        List<Note> noteList = noteRepository.findAll();
        assertThat(noteList).hasSize(databaseSizeBeforeUpdate);
        Note testNote = noteList.get(noteList.size() - 1);
        assertThat(testNote.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testNote.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testNote.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingNote() throws Exception {
        int databaseSizeBeforeUpdate = noteRepository.findAll().size();
        note.setId(longCount.incrementAndGet());

        // Create the Note
        NoteDTO noteDTO = noteMapper.toDto(note);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNoteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, noteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(noteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Note in the database
        List<Note> noteList = noteRepository.findAll();
        assertThat(noteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNote() throws Exception {
        int databaseSizeBeforeUpdate = noteRepository.findAll().size();
        note.setId(longCount.incrementAndGet());

        // Create the Note
        NoteDTO noteDTO = noteMapper.toDto(note);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNoteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(noteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Note in the database
        List<Note> noteList = noteRepository.findAll();
        assertThat(noteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNote() throws Exception {
        int databaseSizeBeforeUpdate = noteRepository.findAll().size();
        note.setId(longCount.incrementAndGet());

        // Create the Note
        NoteDTO noteDTO = noteMapper.toDto(note);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNoteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(noteDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Note in the database
        List<Note> noteList = noteRepository.findAll();
        assertThat(noteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNoteWithPatch() throws Exception {
        // Initialize the database
        noteRepository.saveAndFlush(note);

        int databaseSizeBeforeUpdate = noteRepository.findAll().size();

        // Update the note using partial update
        Note partialUpdatedNote = new Note();
        partialUpdatedNote.setId(note.getId());

        partialUpdatedNote.title(UPDATED_TITLE).description(UPDATED_DESCRIPTION);

        restNoteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNote.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNote))
            )
            .andExpect(status().isOk());

        // Validate the Note in the database
        List<Note> noteList = noteRepository.findAll();
        assertThat(noteList).hasSize(databaseSizeBeforeUpdate);
        Note testNote = noteList.get(noteList.size() - 1);
        assertThat(testNote.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testNote.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testNote.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateNoteWithPatch() throws Exception {
        // Initialize the database
        noteRepository.saveAndFlush(note);

        int databaseSizeBeforeUpdate = noteRepository.findAll().size();

        // Update the note using partial update
        Note partialUpdatedNote = new Note();
        partialUpdatedNote.setId(note.getId());

        partialUpdatedNote.title(UPDATED_TITLE).date(UPDATED_DATE).description(UPDATED_DESCRIPTION);

        restNoteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNote.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNote))
            )
            .andExpect(status().isOk());

        // Validate the Note in the database
        List<Note> noteList = noteRepository.findAll();
        assertThat(noteList).hasSize(databaseSizeBeforeUpdate);
        Note testNote = noteList.get(noteList.size() - 1);
        assertThat(testNote.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testNote.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testNote.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingNote() throws Exception {
        int databaseSizeBeforeUpdate = noteRepository.findAll().size();
        note.setId(longCount.incrementAndGet());

        // Create the Note
        NoteDTO noteDTO = noteMapper.toDto(note);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNoteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, noteDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(noteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Note in the database
        List<Note> noteList = noteRepository.findAll();
        assertThat(noteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNote() throws Exception {
        int databaseSizeBeforeUpdate = noteRepository.findAll().size();
        note.setId(longCount.incrementAndGet());

        // Create the Note
        NoteDTO noteDTO = noteMapper.toDto(note);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNoteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(noteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Note in the database
        List<Note> noteList = noteRepository.findAll();
        assertThat(noteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNote() throws Exception {
        int databaseSizeBeforeUpdate = noteRepository.findAll().size();
        note.setId(longCount.incrementAndGet());

        // Create the Note
        NoteDTO noteDTO = noteMapper.toDto(note);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNoteMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(noteDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Note in the database
        List<Note> noteList = noteRepository.findAll();
        assertThat(noteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNote() throws Exception {
        // Initialize the database
        noteRepository.saveAndFlush(note);

        int databaseSizeBeforeDelete = noteRepository.findAll().size();

        // Delete the note
        restNoteMockMvc
            .perform(delete(ENTITY_API_URL_ID, note.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Note> noteList = noteRepository.findAll();
        assertThat(noteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
