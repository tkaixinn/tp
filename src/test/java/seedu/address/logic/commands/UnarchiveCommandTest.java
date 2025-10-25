package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) for UnarchiveCommand.
 */
public class UnarchiveCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndex_personSuccessfullyUnarchived() {
        Person personToArchive = model.getFilteredPersonList().stream()
                .filter(person -> !person.getArchivalStatus())
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No unarchived persons in test data"));

        Person archivedPerson = new PersonBuilder(personToArchive).archived().build();
        model.setPerson(personToArchive, archivedPerson);

        Person personToUnarchive = model.getFilteredPersonList().stream()
                .filter(Person::getArchivalStatus)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No archived persons found after archiving"));

        int index = model.getFilteredPersonList().indexOf(personToUnarchive);

        assertTrue(personToUnarchive.getArchivalStatus());

        UnarchiveCommand unarchiveCommand = new UnarchiveCommand(Index.fromZeroBased(index));

        try {
            CommandResult result = unarchiveCommand.execute(model);

            String expectedMessage = String.format(UnarchiveCommand.MESSAGE_UNARCHIVE_SUCCESS,
                    personToUnarchive.getName());
            assertEquals(expectedMessage, result.getFeedbackToUser());

            Person actualUnarchivedPerson = model.getAddressBook().getPersonList().stream()
                    .filter(p -> p.isSamePerson(personToUnarchive))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("Person not found in address book after unarchiving"));

            assertFalse(actualUnarchivedPerson.getArchivalStatus());

        } catch (CommandException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void execute_validIndex_personAlreadyUnarchived() {
        Person personToUnarchive = model.getFilteredPersonList().stream()
                .filter(person -> person.getArchivalStatus())
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No archived persons in test data"));

        int index = model.getFilteredPersonList().indexOf(personToUnarchive);
        Person unarchivedPerson = new PersonBuilder(personToUnarchive).build(); // Remove archived status

        model.setPerson(personToUnarchive, unarchivedPerson);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        int newIndex = model.getFilteredPersonList().indexOf(unarchivedPerson);

        UnarchiveCommand unarchiveCommand = new UnarchiveCommand(Index.fromZeroBased(index));

        assertFalse(unarchivedPerson.getArchivalStatus());

        assertCommandFailure(unarchiveCommand, model,
                String.format(UnarchiveCommand.MESSAGE_ALREADY_UNARCHIVED, unarchivedPerson.getName()));
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        UnarchiveCommand unarchiveCommand = new UnarchiveCommand(outOfBoundIndex);

        assertCommandFailure(unarchiveCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        model.updateFilteredPersonList(p -> p.equals(firstPerson));

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        UnarchiveCommand unarchiveCommand = new UnarchiveCommand(outOfBoundIndex);

        assertCommandFailure(unarchiveCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final UnarchiveCommand standardCommand = new UnarchiveCommand(INDEX_FIRST_PERSON);

        // same index -> returns true
        UnarchiveCommand commandWithSameValues = new UnarchiveCommand(INDEX_FIRST_PERSON);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new UnarchiveCommand(INDEX_SECOND_PERSON)));
    }

    @Test
    public void equals_null_returnsFalse() {
        UnarchiveCommand unarchiveCommand = new UnarchiveCommand(INDEX_FIRST_PERSON);
        assertFalse(unarchiveCommand.equals(null));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        UnarchiveCommand unarchiveCommand = new UnarchiveCommand(index);
        String expected = UnarchiveCommand.class.getCanonicalName() + "{index=" + index + "}";
        assertEquals(expected, unarchiveCommand.toString());
    }
}
