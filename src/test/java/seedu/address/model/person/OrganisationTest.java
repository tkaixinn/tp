package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class OrganisationTest {

    @Test
    public void equals() {
        Organisation org = new Organisation("NUS");

        // same object -> returns true
        assertTrue(org.equals(org));

        // same values -> returns true
        Organisation orgCopy = new Organisation("NUS");
        assertTrue(org.equals(orgCopy));

        // different types -> returns false
        assertFalse(org.equals(1));

        // null -> returns false
        assertFalse(org.equals(null));

        // different organisation -> returns false
        Organisation differentOrg = new Organisation("NTU");
        assertFalse(org.equals(differentOrg));

        // empty organisation vs non-empty -> returns false
        Organisation emptyOrg = new Organisation("");
        assertFalse(org.equals(emptyOrg));

        // two empty organisations -> returns true
        Organisation anotherEmpty = new Organisation("");
        assertTrue(emptyOrg.equals(anotherEmpty));
    }

    @Test
    public void toString_returnsValue() {
        Organisation org = new Organisation("National University of Singapore");
        assertEquals("National University of Singapore", org.toString());

        Organisation emptyOrg = new Organisation("");
        assertEquals("", emptyOrg.toString());
    }

    @Test
    public void hashCode_sameValue_sameHashCode() {
        Organisation org1 = new Organisation("Google");
        Organisation org2 = new Organisation("Google");
        assertEquals(org1.hashCode(), org2.hashCode());
    }

    @Test
    public void isValidOrganisation_exceedsMaxLength_returnsFalse() {
        String longOrganisation = "a".repeat(61);
        assertFalse(Organisation.isValidOrganisation(longOrganisation));
    }

    @Test
    public void isValidOrganisation_maxLengthBoundary_returnsTrue() {
        String validOrganisation = "a".repeat(60);
        assertTrue(Organisation.isValidOrganisation(validOrganisation));
    }
}
