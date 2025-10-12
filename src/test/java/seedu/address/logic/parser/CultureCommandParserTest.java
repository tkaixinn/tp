package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CULTURE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CultureCommand;
import seedu.address.model.person.Culture;

public class CultureCommandParserTest {
    private CultureCommandParser parser = new CultureCommandParser();
    private final String nonEmptyCulture = "Some culture notes.";

    @Test
    public void parse_indexSpecified_success() {
        // have remark
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_CULTURE + nonEmptyCulture;
        CultureCommand expectedCommand = new CultureCommand(INDEX_FIRST_PERSON, new Culture(nonEmptyCulture));
        assertParseSuccess(parser, userInput, expectedCommand);

        // no remark
        userInput = targetIndex.getOneBased() + " " + PREFIX_CULTURE;
        expectedCommand = new CultureCommand(INDEX_FIRST_PERSON, new Culture(""));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, CultureCommand.MESSAGE_USAGE);

        // no parameters
        assertParseFailure(parser, CultureCommand.COMMAND_WORD, expectedMessage);

        // no index
        assertParseFailure(parser, CultureCommand.COMMAND_WORD + " " + nonEmptyCulture, expectedMessage);
    }
}
