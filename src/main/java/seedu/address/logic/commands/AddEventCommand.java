package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_UNARCHIVED;

import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Event;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Adds or updates an event for a person identified by name.
 */
public class AddEventCommand extends Command {

    public static final String COMMAND_WORD = "addevent";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds or updates an event for the specified person. "
        + "Parameters: name: <name> event: <event text>\n"
        + "Example: " + COMMAND_WORD
        + " name: John Doe event: Met at NUS Career Fair 2024.";

    public static final String MESSAGE_ADD_EVENT_SUCCESS = "Added event to Person: %1$s";
    public static final String MESSAGE_DELETE_EVENT_SUCCESS = "Removed event from Person: %1$s";
    public static final String MESSAGE_PERSON_NOT_FOUND = "No person found with name: %1$s";

    private final Name name;
    private final Event event;

    /**
     * Creates an AddEventCommand to add or update the given person's event.
     */
    public AddEventCommand(Name name, Event event) {
        requireAllNonNull(name, event);
        this.name = name;
        this.event = event;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireAllNonNull(model);
        List<Person> allPersons = model.getAddressBook().getPersonList();

        Person personToEdit = allPersons.stream()
            .filter(p -> p.getName().equals(name))
            .findFirst()
            .orElse(null);

        if (personToEdit == null) {
            throw new CommandException(String.format(MESSAGE_PERSON_NOT_FOUND, name.fullName));
        }

        // Create edited person with updated event (note preserved)
        Person editedPerson = new Person(
            personToEdit.getName(),
            personToEdit.getPhone(),
            personToEdit.getEmail(),
            personToEdit.getAddress(),
            personToEdit.getCountry(),
            personToEdit.getOrganisation(),
            event, // updated event (just before note)
            personToEdit.getNote(), // preserve existing note
            personToEdit.getTags(),
            personToEdit.getOffset(),
            personToEdit.getPreferredLanguage(),
            personToEdit.getAddedOn(),
            personToEdit.getArchivalStatus()
        );

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_UNARCHIVED);

        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    /**
     * Generates a command execution success message based on whether the event is added or removed.
     */
    private String generateSuccessMessage(Person personToEdit) {
        String message = !event.value.isEmpty()
            ? MESSAGE_ADD_EVENT_SUCCESS
            : MESSAGE_DELETE_EVENT_SUCCESS;
        return String.format(message, personToEdit.getName());
    }

    @Override
    public boolean equals(Object other) {
        return other == this
            || (other instanceof AddEventCommand
            && name.equals(((AddEventCommand) other).name)
            && event.equals(((AddEventCommand) other).event));
    }
}
