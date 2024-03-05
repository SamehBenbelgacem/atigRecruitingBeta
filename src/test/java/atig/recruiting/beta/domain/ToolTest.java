package atig.recruiting.beta.domain;

import static atig.recruiting.beta.domain.ExperienceTestSamples.*;
import static atig.recruiting.beta.domain.ToolTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import atig.recruiting.beta.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ToolTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tool.class);
        Tool tool1 = getToolSample1();
        Tool tool2 = new Tool();
        assertThat(tool1).isNotEqualTo(tool2);

        tool2.setId(tool1.getId());
        assertThat(tool1).isEqualTo(tool2);

        tool2 = getToolSample2();
        assertThat(tool1).isNotEqualTo(tool2);
    }

    @Test
    void toolExperienceTest() throws Exception {
        Tool tool = getToolRandomSampleGenerator();
        Experience experienceBack = getExperienceRandomSampleGenerator();

        tool.setToolExperience(experienceBack);
        assertThat(tool.getToolExperience()).isEqualTo(experienceBack);

        tool.toolExperience(null);
        assertThat(tool.getToolExperience()).isNull();
    }

    @Test
    void experienceTest() throws Exception {
        Tool tool = getToolRandomSampleGenerator();
        Experience experienceBack = getExperienceRandomSampleGenerator();

        tool.setExperience(experienceBack);
        assertThat(tool.getExperience()).isEqualTo(experienceBack);

        tool.experience(null);
        assertThat(tool.getExperience()).isNull();
    }
}
