package atig.recruiting.beta.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Education.
 */
@Entity
@Table(name = "education")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Education implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "diploma")
    private String diploma;

    @Column(name = "establishment")
    private String establishment;

    @Column(name = "mention")
    private String mention;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "duration")
    private Double duration;

    @Column(name = "location")
    private String location;

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
    private Candidate educationCandidate;

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

    public Education id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDiploma() {
        return this.diploma;
    }

    public Education diploma(String diploma) {
        this.setDiploma(diploma);
        return this;
    }

    public void setDiploma(String diploma) {
        this.diploma = diploma;
    }

    public String getEstablishment() {
        return this.establishment;
    }

    public Education establishment(String establishment) {
        this.setEstablishment(establishment);
        return this;
    }

    public void setEstablishment(String establishment) {
        this.establishment = establishment;
    }

    public String getMention() {
        return this.mention;
    }

    public Education mention(String mention) {
        this.setMention(mention);
        return this;
    }

    public void setMention(String mention) {
        this.mention = mention;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public Education startDate(LocalDate startDate) {
        this.setStartDate(startDate);
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public Education endDate(LocalDate endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Double getDuration() {
        return this.duration;
    }

    public Education duration(Double duration) {
        this.setDuration(duration);
        return this;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public String getLocation() {
        return this.location;
    }

    public Education location(String location) {
        this.setLocation(location);
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Candidate getEducationCandidate() {
        return this.educationCandidate;
    }

    public void setEducationCandidate(Candidate candidate) {
        this.educationCandidate = candidate;
    }

    public Education educationCandidate(Candidate candidate) {
        this.setEducationCandidate(candidate);
        return this;
    }

    public Candidate getCandidate() {
        return this.candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public Education candidate(Candidate candidate) {
        this.setCandidate(candidate);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Education)) {
            return false;
        }
        return getId() != null && getId().equals(((Education) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Education{" +
            "id=" + getId() +
            ", diploma='" + getDiploma() + "'" +
            ", establishment='" + getEstablishment() + "'" +
            ", mention='" + getMention() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", duration=" + getDuration() +
            ", location='" + getLocation() + "'" +
            "}";
    }
}
