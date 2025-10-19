package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class CountryTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Country(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidCountry = "###";
        assertThrows(IllegalArgumentException.class, () -> new Country(invalidCountry));
    }

    @Test
    public void isValidCountry() {
        // null country
        assertThrows(NullPointerException.class, () -> Country.isValidCountry(null));

        // invalid country
        assertFalse(Country.isValidCountry("S*ngapore")); // contains invalid non-alphabet characters
        assertFalse(Country.isValidCountry("singapore")); // does not match country list case
        assertFalse(Country.isValidCountry("Not A Country")); // not on country list
        assertFalse(Country.isValidCountry("S1ngap0re")); // numbers

        // valid country
        assertTrue(Country.isValidCountry("Hong Kong SAR China")); // only alphabet letters in title case
        assertTrue(Country.isValidCountry("St. Kitts & Nevis")); // valid non-alphabet characters
        assertTrue(Country.isValidCountry("")); // empty, no country given
    }

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
