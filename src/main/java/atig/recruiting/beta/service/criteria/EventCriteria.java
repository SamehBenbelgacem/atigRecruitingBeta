package atig.recruiting.beta.service.criteria;

import atig.recruiting.beta.domain.enumeration.EnumPriority;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link atig.recruiting.beta.domain.Event} entity. This class is used
 * in {@link atig.recruiting.beta.web.rest.EventResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /events?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EventCriteria implements Serializable, Criteria {

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

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter title;

    private StringFilter externalParticipants;

    private ZonedDateTimeFilter date;

    private DurationFilter duration;

    private StringFilter description;

    private EnumPriorityFilter priority;

    private LongFilter userId;

    private Boolean distinct;

    public EventCriteria() {}

    public EventCriteria(EventCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.externalParticipants = other.externalParticipants == null ? null : other.externalParticipants.copy();
        this.date = other.date == null ? null : other.date.copy();
        this.duration = other.duration == null ? null : other.duration.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.priority = other.priority == null ? null : other.priority.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public EventCriteria copy() {
        return new EventCriteria(this);
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

    public StringFilter getTitle() {
        return title;
    }

    public StringFilter title() {
        if (title == null) {
            title = new StringFilter();
        }
        return title;
    }

    public void setTitle(StringFilter title) {
        this.title = title;
    }

    public StringFilter getExternalParticipants() {
        return externalParticipants;
    }

    public StringFilter externalParticipants() {
        if (externalParticipants == null) {
            externalParticipants = new StringFilter();
        }
        return externalParticipants;
    }

    public void setExternalParticipants(StringFilter externalParticipants) {
        this.externalParticipants = externalParticipants;
    }

    public ZonedDateTimeFilter getDate() {
        return date;
    }

    public ZonedDateTimeFilter date() {
        if (date == null) {
            date = new ZonedDateTimeFilter();
        }
        return date;
    }

    public void setDate(ZonedDateTimeFilter date) {
        this.date = date;
    }

    public DurationFilter getDuration() {
        return duration;
    }

    public DurationFilter duration() {
        if (duration == null) {
            duration = new DurationFilter();
        }
        return duration;
    }

    public void setDuration(DurationFilter duration) {
        this.duration = duration;
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

    public EnumPriorityFilter getPriority() {
        return priority;
    }

    public EnumPriorityFilter priority() {
        if (priority == null) {
            priority = new EnumPriorityFilter();
        }
        return priority;
    }

    public void setPriority(EnumPriorityFilter priority) {
        this.priority = priority;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public LongFilter userId() {
        if (userId == null) {
            userId = new LongFilter();
        }
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
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
        final EventCriteria that = (EventCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(externalParticipants, that.externalParticipants) &&
            Objects.equals(date, that.date) &&
            Objects.equals(duration, that.duration) &&
            Objects.equals(description, that.description) &&
            Objects.equals(priority, that.priority) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, externalParticipants, date, duration, description, priority, userId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EventCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (title != null ? "title=" + title + ", " : "") +
            (externalParticipants != null ? "externalParticipants=" + externalParticipants + ", " : "") +
            (date != null ? "date=" + date + ", " : "") +
            (duration != null ? "duration=" + duration + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (priority != null ? "priority=" + priority + ", " : "") +
            (userId != null ? "userId=" + userId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
