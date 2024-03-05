package atig.recruiting.beta.service.dto;

import atig.recruiting.beta.domain.enumeration.EnumLanguageLevel;
import atig.recruiting.beta.domain.enumeration.EnumLanguageName;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link atig.recruiting.beta.domain.Language} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LanguageDTO implements Serializable {

    private Long id;

    private EnumLanguageName name;

    private EnumLanguageLevel level;

    private CandidateDTO languageCandidate;

    private CandidateDTO candidate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EnumLanguageName getName() {
        return name;
    }

    public void setName(EnumLanguageName name) {
        this.name = name;
    }

    public EnumLanguageLevel getLevel() {
        return level;
    }

    public void setLevel(EnumLanguageLevel level) {
        this.level = level;
    }

    public CandidateDTO getLanguageCandidate() {
        return languageCandidate;
    }

    public void setLanguageCandidate(CandidateDTO languageCandidate) {
        this.languageCandidate = languageCandidate;
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
        if (!(o instanceof LanguageDTO)) {
            return false;
        }

        LanguageDTO languageDTO = (LanguageDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, languageDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LanguageDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", level='" + getLevel() + "'" +
            ", languageCandidate=" + getLanguageCandidate() +
            ", candidate=" + getCandidate() +
            "}";
    }
}
