package atig.recruiting.beta.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link atig.recruiting.beta.domain.CandidateAdditionalInfos} entity. This class is used
 * in {@link atig.recruiting.beta.web.rest.CandidateAdditionalInfosResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /candidate-additional-infos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CandidateAdditionalInfosCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter birthday;

    private IntegerFilter actualSalary;

    private IntegerFilter expectedSalary;

    private InstantFilter firstContact;

    private StringFilter location;

    private StringFilter mobile;

    private StringFilter disponibility;

    private LongFilter documentsId;

    private LongFilter candidateId;

    private Boolean distinct;

    public CandidateAdditionalInfosCriteria() {}

    public CandidateAdditionalInfosCriteria(CandidateAdditionalInfosCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.birthday = other.birthday == null ? null : other.birthday.copy();
        this.actualSalary = other.actualSalary == null ? null : other.actualSalary.copy();
        this.expectedSalary = other.expectedSalary == null ? null : other.expectedSalary.copy();
        this.firstContact = other.firstContact == null ? null : other.firstContact.copy();
        this.location = other.location == null ? null : other.location.copy();
        this.mobile = other.mobile == null ? null : other.mobile.copy();
        this.disponibility = other.disponibility == null ? null : other.disponibility.copy();
        this.documentsId = other.documentsId == null ? null : other.documentsId.copy();
        this.candidateId = other.candidateId == null ? null : other.candidateId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public CandidateAdditionalInfosCriteria copy() {
        return new CandidateAdditionalInfosCriteria(this);
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

    public InstantFilter getBirthday() {
        return birthday;
    }

    public InstantFilter birthday() {
        if (birthday == null) {
            birthday = new InstantFilter();
        }
        return birthday;
    }

    public void setBirthday(InstantFilter birthday) {
        this.birthday = birthday;
    }

    public IntegerFilter getActualSalary() {
        return actualSalary;
    }

    public IntegerFilter actualSalary() {
        if (actualSalary == null) {
            actualSalary = new IntegerFilter();
        }
        return actualSalary;
    }

    public void setActualSalary(IntegerFilter actualSalary) {
        this.actualSalary = actualSalary;
    }

    public IntegerFilter getExpectedSalary() {
        return expectedSalary;
    }

    public IntegerFilter expectedSalary() {
        if (expectedSalary == null) {
            expectedSalary = new IntegerFilter();
        }
        return expectedSalary;
    }

    public void setExpectedSalary(IntegerFilter expectedSalary) {
        this.expectedSalary = expectedSalary;
    }

    public InstantFilter getFirstContact() {
        return firstContact;
    }

    public InstantFilter firstContact() {
        if (firstContact == null) {
            firstContact = new InstantFilter();
        }
        return firstContact;
    }

    public void setFirstContact(InstantFilter firstContact) {
        this.firstContact = firstContact;
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

    public StringFilter getMobile() {
        return mobile;
    }

    public StringFilter mobile() {
        if (mobile == null) {
            mobile = new StringFilter();
        }
        return mobile;
    }

    public void setMobile(StringFilter mobile) {
        this.mobile = mobile;
    }

    public StringFilter getDisponibility() {
        return disponibility;
    }

    public StringFilter disponibility() {
        if (disponibility == null) {
            disponibility = new StringFilter();
        }
        return disponibility;
    }

    public void setDisponibility(StringFilter disponibility) {
        this.disponibility = disponibility;
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
        final CandidateAdditionalInfosCriteria that = (CandidateAdditionalInfosCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(birthday, that.birthday) &&
            Objects.equals(actualSalary, that.actualSalary) &&
            Objects.equals(expectedSalary, that.expectedSalary) &&
            Objects.equals(firstContact, that.firstContact) &&
            Objects.equals(location, that.location) &&
            Objects.equals(mobile, that.mobile) &&
            Objects.equals(disponibility, that.disponibility) &&
            Objects.equals(documentsId, that.documentsId) &&
            Objects.equals(candidateId, that.candidateId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            birthday,
            actualSalary,
            expectedSalary,
            firstContact,
            location,
            mobile,
            disponibility,
            documentsId,
            candidateId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CandidateAdditionalInfosCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (birthday != null ? "birthday=" + birthday + ", " : "") +
            (actualSalary != null ? "actualSalary=" + actualSalary + ", " : "") +
            (expectedSalary != null ? "expectedSalary=" + expectedSalary + ", " : "") +
            (firstContact != null ? "firstContact=" + firstContact + ", " : "") +
            (location != null ? "location=" + location + ", " : "") +
            (mobile != null ? "mobile=" + mobile + ", " : "") +
            (disponibility != null ? "disponibility=" + disponibility + ", " : "") +
            (documentsId != null ? "documentsId=" + documentsId + ", " : "") +
            (candidateId != null ? "candidateId=" + candidateId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
