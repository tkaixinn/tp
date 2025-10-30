package seedu.address.ui;

/**
 * Represents a language entry containing its name, ISO code,
 * and the list of countries where it is used.
 */
public class LanguageEntry {
    private final String name;
    private final String greeting;

    /**
     * Constructs a {@code LanguageEntry} with the specified name, code, and countries used.
     *
     * @param name the name of the language
     * @param greeting a list of greetings
     */
    public LanguageEntry(String name, String greeting) {
        this.name = name;
        this.greeting = greeting;
    }

    public String getName() {
        return name;
    }

    public String getGreeting() {
        return greeting;
    }
}
