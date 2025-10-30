package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindOrganisationCommand;
import seedu.address.model.person.OrganisationContainsKeywordPredicate;

/**
 * Contains unit tests for {@link FindOrganisationCommandParser}.
 * Ensures valid organisation names are correctly parsed into {@link FindOrganisationCommand}.
 * Matching is case-sensitive.
 */
public class FindOrganisationCommandParserTest {

    private FindOrganisationCommandParser parser = new FindOrganisationCommandParser();

    /**
     * Ensures that an empty argument string results in a {@code ParseException}.
     */
    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindOrganisationCommand.MESSAGE_USAGE));
    }

    /**
     * Ensures that valid organisation arguments are parsed successfully.
     * Matching is case-sensitive.
     */
    @Test
    public void parse_validArgs_returnsFindOrganisationCommand() {
        // Exact case
        String orgInput = "NUS";
        FindOrganisationCommand expectedCommand =
            new FindOrganisationCommand(new OrganisationContainsKeywordPredicate(orgInput));

        assertParseSuccess(parser, "NUS", expectedCommand);

        // Leading and trailing whitespaces
        assertParseSuccess(parser, "   NUS   ", expectedCommand);

        // Different case should still parse into command, but matching in execution is case-sensitive
        String orgInputLower = "nus";
        FindOrganisationCommand expectedCommandLower =
            new FindOrganisationCommand(new OrganisationContainsKeywordPredicate(orgInputLower));

        assertParseSuccess(parser, "nus", expectedCommandLower);

        String orgInputMixed = "nUs";
        FindOrganisationCommand expectedCommandMixed =
            new FindOrganisationCommand(new OrganisationContainsKeywordPredicate(orgInputMixed));

        assertParseSuccess(parser, " nUs ", expectedCommandMixed);
    }

    /**
     * Ensures that whitespace-only input throws a {@code ParseException}.
     */
    @Test
    public void parse_whitespaceOnlyArg_throwsParseException() {
        assertParseFailure(parser, "    ",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindOrganisationCommand.MESSAGE_USAGE));
    }
}
