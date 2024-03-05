package atig.recruiting.beta.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link atig.recruiting.beta.domain.Skill} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SkillDTO implements Serializable {

    private Long id;

    private String title;

    private CandidateDTO skillCandidate;

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

    public CandidateDTO getSkillCandidate() {
        return skillCandidate;
    }

    public void setSkillCandidate(CandidateDTO skillCandidate) {
        this.skillCandidate = skillCandidate;
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
        if (!(o instanceof SkillDTO)) {
            return false;
        }

        SkillDTO skillDTO = (SkillDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, skillDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SkillDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", skillCandidate=" + getSkillCandidate() +
            ", candidate=" + getCandidate() +
            "}";
    }
}
