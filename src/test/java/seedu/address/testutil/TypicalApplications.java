package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_BOB;
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

/**
 * A utility class containing a list of {@code Application} objects to be used in tests.
 */
public class TypicalApplications {

    public static final Application ALICE = new ApplicationBuilder().withRole("Alice Pauline")
            .withCompany("123, Jurong West Ave 6, #08-111").withHrEmail("alice@example.com")
            .withPhone("94351253")
            .withTags("friends").build();
    public static final Application BENSON = new ApplicationBuilder().withRole("Benson Meier")
            .withCompany("311, Clementi Ave 2, #02-25")
            .withHrEmail("johnd@example.com").withPhone("98765432")
            .withTags("owesMoney", "friends").build();
    public static final Application CARL = new ApplicationBuilder().withRole("Carl Kurz").withPhone("95352563")
            .withHrEmail("heinz@example.com").withCompany("wall street").build();
    public static final Application DANIEL = new ApplicationBuilder().withRole("Daniel Meier").withPhone("87652533")
            .withHrEmail("cornelia@example.com").withCompany("10th street").withTags("friends").build();
    public static final Application ELLE = new ApplicationBuilder().withRole("Elle Meyer").withPhone("9482224")
            .withHrEmail("werner@example.com").withCompany("michegan ave").build();
    public static final Application FIONA = new ApplicationBuilder().withRole("Fiona Kunz").withPhone("9482427")
            .withHrEmail("lydia@example.com").withCompany("little tokyo").build();
    public static final Application GEORGE = new ApplicationBuilder().withRole("George Best").withPhone("9482442")
            .withHrEmail("anna@example.com").withCompany("4th street").build();

    // Manually added
    public static final Application HOON = new ApplicationBuilder().withRole("Hoon Meier").withPhone("8482424")
            .withHrEmail("stefan@example.com").withCompany("little india").build();
    public static final Application IDA = new ApplicationBuilder().withRole("Ida Mueller").withPhone("8482131")
            .withHrEmail("hans@example.com").withCompany("chicago ave").build();

    // Manually added - Application's details found in {@code CommandTestUtil}
    public static final Application AMY = new ApplicationBuilder().withRole(VALID_ROLE_AMY).withPhone(VALID_PHONE_AMY)
            .withHrEmail(VALID_HREMAIL_AMY).withCompany(VALID_COMPANY_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Application BOB = new ApplicationBuilder().withRole(VALID_ROLE_BOB).withPhone(VALID_PHONE_BOB)
            .withHrEmail(VALID_HREMAIL_BOB).withCompany(VALID_COMPANY_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

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
