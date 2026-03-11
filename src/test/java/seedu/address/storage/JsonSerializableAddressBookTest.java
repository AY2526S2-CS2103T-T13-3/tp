package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.AddressBook;
import seedu.address.testutil.TypicalApplications;

public class JsonSerializableAddressBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableAddressBookTest");
    private static final Path TYPICAL_APPLICATIONS_FILE = TEST_DATA_FOLDER.resolve("typicalApplicationsAddressBook.json");
    private static final Path INVALID_APPLICATION_FILE = TEST_DATA_FOLDER.resolve("invalidApplicationAddressBook.json");
    private static final Path DUPLICATE_APPLICATION_FILE = TEST_DATA_FOLDER.resolve("duplicateApplicationAddressBook.json");

    @Test
    public void toModelType_typicalApplicationsFile_success() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_APPLICATIONS_FILE,
                JsonSerializableAddressBook.class).get();
        AddressBook companyBookFromFile = dataFromFile.toModelType();
        AddressBook typicalApplicationsAddressBook = TypicalApplications.getTypicalAddressBook();
        assertEquals(companyBookFromFile, typicalApplicationsAddressBook);
    }

    @Test
    public void toModelType_invalidApplicationFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(INVALID_APPLICATION_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateApplications_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_APPLICATION_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableAddressBook.MESSAGE_DUPLICATE_APPLICATION,
                dataFromFile::toModelType);
    }

}
