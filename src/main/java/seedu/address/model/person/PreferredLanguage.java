package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Locale;

/**
 * Represents a person's preferred language in the address book.
 * Guarantees: immutable; value is always valid (non-null and matches validation constraints).
 */
public class PreferredLanguage {

    public static final String MESSAGE_CONSTRAINTS =
            "Preferred language can only contain letters and spaces, and should not be blank";

    public static final String VALIDATION_REGEX = "[\\p{L} ]+";

    public final String language;

    /**
     * Constructs a {@code PreferredLanguage}.
     * @param language The language string. Must be non-null and valid.
     */
    public PreferredLanguage(String language) {
        requireNonNull(language);
        checkArgument(isValidLanguage(language), MESSAGE_CONSTRAINTS);
        this.language = language;
    }

    /**
     * Returns true if a given string is a valid language.
     */
    public static boolean isValidLanguage(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return language;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof PreferredLanguage
                && language.equalsIgnoreCase(((PreferredLanguage) other).language));
    }

    @Override
    public int hashCode() {
        return language.toLowerCase(Locale.ROOT).hashCode();
    }

    public String getPreferredLanguage() {
        return language;
    }

}

