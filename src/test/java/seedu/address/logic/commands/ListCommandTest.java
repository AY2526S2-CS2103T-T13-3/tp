package seedu.company.logic.commands;

import static seedu.company.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.company.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.company.testutil.TypicalIndexes.INDEX_FIRST_APPLICATION;
import static seedu.company.testutil.TypicalPersons.getTypicalCompanyBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.company.model.Model;
import seedu.company.model.ModelManager;
import seedu.company.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalCompanyBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getCompanyBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPersonAtIndex(model, INDEX_FIRST_APPLICATION);
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
