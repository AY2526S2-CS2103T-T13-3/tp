package seedu.address.model.application;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Role role;
    private final Phone phone;
    private final HrEmail hrEmail;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Role role, Phone phone, HrEmail hrEmail, Address address, Set<Tag> tags) {
        requireAllNonNull(role, phone, hrEmail, address, tags);
        this.role = role;
        this.phone = phone;
        this.hrEmail = hrEmail;
        this.address = address;
        this.tags.addAll(tags);
    }

    public Role getName() {
        return role;
    }

    public Phone getPhone() {
        return phone;
    }

    public HrEmail getEmail() {
        return hrEmail;
    }

    public Address getAddress() {
        return address;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
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
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return role.equals(otherPerson.role)
                && phone.equals(otherPerson.phone)
                && hrEmail.equals(otherPerson.hrEmail)
                && address.equals(otherPerson.address)
                && tags.equals(otherPerson.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(role, phone, hrEmail, address, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", role)
                .add("phone", phone)
                .add("email", hrEmail)
                .add("address", address)
                .add("tags", tags)
                .toString();
    }

}
