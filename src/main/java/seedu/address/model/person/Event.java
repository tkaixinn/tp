package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's event note in the address book.
 * Guarantees: immutable.
 */
public class Event {

    public static final String MESSAGE_CONSTRAINTS = "Events must not exceed 100 characters.";

    public static final int MAX_LENGTH = 100;

    public final String value;

    /**
     * Constructs an {@code Event}.
     *
     * @param event Event description. Cannot be null.
     */
    public Event(String event) {
        requireNonNull(event);
        checkArgument(isValidEvent(event), MESSAGE_CONSTRAINTS);
        value = event;
    }

    /**
     * Returns true if a given string is a valid event.
     */
    public static boolean isValidEvent(String test) {
        return test.length() <= MAX_LENGTH;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
            || (other instanceof Event
            && value.equals(((Event) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
