package atig.recruiting.beta.service.criteria;

import atig.recruiting.beta.domain.enumeration.EnumPriority;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link atig.recruiting.beta.domain.ProcessStep} entity. This class is used
 * in {@link atig.recruiting.beta.web.rest.ProcessStepResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /process-steps?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProcessStepCriteria implements Serializable, Criteria {

    /**
     * Class for filtering EnumPriority
     */
    public static class EnumPriorityFilter extends Filter<EnumPriority> {

        public EnumPriorityFilter() {}

        public EnumPriorityFilter(EnumPriorityFilter filter) {
            super(filter);
        }

        @Override
        public EnumPriorityFilter copy() {
            return new EnumPriorityFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter title;

    private StringFilter order;

    private EnumPriorityFilter priority;

    private LongFilter candidatesId;

    private LongFilter companiesId;

    private LongFilter processStepProcessId;

    private LongFilter processId;

    private Boolean distinct;

    public ProcessStepCriteria() {}

    public ProcessStepCriteria(ProcessStepCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.order = other.order == null ? null : other.order.copy();
        this.priority = other.priority == null ? null : other.priority.copy();
        this.candidatesId = other.candidatesId == null ? null : other.candidatesId.copy();
        this.companiesId = other.companiesId == null ? null : other.companiesId.copy();
        this.processStepProcessId = other.processStepProcessId == null ? null : other.processStepProcessId.copy();
        this.processId = other.processId == null ? null : other.processId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ProcessStepCriteria copy() {
        return new ProcessStepCriteria(this);
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

    public StringFilter getOrder() {
        return order;
    }

    public StringFilter order() {
        if (order == null) {
            order = new StringFilter();
        }
        return order;
    }

    public void setOrder(StringFilter order) {
        this.order = order;
    }

    public EnumPriorityFilter getPriority() {
        return priority;
    }

    public EnumPriorityFilter priority() {
        if (priority == null) {
            priority = new EnumPriorityFilter();
        }
        return priority;
    }

    public void setPriority(EnumPriorityFilter priority) {
        this.priority = priority;
    }

    public LongFilter getCandidatesId() {
        return candidatesId;
    }

    public LongFilter candidatesId() {
        if (candidatesId == null) {
            candidatesId = new LongFilter();
        }
        return candidatesId;
    }

    public void setCandidatesId(LongFilter candidatesId) {
        this.candidatesId = candidatesId;
    }

    public LongFilter getCompaniesId() {
        return companiesId;
    }

    public LongFilter companiesId() {
        if (companiesId == null) {
            companiesId = new LongFilter();
        }
        return companiesId;
    }

    public void setCompaniesId(LongFilter companiesId) {
        this.companiesId = companiesId;
    }

    public LongFilter getProcessStepProcessId() {
        return processStepProcessId;
    }

    public LongFilter processStepProcessId() {
        if (processStepProcessId == null) {
            processStepProcessId = new LongFilter();
        }
        return processStepProcessId;
    }

    public void setProcessStepProcessId(LongFilter processStepProcessId) {
        this.processStepProcessId = processStepProcessId;
    }

    public LongFilter getProcessId() {
        return processId;
    }

    public LongFilter processId() {
        if (processId == null) {
            processId = new LongFilter();
        }
        return processId;
    }

    public void setProcessId(LongFilter processId) {
        this.processId = processId;
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
        final ProcessStepCriteria that = (ProcessStepCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(order, that.order) &&
            Objects.equals(priority, that.priority) &&
            Objects.equals(candidatesId, that.candidatesId) &&
            Objects.equals(companiesId, that.companiesId) &&
            Objects.equals(processStepProcessId, that.processStepProcessId) &&
            Objects.equals(processId, that.processId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, order, priority, candidatesId, companiesId, processStepProcessId, processId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProcessStepCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (title != null ? "title=" + title + ", " : "") +
            (order != null ? "order=" + order + ", " : "") +
            (priority != null ? "priority=" + priority + ", " : "") +
            (candidatesId != null ? "candidatesId=" + candidatesId + ", " : "") +
            (companiesId != null ? "companiesId=" + companiesId + ", " : "") +
            (processStepProcessId != null ? "processStepProcessId=" + processStepProcessId + ", " : "") +
            (processId != null ? "processId=" + processId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
