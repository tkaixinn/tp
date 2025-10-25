package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Sorts contacts by the date they added the contact.
 */
public class SortDateCommand extends Command {

    public static final String COMMAND_WORD = "sortdate";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts contacts by the date added. ";

    public static final String MESSAGE_SUCCESS = "Addressbook has been sorted by date added!";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.sortPersonsByDate();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}