package atig.recruiting.beta.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link atig.recruiting.beta.domain.Candidate} entity. This class is used
 * in {@link atig.recruiting.beta.web.rest.CandidateResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /candidates?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CandidateCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter firstName;

    private StringFilter lastName;

    private StringFilter profession;

    private IntegerFilter nbExperience;

    private StringFilter personalEmail;

    private LongFilter additionalInfosId;

    private LongFilter experiencesId;

    private LongFilter educationsId;

    private LongFilter certificationsId;

    private LongFilter skillsId;

    private LongFilter languagesId;

    private LongFilter notificationsId;

    private LongFilter notesId;

    private LongFilter emailsId;

    private LongFilter candidateCategoryId;

    private LongFilter candidateSubCategoryId;

    private LongFilter candidateProcessId;

    private LongFilter candidateProcessStepId;

    private LongFilter tagsId;

    private LongFilter categoryId;

    private LongFilter subCategoryId;

    private LongFilter processId;

    private LongFilter processStepId;

    private Boolean distinct;

    public CandidateCriteria() {}

    public CandidateCriteria(CandidateCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.firstName = other.firstName == null ? null : other.firstName.copy();
        this.lastName = other.lastName == null ? null : other.lastName.copy();
        this.profession = other.profession == null ? null : other.profession.copy();
        this.nbExperience = other.nbExperience == null ? null : other.nbExperience.copy();
        this.personalEmail = other.personalEmail == null ? null : other.personalEmail.copy();
        this.additionalInfosId = other.additionalInfosId == null ? null : other.additionalInfosId.copy();
        this.experiencesId = other.experiencesId == null ? null : other.experiencesId.copy();
        this.educationsId = other.educationsId == null ? null : other.educationsId.copy();
        this.certificationsId = other.certificationsId == null ? null : other.certificationsId.copy();
        this.skillsId = other.skillsId == null ? null : other.skillsId.copy();
        this.languagesId = other.languagesId == null ? null : other.languagesId.copy();
        this.notificationsId = other.notificationsId == null ? null : other.notificationsId.copy();
        this.notesId = other.notesId == null ? null : other.notesId.copy();
        this.emailsId = other.emailsId == null ? null : other.emailsId.copy();
        this.candidateCategoryId = other.candidateCategoryId == null ? null : other.candidateCategoryId.copy();
        this.candidateSubCategoryId = other.candidateSubCategoryId == null ? null : other.candidateSubCategoryId.copy();
        this.candidateProcessId = other.candidateProcessId == null ? null : other.candidateProcessId.copy();
        this.candidateProcessStepId = other.candidateProcessStepId == null ? null : other.candidateProcessStepId.copy();
        this.tagsId = other.tagsId == null ? null : other.tagsId.copy();
        this.categoryId = other.categoryId == null ? null : other.categoryId.copy();
        this.subCategoryId = other.subCategoryId == null ? null : other.subCategoryId.copy();
        this.processId = other.processId == null ? null : other.processId.copy();
        this.processStepId = other.processStepId == null ? null : other.processStepId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public CandidateCriteria copy() {
        return new CandidateCriteria(this);
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

    public StringFilter getFirstName() {
        return firstName;
    }

    public StringFilter firstName() {
        if (firstName == null) {
            firstName = new StringFilter();
        }
        return firstName;
    }

    public void setFirstName(StringFilter firstName) {
        this.firstName = firstName;
    }

    public StringFilter getLastName() {
        return lastName;
    }

    public StringFilter lastName() {
        if (lastName == null) {
            lastName = new StringFilter();
        }
        return lastName;
    }

    public void setLastName(StringFilter lastName) {
        this.lastName = lastName;
    }

    public StringFilter getProfession() {
        return profession;
    }

    public StringFilter profession() {
        if (profession == null) {
            profession = new StringFilter();
        }
        return profession;
    }

    public void setProfession(StringFilter profession) {
        this.profession = profession;
    }

    public IntegerFilter getNbExperience() {
        return nbExperience;
    }

    public IntegerFilter nbExperience() {
        if (nbExperience == null) {
            nbExperience = new IntegerFilter();
        }
        return nbExperience;
    }

    public void setNbExperience(IntegerFilter nbExperience) {
        this.nbExperience = nbExperience;
    }

    public StringFilter getPersonalEmail() {
        return personalEmail;
    }

    public StringFilter personalEmail() {
        if (personalEmail == null) {
            personalEmail = new StringFilter();
        }
        return personalEmail;
    }

    public void setPersonalEmail(StringFilter personalEmail) {
        this.personalEmail = personalEmail;
    }

    public LongFilter getAdditionalInfosId() {
        return additionalInfosId;
    }

    public LongFilter additionalInfosId() {
        if (additionalInfosId == null) {
            additionalInfosId = new LongFilter();
        }
        return additionalInfosId;
    }

    public void setAdditionalInfosId(LongFilter additionalInfosId) {
        this.additionalInfosId = additionalInfosId;
    }

    public LongFilter getExperiencesId() {
        return experiencesId;
    }

    public LongFilter experiencesId() {
        if (experiencesId == null) {
            experiencesId = new LongFilter();
        }
        return experiencesId;
    }

    public void setExperiencesId(LongFilter experiencesId) {
        this.experiencesId = experiencesId;
    }

    public LongFilter getEducationsId() {
        return educationsId;
    }

    public LongFilter educationsId() {
        if (educationsId == null) {
            educationsId = new LongFilter();
        }
        return educationsId;
    }

    public void setEducationsId(LongFilter educationsId) {
        this.educationsId = educationsId;
    }

    public LongFilter getCertificationsId() {
        return certificationsId;
    }

    public LongFilter certificationsId() {
        if (certificationsId == null) {
            certificationsId = new LongFilter();
        }
        return certificationsId;
    }

    public void setCertificationsId(LongFilter certificationsId) {
        this.certificationsId = certificationsId;
    }

    public LongFilter getSkillsId() {
        return skillsId;
    }

    public LongFilter skillsId() {
        if (skillsId == null) {
            skillsId = new LongFilter();
        }
        return skillsId;
    }

    public void setSkillsId(LongFilter skillsId) {
        this.skillsId = skillsId;
    }

    public LongFilter getLanguagesId() {
        return languagesId;
    }

    public LongFilter languagesId() {
        if (languagesId == null) {
            languagesId = new LongFilter();
        }
        return languagesId;
    }

    public void setLanguagesId(LongFilter languagesId) {
        this.languagesId = languagesId;
    }

    public LongFilter getNotificationsId() {
        return notificationsId;
    }

    public LongFilter notificationsId() {
        if (notificationsId == null) {
            notificationsId = new LongFilter();
        }
        return notificationsId;
    }

    public void setNotificationsId(LongFilter notificationsId) {
        this.notificationsId = notificationsId;
    }

    public LongFilter getNotesId() {
        return notesId;
    }

    public LongFilter notesId() {
        if (notesId == null) {
            notesId = new LongFilter();
        }
        return notesId;
    }

    public void setNotesId(LongFilter notesId) {
        this.notesId = notesId;
    }

    public LongFilter getEmailsId() {
        return emailsId;
    }

    public LongFilter emailsId() {
        if (emailsId == null) {
            emailsId = new LongFilter();
        }
        return emailsId;
    }

    public void setEmailsId(LongFilter emailsId) {
        this.emailsId = emailsId;
    }

    public LongFilter getCandidateCategoryId() {
        return candidateCategoryId;
    }

    public LongFilter candidateCategoryId() {
        if (candidateCategoryId == null) {
            candidateCategoryId = new LongFilter();
        }
        return candidateCategoryId;
    }

    public void setCandidateCategoryId(LongFilter candidateCategoryId) {
        this.candidateCategoryId = candidateCategoryId;
    }

    public LongFilter getCandidateSubCategoryId() {
        return candidateSubCategoryId;
    }

    public LongFilter candidateSubCategoryId() {
        if (candidateSubCategoryId == null) {
            candidateSubCategoryId = new LongFilter();
        }
        return candidateSubCategoryId;
    }

    public void setCandidateSubCategoryId(LongFilter candidateSubCategoryId) {
        this.candidateSubCategoryId = candidateSubCategoryId;
    }

    public LongFilter getCandidateProcessId() {
        return candidateProcessId;
    }

    public LongFilter candidateProcessId() {
        if (candidateProcessId == null) {
            candidateProcessId = new LongFilter();
        }
        return candidateProcessId;
    }

    public void setCandidateProcessId(LongFilter candidateProcessId) {
        this.candidateProcessId = candidateProcessId;
    }

    public LongFilter getCandidateProcessStepId() {
        return candidateProcessStepId;
    }

    public LongFilter candidateProcessStepId() {
        if (candidateProcessStepId == null) {
            candidateProcessStepId = new LongFilter();
        }
        return candidateProcessStepId;
    }

    public void setCandidateProcessStepId(LongFilter candidateProcessStepId) {
        this.candidateProcessStepId = candidateProcessStepId;
    }

    public LongFilter getTagsId() {
        return tagsId;
    }

    public LongFilter tagsId() {
        if (tagsId == null) {
            tagsId = new LongFilter();
        }
        return tagsId;
    }

    public void setTagsId(LongFilter tagsId) {
        this.tagsId = tagsId;
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

    public LongFilter getSubCategoryId() {
        return subCategoryId;
    }

    public LongFilter subCategoryId() {
        if (subCategoryId == null) {
            subCategoryId = new LongFilter();
        }
        return subCategoryId;
    }

    public void setSubCategoryId(LongFilter subCategoryId) {
        this.subCategoryId = subCategoryId;
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

    public LongFilter getProcessStepId() {
        return processStepId;
    }

    public LongFilter processStepId() {
        if (processStepId == null) {
            processStepId = new LongFilter();
        }
        return processStepId;
    }

    public void setProcessStepId(LongFilter processStepId) {
        this.processStepId = processStepId;
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
        final CandidateCriteria that = (CandidateCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(firstName, that.firstName) &&
            Objects.equals(lastName, that.lastName) &&
            Objects.equals(profession, that.profession) &&
            Objects.equals(nbExperience, that.nbExperience) &&
            Objects.equals(personalEmail, that.personalEmail) &&
            Objects.equals(additionalInfosId, that.additionalInfosId) &&
            Objects.equals(experiencesId, that.experiencesId) &&
            Objects.equals(educationsId, that.educationsId) &&
            Objects.equals(certificationsId, that.certificationsId) &&
            Objects.equals(skillsId, that.skillsId) &&
            Objects.equals(languagesId, that.languagesId) &&
            Objects.equals(notificationsId, that.notificationsId) &&
            Objects.equals(notesId, that.notesId) &&
            Objects.equals(emailsId, that.emailsId) &&
            Objects.equals(candidateCategoryId, that.candidateCategoryId) &&
            Objects.equals(candidateSubCategoryId, that.candidateSubCategoryId) &&
            Objects.equals(candidateProcessId, that.candidateProcessId) &&
            Objects.equals(candidateProcessStepId, that.candidateProcessStepId) &&
            Objects.equals(tagsId, that.tagsId) &&
            Objects.equals(categoryId, that.categoryId) &&
            Objects.equals(subCategoryId, that.subCategoryId) &&
            Objects.equals(processId, that.processId) &&
            Objects.equals(processStepId, that.processStepId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            firstName,
            lastName,
            profession,
            nbExperience,
            personalEmail,
            additionalInfosId,
            experiencesId,
            educationsId,
            certificationsId,
            skillsId,
            languagesId,
            notificationsId,
            notesId,
            emailsId,
            candidateCategoryId,
            candidateSubCategoryId,
            candidateProcessId,
            candidateProcessStepId,
            tagsId,
            categoryId,
            subCategoryId,
            processId,
            processStepId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CandidateCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (firstName != null ? "firstName=" + firstName + ", " : "") +
            (lastName != null ? "lastName=" + lastName + ", " : "") +
            (profession != null ? "profession=" + profession + ", " : "") +
            (nbExperience != null ? "nbExperience=" + nbExperience + ", " : "") +
            (personalEmail != null ? "personalEmail=" + personalEmail + ", " : "") +
            (additionalInfosId != null ? "additionalInfosId=" + additionalInfosId + ", " : "") +
            (experiencesId != null ? "experiencesId=" + experiencesId + ", " : "") +
            (educationsId != null ? "educationsId=" + educationsId + ", " : "") +
            (certificationsId != null ? "certificationsId=" + certificationsId + ", " : "") +
            (skillsId != null ? "skillsId=" + skillsId + ", " : "") +
            (languagesId != null ? "languagesId=" + languagesId + ", " : "") +
            (notificationsId != null ? "notificationsId=" + notificationsId + ", " : "") +
            (notesId != null ? "notesId=" + notesId + ", " : "") +
            (emailsId != null ? "emailsId=" + emailsId + ", " : "") +
            (candidateCategoryId != null ? "candidateCategoryId=" + candidateCategoryId + ", " : "") +
            (candidateSubCategoryId != null ? "candidateSubCategoryId=" + candidateSubCategoryId + ", " : "") +
            (candidateProcessId != null ? "candidateProcessId=" + candidateProcessId + ", " : "") +
            (candidateProcessStepId != null ? "candidateProcessStepId=" + candidateProcessStepId + ", " : "") +
            (tagsId != null ? "tagsId=" + tagsId + ", " : "") +
            (categoryId != null ? "categoryId=" + categoryId + ", " : "") +
            (subCategoryId != null ? "subCategoryId=" + subCategoryId + ", " : "") +
            (processId != null ? "processId=" + processId + ", " : "") +
            (processStepId != null ? "processStepId=" + processStepId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
