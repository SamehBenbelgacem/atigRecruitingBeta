package atig.recruiting.beta.domain;

import static atig.recruiting.beta.domain.CandidateTestSamples.*;
import static atig.recruiting.beta.domain.LanguageTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import atig.recruiting.beta.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LanguageTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Language.class);
        Language language1 = getLanguageSample1();
        Language language2 = new Language();
        assertThat(language1).isNotEqualTo(language2);

        language2.setId(language1.getId());
        assertThat(language1).isEqualTo(language2);

        language2 = getLanguageSample2();
        assertThat(language1).isNotEqualTo(language2);
    }

    @Test
    void languageCandidateTest() throws Exception {
        Language language = getLanguageRandomSampleGenerator();
        Candidate candidateBack = getCandidateRandomSampleGenerator();

        language.setLanguageCandidate(candidateBack);
        assertThat(language.getLanguageCandidate()).isEqualTo(candidateBack);

        language.languageCandidate(null);
        assertThat(language.getLanguageCandidate()).isNull();
    }

    @Test
    void candidateTest() throws Exception {
        Language language = getLanguageRandomSampleGenerator();
        Candidate candidateBack = getCandidateRandomSampleGenerator();

        language.setCandidate(candidateBack);
        assertThat(language.getCandidate()).isEqualTo(candidateBack);

        language.candidate(null);
        assertThat(language.getCandidate()).isNull();
    }
}
