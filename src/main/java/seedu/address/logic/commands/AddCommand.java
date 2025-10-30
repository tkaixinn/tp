package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CHANNEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COUNTRY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LANGUAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OFFSET;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORGANISATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";
    public static final int MAX_NUMBER_OF_CONTACTS = 500;

    public static final String PARAMETERS = "\nParameters: "
        + PREFIX_NAME + "NAME "
        + PREFIX_PHONE + "PHONE "
        + PREFIX_EMAIL + "EMAIL "
        + PREFIX_ADDRESS + "ADDRESS "
        + PREFIX_OFFSET + "UTC OFFSET "
        + "[" + PREFIX_COUNTRY + "COUNTRY] "
        + "[" + PREFIX_ORGANISATION + "ORGANISATION] "
        + "[" + PREFIX_EVENT + "EVENT] "
        + "[" + PREFIX_CHANNEL + "CHANNEL] "
        + "[" + PREFIX_LANGUAGE + "LANGUAGE] "
        + "[" + PREFIX_NOTE + "NOTE] "
        + "[" + PREFIX_TAG + "TAG]...\n";

    public static final String EXAMPLE = "Example: " + COMMAND_WORD + " "
        + PREFIX_NAME + "John Doe "
        + PREFIX_PHONE + "98765432 "
        + PREFIX_EMAIL + "johnd@example.com "
        + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
        + PREFIX_OFFSET + "+08:00 "
        + PREFIX_COUNTRY + "Singapore "
        + PREFIX_ORGANISATION + "NUS "
        + PREFIX_EVENT + "NUS Hackathon "
        + PREFIX_CHANNEL + "Whatsapp "
        + PREFIX_LANGUAGE + "English "
        + PREFIX_NOTE + "Cannot drink alcohol "
        + PREFIX_TAG + "friends "
        + PREFIX_TAG + "owes money ";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person to the address book. "
            + PARAMETERS + EXAMPLE;

    public static final String MESSAGE_SUCCESS = "New person added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";

    private final Person toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddCommand(Person person) {
        requireNonNull(person);
        toAdd = person;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        if (model.getAddressBook().getPersonList().size() >= MAX_NUMBER_OF_CONTACTS) {
            throw new CommandException("Cannot add more contacts. The address book is full (maximum "
                    + MAX_NUMBER_OF_CONTACTS + " contacts).");
        }

        model.addPerson(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddCommand otherAddCommand)) {
            return false;
        }

        return toAdd.equals(otherAddCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
