package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedApplication.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalApplications.META_DA;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.application.Company;
import seedu.address.model.application.HrEmail;
import seedu.address.model.application.Phone;
import seedu.address.model.application.Role;
import seedu.address.model.application.Status;

public class JsonAdaptedApplicationTest {
    private static final String INVALID_ROLE = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_COMPANY = " ";
    private static final String INVALID_HREMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_DATETIME = "31-12-2026 23:59";

    private static final String VALID_ROLE = META_DA.getRole().toString();
    private static final String VALID_PHONE = META_DA.getPhone().toString();
    private static final String VALID_HREMAIL = META_DA.getHrEmail().toString();
    private static final String VALID_COMPANY_NAME = META_DA.getCompany().companyName;
    private static final String VALID_COMPANY_LOCATION = META_DA.getCompany().companyLocation;
    private static final List<JsonAdaptedTag> VALID_TAGS = META_DA.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());
    private static final String VALID_STATUS = META_DA.getStatus().toString();
    private static final String VALID_DEADLINE = "2026-12-31 23:59";
    private static final String VALID_DATETIME = "2026-12-31 23:59";
    private static final String VALID_NOTE = "";
    private static final String VALID_RESUME = "";

    @Test
    public void toModelType_validApplicationDetails_returnsApplication() throws Exception {
        JsonAdaptedApplication application = new JsonAdaptedApplication(META_DA);
        assertEquals(META_DA, application.toModelType());
    }

    @Test
    public void toModelType_validApplicationWithAssessment_returnsApplication() throws Exception {
        JsonAdaptedApplication application = new JsonAdaptedApplication(
                VALID_ROLE, VALID_PHONE, VALID_HREMAIL, VALID_COMPANY_NAME,
                VALID_COMPANY_LOCATION, VALID_TAGS, VALID_STATUS, VALID_DEADLINE,
                "home", VALID_DATETIME, "HackerRank",
                "www.hackerrank.com", null, VALID_NOTE, VALID_RESUME);
        seedu.address.model.application.Application result = application.toModelType();
        assertEquals("home", result.getApplicationEvent().getLocation());
    }

    @Test
    public void toModelType_validApplicationWithAssessmentAndNotes_returnsApplication() throws Exception {
        JsonAdaptedApplication application = new JsonAdaptedApplication(
                VALID_ROLE, VALID_PHONE, VALID_HREMAIL, VALID_COMPANY_NAME,
                VALID_COMPANY_LOCATION, VALID_TAGS, VALID_STATUS, VALID_DEADLINE,
                "home", VALID_DATETIME, "HackerRank", "www.hackerrank.com",
                null, "some notes", VALID_RESUME);
        seedu.address.model.application.Application result = application.toModelType();
        assertEquals("home", result.getApplicationEvent().getLocation());
    }

    @Test
    public void toModelType_invalidEventDateTime_throwsIllegalValueException() {
        JsonAdaptedApplication application = new JsonAdaptedApplication(
                VALID_ROLE, VALID_PHONE, VALID_HREMAIL, VALID_COMPANY_NAME,
                VALID_COMPANY_LOCATION, VALID_TAGS, VALID_STATUS, VALID_DEADLINE,
                "home", INVALID_DATETIME, "HackerRank",
                "www.hackerrank.com", null, VALID_NOTE, VALID_RESUME);
        assertThrows(IllegalValueException.class, application::toModelType);
    }

    @Test
    public void toModelType_invalidRole_throwsIllegalValueException() {
        JsonAdaptedApplication application =
                new JsonAdaptedApplication(INVALID_ROLE, VALID_PHONE, VALID_HREMAIL, VALID_COMPANY_NAME,
                        VALID_COMPANY_LOCATION, VALID_TAGS, VALID_STATUS, VALID_DEADLINE,
                        null, null, null,
                        null, null, VALID_NOTE, VALID_RESUME);
        String expectedMessage = Role.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    @Test
    public void toModelType_nullRole_throwsIllegalValueException() {
        JsonAdaptedApplication application = new JsonAdaptedApplication(
                null, VALID_PHONE, VALID_HREMAIL, VALID_COMPANY_NAME,
                VALID_COMPANY_LOCATION, VALID_TAGS, VALID_STATUS, VALID_DEADLINE,
                null, null, null, null, null, VALID_NOTE, VALID_RESUME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Role.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedApplication application =
                new JsonAdaptedApplication(VALID_ROLE, INVALID_PHONE, VALID_HREMAIL, VALID_COMPANY_NAME,
                        VALID_COMPANY_LOCATION, VALID_TAGS, VALID_STATUS, VALID_DEADLINE,
                        null, null, null, null, null, VALID_NOTE, VALID_RESUME);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedApplication application = new JsonAdaptedApplication(
                VALID_ROLE, null, VALID_HREMAIL, VALID_COMPANY_NAME,
                VALID_COMPANY_LOCATION, VALID_TAGS, VALID_STATUS, VALID_DEADLINE,
                null, null, null, null, null, VALID_NOTE, VALID_RESUME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    @Test
    public void toModelType_invalidHrEmail_throwsIllegalValueException() {
        JsonAdaptedApplication application =
                new JsonAdaptedApplication(VALID_ROLE, VALID_PHONE, INVALID_HREMAIL, VALID_COMPANY_NAME,
                        VALID_COMPANY_LOCATION, VALID_TAGS, VALID_STATUS, VALID_DEADLINE,
                        null, null, null, null, null, VALID_NOTE, VALID_RESUME);
        String expectedMessage = HrEmail.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    @Test
    public void toModelType_nullHrEmail_throwsIllegalValueException() {
        JsonAdaptedApplication application = new JsonAdaptedApplication(
                VALID_ROLE, VALID_PHONE, null, VALID_COMPANY_NAME,
                VALID_COMPANY_LOCATION, VALID_TAGS, VALID_STATUS, VALID_DEADLINE,
                null, null, null, null, null, VALID_NOTE, VALID_RESUME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, HrEmail.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    @Test
    public void toModelType_invalidCompanyName_throwsIllegalValueException() {
        JsonAdaptedApplication application =
                new JsonAdaptedApplication(VALID_ROLE, VALID_PHONE, VALID_HREMAIL, INVALID_COMPANY,
                        VALID_COMPANY_LOCATION, VALID_TAGS, VALID_STATUS, VALID_DEADLINE,
                        null, null, null, null, null, VALID_NOTE, VALID_RESUME);
        String expectedMessage = Company.MESSAGE_CONSTRAINTS_NAME;
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    @Test
    public void toModelType_nullCompanyName_throwsIllegalValueException() {
        JsonAdaptedApplication application = new JsonAdaptedApplication(
                VALID_ROLE, VALID_PHONE, VALID_HREMAIL, null,
                VALID_COMPANY_LOCATION, VALID_TAGS, VALID_STATUS, VALID_DEADLINE,
                null, null, null, null, null, VALID_NOTE, VALID_RESUME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Company.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    @Test
    public void toModelType_nullCompanyLocation_throwsIllegalValueException() {
        JsonAdaptedApplication application = new JsonAdaptedApplication(
                VALID_ROLE, VALID_PHONE, VALID_HREMAIL, VALID_COMPANY_NAME,
                null, VALID_TAGS, VALID_STATUS, VALID_DEADLINE,
                null, null, null, null, null, VALID_NOTE, VALID_RESUME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "companyLocation");
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    @Test
    public void toModelType_nullStatus_defaultsToApplied() throws Exception {
        JsonAdaptedApplication application = new JsonAdaptedApplication(
                VALID_ROLE, VALID_PHONE, VALID_HREMAIL, VALID_COMPANY_NAME,
                VALID_COMPANY_LOCATION, VALID_TAGS, null, VALID_DEADLINE,
                null, null, null, null, null, VALID_NOTE, VALID_RESUME);
        seedu.address.model.application.Application modelApplication = application.toModelType();
        assertEquals(Status.APPLIED, modelApplication.getStatus());
    }

    @Test
    public void toModelType_invalidStatus_throwsIllegalValueException() {
        String invalidStatus = "NOT_A_STATUS";
        JsonAdaptedApplication application = new JsonAdaptedApplication(
                VALID_ROLE, VALID_PHONE, VALID_HREMAIL, VALID_COMPANY_NAME,
                VALID_COMPANY_LOCATION, VALID_TAGS, invalidStatus, VALID_DEADLINE,
                null, null, null, null, null, VALID_NOTE, VALID_RESUME);
        String expectedMessage = "Invalid status: " + invalidStatus;
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedApplication application = new JsonAdaptedApplication(
                VALID_ROLE, VALID_PHONE, VALID_HREMAIL, VALID_COMPANY_NAME,
                VALID_COMPANY_LOCATION, invalidTags, VALID_STATUS, VALID_DEADLINE,
                null, null, null, null, null, VALID_NOTE, VALID_RESUME);
        assertThrows(IllegalValueException.class, application::toModelType);
    }
}
