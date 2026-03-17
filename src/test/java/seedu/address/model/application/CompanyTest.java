package seedu.address.model.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class CompanyTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Company(null, ""));
        assertThrows(NullPointerException.class, () -> new Company("Valid Name", null));
    }

    @Test
    public void constructor_invalidCompany_throwsIllegalArgumentException() {
        String invalidCompany = "";
        assertThrows(IllegalArgumentException.class, () -> new Company(invalidCompany, ""));
    }

    @Test
    public void isValidCompany() {
        // null company
        assertThrows(NullPointerException.class, () -> Company.isValidCompanyName(null));

        // invalid companyes
        assertFalse(Company.isValidCompanyName("")); // empty string
        assertFalse(Company.isValidCompanyName(" ")); // spaces only

        // valid companyes
        assertTrue(Company.isValidCompanyName("Blk 456, Den Road, #01-355"));
        assertTrue(Company.isValidCompanyName("-")); // one character
        assertTrue(Company.isValidCompanyName("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA"));
        // long company
    }

    @Test
    public void isValidCompanyLocation() {
        assertTrue(Company.isValidCompanyLocation("")); // empty allowed
        assertTrue(Company.isValidCompanyLocation("Singapore"));
        assertTrue(Company.isValidCompanyLocation("12345, ABC Street"));
    }

    @Test
    public void toString_includesLocationWhenPresent() {
        Company withLocation = new Company("Valid Company", "Singapore");
        Company withoutLocation = new Company("Valid Company", "");

        assertEquals("Valid Company (Singapore)", withLocation.toString());
        assertEquals("Valid Company", withoutLocation.toString());
    }

    @Test
    public void equals() {
        Company company = new Company("Valid Company", "Singapore");

        // same values -> returns true
        assertTrue(company.equals(new Company("Valid Company", "Singapore")));

        // same object -> returns true
        assertTrue(company.equals(company));

        // different types -> returns false
        assertFalse(company.equals(5.0f));

        // different values -> returns false
        assertFalse(company.equals(new Company("Other Valid Company", "Singapore")));
        assertFalse(company.equals(new Company("Valid Company", "Other Location")));
    }

    @Test
    public void hashCode_sameValues_sameHash() {
        Company company1 = new Company("Valid Company", "Singapore");
        Company company2 = new Company("Valid Company", "Singapore");

        assertEquals(company1.hashCode(), company2.hashCode());
    }
}
