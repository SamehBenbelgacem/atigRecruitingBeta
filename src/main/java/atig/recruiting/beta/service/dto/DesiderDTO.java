package atig.recruiting.beta.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link atig.recruiting.beta.domain.Desider} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DesiderDTO implements Serializable {

    private Long id;

    private String fullName;

    private String email;

    private String mobile;

    private String role;

    private CompanyDTO desiderCompany;

    private CompanyDTO company;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public CompanyDTO getDesiderCompany() {
        return desiderCompany;
    }

    public void setDesiderCompany(CompanyDTO desiderCompany) {
        this.desiderCompany = desiderCompany;
    }

    public CompanyDTO getCompany() {
        return company;
    }

    public void setCompany(CompanyDTO company) {
        this.company = company;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DesiderDTO)) {
            return false;
        }

        DesiderDTO desiderDTO = (DesiderDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, desiderDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DesiderDTO{" +
            "id=" + getId() +
            ", fullName='" + getFullName() + "'" +
            ", email='" + getEmail() + "'" +
            ", mobile='" + getMobile() + "'" +
            ", role='" + getRole() + "'" +
            ", desiderCompany=" + getDesiderCompany() +
            ", company=" + getCompany() +
            "}";
    }
}
