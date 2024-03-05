package atig.recruiting.beta.domain;

import static atig.recruiting.beta.domain.CandidateAdditionalInfosTestSamples.*;
import static atig.recruiting.beta.domain.EmailTestSamples.*;
import static atig.recruiting.beta.domain.NoteTestSamples.*;
import static atig.recruiting.beta.domain.ObjectContainingFileTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import atig.recruiting.beta.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ObjectContainingFileTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ObjectContainingFile.class);
        ObjectContainingFile objectContainingFile1 = getObjectContainingFileSample1();
        ObjectContainingFile objectContainingFile2 = new ObjectContainingFile();
        assertThat(objectContainingFile1).isNotEqualTo(objectContainingFile2);

        objectContainingFile2.setId(objectContainingFile1.getId());
        assertThat(objectContainingFile1).isEqualTo(objectContainingFile2);

        objectContainingFile2 = getObjectContainingFileSample2();
        assertThat(objectContainingFile1).isNotEqualTo(objectContainingFile2);
    }

    @Test
    void candidateDocsTest() throws Exception {
        ObjectContainingFile objectContainingFile = getObjectContainingFileRandomSampleGenerator();
        CandidateAdditionalInfos candidateAdditionalInfosBack = getCandidateAdditionalInfosRandomSampleGenerator();

        objectContainingFile.setCandidateDocs(candidateAdditionalInfosBack);
        assertThat(objectContainingFile.getCandidateDocs()).isEqualTo(candidateAdditionalInfosBack);

        objectContainingFile.candidateDocs(null);
        assertThat(objectContainingFile.getCandidateDocs()).isNull();
    }

    @Test
    void noteDocsTest() throws Exception {
        ObjectContainingFile objectContainingFile = getObjectContainingFileRandomSampleGenerator();
        Note noteBack = getNoteRandomSampleGenerator();

        objectContainingFile.setNoteDocs(noteBack);
        assertThat(objectContainingFile.getNoteDocs()).isEqualTo(noteBack);

        objectContainingFile.noteDocs(null);
        assertThat(objectContainingFile.getNoteDocs()).isNull();
    }

    @Test
    void emailDocsTest() throws Exception {
        ObjectContainingFile objectContainingFile = getObjectContainingFileRandomSampleGenerator();
        Email emailBack = getEmailRandomSampleGenerator();

        objectContainingFile.setEmailDocs(emailBack);
        assertThat(objectContainingFile.getEmailDocs()).isEqualTo(emailBack);

        objectContainingFile.emailDocs(null);
        assertThat(objectContainingFile.getEmailDocs()).isNull();
    }

    @Test
    void candidateAdditionalInfosTest() throws Exception {
        ObjectContainingFile objectContainingFile = getObjectContainingFileRandomSampleGenerator();
        CandidateAdditionalInfos candidateAdditionalInfosBack = getCandidateAdditionalInfosRandomSampleGenerator();

        objectContainingFile.setCandidateAdditionalInfos(candidateAdditionalInfosBack);
        assertThat(objectContainingFile.getCandidateAdditionalInfos()).isEqualTo(candidateAdditionalInfosBack);

        objectContainingFile.candidateAdditionalInfos(null);
        assertThat(objectContainingFile.getCandidateAdditionalInfos()).isNull();
    }

    @Test
    void noteTest() throws Exception {
        ObjectContainingFile objectContainingFile = getObjectContainingFileRandomSampleGenerator();
        Note noteBack = getNoteRandomSampleGenerator();

        objectContainingFile.setNote(noteBack);
        assertThat(objectContainingFile.getNote()).isEqualTo(noteBack);

        objectContainingFile.note(null);
        assertThat(objectContainingFile.getNote()).isNull();
    }

    @Test
    void emailTest() throws Exception {
        ObjectContainingFile objectContainingFile = getObjectContainingFileRandomSampleGenerator();
        Email emailBack = getEmailRandomSampleGenerator();

        objectContainingFile.setEmail(emailBack);
        assertThat(objectContainingFile.getEmail()).isEqualTo(emailBack);

        objectContainingFile.email(null);
        assertThat(objectContainingFile.getEmail()).isNull();
    }
}
