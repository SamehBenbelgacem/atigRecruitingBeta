package atig.recruiting.beta.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Certification.
 */
@Entity
@Table(name = "certification")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Certification implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "date")
    private LocalDate date;

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
    private Candidate certificationCandidate;

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

    public Certification id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Certification title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public Certification date(LocalDate date) {
        this.setDate(date);
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Candidate getCertificationCandidate() {
        return this.certificationCandidate;
    }

    public void setCertificationCandidate(Candidate candidate) {
        this.certificationCandidate = candidate;
    }

    public Certification certificationCandidate(Candidate candidate) {
        this.setCertificationCandidate(candidate);
        return this;
    }

    public Candidate getCandidate() {
        return this.candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public Certification candidate(Candidate candidate) {
        this.setCandidate(candidate);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Certification)) {
            return false;
        }
        return getId() != null && getId().equals(((Certification) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Certification{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }
}
