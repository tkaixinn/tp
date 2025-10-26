package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.PersonBuilder;

public class UniquePersonListTest {

    private final UniquePersonList uniquePersonList = new UniquePersonList();
    private UniquePersonList list;

    // Test persons:
    // - Two with the same country ("China") to exercise name tie-breaker.
    // - One with another country ("Singapore").
    // - One with empty country to ensure it is pushed to the end in sortByCountry.
    private Person alice; // Singapore, 2024-03-01T10:00
    private Person bob; // China, 2024-01-01T09:00
    private Person ann; // China, 2024-02-01T08:00
    private Person charlie; // "" (empty), 2023-05-01T12:00

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.contains(null));
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniquePersonList.contains(ALICE));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniquePersonList.add(ALICE);
        assertTrue(uniquePersonList.contains(ALICE));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniquePersonList.add(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniquePersonList.contains(editedAlice));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.add(null));
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        uniquePersonList.add(ALICE);
        assertThrows(DuplicatePersonException.class, () -> uniquePersonList.add(ALICE));
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.setPerson(null, ALICE));
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.setPerson(ALICE, null));
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> uniquePersonList.setPerson(ALICE, ALICE));
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        uniquePersonList.add(ALICE);
        uniquePersonList.setPerson(ALICE, ALICE);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        expectedUniquePersonList.add(ALICE);
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        uniquePersonList.add(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniquePersonList.setPerson(ALICE, editedAlice);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        expectedUniquePersonList.add(editedAlice);
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        uniquePersonList.add(ALICE);
        uniquePersonList.setPerson(ALICE, BOB);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        expectedUniquePersonList.add(BOB);
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniquePersonList.add(ALICE);
        uniquePersonList.add(BOB);
        assertThrows(DuplicatePersonException.class, () -> uniquePersonList.setPerson(ALICE, BOB));
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> uniquePersonList.remove(ALICE));
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniquePersonList.add(ALICE);
        uniquePersonList.remove(ALICE);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.setPersons((UniquePersonList) null));
    }

    @Test
    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        uniquePersonList.add(ALICE);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        expectedUniquePersonList.add(BOB);
        uniquePersonList.setPersons(expectedUniquePersonList);
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.setPersons((List<Person>) null));
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniquePersonList.add(ALICE);
        List<Person> personList = Collections.singletonList(BOB);
        uniquePersonList.setPersons(personList);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        expectedUniquePersonList.add(BOB);
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Person> listWithDuplicatePersons = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicatePersonException.class, () -> uniquePersonList.setPersons(listWithDuplicatePersons));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniquePersonList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniquePersonList.asUnmodifiableObservableList().toString(), uniquePersonList.toString());
    }

    @BeforeEach
    public void setUp() {
        list = new UniquePersonList();

        alice = new PersonBuilder()
                .withName("Alice Pauline")
                .withCountry("Singapore")
                .withMetOn(String.valueOf(LocalDateTime.of(2024, 3, 1, 10, 0)))
                .build();

        bob = new PersonBuilder()
                .withName("Bob Builder")
                .withCountry("China")
                .withMetOn(String.valueOf(LocalDateTime.of(2024, 1, 1, 9, 0)))
                .build();

        ann = new PersonBuilder()
                .withName("Ann Alpha")
                .withCountry("China")
                .withMetOn(String.valueOf(LocalDateTime.of(2024, 2, 1, 8, 0)))
                .build();

        // Empty country string is considered "no country" in Country#isValidCountry,
        // which your sortByCountry() pushes to the end via countryKey().
        charlie = new PersonBuilder()
                .withName("Charlie Zero")
                .withCountry("") // empty
                .withMetOn(String.valueOf(LocalDateTime.of(2023, 5, 1, 12, 0)))
                .build();

        // Add in a scrambled order so that sorts have an effect.
        list.add(alice);
        list.add(charlie);
        list.add(bob);
        list.add(ann);
    }

    @Test
    public void asUnmodifiableObservableList_modification_throws() {
        ObservableList<Person> exposed = list.asUnmodifiableObservableList();
        assertThrows(UnsupportedOperationException.class, () -> exposed.remove(0));
        assertThrows(UnsupportedOperationException.class, () -> exposed.add(alice));
    }

    @Test
    public void sortByName_sortsCaseInsensitiveAscending() {
        list.sortByName();
        List<Person> ordered = list.asUnmodifiableObservableList();

        // Alphabetical by name: Alice Pauline, Ann Alpha, Bob Builder, Charlie Zero
        assertEquals(List.of(alice, ann, bob, charlie), ordered);
    }

    @Test
    public void sortByDate_sortsByMetOnAscending() {
        list.sortByDate();
        List<Person> ordered = list.asUnmodifiableObservableList();

        // Ascending by metOn: 2023-05-01 (Charlie), 2024-01-01 (Bob),
        // 2024-02-01 (Ann), 2024-03-01 (Alice)
        assertEquals(List.of(charlie, bob, ann, alice), ordered);
    }

    @Test
    public void sortByCountry_sortsCountryThenName_emptyCountryLast() {
        list.sortByCountry();
        List<Person> ordered = list.asUnmodifiableObservableList();

        // Order:
        //   China (name tie-breaker): Ann Alpha, Bob Builder
        //   Singapore: Alice Pauline
        //   "" (empty country) goes LAST: Charlie Zero
        assertEquals(List.of(ann, bob, alice, charlie), ordered);
    }
}
