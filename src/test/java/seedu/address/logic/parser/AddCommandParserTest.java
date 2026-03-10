package seedu.company.logic.parser;

import static seedu.company.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.company.logic.commands.CommandTestUtil.COMPANY_DESC_AMY;
import static seedu.company.logic.commands.CommandTestUtil.COMPANY_DESC_BOB;
import static seedu.company.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.company.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.company.logic.commands.CommandTestUtil.INVALID_COMPANY_DESC;
import static seedu.company.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.company.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.company.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.company.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.company.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.company.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.company.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.company.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.company.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.company.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.company.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.company.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.company.logic.commands.CommandTestUtil.VALID_COMPANY_BOB;
import static seedu.company.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.company.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.company.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.company.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.company.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.company.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.company.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.company.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.company.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.company.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.company.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.company.testutil.TypicalPersons.AMY;
import static seedu.company.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.company.logic.Messages;
import seedu.company.logic.commands.AddCommand;
import seedu.company.model.application.Company;
import seedu.company.model.application.HrEmail;
import seedu.company.model.application.Application;
import seedu.company.model.application.Phone;
import seedu.company.model.application.Role;
import seedu.company.model.tag.Tag;
import seedu.company.testutil.PersonBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Application expectedApplication = new PersonBuilder(BOB).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + COMPANY_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedApplication));


        // multiple tags - all accepted
        Application expectedApplicationMultipleTags = new PersonBuilder(BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        assertParseSuccess(parser,
                NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + COMPANY_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                new AddCommand(expectedApplicationMultipleTags));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedPersonString = NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + COMPANY_DESC_BOB + TAG_DESC_FRIEND;

        // multiple names
        assertParseFailure(parser, NAME_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple phones
        assertParseFailure(parser, PHONE_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple emails
        assertParseFailure(parser, EMAIL_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // multiple companyes
        assertParseFailure(parser, COMPANY_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_COMPANY));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedPersonString + PHONE_DESC_AMY + EMAIL_DESC_AMY + NAME_DESC_AMY + COMPANY_DESC_AMY
                        + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_COMPANY, PREFIX_EMAIL, PREFIX_PHONE));

        // invalid value followed by valid value

        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, INVALID_EMAIL_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, INVALID_PHONE_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid company
        assertParseFailure(parser, INVALID_COMPANY_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_COMPANY));

        // valid value followed by invalid value

        // invalid name
        assertParseFailure(parser, validExpectedPersonString + INVALID_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, validExpectedPersonString + INVALID_EMAIL_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, validExpectedPersonString + INVALID_PHONE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid company
        assertParseFailure(parser, validExpectedPersonString + INVALID_COMPANY_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_COMPANY));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Application expectedApplication = new PersonBuilder(AMY).withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + COMPANY_DESC_AMY,
                new AddCommand(expectedApplication));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + COMPANY_DESC_BOB,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB + COMPANY_DESC_BOB,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB + COMPANY_DESC_BOB,
                expectedMessage);

        // missing company prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + VALID_COMPANY_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB + VALID_COMPANY_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + COMPANY_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Role.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB + COMPANY_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC + COMPANY_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, HrEmail.MESSAGE_CONSTRAINTS);

        // invalid company
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_COMPANY_DESC
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Company.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + COMPANY_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_COMPANY_DESC,
                seedu.company.model.application.Role.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + COMPANY_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
