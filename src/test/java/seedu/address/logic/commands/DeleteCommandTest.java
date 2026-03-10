package seedu.company.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.company.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.company.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.company.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.company.testutil.TypicalIndexes.INDEX_FIRST_APPLICATION;
import static seedu.company.testutil.TypicalIndexes.INDEX_SECOND_APPLICATION;
import static seedu.company.testutil.TypicalPersons.getTypicalCompanyBook;

import org.junit.jupiter.api.Test;

import seedu.company.commons.core.index.Index;
import seedu.company.logic.Messages;
import seedu.company.model.Model;
import seedu.company.model.ModelManager;
import seedu.company.model.UserPrefs;
import seedu.company.model.application.Application;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalCompanyBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Application applicationToDelete = model.getFilteredPersonList().get(INDEX_FIRST_APPLICATION.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_APPLICATION);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_APPLICATION_SUCCESS,
                Messages.format(applicationToDelete));

        ModelManager expectedModel = new ModelManager(model.getCompanyBook(), new UserPrefs());
        expectedModel.deletePerson(applicationToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_APPLICATION_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_APPLICATION);

        Application applicationToDelete = model.getFilteredPersonList().get(INDEX_FIRST_APPLICATION.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_APPLICATION);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_APPLICATION_SUCCESS,
                Messages.format(applicationToDelete));

        Model expectedModel = new ModelManager(model.getCompanyBook(), new UserPrefs());
        expectedModel.deletePerson(applicationToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_APPLICATION);

        Index outOfBoundIndex = INDEX_SECOND_APPLICATION;
        // ensures that outOfBoundIndex is still in bounds of company book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getCompanyBook().getPersonList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_APPLICATION_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_APPLICATION);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_APPLICATION);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_APPLICATION);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        DeleteCommand deleteCommand = new DeleteCommand(targetIndex);
        String expected = DeleteCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, deleteCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}
