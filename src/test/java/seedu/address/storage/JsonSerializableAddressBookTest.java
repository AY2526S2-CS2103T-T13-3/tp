package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.testutil.TypicalApplications;

/**
 * Tests for {@link JsonSerializableAddressBook}.
 * These tests construct in-memory JSON-adapted data instead of reading from files,
 * to avoid any dependency on the file system layout.
 */
public class JsonSerializableAddressBookTest {

    @Test
    public void toModelType_typicalApplications_success() throws Exception {
        AddressBook typicalApplicationsAddressBook = TypicalApplications.getTypicalAddressBook();
        JsonSerializableAddressBook serializable = new JsonSerializableAddressBook(typicalApplicationsAddressBook);
        AddressBook companyBookFromSerializable = serializable.toModelType();
        assertEquals(companyBookFromSerializable, typicalApplicationsAddressBook);
    }

    @Test
    public void toModelType_invalidApplication_throwsIllegalValueException() {
        // Application with invalid role (empty string)
        JsonAdaptedApplication invalidApplication = new JsonAdaptedApplication("",
                "94351253", "alice@example.com",
                "Some Company", "",
                Collections.emptyList(),
                "APPLIED");
        JsonSerializableAddressBook serializable =
                new JsonSerializableAddressBook(Collections.singletonList(invalidApplication));
        assertThrows(IllegalValueException.class, serializable::toModelType);
    }

    @Test
    public void toModelType_duplicateApplications_throwsIllegalValueException() {
        JsonAdaptedApplication alice = new JsonAdaptedApplication(TypicalApplications.ALICE);
        // Duplicate by identity (same role)
        JsonAdaptedApplication duplicateAlice = new JsonAdaptedApplication(TypicalApplications.ALICE);
        List<JsonAdaptedApplication> applications = Arrays.asList(alice, duplicateAlice);
        JsonSerializableAddressBook serializable = new JsonSerializableAddressBook(applications);

        assertThrows(IllegalValueException.class, JsonSerializableAddressBook.MESSAGE_DUPLICATE_APPLICATION,
                serializable::toModelType);
    }

}
