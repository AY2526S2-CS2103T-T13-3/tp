package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.application.Application;
import seedu.address.model.application.Deadline;
import seedu.address.model.application.HrEmail;
import seedu.address.model.application.Note;
import seedu.address.model.application.Phone;
import seedu.address.model.application.Role;
import seedu.address.model.application.Status;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Application objects.
 */
public class ApplicationBuilder {

    public static final String DEFAULT_ROLE = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_HREMAIL = "amy@gmail.com";
    public static final String DEFAULT_COMPANY_NAME = "Google";
    public static final String DEFAULT_COMPANY_LOCATION = "Singapore";
    public static final Status DEFAULT_STATUS = Status.APPLIED;
    public static final String DEFAULT_NOTE = "";

    private Role role;
    private Phone phone;
    private HrEmail hrEmail;
    private String companyName;
    private String companyLocation;
    private Set<Tag> tags;
    private Status status;
    private Deadline deadline;
    private Note note;

    /**
     * Creates a {@code ApplicationBuilder} with the default details.
     */
    public ApplicationBuilder() {
        role = new Role(DEFAULT_ROLE);
        phone = new Phone(DEFAULT_PHONE);
        hrEmail = new HrEmail(DEFAULT_HREMAIL);
        companyName = DEFAULT_COMPANY_NAME;
        companyLocation = DEFAULT_COMPANY_LOCATION;
        tags = new HashSet<>();
        status = DEFAULT_STATUS;
        deadline = Deadline.getEmptyDeadline();
        note = new Note(DEFAULT_NOTE);
    }

    /**
     * Initializes the ApplicationBuilder with the data of {@code applicationToCopy}.
     */
    public ApplicationBuilder(Application applicationToCopy) {
        role = applicationToCopy.getRole();
        phone = applicationToCopy.getPhone();
        hrEmail = applicationToCopy.getHrEmail();
        companyName = applicationToCopy.getCompany().companyName;
        companyLocation = applicationToCopy.getCompany().companyLocation;
        tags = new HashSet<>(applicationToCopy.getTags());
        status = applicationToCopy.getStatus();
        deadline = applicationToCopy.getDeadline();
        note = applicationToCopy.getNote();
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
     * Sets the {@code CompanyName} of the {@code Application} that we are building.
     */
    public ApplicationBuilder withCompanyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    /**
     * Sets the {@code CompanyLocation} of the {@code Application} that we are building.
     */
    public ApplicationBuilder withCompanyLocation(String companyLocation) {
        this.companyLocation = companyLocation;
        return this;
    }

    /**
     * Backward-compatible convenience method for older tests.
     * Sets company name and clears location.
     */
    public ApplicationBuilder withCompany(String companyName) {
        this.companyName = companyName;
        this.companyLocation = "";
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

    /**
     * Sets the {@code Status} of the {@code Application} that we are building.
     */
    public ApplicationBuilder withStatus(Status status) {
        this.status = status;
        return this;
    }

    /**
     * Sets the {@code Deadline} of the {@code Application} that we are building.
     */
    public ApplicationBuilder withDeadline(String deadline) {
        this.deadline = new Deadline(deadline);
        return this;
    }

    /**
     * Sets the {@code Note} of the {@code Application} that we are building.
     */
    public ApplicationBuilder withNote(String note) {
        this.note = new Note(note);
        return this;
    }

    /**
     * Builds an {@code Application} instance.
     */
    public Application build() {
        seedu.address.model.application.Company company =
                new seedu.address.model.application.Company(companyName, companyLocation);
        return new Application(role, phone, hrEmail, company, tags, status, deadline, note);
    }

}
