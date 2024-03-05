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

class ProcessTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Process.class);
        Process process1 = getProcessSample1();
        Process process2 = new Process();
        assertThat(process1).isNotEqualTo(process2);

        process2.setId(process1.getId());
        assertThat(process1).isEqualTo(process2);

        process2 = getProcessSample2();
        assertThat(process1).isNotEqualTo(process2);
    }

    @Test
    void stepsTest() throws Exception {
        Process process = getProcessRandomSampleGenerator();
        ProcessStep processStepBack = getProcessStepRandomSampleGenerator();

        process.addSteps(processStepBack);
        assertThat(process.getSteps()).containsOnly(processStepBack);
        assertThat(processStepBack.getProcess()).isEqualTo(process);

        process.removeSteps(processStepBack);
        assertThat(process.getSteps()).doesNotContain(processStepBack);
        assertThat(processStepBack.getProcess()).isNull();

        process.steps(new HashSet<>(Set.of(processStepBack)));
        assertThat(process.getSteps()).containsOnly(processStepBack);
        assertThat(processStepBack.getProcess()).isEqualTo(process);

        process.setSteps(new HashSet<>());
        assertThat(process.getSteps()).doesNotContain(processStepBack);
        assertThat(processStepBack.getProcess()).isNull();
    }

    @Test
    void candidatesTest() throws Exception {
        Process process = getProcessRandomSampleGenerator();
        Candidate candidateBack = getCandidateRandomSampleGenerator();

        process.addCandidates(candidateBack);
        assertThat(process.getCandidates()).containsOnly(candidateBack);
        assertThat(candidateBack.getProcess()).isEqualTo(process);

        process.removeCandidates(candidateBack);
        assertThat(process.getCandidates()).doesNotContain(candidateBack);
        assertThat(candidateBack.getProcess()).isNull();

        process.candidates(new HashSet<>(Set.of(candidateBack)));
        assertThat(process.getCandidates()).containsOnly(candidateBack);
        assertThat(candidateBack.getProcess()).isEqualTo(process);

        process.setCandidates(new HashSet<>());
        assertThat(process.getCandidates()).doesNotContain(candidateBack);
        assertThat(candidateBack.getProcess()).isNull();
    }

    @Test
    void companiesTest() throws Exception {
        Process process = getProcessRandomSampleGenerator();
        Company companyBack = getCompanyRandomSampleGenerator();

        process.addCompanies(companyBack);
        assertThat(process.getCompanies()).containsOnly(companyBack);
        assertThat(companyBack.getProcess()).isEqualTo(process);

        process.removeCompanies(companyBack);
        assertThat(process.getCompanies()).doesNotContain(companyBack);
        assertThat(companyBack.getProcess()).isNull();

        process.companies(new HashSet<>(Set.of(companyBack)));
        assertThat(process.getCompanies()).containsOnly(companyBack);
        assertThat(companyBack.getProcess()).isEqualTo(process);

        process.setCompanies(new HashSet<>());
        assertThat(process.getCompanies()).doesNotContain(companyBack);
        assertThat(companyBack.getProcess()).isNull();
    }
}
