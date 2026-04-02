package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_APPLICATION;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeadlineCommand;
import seedu.address.model.application.Deadline;

public class DeadlineCommandParserTest {
    private DeadlineCommandParser parser = new DeadlineCommandParser();
    private final String nonEmptyDeadline = "2026-12-31";

    @Test
    public void parse_indexSpecified_success() {
        // Normal input format: deadline 1 2026-12-31
        String userInput = INDEX_FIRST_APPLICATION.getOneBased() + " " + nonEmptyDeadline;
        DeadlineCommand expectedCommand = new DeadlineCommand(INDEX_FIRST_APPLICATION, new Deadline(nonEmptyDeadline));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingParts_failure() {
        // Missing date
        assertParseFailure(parser, "1", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeadlineCommand.MESSAGE_USAGE));

        // Missing index
        assertParseFailure(parser, nonEmptyDeadline,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeadlineCommand.MESSAGE_USAGE));

        // Empty
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeadlineCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidIndex_failure() {
        // Negative index
        assertParseFailure(parser, "-5 " + nonEmptyDeadline, ParserUtil.MESSAGE_INVALID_INDEX);

        // 0 index
        assertParseFailure(parser, "0 " + nonEmptyDeadline, ParserUtil.MESSAGE_INVALID_INDEX);

        // Non-digital index
        assertParseFailure(parser, "a " + nonEmptyDeadline, ParserUtil.MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_invalidDeadlineFormat_failure() {
        // Format is wrong (using slashes instead of dashes)
        assertParseFailure(parser, "1 2026/04/01", Deadline.MESSAGE_CONSTRAINTS_FORMAT);
    }

    @Test
    public void parse_invalidDeadlineDate_failure() {
        // Format is correct, but 60th minute does not exist on a clock
        assertParseFailure(parser, "1 2026-04-01 12:60", Deadline.MESSAGE_CONSTRAINTS_DATE);

        // Format is correct, but 90th day does not exist on the calendar
        assertParseFailure(parser, "1 2026-01-90", Deadline.MESSAGE_CONSTRAINTS_DATE);
    }
}
