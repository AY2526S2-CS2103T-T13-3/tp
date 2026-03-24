package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.model.Model;
import seedu.address.model.application.Application;
import seedu.address.model.tag.Tag;

/**
 * Identifies and tags job applications that are nearing their deadline.
 * This command sorts the current list by deadline and adds a specific reminder tag
 * to any application with a deadline less than 3 days away from today.
 */
public class ReminderCommand extends Command {
    public static final String COMMAND_WORD = "reminder";
    public static final String REMINDER_TAG_NAME = "Urgent";
    public static final String MESSAGE_SUCCESS = "Sorted by deadline and updated reminders!";
    private static final java.util.logging.Logger logger =
            seedu.address.commons.core.LogsCenter.getLogger(ReminderCommand.class);

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        logger.info("Starting reminder command execution...");

        //sort by time
        Comparator<Application> comparator = (a1, a2) ->
                a1.getDeadline().compareTo(a2.getDeadline());
        model.updateSortedApplicationList(comparator);

        //Traverse the list and check the date
        List<Application> lastShownList = model.getFilteredApplicationList();
        LocalDate today = LocalDate.now();

        for (Application app : lastShownList) {
            LocalDate deadlineDate = app.getDeadline().getLocalDate();

            if (deadlineDate == null) {
                continue;
            }

            long daysBetween = ChronoUnit.DAYS.between(today, app.getDeadline().getLocalDate());

            if (daysBetween >= 0 && daysBetween < 3) {
                // 如果少于3天且还没这个 tag，就加上去
                if (!app.getTags().contains(new Tag(REMINDER_TAG_NAME))) {
                    Application updatedApp = addReminderTag(app);
                    model.setApplication(app, updatedApp);
                }
            }
        }
        model.commitAddressBook();
        return new CommandResult("Sorted by deadline and updated reminders!");
    }

    private Application addReminderTag(Application app) {
        Set<Tag> newTags = new HashSet<>(app.getTags());
        newTags.add(new Tag(REMINDER_TAG_NAME));
        return new Application(app.getRole(), app.getPhone(), app.getHrEmail(),
                app.getCompany(), newTags, app.getStatus(), app.getDeadline(), app.getApplicationEvent());
    }

}
