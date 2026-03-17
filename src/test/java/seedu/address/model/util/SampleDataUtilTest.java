package seedu.address.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.application.Application;
import seedu.address.model.application.Status;

/**
 * Tests for {@link SampleDataUtil}.
 */
public class SampleDataUtilTest {

    @Test
    public void getSampleApplications_returnsNonEmptyArrayWithStatuses() {
        Application[] applications = SampleDataUtil.getSampleApplications();

        assertNotNull(applications);
        assertTrue(applications.length > 0);

        for (Application application : applications) {
            assertNotNull(application.getRole());
            assertNotNull(application.getCompany());
            assertNotNull(application.getStatus());
            assertFalse(application.getTags().isEmpty());
        }

        assertEquals(Status.APPLIED, applications[0].getStatus());
    }

    @Test
    public void getSampleAddressBook_containsAllSampleApplications() {
        Application[] applications = SampleDataUtil.getSampleApplications();
        ReadOnlyAddressBook addressBook = SampleDataUtil.getSampleAddressBook();

        assertEquals(applications.length, addressBook.getApplicationList().size());
    }
}

