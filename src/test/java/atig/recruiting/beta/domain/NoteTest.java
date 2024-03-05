package atig.recruiting.beta.domain;

import static atig.recruiting.beta.domain.CandidateTestSamples.*;
import static atig.recruiting.beta.domain.CompanyTestSamples.*;
import static atig.recruiting.beta.domain.NoteTestSamples.*;
import static atig.recruiting.beta.domain.ObjectContainingFileTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import atig.recruiting.beta.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class NoteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Note.class);
        Note note1 = getNoteSample1();
        Note note2 = new Note();
        assertThat(note1).isNotEqualTo(note2);

        note2.setId(note1.getId());
        assertThat(note1).isEqualTo(note2);

        note2 = getNoteSample2();
        assertThat(note1).isNotEqualTo(note2);
    }

    @Test
    void documentsTest() throws Exception {
        Note note = getNoteRandomSampleGenerator();
        ObjectContainingFile objectContainingFileBack = getObjectContainingFileRandomSampleGenerator();

        note.addDocuments(objectContainingFileBack);
        assertThat(note.getDocuments()).containsOnly(objectContainingFileBack);
        assertThat(objectContainingFileBack.getNote()).isEqualTo(note);

        note.removeDocuments(objectContainingFileBack);
        assertThat(note.getDocuments()).doesNotContain(objectContainingFileBack);
        assertThat(objectContainingFileBack.getNote()).isNull();

        note.documents(new HashSet<>(Set.of(objectContainingFileBack)));
        assertThat(note.getDocuments()).containsOnly(objectContainingFileBack);
        assertThat(objectContainingFileBack.getNote()).isEqualTo(note);

        note.setDocuments(new HashSet<>());
        assertThat(note.getDocuments()).doesNotContain(objectContainingFileBack);
        assertThat(objectContainingFileBack.getNote()).isNull();
    }

    @Test
    void noteCompanyTest() throws Exception {
        Note note = getNoteRandomSampleGenerator();
        Company companyBack = getCompanyRandomSampleGenerator();

        note.setNoteCompany(companyBack);
        assertThat(note.getNoteCompany()).isEqualTo(companyBack);

        note.noteCompany(null);
        assertThat(note.getNoteCompany()).isNull();
    }

    @Test
    void noteCandidateTest() throws Exception {
        Note note = getNoteRandomSampleGenerator();
        Candidate candidateBack = getCandidateRandomSampleGenerator();

        note.setNoteCandidate(candidateBack);
        assertThat(note.getNoteCandidate()).isEqualTo(candidateBack);

        note.noteCandidate(null);
        assertThat(note.getNoteCandidate()).isNull();
    }

    @Test
    void candidateTest() throws Exception {
        Note note = getNoteRandomSampleGenerator();
        Candidate candidateBack = getCandidateRandomSampleGenerator();

        note.setCandidate(candidateBack);
        assertThat(note.getCandidate()).isEqualTo(candidateBack);

        note.candidate(null);
        assertThat(note.getCandidate()).isNull();
    }

    @Test
    void companyTest() throws Exception {
        Note note = getNoteRandomSampleGenerator();
        Company companyBack = getCompanyRandomSampleGenerator();

        note.setCompany(companyBack);
        assertThat(note.getCompany()).isEqualTo(companyBack);

        note.company(null);
        assertThat(note.getCompany()).isNull();
    }
}
