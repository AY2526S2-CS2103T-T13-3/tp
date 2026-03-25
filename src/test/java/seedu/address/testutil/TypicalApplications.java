package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_LOCATION_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_LOCATION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HREMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HREMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.application.Application;
import seedu.address.model.application.Status;

/**
 * A utility class containing a list of {@code Application} objects to be used in tests.
 */
public class TypicalApplications {

    public static final Application ALICE = new ApplicationBuilder().withRole("Software Engineer")
            .withCompanyName("Google").withCompanyLocation("Singapore")
            .withHrEmail("alice@example.com").withPhone("94351253")
            .withTags("friends").build();
    public static final Application BENSON = new ApplicationBuilder().withRole("Data Analyst")
            .withCompanyName("Meta").withCompanyLocation("Menlo Park")
            .withHrEmail("johnd@example.com").withPhone("98765432")
            .withTags("owesMoney", "friends").build();
    public static final Application CARL = new ApplicationBuilder().withRole("Frontend Developer")
            .withPhone("95352563").withHrEmail("heinz@example.com")
            .withCompanyName("Shopee").withCompanyLocation("Singapore").build();
    public static final Application DANIEL = new ApplicationBuilder().withRole("Backend Engineer")
            .withPhone("87652533").withHrEmail("cornelia@example.com")
            .withCompanyName("TikTok").withCompanyLocation("Singapore")
            .withTags("friends").build();
    public static final Application ELLE = new ApplicationBuilder().withRole("Product Manager")
            .withPhone("9482224").withHrEmail("werner@example.com")
            .withCompanyName("Grab").withCompanyLocation("Singapore").build();
    public static final Application FIONA = new ApplicationBuilder().withCompanyName("Meta")
            .withRole("Data Analyst").withPhone("9482427")
            .withHrEmail("lydia@example.com")
            .withCompanyLocation("") // empty location
            .withDeadline("")        // empty deadline
            .withNote("")            // empty note
            .withStatus(Status.REJECTED).build();
    public static final Application GEORGE = new ApplicationBuilder().withRole("DevOps Engineer")
            .withPhone("9482442").withHrEmail("anna@example.com")
            .withCompanyName("Microsoft").withCompanyLocation("Redmond").build();

    // Manually added
    public static final Application HOON = new ApplicationBuilder().withRole("UX Designer").withPhone("8482424")
            .withHrEmail("stefan@example.com").withCompanyName("Apple").withCompanyLocation("Cupertino").build();
    public static final Application IDA = new ApplicationBuilder().withRole("Security Engineer").withPhone("8482131")
            .withHrEmail("hans@example.com").withCompanyName("Cloudflare").withCompanyLocation("San Francisco").build();

    // Manually added - Application's details found in {@code CommandTestUtil}
    public static final Application AMY = new ApplicationBuilder().withRole(VALID_ROLE_AMY)
            .withPhone(VALID_PHONE_AMY).withHrEmail(VALID_HREMAIL_AMY)
            .withCompanyName(VALID_COMPANY_NAME_AMY).withCompanyLocation(VALID_COMPANY_LOCATION_AMY)
            .withTags(VALID_TAG_FRIEND).build();
    public static final Application BOB = new ApplicationBuilder().withRole(VALID_ROLE_BOB)
            .withPhone(VALID_PHONE_BOB).withHrEmail(VALID_HREMAIL_BOB)
            .withCompanyName(VALID_COMPANY_NAME_BOB).withCompanyLocation(VALID_COMPANY_LOCATION_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalApplications() {
    } // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical applications.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Application application : getTypicalApplications()) {
            ab.addApplication(application);
        }
        return ab;
    }

    public static List<Application> getTypicalApplications() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
