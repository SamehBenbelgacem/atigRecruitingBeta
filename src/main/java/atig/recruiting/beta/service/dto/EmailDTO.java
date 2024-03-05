package atig.recruiting.beta.service.dto;

import atig.recruiting.beta.domain.enumeration.EnumEmailType;
import jakarta.persistence.Lob;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link atig.recruiting.beta.domain.Email} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmailDTO implements Serializable {

    private Long id;

    private String from;

    private String recipients;

    private String subject;

    private String text;

    private EnumEmailType type;

    private ZonedDateTime date;

    private ZonedDateTime snoozedTo;

    private String folder;

    private String signatureText;

    @Lob
    private byte[] signatureImage;

    private String signatureImageContentType;
    private EmailcredentialsDTO emailEmailcredentials;

    private CandidateDTO emailCandidate;

    private CompanyDTO emailCompany;

    private CandidateDTO candidate;

    private CompanyDTO company;

    private EmailcredentialsDTO emailcredentials;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getRecipients() {
        return recipients;
    }

    public void setRecipients(String recipients) {
        this.recipients = recipients;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public EnumEmailType getType() {
        return type;
    }

    public void setType(EnumEmailType type) {
        this.type = type;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public ZonedDateTime getSnoozedTo() {
        return snoozedTo;
    }

    public void setSnoozedTo(ZonedDateTime snoozedTo) {
        this.snoozedTo = snoozedTo;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getSignatureText() {
        return signatureText;
    }

    public void setSignatureText(String signatureText) {
        this.signatureText = signatureText;
    }

    public byte[] getSignatureImage() {
        return signatureImage;
    }

    public void setSignatureImage(byte[] signatureImage) {
        this.signatureImage = signatureImage;
    }

    public String getSignatureImageContentType() {
        return signatureImageContentType;
    }

    public void setSignatureImageContentType(String signatureImageContentType) {
        this.signatureImageContentType = signatureImageContentType;
    }

    public EmailcredentialsDTO getEmailEmailcredentials() {
        return emailEmailcredentials;
    }

    public void setEmailEmailcredentials(EmailcredentialsDTO emailEmailcredentials) {
        this.emailEmailcredentials = emailEmailcredentials;
    }

    public CandidateDTO getEmailCandidate() {
        return emailCandidate;
    }

    public void setEmailCandidate(CandidateDTO emailCandidate) {
        this.emailCandidate = emailCandidate;
    }

    public CompanyDTO getEmailCompany() {
        return emailCompany;
    }

    public void setEmailCompany(CompanyDTO emailCompany) {
        this.emailCompany = emailCompany;
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

    public EmailcredentialsDTO getEmailcredentials() {
        return emailcredentials;
    }

    public void setEmailcredentials(EmailcredentialsDTO emailcredentials) {
        this.emailcredentials = emailcredentials;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmailDTO)) {
            return false;
        }

        EmailDTO emailDTO = (EmailDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, emailDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmailDTO{" +
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
            ", emailEmailcredentials=" + getEmailEmailcredentials() +
            ", emailCandidate=" + getEmailCandidate() +
            ", emailCompany=" + getEmailCompany() +
            ", candidate=" + getCandidate() +
            ", company=" + getCompany() +
            ", emailcredentials=" + getEmailcredentials() +
            "}";
    }
}
