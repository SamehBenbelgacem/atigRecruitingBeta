package atig.recruiting.beta.domain;

import static atig.recruiting.beta.domain.CandidateTestSamples.*;
import static atig.recruiting.beta.domain.CompanyTestSamples.*;
import static atig.recruiting.beta.domain.OfferTestSamples.*;
import static atig.recruiting.beta.domain.TagTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import atig.recruiting.beta.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class TagTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tag.class);
        Tag tag1 = getTagSample1();
        Tag tag2 = new Tag();
        assertThat(tag1).isNotEqualTo(tag2);

        tag2.setId(tag1.getId());
        assertThat(tag1).isEqualTo(tag2);

        tag2 = getTagSample2();
        assertThat(tag1).isNotEqualTo(tag2);
    }

    @Test
    void companiesTest() throws Exception {
        Tag tag = getTagRandomSampleGenerator();
        Company companyBack = getCompanyRandomSampleGenerator();

        tag.addCompanies(companyBack);
        assertThat(tag.getCompanies()).containsOnly(companyBack);
        assertThat(companyBack.getTags()).containsOnly(tag);

        tag.removeCompanies(companyBack);
        assertThat(tag.getCompanies()).doesNotContain(companyBack);
        assertThat(companyBack.getTags()).doesNotContain(tag);

        tag.companies(new HashSet<>(Set.of(companyBack)));
        assertThat(tag.getCompanies()).containsOnly(companyBack);
        assertThat(companyBack.getTags()).containsOnly(tag);

        tag.setCompanies(new HashSet<>());
        assertThat(tag.getCompanies()).doesNotContain(companyBack);
        assertThat(companyBack.getTags()).doesNotContain(tag);
    }

    @Test
    void offersTest() throws Exception {
        Tag tag = getTagRandomSampleGenerator();
        Offer offerBack = getOfferRandomSampleGenerator();

        tag.addOffers(offerBack);
        assertThat(tag.getOffers()).containsOnly(offerBack);
        assertThat(offerBack.getTags()).containsOnly(tag);

        tag.removeOffers(offerBack);
        assertThat(tag.getOffers()).doesNotContain(offerBack);
        assertThat(offerBack.getTags()).doesNotContain(tag);

        tag.offers(new HashSet<>(Set.of(offerBack)));
        assertThat(tag.getOffers()).containsOnly(offerBack);
        assertThat(offerBack.getTags()).containsOnly(tag);

        tag.setOffers(new HashSet<>());
        assertThat(tag.getOffers()).doesNotContain(offerBack);
        assertThat(offerBack.getTags()).doesNotContain(tag);
    }

    @Test
    void candidatesTest() throws Exception {
        Tag tag = getTagRandomSampleGenerator();
        Candidate candidateBack = getCandidateRandomSampleGenerator();

        tag.addCandidates(candidateBack);
        assertThat(tag.getCandidates()).containsOnly(candidateBack);
        assertThat(candidateBack.getTags()).containsOnly(tag);

        tag.removeCandidates(candidateBack);
        assertThat(tag.getCandidates()).doesNotContain(candidateBack);
        assertThat(candidateBack.getTags()).doesNotContain(tag);

        tag.candidates(new HashSet<>(Set.of(candidateBack)));
        assertThat(tag.getCandidates()).containsOnly(candidateBack);
        assertThat(candidateBack.getTags()).containsOnly(tag);

        tag.setCandidates(new HashSet<>());
        assertThat(tag.getCandidates()).doesNotContain(candidateBack);
        assertThat(candidateBack.getTags()).doesNotContain(tag);
    }
}
