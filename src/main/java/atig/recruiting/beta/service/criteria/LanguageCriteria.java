package atig.recruiting.beta.service.criteria;

import atig.recruiting.beta.domain.enumeration.EnumLanguageLevel;
import atig.recruiting.beta.domain.enumeration.EnumLanguageName;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link atig.recruiting.beta.domain.Language} entity. This class is used
 * in {@link atig.recruiting.beta.web.rest.LanguageResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /languages?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LanguageCriteria implements Serializable, Criteria {

    /**
     * Class for filtering EnumLanguageName
     */
    public static class EnumLanguageNameFilter extends Filter<EnumLanguageName> {

        public EnumLanguageNameFilter() {}

        public EnumLanguageNameFilter(EnumLanguageNameFilter filter) {
            super(filter);
        }

        @Override
        public EnumLanguageNameFilter copy() {
            return new EnumLanguageNameFilter(this);
        }
    }

    /**
     * Class for filtering EnumLanguageLevel
     */
    public static class EnumLanguageLevelFilter extends Filter<EnumLanguageLevel> {

        public EnumLanguageLevelFilter() {}

        public EnumLanguageLevelFilter(EnumLanguageLevelFilter filter) {
            super(filter);
        }

        @Override
        public EnumLanguageLevelFilter copy() {
            return new EnumLanguageLevelFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private EnumLanguageNameFilter name;

    private EnumLanguageLevelFilter level;

    private LongFilter languageCandidateId;

    private LongFilter candidateId;

    private Boolean distinct;

    public LanguageCriteria() {}

    public LanguageCriteria(LanguageCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.level = other.level == null ? null : other.level.copy();
        this.languageCandidateId = other.languageCandidateId == null ? null : other.languageCandidateId.copy();
        this.candidateId = other.candidateId == null ? null : other.candidateId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public LanguageCriteria copy() {
        return new LanguageCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public EnumLanguageNameFilter getName() {
        return name;
    }

    public EnumLanguageNameFilter name() {
        if (name == null) {
            name = new EnumLanguageNameFilter();
        }
        return name;
    }

    public void setName(EnumLanguageNameFilter name) {
        this.name = name;
    }

    public EnumLanguageLevelFilter getLevel() {
        return level;
    }

    public EnumLanguageLevelFilter level() {
        if (level == null) {
            level = new EnumLanguageLevelFilter();
        }
        return level;
    }

    public void setLevel(EnumLanguageLevelFilter level) {
        this.level = level;
    }

    public LongFilter getLanguageCandidateId() {
        return languageCandidateId;
    }

    public LongFilter languageCandidateId() {
        if (languageCandidateId == null) {
            languageCandidateId = new LongFilter();
        }
        return languageCandidateId;
    }

    public void setLanguageCandidateId(LongFilter languageCandidateId) {
        this.languageCandidateId = languageCandidateId;
    }

    public LongFilter getCandidateId() {
        return candidateId;
    }

    public LongFilter candidateId() {
        if (candidateId == null) {
            candidateId = new LongFilter();
        }
        return candidateId;
    }

    public void setCandidateId(LongFilter candidateId) {
        this.candidateId = candidateId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final LanguageCriteria that = (LanguageCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(level, that.level) &&
            Objects.equals(languageCandidateId, that.languageCandidateId) &&
            Objects.equals(candidateId, that.candidateId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, level, languageCandidateId, candidateId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LanguageCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (level != null ? "level=" + level + ", " : "") +
            (languageCandidateId != null ? "languageCandidateId=" + languageCandidateId + ", " : "") +
            (candidateId != null ? "candidateId=" + candidateId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
