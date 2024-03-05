package atig.recruiting.beta.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import atig.recruiting.beta.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DesiderDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DesiderDTO.class);
        DesiderDTO desiderDTO1 = new DesiderDTO();
        desiderDTO1.setId(1L);
        DesiderDTO desiderDTO2 = new DesiderDTO();
        assertThat(desiderDTO1).isNotEqualTo(desiderDTO2);
        desiderDTO2.setId(desiderDTO1.getId());
        assertThat(desiderDTO1).isEqualTo(desiderDTO2);
        desiderDTO2.setId(2L);
        assertThat(desiderDTO1).isNotEqualTo(desiderDTO2);
        desiderDTO1.setId(null);
        assertThat(desiderDTO1).isNotEqualTo(desiderDTO2);
    }
}
