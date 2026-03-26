package seedu.address.model.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HREMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalApplications.BOB;
import static seedu.address.testutil.TypicalApplications.GOOGLE_SWE;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.ApplicationBuilder;

public class ApplicationTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Application application = new ApplicationBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> application.getTags().clear());
    }

    @Test
    public void isSameApplication() {
        // same object -> returns true
        assertTrue(GOOGLE_SWE.isSameApplication(GOOGLE_SWE));

        // null -> returns false
        assertFalse(GOOGLE_SWE.isSameApplication(null));

        // same role and company, all other attributes different -> returns true
        Application editedAlice = new ApplicationBuilder(GOOGLE_SWE)
                .withPhone(VALID_PHONE_BOB)
                .withHrEmail(VALID_HREMAIL_BOB)
                .withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(GOOGLE_SWE.isSameApplication(editedAlice));

        // different role, all other attributes same -> returns false
        editedAlice = new ApplicationBuilder(GOOGLE_SWE).withRole(VALID_ROLE_BOB).build();
        assertFalse(GOOGLE_SWE.isSameApplication(editedAlice));

        // role differs in case, all other attributes same -> returns false
        Application editedBob = new ApplicationBuilder(BOB).withRole(VALID_ROLE_BOB.toLowerCase()).build();
        assertFalse(BOB.isSameApplication(editedBob));

        // role has trailing spaces, all other attributes same -> returns false
        String roleWithTrailingSpaces = VALID_ROLE_BOB + " ";
        editedBob = new ApplicationBuilder(BOB).withRole(roleWithTrailingSpaces).build();
        assertFalse(BOB.isSameApplication(editedBob));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Application aliceCopy = new ApplicationBuilder(GOOGLE_SWE).build();
        assertTrue(GOOGLE_SWE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(GOOGLE_SWE.equals(GOOGLE_SWE));

        // different type -> returns false
        assertFalse(GOOGLE_SWE.equals(5));

        // different application -> returns false
        assertFalse(GOOGLE_SWE.equals(BOB));

        // different role -> returns false
        Application editedAlice = new ApplicationBuilder(GOOGLE_SWE).withRole(VALID_ROLE_BOB).build();
        assertFalse(GOOGLE_SWE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new ApplicationBuilder(GOOGLE_SWE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(GOOGLE_SWE.equals(editedAlice));

        // different hrEmail -> returns false
        editedAlice = new ApplicationBuilder(GOOGLE_SWE).withHrEmail(VALID_HREMAIL_BOB).build();
        assertFalse(GOOGLE_SWE.equals(editedAlice));

        // different company -> returns false
        editedAlice = new ApplicationBuilder(GOOGLE_SWE).withCompany("Some Other Company").build();
        assertFalse(GOOGLE_SWE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new ApplicationBuilder(GOOGLE_SWE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(GOOGLE_SWE.equals(editedAlice));
    }

    @Test
    public void constructor_withoutStatus_defaultsToApplied() {
        Application applicationWithDefaultStatus = new Application(
                GOOGLE_SWE.getRole(),
                GOOGLE_SWE.getPhone(),
                GOOGLE_SWE.getHrEmail(),
                GOOGLE_SWE.getCompany(),
                GOOGLE_SWE.getTags());

        assertEquals(Status.APPLIED, applicationWithDefaultStatus.getStatus());
        assertEquals(Resume.getEmptyResume(), applicationWithDefaultStatus.getResume());
    }

    @Test
    public void toStringMethod() {
        String expected = Application.class.getCanonicalName()
                + "{role=" + GOOGLE_SWE.getRole()
                + ", phone=" + GOOGLE_SWE.getPhone()
                + ", hrEmail=" + GOOGLE_SWE.getHrEmail()
                + ", company=" + GOOGLE_SWE.getCompany()
                + ", tags=" + GOOGLE_SWE.getTags()
                + ", status=" + GOOGLE_SWE.getStatus()
                + ", deadline=" + GOOGLE_SWE.getDeadline()
                + ", applicationEvent=" + GOOGLE_SWE.getApplicationEvent()
                + ", note=" + GOOGLE_SWE.getNote()
                + ", resume=" + GOOGLE_SWE.getResume() + "}";
        assertEquals(expected, GOOGLE_SWE.toString());
    }

    @Test
    public void hashCode_sameValues_sameHash() {
        Application copyOfAlice = new ApplicationBuilder(GOOGLE_SWE).build();
        assertEquals(GOOGLE_SWE.hashCode(), copyOfAlice.hashCode());
    }
}
