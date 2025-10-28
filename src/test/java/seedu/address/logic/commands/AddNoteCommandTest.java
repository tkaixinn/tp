package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Name;
import seedu.address.model.person.Note;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code AddNoteCommand}.
 */
public class AddNoteCommandTest {

    private static final String NOTE_STUB = "Prefers Telegram, loves Nasi Lemak";
    private static final String NOTE_SPECIAL = "!, @, #, $, %, &, *, (, ), -, _, =, +, ,, ., /, é, ü, ñ, å";
    private static final String LONG_NOTE = "A".repeat(2000);
    private static final String WS_NOTE = " ";

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_addNoteExistingPerson_success() throws Exception {
        Name targetName = new Name("Alice Pauline");
        Note note = new Note(NOTE_STUB);

        Person personToEdit = model.getFilteredPersonList().stream()
            .filter(p -> p.getName().equals(targetName))
            .findFirst()
            .orElseThrow(() -> new AssertionError("Person not found in typical persons"));

        Person editedPerson = new PersonBuilder(personToEdit).withNote(NOTE_STUB).build();

        AddNoteCommand command = new AddNoteCommand(targetName, note);

        String expectedMessage = String.format(AddNoteCommand.MESSAGE_ADD_NOTE_SUCCESS, editedPerson.getName());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(personToEdit, editedPerson);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addNoteSpecial_success() throws Exception {
        Name targetName = new Name("Alice Pauline");
        Note note = new Note(NOTE_SPECIAL);

        Person personToEdit = model.getFilteredPersonList().stream()
                .filter(p -> p.getName().equals(targetName))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Person not found in typical persons"));

        Person editedPerson = new PersonBuilder(personToEdit).withNote(NOTE_SPECIAL).build();

        AddNoteCommand command = new AddNoteCommand(targetName, note);

        String expectedMessage = String.format(AddNoteCommand.MESSAGE_ADD_NOTE_SUCCESS, editedPerson.getName());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(personToEdit, editedPerson);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void constructor_nullName_throwsNpe() {
        assertThrows(NullPointerException.class, () -> new AddNoteCommand(null, new Note("x")));
    }

    @Test
    public void constructor_nullNote_throwsNpe() {
        assertThrows(NullPointerException.class, () -> new AddNoteCommand(new Name("Alice Pauline"), null));
    }

    @Test
    public void execute_overwriteExistingNote_success() throws Exception {
        Person original = model.getFilteredPersonList().stream()
                .filter(p -> p.getName().equals(ALICE.getName()))
                .findFirst().orElseThrow();

        Person withExisting = new PersonBuilder(original).withNote("old note").build();
        model.setPerson(original, withExisting);

        Note newNote = new Note(NOTE_STUB);
        Person edited = new PersonBuilder(withExisting).withNote(NOTE_STUB).build();

        AddNoteCommand cmd = new AddNoteCommand(withExisting.getName(), newNote);
        Model expected = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expected.setPerson(withExisting, edited);

        String expectedMsg = String.format(AddNoteCommand.MESSAGE_ADD_NOTE_SUCCESS, edited.getName());
        assertCommandSuccess(cmd, model, expectedMsg, expected);

        Person after = model.getFilteredPersonList().stream()
                .filter(p -> p.getName().equals(edited.getName()))
                .findFirst().orElseThrow();
        assertEquals(NOTE_STUB, after.getNote().value);
    }

    @Test
    public void execute_deleteWhenAlreadyEmpty_success() throws Exception {
        Person target = model.getFilteredPersonList().stream()
                .filter(p -> p.getName().equals(ALICE.getName()))
                .findFirst().orElseThrow();

        Person emptied = new PersonBuilder(target).withNote("").build();
        model.setPerson(target, emptied);

        AddNoteCommand cmd = new AddNoteCommand(emptied.getName(), new Note(""));
        Model expected = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expected.setPerson(emptied, emptied); // no change in fields

        String expectedMsg = String.format(AddNoteCommand.MESSAGE_DELETE_NOTE_SUCCESS, emptied.getName());
        assertCommandSuccess(cmd, model, expectedMsg, expected);

        Person after = model.getFilteredPersonList().stream()
                .filter(p -> p.getName().equals(emptied.getName()))
                .findFirst().orElseThrow();
        assertEquals("", after.getNote().value);
    }

    @Test
    public void execute_updatesOnlyTargetPerson_listSizeUnchanged() throws Exception {
        int beforeSize = model.getFilteredPersonList().size();

        Person target = model.getFilteredPersonList().stream()
                .filter(p -> p.getName().equals(ALICE.getName()))
                .findFirst().orElseThrow();

        Person someoneElseBefore = model.getFilteredPersonList().stream()
                .filter(p -> p.getName().equals(BENSON.getName()))
                .findFirst().orElseThrow();

        AddNoteCommand cmd = new AddNoteCommand(target.getName(), new Note(NOTE_STUB));
        assertDoesNotThrow(() -> cmd.execute(model));

        int afterSize = model.getFilteredPersonList().size();
        assertEquals(beforeSize, afterSize);

        // Target updated
        Person targetAfter = model.getFilteredPersonList().stream()
                .filter(p -> p.getName().equals(target.getName()))
                .findFirst().orElseThrow();
        assertEquals(NOTE_STUB, targetAfter.getNote().value);

        // Someone else unchanged (object may be replaced; compare content)
        Person someoneElseAfter = model.getFilteredPersonList().stream()
                .filter(p -> p.getName().equals(BENSON.getName()))
                .findFirst().orElseThrow();

        // Keep it content-based: name & note unchanged
        assertEquals(someoneElseBefore.getName(), someoneElseAfter.getName());
        assertEquals(someoneElseBefore.getNote(), someoneElseAfter.getNote());
    }

    @Test
    public void execute_longNote_success() throws Exception {
        Person target = model.getFilteredPersonList().get(0);
        AddNoteCommand cmd = new AddNoteCommand(target.getName(), new Note(LONG_NOTE));

        Person edited = new PersonBuilder(target).withNote(LONG_NOTE).build();
        Model expected = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expected.setPerson(target, edited);

        String expectedMsg = String.format(AddNoteCommand.MESSAGE_ADD_NOTE_SUCCESS, edited.getName());
        assertCommandSuccess(cmd, model, expectedMsg, expected);
    }

    @Test
    public void execute_whitespace_success() throws Exception {
        Person target = model.getFilteredPersonList().get(0);
        AddNoteCommand cmd = new AddNoteCommand(target.getName(), new Note(WS_NOTE));

        Person edited = new PersonBuilder(target).withNote(WS_NOTE).build();
        Model expected = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expected.setPerson(target, edited);

        String expectedMsg = String.format(AddNoteCommand.MESSAGE_ADD_NOTE_SUCCESS, edited.getName());
        assertCommandSuccess(cmd, model, expectedMsg, expected);

        Person after = model.getFilteredPersonList().stream()
                .filter(p -> p.getName().equals(edited.getName()))
                .findFirst().orElseThrow();
        assertEquals(WS_NOTE, after.getNote().value);
    }

    @Test
    public void execute_personNotInFilteredView_stillFindsByName() throws Exception {
        // Narrow the list to a different person
        model.updateFilteredPersonList(p -> p.getName().equals(BENSON.getName()));
        List<Person> filtered = model.getFilteredPersonList();
        assertEquals(1, filtered.size());
        assertTrue(filtered.get(0).getName().equals(BENSON.getName()));

        // But update Alice by name; command should still locate her in the model
        Name alice = ALICE.getName();
        AddNoteCommand cmd = new AddNoteCommand(alice, new Note(NOTE_STUB));

        Person aliceBefore = model.getAddressBook().getPersonList().stream()
                .filter(p -> p.getName().equals(alice)).findFirst().orElseThrow();
        Person aliceEdited = new PersonBuilder(aliceBefore).withNote(NOTE_STUB).build();

        Model expected = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expected.setPerson(aliceBefore, aliceEdited);

        String expectedMsg = String.format(AddNoteCommand.MESSAGE_ADD_NOTE_SUCCESS, aliceEdited.getName());
        assertCommandSuccess(cmd, model, expectedMsg, expected);
    }

    @Test
    public void execute_personNotFound_failureMessage() {
        Name invalid = new Name("Nobody Here");
        AddNoteCommand cmd = new AddNoteCommand(invalid, new Note("x"));
        String expected = String.format(AddNoteCommand.MESSAGE_PERSON_NOT_FOUND, invalid.fullName);
        assertCommandFailure(cmd, model, expected);
    }

    @Test
    public void execute_deleteNoteExistingPerson_success() throws Exception {
        Name targetName = new Name("Alice Pauline");
        Note emptyNote = new Note("");

        Person personToEdit = model.getFilteredPersonList().stream()
            .filter(p -> p.getName().equals(targetName))
            .findFirst()
            .orElseThrow(() -> new AssertionError("Person not found"));

        Person editedPerson = new PersonBuilder(personToEdit).withNote("").build();

        AddNoteCommand command = new AddNoteCommand(targetName, emptyNote);

        String expectedMessage = String.format(AddNoteCommand.MESSAGE_DELETE_NOTE_SUCCESS, editedPerson.getName());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(personToEdit, editedPerson);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_personNotFound_failure() {
        Name invalidName = new Name("Nonexistent Person");
        Note note = new Note("Some note");
        AddNoteCommand command = new AddNoteCommand(invalidName, note);

        // execute_personNotFound_failure
        String expectedMessage = String.format(AddNoteCommand.MESSAGE_PERSON_NOT_FOUND, invalidName.fullName);
        assertCommandFailure(command, model, expectedMessage);
    }

    @Test
    public void equals() {
        final AddNoteCommand standardCommand =
            new AddNoteCommand(new Name("Amy Bee"), new Note("Enjoys tea"));

        // same values -> returns true
        AddNoteCommand commandWithSameValues =
            new AddNoteCommand(new Name("Amy Bee"), new Note("Enjoys tea"));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different name -> returns false
        assertFalse(standardCommand.equals(
            new AddNoteCommand(new Name("Bob Choo"), new Note("Enjoys tea"))));

        // different note -> returns false
        assertFalse(standardCommand.equals(
            new AddNoteCommand(new Name("Amy Bee"), new Note("Loves hiking"))));
    }
}
