package atig.recruiting.beta.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link atig.recruiting.beta.domain.Skill} entity. This class is used
 * in {@link atig.recruiting.beta.web.rest.SkillResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /skills?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SkillCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter title;

    private LongFilter skillCandidateId;

    private LongFilter candidateId;

    private Boolean distinct;

    public SkillCriteria() {}

    public SkillCriteria(SkillCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.skillCandidateId = other.skillCandidateId == null ? null : other.skillCandidateId.copy();
        this.candidateId = other.candidateId == null ? null : other.candidateId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public SkillCriteria copy() {
        return new SkillCriteria(this);
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

    public StringFilter getTitle() {
        return title;
    }

    public StringFilter title() {
        if (title == null) {
            title = new StringFilter();
        }
        return title;
    }

    public void setTitle(StringFilter title) {
        this.title = title;
    }

    public LongFilter getSkillCandidateId() {
        return skillCandidateId;
    }

    public LongFilter skillCandidateId() {
        if (skillCandidateId == null) {
            skillCandidateId = new LongFilter();
        }
        return skillCandidateId;
    }

    public void setSkillCandidateId(LongFilter skillCandidateId) {
        this.skillCandidateId = skillCandidateId;
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
        final SkillCriteria that = (SkillCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(skillCandidateId, that.skillCandidateId) &&
            Objects.equals(candidateId, that.candidateId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, skillCandidateId, candidateId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SkillCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (title != null ? "title=" + title + ", " : "") +
            (skillCandidateId != null ? "skillCandidateId=" + skillCandidateId + ", " : "") +
            (candidateId != null ? "candidateId=" + candidateId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
