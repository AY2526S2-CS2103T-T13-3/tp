package seedu.company.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.company.logic.commands.CommandTestUtil.VALID_COMPANY_BOB;
import static seedu.company.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.company.testutil.Assert.assertThrows;
import static seedu.company.testutil.TypicalPersons.ALICE;
import static seedu.company.testutil.TypicalPersons.getTypicalCompanyBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.company.model.application.Application;
import seedu.company.model.application.exceptions.DuplicateApplicationException;
import seedu.company.testutil.PersonBuilder;

public class CompanyBookTest {

    private final CompanyBook companyBook = new CompanyBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), companyBook.getPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> companyBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyCompanyBook_replacesData() {
        CompanyBook newData = getTypicalCompanyBook();
        companyBook.resetData(newData);
        assertEquals(newData, companyBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Application editedAlice = new PersonBuilder(ALICE).withCompany(VALID_COMPANY_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Application> newApplications = Arrays.asList(ALICE, editedAlice);
        CompanyBookStub newData = new CompanyBookStub(newApplications);

        assertThrows(DuplicateApplicationException.class, () -> companyBook.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> companyBook.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInCompanyBook_returnsFalse() {
        assertFalse(companyBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInCompanyBook_returnsTrue() {
        companyBook.addPerson(ALICE);
        assertTrue(companyBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInCompanyBook_returnsTrue() {
        companyBook.addPerson(ALICE);
        Application editedAlice = new PersonBuilder(ALICE).withCompany(VALID_COMPANY_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(companyBook.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> companyBook.getPersonList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = CompanyBook.class.getCanonicalName() + "{persons=" + companyBook.getPersonList() + "}";
        assertEquals(expected, companyBook.toString());
    }

    /**
     * A stub ReadOnlyCompanyBook whose persons list can violate interface constraints.
     */
    private static class CompanyBookStub implements ReadOnlyCompanyBook {
        private final ObservableList<Application> applications = FXCollections.observableArrayList();

        CompanyBookStub(Collection<Application> applications) {
            this.applications.setAll(applications);
        }

        @Override
        public ObservableList<Application> getPersonList() {
            return applications;
        }
    }

}
