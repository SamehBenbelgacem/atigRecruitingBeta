package atig.recruiting.beta.service.dto;

import atig.recruiting.beta.domain.enumeration.EnumEmailType;
import jakarta.persistence.Lob;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link atig.recruiting.beta.domain.SubEmail} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SubEmailDTO implements Serializable {

    private Long id;

    private String from;

    private String recipients;

    private String text;

    private EnumEmailType type;

    private ZonedDateTime date;

    private ZonedDateTime snoozedTo;

    private String signatureText;

    @Lob
    private byte[] signatureImage;

    private String signatureImageContentType;
    private EmailDTO subEmailEmail;

    private EmailDTO email;

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

    public EmailDTO getSubEmailEmail() {
        return subEmailEmail;
    }

    public void setSubEmailEmail(EmailDTO subEmailEmail) {
        this.subEmailEmail = subEmailEmail;
    }

    public EmailDTO getEmail() {
        return email;
    }

    public void setEmail(EmailDTO email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SubEmailDTO)) {
            return false;
        }

        SubEmailDTO subEmailDTO = (SubEmailDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, subEmailDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SubEmailDTO{" +
            "id=" + getId() +
            ", from='" + getFrom() + "'" +
            ", recipients='" + getRecipients() + "'" +
            ", text='" + getText() + "'" +
            ", type='" + getType() + "'" +
            ", date='" + getDate() + "'" +
            ", snoozedTo='" + getSnoozedTo() + "'" +
            ", signatureText='" + getSignatureText() + "'" +
            ", signatureImage='" + getSignatureImage() + "'" +
            ", subEmailEmail=" + getSubEmailEmail() +
            ", email=" + getEmail() +
            "}";
    }
}
