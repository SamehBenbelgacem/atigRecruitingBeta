package atig.recruiting.beta.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link atig.recruiting.beta.domain.Note} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NoteDTO implements Serializable {

    private Long id;

    private String title;

    private Instant date;

    private String description;

    private CompanyDTO noteCompany;

    private CandidateDTO noteCandidate;

    private CandidateDTO candidate;

    private CompanyDTO company;

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

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CompanyDTO getNoteCompany() {
        return noteCompany;
    }

    public void setNoteCompany(CompanyDTO noteCompany) {
        this.noteCompany = noteCompany;
    }

    public CandidateDTO getNoteCandidate() {
        return noteCandidate;
    }

    public void setNoteCandidate(CandidateDTO noteCandidate) {
        this.noteCandidate = noteCandidate;
    }

    public CandidateDTO getCandidate() {
        return candidate;
    }

    public void setCandidate(CandidateDTO candidate) {
        this.candidate = candidate;
    }

    public CompanyDTO getCompany() {
        return company;
    }

    public void setCompany(CompanyDTO company) {
        this.company = company;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NoteDTO)) {
            return false;
        }

        NoteDTO noteDTO = (NoteDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, noteDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NoteDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", date='" + getDate() + "'" +
            ", description='" + getDescription() + "'" +
            ", noteCompany=" + getNoteCompany() +
            ", noteCandidate=" + getNoteCandidate() +
            ", candidate=" + getCandidate() +
            ", company=" + getCompany() +
            "}";
    }
}
