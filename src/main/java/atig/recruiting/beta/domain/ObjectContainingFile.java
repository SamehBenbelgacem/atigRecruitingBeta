package atig.recruiting.beta.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A ObjectContainingFile.
 */
@Entity
@Table(name = "object_containing_file")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ObjectContainingFile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Lob
    @Column(name = "file")
    private byte[] file;

    @Column(name = "file_content_type")
    private String fileContentType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "documents", "candidate" }, allowSetters = true)
    private CandidateAdditionalInfos candidateDocs;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "documents", "noteCompany", "noteCandidate", "candidate", "company" }, allowSetters = true)
    private Note noteDocs;

    @ManyToOne(fetch = FetchType.LAZY)
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
    private Email emailDocs;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "documents", "candidate" }, allowSetters = true)
    private CandidateAdditionalInfos candidateAdditionalInfos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "documents", "noteCompany", "noteCandidate", "candidate", "company" }, allowSetters = true)
    private Note note;

    @ManyToOne(fetch = FetchType.LAZY)
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
    private Email email;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ObjectContainingFile id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getFile() {
        return this.file;
    }

    public ObjectContainingFile file(byte[] file) {
        this.setFile(file);
        return this;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getFileContentType() {
        return this.fileContentType;
    }

    public ObjectContainingFile fileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
        return this;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    public CandidateAdditionalInfos getCandidateDocs() {
        return this.candidateDocs;
    }

    public void setCandidateDocs(CandidateAdditionalInfos candidateAdditionalInfos) {
        this.candidateDocs = candidateAdditionalInfos;
    }

    public ObjectContainingFile candidateDocs(CandidateAdditionalInfos candidateAdditionalInfos) {
        this.setCandidateDocs(candidateAdditionalInfos);
        return this;
    }

    public Note getNoteDocs() {
        return this.noteDocs;
    }

    public void setNoteDocs(Note note) {
        this.noteDocs = note;
    }

    public ObjectContainingFile noteDocs(Note note) {
        this.setNoteDocs(note);
        return this;
    }

    public Email getEmailDocs() {
        return this.emailDocs;
    }

    public void setEmailDocs(Email email) {
        this.emailDocs = email;
    }

    public ObjectContainingFile emailDocs(Email email) {
        this.setEmailDocs(email);
        return this;
    }

    public CandidateAdditionalInfos getCandidateAdditionalInfos() {
        return this.candidateAdditionalInfos;
    }

    public void setCandidateAdditionalInfos(CandidateAdditionalInfos candidateAdditionalInfos) {
        this.candidateAdditionalInfos = candidateAdditionalInfos;
    }

    public ObjectContainingFile candidateAdditionalInfos(CandidateAdditionalInfos candidateAdditionalInfos) {
        this.setCandidateAdditionalInfos(candidateAdditionalInfos);
        return this;
    }

    public Note getNote() {
        return this.note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public ObjectContainingFile note(Note note) {
        this.setNote(note);
        return this;
    }

    public Email getEmail() {
        return this.email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public ObjectContainingFile email(Email email) {
        this.setEmail(email);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ObjectContainingFile)) {
            return false;
        }
        return getId() != null && getId().equals(((ObjectContainingFile) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ObjectContainingFile{" +
            "id=" + getId() +
            ", file='" + getFile() + "'" +
            ", fileContentType='" + getFileContentType() + "'" +
            "}";
    }
}
