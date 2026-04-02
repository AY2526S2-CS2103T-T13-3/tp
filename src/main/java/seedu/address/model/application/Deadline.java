package seedu.address.model.application;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Objects;

/**
 * The deadline for representing the application.
 */
public class Deadline implements Comparable<Deadline> {

    public static final String MESSAGE_CONSTRAINTS_FORMAT =
            "Deadlines must follow yyyy-MM-dd or yyyy-MM-dd HH:mm format.";
    public static final String MESSAGE_CONSTRAINTS_DATE =
            "The deadline provided is not a valid date on the calendar (e.g., 2026-01-90 or Feb 29th on a non-leap year).";

    public static final String MESSAGE_CONSTRAINTS =
            "Deadlines should not be blank, must follow yyyy-MM-dd format, and be a valid calendar date.";

    public static final String EMPTY_DEADLINE_VALUE = "No deadline set";
    public static final String PLACEHOLDER_DEADLINE = "-";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("uuuu-MM-dd")
            .withResolverStyle(ResolverStyle.STRICT);
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm")
            .withResolverStyle(ResolverStyle.STRICT);
    public final String value;

    /**
     * Constructs a {@code Deadline}.
     *
     * <p>Blank input will be treated as an empty deadline placeholder.
     *
     * @param value the deadline value.
     * @throws NullPointerException if {@code value} is {@code null}.
     */
    public Deadline(String value) {
        this.value = (value == null || value.isBlank()) ? EMPTY_DEADLINE_VALUE : value;
    }

    public static Deadline getEmptyDeadline() {
        return new Deadline(EMPTY_DEADLINE_VALUE);
    }

    public boolean isEmpty() {
        return value.equals(EMPTY_DEADLINE_VALUE);
    }

    /**
     * Returns true if a given string is a valid deadline.
     * <p>
     * Valid values are:
     * <ul>
     *     <li>{@code "yyyy-MM-dd"}</li>
     *     <li>{@code "yyyy-MM-dd HH:mm"}</li>
     *     <li>{@code "-"} as an empty/deadline-unset placeholder</li>
     * </ul>
     *
     * @throws NullPointerException if {@code test} is {@code null}
     */
    public static boolean isValidFormat(String test) {
        Objects.requireNonNull(test);
        if (test.isBlank()) {
            return false;
        }
        if (test.equals(PLACEHOLDER_DEADLINE)) {
            return true;
        }
        return test.matches("\\d{4}-\\d{2}-\\d{2}") || test.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}");
    }

    public static boolean isValidCalendarDate(String test) {
        if (test.equals(PLACEHOLDER_DEADLINE)) {
            return true;
        }
        try {
            if (test.matches("\\d{4}-\\d{2}-\\d{2}")) {
                LocalDate.parse(test, DATE_FORMATTER);
                return true;
            }
            if (test.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}")) {
                LocalDateTime.parse(test, DATE_TIME_FORMATTER);
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isValidDeadline(String test) {
        return isValidFormat(test) && isValidCalendarDate(test);
    }

    public LocalDate getLocalDate() {
        if (isEmpty() || value.equals(PLACEHOLDER_DEADLINE)) {
            return null;
        }
        try {
            return LocalDate.parse(value.substring(0, 10));
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public int compareTo(Deadline other) {
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

    @Override
    public String toString() {
        return this.value;
    }
}
