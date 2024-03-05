package atig.recruiting.beta.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link atig.recruiting.beta.domain.SubCategory} entity. This class is used
 * in {@link atig.recruiting.beta.web.rest.SubCategoryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /sub-categories?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SubCategoryCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter title;

    private LongFilter candidatesId;

    private LongFilter companiesId;

    private LongFilter subCategoryCategoryId;

    private LongFilter categoryId;

    private Boolean distinct;

    public SubCategoryCriteria() {}

    public SubCategoryCriteria(SubCategoryCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.candidatesId = other.candidatesId == null ? null : other.candidatesId.copy();
        this.companiesId = other.companiesId == null ? null : other.companiesId.copy();
        this.subCategoryCategoryId = other.subCategoryCategoryId == null ? null : other.subCategoryCategoryId.copy();
        this.categoryId = other.categoryId == null ? null : other.categoryId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public SubCategoryCriteria copy() {
        return new SubCategoryCriteria(this);
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

    public LongFilter getSubCategoryCategoryId() {
        return subCategoryCategoryId;
    }

    public LongFilter subCategoryCategoryId() {
        if (subCategoryCategoryId == null) {
            subCategoryCategoryId = new LongFilter();
        }
        return subCategoryCategoryId;
    }

    public void setSubCategoryCategoryId(LongFilter subCategoryCategoryId) {
        this.subCategoryCategoryId = subCategoryCategoryId;
    }

    public LongFilter getCategoryId() {
        return categoryId;
    }

    public LongFilter categoryId() {
        if (categoryId == null) {
            categoryId = new LongFilter();
        }
        return categoryId;
    }

    public void setCategoryId(LongFilter categoryId) {
        this.categoryId = categoryId;
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
        final SubCategoryCriteria that = (SubCategoryCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(candidatesId, that.candidatesId) &&
            Objects.equals(companiesId, that.companiesId) &&
            Objects.equals(subCategoryCategoryId, that.subCategoryCategoryId) &&
            Objects.equals(categoryId, that.categoryId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, candidatesId, companiesId, subCategoryCategoryId, categoryId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SubCategoryCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (title != null ? "title=" + title + ", " : "") +
            (candidatesId != null ? "candidatesId=" + candidatesId + ", " : "") +
            (companiesId != null ? "companiesId=" + companiesId + ", " : "") +
            (subCategoryCategoryId != null ? "subCategoryCategoryId=" + subCategoryCategoryId + ", " : "") +
            (categoryId != null ? "categoryId=" + categoryId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
