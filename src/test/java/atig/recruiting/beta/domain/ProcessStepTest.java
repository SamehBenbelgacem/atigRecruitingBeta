package atig.recruiting.beta.domain;

import static atig.recruiting.beta.domain.CandidateTestSamples.*;
import static atig.recruiting.beta.domain.CompanyTestSamples.*;
import static atig.recruiting.beta.domain.ProcessStepTestSamples.*;
import static atig.recruiting.beta.domain.ProcessTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import atig.recruiting.beta.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ProcessStepTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProcessStep.class);
        ProcessStep processStep1 = getProcessStepSample1();
        ProcessStep processStep2 = new ProcessStep();
        assertThat(processStep1).isNotEqualTo(processStep2);

        processStep2.setId(processStep1.getId());
        assertThat(processStep1).isEqualTo(processStep2);

        processStep2 = getProcessStepSample2();
        assertThat(processStep1).isNotEqualTo(processStep2);
    }

    @Test
    void candidatesTest() throws Exception {
        ProcessStep processStep = getProcessStepRandomSampleGenerator();
        Candidate candidateBack = getCandidateRandomSampleGenerator();

        processStep.addCandidates(candidateBack);
        assertThat(processStep.getCandidates()).containsOnly(candidateBack);
        assertThat(candidateBack.getProcessStep()).isEqualTo(processStep);

        processStep.removeCandidates(candidateBack);
        assertThat(processStep.getCandidates()).doesNotContain(candidateBack);
        assertThat(candidateBack.getProcessStep()).isNull();

        processStep.candidates(new HashSet<>(Set.of(candidateBack)));
        assertThat(processStep.getCandidates()).containsOnly(candidateBack);
        assertThat(candidateBack.getProcessStep()).isEqualTo(processStep);

        processStep.setCandidates(new HashSet<>());
        assertThat(processStep.getCandidates()).doesNotContain(candidateBack);
        assertThat(candidateBack.getProcessStep()).isNull();
    }

    @Test
    void companiesTest() throws Exception {
        ProcessStep processStep = getProcessStepRandomSampleGenerator();
        Company companyBack = getCompanyRandomSampleGenerator();

        processStep.addCompanies(companyBack);
        assertThat(processStep.getCompanies()).containsOnly(companyBack);
        assertThat(companyBack.getProcessStep()).isEqualTo(processStep);

        processStep.removeCompanies(companyBack);
        assertThat(processStep.getCompanies()).doesNotContain(companyBack);
        assertThat(companyBack.getProcessStep()).isNull();

        processStep.companies(new HashSet<>(Set.of(companyBack)));
        assertThat(processStep.getCompanies()).containsOnly(companyBack);
        assertThat(companyBack.getProcessStep()).isEqualTo(processStep);

        processStep.setCompanies(new HashSet<>());
        assertThat(processStep.getCompanies()).doesNotContain(companyBack);
        assertThat(companyBack.getProcessStep()).isNull();
    }

    @Test
    void processStepProcessTest() throws Exception {
        ProcessStep processStep = getProcessStepRandomSampleGenerator();
        Process processBack = getProcessRandomSampleGenerator();

        processStep.setProcessStepProcess(processBack);
        assertThat(processStep.getProcessStepProcess()).isEqualTo(processBack);

        processStep.processStepProcess(null);
        assertThat(processStep.getProcessStepProcess()).isNull();
    }

    @Test
    void processTest() throws Exception {
        ProcessStep processStep = getProcessStepRandomSampleGenerator();
        Process processBack = getProcessRandomSampleGenerator();

        processStep.setProcess(processBack);
        assertThat(processStep.getProcess()).isEqualTo(processBack);

        processStep.process(null);
        assertThat(processStep.getProcess()).isNull();
    }
}
