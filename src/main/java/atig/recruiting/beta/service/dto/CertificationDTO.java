package atig.recruiting.beta.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link atig.recruiting.beta.domain.Certification} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CertificationDTO implements Serializable {

    private Long id;

    private String title;

    private LocalDate date;

    private CandidateDTO certificationCandidate;

    private CandidateDTO candidate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public CandidateDTO getCertificationCandidate() {
        return certificationCandidate;
    }

    public void setCertificationCandidate(CandidateDTO certificationCandidate) {
        this.certificationCandidate = certificationCandidate;
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
        if (!(o instanceof CertificationDTO)) {
            return false;
        }

        CertificationDTO certificationDTO = (CertificationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, certificationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CertificationDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", date='" + getDate() + "'" +
            ", certificationCandidate=" + getCertificationCandidate() +
            ", candidate=" + getCandidate() +
            "}";
    }
}
