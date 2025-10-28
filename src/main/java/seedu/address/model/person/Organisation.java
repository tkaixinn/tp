// src/main/java/seedu/address/model/person/Organisation.java
package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's organisation (e.g., company, school, club).
 * Guarantees: immutable; is always valid.
 */
public class Organisation {

    public final String value;

    /**
     * Constructs an {@code Organisation}.
     * An empty string is allowed to represent no organisation.
     */
    public Organisation(String organisation) {
        requireNonNull(organisation);
        value = organisation;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
            || (other instanceof Organisation
            && value.equals(((Organisation) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
