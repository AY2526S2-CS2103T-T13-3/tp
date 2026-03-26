package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ResumeCommand;
import seedu.address.model.application.Resume;

public class ResumeCommandParserTest {

    private final ResumeCommandParser parser = new ResumeCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        assertParseSuccess(parser, "1 rp/resume.pdf",
                new ResumeCommand(Index.fromOneBased(1), new Resume("resume.pdf")));
    }

    @Test
    public void parse_missingIndex_failure() {
        assertParseFailure(parser, "rp/resume.pdf",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ResumeCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingPrefix_failure() {
        assertParseFailure(parser, "1 resume.pdf",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ResumeCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidResume_failure() {
        assertParseFailure(parser, "1 rp/resume.txt", Resume.MESSAGE_CONSTRAINTS);
    }
}