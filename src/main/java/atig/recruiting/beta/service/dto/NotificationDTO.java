package atig.recruiting.beta.service.dto;

import atig.recruiting.beta.domain.enumeration.EnumNotificationType;
import atig.recruiting.beta.domain.enumeration.EnumPriority;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link atig.recruiting.beta.domain.Notification} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NotificationDTO implements Serializable {

    private Long id;

    private String message;

    private ZonedDateTime callUpDate;

    private Boolean readStatus;

    @NotNull
    private EnumPriority attention;

    private EnumNotificationType type;

    private CandidateDTO notificationCandidate;

    private CompanyDTO notificationCompany;

    private CandidateDTO candidate;

    private CompanyDTO company;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ZonedDateTime getCallUpDate() {
        return callUpDate;
    }

    public void setCallUpDate(ZonedDateTime callUpDate) {
        this.callUpDate = callUpDate;
    }

    public Boolean getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(Boolean readStatus) {
        this.readStatus = readStatus;
    }

    public EnumPriority getAttention() {
        return attention;
    }

    public void setAttention(EnumPriority attention) {
        this.attention = attention;
    }

    public EnumNotificationType getType() {
        return type;
    }

    public void setType(EnumNotificationType type) {
        this.type = type;
    }

    public CandidateDTO getNotificationCandidate() {
        return notificationCandidate;
    }

    public void setNotificationCandidate(CandidateDTO notificationCandidate) {
        this.notificationCandidate = notificationCandidate;
    }

    public CompanyDTO getNotificationCompany() {
        return notificationCompany;
    }

    public void setNotificationCompany(CompanyDTO notificationCompany) {
        this.notificationCompany = notificationCompany;
    }

    public CandidateDTO getCandidate() {
        return candidate;
    }

    public void setCandidate(CandidateDTO candidate) {
        this.candidate = candidate;
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
        if (!(o instanceof NotificationDTO)) {
            return false;
        }

        NotificationDTO notificationDTO = (NotificationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, notificationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NotificationDTO{" +
            "id=" + getId() +
            ", message='" + getMessage() + "'" +
            ", callUpDate='" + getCallUpDate() + "'" +
            ", readStatus='" + getReadStatus() + "'" +
            ", attention='" + getAttention() + "'" +
            ", type='" + getType() + "'" +
            ", notificationCandidate=" + getNotificationCandidate() +
            ", notificationCompany=" + getNotificationCompany() +
            ", candidate=" + getCandidate() +
            ", company=" + getCompany() +
            "}";
    }
}
