package atig.recruiting.beta.domain;

import atig.recruiting.beta.domain.enumeration.EnumProsessType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Process.
 */
@Entity
@Table(name = "process")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Process implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private EnumProsessType type;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "process")
    @JsonIgnoreProperties(value = { "candidates", "companies", "processStepProcess", "process" }, allowSetters = true)
    private Set<ProcessStep> steps = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "process")
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "process")
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

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Process id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Process title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public EnumProsessType getType() {
        return this.type;
    }

    public Process type(EnumProsessType type) {
        this.setType(type);
        return this;
    }

    public void setType(EnumProsessType type) {
        this.type = type;
    }

    public Set<ProcessStep> getSteps() {
        return this.steps;
    }

    public void setSteps(Set<ProcessStep> processSteps) {
        if (this.steps != null) {
            this.steps.forEach(i -> i.setProcess(null));
        }
        if (processSteps != null) {
            processSteps.forEach(i -> i.setProcess(this));
        }
        this.steps = processSteps;
    }

    public Process steps(Set<ProcessStep> processSteps) {
        this.setSteps(processSteps);
        return this;
    }

    public Process addSteps(ProcessStep processStep) {
        this.steps.add(processStep);
        processStep.setProcess(this);
        return this;
    }

    public Process removeSteps(ProcessStep processStep) {
        this.steps.remove(processStep);
        processStep.setProcess(null);
        return this;
    }

    public Set<Candidate> getCandidates() {
        return this.candidates;
    }

    public void setCandidates(Set<Candidate> candidates) {
        if (this.candidates != null) {
            this.candidates.forEach(i -> i.setProcess(null));
        }
        if (candidates != null) {
            candidates.forEach(i -> i.setProcess(this));
        }
        this.candidates = candidates;
    }

    public Process candidates(Set<Candidate> candidates) {
        this.setCandidates(candidates);
        return this;
    }

    public Process addCandidates(Candidate candidate) {
        this.candidates.add(candidate);
        candidate.setProcess(this);
        return this;
    }

    public Process removeCandidates(Candidate candidate) {
        this.candidates.remove(candidate);
        candidate.setProcess(null);
        return this;
    }

    public Set<Company> getCompanies() {
        return this.companies;
    }

    public void setCompanies(Set<Company> companies) {
        if (this.companies != null) {
            this.companies.forEach(i -> i.setProcess(null));
        }
        if (companies != null) {
            companies.forEach(i -> i.setProcess(this));
        }
        this.companies = companies;
    }

    public Process companies(Set<Company> companies) {
        this.setCompanies(companies);
        return this;
    }

    public Process addCompanies(Company company) {
        this.companies.add(company);
        company.setProcess(this);
        return this;
    }

    public Process removeCompanies(Company company) {
        this.companies.remove(company);
        company.setProcess(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Process)) {
            return false;
        }
        return getId() != null && getId().equals(((Process) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Process{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
