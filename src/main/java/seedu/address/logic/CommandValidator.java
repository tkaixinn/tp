package seedu.address.logic;

import java.util.HashSet;
import java.util.Set;

import seedu.address.logic.parser.CliSyntax;

/**
 * Validates user command input for invalid prefixes.
 */
public class CommandValidator {

    // Set of all valid prefixes from CliSyntax
    private static final Set<String> VALID_PREFIXES = Set.of(
            CliSyntax.PREFIX_NAME.toString(),
            CliSyntax.PREFIX_PHONE.toString(),
            CliSyntax.PREFIX_EMAIL.toString(),
            CliSyntax.PREFIX_ADDRESS.toString(),
            CliSyntax.PREFIX_ORGANISATION.toString(),
            CliSyntax.PREFIX_EVENT.toString(),
            CliSyntax.PREFIX_NOTE.toString(),
            CliSyntax.PREFIX_COUNTRY.toString(),
            CliSyntax.PREFIX_TAG.toString(),
            CliSyntax.PREFIX_CHANNEL.toString(),
            CliSyntax.PREFIX_OFFSET.toString(),
            CliSyntax.PREFIX_LANGUAGE.toString()
    );

    /**
     * Validates the raw command input for invalid prefixes.
     * Returns null if everything is fine, or an invalid format message if something is wrong.
     */
    public static String validateCommand(String userInput) {
        String[] tokens = userInput.split("\\s+");

        Set<String> invalidPrefixes = new HashSet<>();
        for (String token : tokens) {
            if (token.contains(":")) {
                String prefixCandidate = token.substring(0, token.indexOf(":") + 1);
                if (!VALID_PREFIXES.contains(prefixCandidate)) {
                    invalidPrefixes.add(prefixCandidate);
                }
            }
        }

        if (!invalidPrefixes.isEmpty()) {
            return String.format(
                    Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    "Invalid prefix(es): " + String.join(", ", invalidPrefixes)
            );
        }

        return null;
    }
}

