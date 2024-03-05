package atig.recruiting.beta.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Note.
 */
@Entity
@Table(name = "note")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Note implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "date")
    private Instant date;

    @Column(name = "description")
    private String description;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "note")
    @JsonIgnoreProperties(
        value = { "candidateDocs", "noteDocs", "emailDocs", "candidateAdditionalInfos", "note", "email" },
        allowSetters = true
    )
    private Set<ObjectContainingFile> documents = new HashSet<>();

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
    private Company noteCompany;

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
    private Candidate noteCandidate;

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

    public Note id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Note title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Instant getDate() {
        return this.date;
    }

    public Note date(Instant date) {
        this.setDate(date);
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public String getDescription() {
        return this.description;
    }

    public Note description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<ObjectContainingFile> getDocuments() {
        return this.documents;
    }

    public void setDocuments(Set<ObjectContainingFile> objectContainingFiles) {
        if (this.documents != null) {
            this.documents.forEach(i -> i.setNote(null));
        }
        if (objectContainingFiles != null) {
            objectContainingFiles.forEach(i -> i.setNote(this));
        }
        this.documents = objectContainingFiles;
    }

    public Note documents(Set<ObjectContainingFile> objectContainingFiles) {
        this.setDocuments(objectContainingFiles);
        return this;
    }

    public Note addDocuments(ObjectContainingFile objectContainingFile) {
        this.documents.add(objectContainingFile);
        objectContainingFile.setNote(this);
        return this;
    }

    public Note removeDocuments(ObjectContainingFile objectContainingFile) {
        this.documents.remove(objectContainingFile);
        objectContainingFile.setNote(null);
        return this;
    }

    public Company getNoteCompany() {
        return this.noteCompany;
    }

    public void setNoteCompany(Company company) {
        this.noteCompany = company;
    }

    public Note noteCompany(Company company) {
        this.setNoteCompany(company);
        return this;
    }

    public Candidate getNoteCandidate() {
        return this.noteCandidate;
    }

    public void setNoteCandidate(Candidate candidate) {
        this.noteCandidate = candidate;
    }

    public Note noteCandidate(Candidate candidate) {
        this.setNoteCandidate(candidate);
        return this;
    }

    public Candidate getCandidate() {
        return this.candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public Note candidate(Candidate candidate) {
        this.setCandidate(candidate);
        return this;
    }

    public Company getCompany() {
        return this.company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Note company(Company company) {
        this.setCompany(company);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Note)) {
            return false;
        }
        return getId() != null && getId().equals(((Note) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Note{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", date='" + getDate() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
