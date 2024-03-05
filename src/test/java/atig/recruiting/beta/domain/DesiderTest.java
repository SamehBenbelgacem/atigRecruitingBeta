package atig.recruiting.beta.domain;

import static atig.recruiting.beta.domain.CompanyTestSamples.*;
import static atig.recruiting.beta.domain.DesiderTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import atig.recruiting.beta.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DesiderTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Desider.class);
        Desider desider1 = getDesiderSample1();
        Desider desider2 = new Desider();
        assertThat(desider1).isNotEqualTo(desider2);

        desider2.setId(desider1.getId());
        assertThat(desider1).isEqualTo(desider2);

        desider2 = getDesiderSample2();
        assertThat(desider1).isNotEqualTo(desider2);
    }

    @Test
    void desiderCompanyTest() throws Exception {
        Desider desider = getDesiderRandomSampleGenerator();
        Company companyBack = getCompanyRandomSampleGenerator();

        desider.setDesiderCompany(companyBack);
        assertThat(desider.getDesiderCompany()).isEqualTo(companyBack);

        desider.desiderCompany(null);
        assertThat(desider.getDesiderCompany()).isNull();
    }

    @Test
    void companyTest() throws Exception {
        Desider desider = getDesiderRandomSampleGenerator();
        Company companyBack = getCompanyRandomSampleGenerator();

        desider.setCompany(companyBack);
        assertThat(desider.getCompany()).isEqualTo(companyBack);

        desider.company(null);
        assertThat(desider.getCompany()).isNull();
    }
}
