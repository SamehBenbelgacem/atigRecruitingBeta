package atig.recruiting.beta.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link atig.recruiting.beta.domain.Tag} entity. This class is used
 * in {@link atig.recruiting.beta.web.rest.TagResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /tags?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TagCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter title;

    private LongFilter companiesId;

    private LongFilter offersId;

    private LongFilter candidatesId;

    private Boolean distinct;

    public TagCriteria() {}

    public TagCriteria(TagCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.companiesId = other.companiesId == null ? null : other.companiesId.copy();
        this.offersId = other.offersId == null ? null : other.offersId.copy();
        this.candidatesId = other.candidatesId == null ? null : other.candidatesId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public TagCriteria copy() {
        return new TagCriteria(this);
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

    public LongFilter getOffersId() {
        return offersId;
    }

    public LongFilter offersId() {
        if (offersId == null) {
            offersId = new LongFilter();
        }
        return offersId;
    }

    public void setOffersId(LongFilter offersId) {
        this.offersId = offersId;
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
        final TagCriteria that = (TagCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(companiesId, that.companiesId) &&
            Objects.equals(offersId, that.offersId) &&
            Objects.equals(candidatesId, that.candidatesId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, companiesId, offersId, candidatesId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TagCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (title != null ? "title=" + title + ", " : "") +
            (companiesId != null ? "companiesId=" + companiesId + ", " : "") +
            (offersId != null ? "offersId=" + offersId + ", " : "") +
            (candidatesId != null ? "candidatesId=" + candidatesId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
