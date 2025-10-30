package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.OrganisationContainsKeywordPredicate;

/**
 * Finds and lists all persons in the address book whose {@code Organisation}
 * matches the specified keyword.
 * Matching is case-insensitive and exact (i.e., partial matches are not included).
 */
public class FindOrganisationCommand extends Command {

    public static final String COMMAND_WORD = "findorganisation";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose organisation "
        + "matches the specified keyword (case-insensitive) and displays them as a list with index numbers.\n"
        + "Parameters: ORGANISATION\n"
        + "Example: " + COMMAND_WORD + " NUS";

    private final OrganisationContainsKeywordPredicate predicate;

    /**
     * Creates a {@code FindOrganisationCommand} to filter persons by the specified predicate.
     *
     * @param predicate Predicate that checks whether a person's organisation matches the keyword.
     */
    public FindOrganisationCommand(OrganisationContainsKeywordPredicate predicate) {
        this.predicate = predicate;
    }

    /**
     * Executes the command to update the filtered person list according to the predicate.
     *
     * @param model The {@code Model} in which the filtered list will be updated.
     * @return A {@code CommandResult} with feedback about the number of persons listed.
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
        return other == this
            || (other instanceof FindOrganisationCommand
            && predicate.equals(((FindOrganisationCommand) other).predicate));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("predicate", predicate)
            .toString();
    }
}
