package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_APPLICATION_DISPLAYED_INDEX;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.application.Application;
import seedu.address.model.application.Resume;

/**
 * Removes the resume from an application identified by the index number used in the displayed application list.
 */
public class RemoveResumeCommand extends Command {

    public static final String COMMAND_WORD = "removeresume";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes the attached resume from the application identified by the index number used in the "
            + "displayed application list.\n"
            + "Parameters: INDEX\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Removed resume from application: %1$s";

    private final Index index;

    /**
     * Create an RemoveResumeCommand.
     *
     * @param index Index of the application to remove resume
     */
    public RemoveResumeCommand(Index index) {
        requireNonNull(index);
        this.index = index;
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
                applicationToEdit.getStatus(),
                applicationToEdit.getDeadline(),
                applicationToEdit.getApplicationEvent(),
                applicationToEdit.getNote(),
                Resume.getEmptyResume());

        model.setApplication(applicationToEdit, updatedApplication);
        model.commitAddressBook();

        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(updatedApplication)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof RemoveResumeCommand)) {
            return false;
        }

        RemoveResumeCommand otherCommand = (RemoveResumeCommand) other;
        return index.equals(otherCommand.index);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .toString();
    }
}
