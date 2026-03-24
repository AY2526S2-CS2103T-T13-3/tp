package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalApplications.getTypicalAddressBook;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.application.Application;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.ApplicationBuilder;

public class ReminderCommandTest {

    private Model model;
    private Model expectedModel;
    private final String reminderTagName = ReminderCommand.REMINDER_TAG_NAME;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_applicationWithinThreeDays_tagAdded() {
        LocalDate twoDaysLater = LocalDate.now().plusDays(2);
        Application nearDeadlineApp = new ApplicationBuilder()
                .withRole("Near Deadline")
                .withDeadline(twoDaysLater.toString()).build();

        model.addApplication(nearDeadlineApp);

        Application updatedApp = new ApplicationBuilder(nearDeadlineApp)
                .withTags(reminderTagName).build();
        expectedModel.addApplication(updatedApp);

        expectedModel.updateSortedApplicationList((a1, a2) -> a1.getDeadline().compareTo(a2.getDeadline()));

        assertCommandSuccess(new ReminderCommand(), model, ReminderCommand.MESSAGE_SUCCESS, expectedModel);

        assertTrue(model.getFilteredApplicationList().get(0).getTags().contains(new Tag(reminderTagName)));
    }

    @Test
    public void execute_applicationFiveDaysAway_noTagAdded() {
        LocalDate fiveDaysLater = LocalDate.now().plusDays(5);
        Application farDeadlineApp = new ApplicationBuilder()
                .withRole("Far Deadline")
                .withDeadline(fiveDaysLater.toString()).build();

        model.addApplication(farDeadlineApp);
        ReminderCommand reminderCommand = new ReminderCommand();
        reminderCommand.execute(model);

        boolean hasTag = model.getFilteredApplicationList().stream()
                .anyMatch(app -> app.getTags().contains(new Tag(reminderTagName)));
        assertEquals(false, hasTag);
    }
}
