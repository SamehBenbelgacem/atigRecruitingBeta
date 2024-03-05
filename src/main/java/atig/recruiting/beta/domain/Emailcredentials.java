package atig.recruiting.beta.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Emailcredentials.
 */
@Entity
@Table(name = "emailcredentials")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Emailcredentials implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "emailcredentials")
    @JsonIgnoreProperties(
        value = {
            "joinedFiles",
            "subEmails",
            "emailEmailcredentials",
            "emailCandidate",
            "emailCompany",
            "candidate",
            "company",
            "emailcredentials",
        },
        allowSetters = true
    )
    private Set<Email> emails = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Emailcredentials id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public Emailcredentials username(String username) {
        this.setUsername(username);
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public Emailcredentials password(String password) {
        this.setPassword(password);
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Email> getEmails() {
        return this.emails;
    }

    public void setEmails(Set<Email> emails) {
        if (this.emails != null) {
            this.emails.forEach(i -> i.setEmailcredentials(null));
        }
        if (emails != null) {
            emails.forEach(i -> i.setEmailcredentials(this));
        }
        this.emails = emails;
    }

    public Emailcredentials emails(Set<Email> emails) {
        this.setEmails(emails);
        return this;
    }

    public Emailcredentials addEmails(Email email) {
        this.emails.add(email);
        email.setEmailcredentials(this);
        return this;
    }

    public Emailcredentials removeEmails(Email email) {
        this.emails.remove(email);
        email.setEmailcredentials(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Emailcredentials)) {
            return false;
        }
        return getId() != null && getId().equals(((Emailcredentials) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Emailcredentials{" +
            "id=" + getId() +
            ", username='" + getUsername() + "'" +
            ", password='" + getPassword() + "'" +
            "}";
    }
}
