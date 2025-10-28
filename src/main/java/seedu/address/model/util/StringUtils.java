package seedu.address.model.util;

public class StringUtils {
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
