package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.FindOrganisationCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.OrganisationContainsKeywordPredicate;

/**
 * Parses input arguments and creates a new {@link FindOrganisationCommand} object.
 *
 * Example usage: findorganisation NUS
 *
 * Finds and lists all persons who belong to the specified organisation.
 */
public class FindOrganisationCommandParser implements Parser<FindOrganisationCommand> {

    /**
     * Parses the given {@code String} of arguments and returns a {@code FindOrganisationCommand} object
     * for execution.
     *
     * @param args The full command input string (excluding the command word).
     * @return A new {@code FindOrganisationCommand} using the parsed keyword.
     * @throws ParseException If the user input does not conform to the expected format.
     */
    @Override
    public FindOrganisationCommand parse(String args) throws ParseException {
        String organisationInput = args.trim();
        if (organisationInput.isEmpty()) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindOrganisationCommand.MESSAGE_USAGE));
        }

        // Pass the string directly to the predicate
        return new FindOrganisationCommand(new OrganisationContainsKeywordPredicate(organisationInput));
    }
}
