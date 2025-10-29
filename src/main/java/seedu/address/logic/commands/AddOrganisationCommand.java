package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_UNARCHIVED;

import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Organisation;
import seedu.address.model.person.Person;

/**
 * Adds or updates an organisation for a person identified by name.
 */
public class AddOrganisationCommand extends Command {

    public static final String COMMAND_WORD = "addorg";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Adds or updates an organisation for the specified person. "
        + "Parameters: name: <name> organisation: <organisation text>\n"
        + "Example: " + COMMAND_WORD
        + " name: John Doe organisation: National University of Singapore";

    public static final String MESSAGE_ADD_ORGANISATION_SUCCESS = "Added organisation to Person: %1$s";
    public static final String MESSAGE_DELETE_ORGANISATION_SUCCESS = "Removed organisation from Person: %1$s";
    public static final String MESSAGE_PERSON_NOT_FOUND = "No person found with name: %1$s";

    private final Name name;
    private final Organisation organisation;

    /**
     * Adds or updates the organisation for a person identified by name.
     */
    public AddOrganisationCommand(Name name, Organisation organisation) {
        requireAllNonNull(name, organisation);
        this.name = name;
        this.organisation = organisation;
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

        Person editedPerson = new Person(
            personToEdit.getName(),
            personToEdit.getPhone(),
            personToEdit.getEmail(),
            personToEdit.getAddress(),
            personToEdit.getCountry(),
            organisation, // updated organisation
            personToEdit.getEvent(),
            personToEdit.getNote(),
            personToEdit.getTags(),
            personToEdit.getOffset(),
            personToEdit.getPreferredLanguage(),
            personToEdit.getMetOn(),
            personToEdit.getArchivalStatus()
        );

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_UNARCHIVED);

        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    private String generateSuccessMessage(Person personToEdit) {
        String message = !organisation.value.isEmpty()
            ? MESSAGE_ADD_ORGANISATION_SUCCESS
            : MESSAGE_DELETE_ORGANISATION_SUCCESS;
        return String.format(message, personToEdit.getName());
    }

    @Override
    public boolean equals(Object other) {
        return other == this
            || (other instanceof AddOrganisationCommand
            && name.equals(((AddOrganisationCommand) other).name)
            && organisation.equals(((AddOrganisationCommand) other).organisation));
    }
}
