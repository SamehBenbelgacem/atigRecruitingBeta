package atig.recruiting.beta.service.dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link atig.recruiting.beta.domain.Candidate} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CandidateDTO implements Serializable {

    private Long id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @Lob
    private byte[] photo;

    private String photoContentType;

    @NotNull
    private String profession;

    private Integer nbExperience;

    @NotNull
    private String personalEmail;

    private CandidateAdditionalInfosDTO additionalInfos;

    private CategoryDTO candidateCategory;

    private SubCategoryDTO candidateSubCategory;

    private ProcessDTO candidateProcess;

    private ProcessStepDTO candidateProcessStep;

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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return photoContentType;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public Integer getNbExperience() {
        return nbExperience;
    }

    public void setNbExperience(Integer nbExperience) {
        this.nbExperience = nbExperience;
    }

    public String getPersonalEmail() {
        return personalEmail;
    }

    public void setPersonalEmail(String personalEmail) {
        this.personalEmail = personalEmail;
    }

    public CandidateAdditionalInfosDTO getAdditionalInfos() {
        return additionalInfos;
    }

    public void setAdditionalInfos(CandidateAdditionalInfosDTO additionalInfos) {
        this.additionalInfos = additionalInfos;
    }

    public CategoryDTO getCandidateCategory() {
        return candidateCategory;
    }

    public void setCandidateCategory(CategoryDTO candidateCategory) {
        this.candidateCategory = candidateCategory;
    }

    public SubCategoryDTO getCandidateSubCategory() {
        return candidateSubCategory;
    }

    public void setCandidateSubCategory(SubCategoryDTO candidateSubCategory) {
        this.candidateSubCategory = candidateSubCategory;
    }

    public ProcessDTO getCandidateProcess() {
        return candidateProcess;
    }

    public void setCandidateProcess(ProcessDTO candidateProcess) {
        this.candidateProcess = candidateProcess;
    }

    public ProcessStepDTO getCandidateProcessStep() {
        return candidateProcessStep;
    }

    public void setCandidateProcessStep(ProcessStepDTO candidateProcessStep) {
        this.candidateProcessStep = candidateProcessStep;
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
        if (!(o instanceof CandidateDTO)) {
            return false;
        }

        CandidateDTO candidateDTO = (CandidateDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, candidateDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CandidateDTO{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", profession='" + getProfession() + "'" +
            ", nbExperience=" + getNbExperience() +
            ", personalEmail='" + getPersonalEmail() + "'" +
            ", additionalInfos=" + getAdditionalInfos() +
            ", candidateCategory=" + getCandidateCategory() +
            ", candidateSubCategory=" + getCandidateSubCategory() +
            ", candidateProcess=" + getCandidateProcess() +
            ", candidateProcessStep=" + getCandidateProcessStep() +
            ", tags=" + getTags() +
            ", category=" + getCategory() +
            ", subCategory=" + getSubCategory() +
            ", process=" + getProcess() +
            ", processStep=" + getProcessStep() +
            "}";
    }
}
