package seedu.address.model.person;

import java.util.logging.Logger;
import java.util.logging.Level;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.ZoneOffset;

/**
 * Represents a GMT offset for a person.
 * Guarantees: valid format +HH:MM or -HH:MM, immutable.
 */
public class Offset implements Comparable<Offset> {


    private static final Logger logger = Logger.getLogger(Offset.class.getName());

    public static final String MESSAGE_CONSTRAINTS =
            "GMT offset must be in the format +HH:MM or -HH:MM, where HH is 00-14 and MM is 00-59.";
    public static final String VALIDATION_REGEX = "^[+-](\\d{2}):(\\d{2})$";

    public final String value;
    private final int totalMinutes; // offset in minutes


    /**
     * Constructs an {@code Offset} from a string.
     *
     * @param input GMT offset in the format +HH:MM or -HH:MM
     */
    public Offset(String input) {
        logger.info("Creating Offset with input: " + input);
        checkArgument(isValidOffset(input), MESSAGE_CONSTRAINTS);
        this.value = input;

        String[] parts = input.substring(1).split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);

        this.totalMinutes = (input.charAt(0) == '+' ? 1 : -1) * (hours * 60 + minutes);
        logger.info("Offset created: " + this + ", totalMinutes=" + totalMinutes);
    }

    /**
     * Returns true if the given string is a valid GMT/UTC offset in the format +HH:MM or -HH:MM,
     * within the allowed timezone range.
     *
     * @param input The string to validate.
     * @return True if valid, false otherwise.
     */
    public static boolean isValidOffset(String input) {
        if (input == null) {
            logger.warning("Offset is null");
            return false;
        }

        // Must match +HH:MM or -HH:MM
        if (!input.matches(VALIDATION_REGEX)) {
            logger.warning("Offset format invalid: " + input);
            return false;
        }

        int hours = Integer.parseInt(input.substring(1, 3));
        int minutes = Integer.parseInt(input.substring(4, 6));
        char sign = input.charAt(0);

        // Validate minutes
        if (minutes < 0 || minutes >= 60) {
            logger.warning("Minutes out of range: " + minutes);
            return false;
        }

        // Maximum allowed hours based on sign
        int maxHour = (sign == '+') ? 14 : 12;

        if (hours < 0 || hours > maxHour) {
            logger.warning("Hours out of range for sign " + sign + ": " + hours);
            return false;
        }

        if (hours == maxHour && minutes > 0) {
            logger.warning("Minutes exceed max at boundary: " + input);
            return false;
        }

        return true;
    }


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
