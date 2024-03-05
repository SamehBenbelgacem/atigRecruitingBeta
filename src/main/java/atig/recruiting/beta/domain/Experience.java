package atig.recruiting.beta.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Experience.
 */
@Entity
@Table(name = "experience")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Experience implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "company")
    private String company;

    @Column(name = "company_site")
    private String companySite;

    @Column(name = "role")
    private String role;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "duration")
    private Double duration;

    @Column(name = "location")
    private String location;

    @Column(name = "tasks")
    private String tasks;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "experience")
    @JsonIgnoreProperties(value = { "toolExperience", "experience" }, allowSetters = true)
    private Set<Tool> tools = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
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
    private Candidate experienceCandidate;

    @ManyToOne(fetch = FetchType.LAZY)
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
    private Candidate candidate;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Experience id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompany() {
        return this.company;
    }

    public Experience company(String company) {
        this.setCompany(company);
        return this;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompanySite() {
        return this.companySite;
    }

    public Experience companySite(String companySite) {
        this.setCompanySite(companySite);
        return this;
    }

    public void setCompanySite(String companySite) {
        this.companySite = companySite;
    }

    public String getRole() {
        return this.role;
    }

    public Experience role(String role) {
        this.setRole(role);
        return this;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public Experience startDate(LocalDate startDate) {
        this.setStartDate(startDate);
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public Experience endDate(LocalDate endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Double getDuration() {
        return this.duration;
    }

    public Experience duration(Double duration) {
        this.setDuration(duration);
        return this;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public String getLocation() {
        return this.location;
    }

    public Experience location(String location) {
        this.setLocation(location);
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTasks() {
        return this.tasks;
    }

    public Experience tasks(String tasks) {
        this.setTasks(tasks);
        return this;
    }

    public void setTasks(String tasks) {
        this.tasks = tasks;
    }

    public Set<Tool> getTools() {
        return this.tools;
    }

    public void setTools(Set<Tool> tools) {
        if (this.tools != null) {
            this.tools.forEach(i -> i.setExperience(null));
        }
        if (tools != null) {
            tools.forEach(i -> i.setExperience(this));
        }
        this.tools = tools;
    }

    public Experience tools(Set<Tool> tools) {
        this.setTools(tools);
        return this;
    }

    public Experience addTools(Tool tool) {
        this.tools.add(tool);
        tool.setExperience(this);
        return this;
    }

    public Experience removeTools(Tool tool) {
        this.tools.remove(tool);
        tool.setExperience(null);
        return this;
    }

    public Candidate getExperienceCandidate() {
        return this.experienceCandidate;
    }

    public void setExperienceCandidate(Candidate candidate) {
        this.experienceCandidate = candidate;
    }

    public Experience experienceCandidate(Candidate candidate) {
        this.setExperienceCandidate(candidate);
        return this;
    }

    public Candidate getCandidate() {
        return this.candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public Experience candidate(Candidate candidate) {
        this.setCandidate(candidate);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Experience)) {
            return false;
        }
        return getId() != null && getId().equals(((Experience) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Experience{" +
            "id=" + getId() +
            ", company='" + getCompany() + "'" +
            ", companySite='" + getCompanySite() + "'" +
            ", role='" + getRole() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", duration=" + getDuration() +
            ", location='" + getLocation() + "'" +
            ", tasks='" + getTasks() + "'" +
            "}";
    }
}
