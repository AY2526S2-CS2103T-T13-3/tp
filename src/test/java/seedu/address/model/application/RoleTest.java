package seedu.address.model.application;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class RoleTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Role(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new Role(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> seedu.address.model.application.Role.isValidName(null));

        // invalid name
        assertFalse(seedu.address.model.application.Role.isValidName("")); // empty string
        assertFalse(seedu.address.model.application.Role.isValidName(" ")); // spaces only
        assertFalse(seedu.address.model.application.Role.isValidName("^")); // only non-alphanumeric characters
        assertFalse(seedu.address.model.application.Role.isValidName("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(seedu.address.model.application.Role.isValidName("peter jack")); // alphabets only
        assertTrue(seedu.address.model.application.Role.isValidName("12345")); // numbers only
        assertTrue(seedu.address.model.application.Role.isValidName("peter the 2nd")); // alphanumeric characters
        assertTrue(seedu.address.model.application.Role.isValidName("Capital Tan")); // with capital letters
        assertTrue(seedu.address.model.application.Role.isValidName("David Roger Jackson Ray Jr 2nd")); // long names
    }

    @Test
    public void equals() {
        Role role = new Role("Valid Name");

        // same values -> returns true
        assertTrue(role.equals(new Role("Valid Name")));

        // same object -> returns true
        assertTrue(role.equals(role));

        // null -> returns false
        assertFalse(role.equals(null));

        // different types -> returns false
        assertFalse(role.equals(5.0f));

        // different values -> returns false
        assertFalse(role.equals(new Role("Other Valid Name")));
    }
}
