package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.application.Application;

import java.util.Comparator;

import static java.util.Objects.requireNonNull;

public class SortCommand extends Command{
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
            // 按 Role 字母排序（升序）
            comparator = (a1, a2) -> a1.getRole().roleName
                    .compareToIgnoreCase(a2.getRole().roleName);
        } else if (criteria.equalsIgnoreCase("time")) {
            comparator = (a1, a2) -> a1.getDeadline().compareTo(a2.getDeadline());
        } else {
            throw new CommandException("Unknown criteria! Use 'time' or 'alphabet'.");
        }

        model.updateSortedApplicationList(comparator);

        return new CommandResult(String.format(MESSAGE_SUCCESS, criteria));
    }
}
