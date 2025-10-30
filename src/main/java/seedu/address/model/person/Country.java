package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/**
 * Represents a Person's country in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidCountry(String)}
 */
public class Country {

    public static final String MESSAGE_CONSTRAINTS =
        "Country names are case-insensitive and should match a valid country name (type 'help' to view the list).";

    /*
     * Allows alphabetic words separated by spaces.
     */
    public static final String VALIDATION_REGEX =
            "([\\p{L}][\\p{L} '\\-,.&()]*|\\p{L}+([\\p{L} '\\-,.&()]*[\\p{L}])?)?";

    /*
     * Construct set of valid country names for validation matching.
     */
    private static final Set<String> VALID_COUNTRY_NAMES = new HashSet<>();

    static {
        for (String countryCode : Locale.getISOCountries()) {
            Locale locale = new Locale("", countryCode);
            String countryName = locale.getDisplayCountry(Locale.ENGLISH);
            VALID_COUNTRY_NAMES.add(countryName);
        }
    }

    public final String value;

    /**
     * Constructs a {@code Country}.
     *
     * @param country A valid country.
     */
    public Country(String country) {
        requireNonNull(country);
        checkArgument(isValidCountry(country), MESSAGE_CONSTRAINTS);

        this.value = normalizeCountryName(country);
    }

    /**
     * Normalizes the given country name to its canonical capitalized form.
     * For example, "singapore" → "Singapore", "united states" → "United States".
     */
    private static String normalizeCountryName(String input) {
        if (input.trim().isEmpty()) {
            return "";
        }

        for (String validName : VALID_COUNTRY_NAMES) {
            if (validName.equalsIgnoreCase(input.trim())) {
                return validName; // Return the canonical form from Locale list
            }
        }

        return input.trim(); // fallback
    }

    /**
     * Returns true if a given string is a valid country name.
     */
    public static boolean isValidCountry(String test) {
        requireNonNull(test);
        String trimmed = test.trim();

        if (trimmed.isEmpty()) {
            return true; // allow empty
        }

        // check if country (case-insensitive) exists in the valid list
        boolean inCountryList = VALID_COUNTRY_NAMES.stream()
            .anyMatch(name -> name.equalsIgnoreCase(trimmed));

        return trimmed.matches(VALIDATION_REGEX) && inCountryList;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Country)) {
            return false;
        }

        Country otherCountry = (Country) other;
        return value.equals(otherCountry.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
