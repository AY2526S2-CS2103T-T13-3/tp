package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HREMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.application.Application;

/**
 * Adds an application to Hired!.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an application to Hired!.\n"
            + "Format: " + COMMAND_WORD + " "
            + PREFIX_ROLE + "ROLE "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_HREMAIL + "EMAIL "
            + PREFIX_COMPANY_NAME + "COMPANY_NAME "
            + "[" + PREFIX_COMPANY_LOCATION + "COMPANY_LOCATION] "
            + "[" + PREFIX_TAG + "TAG]... "
            + "[" + PREFIX_NOTE + "NOTE]\n"
            + "Required prefixes: " + PREFIX_ROLE + ", " + PREFIX_PHONE + ", "
            + PREFIX_HREMAIL + ", and " + PREFIX_COMPANY_NAME + " must be provided.\n"
            + "Optional prefixes: " + PREFIX_COMPANY_LOCATION + ", " + PREFIX_TAG
            + ", and " + PREFIX_NOTE
            + " may be omitted. If provided, optional prefixes may appear in any order after the required fields.\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ROLE + "Software Engineer "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_HREMAIL + "hr@google.com "
            + PREFIX_COMPANY_NAME + "Google "
            + PREFIX_COMPANY_LOCATION + "Singapore "
            + PREFIX_TAG + "interview "
            + PREFIX_NOTE + "Met recruiter at career fair";

    public static final String MESSAGE_SUCCESS = "New application added: %1$s";
    public static final String MESSAGE_DUPLICATE_APPLICATION = "This application already exists in the Hired!";

    private final Application toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Application}
     */
    public AddCommand(Application application) {
        requireNonNull(application);
        toAdd = application;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasApplication(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_APPLICATION);
        }

        model.addApplication(toAdd);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddCommand)) {
            return false;
        }

        AddCommand otherAddCommand = (AddCommand) other;
        return toAdd.equals(otherAddCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}