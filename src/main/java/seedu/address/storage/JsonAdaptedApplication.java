package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.application.Application;
import seedu.address.model.application.Company;
import seedu.address.model.application.HrEmail;
import seedu.address.model.application.Phone;
import seedu.address.model.application.Role;
import seedu.address.model.application.Status;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Application}.
 */
class JsonAdaptedApplication {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Application's %s field is missing!";

    private final String role;
    private final String phone;
    private final String hrEmail;
    private final String company;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();
    @JsonProperty("status")
    private final String status;

    /**
     * Constructs a {@code JsonAdaptedApplication} with the given application details.
     */
    @JsonCreator
    public JsonAdaptedApplication(@JsonProperty("role") String role, @JsonProperty("phone") String phone,
                                  @JsonProperty("hrEmail") String hrEmail, @JsonProperty("company") String company,
                                  @JsonProperty("tags") List<JsonAdaptedTag> tags,
                                  @JsonProperty("status") String status) {
        this.role = role;
        this.phone = phone;
        this.hrEmail = hrEmail;
        this.company = company;
        if (tags != null) {
            this.tags.addAll(tags);
        }
        this.status = status;
    }

    /**
     * Converts a given {@code Application} into this class for Jackson use.
     */
    public JsonAdaptedApplication(Application source) {
        role = source.getRole().roleName;
        phone = source.getPhone().value;
        hrEmail = source.getHrEmail().value;
        company = source.getCompany().companyName;
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        this.status = source.getStatus().name();
    }

    /**
     * Converts this Jackson-friendly adapted application object into the model's {@code Application} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted application.
     */
    public Application toModelType() throws IllegalValueException {
        final List<Tag> applicationTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            applicationTags.add(tag.toModelType());
        }

        if (role == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Role.class.getSimpleName()));
        }
        if (!seedu.address.model.application.Role.isValidRole(role)) {
            throw new IllegalValueException(seedu.address.model.application.Role.MESSAGE_CONSTRAINTS);
        }
        final Role modelRole = new Role(role);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (hrEmail == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, HrEmail.class.getSimpleName()));
        }
        if (!HrEmail.isValidHrEmail(hrEmail)) {
            throw new IllegalValueException(HrEmail.MESSAGE_CONSTRAINTS);
        }
        final HrEmail modelHrEmail = new HrEmail(hrEmail);

        if (company == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Company.class.getSimpleName()));
        }
        if (!Company.isValidCompanyName(company)) {
            throw new IllegalValueException(Company.MESSAGE_CONSTRAINTS);
        }
        final Company modelCompany = new Company(company);

        final Set<Tag> modelTags = new HashSet<>(applicationTags);

        Status modelStatus;
        try {
            modelStatus = Status.valueOf(status != null ? status : "APPLIED");
        } catch (IllegalArgumentException e) {
            throw new IllegalValueException("Invalid status: " + status);
        }

        return new Application(modelRole, modelPhone, modelHrEmail, modelCompany, modelTags, modelStatus);
    }
}
