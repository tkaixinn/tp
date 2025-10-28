package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddEventCommand;
import seedu.address.model.person.Name;
import seedu.address.model.person.Event;

public class AddEventCommandParserTest {

    private static final String VALID_NAME = "Alice Pauline";
    private static final String VALID_EVENT = "Team lunch next week";
    private static final String EMPTY_EVENT = "";

    private final AddEventCommandParser parser = new AddEventCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        String userInput = " " + PREFIX_NAME + VALID_NAME + " " + PREFIX_EVENT + VALID_EVENT;

        AddEventCommand expectedCommand =
            new AddEventCommand(new Name(VALID_NAME), new Event(VALID_EVENT));

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_emptyEvent_success() {
        String userInput = "name: Alice Pauline event: ";
        AddEventCommand expectedCommand =
            new AddEventCommand(new Name("Alice Pauline"), new Event(""));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE);

        // no parameters
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD, expectedMessage);

        // no name
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + " " + PREFIX_EVENT + VALID_EVENT, expectedMessage);

        // no event
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + " " + PREFIX_NAME + VALID_NAME, expectedMessage);
    }
}
