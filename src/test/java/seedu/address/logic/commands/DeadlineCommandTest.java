package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalApplications.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_APPLICATION;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.application.Application;
import seedu.address.model.application.Deadline;

public class DeadlineCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_setDeadline_success() {
        Application firstApp = model.getFilteredApplicationList().get(INDEX_FIRST_APPLICATION.getZeroBased());
        Deadline newDeadline = new Deadline("2026-12-31");

        // Create the expected updated Application
        Application editedApp = new Application(
                firstApp.getRole(), firstApp.getPhone(), firstApp.getHrEmail(),
                firstApp.getCompany(), firstApp.getTags(), firstApp.getStatus(),
                newDeadline, firstApp.getApplicationEvent(), firstApp.getNote());

        DeadlineCommand deadlineCommand = new DeadlineCommand(INDEX_FIRST_APPLICATION, newDeadline);

        String expectedMessage = String.format(DeadlineCommand.MESSAGE_SUCCESS, Messages.format(editedApp));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setApplication(firstApp, editedApp);

        assertCommandSuccess(deadlineCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        final DeadlineCommand standardCommand = new DeadlineCommand(
                INDEX_FIRST_APPLICATION, new Deadline("2026-12-31"));

        // same values -> returns true
        DeadlineCommand commandWithSameValues = new DeadlineCommand(
                INDEX_FIRST_APPLICATION, new Deadline("2026-12-31"));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different index -> returns false
        assertFalse(standardCommand.equals(
                new DeadlineCommand(INDEX_FIRST_APPLICATION, new Deadline("2026-01-01"))));
    }
}
