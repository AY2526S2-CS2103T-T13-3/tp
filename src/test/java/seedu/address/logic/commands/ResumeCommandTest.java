package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_APPLICATION_DISPLAYED_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_APPLICATION;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_APPLICATION;

import java.nio.file.Files;
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
import seedu.address.logic.Messages;
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

public class ResumeCommandTest {

    @TempDir
    public Path tempDir;

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        Resume resume = new Resume("resume.pdf");
        assertThrows(NullPointerException.class, () -> new ResumeCommand(null, resume));
    }

    @Test
    public void constructor_nullResume_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ResumeCommand(Index.fromOneBased(1), null));
    }

    @Test
    public void execute_validIndex_success() throws Exception {
        Path resumePath = Files.createFile(tempDir.resolve("resume.pdf"));

        Application original = createApplication("");
        ModelStub model = new ModelStub(original);

        Resume resume = new Resume(resumePath.toString());
        ResumeCommand command = new ResumeCommand(Index.fromOneBased(1), resume);
        CommandResult result = command.execute(model);

        Application expected = createApplication(resumePath.toString());

        assertEquals(
                String.format(ResumeCommand.MESSAGE_SUCCESS, Messages.format(expected)),
                result.getFeedbackToUser()
        );
        assertEquals(expected, model.applicationSet);
        assertTrue(model.commitCalled);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Application original = createApplication("");
        ModelStub model = new ModelStub(original);

        ResumeCommand command = new ResumeCommand(
                Index.fromOneBased(2),
                new Resume("resume.pdf")
        );

        assertThrows(CommandException.class, MESSAGE_INVALID_APPLICATION_DISPLAYED_INDEX, () -> command.execute(model));
    }

    @Test
    public void execute_fileNotFound_throwsCommandException() {
        Application original = createApplication("");
        ModelStub model = new ModelStub(original);

        ResumeCommand command = new ResumeCommand(
                Index.fromOneBased(1),
                new Resume(tempDir.resolve("missing.pdf").toString())
        );

        assertThrows(CommandException.class, ResumeCommand.MESSAGE_FILE_NOT_FOUND, () -> command.execute(model));
    }

    @Test
    public void execute_invalidPath_throwsCommandException() {
        Application original = createApplication("");
        ModelStub model = new ModelStub(original);

        ResumeCommand command = new ResumeCommand(
                Index.fromOneBased(1),
                new Resume("resume.pdf")
        );

        assertThrows(CommandException.class, ResumeCommand.MESSAGE_FILE_NOT_FOUND, () -> command.execute(model));
    }

    @Test
    public void equals() {
        ResumeCommand firstCommand = new ResumeCommand(
                Index.fromOneBased(1),
                new Resume("a.pdf")
        );
        ResumeCommand secondCommand = new ResumeCommand(
                Index.fromOneBased(2),
                new Resume("b.pdf")
        );
        ResumeCommand firstCommandCopy = new ResumeCommand(
                Index.fromOneBased(1),
                new Resume("a.pdf")
        );

        assertTrue(firstCommand.equals(firstCommand));
        assertTrue(firstCommand.equals(firstCommandCopy));

        assertFalse(firstCommand.equals(1));
        assertFalse(firstCommand.equals(null));
        assertFalse(firstCommand.equals(secondCommand));
    }

    @Test
    public void toStringMethod() {
        Resume resume = new Resume("valid_resume.pdf");
        ResumeCommand command = new ResumeCommand(INDEX_FIRST_APPLICATION, resume);

        String expected = ResumeCommand.class.getCanonicalName()
                + "{index=" + INDEX_FIRST_APPLICATION + ", resume=" + resume + "}";

        assertEquals(expected, command.toString());
    }

    @Test
    public void equals_sameIndexDifferentResume_false() {
        ResumeCommand firstCommand = new ResumeCommand(INDEX_FIRST_APPLICATION, new Resume("resume1.pdf"));
        ResumeCommand secondCommand = new ResumeCommand(INDEX_FIRST_APPLICATION, new Resume("resume2.pdf"));

        assertFalse(firstCommand.equals(secondCommand));
    }

    @Test
    public void equals_differentIndexSameResume_false() {
        Resume resume = new Resume("resume.pdf");
        ResumeCommand firstCommand = new ResumeCommand(INDEX_FIRST_APPLICATION, resume);
        ResumeCommand secondCommand = new ResumeCommand(INDEX_SECOND_APPLICATION, resume);

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

    /**
     * A default model stub that only supports the methods needed by ResumeCommand.
     */
    private static class ModelStub implements Model {
        private final ObservableList<Application> filteredApplications =
                FXCollections.observableArrayList();
        private Application applicationSet;
        private boolean commitCalled;

        ModelStub(Application application) {
            filteredApplications.add(application);
        }

        @Override
        public ObservableList<Application> getFilteredApplicationList() {
            return filteredApplications;
        }

        @Override
        public void setApplication(Application target, Application editedApplication) {
            requireNonNull(target);
            requireNonNull(editedApplication);

            applicationSet = editedApplication;
            int index = filteredApplications.indexOf(target);
            filteredApplications.set(index, editedApplication);
        }

        @Override
        public void commitAddressBook() {
            commitCalled = true;
        }

        // Unused methods below
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
        public void updateFilteredApplicationList(Predicate<Application> predicate) {
            throw new AssertionError();
        }

        @Override
        public void updateSortedApplicationList(Comparator<Application> comparator) {
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
