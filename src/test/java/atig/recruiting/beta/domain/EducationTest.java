package atig.recruiting.beta.domain;

import static atig.recruiting.beta.domain.CandidateTestSamples.*;
import static atig.recruiting.beta.domain.EducationTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import atig.recruiting.beta.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EducationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Education.class);
        Education education1 = getEducationSample1();
        Education education2 = new Education();
        assertThat(education1).isNotEqualTo(education2);

        education2.setId(education1.getId());
        assertThat(education1).isEqualTo(education2);

        education2 = getEducationSample2();
        assertThat(education1).isNotEqualTo(education2);
    }

    @Test
    void educationCandidateTest() throws Exception {
        Education education = getEducationRandomSampleGenerator();
        Candidate candidateBack = getCandidateRandomSampleGenerator();

        education.setEducationCandidate(candidateBack);
        assertThat(education.getEducationCandidate()).isEqualTo(candidateBack);

        education.educationCandidate(null);
        assertThat(education.getEducationCandidate()).isNull();
    }

    @Test
    void candidateTest() throws Exception {
        Education education = getEducationRandomSampleGenerator();
        Candidate candidateBack = getCandidateRandomSampleGenerator();

        education.setCandidate(candidateBack);
        assertThat(education.getCandidate()).isEqualTo(candidateBack);

        education.candidate(null);
        assertThat(education.getCandidate()).isNull();
    }
}
