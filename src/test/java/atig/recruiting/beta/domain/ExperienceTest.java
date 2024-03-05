package atig.recruiting.beta.domain;

import static atig.recruiting.beta.domain.CandidateTestSamples.*;
import static atig.recruiting.beta.domain.ExperienceTestSamples.*;
import static atig.recruiting.beta.domain.ToolTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import atig.recruiting.beta.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ExperienceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Experience.class);
        Experience experience1 = getExperienceSample1();
        Experience experience2 = new Experience();
        assertThat(experience1).isNotEqualTo(experience2);

        experience2.setId(experience1.getId());
        assertThat(experience1).isEqualTo(experience2);

        experience2 = getExperienceSample2();
        assertThat(experience1).isNotEqualTo(experience2);
    }

    @Test
    void toolsTest() throws Exception {
        Experience experience = getExperienceRandomSampleGenerator();
        Tool toolBack = getToolRandomSampleGenerator();

        experience.addTools(toolBack);
        assertThat(experience.getTools()).containsOnly(toolBack);
        assertThat(toolBack.getExperience()).isEqualTo(experience);

        experience.removeTools(toolBack);
        assertThat(experience.getTools()).doesNotContain(toolBack);
        assertThat(toolBack.getExperience()).isNull();

        experience.tools(new HashSet<>(Set.of(toolBack)));
        assertThat(experience.getTools()).containsOnly(toolBack);
        assertThat(toolBack.getExperience()).isEqualTo(experience);

        experience.setTools(new HashSet<>());
        assertThat(experience.getTools()).doesNotContain(toolBack);
        assertThat(toolBack.getExperience()).isNull();
    }

    @Test
    void experienceCandidateTest() throws Exception {
        Experience experience = getExperienceRandomSampleGenerator();
        Candidate candidateBack = getCandidateRandomSampleGenerator();

        experience.setExperienceCandidate(candidateBack);
        assertThat(experience.getExperienceCandidate()).isEqualTo(candidateBack);

        experience.experienceCandidate(null);
        assertThat(experience.getExperienceCandidate()).isNull();
    }

    @Test
    void candidateTest() throws Exception {
        Experience experience = getExperienceRandomSampleGenerator();
        Candidate candidateBack = getCandidateRandomSampleGenerator();

        experience.setCandidate(candidateBack);
        assertThat(experience.getCandidate()).isEqualTo(candidateBack);

        experience.candidate(null);
        assertThat(experience.getCandidate()).isNull();
    }
}
