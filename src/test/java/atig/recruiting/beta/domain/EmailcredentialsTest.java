package atig.recruiting.beta.domain;

import static atig.recruiting.beta.domain.EmailTestSamples.*;
import static atig.recruiting.beta.domain.EmailcredentialsTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import atig.recruiting.beta.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class EmailcredentialsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Emailcredentials.class);
        Emailcredentials emailcredentials1 = getEmailcredentialsSample1();
        Emailcredentials emailcredentials2 = new Emailcredentials();
        assertThat(emailcredentials1).isNotEqualTo(emailcredentials2);

        emailcredentials2.setId(emailcredentials1.getId());
        assertThat(emailcredentials1).isEqualTo(emailcredentials2);

        emailcredentials2 = getEmailcredentialsSample2();
        assertThat(emailcredentials1).isNotEqualTo(emailcredentials2);
    }

    @Test
    void emailsTest() throws Exception {
        Emailcredentials emailcredentials = getEmailcredentialsRandomSampleGenerator();
        Email emailBack = getEmailRandomSampleGenerator();

        emailcredentials.addEmails(emailBack);
        assertThat(emailcredentials.getEmails()).containsOnly(emailBack);
        assertThat(emailBack.getEmailcredentials()).isEqualTo(emailcredentials);

        emailcredentials.removeEmails(emailBack);
        assertThat(emailcredentials.getEmails()).doesNotContain(emailBack);
        assertThat(emailBack.getEmailcredentials()).isNull();

        emailcredentials.emails(new HashSet<>(Set.of(emailBack)));
        assertThat(emailcredentials.getEmails()).containsOnly(emailBack);
        assertThat(emailBack.getEmailcredentials()).isEqualTo(emailcredentials);

        emailcredentials.setEmails(new HashSet<>());
        assertThat(emailcredentials.getEmails()).doesNotContain(emailBack);
        assertThat(emailBack.getEmailcredentials()).isNull();
    }
}
