package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Sorts contacts by name.
 */
public class SortNameCommand extends Command {

    public static final String COMMAND_WORD = "sortname";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts contacts by name. ";

    public static final String MESSAGE_SUCCESS = "Addressbook has been sorted by name!";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.sortPersonsByName();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
