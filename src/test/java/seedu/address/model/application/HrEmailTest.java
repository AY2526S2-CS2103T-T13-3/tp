package seedu.address.model.application;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class HrEmailTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new HrEmail(null));
    }

    @Test
    public void constructor_invalidHrEmail_throwsIllegalArgumentException() {
        String invalidHrEmail = "";
        assertThrows(IllegalArgumentException.class, () -> new HrEmail(invalidHrEmail));
    }

    @Test
    public void isValidHrEmail() {
        // null HrEmail
        assertThrows(NullPointerException.class, () -> HrEmail.isValidHrEmail(null));

        // blank HrEmail
        assertFalse(HrEmail.isValidHrEmail("")); // empty string
        assertFalse(HrEmail.isValidHrEmail(" ")); // spaces only

        // missing parts
        assertFalse(HrEmail.isValidHrEmail("@example.com")); // missing local part
        assertFalse(HrEmail.isValidHrEmail("peterjackexample.com")); // missing '@' symbol
        assertFalse(HrEmail.isValidHrEmail("peterjack@")); // missing domain role

        // invalid parts
        assertFalse(HrEmail.isValidHrEmail("peterjack@-")); // invalid domain role
        assertFalse(HrEmail.isValidHrEmail("peterjack@exam_ple.com")); // underscore in domain role
        assertFalse(HrEmail.isValidHrEmail("peter jack@example.com")); // spaces in local part
        assertFalse(HrEmail.isValidHrEmail("peterjack@exam ple.com")); // spaces in domain role
        assertFalse(HrEmail.isValidHrEmail(" peterjack@example.com")); // leading space
        assertFalse(HrEmail.isValidHrEmail("peterjack@example.com ")); // trailing space
        assertFalse(HrEmail.isValidHrEmail("peterjack@@example.com")); // double '@' symbol
        assertFalse(HrEmail.isValidHrEmail("peter@jack@example.com")); // '@' symbol in local part
        assertFalse(HrEmail.isValidHrEmail("-peterjack@example.com")); // local part starts with a hyphen
        assertFalse(HrEmail.isValidHrEmail("peterjack-@example.com")); // local part ends with a hyphen
        assertFalse(HrEmail.isValidHrEmail("peter..jack@example.com")); // local part has two consecutive periods
        assertFalse(HrEmail.isValidHrEmail("peterjack@example@com")); // '@' symbol in domain role
        assertFalse(HrEmail.isValidHrEmail("peterjack@.example.com")); // domain role starts with a period
        assertFalse(HrEmail.isValidHrEmail("peterjack@example.com.")); // domain role ends with a period
        assertFalse(HrEmail.isValidHrEmail("peterjack@-example.com")); // domain role starts with a hyphen
        assertFalse(HrEmail.isValidHrEmail("peterjack@example.com-")); // domain role ends with a hyphen
        assertFalse(HrEmail.isValidHrEmail("peterjack@example.c")); // top level domain has less than two chars

        // valid HrEmail
        assertTrue(HrEmail.isValidHrEmail("PeterJack_1190@example.com")); // underscore in local part
        assertTrue(HrEmail.isValidHrEmail("PeterJack.1190@example.com")); // period in local part
        assertTrue(HrEmail.isValidHrEmail("PeterJack+1190@example.com")); // '+' symbol in local part
        assertTrue(HrEmail.isValidHrEmail("PeterJack-1190@example.com")); // hyphen in local part
        assertTrue(HrEmail.isValidHrEmail("a@bc")); // minimal
        assertTrue(HrEmail.isValidHrEmail("test@localhost")); // alphabets only
        assertTrue(HrEmail.isValidHrEmail("123@145")); // numeric local part and domain role
        assertTrue(HrEmail.isValidHrEmail("a1+be.d@example1.com")); // mixture of alphanumeric and special characters
        assertTrue(HrEmail.isValidHrEmail("peter_jack@very-very-very-long-example.com")); // long domain role
        assertTrue(HrEmail.isValidHrEmail("if.you.dream.it_you.can.do.it@example.com")); // long local part
        assertTrue(HrEmail.isValidHrEmail("e1234567@u.nus.edu")); // more than one period in domain
    }

    @Test
    public void equals() {
        HrEmail HrEmail = new HrEmail("valid@HrEmail");

        // same values -> returns true
        assertTrue(HrEmail.equals(new HrEmail("valid@HrEmail")));

        // same object -> returns true
        assertTrue(HrEmail.equals(HrEmail));

        // null -> returns false
        assertFalse(HrEmail.equals(null));

        // different types -> returns false
        assertFalse(HrEmail.equals(5.0f));

        // different values -> returns false
        assertFalse(HrEmail.equals(new HrEmail("other.valid@HrEmail")));
    }
}
