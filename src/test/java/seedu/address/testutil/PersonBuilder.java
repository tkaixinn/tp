package seedu.address.testutil;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Country;
import seedu.address.model.person.Email;
import seedu.address.model.person.Event;
import seedu.address.model.person.AddedOn;
import seedu.address.model.person.Name;
import seedu.address.model.person.Note;
import seedu.address.model.person.Offset;
import seedu.address.model.person.Organisation;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.PreferredLanguage;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_COUNTRY = "Singapore";
    public static final String DEFAULT_ORGANISATION = "";
    public static final String DEFAULT_EVENT = "";
    public static final String DEFAULT_NOTE = "";
    public static final String DEFAULT_OFFSET = "+00:00";
    public static final String DEFAULT_ADDEDON = "2023-11-15T14:30:00";
    public static final String DEFAULT_LANGUAGE = "english";
    private static final String DEFAULT_CHANNEL = "EMAIL";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Country country;
    private Organisation organisation;
    private Event event;
    private Note note;
    private Set<Tag> tags;
    private Offset offset;
    private AddedOn addedOn;
    private boolean isArchived;
    private PreferredLanguage preferredLanguage;
    private Person.CommunicationChannel channel;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        country = new Country("");
        organisation = new Organisation(DEFAULT_ORGANISATION);
        event = new Event(DEFAULT_EVENT);
        note = new Note(DEFAULT_NOTE);
        tags = new HashSet<>();
        offset = new Offset(DEFAULT_OFFSET);
        addedOn = new AddedOn(LocalDateTime.parse(DEFAULT_ADDEDON));
        isArchived = false;
        preferredLanguage = new PreferredLanguage(DEFAULT_LANGUAGE);
        channel = Person.CommunicationChannel.valueOf(DEFAULT_CHANNEL);
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        country = personToCopy.getCountry();
        organisation = personToCopy.getOrganisation();
        event = personToCopy.getEvent();
        note = personToCopy.getNote();
        tags = new HashSet<>(personToCopy.getTags());
        offset = personToCopy.getOffset();
        addedOn = personToCopy.getAddedOn();
        isArchived = personToCopy.getArchivalStatus();
        preferredLanguage = personToCopy.getPreferredLanguage();
        channel = personToCopy.getPreferredChannel();
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the
     * {@code Person} that we are building.
     */
    public PersonBuilder withTags(String... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Organisation} of the {@code Person} that we are building.
     */
    public PersonBuilder withOrganisation(String organisation) { // <<<<< ADDED
        this.organisation = new Organisation(organisation);
        return this;
    }

    /**
     * Sets the {@code Event} of the {@code Person} that we are building.
     */
    public PersonBuilder withEvent(String event) { // <<<<< ADDED
        this.event = new Event(event);
        return this;
    }

    /**
     * Sets the {@code Note} of the {@code Person} that we are building.
     */
    public PersonBuilder withNote(String note) {
        this.note = new Note(note);
        return this;
    }

    /**
     * Sets the {@code Country} of the {@code Person} that we are building.
     */
    public PersonBuilder withCountry(String country) {
        this.country = new Country(country);
        return this;
    }

    /**
     * Sets the {@code Offset} of the {@code Person} that we are building.
     */
    public PersonBuilder withOffset(String offset) {
        this.offset = new Offset(offset);
        return this;
    }

    /**
     * Sets the {@code preferredChannel} of the {@code Person} that we are building.
     */
    public PersonBuilder withChannel(String channel) {
        this.channel = Person.CommunicationChannel.valueOf(channel);
        return this;
    }

    /**
     * Sets the {@code preferredLanguage} of the {@code Person} that we are building.
     */
    public PersonBuilder withLanguage(String language) {
        this.preferredLanguage = new PreferredLanguage(language);
        return this;
    }

    /**
     * Sets the {@code AddedOn} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddedOn(String addedOn) {
        this.addedOn = new AddedOn(LocalDateTime.parse(addedOn));
        return this;
    }

    /**
     * Sets the {@code Person} that we are building as archived.
     */
    public PersonBuilder archived() {
        this.isArchived = true;
        return this;
    }

    /**
     * Sets the {@code Person} that we are building as unarchived.
     */
    public PersonBuilder unarchived() {
        this.isArchived = false;
        return this;
    }

    /**
     * Builds the {@code Person}.
     */
    public Person build() {
        return new Person(name, phone, email, address, country, organisation, event, note, channel, tags, offset,
            preferredLanguage, addedOn, isArchived);
    }

}
