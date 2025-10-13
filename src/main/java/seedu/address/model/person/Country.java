package seedu.address.model.person;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's country in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Country {

    public static final String MESSAGE_CONSTRAINTS =
            "Country names should only contain letters, spaces, hyphens and apostrophes.";

    /*
     * Allows alphabetic words separated by spaces.
     */
    public static final String VALIDATION_REGEX = "([\\p{L}][\\p{L} '\\-]*)?";

    public final String countryName;

    /**
     * Constructs a {@code Country}.
     *
     * @param country A valid country.
     */
    public Country(String country) {
        checkArgument(isValidCountry(country), MESSAGE_CONSTRAINTS);
        countryName = country;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidCountry(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return countryName;
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
        return countryName.equals(otherCountry.countryName);
    }

    @Override
    public int hashCode() {
        return countryName.hashCode();
    }

}
