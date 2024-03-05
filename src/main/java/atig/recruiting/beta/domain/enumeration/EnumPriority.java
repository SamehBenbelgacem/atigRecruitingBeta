package atig.recruiting.beta.domain.enumeration;

/**
 * The EnumPriority enumeration.
 */
public enum EnumPriority {
    LOW("Low"),
    MEDUIM("Meduim"),
    HIGH("High");

    private final String value;

    EnumPriority(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
