package atig.recruiting.beta.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link atig.recruiting.beta.domain.Note} entity. This class is used
 * in {@link atig.recruiting.beta.web.rest.NoteResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /notes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NoteCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter title;

    private InstantFilter date;

    private StringFilter description;

    private LongFilter documentsId;

    private LongFilter noteCompanyId;

    private LongFilter noteCandidateId;

    private LongFilter candidateId;

    private LongFilter companyId;

    private Boolean distinct;

    public NoteCriteria() {}

    public NoteCriteria(NoteCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.date = other.date == null ? null : other.date.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.documentsId = other.documentsId == null ? null : other.documentsId.copy();
        this.noteCompanyId = other.noteCompanyId == null ? null : other.noteCompanyId.copy();
        this.noteCandidateId = other.noteCandidateId == null ? null : other.noteCandidateId.copy();
        this.candidateId = other.candidateId == null ? null : other.candidateId.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public NoteCriteria copy() {
        return new NoteCriteria(this);
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

    public InstantFilter getDate() {
        return date;
    }

    public InstantFilter date() {
        if (date == null) {
            date = new InstantFilter();
        }
        return date;
    }

    public void setDate(InstantFilter date) {
        this.date = date;
    }

    public StringFilter getDescription() {
        return description;
    }

    public StringFilter description() {
        if (description == null) {
            description = new StringFilter();
        }
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public LongFilter getDocumentsId() {
        return documentsId;
    }

    public LongFilter documentsId() {
        if (documentsId == null) {
            documentsId = new LongFilter();
        }
        return documentsId;
    }

    public void setDocumentsId(LongFilter documentsId) {
        this.documentsId = documentsId;
    }

    public LongFilter getNoteCompanyId() {
        return noteCompanyId;
    }

    public LongFilter noteCompanyId() {
        if (noteCompanyId == null) {
            noteCompanyId = new LongFilter();
        }
        return noteCompanyId;
    }

    public void setNoteCompanyId(LongFilter noteCompanyId) {
        this.noteCompanyId = noteCompanyId;
    }

    public LongFilter getNoteCandidateId() {
        return noteCandidateId;
    }

    public LongFilter noteCandidateId() {
        if (noteCandidateId == null) {
            noteCandidateId = new LongFilter();
        }
        return noteCandidateId;
    }

    public void setNoteCandidateId(LongFilter noteCandidateId) {
        this.noteCandidateId = noteCandidateId;
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

    public LongFilter getCompanyId() {
        return companyId;
    }

    public LongFilter companyId() {
        if (companyId == null) {
            companyId = new LongFilter();
        }
        return companyId;
    }

    public void setCompanyId(LongFilter companyId) {
        this.companyId = companyId;
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
        final NoteCriteria that = (NoteCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(date, that.date) &&
            Objects.equals(description, that.description) &&
            Objects.equals(documentsId, that.documentsId) &&
            Objects.equals(noteCompanyId, that.noteCompanyId) &&
            Objects.equals(noteCandidateId, that.noteCandidateId) &&
            Objects.equals(candidateId, that.candidateId) &&
            Objects.equals(companyId, that.companyId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, date, description, documentsId, noteCompanyId, noteCandidateId, candidateId, companyId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NoteCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (title != null ? "title=" + title + ", " : "") +
            (date != null ? "date=" + date + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (documentsId != null ? "documentsId=" + documentsId + ", " : "") +
            (noteCompanyId != null ? "noteCompanyId=" + noteCompanyId + ", " : "") +
            (noteCandidateId != null ? "noteCandidateId=" + noteCandidateId + ", " : "") +
            (candidateId != null ? "candidateId=" + candidateId + ", " : "") +
            (companyId != null ? "companyId=" + companyId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
