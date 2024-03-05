package atig.recruiting.beta.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link atig.recruiting.beta.domain.Experience} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ExperienceDTO implements Serializable {

    private Long id;

    private String company;

    private String companySite;

    private String role;

    private LocalDate startDate;

    private LocalDate endDate;

    private Double duration;

    private String location;

    private String tasks;

    private CandidateDTO experienceCandidate;

    private CandidateDTO candidate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompanySite() {
        return companySite;
    }

    public void setCompanySite(String companySite) {
        this.companySite = companySite;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTasks() {
        return tasks;
    }

    public void setTasks(String tasks) {
        this.tasks = tasks;
    }

    public CandidateDTO getExperienceCandidate() {
        return experienceCandidate;
    }

    public void setExperienceCandidate(CandidateDTO experienceCandidate) {
        this.experienceCandidate = experienceCandidate;
    }

    public CandidateDTO getCandidate() {
        return candidate;
    }

    public void setCandidate(CandidateDTO candidate) {
        this.candidate = candidate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExperienceDTO)) {
            return false;
        }

        ExperienceDTO experienceDTO = (ExperienceDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, experienceDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ExperienceDTO{" +
            "id=" + getId() +
            ", company='" + getCompany() + "'" +
            ", companySite='" + getCompanySite() + "'" +
            ", role='" + getRole() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", duration=" + getDuration() +
            ", location='" + getLocation() + "'" +
            ", tasks='" + getTasks() + "'" +
            ", experienceCandidate=" + getExperienceCandidate() +
            ", candidate=" + getCandidate() +
            "}";
    }
}
