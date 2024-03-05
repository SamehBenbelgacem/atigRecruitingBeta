package atig.recruiting.beta.service.dto;

import jakarta.persistence.Lob;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link atig.recruiting.beta.domain.Company} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CompanyDTO implements Serializable {

    private Long id;

    private String name;

    private String speciality;

    @Lob
    private byte[] logo;

    private String logoContentType;
    private String description;

    private String website;

    private String location;

    private String infoEmail;

    private String phone;

    private LocalDate firstContactDate;

    private CategoryDTO companyCategory;

    private SubCategoryDTO companySubCategory;

    private ProcessDTO companyProcess;

    private ProcessStepDTO companyProcessStep;

    private Set<TagDTO> tags = new HashSet<>();

    private CategoryDTO category;

    private SubCategoryDTO subCategory;

    private ProcessDTO process;

    private ProcessStepDTO processStep;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public String getLogoContentType() {
        return logoContentType;
    }

    public void setLogoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getInfoEmail() {
        return infoEmail;
    }

    public void setInfoEmail(String infoEmail) {
        this.infoEmail = infoEmail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getFirstContactDate() {
        return firstContactDate;
    }

    public void setFirstContactDate(LocalDate firstContactDate) {
        this.firstContactDate = firstContactDate;
    }

    public CategoryDTO getCompanyCategory() {
        return companyCategory;
    }

    public void setCompanyCategory(CategoryDTO companyCategory) {
        this.companyCategory = companyCategory;
    }

    public SubCategoryDTO getCompanySubCategory() {
        return companySubCategory;
    }

    public void setCompanySubCategory(SubCategoryDTO companySubCategory) {
        this.companySubCategory = companySubCategory;
    }

    public ProcessDTO getCompanyProcess() {
        return companyProcess;
    }

    public void setCompanyProcess(ProcessDTO companyProcess) {
        this.companyProcess = companyProcess;
    }

    public ProcessStepDTO getCompanyProcessStep() {
        return companyProcessStep;
    }

    public void setCompanyProcessStep(ProcessStepDTO companyProcessStep) {
        this.companyProcessStep = companyProcessStep;
    }

    public Set<TagDTO> getTags() {
        return tags;
    }

    public void setTags(Set<TagDTO> tags) {
        this.tags = tags;
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
    }

    public SubCategoryDTO getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(SubCategoryDTO subCategory) {
        this.subCategory = subCategory;
    }

    public ProcessDTO getProcess() {
        return process;
    }

    public void setProcess(ProcessDTO process) {
        this.process = process;
    }

    public ProcessStepDTO getProcessStep() {
        return processStep;
    }

    public void setProcessStep(ProcessStepDTO processStep) {
        this.processStep = processStep;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompanyDTO)) {
            return false;
        }

        CompanyDTO companyDTO = (CompanyDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, companyDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompanyDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", speciality='" + getSpeciality() + "'" +
            ", logo='" + getLogo() + "'" +
            ", description='" + getDescription() + "'" +
            ", website='" + getWebsite() + "'" +
            ", location='" + getLocation() + "'" +
            ", infoEmail='" + getInfoEmail() + "'" +
            ", phone='" + getPhone() + "'" +
            ", firstContactDate='" + getFirstContactDate() + "'" +
            ", companyCategory=" + getCompanyCategory() +
            ", companySubCategory=" + getCompanySubCategory() +
            ", companyProcess=" + getCompanyProcess() +
            ", companyProcessStep=" + getCompanyProcessStep() +
            ", tags=" + getTags() +
            ", category=" + getCategory() +
            ", subCategory=" + getSubCategory() +
            ", process=" + getProcess() +
            ", processStep=" + getProcessStep() +
            "}";
    }
}
