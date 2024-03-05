package atig.recruiting.beta.domain;

import static atig.recruiting.beta.domain.CandidateTestSamples.*;
import static atig.recruiting.beta.domain.CertificationTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import atig.recruiting.beta.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CertificationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Certification.class);
        Certification certification1 = getCertificationSample1();
        Certification certification2 = new Certification();
        assertThat(certification1).isNotEqualTo(certification2);

        certification2.setId(certification1.getId());
        assertThat(certification1).isEqualTo(certification2);

        certification2 = getCertificationSample2();
        assertThat(certification1).isNotEqualTo(certification2);
    }

    @Test
    void certificationCandidateTest() throws Exception {
        Certification certification = getCertificationRandomSampleGenerator();
        Candidate candidateBack = getCandidateRandomSampleGenerator();

        certification.setCertificationCandidate(candidateBack);
        assertThat(certification.getCertificationCandidate()).isEqualTo(candidateBack);

        certification.certificationCandidate(null);
        assertThat(certification.getCertificationCandidate()).isNull();
    }

    @Test
    void candidateTest() throws Exception {
        Certification certification = getCertificationRandomSampleGenerator();
        Candidate candidateBack = getCandidateRandomSampleGenerator();

        certification.setCandidate(candidateBack);
        assertThat(certification.getCandidate()).isEqualTo(candidateBack);

        certification.candidate(null);
        assertThat(certification.getCandidate()).isNull();
    }
}
