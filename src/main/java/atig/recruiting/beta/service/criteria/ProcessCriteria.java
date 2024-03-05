package atig.recruiting.beta.service.criteria;

import atig.recruiting.beta.domain.enumeration.EnumProsessType;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link atig.recruiting.beta.domain.Process} entity. This class is used
 * in {@link atig.recruiting.beta.web.rest.ProcessResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /processes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProcessCriteria implements Serializable, Criteria {

    /**
     * Class for filtering EnumProsessType
     */
    public static class EnumProsessTypeFilter extends Filter<EnumProsessType> {

        public EnumProsessTypeFilter() {}

        public EnumProsessTypeFilter(EnumProsessTypeFilter filter) {
            super(filter);
        }

        @Override
        public EnumProsessTypeFilter copy() {
            return new EnumProsessTypeFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter title;

    private EnumProsessTypeFilter type;

    private LongFilter stepsId;

    private LongFilter candidatesId;

    private LongFilter companiesId;

    private Boolean distinct;

    public ProcessCriteria() {}

    public ProcessCriteria(ProcessCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.stepsId = other.stepsId == null ? null : other.stepsId.copy();
        this.candidatesId = other.candidatesId == null ? null : other.candidatesId.copy();
        this.companiesId = other.companiesId == null ? null : other.companiesId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ProcessCriteria copy() {
        return new ProcessCriteria(this);
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

    public EnumProsessTypeFilter getType() {
        return type;
    }

    public EnumProsessTypeFilter type() {
        if (type == null) {
            type = new EnumProsessTypeFilter();
        }
        return type;
    }

    public void setType(EnumProsessTypeFilter type) {
        this.type = type;
    }

    public LongFilter getStepsId() {
        return stepsId;
    }

    public LongFilter stepsId() {
        if (stepsId == null) {
            stepsId = new LongFilter();
        }
        return stepsId;
    }

    public void setStepsId(LongFilter stepsId) {
        this.stepsId = stepsId;
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
        final ProcessCriteria that = (ProcessCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(type, that.type) &&
            Objects.equals(stepsId, that.stepsId) &&
            Objects.equals(candidatesId, that.candidatesId) &&
            Objects.equals(companiesId, that.companiesId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, type, stepsId, candidatesId, companiesId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProcessCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (title != null ? "title=" + title + ", " : "") +
            (type != null ? "type=" + type + ", " : "") +
            (stepsId != null ? "stepsId=" + stepsId + ", " : "") +
            (candidatesId != null ? "candidatesId=" + candidatesId + ", " : "") +
            (companiesId != null ? "companiesId=" + companiesId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
