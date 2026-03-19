package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_APPLICATION;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.StatusCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.application.Status;

class StatusCommandParserTest {

    private final StatusCommandParser parser = new StatusCommandParser();

    @Test
    public void parse_validArgs_returnsStatusCommand() throws Exception {
        StatusCommand command = parser.parse(" 1 " + PREFIX_STATUS + "OFFERED");
        assertEquals(new StatusCommand(INDEX_FIRST_APPLICATION, Status.OFFERED), command);
    }

    @Test
    public void parse_validArgsCaseInsensitive_returnsStatusCommand() throws Exception {
        // lowercase status
        StatusCommand lowerCaseCommand = parser.parse(" 1 " + PREFIX_STATUS + "offered");
        assertEquals(new StatusCommand(INDEX_FIRST_APPLICATION, Status.OFFERED), lowerCaseCommand);

        // mixed case status
        StatusCommand mixedCaseCommand = parser.parse(" 1 " + PREFIX_STATUS + "Offered");
        assertEquals(new StatusCommand(INDEX_FIRST_APPLICATION, Status.OFFERED), mixedCaseCommand);
    }

    @Test
    public void parse_allValidStatuses_success() throws Exception {
        for (Status status : Status.values()) {
            StatusCommand command = parser.parse(" 1 " + PREFIX_STATUS + status.name());
            assertEquals(new StatusCommand(INDEX_FIRST_APPLICATION, status), command);
        }
    }

    @Test
    public void parse_missingIndex_throwsParseException() {
        // no index, only status prefix
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatusCommand.MESSAGE_USAGE), ()
                        -> parser.parse(" " + PREFIX_STATUS + "OFFERED"));
    }

    @Test
    public void parse_missingStatusPrefix_throwsParseException() {
        // index present but no s/ prefix
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatusCommand.MESSAGE_USAGE), ()
                        -> parser.parse(" 1 OFFERED"));
    }

    @Test
    public void parse_missingAllArgs_throwsParseException() {
        // completely empty args
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatusCommand.MESSAGE_USAGE), ()
                        -> parser.parse(""));
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        // non-integer index
        assertThrows(ParseException.class, () -> parser.parse(" abc " + PREFIX_STATUS + "OFFERED"));

        // zero index (not positive)
        assertThrows(ParseException.class, () -> parser.parse(" 0 " + PREFIX_STATUS + "OFFERED"));

        // negative index
        assertThrows(ParseException.class, () -> parser.parse(" -1 " + PREFIX_STATUS + "OFFERED"));
    }

    @Test
    public void parse_invalidStatus_throwsParseException() {
        // completely invalid status value
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatusCommand.MESSAGE_USAGE), ()
                        -> parser.parse(" 1 " + PREFIX_STATUS + "INVALID_STATUS"));

        // empty status value
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatusCommand.MESSAGE_USAGE), ()
                        -> parser.parse(" 1 " + PREFIX_STATUS));
    }

    @Test
    public void parse_multipleStatusPrefixes_usesLastValue() throws Exception {
        // when duplicate prefixes provided, last value should be used (per ArgumentMultimap behaviour)
        StatusCommand command = parser.parse(
                " 1 " + PREFIX_STATUS + "APPLIED " + PREFIX_STATUS + "OFFERED");
        assertEquals(new StatusCommand(INDEX_FIRST_APPLICATION, Status.OFFERED), command);
    }

    @Test
    public void parse_extraWhitespace_success() throws Exception {
        // extra leading/trailing/middle whitespace should still parse correctly
        StatusCommand command = parser.parse("   1   " + PREFIX_STATUS + "  OFFERED  ");
        assertEquals(new StatusCommand(INDEX_FIRST_APPLICATION, Status.OFFERED), command);
    }

    @Test
    public void parse_differentValidIndex_success() throws Exception {
        Index secondIndex = Index.fromOneBased(2);
        StatusCommand command = parser.parse(" 2 " + PREFIX_STATUS + "INTERVIEWING");
        assertEquals(new StatusCommand(secondIndex, Status.INTERVIEWING), command);
    }
}
