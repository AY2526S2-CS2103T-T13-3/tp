package seedu.company.logic.commands;

import static seedu.company.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.company.testutil.TypicalPersons.getTypicalCompanyBook;

import org.junit.jupiter.api.Test;

import seedu.company.model.CompanyBook;
import seedu.company.model.Model;
import seedu.company.model.ModelManager;
import seedu.company.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyCompanyBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyCompanyBook_success() {
        Model model = new ModelManager(getTypicalCompanyBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalCompanyBook(), new UserPrefs());
        expectedModel.setCompanyBook(new CompanyBook());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
