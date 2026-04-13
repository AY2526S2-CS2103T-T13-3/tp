package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_APPLICATION_DISPLAYED_INDEX;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashSet;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

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
import seedu.address.model.application.ApplicationEvent;
import seedu.address.model.application.Company;
import seedu.address.model.application.Deadline;
import seedu.address.model.application.HrEmail;
import seedu.address.model.application.Note;
import seedu.address.model.application.OnlineAssessment;
import seedu.address.model.application.Phone;
import seedu.address.model.application.Resume;
import seedu.address.model.application.Role;
import seedu.address.model.application.Status;
import seedu.address.model.tag.Tag;

public class RemoveEventCommandTest {

    // ── constructor ──────────────────────────────────────────────────────────

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RemoveEventCommand(null));
    }

    // ── execute: success ─────────────────────────────────────────────────────

    @Test
    public void execute_validIndexWithEvent_removesEvent() throws Exception {
        ApplicationEvent event = new OnlineAssessment("home",
                LocalDateTime.of(2026, 5, 1, 10, 0), "HackerRank", "www.hackerrank.com");
        Application original = createApplicationWithEvent(event);
        ModelStub model = new ModelStub(original);

        RemoveEventCommand command = new RemoveEventCommand(Index.fromOneBased(1));
        CommandResult result = command.execute(model);

        // The updated application should have a null event
        Application expected = createApplicationWithEvent(null);
        assertEquals(String.format(RemoveEventCommand.MESSAGE_SUCCESS, Messages.format(expected)),
                result.getFeedbackToUser());
        assertEquals(expected, model.applicationSet);
        assertTrue(model.commitCalled);
    }

    // ── execute: failure ─────────────────────────────────────────────────────

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        ApplicationEvent event = new OnlineAssessment("home",
                LocalDateTime.of(2026, 5, 1, 10, 0), "HackerRank", "www.hackerrank.com");
        Application original = createApplicationWithEvent(event);
        ModelStub model = new ModelStub(original);

        RemoveEventCommand command = new RemoveEventCommand(Index.fromOneBased(2));

        assertThrows(CommandException.class, MESSAGE_INVALID_APPLICATION_DISPLAYED_INDEX, () -> command.execute(model));
    }

    @Test
    public void execute_noEvent_throwsCommandException() {
        Application original = createApplicationWithEvent(null);
        ModelStub model = new ModelStub(original);

        RemoveEventCommand command = new RemoveEventCommand(Index.fromOneBased(1));

        assertThrows(CommandException.class, RemoveEventCommand.MESSAGE_NO_EVENT, () -> command.execute(model));
    }

    // ── equals ───────────────────────────────────────────────────────────────

    @Test
    public void equals() {
        RemoveEventCommand firstCommand = new RemoveEventCommand(Index.fromOneBased(1));
        RemoveEventCommand secondCommand = new RemoveEventCommand(Index.fromOneBased(2));
        RemoveEventCommand firstCommandCopy = new RemoveEventCommand(Index.fromOneBased(1));

        // same object
        assertEquals(firstCommand, firstCommand);

        // same index
        assertEquals(firstCommand, firstCommandCopy);

        // different type
        assertNotEquals(1, firstCommand);

        // null
        assertNotEquals(null, firstCommand);

        // different index
        assertNotEquals(firstCommand, secondCommand);
    }

    // ── toString ─────────────────────────────────────────────────────────────

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        RemoveEventCommand command = new RemoveEventCommand(targetIndex);
        String expected = RemoveEventCommand.class.getCanonicalName() + "{index=" + targetIndex + "}";
        assertEquals(expected, command.toString());
    }

    // ── helpers ──────────────────────────────────────────────────────────────

    private static Application createApplicationWithEvent(ApplicationEvent event) {
        return new Application(
                new Role("Software Engineer"),
                new Phone("91234567"),
                new HrEmail("hr@google.com"),
                new Company("Google", "Singapore"),
                new HashSet<Tag>(),
                Status.APPLIED,
                Deadline.getEmptyDeadline(),
                event,
                new Note(""),
                Resume.getEmptyResume());
    }

    // ── ModelStub ─────────────────────────────────────────────────────────────

    private static class ModelStub implements Model {
        private final ObservableList<Application> filteredApplications =
                FXCollections.observableArrayList();
        private Application applicationSet;
        private boolean commitCalled = false;

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
