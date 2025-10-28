package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORGANISATION;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddOrganisationCommand;
import seedu.address.model.person.Name;
import seedu.address.model.person.Organisation;

public class AddOrganisationCommandParserTest {

    private static final String VALID_NAME = "Alice Pauline";
    private static final String VALID_ORG = "National University of Singapore";

    private final AddOrganisationCommandParser parser = new AddOrganisationCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        String userInput = " " + PREFIX_NAME + VALID_NAME + " " + PREFIX_ORGANISATION + VALID_ORG;

        AddOrganisationCommand expectedCommand =
            new AddOrganisationCommand(new Name(VALID_NAME), new Organisation(VALID_ORG));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_emptyOrganisation_success() {
        String userInput = "name: Alice Pauline organisation: ";
        AddOrganisationCommand expectedCommand =
            new AddOrganisationCommand(new Name("Alice Pauline"), new Organisation(""));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddOrganisationCommand.MESSAGE_USAGE);
        assertParseFailure(parser, AddOrganisationCommand.COMMAND_WORD, expectedMessage);
        assertParseFailure(parser, AddOrganisationCommand.COMMAND_WORD + " "
            + PREFIX_ORGANISATION + VALID_ORG, expectedMessage);
        assertParseFailure(parser, AddOrganisationCommand.COMMAND_WORD + " "
            + PREFIX_NAME + VALID_NAME, expectedMessage);
    }
}
