package atig.recruiting.beta.domain;

import atig.recruiting.beta.domain.enumeration.EnumPriority;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Duration;
import java.time.ZonedDateTime;

/**
 * A Event.
 */
@Entity
@Table(name = "event")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "external_participants")
    private String externalParticipants;

    @Column(name = "date")
    private ZonedDateTime date;

    @Column(name = "duration")
    private Duration duration;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private EnumPriority priority;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Event id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Event title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExternalParticipants() {
        return this.externalParticipants;
    }

    public Event externalParticipants(String externalParticipants) {
        this.setExternalParticipants(externalParticipants);
        return this;
    }

    public void setExternalParticipants(String externalParticipants) {
        this.externalParticipants = externalParticipants;
    }

    public ZonedDateTime getDate() {
        return this.date;
    }

    public Event date(ZonedDateTime date) {
        this.setDate(date);
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public Duration getDuration() {
        return this.duration;
    }

    public Event duration(Duration duration) {
        this.setDuration(duration);
        return this;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return this.description;
    }

    public Event description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EnumPriority getPriority() {
        return this.priority;
    }

    public Event priority(EnumPriority priority) {
        this.setPriority(priority);
        return this;
    }

    public void setPriority(EnumPriority priority) {
        this.priority = priority;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Event user(User user) {
        this.setUser(user);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Event)) {
            return false;
        }
        return getId() != null && getId().equals(((Event) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Event{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", externalParticipants='" + getExternalParticipants() + "'" +
            ", date='" + getDate() + "'" +
            ", duration='" + getDuration() + "'" +
            ", description='" + getDescription() + "'" +
            ", priority='" + getPriority() + "'" +
            "}";
    }
}
