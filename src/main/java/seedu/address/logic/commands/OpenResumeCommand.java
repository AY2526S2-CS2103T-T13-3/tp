package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_APPLICATION_DISPLAYED_INDEX;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.application.Application;

/**
 * Opens the resume attached to an application identified by the index number used in the displayed application list.
 */
public class OpenResumeCommand extends Command {

    public static final String COMMAND_WORD = "openresume";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Opens the attached resume of the application identified by the index number used in the "
            + "displayed application list.\n"
            + "Parameters: INDEX\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_NO_RESUME = "This application does not have a resume attached.";
    public static final String MESSAGE_FILE_NOT_FOUND = "The linked resume file cannot be found.";
    public static final String MESSAGE_OPEN_FAILED = "Failed to open the resume file.";
    public static final String MESSAGE_DESKTOP_NOT_SUPPORTED =
            "Opening files is not supported on this system.";
    public static final String MESSAGE_SUCCESS = "Opened resume for application: %1$s";

    private final Index index;

    /**
     * Create an OpenResumeCommand.
     *
     * @param index Index of the application to open resume
     */
    public OpenResumeCommand(Index index) {
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

        Application applicationToOpen = lastShownList.get(index.getZeroBased());

        if (!applicationToOpen.hasResume()) {
            throw new CommandException(MESSAGE_NO_RESUME);
        }

        File resumeFile = new File(applicationToOpen.getResume().value);

        if (!resumeFile.exists() || !resumeFile.isFile()) {
            throw new CommandException(MESSAGE_FILE_NOT_FOUND);
        }

        if (!Desktop.isDesktopSupported()) {
            throw new CommandException(MESSAGE_DESKTOP_NOT_SUPPORTED);
        }

        try {
            Desktop.getDesktop().open(resumeFile);
        } catch (IOException e) {
            throw new CommandException(MESSAGE_OPEN_FAILED);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, applicationToOpen));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof OpenResumeCommand)) {
            return false;
        }

        OpenResumeCommand otherCommand = (OpenResumeCommand) other;
        return index.equals(otherCommand.index);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .toString();
    }
}
