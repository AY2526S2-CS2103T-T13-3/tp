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

/**
 * Removes the {@code ApplicationEvent} from an application identified by the
 * index number used in the displayed application list.
 */
public class RemoveEventCommand extends Command {

    public static final String COMMAND_WORD = "removeevent";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes the event (interview / online assessment) from the application identified "
            + "by the index number used in the displayed application list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Removed event from application: %1$s";
    public static final String MESSAGE_NO_EVENT =
            "The application at the specified index does not have an event to remove.";

    private final Index index;

    /**
     * Creates a {@code RemoveEventCommand} that removes the event from the
     * application at the given {@code index}.
     *
     * @param index one-based index of the application whose event is to be removed.
     */
    public RemoveEventCommand(Index index) {
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

        if (applicationToEdit.getApplicationEvent() == null) {
            throw new CommandException(MESSAGE_NO_EVENT);
        }

        Application updatedApplication = new Application(
                applicationToEdit.getRole(),
                applicationToEdit.getPhone(),
                applicationToEdit.getHrEmail(),
                applicationToEdit.getCompany(),
                applicationToEdit.getTags(),
                applicationToEdit.getStatus(),
                applicationToEdit.getDeadline(),
                null,
                applicationToEdit.getNote(),
                applicationToEdit.getResume());

        model.setApplication(applicationToEdit, updatedApplication);
        model.commitAddressBook();

        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(updatedApplication)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof RemoveEventCommand)) {
            return false;
        }
        RemoveEventCommand otherCommand = (RemoveEventCommand) other;
        return index.equals(otherCommand.index);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .toString();
    }
}
