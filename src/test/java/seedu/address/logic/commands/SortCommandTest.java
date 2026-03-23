package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalApplications.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Integration testing of SortCommand (interaction with Model).
 */
public class SortCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_sortByAlphabet_success() throws CommandException {
        SortCommand sortCommand = new SortCommand("alphabet");
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "alphabet");

        expectedModel.updateSortedApplicationList((a1, a2) ->
                a1.getRole().roleName.compareToIgnoreCase(a2.getRole().roleName));

        CommandResult result = sortCommand.execute(model);

        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedModel.getFilteredApplicationList(), model.getFilteredApplicationList());
    }

    @Test
    public void execute_sortByTime_success() throws CommandException { // 添加 throws
        SortCommand sortCommand = new SortCommand("time");
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "time");

        expectedModel.updateSortedApplicationList((a1, a2) ->
                a1.getDeadline().compareTo(a2.getDeadline()));

        CommandResult result = sortCommand.execute(model);

        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedModel.getFilteredApplicationList(), model.getFilteredApplicationList());
    }
}
