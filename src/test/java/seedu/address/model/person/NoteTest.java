package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class NoteTest {

    @Test
    public void equals() {
        Note note = new Note("Hello");

        // same object -> returns true
        assertTrue(note.equals(note));

        // same values -> returns true
        Note noteCopy = new Note("Hello");
        assertTrue(note.equals(noteCopy));

        // different types -> returns false
        assertFalse(note.equals(1));

        // null -> returns false
        assertFalse(note.equals(null));

        // different note -> returns false
        Note differentNote = new Note("Bye");
        assertFalse(note.equals(differentNote));

        // empty note vs non-empty -> returns false
        Note emptyNote = new Note("");
        assertFalse(note.equals(emptyNote));

        // two empty notes -> returns true
        Note anotherEmpty = new Note("");
        assertTrue(emptyNote.equals(anotherEmpty));
    }

    @Test
    public void toString_returnsValue() {
        Note note = new Note("Prefers WhatsApp");
        assertEquals("Prefers WhatsApp", note.toString());

        Note emptyNote = new Note("");
        assertEquals("", emptyNote.toString());
    }

    @Test
    public void hashCode_sameValue_sameHashCode() {
        Note note1 = new Note("Test note");
        Note note2 = new Note("Test note");
        assertEquals(note1.hashCode(), note2.hashCode());
    }
}