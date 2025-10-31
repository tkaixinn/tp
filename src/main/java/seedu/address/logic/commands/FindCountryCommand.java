package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.CountryContainsKeywordPredicate;

/**
 * Finds and lists all persons in address book who comes from the country specified in the argument.
 * Country matching is case-insensitive.
 */
public class FindCountryCommand extends Command {

    /** The command word used to execute this command in the CLI. */
    public static final String COMMAND_WORD = "findcountry";

    /**
     * Usage message for the {@code findcountry} command.
     * Shows correct format and example for user reference.
     */
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons who come from "
            + "the specified country (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: COUNTRY \n"
            + "Example: " + COMMAND_WORD + " Singapore";

    private final CountryContainsKeywordPredicate predicate;

    /**
     * Creates a {@code FindCountryCommand} with the specified {@code CountryContainsKeywordPredicate}.
     *
     * @param predicate the condition used to filter persons by country.
     */
    public FindCountryCommand(CountryContainsKeywordPredicate predicate) {
        this.predicate = predicate;
    }

    /**
     * Executes the {@code FindCountryCommand} and filters the list of persons in the model
     * to only those whose country matches the given predicate.
     *
     * @param model the model containing the person list and other app data.
     * @return a {@code CommandResult} containing the result message and updated list.
     */
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindCountryCommand)) {
            return false;
        }

        FindCountryCommand otherFindCountryCommand = (FindCountryCommand) other;
        return predicate.equals(otherFindCountryCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
