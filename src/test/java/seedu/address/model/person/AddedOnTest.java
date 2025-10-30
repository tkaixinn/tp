package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class AddedOnTest {

    @Test
    public void toString_correctFormat() {
        // Arrange
        LocalDateTime dateTime = LocalDateTime.of(2005, 3, 24, 15, 0);
        AddedOn addedOn = new AddedOn(dateTime);

        // Act
        String result = addedOn.toString();

        // Assert
        assertEquals("24 March 2005 (15:00)", result);
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 1, 1, 10, 0);
        AddedOn addedOn = new AddedOn(dateTime);
        assertEquals(addedOn, addedOn);
    }

    @Test
    public void equals_differentObjectSameDate_returnsTrue() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 1, 1, 10, 0);
        AddedOn addedOn1 = new AddedOn(dateTime);
        AddedOn addedOn2 = new AddedOn(LocalDateTime.of(2023, 1, 1, 10, 0));
        assertEquals(addedOn1, addedOn2);
    }

    @Test
    public void equals_differentDates_returnsFalse() {
        AddedOn addedOn1 = new AddedOn(LocalDateTime.of(2023, 1, 1, 10, 0));
        AddedOn addedOn2 = new AddedOn(LocalDateTime.of(2023, 1, 2, 10, 0));
        assertNotEquals(addedOn1, addedOn2);
    }

    @Test
    public void equals_null_returnsFalse() {
        AddedOn addedOn = new AddedOn(LocalDateTime.of(2023, 1, 1, 10, 0));
        assertNotEquals(addedOn, null);
    }

    @Test
    public void equals_differentType_returnsFalse() {
        AddedOn addedOn = new AddedOn(LocalDateTime.of(2023, 1, 1, 10, 0));
        assertNotEquals(addedOn, "not a AddedOn object");
    }

    @Test
    public void hashCode_sameDate_returnsSameHashCode() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 1, 1, 10, 0);
        AddedOn addedOn1 = new AddedOn(dateTime);
        AddedOn addedOn2 = new AddedOn(LocalDateTime.of(2023, 1, 1, 10, 0));
        assertEquals(addedOn1.hashCode(), addedOn2.hashCode());
    }

    @Test
    public void hashCode_differentDates_returnsDifferentHashCode() {
        AddedOn addedOn1 = new AddedOn(LocalDateTime.of(2023, 1, 1, 10, 0));
        AddedOn addedOn2 = new AddedOn(LocalDateTime.of(2023, 1, 2, 10, 0));
        assertNotEquals(addedOn1.hashCode(), addedOn2.hashCode());
    }
}
