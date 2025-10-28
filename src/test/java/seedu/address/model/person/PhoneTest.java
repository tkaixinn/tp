package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PhoneTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Phone(null));
    }

    @Test
    public void constructor_invalidPhone_throwsIllegalArgumentException() {
        String invalidPhone = "";
        assertThrows(IllegalArgumentException.class, () -> new Phone(invalidPhone));
    }

    @Test
    public void isValidPhone() {
        // null phone number
        assertThrows(NullPointerException.class, () -> Phone.isValidPhone(null));

        // invalid phone numbers
        assertFalse(Phone.isValidPhone("")); // empty string
        assertFalse(Phone.isValidPhone(" ")); // spaces only
        assertFalse(Phone.isValidPhone("91")); // less than 3 numbers
        assertFalse(Phone.isValidPhone("phone")); // non-numeric
        assertFalse(Phone.isValidPhone("9011p041")); // alphabets within digits

        // valid phone numbers
        assertTrue(Phone.isValidPhone("911")); // exactly 3 numbers
        assertTrue(Phone.isValidPhone("93121534"));
        assertTrue(Phone.isValidPhone("124293842033123")); // long phone numbers
        assertTrue(Phone.isValidPhone("9312 1534")); // spaces within digits
    }

    @Test
    public void countryDetection_validNumbers_success() {
        assertEquals("65", new Phone("+6598765432").getCountryCode());
        assertEquals("1", new Phone("+14155552671").getCountryCode());
        assertEquals("91", new Phone("+919876543210").getCountryCode());
    }

    @Test
    public void toString_formatsCorrectly() {
        assertEquals("+6598765432 (65)", new Phone("+6598765432").toString());
    }

    @Test
    public void equals() {
        Phone phone = new Phone("999");

        // same values -> returns true
        assertTrue(phone.equals(new Phone("999")));

        // same object -> returns true
        assertTrue(phone.equals(phone));

        // null -> returns false
        assertFalse(phone.equals(null));

        // different types -> returns false
        assertFalse(phone.equals(5.0f));

        // different values -> returns false
        assertFalse(phone.equals(new Phone("995")));
    }
    @Test
    public void isValidPhone_minLength() {
        assertFalse(Phone.isValidPhone("12")); // less than 3 digits
        assertTrue(Phone.isValidPhone("123")); // exactly 3 digits
    }

    @Test
    public void isValidPhone_onlyNumbers() {
        assertTrue(Phone.isValidPhone("1234567890"));
        assertTrue(Phone.isValidPhone("999"));
    }

    @Test
    public void isValidPhone_withSpacesAndDashes() {
        assertTrue(Phone.isValidPhone("123-456-7890"));
        assertTrue(Phone.isValidPhone("123 456 7890"));
        assertTrue(Phone.isValidPhone("+1 123-456-7890"));
    }

    @Test
    public void isValidPhone_withParentheses() {
        assertTrue(Phone.isValidPhone("(123) 456-7890"));
        assertTrue(Phone.isValidPhone("+65 (123) 456 7890"));
    }

    @Test
    public void isValidPhone_invalidCharacters() {
        assertFalse(Phone.isValidPhone("123A456"));
        assertFalse(Phone.isValidPhone("+65@12345678"));
        assertFalse(Phone.isValidPhone("phone123"));
    }

    @Test
    public void isValidPhone_leadingPlus() {
        assertTrue(Phone.isValidPhone("+6598765432"));
        assertTrue(Phone.isValidPhone("+14155552671"));
        assertFalse(Phone.isValidPhone("+12")); // less than 3 digits
    }

    @Test
    public void isValidPhone_emptyString() {
        assertFalse(Phone.isValidPhone(""));
    }

    @Test
    public void constructor_validPhone_numbers() {
        Phone phone1 = new Phone("911");
        assertEquals("911", phone1.value);
        Phone phone2 = new Phone("+6598765432");
        assertEquals("+6598765432", phone2.value);
    }

    @Test
    public void constructor_invalidPhone_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> new Phone(""));
        assertThrows(IllegalArgumentException.class, () -> new Phone("12"));
        assertThrows(IllegalArgumentException.class, () -> new Phone("phone123"));
    }

    @Test
    public void countryCode_derivation_validNumbers() {
        assertEquals("65", new Phone("+6598765432").getCountryCode());
        assertEquals("1", new Phone("+14155552671").getCountryCode());
        assertEquals("91", new Phone("+919876543210").getCountryCode());
    }

    @Test
    public void equals_sameValue() {
        Phone phone1 = new Phone("999");
        Phone phone2 = new Phone("999");
        assertTrue(phone1.equals(phone2));
    }

    @Test
    public void equals_sameObject() {
        Phone phone = new Phone("999");
        assertTrue(phone.equals(phone));
    }

    @Test
    public void equals_nullAndDifferentType() {
        Phone phone = new Phone("999");
        assertFalse(phone.equals(null));
        assertFalse(phone.equals("999"));
        assertFalse(phone.equals(123));
    }

    @Test
    public void equals_differentValues() {
        Phone phone1 = new Phone("999");
        Phone phone2 = new Phone("995");
        assertFalse(phone1.equals(phone2));
    }

    @Test
    public void hashCode_consistency() {
        Phone phone = new Phone("+6598765432");
        int first = phone.hashCode();
        int second = phone.hashCode();
        assertEquals(first, second);
    }

    @Test
    public void hashCode_differentPhones() {
        Phone phone1 = new Phone("+6598765432");
        Phone phone2 = new Phone("+14155552671");
        assertFalse(phone1.hashCode() == phone2.hashCode());
    }

    @Test
    public void immutability_testValueField() {
        Phone phone = new Phone("911");
        String copy = phone.value;
        copy = "12345";
        assertEquals("911", phone.value);
    }

    @Test
    public void immutability_testCountryCodeField() {
        Phone phone = new Phone("+6598765432");
        String copy = phone.countryCode;
        copy = "99";
        assertEquals("65", phone.countryCode);
    }

    @Test
    public void multipleValidPhones() {
        String[] validNumbers = { "911", "93121534", "+14155552671", "+919876543210", "124293842033123" };
        for (String number : validNumbers) {
            Phone phone = new Phone(number);
            assertTrue(Phone.isValidPhone(number));
            assertNotNull(phone.getCountryCode());
        }
    }

    @Test
    public void repeatedToStringConsistency() {
        Phone phone = new Phone("+6598765432");
        String first = phone.toString();
        String second = phone.toString();
        assertEquals(first, second);
    }

    @Test
    public void repeatedEqualsConsistency() {
        Phone phone1 = new Phone("911");
        Phone phone2 = new Phone("911");
        assertTrue(phone1.equals(phone2));
        assertTrue(phone1.equals(phone2));
    }

    @Test
    public void repeatedHashCodeConsistency() {
        Phone phone = new Phone("911");
        int first = phone.hashCode();
        int second = phone.hashCode();
        int third = phone.hashCode();
        assertEquals(first, second);
        assertEquals(second, third);
    }
    @Test
    public void isValidPhone_multipleLeadingSigns() {
        assertFalse(Phone.isValidPhone("++6598765432"));
        assertFalse(Phone.isValidPhone("+-6598765432"));
        assertFalse(Phone.isValidPhone("+--1234567"));

        assertTrue(Phone.isValidPhone("+6598765432"));
        assertTrue(Phone.isValidPhone("+1 123-456-7890"));
    }

    @Test
    public void isValidPhone_plusInsideNumber_invalid() {
        assertFalse(Phone.isValidPhone("123+456789"));
        assertFalse(Phone.isValidPhone("12+34+56"));
        assertFalse(Phone.isValidPhone("123456+"));
    }

    @Test
    public void isValidPhone_onlyDigitsAndAllowedSymbols() {
        assertTrue(Phone.isValidPhone("1234567890"));
        assertTrue(Phone.isValidPhone("123 456 7890"));
        assertTrue(Phone.isValidPhone("(123) 456-7890"));

        assertFalse(Phone.isValidPhone("123*456"));
        assertFalse(Phone.isValidPhone("123#456"));
        assertFalse(Phone.isValidPhone("123@456"));
    }

    @Test
    public void constructor_invalidPhone_multipleSigns_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> new Phone("++6598765432"));
        assertThrows(IllegalArgumentException.class, () -> new Phone("+-6598765432"));
    }

    @Test
    public void constructor_invalidPhone_plusInside_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> new Phone("123+456789"));
    }

    @Test
    public void isValidPhone_maxLength() {
        // 15 digits -> valid
        assertTrue(Phone.isValidPhone("123456789012345"));
        assertTrue(Phone.isValidPhone("+651234567890123")); // including country code

        // 16 digits -> invalid
        assertFalse(Phone.isValidPhone("1234567890123456"));
        assertFalse(Phone.isValidPhone("+6512345678901234")); // including country code
    }

    @Test
    public void constructor_invalidPhone_exceedsMaxLength() {
        // 16 digits should throw IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> new Phone("1234567890123456"));
        assertThrows(IllegalArgumentException.class, () -> new Phone("+6512345678901234"));
    }

    @Test
    public void constructor_validPhone_maxLength() {
        // 15 digits should be allowed
        Phone phone1 = new Phone("123456789012345");
        assertEquals("123456789012345", phone1.value);

        Phone phone2 = new Phone("+651234567890123");
        assertEquals("+651234567890123", phone2.value);
    }
}
