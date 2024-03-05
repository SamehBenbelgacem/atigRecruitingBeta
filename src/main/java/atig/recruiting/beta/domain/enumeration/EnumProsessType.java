package atig.recruiting.beta.domain.enumeration;

/**
 * The EnumProsessType enumeration.
 */
public enum EnumProsessType {
    FORCOMPANY("For Company"),
    FORCANDIDATE("For Candidate");

    private final String value;

    EnumProsessType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
