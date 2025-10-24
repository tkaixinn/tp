package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_UNARCHIVED;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Archives a person in the current list.
 */
public class ArchiveCommand extends Command {

    public static final String COMMAND_WORD = "archive";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Archives the specified person. "
            + "Parameters: index: <index>\n"
            + "Example: " + COMMAND_WORD
            + " archive 1";

    public static final String MESSAGE_ARCHIVE_SUCCESS = "Person: %1$s has been archived";
    public static final String MESSAGE_ALREADY_ARCHIVED = "Person: %1$s is already archived!";

    private final Index index;

    /**
     * Creates an ArchiveCommand to archive the person.
     */
    public ArchiveCommand(Index index) {
        requireAllNonNull(index);
        this.index = index;
    }

    public ArchiveCommand(Integer index) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToArchive = lastShownList.get(index.getZeroBased());

        if (personToArchive.checkIfArchived()) {
            throw new CommandException(MESSAGE_ALREADY_ARCHIVED);
        }

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_UNARCHIVED);
        return new CommandResult(MESSAGE_ARCHIVE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ArchiveCommand)
                        && index.equals(((ArchiveCommand) other).index);
    }
}
