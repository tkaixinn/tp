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
 * Contains integration tests (interaction with the Model) for ArchiveCommand.
 */
public class ArchiveCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndex_personSuccessfullyArchived() {
        Person personToArchive = model.getFilteredPersonList().stream()
                .filter(person -> !person.getArchivalStatus())
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No unarchived persons in test data"));

        int index = model.getFilteredPersonList().indexOf(personToArchive);

        assertFalse(personToArchive.getArchivalStatus());

        ArchiveCommand archiveCommand = new ArchiveCommand(Index.fromZeroBased(index));

        try {
            CommandResult result = archiveCommand.execute(model);

            String expectedMessage = String.format(ArchiveCommand.MESSAGE_ARCHIVE_SUCCESS, personToArchive.getName());
            assertEquals(expectedMessage, result.getFeedbackToUser());

            Person actualArchivedPerson = model.getAddressBook().getPersonList().stream()
                    .filter(p -> p.isSamePerson(personToArchive))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("Person not found in address book after archiving"));

            assertTrue(actualArchivedPerson.getArchivalStatus());

        } catch (CommandException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void execute_validIndex_personAlreadyArchived() {
        Person personToArchive = model.getFilteredPersonList().stream()
                .filter(person -> !person.getArchivalStatus())
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No unarchived persons in test data"));

        int index = model.getFilteredPersonList().indexOf(personToArchive);
        Person archivedPerson = new PersonBuilder(personToArchive).archived().build();
        model.setPerson(personToArchive, archivedPerson);

        ArchiveCommand archiveCommand = new ArchiveCommand(Index.fromZeroBased(index));

        assertTrue(archivedPerson.getArchivalStatus());

        assertCommandFailure(archiveCommand, model,
                String.format(ArchiveCommand.MESSAGE_ALREADY_ARCHIVED, archivedPerson.getName()));
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        ArchiveCommand archiveCommand = new ArchiveCommand(outOfBoundIndex);

        assertCommandFailure(archiveCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        model.updateFilteredPersonList(p -> p.equals(firstPerson));

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        ArchiveCommand archiveCommand = new ArchiveCommand(outOfBoundIndex);

        assertCommandFailure(archiveCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final ArchiveCommand standardCommand = new ArchiveCommand(INDEX_FIRST_PERSON);

        // same index -> returns true
        ArchiveCommand commandWithSameValues = new ArchiveCommand(INDEX_FIRST_PERSON);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new ArchiveCommand(INDEX_SECOND_PERSON)));
    }

    @Test
    public void equals_null_returnsFalse() {
        ArchiveCommand archiveCommand = new ArchiveCommand(INDEX_FIRST_PERSON);
        assertFalse(archiveCommand.equals(null));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        ArchiveCommand archiveCommand = new ArchiveCommand(index);
        String expected = ArchiveCommand.class.getCanonicalName() + "{index=" + index + "}";
        assertEquals(expected, archiveCommand.toString());
    }
}
