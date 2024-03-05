package atig.recruiting.beta.domain;

import atig.recruiting.beta.domain.enumeration.EnumNotificationType;
import atig.recruiting.beta.domain.enumeration.EnumPriority;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A Notification.
 */
@Entity
@Table(name = "notification")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Notification implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "message")
    private String message;

    @Column(name = "call_up_date")
    private ZonedDateTime callUpDate;

    @Column(name = "read_status")
    private Boolean readStatus;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "attention", nullable = false)
    private EnumPriority attention;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private EnumNotificationType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "additionalInfos",
            "experiences",
            "educations",
            "certifications",
            "skills",
            "languages",
            "notifications",
            "notes",
            "emails",
            "candidateCategory",
            "candidateSubCategory",
            "candidateProcess",
            "candidateProcessStep",
            "tags",
            "category",
            "subCategory",
            "process",
            "processStep",
        },
        allowSetters = true
    )
    private Candidate notificationCandidate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "notifications",
            "notes",
            "desiders",
            "offers",
            "emails",
            "companyCategory",
            "companySubCategory",
            "companyProcess",
            "companyProcessStep",
            "tags",
            "category",
            "subCategory",
            "process",
            "processStep",
        },
        allowSetters = true
    )
    private Company notificationCompany;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "additionalInfos",
            "experiences",
            "educations",
            "certifications",
            "skills",
            "languages",
            "notifications",
            "notes",
            "emails",
            "candidateCategory",
            "candidateSubCategory",
            "candidateProcess",
            "candidateProcessStep",
            "tags",
            "category",
            "subCategory",
            "process",
            "processStep",
        },
        allowSetters = true
    )
    private Candidate candidate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "notifications",
            "notes",
            "desiders",
            "offers",
            "emails",
            "companyCategory",
            "companySubCategory",
            "companyProcess",
            "companyProcessStep",
            "tags",
            "category",
            "subCategory",
            "process",
            "processStep",
        },
        allowSetters = true
    )
    private Company company;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Notification id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return this.message;
    }

    public Notification message(String message) {
        this.setMessage(message);
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ZonedDateTime getCallUpDate() {
        return this.callUpDate;
    }

    public Notification callUpDate(ZonedDateTime callUpDate) {
        this.setCallUpDate(callUpDate);
        return this;
    }

    public void setCallUpDate(ZonedDateTime callUpDate) {
        this.callUpDate = callUpDate;
    }

    public Boolean getReadStatus() {
        return this.readStatus;
    }

    public Notification readStatus(Boolean readStatus) {
        this.setReadStatus(readStatus);
        return this;
    }

    public void setReadStatus(Boolean readStatus) {
        this.readStatus = readStatus;
    }

    public EnumPriority getAttention() {
        return this.attention;
    }

    public Notification attention(EnumPriority attention) {
        this.setAttention(attention);
        return this;
    }

    public void setAttention(EnumPriority attention) {
        this.attention = attention;
    }

    public EnumNotificationType getType() {
        return this.type;
    }

    public Notification type(EnumNotificationType type) {
        this.setType(type);
        return this;
    }

    public void setType(EnumNotificationType type) {
        this.type = type;
    }

    public Candidate getNotificationCandidate() {
        return this.notificationCandidate;
    }

    public void setNotificationCandidate(Candidate candidate) {
        this.notificationCandidate = candidate;
    }

    public Notification notificationCandidate(Candidate candidate) {
        this.setNotificationCandidate(candidate);
        return this;
    }

    public Company getNotificationCompany() {
        return this.notificationCompany;
    }

    public void setNotificationCompany(Company company) {
        this.notificationCompany = company;
    }

    public Notification notificationCompany(Company company) {
        this.setNotificationCompany(company);
        return this;
    }

    public Candidate getCandidate() {
        return this.candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public Notification candidate(Candidate candidate) {
        this.setCandidate(candidate);
        return this;
    }

    public Company getCompany() {
        return this.company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Notification company(Company company) {
        this.setCompany(company);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Notification)) {
            return false;
        }
        return getId() != null && getId().equals(((Notification) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Notification{" +
            "id=" + getId() +
            ", message='" + getMessage() + "'" +
            ", callUpDate='" + getCallUpDate() + "'" +
            ", readStatus='" + getReadStatus() + "'" +
            ", attention='" + getAttention() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
