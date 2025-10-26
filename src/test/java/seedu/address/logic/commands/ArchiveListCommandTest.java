package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) for
 * ArchiveListCommand.
 */
public class ArchiveListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsArchivedList() {
        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_ARCHIVED);
        assertCommandSuccess(new ArchiveListCommand(), model, ArchiveListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsArchivedOnly() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_ARCHIVED);
        assertCommandSuccess(new ArchiveListCommand(), model, ArchiveListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
