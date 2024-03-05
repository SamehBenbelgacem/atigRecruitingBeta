package atig.recruiting.beta.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import atig.recruiting.beta.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CandidateAdditionalInfosDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CandidateAdditionalInfosDTO.class);
        CandidateAdditionalInfosDTO candidateAdditionalInfosDTO1 = new CandidateAdditionalInfosDTO();
        candidateAdditionalInfosDTO1.setId(1L);
        CandidateAdditionalInfosDTO candidateAdditionalInfosDTO2 = new CandidateAdditionalInfosDTO();
        assertThat(candidateAdditionalInfosDTO1).isNotEqualTo(candidateAdditionalInfosDTO2);
        candidateAdditionalInfosDTO2.setId(candidateAdditionalInfosDTO1.getId());
        assertThat(candidateAdditionalInfosDTO1).isEqualTo(candidateAdditionalInfosDTO2);
        candidateAdditionalInfosDTO2.setId(2L);
        assertThat(candidateAdditionalInfosDTO1).isNotEqualTo(candidateAdditionalInfosDTO2);
        candidateAdditionalInfosDTO1.setId(null);
        assertThat(candidateAdditionalInfosDTO1).isNotEqualTo(candidateAdditionalInfosDTO2);
    }
}
