package seedu.address.model.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HREMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalApplications.ALICE;
import static seedu.address.testutil.TypicalApplications.BOB;

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
        assertTrue(ALICE.isSameApplication(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameApplication(null));

        // same role and company, all other attributes different -> returns true
        Application editedAlice = new ApplicationBuilder(ALICE)
                .withPhone(VALID_PHONE_BOB)
                .withHrEmail(VALID_HREMAIL_BOB)
                .withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(ALICE.isSameApplication(editedAlice));

        // different role, all other attributes same -> returns false
        editedAlice = new ApplicationBuilder(ALICE).withRole(VALID_ROLE_BOB).build();
        assertFalse(ALICE.isSameApplication(editedAlice));

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
        Application aliceCopy = new ApplicationBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different application -> returns false
        assertFalse(ALICE.equals(BOB));

        // different role -> returns false
        Application editedAlice = new ApplicationBuilder(ALICE).withRole(VALID_ROLE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new ApplicationBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different hrEmail -> returns false
        editedAlice = new ApplicationBuilder(ALICE).withHrEmail(VALID_HREMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different company -> returns false
        editedAlice = new ApplicationBuilder(ALICE).withCompany("Some Other Company").build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new ApplicationBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void constructor_withoutStatus_defaultsToApplied() {
        Application applicationWithDefaultStatus = new Application(
                ALICE.getRole(),
                ALICE.getPhone(),
                ALICE.getHrEmail(),
                ALICE.getCompany(),
                ALICE.getTags());

        assertEquals(Status.APPLIED, applicationWithDefaultStatus.getStatus());
    }

    @Test
    public void toStringMethod() {
        String expected = Application.class.getCanonicalName()
                + "{role=" + ALICE.getRole()
                + ", phone=" + ALICE.getPhone()
                + ", hrEmail=" + ALICE.getHrEmail()
                + ", company=" + ALICE.getCompany()
                + ", tags=" + ALICE.getTags()
                + ", status=" + ALICE.getStatus()
                + ", deadline=" + ALICE.getDeadline() + "}";
        assertEquals(expected, ALICE.toString());
    }

    @Test
    public void hashCode_sameValues_sameHash() {
        Application copyOfAlice = new ApplicationBuilder(ALICE).build();
        assertEquals(ALICE.hashCode(), copyOfAlice.hashCode());
    }
}
