package atig.recruiting.beta.domain;

import atig.recruiting.beta.domain.enumeration.EnumEmailType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A SubEmail.
 */
@Entity
@Table(name = "sub_email")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SubEmail implements Serializable {

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

    @Column(name = "text")
    private String text;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private EnumEmailType type;

    @Column(name = "date")
    private ZonedDateTime date;

    @Column(name = "snoozed_to")
    private ZonedDateTime snoozedTo;

    @Column(name = "signature_text")
    private String signatureText;

    @Lob
    @Column(name = "signature_image")
    private byte[] signatureImage;

    @Column(name = "signature_image_content_type")
    private String signatureImageContentType;

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
    private Email subEmailEmail;

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

    public SubEmail id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFrom() {
        return this.from;
    }

    public SubEmail from(String from) {
        this.setFrom(from);
        return this;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getRecipients() {
        return this.recipients;
    }

    public SubEmail recipients(String recipients) {
        this.setRecipients(recipients);
        return this;
    }

    public void setRecipients(String recipients) {
        this.recipients = recipients;
    }

    public String getText() {
        return this.text;
    }

    public SubEmail text(String text) {
        this.setText(text);
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public EnumEmailType getType() {
        return this.type;
    }

    public SubEmail type(EnumEmailType type) {
        this.setType(type);
        return this;
    }

    public void setType(EnumEmailType type) {
        this.type = type;
    }

    public ZonedDateTime getDate() {
        return this.date;
    }

    public SubEmail date(ZonedDateTime date) {
        this.setDate(date);
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public ZonedDateTime getSnoozedTo() {
        return this.snoozedTo;
    }

    public SubEmail snoozedTo(ZonedDateTime snoozedTo) {
        this.setSnoozedTo(snoozedTo);
        return this;
    }

    public void setSnoozedTo(ZonedDateTime snoozedTo) {
        this.snoozedTo = snoozedTo;
    }

    public String getSignatureText() {
        return this.signatureText;
    }

    public SubEmail signatureText(String signatureText) {
        this.setSignatureText(signatureText);
        return this;
    }

    public void setSignatureText(String signatureText) {
        this.signatureText = signatureText;
    }

    public byte[] getSignatureImage() {
        return this.signatureImage;
    }

    public SubEmail signatureImage(byte[] signatureImage) {
        this.setSignatureImage(signatureImage);
        return this;
    }

    public void setSignatureImage(byte[] signatureImage) {
        this.signatureImage = signatureImage;
    }

    public String getSignatureImageContentType() {
        return this.signatureImageContentType;
    }

    public SubEmail signatureImageContentType(String signatureImageContentType) {
        this.signatureImageContentType = signatureImageContentType;
        return this;
    }

    public void setSignatureImageContentType(String signatureImageContentType) {
        this.signatureImageContentType = signatureImageContentType;
    }

    public Email getSubEmailEmail() {
        return this.subEmailEmail;
    }

    public void setSubEmailEmail(Email email) {
        this.subEmailEmail = email;
    }

    public SubEmail subEmailEmail(Email email) {
        this.setSubEmailEmail(email);
        return this;
    }

    public Email getEmail() {
        return this.email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public SubEmail email(Email email) {
        this.setEmail(email);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SubEmail)) {
            return false;
        }
        return getId() != null && getId().equals(((SubEmail) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SubEmail{" +
            "id=" + getId() +
            ", from='" + getFrom() + "'" +
            ", recipients='" + getRecipients() + "'" +
            ", text='" + getText() + "'" +
            ", type='" + getType() + "'" +
            ", date='" + getDate() + "'" +
            ", snoozedTo='" + getSnoozedTo() + "'" +
            ", signatureText='" + getSignatureText() + "'" +
            ", signatureImage='" + getSignatureImage() + "'" +
            ", signatureImageContentType='" + getSignatureImageContentType() + "'" +
            "}";
    }
}
