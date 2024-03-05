package atig.recruiting.beta.domain;

import static atig.recruiting.beta.domain.CandidateTestSamples.*;
import static atig.recruiting.beta.domain.SkillTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import atig.recruiting.beta.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SkillTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Skill.class);
        Skill skill1 = getSkillSample1();
        Skill skill2 = new Skill();
        assertThat(skill1).isNotEqualTo(skill2);

        skill2.setId(skill1.getId());
        assertThat(skill1).isEqualTo(skill2);

        skill2 = getSkillSample2();
        assertThat(skill1).isNotEqualTo(skill2);
    }

    @Test
    void skillCandidateTest() throws Exception {
        Skill skill = getSkillRandomSampleGenerator();
        Candidate candidateBack = getCandidateRandomSampleGenerator();

        skill.setSkillCandidate(candidateBack);
        assertThat(skill.getSkillCandidate()).isEqualTo(candidateBack);

        skill.skillCandidate(null);
        assertThat(skill.getSkillCandidate()).isNull();
    }

    @Test
    void candidateTest() throws Exception {
        Skill skill = getSkillRandomSampleGenerator();
        Candidate candidateBack = getCandidateRandomSampleGenerator();

        skill.setCandidate(candidateBack);
        assertThat(skill.getCandidate()).isEqualTo(candidateBack);

        skill.candidate(null);
        assertThat(skill.getCandidate()).isNull();
    }
}
