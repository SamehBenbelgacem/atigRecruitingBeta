package atig.recruiting.beta.domain;

import atig.recruiting.beta.domain.enumeration.EnumPriority;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A ProcessStep.
 */
@Entity
@Table(name = "process_step")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProcessStep implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "jhi_order")
    private String order;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private EnumPriority priority;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "processStep")
    @JsonIgnoreProperties(
        value = {
            "additionalInfos",
            "experiences",
            "educations",
            "certifications",
            "skills",
            "languages",
            "notifications",
            "notes",
            "emails",
            "candidateCategory",
            "candidateSubCategory",
            "candidateProcess",
            "candidateProcessStep",
            "tags",
            "category",
            "subCategory",
            "process",
            "processStep",
        },
        allowSetters = true
    )
    private Set<Candidate> candidates = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "processStep")
    @JsonIgnoreProperties(
        value = {
            "notifications",
            "notes",
            "desiders",
            "offers",
            "emails",
            "companyCategory",
            "companySubCategory",
            "companyProcess",
            "companyProcessStep",
            "tags",
            "category",
            "subCategory",
            "process",
            "processStep",
        },
        allowSetters = true
    )
    private Set<Company> companies = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "steps", "candidates", "companies" }, allowSetters = true)
    private Process processStepProcess;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "steps", "candidates", "companies" }, allowSetters = true)
    private Process process;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ProcessStep id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public ProcessStep title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrder() {
        return this.order;
    }

    public ProcessStep order(String order) {
        this.setOrder(order);
        return this;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public EnumPriority getPriority() {
        return this.priority;
    }

    public ProcessStep priority(EnumPriority priority) {
        this.setPriority(priority);
        return this;
    }

    public void setPriority(EnumPriority priority) {
        this.priority = priority;
    }

    public Set<Candidate> getCandidates() {
        return this.candidates;
    }

    public void setCandidates(Set<Candidate> candidates) {
        if (this.candidates != null) {
            this.candidates.forEach(i -> i.setProcessStep(null));
        }
        if (candidates != null) {
            candidates.forEach(i -> i.setProcessStep(this));
        }
        this.candidates = candidates;
    }

    public ProcessStep candidates(Set<Candidate> candidates) {
        this.setCandidates(candidates);
        return this;
    }

    public ProcessStep addCandidates(Candidate candidate) {
        this.candidates.add(candidate);
        candidate.setProcessStep(this);
        return this;
    }

    public ProcessStep removeCandidates(Candidate candidate) {
        this.candidates.remove(candidate);
        candidate.setProcessStep(null);
        return this;
    }

    public Set<Company> getCompanies() {
        return this.companies;
    }

    public void setCompanies(Set<Company> companies) {
        if (this.companies != null) {
            this.companies.forEach(i -> i.setProcessStep(null));
        }
        if (companies != null) {
            companies.forEach(i -> i.setProcessStep(this));
        }
        this.companies = companies;
    }

    public ProcessStep companies(Set<Company> companies) {
        this.setCompanies(companies);
        return this;
    }

    public ProcessStep addCompanies(Company company) {
        this.companies.add(company);
        company.setProcessStep(this);
        return this;
    }

    public ProcessStep removeCompanies(Company company) {
        this.companies.remove(company);
        company.setProcessStep(null);
        return this;
    }

    public Process getProcessStepProcess() {
        return this.processStepProcess;
    }

    public void setProcessStepProcess(Process process) {
        this.processStepProcess = process;
    }

    public ProcessStep processStepProcess(Process process) {
        this.setProcessStepProcess(process);
        return this;
    }

    public Process getProcess() {
        return this.process;
    }

    public void setProcess(Process process) {
        this.process = process;
    }

    public ProcessStep process(Process process) {
        this.setProcess(process);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProcessStep)) {
            return false;
        }
        return getId() != null && getId().equals(((ProcessStep) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProcessStep{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", order='" + getOrder() + "'" +
            ", priority='" + getPriority() + "'" +
            "}";
    }
}
