package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's event note in the address book.
 * Guarantees: immutable; is always valid.
 */
public class Event {

    public final String value;

    /**
     * Constructs an {@code Event}.
     *
     * @param event Event description. Cannot be null.
     */
    public Event(String event) {
        requireNonNull(event);
        value = event;
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
