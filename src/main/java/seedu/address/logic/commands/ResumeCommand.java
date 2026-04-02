package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.application.Application;
import seedu.address.model.application.Resume;

/**
 * Attaches a resume to an application identified by the index number used in the displayed application list.
 */
public class ResumeCommand extends Command {

    public static final String COMMAND_WORD = "resume";

    public static final String MESSAGE_USAGE =
            "resume: Attaches a resume file path to the application.\n"
                    + "Parameters: INDEX rp/FILE_PATH\n"
                    + "Example: resume 1 rp/path/to/resume.pdf";

    public static final String MESSAGE_SUCCESS = "Attached resume to application: %1$s";
    public static final String MESSAGE_FILE_NOT_FOUND =
            "Resume file not found. Please ensure the file exists and the path is correct.\n"
                    + MESSAGE_USAGE;

    public static final String MESSAGE_INVALID_PATH =
            "Invalid resume file path. Please check your path format.\n"
                    + MESSAGE_USAGE;

    private final Index index;
    private final Resume resume;

    /**
     * Creates a ResumeCommand.
     *
     * @param index Index of the application to create resume
     * @param resume The resume
     */
    public ResumeCommand(Index index, Resume resume) {
        requireNonNull(index);
        requireNonNull(resume);
        this.index = index;
        this.resume = resume;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Application> lastShownList = model.getFilteredApplicationList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_APPLICATION_DISPLAYED_INDEX);
        }

        Application applicationToEdit = lastShownList.get(index.getZeroBased());

        try {
            Path resumePath = Path.of(resume.value);

            if (!Files.exists(resumePath) || !Files.isRegularFile(resumePath)) {
                throw new CommandException(MESSAGE_FILE_NOT_FOUND);
            }

        } catch (InvalidPathException e) {
            throw new CommandException(MESSAGE_INVALID_PATH);
        }

        Application editedApplication = new Application(
                applicationToEdit.getRole(),
                applicationToEdit.getPhone(),
                applicationToEdit.getHrEmail(),
                applicationToEdit.getCompany(),
                applicationToEdit.getTags(),
                applicationToEdit.getStatus(),
                applicationToEdit.getDeadline(),
                applicationToEdit.getApplicationEvent(),
                applicationToEdit.getNote(),
                resume
        );

        model.setApplication(applicationToEdit, editedApplication);
        model.commitAddressBook();

        return new CommandResult(
                String.format(MESSAGE_SUCCESS, editedApplication)
        );
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ResumeCommand)) {
            return false;
        }

        ResumeCommand otherResumeCommand = (ResumeCommand) other;
        return index.equals(otherResumeCommand.index)
                && resume.equals(otherResumeCommand.resume);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("resume", resume)
                .toString();
    }
}
