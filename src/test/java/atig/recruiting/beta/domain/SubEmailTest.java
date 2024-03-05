package atig.recruiting.beta.domain;

import static atig.recruiting.beta.domain.EmailTestSamples.*;
import static atig.recruiting.beta.domain.SubEmailTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import atig.recruiting.beta.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SubEmailTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubEmail.class);
        SubEmail subEmail1 = getSubEmailSample1();
        SubEmail subEmail2 = new SubEmail();
        assertThat(subEmail1).isNotEqualTo(subEmail2);

        subEmail2.setId(subEmail1.getId());
        assertThat(subEmail1).isEqualTo(subEmail2);

        subEmail2 = getSubEmailSample2();
        assertThat(subEmail1).isNotEqualTo(subEmail2);
    }

    @Test
    void subEmailEmailTest() throws Exception {
        SubEmail subEmail = getSubEmailRandomSampleGenerator();
        Email emailBack = getEmailRandomSampleGenerator();

        subEmail.setSubEmailEmail(emailBack);
        assertThat(subEmail.getSubEmailEmail()).isEqualTo(emailBack);

        subEmail.subEmailEmail(null);
        assertThat(subEmail.getSubEmailEmail()).isNull();
    }

    @Test
    void emailTest() throws Exception {
        SubEmail subEmail = getSubEmailRandomSampleGenerator();
        Email emailBack = getEmailRandomSampleGenerator();

        subEmail.setEmail(emailBack);
        assertThat(subEmail.getEmail()).isEqualTo(emailBack);

        subEmail.email(null);
        assertThat(subEmail.getEmail()).isNull();
    }
}
