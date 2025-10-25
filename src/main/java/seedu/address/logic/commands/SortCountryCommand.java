package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Sorts contacts by country.
 */
public class SortCountryCommand extends Command {

    public static final String COMMAND_WORD = "sortcountry";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts contacts by country. ";

    public static final String MESSAGE_SUCCESS = "Addressbook has been sorted by country!";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.sortPersonsByCountry();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
