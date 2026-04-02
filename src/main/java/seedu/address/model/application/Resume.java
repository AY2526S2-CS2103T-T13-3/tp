package seedu.address.model.application;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Resume attached to an Application.
 * Guarantees: immutable; is valid as declared in {@link #isValidResume(String)}
 */
public class Resume {

    public static final String MESSAGE_CONSTRAINTS =
            "Resume path should end with .pdf, .doc, or .docx.\n"
                    + "Example: resume 1 rp/path/to/resume.pdf";

    public final String value;

    /**
     * Constructs a {@code Resume}.
     *
     * @param value A valid resume path.
     */
    public Resume(String value) {
        requireNonNull(value);
        String trimmed = value.trim();
        if (!isValidResume(trimmed)) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
        this.value = trimmed;
    }

    /**
     * Returns an empty Resume.
     */
    public static Resume getEmptyResume() {
        return new Resume("");
    }

    /**
     * Returns true if the given string is a valid resume path.
     */
    public static boolean isValidResume(String test) {
        if (test == null) {
            return false;
        }
        if (test.isBlank()) {
            return true;
        }

        String lower = test.toLowerCase();
        return lower.endsWith(".pdf") || lower.endsWith(".doc") || lower.endsWith(".docx");
    }

    public boolean isEmpty() {
        return value.isBlank();
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
        if (!(other instanceof Resume)) {
            return false;
        }
        Resume otherResume = (Resume) other;
        return value.equals(otherResume.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
