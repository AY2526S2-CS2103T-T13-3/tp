package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RESUME;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ResumeCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.application.Resume;

/**
 * Parses input arguments and creates a new ResumeCommand object.
 */
public class ResumeCommandParser implements Parser<ResumeCommand> {

    @Override
    public ResumeCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_RESUME);

        if (argMultimap.getPreamble().isEmpty() || !arePrefixesPresent(argMultimap, PREFIX_RESUME)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ResumeCommand.MESSAGE_USAGE));
        }

        Index index = ParserUtil.parseIndex(argMultimap.getPreamble());
        Resume resume = ParserUtil.parseResume(argMultimap.getValue(PREFIX_RESUME).get());

        return new ResumeCommand(index, resume);
    }

    /**
     * Returns true if all prefixes contain non-empty values.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
