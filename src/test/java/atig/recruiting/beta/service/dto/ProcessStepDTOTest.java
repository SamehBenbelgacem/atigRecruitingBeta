package atig.recruiting.beta.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import atig.recruiting.beta.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProcessStepDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProcessStepDTO.class);
        ProcessStepDTO processStepDTO1 = new ProcessStepDTO();
        processStepDTO1.setId(1L);
        ProcessStepDTO processStepDTO2 = new ProcessStepDTO();
        assertThat(processStepDTO1).isNotEqualTo(processStepDTO2);
        processStepDTO2.setId(processStepDTO1.getId());
        assertThat(processStepDTO1).isEqualTo(processStepDTO2);
        processStepDTO2.setId(2L);
        assertThat(processStepDTO1).isNotEqualTo(processStepDTO2);
        processStepDTO1.setId(null);
        assertThat(processStepDTO1).isNotEqualTo(processStepDTO2);
    }
}
