package atig.recruiting.beta.domain;

import static atig.recruiting.beta.domain.CompanyTestSamples.*;
import static atig.recruiting.beta.domain.OfferTestSamples.*;
import static atig.recruiting.beta.domain.TagTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import atig.recruiting.beta.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class OfferTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Offer.class);
        Offer offer1 = getOfferSample1();
        Offer offer2 = new Offer();
        assertThat(offer1).isNotEqualTo(offer2);

        offer2.setId(offer1.getId());
        assertThat(offer1).isEqualTo(offer2);

        offer2 = getOfferSample2();
        assertThat(offer1).isNotEqualTo(offer2);
    }

    @Test
    void offerCompanyTest() throws Exception {
        Offer offer = getOfferRandomSampleGenerator();
        Company companyBack = getCompanyRandomSampleGenerator();

        offer.setOfferCompany(companyBack);
        assertThat(offer.getOfferCompany()).isEqualTo(companyBack);

        offer.offerCompany(null);
        assertThat(offer.getOfferCompany()).isNull();
    }

    @Test
    void tagsTest() throws Exception {
        Offer offer = getOfferRandomSampleGenerator();
        Tag tagBack = getTagRandomSampleGenerator();

        offer.addTags(tagBack);
        assertThat(offer.getTags()).containsOnly(tagBack);

        offer.removeTags(tagBack);
        assertThat(offer.getTags()).doesNotContain(tagBack);

        offer.tags(new HashSet<>(Set.of(tagBack)));
        assertThat(offer.getTags()).containsOnly(tagBack);

        offer.setTags(new HashSet<>());
        assertThat(offer.getTags()).doesNotContain(tagBack);
    }

    @Test
    void companyTest() throws Exception {
        Offer offer = getOfferRandomSampleGenerator();
        Company companyBack = getCompanyRandomSampleGenerator();

        offer.setCompany(companyBack);
        assertThat(offer.getCompany()).isEqualTo(companyBack);

        offer.company(null);
        assertThat(offer.getCompany()).isNull();
    }
}
