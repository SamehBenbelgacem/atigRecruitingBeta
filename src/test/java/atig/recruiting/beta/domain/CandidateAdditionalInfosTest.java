package atig.recruiting.beta.domain;

import static atig.recruiting.beta.domain.CandidateAdditionalInfosTestSamples.*;
import static atig.recruiting.beta.domain.CandidateTestSamples.*;
import static atig.recruiting.beta.domain.ObjectContainingFileTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import atig.recruiting.beta.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CandidateAdditionalInfosTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CandidateAdditionalInfos.class);
        CandidateAdditionalInfos candidateAdditionalInfos1 = getCandidateAdditionalInfosSample1();
        CandidateAdditionalInfos candidateAdditionalInfos2 = new CandidateAdditionalInfos();
        assertThat(candidateAdditionalInfos1).isNotEqualTo(candidateAdditionalInfos2);

        candidateAdditionalInfos2.setId(candidateAdditionalInfos1.getId());
        assertThat(candidateAdditionalInfos1).isEqualTo(candidateAdditionalInfos2);

        candidateAdditionalInfos2 = getCandidateAdditionalInfosSample2();
        assertThat(candidateAdditionalInfos1).isNotEqualTo(candidateAdditionalInfos2);
    }

    @Test
    void documentsTest() throws Exception {
        CandidateAdditionalInfos candidateAdditionalInfos = getCandidateAdditionalInfosRandomSampleGenerator();
        ObjectContainingFile objectContainingFileBack = getObjectContainingFileRandomSampleGenerator();

        candidateAdditionalInfos.addDocuments(objectContainingFileBack);
        assertThat(candidateAdditionalInfos.getDocuments()).containsOnly(objectContainingFileBack);
        assertThat(objectContainingFileBack.getCandidateAdditionalInfos()).isEqualTo(candidateAdditionalInfos);

        candidateAdditionalInfos.removeDocuments(objectContainingFileBack);
        assertThat(candidateAdditionalInfos.getDocuments()).doesNotContain(objectContainingFileBack);
        assertThat(objectContainingFileBack.getCandidateAdditionalInfos()).isNull();

        candidateAdditionalInfos.documents(new HashSet<>(Set.of(objectContainingFileBack)));
        assertThat(candidateAdditionalInfos.getDocuments()).containsOnly(objectContainingFileBack);
        assertThat(objectContainingFileBack.getCandidateAdditionalInfos()).isEqualTo(candidateAdditionalInfos);

        candidateAdditionalInfos.setDocuments(new HashSet<>());
        assertThat(candidateAdditionalInfos.getDocuments()).doesNotContain(objectContainingFileBack);
        assertThat(objectContainingFileBack.getCandidateAdditionalInfos()).isNull();
    }

    @Test
    void candidateTest() throws Exception {
        CandidateAdditionalInfos candidateAdditionalInfos = getCandidateAdditionalInfosRandomSampleGenerator();
        Candidate candidateBack = getCandidateRandomSampleGenerator();

        candidateAdditionalInfos.setCandidate(candidateBack);
        assertThat(candidateAdditionalInfos.getCandidate()).isEqualTo(candidateBack);
        assertThat(candidateBack.getAdditionalInfos()).isEqualTo(candidateAdditionalInfos);

        candidateAdditionalInfos.candidate(null);
        assertThat(candidateAdditionalInfos.getCandidate()).isNull();
        assertThat(candidateBack.getAdditionalInfos()).isNull();
    }
}
