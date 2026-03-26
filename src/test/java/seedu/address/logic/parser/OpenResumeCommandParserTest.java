package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.OpenResumeCommand;

public class OpenResumeCommandParserTest {

    private final OpenResumeCommandParser parser = new OpenResumeCommandParser();

    @Test
    public void parse_validArgs_success() {
        assertParseSuccess(parser, "1",
                new OpenResumeCommand(Index.fromOneBased(1)));
    }

    @Test
    public void parse_emptyArgs_failure() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, OpenResumeCommand.MESSAGE_USAGE));
    }
}
