package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSESSMENT_LINK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSESSMENT_PLATFORM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_TIME;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.application.Application;
import seedu.address.model.application.ApplicationEvent;

/**
 * Creates new Online Assessment for the application
 */
public class AssessmentCommand extends Command {
    public static final String COMMAND_WORD = "assessment";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets the online assessment for an application.\n"
            + "Example: " + COMMAND_WORD + " 1 (Must be a positive integer) "
            + PREFIX_EVENT_LOCATION + "home "
            + PREFIX_EVENT_TIME + "2026-03-24 10:00 "
            + PREFIX_ASSESSMENT_PLATFORM + "HackerRank "
            + PREFIX_ASSESSMENT_LINK + "www.hackerrank.com ";
    public static final String DATETIME_USAGE = "Ensure datetime is in yyyy-MM-dd HH:mm format\n";

    private final Index index;
    private final ApplicationEvent applicationEvent;

    /**
     * Constructs a new AssessmentCommand instance
     *
     * @param index
     * @param applicationEvent
     */
    public AssessmentCommand(Index index, ApplicationEvent applicationEvent) {
        this.index = index;
        this.applicationEvent = applicationEvent;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Application> lastShownList = model.getFilteredApplicationList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_APPLICATION_DISPLAYED_INDEX);
        }

        Application appToEdit = lastShownList.get(index.getZeroBased());
        Application editedApp = new Application(
                appToEdit.getRole(), appToEdit.getPhone(), appToEdit.getHrEmail(),
                appToEdit.getCompany(), appToEdit.getTags(), appToEdit.getStatus(), appToEdit.getDeadline(),
                applicationEvent, appToEdit.getNote(), appToEdit.getResume());

        model.setApplication(appToEdit, editedApp);
        model.commitAddressBook();
        return new CommandResult("Online Assessment updated for: " + editedApp.getCompany());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof AssessmentCommand)) {
            return false;
        }
        AssessmentCommand otherAssessmentCommand = (AssessmentCommand) other;
        return index.equals(otherAssessmentCommand.index)
                && applicationEvent.equals(otherAssessmentCommand.applicationEvent); // add this
    }
}
