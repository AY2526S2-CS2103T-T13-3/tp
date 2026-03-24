package seedu.address.model.application;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Note in an Application.
 */
public class Note {
    public static final String MESSAGE_CONSTRAINTS =
            "Note can be empty and can contain any text.";

    public final String value;

    /**
     * Constructs a {@code Note}.
     *
     * @param value A valid note.
     */
    public Note(String value) {
        requireNonNull(value);
        this.value = value;
    }

    /**
     * Returns true if a given string is a valid note.
     */
    public static boolean isValidNote(String test) {
        return test != null;
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
