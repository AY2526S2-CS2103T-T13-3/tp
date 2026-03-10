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
    public void constructor_invalidEmail_throwsIllegalArgumentException() {
        String invalidEmail = "";
        assertThrows(IllegalArgumentException.class, () -> new HrEmail(invalidEmail));
    }

    @Test
    public void isValidEmail() {
        // null email
        assertThrows(NullPointerException.class, () -> HrEmail.isValidEmail(null));

        // blank email
        assertFalse(HrEmail.isValidEmail("")); // empty string
        assertFalse(HrEmail.isValidEmail(" ")); // spaces only

        // missing parts
        assertFalse(HrEmail.isValidEmail("@example.com")); // missing local part
        assertFalse(HrEmail.isValidEmail("peterjackexample.com")); // missing '@' symbol
        assertFalse(HrEmail.isValidEmail("peterjack@")); // missing domain name

        // invalid parts
        assertFalse(HrEmail.isValidEmail("peterjack@-")); // invalid domain name
        assertFalse(HrEmail.isValidEmail("peterjack@exam_ple.com")); // underscore in domain name
        assertFalse(HrEmail.isValidEmail("peter jack@example.com")); // spaces in local part
        assertFalse(HrEmail.isValidEmail("peterjack@exam ple.com")); // spaces in domain name
        assertFalse(HrEmail.isValidEmail(" peterjack@example.com")); // leading space
        assertFalse(HrEmail.isValidEmail("peterjack@example.com ")); // trailing space
        assertFalse(HrEmail.isValidEmail("peterjack@@example.com")); // double '@' symbol
        assertFalse(HrEmail.isValidEmail("peter@jack@example.com")); // '@' symbol in local part
        assertFalse(HrEmail.isValidEmail("-peterjack@example.com")); // local part starts with a hyphen
        assertFalse(HrEmail.isValidEmail("peterjack-@example.com")); // local part ends with a hyphen
        assertFalse(HrEmail.isValidEmail("peter..jack@example.com")); // local part has two consecutive periods
        assertFalse(HrEmail.isValidEmail("peterjack@example@com")); // '@' symbol in domain name
        assertFalse(HrEmail.isValidEmail("peterjack@.example.com")); // domain name starts with a period
        assertFalse(HrEmail.isValidEmail("peterjack@example.com.")); // domain name ends with a period
        assertFalse(HrEmail.isValidEmail("peterjack@-example.com")); // domain name starts with a hyphen
        assertFalse(HrEmail.isValidEmail("peterjack@example.com-")); // domain name ends with a hyphen
        assertFalse(HrEmail.isValidEmail("peterjack@example.c")); // top level domain has less than two chars

        // valid email
        assertTrue(HrEmail.isValidEmail("PeterJack_1190@example.com")); // underscore in local part
        assertTrue(HrEmail.isValidEmail("PeterJack.1190@example.com")); // period in local part
        assertTrue(HrEmail.isValidEmail("PeterJack+1190@example.com")); // '+' symbol in local part
        assertTrue(HrEmail.isValidEmail("PeterJack-1190@example.com")); // hyphen in local part
        assertTrue(HrEmail.isValidEmail("a@bc")); // minimal
        assertTrue(HrEmail.isValidEmail("test@localhost")); // alphabets only
        assertTrue(HrEmail.isValidEmail("123@145")); // numeric local part and domain name
        assertTrue(HrEmail.isValidEmail("a1+be.d@example1.com")); // mixture of alphanumeric and special characters
        assertTrue(HrEmail.isValidEmail("peter_jack@very-very-very-long-example.com")); // long domain name
        assertTrue(HrEmail.isValidEmail("if.you.dream.it_you.can.do.it@example.com")); // long local part
        assertTrue(HrEmail.isValidEmail("e1234567@u.nus.edu")); // more than one period in domain
    }

    @Test
    public void equals() {
        HrEmail hrEmail = new HrEmail("valid@email");

        // same values -> returns true
        assertTrue(hrEmail.equals(new HrEmail("valid@email")));

        // same object -> returns true
        assertTrue(hrEmail.equals(hrEmail));

        // null -> returns false
        assertFalse(hrEmail.equals(null));

        // different types -> returns false
        assertFalse(hrEmail.equals(5.0f));

        // different values -> returns false
        assertFalse(hrEmail.equals(new HrEmail("other.valid@email")));
    }
}
