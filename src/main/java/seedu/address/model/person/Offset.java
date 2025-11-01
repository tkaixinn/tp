package seedu.address.model.person;

import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.ZoneOffset;
import java.util.logging.Logger;

/**
 * Represents a GMT offset for a person.
 * Guarantees: valid format +HH:MM or -HH:MM, immutable.
 */
public class Offset implements Comparable<Offset> {


    public static final String MESSAGE_CONSTRAINTS =
            "offset must be from -12:00 to +14:00 and must be +HH:MM or -HH:MM and "
                    + "MM is one of 00, 30, or 45 only and valid existing offsets";
    public static final String VALIDATION_REGEX = "^[+-](\\d{2}):(\\d{2})$";
    private static final Logger logger = Logger.getLogger(Offset.class.getName());
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
        if ((minutes < 0 || minutes >= 60) && minutes != 0 && minutes != 30 && minutes != 45) {
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

        if (input.equals("+14:00") || input.equals("-12:00") || minutes == 0) {
            return true;
        }

        return switch (input) {
        case "-09:30", "-02:30", "-03:30", "+03:30", "+04:30", "+05:30", "+05:45", "+06:30", "+08:45",
            "+09:30", "+10:30", "+12:45", "+13:45" -> true; //timezones with :30 and :45 includes DST adjustments
        default -> false;
        };
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
