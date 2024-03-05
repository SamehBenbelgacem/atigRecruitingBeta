package atig.recruiting.beta.domain;

import static atig.recruiting.beta.domain.CandidateTestSamples.*;
import static atig.recruiting.beta.domain.CompanyTestSamples.*;
import static atig.recruiting.beta.domain.EmailTestSamples.*;
import static atig.recruiting.beta.domain.EmailcredentialsTestSamples.*;
import static atig.recruiting.beta.domain.ObjectContainingFileTestSamples.*;
import static atig.recruiting.beta.domain.SubEmailTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import atig.recruiting.beta.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class EmailTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Email.class);
        Email email1 = getEmailSample1();
        Email email2 = new Email();
        assertThat(email1).isNotEqualTo(email2);

        email2.setId(email1.getId());
        assertThat(email1).isEqualTo(email2);

        email2 = getEmailSample2();
        assertThat(email1).isNotEqualTo(email2);
    }

    @Test
    void joinedFilesTest() throws Exception {
        Email email = getEmailRandomSampleGenerator();
        ObjectContainingFile objectContainingFileBack = getObjectContainingFileRandomSampleGenerator();

        email.addJoinedFiles(objectContainingFileBack);
        assertThat(email.getJoinedFiles()).containsOnly(objectContainingFileBack);
        assertThat(objectContainingFileBack.getEmail()).isEqualTo(email);

        email.removeJoinedFiles(objectContainingFileBack);
        assertThat(email.getJoinedFiles()).doesNotContain(objectContainingFileBack);
        assertThat(objectContainingFileBack.getEmail()).isNull();

        email.joinedFiles(new HashSet<>(Set.of(objectContainingFileBack)));
        assertThat(email.getJoinedFiles()).containsOnly(objectContainingFileBack);
        assertThat(objectContainingFileBack.getEmail()).isEqualTo(email);

        email.setJoinedFiles(new HashSet<>());
        assertThat(email.getJoinedFiles()).doesNotContain(objectContainingFileBack);
        assertThat(objectContainingFileBack.getEmail()).isNull();
    }

    @Test
    void subEmailsTest() throws Exception {
        Email email = getEmailRandomSampleGenerator();
        SubEmail subEmailBack = getSubEmailRandomSampleGenerator();

        email.addSubEmails(subEmailBack);
        assertThat(email.getSubEmails()).containsOnly(subEmailBack);
        assertThat(subEmailBack.getEmail()).isEqualTo(email);

        email.removeSubEmails(subEmailBack);
        assertThat(email.getSubEmails()).doesNotContain(subEmailBack);
        assertThat(subEmailBack.getEmail()).isNull();

        email.subEmails(new HashSet<>(Set.of(subEmailBack)));
        assertThat(email.getSubEmails()).containsOnly(subEmailBack);
        assertThat(subEmailBack.getEmail()).isEqualTo(email);

        email.setSubEmails(new HashSet<>());
        assertThat(email.getSubEmails()).doesNotContain(subEmailBack);
        assertThat(subEmailBack.getEmail()).isNull();
    }

    @Test
    void emailEmailcredentialsTest() throws Exception {
        Email email = getEmailRandomSampleGenerator();
        Emailcredentials emailcredentialsBack = getEmailcredentialsRandomSampleGenerator();

        email.setEmailEmailcredentials(emailcredentialsBack);
        assertThat(email.getEmailEmailcredentials()).isEqualTo(emailcredentialsBack);

        email.emailEmailcredentials(null);
        assertThat(email.getEmailEmailcredentials()).isNull();
    }

    @Test
    void emailCandidateTest() throws Exception {
        Email email = getEmailRandomSampleGenerator();
        Candidate candidateBack = getCandidateRandomSampleGenerator();

        email.setEmailCandidate(candidateBack);
        assertThat(email.getEmailCandidate()).isEqualTo(candidateBack);

        email.emailCandidate(null);
        assertThat(email.getEmailCandidate()).isNull();
    }

    @Test
    void emailCompanyTest() throws Exception {
        Email email = getEmailRandomSampleGenerator();
        Company companyBack = getCompanyRandomSampleGenerator();

        email.setEmailCompany(companyBack);
        assertThat(email.getEmailCompany()).isEqualTo(companyBack);

        email.emailCompany(null);
        assertThat(email.getEmailCompany()).isNull();
    }

    @Test
    void candidateTest() throws Exception {
        Email email = getEmailRandomSampleGenerator();
        Candidate candidateBack = getCandidateRandomSampleGenerator();

        email.setCandidate(candidateBack);
        assertThat(email.getCandidate()).isEqualTo(candidateBack);

        email.candidate(null);
        assertThat(email.getCandidate()).isNull();
    }

    @Test
    void companyTest() throws Exception {
        Email email = getEmailRandomSampleGenerator();
        Company companyBack = getCompanyRandomSampleGenerator();

        email.setCompany(companyBack);
        assertThat(email.getCompany()).isEqualTo(companyBack);

        email.company(null);
        assertThat(email.getCompany()).isNull();
    }

    @Test
    void emailcredentialsTest() throws Exception {
        Email email = getEmailRandomSampleGenerator();
        Emailcredentials emailcredentialsBack = getEmailcredentialsRandomSampleGenerator();

        email.setEmailcredentials(emailcredentialsBack);
        assertThat(email.getEmailcredentials()).isEqualTo(emailcredentialsBack);

        email.emailcredentials(null);
        assertThat(email.getEmailcredentials()).isNull();
    }
}
