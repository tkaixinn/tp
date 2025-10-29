package seedu.address.model.util;

/**
 * Utility class for common string operations.
 */
public class StringUtils {
    /**
     * Converts the given string to title case.
     *
     * @param input The string to convert. Must not be null.
     * @return A new string in title case. If the input is empty, returns an empty
     *         string.
     */
    public static String toTitleCase(String input) {
        StringBuilder result = new StringBuilder(input.length());
        boolean capitaliseNext = true;

        for (char c : input.toCharArray()) {
            if (Character.isSpaceChar(c)) {
                capitaliseNext = true;
            } else {
                if (capitaliseNext) {
                    c = Character.toTitleCase(c);
                }
                capitaliseNext = false;
            }
            result.append(c);
        }

        return result.toString();
    }
}
