package seedu.company.testutil;

import static seedu.company.logic.commands.CommandTestUtil.VALID_COMPANY_AMY;
import static seedu.company.logic.commands.CommandTestUtil.VALID_COMPANY_BOB;
import static seedu.company.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.company.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.company.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.company.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.company.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.company.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.company.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.company.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.company.model.CompanyBook;
import seedu.company.model.application.Application;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Application ALICE = new PersonBuilder().withName("Alice Pauline")
            .withCompany("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("94351253")
            .withTags("friends").build();
    public static final Application BENSON = new PersonBuilder().withName("Benson Meier")
            .withCompany("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withTags("owesMoney", "friends").build();
    public static final Application CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withCompany("wall street").build();
    public static final Application DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withCompany("10th street").withTags("friends").build();
    public static final Application ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withCompany("michegan ave").build();
    public static final Application FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withCompany("little tokyo").build();
    public static final Application GEORGE = new PersonBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withCompany("4th street").build();

    // Manually added
    public static final Application HOON = new PersonBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withCompany("little india").build();
    public static final Application IDA = new PersonBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withCompany("chicago ave").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Application AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withCompany(VALID_COMPANY_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Application BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withCompany(VALID_COMPANY_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code CompanyBook} with all the typical persons.
     */
    public static CompanyBook getTypicalCompanyBook() {
        CompanyBook ab = new CompanyBook();
        for (Application application : getTypicalPersons()) {
            ab.addPerson(application);
        }
        return ab;
    }

    public static List<Application> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
