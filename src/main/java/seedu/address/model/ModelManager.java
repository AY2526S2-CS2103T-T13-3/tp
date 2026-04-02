package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.application.Application;

/**
 * Represents the in-memory model of the Hired! data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedAddressBook versionedAddressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Application> filteredApplications;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with Hired!: " + addressBook + " and user prefs " + userPrefs);

        this.versionedAddressBook = new VersionedAddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredApplications = new FilteredList<>(this.versionedAddressBook.getApplicationList());
    }

    //    public ModelManager() {
    //        this(new VersionedAddressBook(), new UserPrefs());
    //    }

    //=========== Undo ==================================================================================

    @Override
    public boolean canUndoAddressBook() {
        return versionedAddressBook.canUndo();
    }

    @Override
    public void undoAddressBook() {
        versionedAddressBook.undo();
    }

    @Override
    public void commitAddressBook() {
        versionedAddressBook.commit();
    }

    //=========== Undo ==================================================================================

    @Override
    public boolean canRedoAddressBook() {
        return versionedAddressBook.canRedo();
    }

    @Override
    public void redoAddressBook() {
        versionedAddressBook.redo();
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.versionedAddressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return versionedAddressBook;
    }

    @Override
    public boolean hasApplication(Application application) {
        requireNonNull(application);
        return versionedAddressBook.hasApplication(application);
    }

    @Override
    public void deleteApplication(Application target) {
        versionedAddressBook.removeApplication(target);
    }

    @Override
    public void addApplication(Application application) {
        versionedAddressBook.addApplication(application);
        updateFilteredApplicationList(PREDICATE_SHOW_ALL_APPLICATIONS);
    }

    @Override
    public void setApplication(Application target, Application editedApplication) {
        requireAllNonNull(target, editedApplication);
        versionedAddressBook.setApplication(target, editedApplication);
    }

    //=========== Filtered Application List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Application} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Application> getFilteredApplicationList() {
        return filteredApplications;
    }

    @Override
    public void updateFilteredApplicationList(Predicate<Application> predicate) {
        requireNonNull(predicate);
        filteredApplications.setPredicate(predicate);
    }

    @Override
    public void updateSortedApplicationList(Comparator<Application> comparator) {
        requireNonNull(comparator);

        List<Application> sortedList = new ArrayList<>(versionedAddressBook.getApplicationList());

        sortedList.sort(comparator);

        versionedAddressBook.setApplications(sortedList);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return versionedAddressBook.equals(otherModelManager.versionedAddressBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredApplications.equals(otherModelManager.filteredApplications);
    }
}
