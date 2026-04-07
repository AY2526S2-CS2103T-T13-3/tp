package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindNoteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.application.NoteContainsKeywordsPredicate;

public class FindNoteCommandParserTest {

    private final FindNoteCommandParser parser = new FindNoteCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        // EP: empty / blank input
        // Boundary value: whitespace-only input
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindNoteCommand.MESSAGE_USAGE), ()
                        -> parser.parse("   "));
    }

    @Test
    public void parse_singleKeyword_returnsFindNoteCommand() throws Exception {
        // EP: single valid keyword -> returns FindNoteCommand
        FindNoteCommand expectedFindNoteCommand =
                new FindNoteCommand(new NoteContainsKeywordsPredicate(Collections.singletonList("follow")));

        assertEquals(expectedFindNoteCommand, parser.parse(" follow"));
    }

    @Test
    public void parse_multipleKeywords_returnsFindNoteCommand() throws Exception {
        // EP: multiple valid keywords -> returns FindNoteCommand with all keywords
        FindNoteCommand expectedFindNoteCommand =
                new FindNoteCommand(new NoteContainsKeywordsPredicate(Arrays.asList("follow", "recruiter")));

        assertEquals(expectedFindNoteCommand, parser.parse(" follow recruiter"));
    }

    @Test
    public void parse_extraWhitespace_returnsFindNoteCommand() throws Exception {
        // EP: extra whitespace between keywords is ignored
        FindNoteCommand expectedFindNoteCommand =
                new FindNoteCommand(new NoteContainsKeywordsPredicate(Arrays.asList("follow", "recruiter", "Monday")));

        assertEquals(expectedFindNoteCommand, parser.parse("   follow    recruiter   Monday   "));
    }

    @Test
    public void parse_mixedCaseKeywords_returnsFindNoteCommand() throws Exception {
        // EP: parser preserves mixed-case keywords as individual tokens
        FindNoteCommand expectedFindNoteCommand =
                new FindNoteCommand(new NoteContainsKeywordsPredicate(Arrays.asList("Follow", "ReCrUiTeR")));

        assertEquals(expectedFindNoteCommand, parser.parse(" Follow ReCrUiTeR"));
    }

    @Test
    public void parse_manyKeywords_returnsFindNoteCommand() throws Exception {
        // EP: multiple keywords of larger input size are parsed correctly
        FindNoteCommand expectedFindNoteCommand =
                new FindNoteCommand(new NoteContainsKeywordsPredicate(
                        Arrays.asList("follow", "recruiter", "Monday", "email", "offer")));

        assertEquals(expectedFindNoteCommand, parser.parse(" follow recruiter Monday email offer"));
    }

    @Test
    public void parse_keywordWithPunctuation_returnsFindNoteCommand() throws Exception {
        // EP: keyword containing punctuation is still parsed as one token if no whitespace splits it
        FindNoteCommand expectedFindNoteCommand =
                new FindNoteCommand(new NoteContainsKeywordsPredicate(Arrays.asList("follow-up", "recruiter")));

        assertEquals(expectedFindNoteCommand, parser.parse(" follow-up recruiter"));
    }

    @Test
    public void parse_leadingAndTrailingWhitespaceOnlyWithKeyword_returnsFindNoteCommand() throws Exception {
        // EP: leading and trailing whitespace do not affect parsing
        FindNoteCommand expectedFindNoteCommand =
                new FindNoteCommand(new NoteContainsKeywordsPredicate(Collections.singletonList("recruiter")));

        assertEquals(expectedFindNoteCommand, parser.parse("    recruiter    "));
    }
}
