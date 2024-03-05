package atig.recruiting.beta.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link atig.recruiting.beta.domain.Certification} entity. This class is used
 * in {@link atig.recruiting.beta.web.rest.CertificationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /certifications?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CertificationCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter title;

    private LocalDateFilter date;

    private LongFilter certificationCandidateId;

    private LongFilter candidateId;

    private Boolean distinct;

    public CertificationCriteria() {}

    public CertificationCriteria(CertificationCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.date = other.date == null ? null : other.date.copy();
        this.certificationCandidateId = other.certificationCandidateId == null ? null : other.certificationCandidateId.copy();
        this.candidateId = other.candidateId == null ? null : other.candidateId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public CertificationCriteria copy() {
        return new CertificationCriteria(this);
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

    public LocalDateFilter getDate() {
        return date;
    }

    public LocalDateFilter date() {
        if (date == null) {
            date = new LocalDateFilter();
        }
        return date;
    }

    public void setDate(LocalDateFilter date) {
        this.date = date;
    }

    public LongFilter getCertificationCandidateId() {
        return certificationCandidateId;
    }

    public LongFilter certificationCandidateId() {
        if (certificationCandidateId == null) {
            certificationCandidateId = new LongFilter();
        }
        return certificationCandidateId;
    }

    public void setCertificationCandidateId(LongFilter certificationCandidateId) {
        this.certificationCandidateId = certificationCandidateId;
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
        final CertificationCriteria that = (CertificationCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(date, that.date) &&
            Objects.equals(certificationCandidateId, that.certificationCandidateId) &&
            Objects.equals(candidateId, that.candidateId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, date, certificationCandidateId, candidateId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CertificationCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (title != null ? "title=" + title + ", " : "") +
            (date != null ? "date=" + date + ", " : "") +
            (certificationCandidateId != null ? "certificationCandidateId=" + certificationCandidateId + ", " : "") +
            (candidateId != null ? "candidateId=" + candidateId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
