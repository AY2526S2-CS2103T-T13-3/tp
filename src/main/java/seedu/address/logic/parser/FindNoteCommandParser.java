package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindNoteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.application.NoteContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindNoteCommand object.
 */
public class FindNoteCommandParser implements Parser<FindNoteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindNoteCommand
     * and returns a FindNoteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindNoteCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindNoteCommand.MESSAGE_USAGE));
        }

        String[] noteKeywords = trimmedArgs.split("\\s+");
        return new FindNoteCommand(new NoteContainsKeywordsPredicate(Arrays.asList(noteKeywords)));
    }
}
