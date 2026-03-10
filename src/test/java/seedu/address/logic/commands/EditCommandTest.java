package seedu.company.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.company.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.company.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.company.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.company.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.company.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.company.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.company.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.company.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.company.testutil.TypicalIndexes.INDEX_FIRST_APPLICATION;
import static seedu.company.testutil.TypicalIndexes.INDEX_SECOND_APPLICATION;
import static seedu.company.testutil.TypicalPersons.getTypicalCompanyBook;

import org.junit.jupiter.api.Test;

import seedu.company.commons.core.index.Index;
import seedu.company.logic.Messages;
import seedu.company.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.company.model.CompanyBook;
import seedu.company.model.Model;
import seedu.company.model.ModelManager;
import seedu.company.model.UserPrefs;
import seedu.company.model.application.Application;
import seedu.company.testutil.EditPersonDescriptorBuilder;
import seedu.company.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalCompanyBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Application editedApplication = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedApplication).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_APPLICATION, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_APPLICATION_SUCCESS, Messages.format(editedApplication));

        Model expectedModel = new ModelManager(new CompanyBook(model.getCompanyBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedApplication);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
        Application lastApplication = model.getFilteredPersonList().get(indexLastPerson.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(lastApplication);
        Application editedApplication = personInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditCommand editCommand = new EditCommand(indexLastPerson, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_APPLICATION_SUCCESS, Messages.format(editedApplication));

        Model expectedModel = new ModelManager(new CompanyBook(model.getCompanyBook()), new UserPrefs());
        expectedModel.setPerson(lastApplication, editedApplication);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_APPLICATION, new EditPersonDescriptor());
        Application editedApplication = model.getFilteredPersonList().get(INDEX_FIRST_APPLICATION.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_APPLICATION_SUCCESS, Messages.format(editedApplication));

        Model expectedModel = new ModelManager(new CompanyBook(model.getCompanyBook()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_APPLICATION);

        Application applicationInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_APPLICATION.getZeroBased());
        Application editedApplication = new PersonBuilder(applicationInFilteredList).withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_APPLICATION,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_APPLICATION_SUCCESS, Messages.format(editedApplication));

        Model expectedModel = new ModelManager(new CompanyBook(model.getCompanyBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedApplication);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePersonUnfilteredList_failure() {
        Application firstApplication = model.getFilteredPersonList().get(INDEX_FIRST_APPLICATION.getZeroBased());
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(firstApplication).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_APPLICATION, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_APPLICATION);
    }

    @Test
    public void execute_duplicatePersonFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_APPLICATION);

        // edit person in filtered list into a duplicate in company book
        Application applicationInList = model.getCompanyBook().getPersonList().get(INDEX_SECOND_APPLICATION.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_APPLICATION,
                new EditPersonDescriptorBuilder(applicationInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_APPLICATION);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_APPLICATION_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of company book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_APPLICATION);
        Index outOfBoundIndex = INDEX_SECOND_APPLICATION;
        // ensures that outOfBoundIndex is still in bounds of company book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getCompanyBook().getPersonList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_APPLICATION_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_APPLICATION, DESC_AMY);

        // same values -> returns true
        EditPersonDescriptor copyDescriptor = new EditPersonDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_APPLICATION, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_APPLICATION, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_APPLICATION, DESC_BOB)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        EditCommand editCommand = new EditCommand(index, editPersonDescriptor);
        String expected = EditCommand.class.getCanonicalName() + "{index=" + index + ", editPersonDescriptor="
                + editPersonDescriptor + "}";
        assertEquals(expected, editCommand.toString());
    }

}
