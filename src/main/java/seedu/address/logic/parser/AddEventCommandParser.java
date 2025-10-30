package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Event;
import seedu.address.model.person.Name;

/**
 * Parses input arguments and creates a new {@code AddEventCommand} object.
 *
 * <p>Expected format: {@code addevent name: <name> event: <event text>}</p>
 */
public class AddEventCommandParser implements Parser<AddEventCommand> {

    /**
     * Regex to capture:
     *  - name: everything before the next " event:" (non-greedy)
     *  - event: rest of the line
     *
     * Example matches:
     *   "name: John Doe event: Met at conference"
     *   "name: Alice B.  event: Team lunch next week"
     */
    private static final Pattern ADDEVENT_COMMAND_FORMAT =
        Pattern.compile("(?i)name:\\s*(?<name>.*?)\\s+event:\\s*(?<event>.*)");

    @Override
    public AddEventCommand parse(String args) throws ParseException {
        requireNonNull(args);
        final Matcher matcher = ADDEVENT_COMMAND_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));
        }

        String nameStr = matcher.group("name").trim();
        String eventStr = matcher.group("event").trim();

        Name name = ParserUtil.parseName(nameStr);
        Event event = new Event(eventStr);

        return new AddEventCommand(name, event);
    }
}
