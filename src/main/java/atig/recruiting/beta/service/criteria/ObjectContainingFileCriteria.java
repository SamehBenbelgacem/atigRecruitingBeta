package atig.recruiting.beta.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link atig.recruiting.beta.domain.ObjectContainingFile} entity. This class is used
 * in {@link atig.recruiting.beta.web.rest.ObjectContainingFileResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /object-containing-files?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ObjectContainingFileCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter candidateDocsId;

    private LongFilter noteDocsId;

    private LongFilter emailDocsId;

    private LongFilter candidateAdditionalInfosId;

    private LongFilter noteId;

    private LongFilter emailId;

    private Boolean distinct;

    public ObjectContainingFileCriteria() {}

    public ObjectContainingFileCriteria(ObjectContainingFileCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.candidateDocsId = other.candidateDocsId == null ? null : other.candidateDocsId.copy();
        this.noteDocsId = other.noteDocsId == null ? null : other.noteDocsId.copy();
        this.emailDocsId = other.emailDocsId == null ? null : other.emailDocsId.copy();
        this.candidateAdditionalInfosId = other.candidateAdditionalInfosId == null ? null : other.candidateAdditionalInfosId.copy();
        this.noteId = other.noteId == null ? null : other.noteId.copy();
        this.emailId = other.emailId == null ? null : other.emailId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ObjectContainingFileCriteria copy() {
        return new ObjectContainingFileCriteria(this);
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

    public LongFilter getCandidateDocsId() {
        return candidateDocsId;
    }

    public LongFilter candidateDocsId() {
        if (candidateDocsId == null) {
            candidateDocsId = new LongFilter();
        }
        return candidateDocsId;
    }

    public void setCandidateDocsId(LongFilter candidateDocsId) {
        this.candidateDocsId = candidateDocsId;
    }

    public LongFilter getNoteDocsId() {
        return noteDocsId;
    }

    public LongFilter noteDocsId() {
        if (noteDocsId == null) {
            noteDocsId = new LongFilter();
        }
        return noteDocsId;
    }

    public void setNoteDocsId(LongFilter noteDocsId) {
        this.noteDocsId = noteDocsId;
    }

    public LongFilter getEmailDocsId() {
        return emailDocsId;
    }

    public LongFilter emailDocsId() {
        if (emailDocsId == null) {
            emailDocsId = new LongFilter();
        }
        return emailDocsId;
    }

    public void setEmailDocsId(LongFilter emailDocsId) {
        this.emailDocsId = emailDocsId;
    }

    public LongFilter getCandidateAdditionalInfosId() {
        return candidateAdditionalInfosId;
    }

    public LongFilter candidateAdditionalInfosId() {
        if (candidateAdditionalInfosId == null) {
            candidateAdditionalInfosId = new LongFilter();
        }
        return candidateAdditionalInfosId;
    }

    public void setCandidateAdditionalInfosId(LongFilter candidateAdditionalInfosId) {
        this.candidateAdditionalInfosId = candidateAdditionalInfosId;
    }

    public LongFilter getNoteId() {
        return noteId;
    }

    public LongFilter noteId() {
        if (noteId == null) {
            noteId = new LongFilter();
        }
        return noteId;
    }

    public void setNoteId(LongFilter noteId) {
        this.noteId = noteId;
    }

    public LongFilter getEmailId() {
        return emailId;
    }

    public LongFilter emailId() {
        if (emailId == null) {
            emailId = new LongFilter();
        }
        return emailId;
    }

    public void setEmailId(LongFilter emailId) {
        this.emailId = emailId;
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
        final ObjectContainingFileCriteria that = (ObjectContainingFileCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(candidateDocsId, that.candidateDocsId) &&
            Objects.equals(noteDocsId, that.noteDocsId) &&
            Objects.equals(emailDocsId, that.emailDocsId) &&
            Objects.equals(candidateAdditionalInfosId, that.candidateAdditionalInfosId) &&
            Objects.equals(noteId, that.noteId) &&
            Objects.equals(emailId, that.emailId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, candidateDocsId, noteDocsId, emailDocsId, candidateAdditionalInfosId, noteId, emailId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ObjectContainingFileCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (candidateDocsId != null ? "candidateDocsId=" + candidateDocsId + ", " : "") +
            (noteDocsId != null ? "noteDocsId=" + noteDocsId + ", " : "") +
            (emailDocsId != null ? "emailDocsId=" + emailDocsId + ", " : "") +
            (candidateAdditionalInfosId != null ? "candidateAdditionalInfosId=" + candidateAdditionalInfosId + ", " : "") +
            (noteId != null ? "noteId=" + noteId + ", " : "") +
            (emailId != null ? "emailId=" + emailId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
