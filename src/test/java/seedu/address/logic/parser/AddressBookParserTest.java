package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ArchiveCommand;
import seedu.address.logic.commands.ArchiveListCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.UnarchiveCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

/**
 * Contains unit tests for {@link AddressBookParser}.
 * Ensures that command words are correctly parsed into their respective command objects.
 * These tests verify parser behaviour for valid commands, invalid formats, and unknown inputs.
 */
public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    /**
     * Tests parsing of an add command with valid input.
     */
    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
    }

    /**
     * Tests parsing of the clear command with or without arguments.
     */
    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    /**
     * Tests parsing of a delete command using an index.
     */
    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    /**
     * Tests parsing of a standard edit command with multiple fields.
     */
    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    /**
     * Tests parsing of the exit command.
     */
    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    /**
     * Tests parsing of a find command using keyword list.
     */
    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    /**
     * Tests parsing of the help command with or without arguments.
     */
    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    /**
     * Tests parsing of an empty input string.
     * Expected: ParseException.
     */
    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                HelpCommand.MESSAGE_USAGE), () -> parser.parseCommand(""));
    }

    /**
     * Tests parsing of the list command with or without arguments.
     */
    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    /**
     * Tests parsing of the archivelist command.
     */
    @Test
    public void parseCommand_archivelist() throws Exception {
        assertTrue(parser.parseCommand(ArchiveListCommand.COMMAND_WORD + " 3") instanceof ArchiveListCommand);
    }

    /**
     * Tests parsing of the archive command using an index.
     */
    @Test
    public void parseCommand_archive() throws Exception {
        ArchiveCommand command = (ArchiveCommand) parser.parseCommand(
                ArchiveCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new ArchiveCommand(INDEX_FIRST_PERSON), command);
    }

    /**
     * Tests parsing of the unarchive command using an index.
     */
    @Test
    public void parseCommand_unarchive() throws Exception {
        UnarchiveCommand command = (UnarchiveCommand) parser.parseCommand(
                UnarchiveCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new UnarchiveCommand(INDEX_FIRST_PERSON), command);
    }

    /**
     * Tests parsing of an edit command that updates a person's note.
     */
    @Test
    public void parseCommand_editNote() throws Exception {
        Person person = new PersonBuilder().withNote("Hello").build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withNote("Updated").build();
        EditCommand command = (EditCommand) parser.parseCommand(
            EditCommand.COMMAND_WORD + " 1 note:Updated"
        );
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    /**
     * Tests parsing of an edit command that updates a person's organisation field.
     */
    @Test
    public void parseCommand_editOrganisation() throws Exception {
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder()
            .withOrganisation("Google").build();
        EditCommand command = (EditCommand) parser.parseCommand(
            EditCommand.COMMAND_WORD + " 1 organisation:Google"
        );
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    /**
     * Tests parsing of an edit command that updates a person's event field.
     */
    @Test
    public void parseCommand_editEvent() throws Exception {
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder()
            .withEvent("TechFair").build();
        EditCommand command = (EditCommand) parser.parseCommand(
            EditCommand.COMMAND_WORD + " 1 event:TechFair"
        );
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }
}
