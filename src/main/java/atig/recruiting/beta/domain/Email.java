package atig.recruiting.beta.domain;

import atig.recruiting.beta.domain.enumeration.EnumEmailType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A Email.
 */
@Entity
@Table(name = "email")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Email implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "jhi_from")
    private String from;

    @Column(name = "recipients")
    private String recipients;

    @Column(name = "subject")
    private String subject;

    @Column(name = "text")
    private String text;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private EnumEmailType type;

    @Column(name = "date")
    private ZonedDateTime date;

    @Column(name = "snoozed_to")
    private ZonedDateTime snoozedTo;

    @Column(name = "folder")
    private String folder;

    @Column(name = "signature_text")
    private String signatureText;

    @Lob
    @Column(name = "signature_image")
    private byte[] signatureImage;

    @Column(name = "signature_image_content_type")
    private String signatureImageContentType;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "email")
    @JsonIgnoreProperties(
        value = { "candidateDocs", "noteDocs", "emailDocs", "candidateAdditionalInfos", "note", "email" },
        allowSetters = true
    )
    private Set<ObjectContainingFile> joinedFiles = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "email")
    @JsonIgnoreProperties(value = { "subEmailEmail", "email" }, allowSetters = true)
    private Set<SubEmail> subEmails = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "emails" }, allowSetters = true)
    private Emailcredentials emailEmailcredentials;

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
    private Candidate emailCandidate;

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
    private Company emailCompany;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "emails" }, allowSetters = true)
    private Emailcredentials emailcredentials;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Email id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFrom() {
        return this.from;
    }

    public Email from(String from) {
        this.setFrom(from);
        return this;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getRecipients() {
        return this.recipients;
    }

    public Email recipients(String recipients) {
        this.setRecipients(recipients);
        return this;
    }

    public void setRecipients(String recipients) {
        this.recipients = recipients;
    }

    public String getSubject() {
        return this.subject;
    }

    public Email subject(String subject) {
        this.setSubject(subject);
        return this;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return this.text;
    }

    public Email text(String text) {
        this.setText(text);
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public EnumEmailType getType() {
        return this.type;
    }

    public Email type(EnumEmailType type) {
        this.setType(type);
        return this;
    }

    public void setType(EnumEmailType type) {
        this.type = type;
    }

    public ZonedDateTime getDate() {
        return this.date;
    }

    public Email date(ZonedDateTime date) {
        this.setDate(date);
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public ZonedDateTime getSnoozedTo() {
        return this.snoozedTo;
    }

    public Email snoozedTo(ZonedDateTime snoozedTo) {
        this.setSnoozedTo(snoozedTo);
        return this;
    }

    public void setSnoozedTo(ZonedDateTime snoozedTo) {
        this.snoozedTo = snoozedTo;
    }

    public String getFolder() {
        return this.folder;
    }

    public Email folder(String folder) {
        this.setFolder(folder);
        return this;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getSignatureText() {
        return this.signatureText;
    }

    public Email signatureText(String signatureText) {
        this.setSignatureText(signatureText);
        return this;
    }

    public void setSignatureText(String signatureText) {
        this.signatureText = signatureText;
    }

    public byte[] getSignatureImage() {
        return this.signatureImage;
    }

    public Email signatureImage(byte[] signatureImage) {
        this.setSignatureImage(signatureImage);
        return this;
    }

    public void setSignatureImage(byte[] signatureImage) {
        this.signatureImage = signatureImage;
    }

    public String getSignatureImageContentType() {
        return this.signatureImageContentType;
    }

    public Email signatureImageContentType(String signatureImageContentType) {
        this.signatureImageContentType = signatureImageContentType;
        return this;
    }

    public void setSignatureImageContentType(String signatureImageContentType) {
        this.signatureImageContentType = signatureImageContentType;
    }

    public Set<ObjectContainingFile> getJoinedFiles() {
        return this.joinedFiles;
    }

    public void setJoinedFiles(Set<ObjectContainingFile> objectContainingFiles) {
        if (this.joinedFiles != null) {
            this.joinedFiles.forEach(i -> i.setEmail(null));
        }
        if (objectContainingFiles != null) {
            objectContainingFiles.forEach(i -> i.setEmail(this));
        }
        this.joinedFiles = objectContainingFiles;
    }

    public Email joinedFiles(Set<ObjectContainingFile> objectContainingFiles) {
        this.setJoinedFiles(objectContainingFiles);
        return this;
    }

    public Email addJoinedFiles(ObjectContainingFile objectContainingFile) {
        this.joinedFiles.add(objectContainingFile);
        objectContainingFile.setEmail(this);
        return this;
    }

    public Email removeJoinedFiles(ObjectContainingFile objectContainingFile) {
        this.joinedFiles.remove(objectContainingFile);
        objectContainingFile.setEmail(null);
        return this;
    }

    public Set<SubEmail> getSubEmails() {
        return this.subEmails;
    }

    public void setSubEmails(Set<SubEmail> subEmails) {
        if (this.subEmails != null) {
            this.subEmails.forEach(i -> i.setEmail(null));
        }
        if (subEmails != null) {
            subEmails.forEach(i -> i.setEmail(this));
        }
        this.subEmails = subEmails;
    }

    public Email subEmails(Set<SubEmail> subEmails) {
        this.setSubEmails(subEmails);
        return this;
    }

    public Email addSubEmails(SubEmail subEmail) {
        this.subEmails.add(subEmail);
        subEmail.setEmail(this);
        return this;
    }

    public Email removeSubEmails(SubEmail subEmail) {
        this.subEmails.remove(subEmail);
        subEmail.setEmail(null);
        return this;
    }

    public Emailcredentials getEmailEmailcredentials() {
        return this.emailEmailcredentials;
    }

    public void setEmailEmailcredentials(Emailcredentials emailcredentials) {
        this.emailEmailcredentials = emailcredentials;
    }

    public Email emailEmailcredentials(Emailcredentials emailcredentials) {
        this.setEmailEmailcredentials(emailcredentials);
        return this;
    }

    public Candidate getEmailCandidate() {
        return this.emailCandidate;
    }

    public void setEmailCandidate(Candidate candidate) {
        this.emailCandidate = candidate;
    }

    public Email emailCandidate(Candidate candidate) {
        this.setEmailCandidate(candidate);
        return this;
    }

    public Company getEmailCompany() {
        return this.emailCompany;
    }

    public void setEmailCompany(Company company) {
        this.emailCompany = company;
    }

    public Email emailCompany(Company company) {
        this.setEmailCompany(company);
        return this;
    }

    public Candidate getCandidate() {
        return this.candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public Email candidate(Candidate candidate) {
        this.setCandidate(candidate);
        return this;
    }

    public Company getCompany() {
        return this.company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Email company(Company company) {
        this.setCompany(company);
        return this;
    }

    public Emailcredentials getEmailcredentials() {
        return this.emailcredentials;
    }

    public void setEmailcredentials(Emailcredentials emailcredentials) {
        this.emailcredentials = emailcredentials;
    }

    public Email emailcredentials(Emailcredentials emailcredentials) {
        this.setEmailcredentials(emailcredentials);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Email)) {
            return false;
        }
        return getId() != null && getId().equals(((Email) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Email{" +
            "id=" + getId() +
            ", from='" + getFrom() + "'" +
            ", recipients='" + getRecipients() + "'" +
            ", subject='" + getSubject() + "'" +
            ", text='" + getText() + "'" +
            ", type='" + getType() + "'" +
            ", date='" + getDate() + "'" +
            ", snoozedTo='" + getSnoozedTo() + "'" +
            ", folder='" + getFolder() + "'" +
            ", signatureText='" + getSignatureText() + "'" +
            ", signatureImage='" + getSignatureImage() + "'" +
            ", signatureImageContentType='" + getSignatureImageContentType() + "'" +
            "}";
    }
}
