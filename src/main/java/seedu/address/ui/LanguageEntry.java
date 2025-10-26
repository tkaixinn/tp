package seedu.address.ui;

/**
 * Represents a language entry containing its name, ISO code,
 * and the list of countries where it is used.
 */
public class LanguageEntry {
    private final String name;
    private final String code;
    private final String countriesUsed;

    /**
     * Constructs a {@code LanguageEntry} with the specified name, code, and countries used.
     *
     * @param name the name of the language
     * @param code the ISO code of the language
     * @param countriesUsed a list or description of countries where the language is used
     */
    public LanguageEntry(String name, String code, String countriesUsed) {
        this.name = name;
        this.code = code;
        this.countriesUsed = countriesUsed;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getCountriesUsed() {
        return countriesUsed;
    }
}
