package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SortCommand;

public class SortCommandParserTest {

    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_validArgs_returnsSortCommand() {
        // Test the alphabetic sort input
        assertParseSuccess(parser, "alphabet", new SortCommand("alphabet"));
        assertParseSuccess(parser, "  ALPHABET  ", new SortCommand("alphabet"));

        // Test time sorting input
        assertParseSuccess(parser, "time", new SortCommand("time"));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Test for empty input or illegal criteria
        assertParseFailure(parser, "    ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "invalidCriteria",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }
}
