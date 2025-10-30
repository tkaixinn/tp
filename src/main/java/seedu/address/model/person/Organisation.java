package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's organisation (e.g., company, school, club).
 * Guarantees: immutable.
 */
public class Organisation {

    public final String value;
    public static final String MESSAGE_CONSTRAINTS = "Organisations must not exceed 60 characters.";

    public static final int MAX_LENGTH = 60;

    /**
     * Constructs an {@code Organisation}.
     * An empty string is allowed to represent no organisation.
     */
    public Organisation(String organisation) {
        requireNonNull(organisation);
        checkArgument(isValidOrganisation(organisation), MESSAGE_CONSTRAINTS);
        value = organisation;
    }

    /**
     * Returns true if a given string is a valid organisation.
     */
    public static boolean isValidOrganisation(String test) {
        return test.length() <= MAX_LENGTH;
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
