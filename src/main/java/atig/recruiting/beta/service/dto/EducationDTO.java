package atig.recruiting.beta.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link atig.recruiting.beta.domain.Education} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EducationDTO implements Serializable {

    private Long id;

    private String diploma;

    private String establishment;

    private String mention;

    private LocalDate startDate;

    private LocalDate endDate;

    private Double duration;

    private String location;

    private CandidateDTO educationCandidate;

    private CandidateDTO candidate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDiploma() {
        return diploma;
    }

    public void setDiploma(String diploma) {
        this.diploma = diploma;
    }

    public String getEstablishment() {
        return establishment;
    }

    public void setEstablishment(String establishment) {
        this.establishment = establishment;
    }

    public String getMention() {
        return mention;
    }

    public void setMention(String mention) {
        this.mention = mention;
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

    public CandidateDTO getEducationCandidate() {
        return educationCandidate;
    }

    public void setEducationCandidate(CandidateDTO educationCandidate) {
        this.educationCandidate = educationCandidate;
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
        if (!(o instanceof EducationDTO)) {
            return false;
        }

        EducationDTO educationDTO = (EducationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, educationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EducationDTO{" +
            "id=" + getId() +
            ", diploma='" + getDiploma() + "'" +
            ", establishment='" + getEstablishment() + "'" +
            ", mention='" + getMention() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", duration=" + getDuration() +
            ", location='" + getLocation() + "'" +
            ", educationCandidate=" + getEducationCandidate() +
            ", candidate=" + getCandidate() +
            "}";
    }
}
