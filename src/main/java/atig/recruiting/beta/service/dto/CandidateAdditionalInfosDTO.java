package atig.recruiting.beta.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link atig.recruiting.beta.domain.CandidateAdditionalInfos} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CandidateAdditionalInfosDTO implements Serializable {

    private Long id;

    private Instant birthday;

    private Integer actualSalary;

    private Integer expectedSalary;

    private Instant firstContact;

    private String location;

    private String mobile;

    private String disponibility;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getBirthday() {
        return birthday;
    }

    public void setBirthday(Instant birthday) {
        this.birthday = birthday;
    }

    public Integer getActualSalary() {
        return actualSalary;
    }

    public void setActualSalary(Integer actualSalary) {
        this.actualSalary = actualSalary;
    }

    public Integer getExpectedSalary() {
        return expectedSalary;
    }

    public void setExpectedSalary(Integer expectedSalary) {
        this.expectedSalary = expectedSalary;
    }

    public Instant getFirstContact() {
        return firstContact;
    }

    public void setFirstContact(Instant firstContact) {
        this.firstContact = firstContact;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDisponibility() {
        return disponibility;
    }

    public void setDisponibility(String disponibility) {
        this.disponibility = disponibility;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CandidateAdditionalInfosDTO)) {
            return false;
        }

        CandidateAdditionalInfosDTO candidateAdditionalInfosDTO = (CandidateAdditionalInfosDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, candidateAdditionalInfosDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CandidateAdditionalInfosDTO{" +
            "id=" + getId() +
            ", birthday='" + getBirthday() + "'" +
            ", actualSalary=" + getActualSalary() +
            ", expectedSalary=" + getExpectedSalary() +
            ", firstContact='" + getFirstContact() + "'" +
            ", location='" + getLocation() + "'" +
            ", mobile='" + getMobile() + "'" +
            ", disponibility='" + getDisponibility() + "'" +
            "}";
    }
}
