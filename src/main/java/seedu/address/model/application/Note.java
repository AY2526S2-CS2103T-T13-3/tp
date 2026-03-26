package seedu.address.model.application;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Note in an Application.
 * Guarantees: immutable; is valid as declared in {@link #isValidNote(String)}
 */
public class Note {

    /** The maximum allowed length for a note. */
    public static final int MAX_LENGTH = 1000;

    /** Error message to display when the note violates constraints. */
    public static final String MESSAGE_CONSTRAINTS =
            "Notes can take any values, but should not be completely blank or exceed " + MAX_LENGTH + " characters.";

    /** The actual text content of the note. */
    public final String value;

    /**
     * Constructs a {@code Note}.
     *
     * @param value A valid note string.
     */
    public Note(String value) {
        requireNonNull(value);
        this.value = value;
    }

    /**
     * Returns true if a given string is a valid note.
     * A note is valid if its length is less than or equal to {@code MAX_LENGTH}.
     *
     * @param test The string to test.
     * @return True if the string is a valid note, false otherwise.
     */
    public static boolean isValidNote(String test) {
        return test.length() <= MAX_LENGTH;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Note)) {
            return false;
        }

        Note otherNote = (Note) other;
        return value.equals(otherNote.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
