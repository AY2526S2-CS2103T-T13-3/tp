package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_APPLICATIONS_LISTED_OVERVIEW;
import static seedu.address.testutil.TypicalApplications.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.application.NoteContainsKeywordsPredicate;
import seedu.address.testutil.ApplicationBuilder;

public class FindNoteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        NoteContainsKeywordsPredicate firstPredicate =
                new NoteContainsKeywordsPredicate(Collections.singletonList("follow"));
        NoteContainsKeywordsPredicate secondPredicate =
                new NoteContainsKeywordsPredicate(Collections.singletonList("recruiter"));

        FindNoteCommand findFirstCommand = new FindNoteCommand(firstPredicate);
        FindNoteCommand findSecondCommand = new FindNoteCommand(secondPredicate);

        assertTrue(findFirstCommand.equals(findFirstCommand));
        assertTrue(findFirstCommand.equals(new FindNoteCommand(firstPredicate)));
        assertFalse(findFirstCommand.equals(1));
        assertFalse(findFirstCommand.equals(null));
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noApplicationFound() {
        String expectedMessage = String.format(MESSAGE_APPLICATIONS_LISTED_OVERVIEW, 0);
        NoteContainsKeywordsPredicate predicate = preparePredicate("nomatch");
        FindNoteCommand command = new FindNoteCommand(predicate);
        expectedModel.updateFilteredApplicationList(predicate);
        assertEquals(expectedMessage, command.execute(model).getFeedbackToUser());
        assertEquals(expectedModel.getFilteredApplicationList(), model.getFilteredApplicationList());
    }

    @Test
    public void execute_multipleKeywords_multipleApplicationsFound() {
        model.addApplication(new ApplicationBuilder().withRole("Intern A").withNote("Follow up next Monday").build());
        model.addApplication(new ApplicationBuilder().withRole("Intern B").withNote("Met recruiter at fair").build());

        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        // Modified to test PARTIAL match: "foll" matches "Follow", "recruit" matches "recruiter"
        NoteContainsKeywordsPredicate predicate = preparePredicate("foll recruit");
        FindNoteCommand command = new FindNoteCommand(predicate);
        expectedModel.updateFilteredApplicationList(predicate);

        String expectedMessage = String.format(MESSAGE_APPLICATIONS_LISTED_OVERVIEW,
                expectedModel.getFilteredApplicationList().size());

        assertEquals(expectedMessage, command.execute(model).getFeedbackToUser());
        assertEquals(expectedModel.getFilteredApplicationList(), model.getFilteredApplicationList());
    }

    @Test
    public void execute_one_extraKeyword() {
        model.addApplication(new ApplicationBuilder().withRole("Intern C").withNote("Follow up with HR").build());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        NoteContainsKeywordsPredicate predicate = preparePredicate("follow zzznotfound");
        FindNoteCommand command = new FindNoteCommand(predicate);
        expectedModel.updateFilteredApplicationList(predicate);

        String expectedMessage = String.format(MESSAGE_APPLICATIONS_LISTED_OVERVIEW,
                expectedModel.getFilteredApplicationList().size());
        assertEquals(expectedMessage, command.execute(model).getFeedbackToUser());
        assertEquals(expectedModel.getFilteredApplicationList(), model.getFilteredApplicationList());
    }

    /**
     * Parses {@code userInput} into a {@code NoteContainsKeywordsPredicate}.
     */
    private NoteContainsKeywordsPredicate preparePredicate(String userInput) {
        return new NoteContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
