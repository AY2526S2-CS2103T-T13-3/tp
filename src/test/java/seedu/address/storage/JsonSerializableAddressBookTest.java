package seedu.company.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.company.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.company.commons.exceptions.IllegalValueException;
import seedu.company.commons.util.JsonUtil;
import seedu.company.model.CompanyBook;
import seedu.company.testutil.TypicalPersons;

public class JsonSerializableCompanyBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableCompanyBookTest");
    private static final Path TYPICAL_APPLICATIONS_FILE = TEST_DATA_FOLDER.resolve("typicalPersonsCompanyBook.json");
    private static final Path INVALID_APPLICATION_FILE = TEST_DATA_FOLDER.resolve("invalidPersonCompanyBook.json");
    private static final Path DUPLICATE_APPLICATION_FILE = TEST_DATA_FOLDER.resolve("duplicatePersonCompanyBook.json");

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializableCompanyBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_APPLICATIONS_FILE,
                JsonSerializableCompanyBook.class).get();
        CompanyBook companyBookFromFile = dataFromFile.toModelType();
        CompanyBook typicalPersonsCompanyBook = TypicalPersons.getTypicalCompanyBook();
        assertEquals(companyBookFromFile, typicalPersonsCompanyBook);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializableCompanyBook dataFromFile = JsonUtil.readJsonFile(INVALID_APPLICATION_FILE,
                JsonSerializableCompanyBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonSerializableCompanyBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_APPLICATION_FILE,
                JsonSerializableCompanyBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableCompanyBook.MESSAGE_DUPLICATE_APPLICATION,
                dataFromFile::toModelType);
    }

}
