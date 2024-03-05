package atig.recruiting.beta.service.criteria;

import atig.recruiting.beta.domain.enumeration.EnumEmailType;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link atig.recruiting.beta.domain.Email} entity. This class is used
 * in {@link atig.recruiting.beta.web.rest.EmailResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /emails?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmailCriteria implements Serializable, Criteria {

    /**
     * Class for filtering EnumEmailType
     */
    public static class EnumEmailTypeFilter extends Filter<EnumEmailType> {

        public EnumEmailTypeFilter() {}

        public EnumEmailTypeFilter(EnumEmailTypeFilter filter) {
            super(filter);
        }

        @Override
        public EnumEmailTypeFilter copy() {
            return new EnumEmailTypeFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter from;

    private StringFilter recipients;

    private StringFilter subject;

    private StringFilter text;

    private EnumEmailTypeFilter type;

    private ZonedDateTimeFilter date;

    private ZonedDateTimeFilter snoozedTo;

    private StringFilter folder;

    private StringFilter signatureText;

    private LongFilter joinedFilesId;

    private LongFilter subEmailsId;

    private LongFilter emailEmailcredentialsId;

    private LongFilter emailCandidateId;

    private LongFilter emailCompanyId;

    private LongFilter candidateId;

    private LongFilter companyId;

    private LongFilter emailcredentialsId;

    private Boolean distinct;

    public EmailCriteria() {}

    public EmailCriteria(EmailCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.from = other.from == null ? null : other.from.copy();
        this.recipients = other.recipients == null ? null : other.recipients.copy();
        this.subject = other.subject == null ? null : other.subject.copy();
        this.text = other.text == null ? null : other.text.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.date = other.date == null ? null : other.date.copy();
        this.snoozedTo = other.snoozedTo == null ? null : other.snoozedTo.copy();
        this.folder = other.folder == null ? null : other.folder.copy();
        this.signatureText = other.signatureText == null ? null : other.signatureText.copy();
        this.joinedFilesId = other.joinedFilesId == null ? null : other.joinedFilesId.copy();
        this.subEmailsId = other.subEmailsId == null ? null : other.subEmailsId.copy();
        this.emailEmailcredentialsId = other.emailEmailcredentialsId == null ? null : other.emailEmailcredentialsId.copy();
        this.emailCandidateId = other.emailCandidateId == null ? null : other.emailCandidateId.copy();
        this.emailCompanyId = other.emailCompanyId == null ? null : other.emailCompanyId.copy();
        this.candidateId = other.candidateId == null ? null : other.candidateId.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
        this.emailcredentialsId = other.emailcredentialsId == null ? null : other.emailcredentialsId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public EmailCriteria copy() {
        return new EmailCriteria(this);
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

    public StringFilter getFrom() {
        return from;
    }

    public StringFilter from() {
        if (from == null) {
            from = new StringFilter();
        }
        return from;
    }

    public void setFrom(StringFilter from) {
        this.from = from;
    }

    public StringFilter getRecipients() {
        return recipients;
    }

    public StringFilter recipients() {
        if (recipients == null) {
            recipients = new StringFilter();
        }
        return recipients;
    }

    public void setRecipients(StringFilter recipients) {
        this.recipients = recipients;
    }

    public StringFilter getSubject() {
        return subject;
    }

    public StringFilter subject() {
        if (subject == null) {
            subject = new StringFilter();
        }
        return subject;
    }

    public void setSubject(StringFilter subject) {
        this.subject = subject;
    }

    public StringFilter getText() {
        return text;
    }

    public StringFilter text() {
        if (text == null) {
            text = new StringFilter();
        }
        return text;
    }

    public void setText(StringFilter text) {
        this.text = text;
    }

    public EnumEmailTypeFilter getType() {
        return type;
    }

    public EnumEmailTypeFilter type() {
        if (type == null) {
            type = new EnumEmailTypeFilter();
        }
        return type;
    }

    public void setType(EnumEmailTypeFilter type) {
        this.type = type;
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

    public ZonedDateTimeFilter getSnoozedTo() {
        return snoozedTo;
    }

    public ZonedDateTimeFilter snoozedTo() {
        if (snoozedTo == null) {
            snoozedTo = new ZonedDateTimeFilter();
        }
        return snoozedTo;
    }

    public void setSnoozedTo(ZonedDateTimeFilter snoozedTo) {
        this.snoozedTo = snoozedTo;
    }

    public StringFilter getFolder() {
        return folder;
    }

    public StringFilter folder() {
        if (folder == null) {
            folder = new StringFilter();
        }
        return folder;
    }

    public void setFolder(StringFilter folder) {
        this.folder = folder;
    }

    public StringFilter getSignatureText() {
        return signatureText;
    }

    public StringFilter signatureText() {
        if (signatureText == null) {
            signatureText = new StringFilter();
        }
        return signatureText;
    }

    public void setSignatureText(StringFilter signatureText) {
        this.signatureText = signatureText;
    }

    public LongFilter getJoinedFilesId() {
        return joinedFilesId;
    }

    public LongFilter joinedFilesId() {
        if (joinedFilesId == null) {
            joinedFilesId = new LongFilter();
        }
        return joinedFilesId;
    }

    public void setJoinedFilesId(LongFilter joinedFilesId) {
        this.joinedFilesId = joinedFilesId;
    }

    public LongFilter getSubEmailsId() {
        return subEmailsId;
    }

    public LongFilter subEmailsId() {
        if (subEmailsId == null) {
            subEmailsId = new LongFilter();
        }
        return subEmailsId;
    }

    public void setSubEmailsId(LongFilter subEmailsId) {
        this.subEmailsId = subEmailsId;
    }

    public LongFilter getEmailEmailcredentialsId() {
        return emailEmailcredentialsId;
    }

    public LongFilter emailEmailcredentialsId() {
        if (emailEmailcredentialsId == null) {
            emailEmailcredentialsId = new LongFilter();
        }
        return emailEmailcredentialsId;
    }

    public void setEmailEmailcredentialsId(LongFilter emailEmailcredentialsId) {
        this.emailEmailcredentialsId = emailEmailcredentialsId;
    }

    public LongFilter getEmailCandidateId() {
        return emailCandidateId;
    }

    public LongFilter emailCandidateId() {
        if (emailCandidateId == null) {
            emailCandidateId = new LongFilter();
        }
        return emailCandidateId;
    }

    public void setEmailCandidateId(LongFilter emailCandidateId) {
        this.emailCandidateId = emailCandidateId;
    }

    public LongFilter getEmailCompanyId() {
        return emailCompanyId;
    }

    public LongFilter emailCompanyId() {
        if (emailCompanyId == null) {
            emailCompanyId = new LongFilter();
        }
        return emailCompanyId;
    }

    public void setEmailCompanyId(LongFilter emailCompanyId) {
        this.emailCompanyId = emailCompanyId;
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

    public LongFilter getEmailcredentialsId() {
        return emailcredentialsId;
    }

    public LongFilter emailcredentialsId() {
        if (emailcredentialsId == null) {
            emailcredentialsId = new LongFilter();
        }
        return emailcredentialsId;
    }

    public void setEmailcredentialsId(LongFilter emailcredentialsId) {
        this.emailcredentialsId = emailcredentialsId;
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
        final EmailCriteria that = (EmailCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(from, that.from) &&
            Objects.equals(recipients, that.recipients) &&
            Objects.equals(subject, that.subject) &&
            Objects.equals(text, that.text) &&
            Objects.equals(type, that.type) &&
            Objects.equals(date, that.date) &&
            Objects.equals(snoozedTo, that.snoozedTo) &&
            Objects.equals(folder, that.folder) &&
            Objects.equals(signatureText, that.signatureText) &&
            Objects.equals(joinedFilesId, that.joinedFilesId) &&
            Objects.equals(subEmailsId, that.subEmailsId) &&
            Objects.equals(emailEmailcredentialsId, that.emailEmailcredentialsId) &&
            Objects.equals(emailCandidateId, that.emailCandidateId) &&
            Objects.equals(emailCompanyId, that.emailCompanyId) &&
            Objects.equals(candidateId, that.candidateId) &&
            Objects.equals(companyId, that.companyId) &&
            Objects.equals(emailcredentialsId, that.emailcredentialsId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            from,
            recipients,
            subject,
            text,
            type,
            date,
            snoozedTo,
            folder,
            signatureText,
            joinedFilesId,
            subEmailsId,
            emailEmailcredentialsId,
            emailCandidateId,
            emailCompanyId,
            candidateId,
            companyId,
            emailcredentialsId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmailCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (from != null ? "from=" + from + ", " : "") +
            (recipients != null ? "recipients=" + recipients + ", " : "") +
            (subject != null ? "subject=" + subject + ", " : "") +
            (text != null ? "text=" + text + ", " : "") +
            (type != null ? "type=" + type + ", " : "") +
            (date != null ? "date=" + date + ", " : "") +
            (snoozedTo != null ? "snoozedTo=" + snoozedTo + ", " : "") +
            (folder != null ? "folder=" + folder + ", " : "") +
            (signatureText != null ? "signatureText=" + signatureText + ", " : "") +
            (joinedFilesId != null ? "joinedFilesId=" + joinedFilesId + ", " : "") +
            (subEmailsId != null ? "subEmailsId=" + subEmailsId + ", " : "") +
            (emailEmailcredentialsId != null ? "emailEmailcredentialsId=" + emailEmailcredentialsId + ", " : "") +
            (emailCandidateId != null ? "emailCandidateId=" + emailCandidateId + ", " : "") +
            (emailCompanyId != null ? "emailCompanyId=" + emailCompanyId + ", " : "") +
            (candidateId != null ? "candidateId=" + candidateId + ", " : "") +
            (companyId != null ? "companyId=" + companyId + ", " : "") +
            (emailcredentialsId != null ? "emailcredentialsId=" + emailcredentialsId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
