package atig.recruiting.beta.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link atig.recruiting.beta.domain.Education} entity. This class is used
 * in {@link atig.recruiting.beta.web.rest.EducationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /educations?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EducationCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter diploma;

    private StringFilter establishment;

    private StringFilter mention;

    private LocalDateFilter startDate;

    private LocalDateFilter endDate;

    private DoubleFilter duration;

    private StringFilter location;

    private LongFilter educationCandidateId;

    private LongFilter candidateId;

    private Boolean distinct;

    public EducationCriteria() {}

    public EducationCriteria(EducationCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.diploma = other.diploma == null ? null : other.diploma.copy();
        this.establishment = other.establishment == null ? null : other.establishment.copy();
        this.mention = other.mention == null ? null : other.mention.copy();
        this.startDate = other.startDate == null ? null : other.startDate.copy();
        this.endDate = other.endDate == null ? null : other.endDate.copy();
        this.duration = other.duration == null ? null : other.duration.copy();
        this.location = other.location == null ? null : other.location.copy();
        this.educationCandidateId = other.educationCandidateId == null ? null : other.educationCandidateId.copy();
        this.candidateId = other.candidateId == null ? null : other.candidateId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public EducationCriteria copy() {
        return new EducationCriteria(this);
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

    public StringFilter getDiploma() {
        return diploma;
    }

    public StringFilter diploma() {
        if (diploma == null) {
            diploma = new StringFilter();
        }
        return diploma;
    }

    public void setDiploma(StringFilter diploma) {
        this.diploma = diploma;
    }

    public StringFilter getEstablishment() {
        return establishment;
    }

    public StringFilter establishment() {
        if (establishment == null) {
            establishment = new StringFilter();
        }
        return establishment;
    }

    public void setEstablishment(StringFilter establishment) {
        this.establishment = establishment;
    }

    public StringFilter getMention() {
        return mention;
    }

    public StringFilter mention() {
        if (mention == null) {
            mention = new StringFilter();
        }
        return mention;
    }

    public void setMention(StringFilter mention) {
        this.mention = mention;
    }

    public LocalDateFilter getStartDate() {
        return startDate;
    }

    public LocalDateFilter startDate() {
        if (startDate == null) {
            startDate = new LocalDateFilter();
        }
        return startDate;
    }

    public void setStartDate(LocalDateFilter startDate) {
        this.startDate = startDate;
    }

    public LocalDateFilter getEndDate() {
        return endDate;
    }

    public LocalDateFilter endDate() {
        if (endDate == null) {
            endDate = new LocalDateFilter();
        }
        return endDate;
    }

    public void setEndDate(LocalDateFilter endDate) {
        this.endDate = endDate;
    }

    public DoubleFilter getDuration() {
        return duration;
    }

    public DoubleFilter duration() {
        if (duration == null) {
            duration = new DoubleFilter();
        }
        return duration;
    }

    public void setDuration(DoubleFilter duration) {
        this.duration = duration;
    }

    public StringFilter getLocation() {
        return location;
    }

    public StringFilter location() {
        if (location == null) {
            location = new StringFilter();
        }
        return location;
    }

    public void setLocation(StringFilter location) {
        this.location = location;
    }

    public LongFilter getEducationCandidateId() {
        return educationCandidateId;
    }

    public LongFilter educationCandidateId() {
        if (educationCandidateId == null) {
            educationCandidateId = new LongFilter();
        }
        return educationCandidateId;
    }

    public void setEducationCandidateId(LongFilter educationCandidateId) {
        this.educationCandidateId = educationCandidateId;
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
        final EducationCriteria that = (EducationCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(diploma, that.diploma) &&
            Objects.equals(establishment, that.establishment) &&
            Objects.equals(mention, that.mention) &&
            Objects.equals(startDate, that.startDate) &&
            Objects.equals(endDate, that.endDate) &&
            Objects.equals(duration, that.duration) &&
            Objects.equals(location, that.location) &&
            Objects.equals(educationCandidateId, that.educationCandidateId) &&
            Objects.equals(candidateId, that.candidateId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            diploma,
            establishment,
            mention,
            startDate,
            endDate,
            duration,
            location,
            educationCandidateId,
            candidateId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EducationCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (diploma != null ? "diploma=" + diploma + ", " : "") +
            (establishment != null ? "establishment=" + establishment + ", " : "") +
            (mention != null ? "mention=" + mention + ", " : "") +
            (startDate != null ? "startDate=" + startDate + ", " : "") +
            (endDate != null ? "endDate=" + endDate + ", " : "") +
            (duration != null ? "duration=" + duration + ", " : "") +
            (location != null ? "location=" + location + ", " : "") +
            (educationCandidateId != null ? "educationCandidateId=" + educationCandidateId + ", " : "") +
            (candidateId != null ? "candidateId=" + candidateId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
