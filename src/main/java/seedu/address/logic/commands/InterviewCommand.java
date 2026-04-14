package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INTERVIEW_INTERVIEWER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INTERVIEW_TYPE;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.application.Application;
import seedu.address.model.application.ApplicationEvent;

/**
 * Sets or updates the interview event for an application identified by its index.
 */
public class InterviewCommand extends Command {

    public static final String COMMAND_WORD = "interview";

    public static final String DATETIME_USAGE = "Ensure datetime is in yyyy-MM-dd HH:mm format\n";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sets the interview for an application.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_EVENT_LOCATION + "LOCATION "
            + PREFIX_EVENT_TIME + "DATETIME "
            + "[" + PREFIX_INTERVIEW_INTERVIEWER + "INTERVIEWER_NAME] "
            + "[" + PREFIX_INTERVIEW_TYPE + "INTERVIEW_TYPE]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_EVENT_LOCATION + "Google HQ "
            + PREFIX_EVENT_TIME + "2026-05-10 14:00 "
            + PREFIX_INTERVIEW_INTERVIEWER + "John Doe "
            + PREFIX_INTERVIEW_TYPE + "technical";

    private final Index index;
    private final ApplicationEvent applicationEvent;

    /**
     * Constructs an {@code InterviewCommand}.
     *
     * @param index            one-based index of the application to update
     * @param applicationEvent the {@code Interview} event to associate
     */
    public InterviewCommand(Index index, ApplicationEvent applicationEvent) {
        requireNonNull(index);
        requireNonNull(applicationEvent);
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
        return new CommandResult("Interview updated for: " + editedApp.getCompany());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof InterviewCommand)) {
            return false;
        }
        InterviewCommand otherInterviewCommand = (InterviewCommand) other;
        return index.equals(otherInterviewCommand.index)
                && applicationEvent.equals(otherInterviewCommand.applicationEvent);
    }
}
