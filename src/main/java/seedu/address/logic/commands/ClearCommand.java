package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;

/**
 * Clears Hired!.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Hired! has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setAddressBook(new AddressBook());
        model.commitAddressBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
