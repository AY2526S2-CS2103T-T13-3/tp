package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindNoteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.application.NoteContainsKeywordsPredicate;

public class FindNoteCommandParserTest {

    private FindNoteCommandParser parser = new FindNoteCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindNoteCommand.MESSAGE_USAGE), ()
                        -> parser.parse("   "));
    }

    @Test
    public void parse_validArgs_returnsFindNoteCommand() throws Exception {
        FindNoteCommand expectedFindNoteCommand =
                new FindNoteCommand(new NoteContainsKeywordsPredicate(Arrays.asList("follow", "recruiter")));

        assertEquals(expectedFindNoteCommand, parser.parse(" follow recruiter"));
    }
}
