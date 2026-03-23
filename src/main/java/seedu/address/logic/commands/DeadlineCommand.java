package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import java.util.List;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.application.Application;
import seedu.address.model.application.Deadline;

public class DeadlineCommand extends Command {
    public static final String COMMAND_WORD = "deadline";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets the deadline for an application.\n"
            + "Parameters: INDEX (must be a positive integer) DATE_TIME\n"
            + "Example: " + COMMAND_WORD + " 1 2026-12-31 23:59";

    private final Index index;
    private final Deadline deadline;

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
                appToEdit.getCompany(), appToEdit.getTags(), appToEdit.getStatus(), deadline);

        model.setApplication(appToEdit, editedApp);
        return new CommandResult("Deadline updated for: " + editedApp.getCompany());
    }
}