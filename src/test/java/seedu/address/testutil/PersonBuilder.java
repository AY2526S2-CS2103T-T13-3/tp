package seedu.company.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.company.model.application.Application;
import seedu.company.model.application.Company;
import seedu.company.model.application.HrEmail;
import seedu.company.model.application.Phone;
import seedu.company.model.application.Role;
import seedu.company.model.tag.Tag;
import seedu.company.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_COMPANY = "123, Jurong West Ave 6, #08-111";

    private Role role;
    private Phone phone;
    private HrEmail hrEmail;
    private Company company;
    private Set<Tag> tags;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        role = new Role(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        hrEmail = new HrEmail(DEFAULT_EMAIL);
        company = new Company(DEFAULT_COMPANY);
        tags = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Application applicationToCopy) {
        role = applicationToCopy.getName();
        phone = applicationToCopy.getPhone();
        hrEmail = applicationToCopy.getEmail();
        company = applicationToCopy.getCompany();
        tags = new HashSet<>(applicationToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.role = new Role(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Company} of the {@code Person} that we are building.
     */
    public PersonBuilder withCompany(String company) {
        this.company = new Company(company);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.hrEmail = new HrEmail(email);
        return this;
    }

    public Application build() {
        return new Application(role, phone, hrEmail, company, tags);
    }

}
