package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Contains unit tests for {@link Country}.
 * Verifies the correctness of its constructor, validation logic, and equality checks.
 */
public class CountryTest {

    /**
     * Verifies that constructing a {@code Country} with {@code null} throws a {@code NullPointerException}.
     */
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Country(null));
    }

    /**
     * Verifies that constructing a {@code Country} with an invalid name
     * throws an {@code IllegalArgumentException}.
     */
    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidCountry = "###";
        assertThrows(IllegalArgumentException.class, () -> new Country(invalidCountry));
    }

    /**
     * Tests the {@link Country#isValidCountry(String)} method for various valid and invalid inputs.
     */
    @Test
    public void isValidCountry() {
        // null country
        assertThrows(NullPointerException.class, () -> Country.isValidCountry(null));

        // invalid country
        assertFalse(Country.isValidCountry("S*ngapore")); // contains invalid non-alphabet characters
        assertFalse(Country.isValidCountry("Not A Country")); // not on country list
        assertFalse(Country.isValidCountry("S1ngap0re")); // numbers

        // valid country
        assertTrue(Country.isValidCountry("Hong Kong SAR China")); // only alphabet letters in title case
        assertTrue(Country.isValidCountry("St. Kitts & Nevis")); // valid non-alphabet characters
        assertTrue(Country.isValidCountry("")); // empty, no country given

        // valid country names - NEW
        assertTrue(Country.isValidCountry("Singapore")); // proper case
        assertTrue(Country.isValidCountry("singapore")); // all lowercase
        assertTrue(Country.isValidCountry("SINGAPORE")); // all uppercase
        assertTrue(Country.isValidCountry("sInGaPoRe")); // mixed case

        // NEW: Bosnia & Herzegovina should be valid under all case variations
        assertTrue(Country.isValidCountry("Bosnia & Herzegovina"));
        assertTrue(Country.isValidCountry("bosnia & herzegovina"));
        assertTrue(Country.isValidCountry("BOSNIA & HERZEGOVINA"));
        assertTrue(Country.isValidCountry("bOsNiA & hErZeGoViNa"));

        // NEW: Another example - Russia in multiple case variations
        assertTrue(Country.isValidCountry("Russia"));
        assertTrue(Country.isValidCountry("russia"));
        assertTrue(Country.isValidCountry("RUSSIA"));
        assertTrue(Country.isValidCountry("RUssia"));
    }

    /**
     * Verifies the equality logic of {@code Country}.
     */
    @Test
    public void equals() {
        Country country = new Country("Singapore");

        // same values -> returns true
        assertTrue(country.equals(new Country("Singapore")));

        // same object -> returns true
        assertTrue(country.equals(country));

        // null -> returns false
        assertFalse(country.equals(null));

        // different types -> returns false
        assertFalse(country.equals(5.0f));

        // different values -> returns false
        assertFalse(country.equals(new Country("United States")));
    }
}
