package seedu.address.model.application;

import java.util.Objects;

/**
 * The deadline for representing the application.
 */
public class Deadline implements Comparable<Deadline> {
    public static final String EMPTY_DEADLINE_VALUE = "No deadline set";
    public final String value;

    public Deadline(String value) {
        this.value = (value == null || value.isBlank()) ? EMPTY_DEADLINE_VALUE : value;
    }

    public static Deadline getEmptyDeadline() {
        return new Deadline(EMPTY_DEADLINE_VALUE);
    }

    public boolean isEmpty() {
        return value.equals(EMPTY_DEADLINE_VALUE);
    }

    @Override
    public int compareTo(Deadline other) {
        // 排序逻辑：空的 deadline 在最后
        if (this.isEmpty() && !other.isEmpty()) {
            return 1;
        }
        if (!this.isEmpty() && other.isEmpty()) {
            return -1;
        }
        if (this.isEmpty() && other.isEmpty()) {
            return 0;
        }

        return this.value.compareTo(other.value);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Deadline)) {
            return false;
        }

        Deadline otherDeadline = (Deadline) other;
        return value.equals(otherDeadline.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
