package seedu.company.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.company.testutil.TypicalPersons.getTypicalCompanyBook;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.company.commons.core.GuiSettings;
import seedu.company.model.CompanyBook;
import seedu.company.model.ReadOnlyCompanyBook;
import seedu.company.model.UserPrefs;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonCompanyBookStorage companyBookStorage = new JsonCompanyBookStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(companyBookStorage, userPrefsStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void companyBookReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonCompanyBookStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonCompanyBookStorageTest} class.
         */
        CompanyBook original = getTypicalCompanyBook();
        storageManager.saveCompanyBook(original);
        ReadOnlyCompanyBook retrieved = storageManager.readCompanyBook().get();
        assertEquals(original, new CompanyBook(retrieved));
    }

    @Test
    public void getCompanyBookFilePath() {
        assertNotNull(storageManager.getCompanyBookFilePath());
    }

}
