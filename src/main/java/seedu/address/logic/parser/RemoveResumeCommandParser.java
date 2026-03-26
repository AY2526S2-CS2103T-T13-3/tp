package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RemoveResumeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new RemoveResumeCommand object.
 */
public class RemoveResumeCommandParser implements Parser<RemoveResumeCommand> {

    @Override
    public RemoveResumeCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    RemoveResumeCommand.MESSAGE_USAGE));
        }

        Index index = ParserUtil.parseIndex(trimmedArgs);
        return new RemoveResumeCommand(index);
    }
}
