package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.PersonContainsTagsPredicate;

/**
 * Finds and lists all persons in address book whose tag contains ALL of the
 * argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FindTagCommand extends Command {

    public static final String COMMAND_WORD = "findtag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons who is tagged with all of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " friends";

    private final PersonContainsTagsPredicate predicate;

    /**
     * Creates a FindTagCommand to filter the person list using the given {@code predicate}.
     *
     * @param predicate the condition used to test each person for matching tags.
     */
    public FindTagCommand(PersonContainsTagsPredicate predicate) {
        this.predicate = predicate;
    }

    /**
     * Executes the find tag command and returns the result message.
     * Updates the filtered person list in the model to show only persons
     * whose tags match all specified keywords.
     *
     * @param model {@code Model} which the command should operate on.
     * @return a {@code CommandResult} containing the result message.
     */
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    /**
     * Returns true if both FindTagCommand objects have the same predicate.
     *
     * @param other another object to compare with.
     * @return true if both commands are equal; false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindTagCommand)) {
            return false;
        }

        FindTagCommand otherFindTagCommand = (FindTagCommand) other;
        return predicate.equals(otherFindTagCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
