package atig.recruiting.beta.domain.enumeration;

/**
 * The EnumEmailType enumeration.
 */
public enum EnumEmailType {
    INBOX("Inbox"),
    SENT("Sent"),
    SNOOZED("Snoozed"),
    DRAFT("Draft");

    private final String value;

    EnumEmailType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
