package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Event;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code AddEventCommand}.
 */
public class AddEventCommandTest {

    private static final String EVENT_STUB = "Met at NUS Career Fair 2024";

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_addEventExistingPerson_success() throws Exception {
        Name targetName = new Name("Alice Pauline");
        Event event = new Event(EVENT_STUB);

        Person personToEdit = model.getFilteredPersonList().stream()
            .filter(p -> p.getName().equals(targetName))
            .findFirst()
            .orElseThrow(() -> new AssertionError("Person not found in typical persons"));

        Person editedPerson = new PersonBuilder(personToEdit).withEvent(EVENT_STUB).build();

        AddEventCommand command = new AddEventCommand(targetName, event);

        String expectedMessage = String.format(AddEventCommand.MESSAGE_ADD_EVENT_SUCCESS, editedPerson.getName());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(personToEdit, editedPerson);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteEventExistingPerson_success() throws Exception {
        Name targetName = new Name("Alice Pauline");
        Event emptyEvent = new Event("");

        Person personToEdit = model.getFilteredPersonList().stream()
            .filter(p -> p.getName().equals(targetName))
            .findFirst()
            .orElseThrow(() -> new AssertionError("Person not found"));

        Person editedPerson = new PersonBuilder(personToEdit).withEvent("").build();

        AddEventCommand command = new AddEventCommand(targetName, emptyEvent);

        String expectedMessage = String.format(AddEventCommand.MESSAGE_DELETE_EVENT_SUCCESS, editedPerson.getName());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(personToEdit, editedPerson);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_personNotFound_failure() {
        Name invalidName = new Name("Nonexistent Person");
        Event event = new Event("Some event");
        AddEventCommand command = new AddEventCommand(invalidName, event);

        // execute_personNotFound_failure
        String expectedMessage = String.format(AddEventCommand.MESSAGE_PERSON_NOT_FOUND, invalidName.fullName);
        assertCommandFailure(command, model, expectedMessage);
    }

    @Test
    public void equals() {
        final AddEventCommand standardCommand =
            new AddEventCommand(new Name("Amy Bee"), new Event("Attended workshop"));

        // same values -> returns true
        AddEventCommand commandWithSameValues =
            new AddEventCommand(new Name("Amy Bee"), new Event("Attended workshop"));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different name -> returns false
        assertFalse(standardCommand.equals(
            new AddEventCommand(new Name("Bob Choo"), new Event("Attended workshop"))));

        // different event -> returns false
        assertFalse(standardCommand.equals(
            new AddEventCommand(new Name("Amy Bee"), new Event("Gave a talk"))));
    }
}
