package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_APPLICATION_DISPLAYED_INDEX;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.HashSet;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.application.Application;
import seedu.address.model.application.Company;
import seedu.address.model.application.Deadline;
import seedu.address.model.application.HrEmail;
import seedu.address.model.application.Note;
import seedu.address.model.application.Phone;
import seedu.address.model.application.Resume;
import seedu.address.model.application.Role;
import seedu.address.model.application.Status;
import seedu.address.model.tag.Tag;

public class OpenResumeCommandTest {

    @TempDir
    public Path tempDir;

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new OpenResumeCommand(null));
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Application application = createApplication("");
        ModelStub model = new ModelStub(application);

        OpenResumeCommand command = new OpenResumeCommand(Index.fromOneBased(2));

        assertThrows(CommandException.class, MESSAGE_INVALID_APPLICATION_DISPLAYED_INDEX, () -> command.execute(model));
    }

    @Test
    public void execute_noResume_throwsCommandException() {
        Application application = createApplication("");
        ModelStub model = new ModelStub(application);

        OpenResumeCommand command = new OpenResumeCommand(Index.fromOneBased(1));

        assertThrows(CommandException.class, OpenResumeCommand.MESSAGE_NO_RESUME, () -> command.execute(model));
    }

    @Test
    public void execute_missingFile_throwsCommandException() {
        Path missing = tempDir.resolve("missing.pdf");
        Application application = createApplication(missing.toString());
        ModelStub model = new ModelStub(application);

        OpenResumeCommand command = new OpenResumeCommand(Index.fromOneBased(1));

        assertThrows(CommandException.class, OpenResumeCommand.MESSAGE_FILE_NOT_FOUND, () -> command.execute(model));
    }

    @Test
    public void equals() {
        OpenResumeCommand firstCommand = new OpenResumeCommand(Index.fromOneBased(1));
        OpenResumeCommand secondCommand = new OpenResumeCommand(Index.fromOneBased(2));
        OpenResumeCommand firstCommandCopy = new OpenResumeCommand(Index.fromOneBased(1));

        assertTrue(firstCommand.equals(firstCommand));
        assertTrue(firstCommand.equals(firstCommandCopy));

        assertFalse(firstCommand.equals(1));
        assertFalse(firstCommand.equals(null));
        assertFalse(firstCommand.equals(secondCommand));
    }

    private static Application createApplication(String resumePath) {
        return new Application(
                new Role("Software Engineer"),
                new Phone("91234567"),
                new HrEmail("hr@google.com"),
                new Company("Google", "Singapore"),
                new HashSet<Tag>(),
                Status.APPLIED,
                Deadline.getEmptyDeadline(),
                null,
                new Note(""),
                new Resume(resumePath)
        );
    }

    private static class ModelStub implements Model {
        private final ObservableList<Application> filteredApplications = FXCollections.observableArrayList();

        ModelStub(Application application) {
            filteredApplications.add(application);
        }

        @Override
        public ObservableList<Application> getFilteredApplicationList() {
            return filteredApplications;
        }

        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError();
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError();
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError();
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError();
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError();
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError();
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook addressBook) {
            throw new AssertionError();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError();
        }

        @Override
        public boolean hasApplication(Application application) {
            throw new AssertionError();
        }

        @Override
        public void deleteApplication(Application target) {
            throw new AssertionError();
        }

        @Override
        public void addApplication(Application application) {
            throw new AssertionError();
        }

        @Override
        public void setApplication(Application target, Application editedApplication) {
            throw new AssertionError();
        }

        @Override
        public void updateFilteredApplicationList(Predicate<Application> predicate) {
            throw new AssertionError();
        }

        @Override
        public void updateSortedApplicationList(Comparator<Application> comparator) {
            throw new AssertionError();
        }

        @Override
        public void commitAddressBook() {
            throw new AssertionError();
        }

        @Override
        public void undoAddressBook() {
            throw new AssertionError();
        }

        @Override
        public boolean canUndoAddressBook() {
            return false;
        }

        @Override
        public void redoAddressBook() {
            throw new AssertionError();
        }

        @Override
        public boolean canRedoAddressBook() {
            return false;
        }
    }
}
