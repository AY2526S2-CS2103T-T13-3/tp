package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_APPLICATION_DISPLAYED_INDEX;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.application.Application;
import seedu.address.model.application.Status;

/**
 * Updates the status of an application identified by the index number used in the displayed application list.
 */
public class StatusCommand extends Command {

    public static final String COMMAND_WORD = "status";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Updates the status of the application identified by the index number used in the displayed "
            + "application list.\n"
            + "Parameters: INDEX s/STATUS\n"
            + "Example: " + COMMAND_WORD + " 1 s/OFFERED";

    public static final String VALID_STATUS_LIST = "Input one of the following valid statuses (case-insensitive):\n"
            + "APPLIED\n"
            + "INTERVIEWING\n"
            + "OFFERED\n"
            + "REJECTED\n"
            + "WITHDRAWN";

    public static final String MESSAGE_SUCCESS = "Updated application status: %1$s";

    private final Index index;
    private final Status status;

    /**
     * Creates a StatusCommand to update the status of the application at the specified {@code Index}
     */
    public StatusCommand(Index index, Status status) {
        requireNonNull(index);
        requireNonNull(status);
        this.index = index;
        this.status = status;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Application> lastShownList = model.getFilteredApplicationList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_APPLICATION_DISPLAYED_INDEX);
        }

        Application applicationToEdit = lastShownList.get(index.getZeroBased());

        Application updatedApplication = new Application(
                applicationToEdit.getRole(),
                applicationToEdit.getPhone(),
                applicationToEdit.getHrEmail(),
                applicationToEdit.getCompany(),
                applicationToEdit.getTags(),
                status,
                applicationToEdit.getDeadline(),
                applicationToEdit.getNote()
        );

        model.setApplication(applicationToEdit, updatedApplication);
        model.commitAddressBook();

        return new CommandResult(String.format(MESSAGE_SUCCESS, updatedApplication));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof StatusCommand)) {
            return false;
        }

        StatusCommand otherStatusCommand = (StatusCommand) other;
        return index.equals(otherStatusCommand.index)
                && status.equals(otherStatusCommand.status);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("status", status)
                .toString();
    }
}
