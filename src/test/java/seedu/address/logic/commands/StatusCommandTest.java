package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalApplications.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_APPLICATION;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_APPLICATION;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.application.Application;
import seedu.address.model.application.Status;
import seedu.address.testutil.ApplicationBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code StatusCommand}.
 */
class StatusCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        // EP: valid index, different valid status -> status is updated successfully
        Application applicationToUpdate = model.getFilteredApplicationList()
                .get(INDEX_FIRST_APPLICATION.getZeroBased());
        Status newStatus = Status.INTERVIEWING;

        StatusCommand statusCommand = new StatusCommand(INDEX_FIRST_APPLICATION, newStatus);

        Application updatedApplication = new ApplicationBuilder(applicationToUpdate)
                .withStatus(newStatus)
                .build();

        String expectedMessage = String.format(StatusCommand.MESSAGE_SUCCESS, Messages.format(updatedApplication));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setApplication(applicationToUpdate, updatedApplication);

        assertCommandSuccess(statusCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        // BVA + Invalid: index just outside the valid displayed list range -> command failure
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredApplicationList().size() + 1);
        StatusCommand statusCommand = new StatusCommand(outOfBoundIndex, Status.INTERVIEWING);

        assertCommandFailure(statusCommand, model, Messages.MESSAGE_INVALID_APPLICATION_DISPLAYED_INDEX);
    }

    @Test
    public void execute_allStatusValues_success() {
        // EP: all valid status enum values are accepted
        for (Status status : Status.values()) {
            Model testModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
            Application applicationToUpdate = testModel.getFilteredApplicationList()
                    .get(INDEX_FIRST_APPLICATION.getZeroBased());

            StatusCommand statusCommand = new StatusCommand(INDEX_FIRST_APPLICATION, status);

            if (applicationToUpdate.getStatus().equals(status)) {
                // EP: valid status same as current status -> unchanged message, no model update
                String expectedMessage = String.format(StatusCommand.MESSAGE_STATUS_UNCHANGED, status);
                Model expectedModel = new ModelManager(testModel.getAddressBook(), new UserPrefs());
                assertCommandSuccess(statusCommand, testModel, expectedMessage, expectedModel);
            } else {
                // EP: valid status different from current status -> model updated
                Application updatedApplication = new ApplicationBuilder(applicationToUpdate)
                        .withStatus(status)
                        .build();

                String expectedMessage = String.format(StatusCommand.MESSAGE_SUCCESS,
                        Messages.format(updatedApplication));

                Model expectedModel = new ModelManager(testModel.getAddressBook(), new UserPrefs());
                expectedModel.setApplication(applicationToUpdate, updatedApplication);

                assertCommandSuccess(statusCommand, testModel, expectedMessage, expectedModel);
            }
        }
    }

    @Test
    public void execute_sameStatus_success() {
        // EP: valid index, status same as existing status -> no-op with unchanged message
        Application applicationToUpdate = model.getFilteredApplicationList()
                .get(INDEX_FIRST_APPLICATION.getZeroBased());

        Status sameStatus = applicationToUpdate.getStatus();
        StatusCommand statusCommand = new StatusCommand(INDEX_FIRST_APPLICATION, sameStatus);

        String expectedMessage = String.format(StatusCommand.MESSAGE_STATUS_UNCHANGED, sameStatus);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        assertCommandSuccess(statusCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        // Equality case: same values, different values, null, and different types
        StatusCommand statusFirstCommand = new StatusCommand(INDEX_FIRST_APPLICATION, Status.INTERVIEWING);
        StatusCommand statusSecondCommand = new StatusCommand(INDEX_SECOND_APPLICATION, Status.OFFERED);

        // same object -> returns true
        assertTrue(statusFirstCommand.equals(statusFirstCommand));

        // same values -> returns true
        StatusCommand statusFirstCommandCopy = new StatusCommand(INDEX_FIRST_APPLICATION, Status.INTERVIEWING);
        assertTrue(statusFirstCommand.equals(statusFirstCommandCopy));

        // different types -> returns false
        assertFalse(statusFirstCommand.equals(new ClearCommand()));

        // null -> returns false
        assertFalse(statusFirstCommand.equals(null));

        // different index -> returns false
        assertFalse(statusFirstCommand.equals(new StatusCommand(INDEX_SECOND_APPLICATION, Status.INTERVIEWING)));

        // different status -> returns false
        assertFalse(statusFirstCommand.equals(new StatusCommand(INDEX_FIRST_APPLICATION, Status.OFFERED)));

        // different index and status -> returns false
        assertFalse(statusFirstCommand.equals(statusSecondCommand));
    }

    @Test
    public void toStringMethod() {
        // String representation: toString returns the expected format
        Index index = Index.fromOneBased(1);
        StatusCommand statusCommand = new StatusCommand(index, Status.INTERVIEWING);
        String expected = StatusCommand.class.getCanonicalName()
                + "{index=" + index + ", status=" + Status.INTERVIEWING + "}";
        assertEquals(expected, statusCommand.toString());
    }

    @Test
    public void execute_firstApplicationToOffered_success() {
        // EP: first valid index, different valid status -> application updated successfully
        Model testModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Application applicationToUpdate = testModel.getFilteredApplicationList()
                .get(INDEX_FIRST_APPLICATION.getZeroBased());

        StatusCommand statusCommand = new StatusCommand(INDEX_FIRST_APPLICATION, Status.OFFERED);

        Application updatedApplication = new ApplicationBuilder(applicationToUpdate)
                .withStatus(Status.OFFERED)
                .build();

        String expectedMessage = String.format(StatusCommand.MESSAGE_SUCCESS, Messages.format(updatedApplication));

        Model expectedModel = new ModelManager(testModel.getAddressBook(), new UserPrefs());
        expectedModel.setApplication(applicationToUpdate, updatedApplication);

        assertCommandSuccess(statusCommand, testModel, expectedMessage, expectedModel);
    }

    @Test
    public void execute_secondApplicationToRejected_success() {
        // EP: another valid index, different valid status -> correct application updated successfully
        Model testModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Application applicationToUpdate = testModel.getFilteredApplicationList()
                .get(INDEX_SECOND_APPLICATION.getZeroBased());

        StatusCommand statusCommand = new StatusCommand(INDEX_SECOND_APPLICATION, Status.REJECTED);

        Application updatedApplication = new ApplicationBuilder(applicationToUpdate)
                .withStatus(Status.REJECTED)
                .build();

        String expectedMessage = String.format(StatusCommand.MESSAGE_SUCCESS, Messages.format(updatedApplication));

        Model expectedModel = new ModelManager(testModel.getAddressBook(), new UserPrefs());
        expectedModel.setApplication(applicationToUpdate, updatedApplication);

        assertCommandSuccess(statusCommand, testModel, expectedMessage, expectedModel);
    }
}
