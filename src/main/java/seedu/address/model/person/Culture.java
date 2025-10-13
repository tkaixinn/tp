package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's cuture notes in the address book.
 * Guarantees: immutable; is always valid
 */
public class Culture {
    public final String value;

    /**
     * @param culture note of the person
     */

    public Culture(String culture) {
        requireNonNull(culture);
        value = culture;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Culture // instanceof handles nulls
            && value.equals(((Culture) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
