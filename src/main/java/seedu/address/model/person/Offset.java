package seedu.address.model.person;

import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.ZoneOffset;

/**
 * Represents a GMT offset for a person.
 * Guarantees: valid format +HH:MM or -HH:MM, immutable.
 */
public class Offset implements Comparable<Offset> {

    public static final String MESSAGE_CONSTRAINTS =
            "GMT offset must be in the format +HH:MM or -HH:MM, where HH is 00-14 and MM is 00-59.";
    public static final String VALIDATION_REGEX = "^[+-](?:0\\d|1[0-4]):[0-5]\\d$";

    public final String value;
    private final int totalMinutes; // offset in minutes


    /**
     * Constructs an {@code Offset} from a string.
     *
     * @param input GMT offset in the format +HH:MM or -HH:MM
     */
    public Offset(String input) {
        checkArgument(isValidOffset(input), MESSAGE_CONSTRAINTS);
        this.value = input;

        String[] parts = input.substring(1).split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);

        this.totalMinutes = (input.charAt(0) == '+' ? 1 : -1) * (hours * 60 + minutes);
    }

    /**
     * Returns true if the given string is a valid UTC offset in the format {@code +HH:MM} or {@code -HH:MM},
     * within the allowed timezone range.
     *
     * @param test The string to validate.
     * @return True if the string represents a valid offset, false otherwise.
     */
    public static boolean isValidOffset(String test) {
        if (test == null) {
            return false;
        }

        // Must match +HH:MM or -HH:MM
        if (!test.matches("^[+-](\\d{2}):(\\d{2})$")) {
            return false;
        }

        // Parse hours and minutes
        int hours = Integer.parseInt(test.substring(1, 3));
        int minutes = Integer.parseInt(test.substring(4, 6));

        // Check range
        if (hours > 14 || minutes >= 60) {
            return false;
        }

        // Enforce realistic timezone range
        if (test.startsWith("+") && hours == 14 && minutes > 0) {
            // +14:00 is max, but +14:01 not allowed
            return false;
        }

        if (test.startsWith("-") && hours > 12) {
            // -12:00 is min, disallow -13:00 etc.
            return false;
        }

        if (test.startsWith("-") && hours == 12 && minutes > 0) {
            return false;
        }
        return true;
    };

    /**
     * Returns the total offset in minutes.
     *
     * @return total offset in minutes
     */
    public int getTotalMinutes() {
        return totalMinutes;
    }

    /**
     * Returns a string representation in +HH:MM or -HH:MM format.
     */
    @Override
    public String toString() {
        if (totalMinutes == 0) {
            return "+00:00";
        }
        return value;
    }

    /**
     * Compares two Offsets by their total minutes.
     */
    @Override
    public int compareTo(Offset other) {
        return Integer.compare(this.totalMinutes, other.totalMinutes);
    }

    /**
     * Converts to a {@link ZoneOffset}.
     */
    public ZoneOffset toZoneOffset() {
        return ZoneOffset.of(totalMinutes == 0 ? "+00:00" : value);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Offset o)) {
            return false;
        }
        return value.equals(o.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
