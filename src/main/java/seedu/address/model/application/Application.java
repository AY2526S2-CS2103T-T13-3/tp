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

    // ApplicationEvent field
    private final ApplicationEvent applicationEvent;

    // Note field
    private final Note note;

    // Resume field
    private final Resume resume;

    /**
     * Every field must be present and not null except applicationEvent, which may be null.
     */
    public Application(Role role, Phone phone, HrEmail hrEmail, Company company, Set<Tag> tags,
                       Status status, Deadline deadline, ApplicationEvent applicationEvent, Note note,
                       Resume resume) {
        requireAllNonNull(role, phone, hrEmail, company, tags, status, deadline, note, resume);
        this.role = role;
        this.phone = phone;
        this.hrEmail = hrEmail;
        this.company = company;
        this.tags.addAll(tags);
        this.status = status;
        this.deadline = deadline;
        this.applicationEvent = applicationEvent;
        this.note = note;
        this.resume = resume;
    }

    /**
     * Backward-compatible constructor.
     */
    public Application(Role role, Phone phone, HrEmail hrEmail, Company company, Set<Tag> tags,
                       Status status, Deadline deadline, ApplicationEvent applicationEvent, Note note) {
        this(role, phone, hrEmail, company, tags, status, deadline, applicationEvent, note,
                Resume.getEmptyResume());
    }

    /**
     * Constructs a new Application with status APPLIED, empty deadline, empty note, and empty resume by default.
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
        this.applicationEvent = null;
        this.note = new Note("");
        this.resume = Resume.getEmptyResume();
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

    public ApplicationEvent getApplicationEvent() {
        return applicationEvent;
    }

    public Note getNote() {
        return note;
    }

    public Resume getResume() {
        return resume;
    }

    public boolean hasResume() {
        return !resume.isEmpty();
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

        if (!(other instanceof Application otherApplication)) {
            return false;
        }

        return role.equals(otherApplication.role)
                && phone.equals(otherApplication.phone)
                && hrEmail.equals(otherApplication.hrEmail)
                && company.equals(otherApplication.company)
                && tags.equals(otherApplication.tags)
                && status.equals(otherApplication.status)
                && deadline.equals(otherApplication.deadline)
                && Objects.equals(applicationEvent, otherApplication.applicationEvent)
                && note.equals(otherApplication.note)
                && resume.equals(otherApplication.resume);
    }

    @Override
    public int hashCode() {
        return Objects.hash(role, phone, hrEmail, company, tags, status, deadline, applicationEvent, note, resume);
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
                .add("applicationEvent", applicationEvent)
                .add("note", note)
                .add("resume", resume)
                .toString();
    }
}
