package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedApplication.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalApplications.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.application.Company;
import seedu.address.model.application.HrEmail;
import seedu.address.model.application.Phone;
import seedu.address.model.application.Role;

public class JsonAdaptedApplicationTest {
    private static final String INVALID_ROLE = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_COMPANY = " ";
    private static final String INVALID_HREMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_ROLE = BENSON.getRole().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_HREMAIL = BENSON.getHrEmail().toString();
    private static final String VALID_COMPANY_NAME = BENSON.getCompany().companyName;
    private static final String VALID_COMPANY_LOCATION = BENSON.getCompany().companyLocation;
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());
    private static final String VALID_STATUS = BENSON.getStatus().toString();
    private static final String VALID_DEADLINE = "2026-12-31 23:59";
    private static final String VALID_NOTE = "";

    @Test
    public void toModelType_validApplicationDetails_returnsApplication() throws Exception {
        JsonAdaptedApplication application = new JsonAdaptedApplication(BENSON);
        assertEquals(BENSON, application.toModelType());
    }

    @Test
    public void toModelType_invalidRole_throwsIllegalValueException() {
        JsonAdaptedApplication application =
                new JsonAdaptedApplication(INVALID_ROLE, VALID_PHONE, VALID_HREMAIL, VALID_COMPANY_NAME,
                        VALID_COMPANY_LOCATION, VALID_TAGS, VALID_STATUS, VALID_DEADLINE, VALID_NOTE);
        String expectedMessage = Role.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    @Test
    public void toModelType_nullRole_throwsIllegalValueException() {
        JsonAdaptedApplication application = new JsonAdaptedApplication(null, VALID_PHONE, VALID_HREMAIL,
                VALID_COMPANY_NAME, VALID_COMPANY_LOCATION, VALID_TAGS, VALID_STATUS, VALID_DEADLINE, VALID_NOTE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Role.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedApplication application =
                new JsonAdaptedApplication(VALID_ROLE, INVALID_PHONE, VALID_HREMAIL, VALID_COMPANY_NAME,
                        VALID_COMPANY_LOCATION, VALID_TAGS, VALID_STATUS, VALID_DEADLINE, VALID_NOTE);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedApplication application = new JsonAdaptedApplication(VALID_ROLE, null, VALID_HREMAIL,
                VALID_COMPANY_NAME, VALID_COMPANY_LOCATION, VALID_TAGS, VALID_STATUS, VALID_DEADLINE, VALID_NOTE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    @Test
    public void toModelType_invalidHrEmail_throwsIllegalValueException() {
        JsonAdaptedApplication application =
                new JsonAdaptedApplication(VALID_ROLE, VALID_PHONE, INVALID_HREMAIL, VALID_COMPANY_NAME,
                        VALID_COMPANY_LOCATION, VALID_TAGS, VALID_STATUS, VALID_DEADLINE, VALID_NOTE);
        String expectedMessage = HrEmail.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    @Test
    public void toModelType_nullHrEmail_throwsIllegalValueException() {
        JsonAdaptedApplication application = new JsonAdaptedApplication(VALID_ROLE, VALID_PHONE, null,
                VALID_COMPANY_NAME, VALID_COMPANY_LOCATION, VALID_TAGS, VALID_STATUS, VALID_DEADLINE, VALID_NOTE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, HrEmail.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    @Test
    public void toModelType_invalidCompanyName_throwsIllegalValueException() {
        JsonAdaptedApplication application =
                new JsonAdaptedApplication(VALID_ROLE, VALID_PHONE, VALID_HREMAIL, INVALID_COMPANY,
                        VALID_COMPANY_LOCATION, VALID_TAGS, VALID_STATUS, VALID_DEADLINE, VALID_NOTE);
        String expectedMessage = Company.MESSAGE_CONSTRAINTS_NAME;
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    @Test
    public void toModelType_nullCompanyName_throwsIllegalValueException() {
        JsonAdaptedApplication application = new JsonAdaptedApplication(VALID_ROLE, VALID_PHONE, VALID_HREMAIL, null,
                VALID_COMPANY_LOCATION, VALID_TAGS, VALID_STATUS, VALID_DEADLINE, VALID_NOTE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Company.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    @Test
    public void toModelType_nullCompanyLocation_throwsIllegalValueException() {
        JsonAdaptedApplication application = new JsonAdaptedApplication(VALID_ROLE, VALID_PHONE, VALID_HREMAIL,
                VALID_COMPANY_NAME, null, VALID_TAGS, VALID_STATUS, VALID_DEADLINE, VALID_NOTE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "companyLocation");
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    @Test
    public void toModelType_nullStatus_defaultsToApplied() throws Exception {
        JsonAdaptedApplication application =
                new JsonAdaptedApplication(VALID_ROLE, VALID_PHONE, VALID_HREMAIL, VALID_COMPANY_NAME,
                        VALID_COMPANY_LOCATION, VALID_TAGS, null, VALID_DEADLINE, VALID_NOTE);
        seedu.address.model.application.Application modelApplication = application.toModelType();
        assertEquals(seedu.address.model.application.Status.APPLIED, modelApplication.getStatus());
    }

    @Test
    public void toModelType_invalidStatus_throwsIllegalValueException() {
        String invalidStatus = "NOT_A_STATUS";
        JsonAdaptedApplication application =
                new JsonAdaptedApplication(VALID_ROLE, VALID_PHONE, VALID_HREMAIL, VALID_COMPANY_NAME,
                        VALID_COMPANY_LOCATION, VALID_TAGS, invalidStatus, VALID_DEADLINE, VALID_NOTE);
        String expectedMessage = "Invalid status: " + invalidStatus;
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedApplication application =
                new JsonAdaptedApplication(VALID_ROLE, VALID_PHONE, VALID_HREMAIL, VALID_COMPANY_NAME,
                        VALID_COMPANY_LOCATION, invalidTags, VALID_STATUS, VALID_DEADLINE, VALID_NOTE);
        assertThrows(IllegalValueException.class, application::toModelType);
    }

}
