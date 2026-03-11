package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalApplications.ALICE;
import static seedu.address.testutil.TypicalApplications.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.application.Application;
import seedu.address.model.application.exceptions.DuplicateApplicationException;
import seedu.address.testutil.ApplicationBuilder;

public class AddressBookTest {

    private final AddressBook companyBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), companyBook.getApplicationList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> companyBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        companyBook.resetData(newData);
        assertEquals(newData, companyBook);
    }

    @Test
    public void resetData_withDuplicateApplications_throwsDuplicateApplicationException() {
        // Two applications with the same identity fields
        Application editedAlice = new ApplicationBuilder(ALICE).withCompany(VALID_COMPANY_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Application> newApplications = Arrays.asList(ALICE, editedAlice);
        AddressBookStub newData = new AddressBookStub(newApplications);

        assertThrows(DuplicateApplicationException.class, () -> companyBook.resetData(newData));
    }

    @Test
    public void hasApplication_nullApplication_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> companyBook.hasApplication(null));
    }

    @Test
    public void hasApplication_applicationNotInAddressBook_returnsFalse() {
        assertFalse(companyBook.hasApplication(ALICE));
    }

    @Test
    public void hasApplication_applicationInAddressBook_returnsTrue() {
        companyBook.addApplication(ALICE);
        assertTrue(companyBook.hasApplication(ALICE));
    }

    @Test
    public void hasApplication_applicationWithSameIdentityFieldsInAddressBook_returnsTrue() {
        companyBook.addApplication(ALICE);
        Application editedAlice = new ApplicationBuilder(ALICE).withCompany(VALID_COMPANY_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(companyBook.hasApplication(editedAlice));
    }

    @Test
    public void getApplicationList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> companyBook.getApplicationList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = AddressBook.class.getCanonicalName() + "{applications=" + companyBook.getApplicationList() + "}";
        assertEquals(expected, companyBook.toString());
    }

    /**
     * A stub ReadOnlyAddressBook whose applications list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Application> applications = FXCollections.observableArrayList();

        AddressBookStub(Collection<Application> applications) {
            this.applications.setAll(applications);
        }

        @Override
        public ObservableList<Application> getApplicationList() {
            return applications;
        }
    }

}
