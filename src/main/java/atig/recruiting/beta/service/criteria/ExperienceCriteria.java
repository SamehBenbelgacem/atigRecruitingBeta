package atig.recruiting.beta.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link atig.recruiting.beta.domain.Experience} entity. This class is used
 * in {@link atig.recruiting.beta.web.rest.ExperienceResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /experiences?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ExperienceCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter company;

    private StringFilter companySite;

    private StringFilter role;

    private LocalDateFilter startDate;

    private LocalDateFilter endDate;

    private DoubleFilter duration;

    private StringFilter location;

    private StringFilter tasks;

    private LongFilter toolsId;

    private LongFilter experienceCandidateId;

    private LongFilter candidateId;

    private Boolean distinct;

    public ExperienceCriteria() {}

    public ExperienceCriteria(ExperienceCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.company = other.company == null ? null : other.company.copy();
        this.companySite = other.companySite == null ? null : other.companySite.copy();
        this.role = other.role == null ? null : other.role.copy();
        this.startDate = other.startDate == null ? null : other.startDate.copy();
        this.endDate = other.endDate == null ? null : other.endDate.copy();
        this.duration = other.duration == null ? null : other.duration.copy();
        this.location = other.location == null ? null : other.location.copy();
        this.tasks = other.tasks == null ? null : other.tasks.copy();
        this.toolsId = other.toolsId == null ? null : other.toolsId.copy();
        this.experienceCandidateId = other.experienceCandidateId == null ? null : other.experienceCandidateId.copy();
        this.candidateId = other.candidateId == null ? null : other.candidateId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ExperienceCriteria copy() {
        return new ExperienceCriteria(this);
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

    public StringFilter getCompany() {
        return company;
    }

    public StringFilter company() {
        if (company == null) {
            company = new StringFilter();
        }
        return company;
    }

    public void setCompany(StringFilter company) {
        this.company = company;
    }

    public StringFilter getCompanySite() {
        return companySite;
    }

    public StringFilter companySite() {
        if (companySite == null) {
            companySite = new StringFilter();
        }
        return companySite;
    }

    public void setCompanySite(StringFilter companySite) {
        this.companySite = companySite;
    }

    public StringFilter getRole() {
        return role;
    }

    public StringFilter role() {
        if (role == null) {
            role = new StringFilter();
        }
        return role;
    }

    public void setRole(StringFilter role) {
        this.role = role;
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

    public StringFilter getTasks() {
        return tasks;
    }

    public StringFilter tasks() {
        if (tasks == null) {
            tasks = new StringFilter();
        }
        return tasks;
    }

    public void setTasks(StringFilter tasks) {
        this.tasks = tasks;
    }

    public LongFilter getToolsId() {
        return toolsId;
    }

    public LongFilter toolsId() {
        if (toolsId == null) {
            toolsId = new LongFilter();
        }
        return toolsId;
    }

    public void setToolsId(LongFilter toolsId) {
        this.toolsId = toolsId;
    }

    public LongFilter getExperienceCandidateId() {
        return experienceCandidateId;
    }

    public LongFilter experienceCandidateId() {
        if (experienceCandidateId == null) {
            experienceCandidateId = new LongFilter();
        }
        return experienceCandidateId;
    }

    public void setExperienceCandidateId(LongFilter experienceCandidateId) {
        this.experienceCandidateId = experienceCandidateId;
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
        final ExperienceCriteria that = (ExperienceCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(company, that.company) &&
            Objects.equals(companySite, that.companySite) &&
            Objects.equals(role, that.role) &&
            Objects.equals(startDate, that.startDate) &&
            Objects.equals(endDate, that.endDate) &&
            Objects.equals(duration, that.duration) &&
            Objects.equals(location, that.location) &&
            Objects.equals(tasks, that.tasks) &&
            Objects.equals(toolsId, that.toolsId) &&
            Objects.equals(experienceCandidateId, that.experienceCandidateId) &&
            Objects.equals(candidateId, that.candidateId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            company,
            companySite,
            role,
            startDate,
            endDate,
            duration,
            location,
            tasks,
            toolsId,
            experienceCandidateId,
            candidateId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ExperienceCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (company != null ? "company=" + company + ", " : "") +
            (companySite != null ? "companySite=" + companySite + ", " : "") +
            (role != null ? "role=" + role + ", " : "") +
            (startDate != null ? "startDate=" + startDate + ", " : "") +
            (endDate != null ? "endDate=" + endDate + ", " : "") +
            (duration != null ? "duration=" + duration + ", " : "") +
            (location != null ? "location=" + location + ", " : "") +
            (tasks != null ? "tasks=" + tasks + ", " : "") +
            (toolsId != null ? "toolsId=" + toolsId + ", " : "") +
            (experienceCandidateId != null ? "experienceCandidateId=" + experienceCandidateId + ", " : "") +
            (candidateId != null ? "candidateId=" + candidateId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
