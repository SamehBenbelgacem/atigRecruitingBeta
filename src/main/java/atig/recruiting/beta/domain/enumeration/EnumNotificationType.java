package atig.recruiting.beta.domain.enumeration;

/**
 * The EnumNotificationType enumeration.
 */
public enum EnumNotificationType {
    GENERAl("General"),
    FORCOMPANY("For Company"),
    FORCANDIDATE("For Candidate");

    private final String value;

    EnumNotificationType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
