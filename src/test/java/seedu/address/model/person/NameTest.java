package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class NameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new Name(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> Name.isValidName(null));


        assertFalse(Name.isValidName(""));
        assertFalse(Name.isValidName(" "));
        assertFalse(Name.isValidName("^"));
        assertFalse(Name.isValidName("peter*"));


        assertTrue(Name.isValidName("peter jack"));
        assertTrue(Name.isValidName("12345"));
        assertTrue(Name.isValidName("peter the 2nd"));
        assertTrue(Name.isValidName("Capital Tan"));
        assertTrue(Name.isValidName("David Roger Jackson Ray Jr 2nd"));
    }

    @Test
    public void equals() {
        Name name = new Name("Valid Name");

        // same values -> returns true
        assertTrue(name.equals(new Name("Valid Name")));

        // same object -> returns true
        assertTrue(name.equals(name));

        // null -> returns false
        assertFalse(name.equals(null));

        // different types -> returns false
        assertFalse(name.equals(5.0f));

        // different values -> returns false
        assertFalse(name.equals(new Name("Other Valid Name")));
    }

    @Test
    public void toString_returnsFullName() {
        Name name = new Name("Alice Bob");
        assertTrue(name.toString().equals("Alice Bob"));
    }

    @Test
    public void hashCode_sameForSameName() {
        Name name1 = new Name("Charlie");
        Name name2 = new Name("Charlie");
        assertTrue(name1.hashCode() == name2.hashCode());
    }

    @Test
    public void hashCode_differentForDifferentNames() {
        Name name1 = new Name("Charlie");
        Name name2 = new Name("David");
        assertFalse(name1.hashCode() == name2.hashCode());
    }

    @Test
    public void isValidName_validNames() {
        assertTrue(Name.isValidName("A"));
        assertTrue(Name.isValidName("John Doe"));
        assertTrue(Name.isValidName("Mary Jane 2nd"));
        assertTrue(Name.isValidName("X Æ A-12".replaceAll("[^\\p{Alnum} ]", "")));
        assertTrue(Name.isValidName("O"));
    }

    @Test
    public void isValidName_invalidNames() {
        assertFalse(Name.isValidName(""));
        assertFalse(Name.isValidName(" "));
        assertFalse(Name.isValidName("  John"));
        assertFalse(Name.isValidName("John@"));
        assertFalse(Name.isValidName("John*Doe"));
        assertFalse(Name.isValidName("123!"));
    }

    @Test
    public void constructor_trimsNotApplied() {
        String input = "  Alice ";
        assertThrows(IllegalArgumentException.class, () -> new Name(input));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        Name name = new Name("Eve");
        assertTrue(name.equals(name));
    }

    @Test
    public void equals_differentObjectsSameName_returnsTrue() {
        Name name1 = new Name("Eve");
        Name name2 = new Name("Eve");
        assertTrue(name1.equals(name2));
    }

    @Test
    public void equals_differentObjectsDifferentName_returnsFalse() {
        Name name1 = new Name("Eve");
        Name name2 = new Name("Adam");
        assertFalse(name1.equals(name2));
    }

    @Test
    public void equals_null_returnsFalse() {
        Name name = new Name("Eve");
        assertFalse(name.equals(null));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        Name name = new Name("Eve");
        assertFalse(name.equals("Eve"));
    }

    @Test
    public void equals_caseInsensitive_returnsTrue() {
        Name name1 = new Name("John Doe");
        Name name2 = new Name("john doe");
        assertTrue(name1.equals(name2));
    }

    @Test
    public void immutability_test() {
        Name name = new Name("Immutable");
        String original = name.fullName;
        original = "Changed";
        assertEquals("Immutable", name.fullName);
    }

    @Test
    public void isValidName_onlyAlphanumericAndSpaces() {
        assertTrue(Name.isValidName("A1 B2 C3"));
        assertFalse(Name.isValidName("A1! B2?"));
    }

    @Test
    public void longNames_valid() {
        String longName = "This Is A Very Long Name With Multiple Words And Numbers 12345";
        assertTrue(Name.isValidName(longName));
        Name name = new Name(longName);
        assertEquals(longName, name.fullName);
    }

    @Test
    public void isValidName_exceedsMaxLength_returnsFalse() {
        String longName = "a".repeat(71);
        assertFalse(Name.isValidName(longName));
    }

    @Test
    public void isValidName_maxLengthBoundary_returnsTrue() {
        String validName = "a".repeat(70);
        assertTrue(Name.isValidName(validName));
    }
}
