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

    /**
     * Every field must be present and not null.
     */
    public Application(Role role, Phone phone, HrEmail hrEmail, Company company, Set<Tag> tags) {
        requireAllNonNull(role, phone, hrEmail, company, tags);
        this.role = role;
        this.phone = phone;
        this.hrEmail = hrEmail;
        this.company = company;
        this.tags.addAll(tags);
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

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons have the same role and company.
     * This defines a weaker notion of equality between two persons/applications.
     */
    public boolean isSameApplication(Application otherApplication) {
        if (otherApplication == this) {
            return true;
        }

        return otherApplication != null
                && otherApplication.getRole().equals(getRole())
                && otherApplication.getCompany().equals(getCompany());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Application)) {
            return false;
        }

        Application otherApplication = (Application) other;
        return role.equals(otherApplication.role)
                && phone.equals(otherApplication.phone)
                && hrEmail.equals(otherApplication.hrEmail)
                && company.equals(otherApplication.company)
                && tags.equals(otherApplication.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(role, phone, hrEmail, company, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("role", role)
                .add("phone", phone)
                .add("hrEmail", hrEmail)
                .add("company", company)
                .add("tags", tags)
                .toString();
    }
}
