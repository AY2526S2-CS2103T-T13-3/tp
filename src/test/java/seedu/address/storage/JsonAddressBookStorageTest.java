package seedu.company.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.company.testutil.Assert.assertThrows;
import static seedu.company.testutil.TypicalPersons.ALICE;
import static seedu.company.testutil.TypicalPersons.HOON;
import static seedu.company.testutil.TypicalPersons.IDA;
import static seedu.company.testutil.TypicalPersons.getTypicalCompanyBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.company.commons.exceptions.DataLoadingException;
import seedu.company.model.CompanyBook;
import seedu.company.model.ReadOnlyCompanyBook;

public class JsonCompanyBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonCompanyBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readCompanyBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readCompanyBook(null));
    }

    private java.util.Optional<ReadOnlyCompanyBook> readCompanyBook(String filePath) throws Exception {
        return new JsonCompanyBookStorage(Paths.get(filePath)).readCompanyBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readCompanyBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataLoadingException.class, () -> readCompanyBook("notJsonFormatCompanyBook.json"));
    }

    @Test
    public void readCompanyBook_invalidPersonCompanyBook_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readCompanyBook("invalidPersonCompanyBook.json"));
    }

    @Test
    public void readCompanyBook_invalidAndValidPersonCompanyBook_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readCompanyBook("invalidAndValidPersonCompanyBook.json"));
    }

    @Test
    public void readAndSaveCompanyBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempCompanyBook.json");
        CompanyBook original = getTypicalCompanyBook();
        JsonCompanyBookStorage jsonCompanyBookStorage = new JsonCompanyBookStorage(filePath);

        // Save in new file and read back
        jsonCompanyBookStorage.saveCompanyBook(original, filePath);
        ReadOnlyCompanyBook readBack = jsonCompanyBookStorage.readCompanyBook(filePath).get();
        assertEquals(original, new CompanyBook(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addPerson(HOON);
        original.removePerson(ALICE);
        jsonCompanyBookStorage.saveCompanyBook(original, filePath);
        readBack = jsonCompanyBookStorage.readCompanyBook(filePath).get();
        assertEquals(original, new CompanyBook(readBack));

        // Save and read without specifying file path
        original.addPerson(IDA);
        jsonCompanyBookStorage.saveCompanyBook(original); // file path not specified
        readBack = jsonCompanyBookStorage.readCompanyBook().get(); // file path not specified
        assertEquals(original, new CompanyBook(readBack));

    }

    @Test
    public void saveCompanyBook_nullCompanyBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveCompanyBook(null, "SomeFile.json"));
    }

    /**
     * Saves {@code companyBook} at the specified {@code filePath}.
     */
    private void saveCompanyBook(ReadOnlyCompanyBook companyBook, String filePath) {
        try {
            new JsonCompanyBookStorage(Paths.get(filePath))
                    .saveCompanyBook(companyBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveCompanyBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveCompanyBook(new CompanyBook(), null));
    }
}
