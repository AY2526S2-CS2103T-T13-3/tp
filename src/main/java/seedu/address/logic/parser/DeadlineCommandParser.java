package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeadlineCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.application.Deadline;

/**
 * Parse the input parameters and create a new DeadlineCommand object
 */
public class DeadlineCommandParser implements Parser<DeadlineCommand> {

    /**
     * Parse the given {@code String} parameter.
     * @throws ParseException If the user input does not conform to the expected format
     */
    public DeadlineCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();

        String[] parts = trimmedArgs.split("\\s+", 2);

        if (parts.length < 2) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeadlineCommand.MESSAGE_USAGE));
        }

        // Use ParserUtil to parse the Index: If the index is not valid,
        // directly throw the original ParseException information
        Index index = ParserUtil.parseIndex(parts[0]);
        Deadline deadline = ParserUtil.parseDeadline(parts[1]);

        return new DeadlineCommand(index, deadline);
    }
}
