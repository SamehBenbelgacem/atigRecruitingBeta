package atig.recruiting.beta.domain;

import static atig.recruiting.beta.domain.CategoryTestSamples.*;
import static atig.recruiting.beta.domain.CompanyTestSamples.*;
import static atig.recruiting.beta.domain.DesiderTestSamples.*;
import static atig.recruiting.beta.domain.EmailTestSamples.*;
import static atig.recruiting.beta.domain.NoteTestSamples.*;
import static atig.recruiting.beta.domain.NotificationTestSamples.*;
import static atig.recruiting.beta.domain.OfferTestSamples.*;
import static atig.recruiting.beta.domain.ProcessStepTestSamples.*;
import static atig.recruiting.beta.domain.ProcessTestSamples.*;
import static atig.recruiting.beta.domain.SubCategoryTestSamples.*;
import static atig.recruiting.beta.domain.TagTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import atig.recruiting.beta.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CompanyTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Company.class);
        Company company1 = getCompanySample1();
        Company company2 = new Company();
        assertThat(company1).isNotEqualTo(company2);

        company2.setId(company1.getId());
        assertThat(company1).isEqualTo(company2);

        company2 = getCompanySample2();
        assertThat(company1).isNotEqualTo(company2);
    }

    @Test
    void notificationsTest() throws Exception {
        Company company = getCompanyRandomSampleGenerator();
        Notification notificationBack = getNotificationRandomSampleGenerator();

        company.addNotifications(notificationBack);
        assertThat(company.getNotifications()).containsOnly(notificationBack);
        assertThat(notificationBack.getCompany()).isEqualTo(company);

        company.removeNotifications(notificationBack);
        assertThat(company.getNotifications()).doesNotContain(notificationBack);
        assertThat(notificationBack.getCompany()).isNull();

        company.notifications(new HashSet<>(Set.of(notificationBack)));
        assertThat(company.getNotifications()).containsOnly(notificationBack);
        assertThat(notificationBack.getCompany()).isEqualTo(company);

        company.setNotifications(new HashSet<>());
        assertThat(company.getNotifications()).doesNotContain(notificationBack);
        assertThat(notificationBack.getCompany()).isNull();
    }

    @Test
    void notesTest() throws Exception {
        Company company = getCompanyRandomSampleGenerator();
        Note noteBack = getNoteRandomSampleGenerator();

        company.addNotes(noteBack);
        assertThat(company.getNotes()).containsOnly(noteBack);
        assertThat(noteBack.getCompany()).isEqualTo(company);

        company.removeNotes(noteBack);
        assertThat(company.getNotes()).doesNotContain(noteBack);
        assertThat(noteBack.getCompany()).isNull();

        company.notes(new HashSet<>(Set.of(noteBack)));
        assertThat(company.getNotes()).containsOnly(noteBack);
        assertThat(noteBack.getCompany()).isEqualTo(company);

        company.setNotes(new HashSet<>());
        assertThat(company.getNotes()).doesNotContain(noteBack);
        assertThat(noteBack.getCompany()).isNull();
    }

    @Test
    void desidersTest() throws Exception {
        Company company = getCompanyRandomSampleGenerator();
        Desider desiderBack = getDesiderRandomSampleGenerator();

        company.addDesiders(desiderBack);
        assertThat(company.getDesiders()).containsOnly(desiderBack);
        assertThat(desiderBack.getCompany()).isEqualTo(company);

        company.removeDesiders(desiderBack);
        assertThat(company.getDesiders()).doesNotContain(desiderBack);
        assertThat(desiderBack.getCompany()).isNull();

        company.desiders(new HashSet<>(Set.of(desiderBack)));
        assertThat(company.getDesiders()).containsOnly(desiderBack);
        assertThat(desiderBack.getCompany()).isEqualTo(company);

        company.setDesiders(new HashSet<>());
        assertThat(company.getDesiders()).doesNotContain(desiderBack);
        assertThat(desiderBack.getCompany()).isNull();
    }

    @Test
    void offersTest() throws Exception {
        Company company = getCompanyRandomSampleGenerator();
        Offer offerBack = getOfferRandomSampleGenerator();

        company.addOffers(offerBack);
        assertThat(company.getOffers()).containsOnly(offerBack);
        assertThat(offerBack.getCompany()).isEqualTo(company);

        company.removeOffers(offerBack);
        assertThat(company.getOffers()).doesNotContain(offerBack);
        assertThat(offerBack.getCompany()).isNull();

        company.offers(new HashSet<>(Set.of(offerBack)));
        assertThat(company.getOffers()).containsOnly(offerBack);
        assertThat(offerBack.getCompany()).isEqualTo(company);

        company.setOffers(new HashSet<>());
        assertThat(company.getOffers()).doesNotContain(offerBack);
        assertThat(offerBack.getCompany()).isNull();
    }

    @Test
    void emailsTest() throws Exception {
        Company company = getCompanyRandomSampleGenerator();
        Email emailBack = getEmailRandomSampleGenerator();

        company.addEmails(emailBack);
        assertThat(company.getEmails()).containsOnly(emailBack);
        assertThat(emailBack.getCompany()).isEqualTo(company);

        company.removeEmails(emailBack);
        assertThat(company.getEmails()).doesNotContain(emailBack);
        assertThat(emailBack.getCompany()).isNull();

        company.emails(new HashSet<>(Set.of(emailBack)));
        assertThat(company.getEmails()).containsOnly(emailBack);
        assertThat(emailBack.getCompany()).isEqualTo(company);

        company.setEmails(new HashSet<>());
        assertThat(company.getEmails()).doesNotContain(emailBack);
        assertThat(emailBack.getCompany()).isNull();
    }

    @Test
    void companyCategoryTest() throws Exception {
        Company company = getCompanyRandomSampleGenerator();
        Category categoryBack = getCategoryRandomSampleGenerator();

        company.setCompanyCategory(categoryBack);
        assertThat(company.getCompanyCategory()).isEqualTo(categoryBack);

        company.companyCategory(null);
        assertThat(company.getCompanyCategory()).isNull();
    }

    @Test
    void companySubCategoryTest() throws Exception {
        Company company = getCompanyRandomSampleGenerator();
        SubCategory subCategoryBack = getSubCategoryRandomSampleGenerator();

        company.setCompanySubCategory(subCategoryBack);
        assertThat(company.getCompanySubCategory()).isEqualTo(subCategoryBack);

        company.companySubCategory(null);
        assertThat(company.getCompanySubCategory()).isNull();
    }

    @Test
    void companyProcessTest() throws Exception {
        Company company = getCompanyRandomSampleGenerator();
        Process processBack = getProcessRandomSampleGenerator();

        company.setCompanyProcess(processBack);
        assertThat(company.getCompanyProcess()).isEqualTo(processBack);

        company.companyProcess(null);
        assertThat(company.getCompanyProcess()).isNull();
    }

    @Test
    void companyProcessStepTest() throws Exception {
        Company company = getCompanyRandomSampleGenerator();
        ProcessStep processStepBack = getProcessStepRandomSampleGenerator();

        company.setCompanyProcessStep(processStepBack);
        assertThat(company.getCompanyProcessStep()).isEqualTo(processStepBack);

        company.companyProcessStep(null);
        assertThat(company.getCompanyProcessStep()).isNull();
    }

    @Test
    void tagsTest() throws Exception {
        Company company = getCompanyRandomSampleGenerator();
        Tag tagBack = getTagRandomSampleGenerator();

        company.addTags(tagBack);
        assertThat(company.getTags()).containsOnly(tagBack);

        company.removeTags(tagBack);
        assertThat(company.getTags()).doesNotContain(tagBack);

        company.tags(new HashSet<>(Set.of(tagBack)));
        assertThat(company.getTags()).containsOnly(tagBack);

        company.setTags(new HashSet<>());
        assertThat(company.getTags()).doesNotContain(tagBack);
    }

    @Test
    void categoryTest() throws Exception {
        Company company = getCompanyRandomSampleGenerator();
        Category categoryBack = getCategoryRandomSampleGenerator();

        company.setCategory(categoryBack);
        assertThat(company.getCategory()).isEqualTo(categoryBack);

        company.category(null);
        assertThat(company.getCategory()).isNull();
    }

    @Test
    void subCategoryTest() throws Exception {
        Company company = getCompanyRandomSampleGenerator();
        SubCategory subCategoryBack = getSubCategoryRandomSampleGenerator();

        company.setSubCategory(subCategoryBack);
        assertThat(company.getSubCategory()).isEqualTo(subCategoryBack);

        company.subCategory(null);
        assertThat(company.getSubCategory()).isNull();
    }

    @Test
    void processTest() throws Exception {
        Company company = getCompanyRandomSampleGenerator();
        Process processBack = getProcessRandomSampleGenerator();

        company.setProcess(processBack);
        assertThat(company.getProcess()).isEqualTo(processBack);

        company.process(null);
        assertThat(company.getProcess()).isNull();
    }

    @Test
    void processStepTest() throws Exception {
        Company company = getCompanyRandomSampleGenerator();
        ProcessStep processStepBack = getProcessStepRandomSampleGenerator();

        company.setProcessStep(processStepBack);
        assertThat(company.getProcessStep()).isEqualTo(processStepBack);

        company.processStep(null);
        assertThat(company.getProcessStep()).isNull();
    }
}
