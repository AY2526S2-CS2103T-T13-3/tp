package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.application.Application;
import seedu.address.model.application.Company;
import seedu.address.model.application.HrEmail;
import seedu.address.model.application.Phone;
import seedu.address.model.application.Role;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Application objects.
 */
public class ApplicationBuilder {

    public static final String DEFAULT_ROLE = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_HREMAIL = "amy@gmail.com";
    public static final String DEFAULT_COMPANY = "123, Jurong West Ave 6, #08-111";

    private Role role;
    private Phone phone;
    private HrEmail hrEmail;
    private Company company;
    private Set<Tag> tags;

    /**
     * Creates a {@code ApplicationBuilder} with the default details.
     */
    public ApplicationBuilder() {
        role = new Role(DEFAULT_ROLE);
        phone = new Phone(DEFAULT_PHONE);
        hrEmail = new HrEmail(DEFAULT_HREMAIL);
        company = new Company(DEFAULT_COMPANY);
        tags = new HashSet<>();
    }

    /**
     * Initializes the ApplicationBuilder with the data of {@code applicationToCopy}.
     */
    public ApplicationBuilder(Application applicationToCopy) {
        role = applicationToCopy.getRole();
        phone = applicationToCopy.getPhone();
        hrEmail = applicationToCopy.getHrEmail();
        company = applicationToCopy.getCompany();
        tags = new HashSet<>(applicationToCopy.getTags());
    }

    /**
     * Sets the {@code Role} of the {@code Application} that we are building.
     */
    public ApplicationBuilder withRole(String role) {
        this.role = new Role(role);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Application} that we are building.
     */
    public ApplicationBuilder withTags(String... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Company} of the {@code Application} that we are building.
     */
    public ApplicationBuilder withCompany(String company) {
        this.company = new Company(company);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Application} that we are building.
     */
    public ApplicationBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code HrEmail} of the {@code Application} that we are building.
     */
    public ApplicationBuilder withHrEmail(String hrEmail) {
        this.hrEmail = new HrEmail(hrEmail);
        return this;
    }

    public Application build() {
        return new Application(role, phone, hrEmail, company, tags);
    }

}
