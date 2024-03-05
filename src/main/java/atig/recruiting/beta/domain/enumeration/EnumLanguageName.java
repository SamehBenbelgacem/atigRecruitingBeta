package atig.recruiting.beta.domain.enumeration;

/**
 * The EnumLanguageName enumeration.
 */
public enum EnumLanguageName {
    GERMAN("German"),
    ENGLISH("English"),
    FRENCH("French"),
    ARABIC("Arabic");

    private final String value;

    EnumLanguageName(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
