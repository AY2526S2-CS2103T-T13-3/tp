package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.application.Application;

/**
 * Sorts applications by a specified criteria (timeline or role alphabetically).
 */
public class SortCommand extends Command {
    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + "Example: " + COMMAND_WORD + " time or " + COMMAND_WORD + " alphabet"
            + ": Sorts the list by timeline or role alphabet.\n"
            + "Parameters: field (must be 'time' or 'alphabet')\n";

    public static final String MESSAGE_SUCCESS = "Sorted by %1$s";

    private final String criteria;

    public SortCommand(String criteria) {
        this.criteria = criteria;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Comparator<Application> comparator;

        if (criteria.equalsIgnoreCase("alphabet")) {
            // A-Z
            comparator = (a1, a2) -> a1.getRole().roleName
                    .compareToIgnoreCase(a2.getRole().roleName);
        } else if (criteria.equalsIgnoreCase("time")) {
            // sort by date
            comparator = (a1, a2) -> a1.getDeadline().compareTo(a2.getDeadline());
        } else {
            throw new CommandException("Unknown criteria! Use 'time' or 'alphabet'.");
        }

        model.updateSortedApplicationList(comparator);
        model.commitAddressBook();

        return new CommandResult(String.format(MESSAGE_SUCCESS, criteria));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof SortCommand)) {
            return false;
        }

        SortCommand otherSortCommand = (SortCommand) other;
        return criteria.equalsIgnoreCase(otherSortCommand.criteria);
    }

    @Override
    public int hashCode() {
        return criteria.toLowerCase().hashCode();
    }
}
