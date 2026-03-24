package seedu.address.model.application;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents an Application record in Hired!.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Application {

    // Identity fields
    private final Role role;
    private final Phone phone;
    private final HrEmail hrEmail;

    // Data fields
    private final Company company;
    private final Set<Tag> tags = new HashSet<>();

    // Status field
    private final Status status;

    // Deadline field
    private final Deadline deadline;

    // Note field
    private final Note note;

    /**
     * Every field must be present and not null.
     */
    public Application(Role role, Phone phone, HrEmail hrEmail, Company company, Set<Tag> tags,
                       Status status, Deadline deadline, Note note) {
        requireAllNonNull(role, phone, hrEmail, company, tags, status, deadline, note);
        this.role = role;
        this.phone = phone;
        this.hrEmail = hrEmail;
        this.company = company;
        this.tags.addAll(tags);
        this.status = status;
        this.deadline = deadline;
        this.note = note;
    }

    /**
     * Constructs a new Application with status APPLIED, empty deadline, and empty note by default.
     */
    public Application(Role role, Phone phone, HrEmail hrEmail, Company company, Set<Tag> tags) {
        requireAllNonNull(role, phone, hrEmail, company, tags);
        this.role = role;
        this.phone = phone;
        this.hrEmail = hrEmail;
        this.company = company;
        this.tags.addAll(tags);
        this.status = Status.APPLIED;
        this.deadline = Deadline.getEmptyDeadline();
        this.note = new Note("");
    }

    public Role getRole() {
        return role;
    }

    public Phone getPhone() {
        return phone;
    }

    public HrEmail getHrEmail() {
        return hrEmail;
    }

    public Company getCompany() {
        return company;
    }

    public Status getStatus() {
        return status;
    }

    public Deadline getDeadline() {
        return deadline;
    }

    public Note getNote() {
        return note;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both applications have the same role.
     * This defines a weaker notion of equality between two applications, used for identity.
     */
    public boolean isSameApplication(Application otherApplication) {
        if (otherApplication == this) {
            return true;
        }

        return otherApplication != null
                && otherApplication.getRole().equals(getRole())
                && otherApplication.getCompany().companyName.equalsIgnoreCase(getCompany().companyName);
    }

    /**
     * Returns true if both applications have the same identity and data fields.
     * This defines a stronger notion of equality between two applications.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Application)) {
            return false;
        }

        Application otherApplication = (Application) other;
        return role.equals(otherApplication.role)
                && phone.equals(otherApplication.phone)
                && hrEmail.equals(otherApplication.hrEmail)
                && company.equals(otherApplication.company)
                && tags.equals(otherApplication.tags)
                && status.equals(otherApplication.status)
                && deadline.equals(otherApplication.deadline)
                && note.equals(otherApplication.note);
    }

    @Override
    public int hashCode() {
        return Objects.hash(role, phone, hrEmail, company, tags, status, deadline, note);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("role", role)
                .add("phone", phone)
                .add("hrEmail", hrEmail)
                .add("company", company)
                .add("tags", tags)
                .add("status", status)
                .add("deadline", deadline)
                .add("note", note)
                .toString();
    }
}
