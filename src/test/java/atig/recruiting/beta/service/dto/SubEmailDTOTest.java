package atig.recruiting.beta.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import atig.recruiting.beta.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SubEmailDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubEmailDTO.class);
        SubEmailDTO subEmailDTO1 = new SubEmailDTO();
        subEmailDTO1.setId(1L);
        SubEmailDTO subEmailDTO2 = new SubEmailDTO();
        assertThat(subEmailDTO1).isNotEqualTo(subEmailDTO2);
        subEmailDTO2.setId(subEmailDTO1.getId());
        assertThat(subEmailDTO1).isEqualTo(subEmailDTO2);
        subEmailDTO2.setId(2L);
        assertThat(subEmailDTO1).isNotEqualTo(subEmailDTO2);
        subEmailDTO1.setId(null);
        assertThat(subEmailDTO1).isNotEqualTo(subEmailDTO2);
    }
}
