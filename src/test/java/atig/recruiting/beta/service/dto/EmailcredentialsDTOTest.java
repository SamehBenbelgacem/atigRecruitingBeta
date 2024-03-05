package atig.recruiting.beta.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import atig.recruiting.beta.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EmailcredentialsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmailcredentialsDTO.class);
        EmailcredentialsDTO emailcredentialsDTO1 = new EmailcredentialsDTO();
        emailcredentialsDTO1.setId(1L);
        EmailcredentialsDTO emailcredentialsDTO2 = new EmailcredentialsDTO();
        assertThat(emailcredentialsDTO1).isNotEqualTo(emailcredentialsDTO2);
        emailcredentialsDTO2.setId(emailcredentialsDTO1.getId());
        assertThat(emailcredentialsDTO1).isEqualTo(emailcredentialsDTO2);
        emailcredentialsDTO2.setId(2L);
        assertThat(emailcredentialsDTO1).isNotEqualTo(emailcredentialsDTO2);
        emailcredentialsDTO1.setId(null);
        assertThat(emailcredentialsDTO1).isNotEqualTo(emailcredentialsDTO2);
    }
}
