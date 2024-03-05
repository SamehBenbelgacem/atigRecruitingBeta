package atig.recruiting.beta.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link atig.recruiting.beta.domain.Emailcredentials} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmailcredentialsDTO implements Serializable {

    private Long id;

    private String username;

    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmailcredentialsDTO)) {
            return false;
        }

        EmailcredentialsDTO emailcredentialsDTO = (EmailcredentialsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, emailcredentialsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmailcredentialsDTO{" +
            "id=" + getId() +
            ", username='" + getUsername() + "'" +
            ", password='" + getPassword() + "'" +
            "}";
    }
}
