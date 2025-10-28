package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddOrganisationCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;
import seedu.address.model.person.Organisation;

/**
 * Parses input arguments and creates a new {@code AddOrganisationCommand} object.
 */
public class AddOrganisationCommandParser implements Parser<AddOrganisationCommand> {

    /**
     * Regex to capture:
     *  - name: everything before the next " note:" (non-greedy)
     *  - organisation: rest of the line
     *
     * Example matches:
     *   "name: John Doe organisation: Google"
     */
    private static final Pattern ADDORG_COMMAND_FORMAT =
        Pattern.compile("(?i)name:\\s*(?<name>.*?)\\s+organisation:\\s*(?<organisation>.*)");

    @Override
    public AddOrganisationCommand parse(String args) throws ParseException {
        requireNonNull(args);
        final Matcher matcher = ADDORG_COMMAND_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddOrganisationCommand.MESSAGE_USAGE));
        }

        String nameStr = matcher.group("name").trim();
        String orgStr = matcher.group("organisation").trim();

        Name name = ParserUtil.parseName(nameStr);
        Organisation organisation = new Organisation(orgStr);

        return new AddOrganisationCommand(name, organisation);
    }
}
