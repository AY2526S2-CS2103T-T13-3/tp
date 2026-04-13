package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.application.Application;
import seedu.address.model.application.Deadline;

/**
 * Sets or updates the deadline for an application in the address book.
 */
public class DeadlineCommand extends Command {
    public static final String COMMAND_WORD = "deadline";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets the deadline for an application.\n"
            + "Example: " + COMMAND_WORD + " 1 2026-12-31 23:59\n"
            + "Parameters: INDEX (must be a positive integer) DATE_TIME\n";

    private final Index index;
    private final Deadline deadline;

    /**
     * Creates a {@code DeadlineCommand} to update the deadline of the application at the given index.
     *
     * @param index the index of the application in the displayed application list.
     * @param deadline the new deadline.
     */
    public DeadlineCommand(Index index, Deadline deadline) {
        this.index = index;
        this.deadline = deadline;
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
                appToEdit.getCompany(), appToEdit.getTags(), appToEdit.getStatus(), deadline,
                appToEdit.getApplicationEvent(), appToEdit.getNote());

        model.setApplication(appToEdit, editedApp);
        model.commitAddressBook();
        return new CommandResult("Deadline updated for: " + editedApp.getCompany());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeadlineCommand)) {
            return false;
        }

        DeadlineCommand otherDeadlineCommand = (DeadlineCommand) other;
        return index.equals(otherDeadlineCommand.index)
                && deadline.equals(otherDeadlineCommand.deadline);
    }

    @Override
    public int hashCode() {
        return Objects.hash(index, deadline);
    }
}
