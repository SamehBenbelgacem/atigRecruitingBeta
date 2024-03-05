package atig.recruiting.beta.domain;

import static atig.recruiting.beta.domain.CandidateAdditionalInfosTestSamples.*;
import static atig.recruiting.beta.domain.CandidateTestSamples.*;
import static atig.recruiting.beta.domain.CategoryTestSamples.*;
import static atig.recruiting.beta.domain.CertificationTestSamples.*;
import static atig.recruiting.beta.domain.EducationTestSamples.*;
import static atig.recruiting.beta.domain.EmailTestSamples.*;
import static atig.recruiting.beta.domain.ExperienceTestSamples.*;
import static atig.recruiting.beta.domain.LanguageTestSamples.*;
import static atig.recruiting.beta.domain.NoteTestSamples.*;
import static atig.recruiting.beta.domain.NotificationTestSamples.*;
import static atig.recruiting.beta.domain.ProcessStepTestSamples.*;
import static atig.recruiting.beta.domain.ProcessTestSamples.*;
import static atig.recruiting.beta.domain.SkillTestSamples.*;
import static atig.recruiting.beta.domain.SubCategoryTestSamples.*;
import static atig.recruiting.beta.domain.TagTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import atig.recruiting.beta.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CandidateTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Candidate.class);
        Candidate candidate1 = getCandidateSample1();
        Candidate candidate2 = new Candidate();
        assertThat(candidate1).isNotEqualTo(candidate2);

        candidate2.setId(candidate1.getId());
        assertThat(candidate1).isEqualTo(candidate2);

        candidate2 = getCandidateSample2();
        assertThat(candidate1).isNotEqualTo(candidate2);
    }

    @Test
    void additionalInfosTest() throws Exception {
        Candidate candidate = getCandidateRandomSampleGenerator();
        CandidateAdditionalInfos candidateAdditionalInfosBack = getCandidateAdditionalInfosRandomSampleGenerator();

        candidate.setAdditionalInfos(candidateAdditionalInfosBack);
        assertThat(candidate.getAdditionalInfos()).isEqualTo(candidateAdditionalInfosBack);

        candidate.additionalInfos(null);
        assertThat(candidate.getAdditionalInfos()).isNull();
    }

    @Test
    void experiencesTest() throws Exception {
        Candidate candidate = getCandidateRandomSampleGenerator();
        Experience experienceBack = getExperienceRandomSampleGenerator();

        candidate.addExperiences(experienceBack);
        assertThat(candidate.getExperiences()).containsOnly(experienceBack);
        assertThat(experienceBack.getCandidate()).isEqualTo(candidate);

        candidate.removeExperiences(experienceBack);
        assertThat(candidate.getExperiences()).doesNotContain(experienceBack);
        assertThat(experienceBack.getCandidate()).isNull();

        candidate.experiences(new HashSet<>(Set.of(experienceBack)));
        assertThat(candidate.getExperiences()).containsOnly(experienceBack);
        assertThat(experienceBack.getCandidate()).isEqualTo(candidate);

        candidate.setExperiences(new HashSet<>());
        assertThat(candidate.getExperiences()).doesNotContain(experienceBack);
        assertThat(experienceBack.getCandidate()).isNull();
    }

    @Test
    void educationsTest() throws Exception {
        Candidate candidate = getCandidateRandomSampleGenerator();
        Education educationBack = getEducationRandomSampleGenerator();

        candidate.addEducations(educationBack);
        assertThat(candidate.getEducations()).containsOnly(educationBack);
        assertThat(educationBack.getCandidate()).isEqualTo(candidate);

        candidate.removeEducations(educationBack);
        assertThat(candidate.getEducations()).doesNotContain(educationBack);
        assertThat(educationBack.getCandidate()).isNull();

        candidate.educations(new HashSet<>(Set.of(educationBack)));
        assertThat(candidate.getEducations()).containsOnly(educationBack);
        assertThat(educationBack.getCandidate()).isEqualTo(candidate);

        candidate.setEducations(new HashSet<>());
        assertThat(candidate.getEducations()).doesNotContain(educationBack);
        assertThat(educationBack.getCandidate()).isNull();
    }

    @Test
    void certificationsTest() throws Exception {
        Candidate candidate = getCandidateRandomSampleGenerator();
        Certification certificationBack = getCertificationRandomSampleGenerator();

        candidate.addCertifications(certificationBack);
        assertThat(candidate.getCertifications()).containsOnly(certificationBack);
        assertThat(certificationBack.getCandidate()).isEqualTo(candidate);

        candidate.removeCertifications(certificationBack);
        assertThat(candidate.getCertifications()).doesNotContain(certificationBack);
        assertThat(certificationBack.getCandidate()).isNull();

        candidate.certifications(new HashSet<>(Set.of(certificationBack)));
        assertThat(candidate.getCertifications()).containsOnly(certificationBack);
        assertThat(certificationBack.getCandidate()).isEqualTo(candidate);

        candidate.setCertifications(new HashSet<>());
        assertThat(candidate.getCertifications()).doesNotContain(certificationBack);
        assertThat(certificationBack.getCandidate()).isNull();
    }

    @Test
    void skillsTest() throws Exception {
        Candidate candidate = getCandidateRandomSampleGenerator();
        Skill skillBack = getSkillRandomSampleGenerator();

        candidate.addSkills(skillBack);
        assertThat(candidate.getSkills()).containsOnly(skillBack);
        assertThat(skillBack.getCandidate()).isEqualTo(candidate);

        candidate.removeSkills(skillBack);
        assertThat(candidate.getSkills()).doesNotContain(skillBack);
        assertThat(skillBack.getCandidate()).isNull();

        candidate.skills(new HashSet<>(Set.of(skillBack)));
        assertThat(candidate.getSkills()).containsOnly(skillBack);
        assertThat(skillBack.getCandidate()).isEqualTo(candidate);

        candidate.setSkills(new HashSet<>());
        assertThat(candidate.getSkills()).doesNotContain(skillBack);
        assertThat(skillBack.getCandidate()).isNull();
    }

    @Test
    void languagesTest() throws Exception {
        Candidate candidate = getCandidateRandomSampleGenerator();
        Language languageBack = getLanguageRandomSampleGenerator();

        candidate.addLanguages(languageBack);
        assertThat(candidate.getLanguages()).containsOnly(languageBack);
        assertThat(languageBack.getCandidate()).isEqualTo(candidate);

        candidate.removeLanguages(languageBack);
        assertThat(candidate.getLanguages()).doesNotContain(languageBack);
        assertThat(languageBack.getCandidate()).isNull();

        candidate.languages(new HashSet<>(Set.of(languageBack)));
        assertThat(candidate.getLanguages()).containsOnly(languageBack);
        assertThat(languageBack.getCandidate()).isEqualTo(candidate);

        candidate.setLanguages(new HashSet<>());
        assertThat(candidate.getLanguages()).doesNotContain(languageBack);
        assertThat(languageBack.getCandidate()).isNull();
    }

    @Test
    void notificationsTest() throws Exception {
        Candidate candidate = getCandidateRandomSampleGenerator();
        Notification notificationBack = getNotificationRandomSampleGenerator();

        candidate.addNotifications(notificationBack);
        assertThat(candidate.getNotifications()).containsOnly(notificationBack);
        assertThat(notificationBack.getCandidate()).isEqualTo(candidate);

        candidate.removeNotifications(notificationBack);
        assertThat(candidate.getNotifications()).doesNotContain(notificationBack);
        assertThat(notificationBack.getCandidate()).isNull();

        candidate.notifications(new HashSet<>(Set.of(notificationBack)));
        assertThat(candidate.getNotifications()).containsOnly(notificationBack);
        assertThat(notificationBack.getCandidate()).isEqualTo(candidate);

        candidate.setNotifications(new HashSet<>());
        assertThat(candidate.getNotifications()).doesNotContain(notificationBack);
        assertThat(notificationBack.getCandidate()).isNull();
    }

    @Test
    void notesTest() throws Exception {
        Candidate candidate = getCandidateRandomSampleGenerator();
        Note noteBack = getNoteRandomSampleGenerator();

        candidate.addNotes(noteBack);
        assertThat(candidate.getNotes()).containsOnly(noteBack);
        assertThat(noteBack.getCandidate()).isEqualTo(candidate);

        candidate.removeNotes(noteBack);
        assertThat(candidate.getNotes()).doesNotContain(noteBack);
        assertThat(noteBack.getCandidate()).isNull();

        candidate.notes(new HashSet<>(Set.of(noteBack)));
        assertThat(candidate.getNotes()).containsOnly(noteBack);
        assertThat(noteBack.getCandidate()).isEqualTo(candidate);

        candidate.setNotes(new HashSet<>());
        assertThat(candidate.getNotes()).doesNotContain(noteBack);
        assertThat(noteBack.getCandidate()).isNull();
    }

    @Test
    void emailsTest() throws Exception {
        Candidate candidate = getCandidateRandomSampleGenerator();
        Email emailBack = getEmailRandomSampleGenerator();

        candidate.addEmails(emailBack);
        assertThat(candidate.getEmails()).containsOnly(emailBack);
        assertThat(emailBack.getCandidate()).isEqualTo(candidate);

        candidate.removeEmails(emailBack);
        assertThat(candidate.getEmails()).doesNotContain(emailBack);
        assertThat(emailBack.getCandidate()).isNull();

        candidate.emails(new HashSet<>(Set.of(emailBack)));
        assertThat(candidate.getEmails()).containsOnly(emailBack);
        assertThat(emailBack.getCandidate()).isEqualTo(candidate);

        candidate.setEmails(new HashSet<>());
        assertThat(candidate.getEmails()).doesNotContain(emailBack);
        assertThat(emailBack.getCandidate()).isNull();
    }

    @Test
    void candidateCategoryTest() throws Exception {
        Candidate candidate = getCandidateRandomSampleGenerator();
        Category categoryBack = getCategoryRandomSampleGenerator();

        candidate.setCandidateCategory(categoryBack);
        assertThat(candidate.getCandidateCategory()).isEqualTo(categoryBack);

        candidate.candidateCategory(null);
        assertThat(candidate.getCandidateCategory()).isNull();
    }

    @Test
    void candidateSubCategoryTest() throws Exception {
        Candidate candidate = getCandidateRandomSampleGenerator();
        SubCategory subCategoryBack = getSubCategoryRandomSampleGenerator();

        candidate.setCandidateSubCategory(subCategoryBack);
        assertThat(candidate.getCandidateSubCategory()).isEqualTo(subCategoryBack);

        candidate.candidateSubCategory(null);
        assertThat(candidate.getCandidateSubCategory()).isNull();
    }

    @Test
    void candidateProcessTest() throws Exception {
        Candidate candidate = getCandidateRandomSampleGenerator();
        Process processBack = getProcessRandomSampleGenerator();

        candidate.setCandidateProcess(processBack);
        assertThat(candidate.getCandidateProcess()).isEqualTo(processBack);

        candidate.candidateProcess(null);
        assertThat(candidate.getCandidateProcess()).isNull();
    }

    @Test
    void candidateProcessStepTest() throws Exception {
        Candidate candidate = getCandidateRandomSampleGenerator();
        ProcessStep processStepBack = getProcessStepRandomSampleGenerator();

        candidate.setCandidateProcessStep(processStepBack);
        assertThat(candidate.getCandidateProcessStep()).isEqualTo(processStepBack);

        candidate.candidateProcessStep(null);
        assertThat(candidate.getCandidateProcessStep()).isNull();
    }

    @Test
    void tagsTest() throws Exception {
        Candidate candidate = getCandidateRandomSampleGenerator();
        Tag tagBack = getTagRandomSampleGenerator();

        candidate.addTags(tagBack);
        assertThat(candidate.getTags()).containsOnly(tagBack);

        candidate.removeTags(tagBack);
        assertThat(candidate.getTags()).doesNotContain(tagBack);

        candidate.tags(new HashSet<>(Set.of(tagBack)));
        assertThat(candidate.getTags()).containsOnly(tagBack);

        candidate.setTags(new HashSet<>());
        assertThat(candidate.getTags()).doesNotContain(tagBack);
    }

    @Test
    void categoryTest() throws Exception {
        Candidate candidate = getCandidateRandomSampleGenerator();
        Category categoryBack = getCategoryRandomSampleGenerator();

        candidate.setCategory(categoryBack);
        assertThat(candidate.getCategory()).isEqualTo(categoryBack);

        candidate.category(null);
        assertThat(candidate.getCategory()).isNull();
    }

    @Test
    void subCategoryTest() throws Exception {
        Candidate candidate = getCandidateRandomSampleGenerator();
        SubCategory subCategoryBack = getSubCategoryRandomSampleGenerator();

        candidate.setSubCategory(subCategoryBack);
        assertThat(candidate.getSubCategory()).isEqualTo(subCategoryBack);

        candidate.subCategory(null);
        assertThat(candidate.getSubCategory()).isNull();
    }

    @Test
    void processTest() throws Exception {
        Candidate candidate = getCandidateRandomSampleGenerator();
        Process processBack = getProcessRandomSampleGenerator();

        candidate.setProcess(processBack);
        assertThat(candidate.getProcess()).isEqualTo(processBack);

        candidate.process(null);
        assertThat(candidate.getProcess()).isNull();
    }

    @Test
    void processStepTest() throws Exception {
        Candidate candidate = getCandidateRandomSampleGenerator();
        ProcessStep processStepBack = getProcessStepRandomSampleGenerator();

        candidate.setProcessStep(processStepBack);
        assertThat(candidate.getProcessStep()).isEqualTo(processStepBack);

        candidate.processStep(null);
        assertThat(candidate.getProcessStep()).isNull();
    }
}
