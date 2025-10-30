package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class Phone {

    public static final String MESSAGE_CONSTRAINTS = "Phone numbers should be valid international numbers "
            + "(e.g. +6598765432) "
            + "or contain at least 3 digits if no country code is provided.";

    public static final String VALIDATION_REGEX = "^\\+?[0-9\\-()\\s]*$";
    public final String value;
    public final String countryCode;

    /**
     * Constructs a {@code Phone}.
     *
     * @param phone A valid phone number.
     */
    public Phone(String phone) {
        requireNonNull(phone);
        checkArgument(isValidPhone(phone), MESSAGE_CONSTRAINTS);
        this.value = phone;
        this.countryCode = deriveCountryCode(phone);
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidPhone(String test) {
        if (test == null) {
            throw new NullPointerException();
        }

        if (!test.matches(VALIDATION_REGEX)) {
            return false;
        }

        String digitsOnly = test.replaceAll("\\D", "");
        if (digitsOnly.length() < 3 || digitsOnly.length() > 15) {
            return false;
        }

        if (test.startsWith("+")) {
            if (test.length() < 2 || !Character.isDigit(test.charAt(1))) {
                return false;
            }
        }

        if (test.substring(1).contains("+")) {
            return false;
        }
        return true;
    }

    /**
     * Uses Google's libphonenumber to parse the phone number and extract the ISO
     * country code.
     */
    private String deriveCountryCode(String phone) {
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        try {
            Phonenumber.PhoneNumber parsedNumber = phoneUtil.parse(phone, "");

            int countryCallingCode = parsedNumber.getCountryCode();

            return String.valueOf(countryCallingCode);

        } catch (NumberParseException e) {
            return "Invalid";
        }
    }

    public String getCountryCode() {
        return deriveCountryCode(value);
    }

    @Override
    public String toString() {
        return !countryCode.equals("Invalid") ? value + " (" + countryCode + ")" : value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Phone otherPhone)) {
            return false;
        }

        return value.equals(otherPhone.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
