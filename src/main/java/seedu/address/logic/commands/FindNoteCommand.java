package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.application.NoteContainsKeywordsPredicate;

/**
 * Finds and lists all applications whose notes contain any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FindNoteCommand extends Command {

    public static final String COMMAND_WORD = "findnote";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all applications whose notes contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " recruiter followup";

    private final NoteContainsKeywordsPredicate predicate;

    public FindNoteCommand(NoteContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredApplicationList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_APPLICATIONS_LISTED_OVERVIEW,
                        model.getFilteredApplicationList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof FindNoteCommand)) {
            return false;
        }

        FindNoteCommand otherFindNoteCommand = (FindNoteCommand) other;
        return predicate.equals(otherFindNoteCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
