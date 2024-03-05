package atig.recruiting.beta.service.criteria;

import atig.recruiting.beta.domain.enumeration.EnumNotificationType;
import atig.recruiting.beta.domain.enumeration.EnumPriority;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link atig.recruiting.beta.domain.Notification} entity. This class is used
 * in {@link atig.recruiting.beta.web.rest.NotificationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /notifications?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NotificationCriteria implements Serializable, Criteria {

    /**
     * Class for filtering EnumPriority
     */
    public static class EnumPriorityFilter extends Filter<EnumPriority> {

        public EnumPriorityFilter() {}

        public EnumPriorityFilter(EnumPriorityFilter filter) {
            super(filter);
        }

        @Override
        public EnumPriorityFilter copy() {
            return new EnumPriorityFilter(this);
        }
    }

    /**
     * Class for filtering EnumNotificationType
     */
    public static class EnumNotificationTypeFilter extends Filter<EnumNotificationType> {

        public EnumNotificationTypeFilter() {}

        public EnumNotificationTypeFilter(EnumNotificationTypeFilter filter) {
            super(filter);
        }

        @Override
        public EnumNotificationTypeFilter copy() {
            return new EnumNotificationTypeFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter message;

    private ZonedDateTimeFilter callUpDate;

    private BooleanFilter readStatus;

    private EnumPriorityFilter attention;

    private EnumNotificationTypeFilter type;

    private LongFilter notificationCandidateId;

    private LongFilter notificationCompanyId;

    private LongFilter candidateId;

    private LongFilter companyId;

    private Boolean distinct;

    public NotificationCriteria() {}

    public NotificationCriteria(NotificationCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.message = other.message == null ? null : other.message.copy();
        this.callUpDate = other.callUpDate == null ? null : other.callUpDate.copy();
        this.readStatus = other.readStatus == null ? null : other.readStatus.copy();
        this.attention = other.attention == null ? null : other.attention.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.notificationCandidateId = other.notificationCandidateId == null ? null : other.notificationCandidateId.copy();
        this.notificationCompanyId = other.notificationCompanyId == null ? null : other.notificationCompanyId.copy();
        this.candidateId = other.candidateId == null ? null : other.candidateId.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public NotificationCriteria copy() {
        return new NotificationCriteria(this);
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

    public StringFilter getMessage() {
        return message;
    }

    public StringFilter message() {
        if (message == null) {
            message = new StringFilter();
        }
        return message;
    }

    public void setMessage(StringFilter message) {
        this.message = message;
    }

    public ZonedDateTimeFilter getCallUpDate() {
        return callUpDate;
    }

    public ZonedDateTimeFilter callUpDate() {
        if (callUpDate == null) {
            callUpDate = new ZonedDateTimeFilter();
        }
        return callUpDate;
    }

    public void setCallUpDate(ZonedDateTimeFilter callUpDate) {
        this.callUpDate = callUpDate;
    }

    public BooleanFilter getReadStatus() {
        return readStatus;
    }

    public BooleanFilter readStatus() {
        if (readStatus == null) {
            readStatus = new BooleanFilter();
        }
        return readStatus;
    }

    public void setReadStatus(BooleanFilter readStatus) {
        this.readStatus = readStatus;
    }

    public EnumPriorityFilter getAttention() {
        return attention;
    }

    public EnumPriorityFilter attention() {
        if (attention == null) {
            attention = new EnumPriorityFilter();
        }
        return attention;
    }

    public void setAttention(EnumPriorityFilter attention) {
        this.attention = attention;
    }

    public EnumNotificationTypeFilter getType() {
        return type;
    }

    public EnumNotificationTypeFilter type() {
        if (type == null) {
            type = new EnumNotificationTypeFilter();
        }
        return type;
    }

    public void setType(EnumNotificationTypeFilter type) {
        this.type = type;
    }

    public LongFilter getNotificationCandidateId() {
        return notificationCandidateId;
    }

    public LongFilter notificationCandidateId() {
        if (notificationCandidateId == null) {
            notificationCandidateId = new LongFilter();
        }
        return notificationCandidateId;
    }

    public void setNotificationCandidateId(LongFilter notificationCandidateId) {
        this.notificationCandidateId = notificationCandidateId;
    }

    public LongFilter getNotificationCompanyId() {
        return notificationCompanyId;
    }

    public LongFilter notificationCompanyId() {
        if (notificationCompanyId == null) {
            notificationCompanyId = new LongFilter();
        }
        return notificationCompanyId;
    }

    public void setNotificationCompanyId(LongFilter notificationCompanyId) {
        this.notificationCompanyId = notificationCompanyId;
    }

    public LongFilter getCandidateId() {
        return candidateId;
    }

    public LongFilter candidateId() {
        if (candidateId == null) {
            candidateId = new LongFilter();
        }
        return candidateId;
    }

    public void setCandidateId(LongFilter candidateId) {
        this.candidateId = candidateId;
    }

    public LongFilter getCompanyId() {
        return companyId;
    }

    public LongFilter companyId() {
        if (companyId == null) {
            companyId = new LongFilter();
        }
        return companyId;
    }

    public void setCompanyId(LongFilter companyId) {
        this.companyId = companyId;
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
        final NotificationCriteria that = (NotificationCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(message, that.message) &&
            Objects.equals(callUpDate, that.callUpDate) &&
            Objects.equals(readStatus, that.readStatus) &&
            Objects.equals(attention, that.attention) &&
            Objects.equals(type, that.type) &&
            Objects.equals(notificationCandidateId, that.notificationCandidateId) &&
            Objects.equals(notificationCompanyId, that.notificationCompanyId) &&
            Objects.equals(candidateId, that.candidateId) &&
            Objects.equals(companyId, that.companyId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            message,
            callUpDate,
            readStatus,
            attention,
            type,
            notificationCandidateId,
            notificationCompanyId,
            candidateId,
            companyId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NotificationCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (message != null ? "message=" + message + ", " : "") +
            (callUpDate != null ? "callUpDate=" + callUpDate + ", " : "") +
            (readStatus != null ? "readStatus=" + readStatus + ", " : "") +
            (attention != null ? "attention=" + attention + ", " : "") +
            (type != null ? "type=" + type + ", " : "") +
            (notificationCandidateId != null ? "notificationCandidateId=" + notificationCandidateId + ", " : "") +
            (notificationCompanyId != null ? "notificationCompanyId=" + notificationCompanyId + ", " : "") +
            (candidateId != null ? "candidateId=" + candidateId + ", " : "") +
            (companyId != null ? "companyId=" + companyId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
