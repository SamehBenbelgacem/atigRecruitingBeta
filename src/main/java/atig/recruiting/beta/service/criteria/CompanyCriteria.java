package atig.recruiting.beta.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link atig.recruiting.beta.domain.Company} entity. This class is used
 * in {@link atig.recruiting.beta.web.rest.CompanyResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /companies?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CompanyCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter speciality;

    private StringFilter description;

    private StringFilter website;

    private StringFilter location;

    private StringFilter infoEmail;

    private StringFilter phone;

    private LocalDateFilter firstContactDate;

    private LongFilter notificationsId;

    private LongFilter notesId;

    private LongFilter desidersId;

    private LongFilter offersId;

    private LongFilter emailsId;

    private LongFilter companyCategoryId;

    private LongFilter companySubCategoryId;

    private LongFilter companyProcessId;

    private LongFilter companyProcessStepId;

    private LongFilter tagsId;

    private LongFilter categoryId;

    private LongFilter subCategoryId;

    private LongFilter processId;

    private LongFilter processStepId;

    private Boolean distinct;

    public CompanyCriteria() {}

    public CompanyCriteria(CompanyCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.speciality = other.speciality == null ? null : other.speciality.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.website = other.website == null ? null : other.website.copy();
        this.location = other.location == null ? null : other.location.copy();
        this.infoEmail = other.infoEmail == null ? null : other.infoEmail.copy();
        this.phone = other.phone == null ? null : other.phone.copy();
        this.firstContactDate = other.firstContactDate == null ? null : other.firstContactDate.copy();
        this.notificationsId = other.notificationsId == null ? null : other.notificationsId.copy();
        this.notesId = other.notesId == null ? null : other.notesId.copy();
        this.desidersId = other.desidersId == null ? null : other.desidersId.copy();
        this.offersId = other.offersId == null ? null : other.offersId.copy();
        this.emailsId = other.emailsId == null ? null : other.emailsId.copy();
        this.companyCategoryId = other.companyCategoryId == null ? null : other.companyCategoryId.copy();
        this.companySubCategoryId = other.companySubCategoryId == null ? null : other.companySubCategoryId.copy();
        this.companyProcessId = other.companyProcessId == null ? null : other.companyProcessId.copy();
        this.companyProcessStepId = other.companyProcessStepId == null ? null : other.companyProcessStepId.copy();
        this.tagsId = other.tagsId == null ? null : other.tagsId.copy();
        this.categoryId = other.categoryId == null ? null : other.categoryId.copy();
        this.subCategoryId = other.subCategoryId == null ? null : other.subCategoryId.copy();
        this.processId = other.processId == null ? null : other.processId.copy();
        this.processStepId = other.processStepId == null ? null : other.processStepId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public CompanyCriteria copy() {
        return new CompanyCriteria(this);
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

    public StringFilter getName() {
        return name;
    }

    public StringFilter name() {
        if (name == null) {
            name = new StringFilter();
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getSpeciality() {
        return speciality;
    }

    public StringFilter speciality() {
        if (speciality == null) {
            speciality = new StringFilter();
        }
        return speciality;
    }

    public void setSpeciality(StringFilter speciality) {
        this.speciality = speciality;
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

    public StringFilter getWebsite() {
        return website;
    }

    public StringFilter website() {
        if (website == null) {
            website = new StringFilter();
        }
        return website;
    }

    public void setWebsite(StringFilter website) {
        this.website = website;
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

    public StringFilter getInfoEmail() {
        return infoEmail;
    }

    public StringFilter infoEmail() {
        if (infoEmail == null) {
            infoEmail = new StringFilter();
        }
        return infoEmail;
    }

    public void setInfoEmail(StringFilter infoEmail) {
        this.infoEmail = infoEmail;
    }

    public StringFilter getPhone() {
        return phone;
    }

    public StringFilter phone() {
        if (phone == null) {
            phone = new StringFilter();
        }
        return phone;
    }

    public void setPhone(StringFilter phone) {
        this.phone = phone;
    }

    public LocalDateFilter getFirstContactDate() {
        return firstContactDate;
    }

    public LocalDateFilter firstContactDate() {
        if (firstContactDate == null) {
            firstContactDate = new LocalDateFilter();
        }
        return firstContactDate;
    }

    public void setFirstContactDate(LocalDateFilter firstContactDate) {
        this.firstContactDate = firstContactDate;
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

    public LongFilter getDesidersId() {
        return desidersId;
    }

    public LongFilter desidersId() {
        if (desidersId == null) {
            desidersId = new LongFilter();
        }
        return desidersId;
    }

    public void setDesidersId(LongFilter desidersId) {
        this.desidersId = desidersId;
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

    public LongFilter getCompanyCategoryId() {
        return companyCategoryId;
    }

    public LongFilter companyCategoryId() {
        if (companyCategoryId == null) {
            companyCategoryId = new LongFilter();
        }
        return companyCategoryId;
    }

    public void setCompanyCategoryId(LongFilter companyCategoryId) {
        this.companyCategoryId = companyCategoryId;
    }

    public LongFilter getCompanySubCategoryId() {
        return companySubCategoryId;
    }

    public LongFilter companySubCategoryId() {
        if (companySubCategoryId == null) {
            companySubCategoryId = new LongFilter();
        }
        return companySubCategoryId;
    }

    public void setCompanySubCategoryId(LongFilter companySubCategoryId) {
        this.companySubCategoryId = companySubCategoryId;
    }

    public LongFilter getCompanyProcessId() {
        return companyProcessId;
    }

    public LongFilter companyProcessId() {
        if (companyProcessId == null) {
            companyProcessId = new LongFilter();
        }
        return companyProcessId;
    }

    public void setCompanyProcessId(LongFilter companyProcessId) {
        this.companyProcessId = companyProcessId;
    }

    public LongFilter getCompanyProcessStepId() {
        return companyProcessStepId;
    }

    public LongFilter companyProcessStepId() {
        if (companyProcessStepId == null) {
            companyProcessStepId = new LongFilter();
        }
        return companyProcessStepId;
    }

    public void setCompanyProcessStepId(LongFilter companyProcessStepId) {
        this.companyProcessStepId = companyProcessStepId;
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
        final CompanyCriteria that = (CompanyCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(speciality, that.speciality) &&
            Objects.equals(description, that.description) &&
            Objects.equals(website, that.website) &&
            Objects.equals(location, that.location) &&
            Objects.equals(infoEmail, that.infoEmail) &&
            Objects.equals(phone, that.phone) &&
            Objects.equals(firstContactDate, that.firstContactDate) &&
            Objects.equals(notificationsId, that.notificationsId) &&
            Objects.equals(notesId, that.notesId) &&
            Objects.equals(desidersId, that.desidersId) &&
            Objects.equals(offersId, that.offersId) &&
            Objects.equals(emailsId, that.emailsId) &&
            Objects.equals(companyCategoryId, that.companyCategoryId) &&
            Objects.equals(companySubCategoryId, that.companySubCategoryId) &&
            Objects.equals(companyProcessId, that.companyProcessId) &&
            Objects.equals(companyProcessStepId, that.companyProcessStepId) &&
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
            name,
            speciality,
            description,
            website,
            location,
            infoEmail,
            phone,
            firstContactDate,
            notificationsId,
            notesId,
            desidersId,
            offersId,
            emailsId,
            companyCategoryId,
            companySubCategoryId,
            companyProcessId,
            companyProcessStepId,
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
        return "CompanyCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (speciality != null ? "speciality=" + speciality + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (website != null ? "website=" + website + ", " : "") +
            (location != null ? "location=" + location + ", " : "") +
            (infoEmail != null ? "infoEmail=" + infoEmail + ", " : "") +
            (phone != null ? "phone=" + phone + ", " : "") +
            (firstContactDate != null ? "firstContactDate=" + firstContactDate + ", " : "") +
            (notificationsId != null ? "notificationsId=" + notificationsId + ", " : "") +
            (notesId != null ? "notesId=" + notesId + ", " : "") +
            (desidersId != null ? "desidersId=" + desidersId + ", " : "") +
            (offersId != null ? "offersId=" + offersId + ", " : "") +
            (emailsId != null ? "emailsId=" + emailsId + ", " : "") +
            (companyCategoryId != null ? "companyCategoryId=" + companyCategoryId + ", " : "") +
            (companySubCategoryId != null ? "companySubCategoryId=" + companySubCategoryId + ", " : "") +
            (companyProcessId != null ? "companyProcessId=" + companyProcessId + ", " : "") +
            (companyProcessStepId != null ? "companyProcessStepId=" + companyProcessStepId + ", " : "") +
            (tagsId != null ? "tagsId=" + tagsId + ", " : "") +
            (categoryId != null ? "categoryId=" + categoryId + ", " : "") +
            (subCategoryId != null ? "subCategoryId=" + subCategoryId + ", " : "") +
            (processId != null ? "processId=" + processId + ", " : "") +
            (processStepId != null ? "processStepId=" + processStepId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
