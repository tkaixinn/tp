package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCountryCommand;
import seedu.address.model.person.Country;
import seedu.address.model.person.CountryContainsKeywordPredicate;

/**
 * Contains unit tests for {@link FindCountryCommandParser}.
 * Ensures valid country names are correctly parsed into {@link FindCountryCommand}.
 */
public class FindCountryCommandParserTest {

    private FindCountryCommandParser parser = new FindCountryCommandParser();

    /**
     * Ensures that an empty argument string results in a {@code ParseException}.
     */
    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCountryCommand.MESSAGE_USAGE));
    }

    /**
     * Ensures that an invalid country input (not found in the country list)
     * results in a {@code ParseException}.
     */
    @Test
    public void parse_invalidCountry_throwsParseException() {
        assertParseFailure(parser, "hello",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCountryCommand.MESSAGE_USAGE));
    }

    /**
     * Ensures that valid country arguments (case-insensitive) are parsed successfully.
     */
    @Test
    public void parse_validArgs_returnsFindCountryCommand() {
        // no leading and trailing whitespaces
        Country singapore = new Country("Singapore");
        FindCountryCommand expectedFindCountryCommand =
            new FindCountryCommand(new CountryContainsKeywordPredicate(singapore));

        assertParseSuccess(parser, "Singapore", expectedFindCountryCommand);
        assertParseSuccess(parser, "SINGAPORE", expectedFindCountryCommand);
        assertParseSuccess(parser, "singapore", expectedFindCountryCommand);
        assertParseSuccess(parser, "sInGaPoRe", expectedFindCountryCommand);

        // Additional valid test for multi-word country
        Country bosnia = new Country("Bosnia & Herzegovina");
        FindCountryCommand expectedBosniaCommand =
            new FindCountryCommand(new CountryContainsKeywordPredicate(bosnia));

        assertParseSuccess(parser, "Bosnia & Herzegovina", expectedBosniaCommand);
        assertParseSuccess(parser, "bosnia & herzegovina", expectedBosniaCommand);
        assertParseSuccess(parser, "BOSNIA & HERZEGOVINA", expectedBosniaCommand);
        assertParseSuccess(parser, "bOsNiA & hErZeGoViNa", expectedBosniaCommand);
    }
}
