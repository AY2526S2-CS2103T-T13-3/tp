package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.COMPANY_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.COMPANY_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.HREMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.HREMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_COMPANY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_HREMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ROLE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.ROLE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ROLE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HREMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HREMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalApplications.AMY;
import static seedu.address.testutil.TypicalApplications.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.model.application.Application;
import seedu.address.model.application.Company;
import seedu.address.model.application.HrEmail;
import seedu.address.model.application.Phone;
import seedu.address.model.application.Role;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.ApplicationBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Application expectedApplication = new ApplicationBuilder(BOB).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + ROLE_DESC_BOB + PHONE_DESC_BOB + HREMAIL_DESC_BOB
                + COMPANY_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedApplication));


        // multiple tags - all accepted
        Application expectedApplicationMultipleTags = new ApplicationBuilder(BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        assertParseSuccess(parser,
                ROLE_DESC_BOB + PHONE_DESC_BOB + HREMAIL_DESC_BOB + COMPANY_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                new AddCommand(expectedApplicationMultipleTags));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedApplicationString = ROLE_DESC_BOB + PHONE_DESC_BOB + HREMAIL_DESC_BOB
                + COMPANY_DESC_BOB + TAG_DESC_FRIEND;

        // multiple roles
        assertParseFailure(parser, ROLE_DESC_AMY + validExpectedApplicationString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ROLE));

        // multiple phones
        assertParseFailure(parser, PHONE_DESC_AMY + validExpectedApplicationString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple hrEmails
        assertParseFailure(parser, HREMAIL_DESC_AMY + validExpectedApplicationString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_HREMAIL));

        // multiple companyes
        assertParseFailure(parser, COMPANY_DESC_AMY + validExpectedApplicationString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_COMPANY));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedApplicationString + PHONE_DESC_AMY + HREMAIL_DESC_AMY + ROLE_DESC_AMY + COMPANY_DESC_AMY
                        + validExpectedApplicationString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ROLE, PREFIX_COMPANY, PREFIX_HREMAIL, PREFIX_PHONE));

        // invalid value followed by valid value

        // invalid role
        assertParseFailure(parser, INVALID_ROLE_DESC + validExpectedApplicationString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ROLE));

        // invalid hrEmail
        assertParseFailure(parser, INVALID_HREMAIL_DESC + validExpectedApplicationString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_HREMAIL));

        // invalid phone
        assertParseFailure(parser, INVALID_PHONE_DESC + validExpectedApplicationString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid company
        assertParseFailure(parser, INVALID_COMPANY_DESC + validExpectedApplicationString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_COMPANY));

        // valid value followed by invalid value

        // invalid role
        assertParseFailure(parser, validExpectedApplicationString + INVALID_ROLE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ROLE));

        // invalid hrEmail
        assertParseFailure(parser, validExpectedApplicationString + INVALID_HREMAIL_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_HREMAIL));

        // invalid phone
        assertParseFailure(parser, validExpectedApplicationString + INVALID_PHONE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid company
        assertParseFailure(parser, validExpectedApplicationString + INVALID_COMPANY_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_COMPANY));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Application expectedApplication = new ApplicationBuilder(AMY).withTags().build();
        assertParseSuccess(parser, ROLE_DESC_AMY + PHONE_DESC_AMY + HREMAIL_DESC_AMY + COMPANY_DESC_AMY,
                new AddCommand(expectedApplication));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing role prefix
        assertParseFailure(parser, VALID_ROLE_BOB + PHONE_DESC_BOB + HREMAIL_DESC_BOB + COMPANY_DESC_BOB,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, ROLE_DESC_BOB + VALID_PHONE_BOB + HREMAIL_DESC_BOB + COMPANY_DESC_BOB,
                expectedMessage);

        // missing hrEmail prefix
        assertParseFailure(parser, ROLE_DESC_BOB + PHONE_DESC_BOB + VALID_HREMAIL_BOB + COMPANY_DESC_BOB,
                expectedMessage);

        // missing company prefix
        assertParseFailure(parser, ROLE_DESC_BOB + PHONE_DESC_BOB + HREMAIL_DESC_BOB + VALID_COMPANY_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_ROLE_BOB + VALID_PHONE_BOB + VALID_HREMAIL_BOB + VALID_COMPANY_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid role
        assertParseFailure(parser, INVALID_ROLE_DESC + PHONE_DESC_BOB + HREMAIL_DESC_BOB + COMPANY_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Role.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, ROLE_DESC_BOB + INVALID_PHONE_DESC + HREMAIL_DESC_BOB + COMPANY_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Phone.MESSAGE_CONSTRAINTS);

        // invalid hrEmail
        assertParseFailure(parser, ROLE_DESC_BOB + PHONE_DESC_BOB + INVALID_HREMAIL_DESC + COMPANY_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, HrEmail.MESSAGE_CONSTRAINTS);

        // invalid company
        assertParseFailure(parser, ROLE_DESC_BOB + PHONE_DESC_BOB + HREMAIL_DESC_BOB + INVALID_COMPANY_DESC
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Company.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, ROLE_DESC_BOB + PHONE_DESC_BOB + HREMAIL_DESC_BOB + COMPANY_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_ROLE_DESC + PHONE_DESC_BOB + HREMAIL_DESC_BOB + INVALID_COMPANY_DESC,
                seedu.address.model.application.Role.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + ROLE_DESC_BOB + PHONE_DESC_BOB + HREMAIL_DESC_BOB
                        + COMPANY_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
