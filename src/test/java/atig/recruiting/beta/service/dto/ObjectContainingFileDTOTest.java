package atig.recruiting.beta.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import atig.recruiting.beta.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ObjectContainingFileDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ObjectContainingFileDTO.class);
        ObjectContainingFileDTO objectContainingFileDTO1 = new ObjectContainingFileDTO();
        objectContainingFileDTO1.setId(1L);
        ObjectContainingFileDTO objectContainingFileDTO2 = new ObjectContainingFileDTO();
        assertThat(objectContainingFileDTO1).isNotEqualTo(objectContainingFileDTO2);
        objectContainingFileDTO2.setId(objectContainingFileDTO1.getId());
        assertThat(objectContainingFileDTO1).isEqualTo(objectContainingFileDTO2);
        objectContainingFileDTO2.setId(2L);
        assertThat(objectContainingFileDTO1).isNotEqualTo(objectContainingFileDTO2);
        objectContainingFileDTO1.setId(null);
        assertThat(objectContainingFileDTO1).isNotEqualTo(objectContainingFileDTO2);
    }
}
