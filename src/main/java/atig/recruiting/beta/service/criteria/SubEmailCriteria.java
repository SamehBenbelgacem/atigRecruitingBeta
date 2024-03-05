package atig.recruiting.beta.service.criteria;

import atig.recruiting.beta.domain.enumeration.EnumEmailType;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link atig.recruiting.beta.domain.SubEmail} entity. This class is used
 * in {@link atig.recruiting.beta.web.rest.SubEmailResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /sub-emails?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SubEmailCriteria implements Serializable, Criteria {

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

    private StringFilter text;

    private EnumEmailTypeFilter type;

    private ZonedDateTimeFilter date;

    private ZonedDateTimeFilter snoozedTo;

    private StringFilter signatureText;

    private LongFilter subEmailEmailId;

    private LongFilter emailId;

    private Boolean distinct;

    public SubEmailCriteria() {}

    public SubEmailCriteria(SubEmailCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.from = other.from == null ? null : other.from.copy();
        this.recipients = other.recipients == null ? null : other.recipients.copy();
        this.text = other.text == null ? null : other.text.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.date = other.date == null ? null : other.date.copy();
        this.snoozedTo = other.snoozedTo == null ? null : other.snoozedTo.copy();
        this.signatureText = other.signatureText == null ? null : other.signatureText.copy();
        this.subEmailEmailId = other.subEmailEmailId == null ? null : other.subEmailEmailId.copy();
        this.emailId = other.emailId == null ? null : other.emailId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public SubEmailCriteria copy() {
        return new SubEmailCriteria(this);
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

    public LongFilter getSubEmailEmailId() {
        return subEmailEmailId;
    }

    public LongFilter subEmailEmailId() {
        if (subEmailEmailId == null) {
            subEmailEmailId = new LongFilter();
        }
        return subEmailEmailId;
    }

    public void setSubEmailEmailId(LongFilter subEmailEmailId) {
        this.subEmailEmailId = subEmailEmailId;
    }

    public LongFilter getEmailId() {
        return emailId;
    }

    public LongFilter emailId() {
        if (emailId == null) {
            emailId = new LongFilter();
        }
        return emailId;
    }

    public void setEmailId(LongFilter emailId) {
        this.emailId = emailId;
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
        final SubEmailCriteria that = (SubEmailCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(from, that.from) &&
            Objects.equals(recipients, that.recipients) &&
            Objects.equals(text, that.text) &&
            Objects.equals(type, that.type) &&
            Objects.equals(date, that.date) &&
            Objects.equals(snoozedTo, that.snoozedTo) &&
            Objects.equals(signatureText, that.signatureText) &&
            Objects.equals(subEmailEmailId, that.subEmailEmailId) &&
            Objects.equals(emailId, that.emailId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, from, recipients, text, type, date, snoozedTo, signatureText, subEmailEmailId, emailId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SubEmailCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (from != null ? "from=" + from + ", " : "") +
            (recipients != null ? "recipients=" + recipients + ", " : "") +
            (text != null ? "text=" + text + ", " : "") +
            (type != null ? "type=" + type + ", " : "") +
            (date != null ? "date=" + date + ", " : "") +
            (snoozedTo != null ? "snoozedTo=" + snoozedTo + ", " : "") +
            (signatureText != null ? "signatureText=" + signatureText + ", " : "") +
            (subEmailEmailId != null ? "subEmailEmailId=" + subEmailEmailId + ", " : "") +
            (emailId != null ? "emailId=" + emailId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
