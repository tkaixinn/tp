package seedu.address.logic;

import java.util.HashSet;
import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.CliSyntax;

/**
 * Validates user command input for invalid prefixes.
 */
public class CommandValidator {

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
        Set<String> invalidPrefixes = new HashSet<>();

        String[] tokens = userInput.trim().split("\\s+");

        for (String token : tokens) {
            token = token.trim();
            if (token.contains(":")) {
                boolean matches = VALID_PREFIXES.stream().anyMatch(token::startsWith);
                if (!matches) {

                    String prefixCandidate = token.substring(0, token.indexOf(":") + 1);
                    invalidPrefixes.add(prefixCandidate);
                }
            }
        }

        if (!invalidPrefixes.isEmpty()) {
            return String.format(
                    Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    "Invalid prefix(es): " + String.join(", ", invalidPrefixes)
                            + "\n" + AddCommand.MESSAGE_USAGE
            );
        }

        return null;
    }
}


