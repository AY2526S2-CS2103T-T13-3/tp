package seedu.company.logic.commands;

import static seedu.company.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.company.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.company.testutil.TypicalPersons.getTypicalCompanyBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.company.logic.Messages;
import seedu.company.model.Model;
import seedu.company.model.ModelManager;
import seedu.company.model.UserPrefs;
import seedu.company.model.application.Application;
import seedu.company.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalCompanyBook(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() {
        Application validApplication = new PersonBuilder().build();

        Model expectedModel = new ModelManager(model.getCompanyBook(), new UserPrefs());
        expectedModel.addPerson(validApplication);

        assertCommandSuccess(new AddCommand(validApplication), model,
                String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validApplication)),
                expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Application applicationInList = model.getCompanyBook().getPersonList().get(0);
        assertCommandFailure(new AddCommand(applicationInList), model,
                AddCommand.MESSAGE_DUPLICATE_APPLICATION);
    }

}
