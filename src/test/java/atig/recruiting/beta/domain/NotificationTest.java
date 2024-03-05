package atig.recruiting.beta.domain;

import static atig.recruiting.beta.domain.CandidateTestSamples.*;
import static atig.recruiting.beta.domain.CompanyTestSamples.*;
import static atig.recruiting.beta.domain.NotificationTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import atig.recruiting.beta.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NotificationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Notification.class);
        Notification notification1 = getNotificationSample1();
        Notification notification2 = new Notification();
        assertThat(notification1).isNotEqualTo(notification2);

        notification2.setId(notification1.getId());
        assertThat(notification1).isEqualTo(notification2);

        notification2 = getNotificationSample2();
        assertThat(notification1).isNotEqualTo(notification2);
    }

    @Test
    void notificationCandidateTest() throws Exception {
        Notification notification = getNotificationRandomSampleGenerator();
        Candidate candidateBack = getCandidateRandomSampleGenerator();

        notification.setNotificationCandidate(candidateBack);
        assertThat(notification.getNotificationCandidate()).isEqualTo(candidateBack);

        notification.notificationCandidate(null);
        assertThat(notification.getNotificationCandidate()).isNull();
    }

    @Test
    void notificationCompanyTest() throws Exception {
        Notification notification = getNotificationRandomSampleGenerator();
        Company companyBack = getCompanyRandomSampleGenerator();

        notification.setNotificationCompany(companyBack);
        assertThat(notification.getNotificationCompany()).isEqualTo(companyBack);

        notification.notificationCompany(null);
        assertThat(notification.getNotificationCompany()).isNull();
    }

    @Test
    void candidateTest() throws Exception {
        Notification notification = getNotificationRandomSampleGenerator();
        Candidate candidateBack = getCandidateRandomSampleGenerator();

        notification.setCandidate(candidateBack);
        assertThat(notification.getCandidate()).isEqualTo(candidateBack);

        notification.candidate(null);
        assertThat(notification.getCandidate()).isNull();
    }

    @Test
    void companyTest() throws Exception {
        Notification notification = getNotificationRandomSampleGenerator();
        Company companyBack = getCompanyRandomSampleGenerator();

        notification.setCompany(companyBack);
        assertThat(notification.getCompany()).isEqualTo(companyBack);

        notification.company(null);
        assertThat(notification.getCompany()).isNull();
    }
}
