package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HREMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.application.Application;
import seedu.address.model.application.RoleContainsKeywordsPredicate;
import seedu.address.model.application.Status;
import seedu.address.testutil.EditApplicationDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_ROLE_AMY = "Amy Bee";
    public static final String VALID_ROLE_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_HREMAIL_AMY = "amy@example.com";
    public static final String VALID_HREMAIL_BOB = "bob@example.com";
    public static final String VALID_COMPANY_NAME_AMY = "Google";
    public static final String VALID_COMPANY_NAME_BOB = "Meta";
    public static final String VALID_COMPANY_LOCATION_AMY = "Singapore";
    public static final String VALID_COMPANY_LOCATION_BOB = "Menlo Park";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final Status VALID_STATUS_OFFERED = Status.OFFERED;

    public static final String ROLE_DESC_AMY = " " + PREFIX_ROLE + VALID_ROLE_AMY;
    public static final String ROLE_DESC_BOB = " " + PREFIX_ROLE + VALID_ROLE_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String HREMAIL_DESC_AMY = " " + PREFIX_HREMAIL + VALID_HREMAIL_AMY;
    public static final String HREMAIL_DESC_BOB = " " + PREFIX_HREMAIL + VALID_HREMAIL_BOB;
    public static final String COMPANY_NAME_DESC_AMY = " " + PREFIX_COMPANY_NAME + VALID_COMPANY_NAME_AMY;
    public static final String COMPANY_NAME_DESC_BOB = " " + PREFIX_COMPANY_NAME + VALID_COMPANY_NAME_BOB;
    public static final String COMPANY_LOCATION_DESC_AMY = " " + PREFIX_COMPANY_LOCATION + VALID_COMPANY_LOCATION_AMY;
    public static final String COMPANY_LOCATION_DESC_BOB = " " + PREFIX_COMPANY_LOCATION + VALID_COMPANY_LOCATION_BOB;
    // Backward-compatible aliases for older tests expecting a single company prefix/value.
    public static final String COMPANY_DESC_AMY = COMPANY_NAME_DESC_AMY;
    public static final String COMPANY_DESC_BOB = COMPANY_NAME_DESC_BOB;
    // Backward-compatible aliases for older tests expecting company as a single raw string.
    public static final String VALID_COMPANY_AMY = VALID_COMPANY_NAME_AMY;
    public static final String VALID_COMPANY_BOB = VALID_COMPANY_NAME_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String INVALID_ROLE_DESC = " " + PREFIX_ROLE + "James&"; // '&' not allowed in roles
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_HREMAIL_DESC = " " + PREFIX_HREMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_COMPANY_DESC = " " + PREFIX_COMPANY_NAME;
    // empty string not allowed for company names
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditApplicationDescriptor DESC_AMY;
    public static final EditCommand.EditApplicationDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditApplicationDescriptorBuilder().withRole(VALID_ROLE_AMY)
                .withPhone(VALID_PHONE_AMY).withHrEmail(VALID_HREMAIL_AMY)
                .withCompanyName(VALID_COMPANY_NAME_AMY)
                .withCompanyLocation(VALID_COMPANY_LOCATION_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditApplicationDescriptorBuilder().withRole(VALID_ROLE_BOB)
                .withPhone(VALID_PHONE_BOB).withHrEmail(VALID_HREMAIL_BOB)
                .withCompanyName(VALID_COMPANY_NAME_BOB)
                .withCompanyLocation(VALID_COMPANY_LOCATION_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
                                            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(new AddressBook(expectedModel.getAddressBook()),
                    new AddressBook(actualModel.getAddressBook()));
            assertEquals(expectedModel.getFilteredApplicationList(), actualModel.getFilteredApplicationList());
            assertEquals(expectedModel.getUserPrefs(), actualModel.getUserPrefs());
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
                                            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the company book, filtered application list and selected application in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Application> expectedFilteredList = new ArrayList<>(actualModel.getFilteredApplicationList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredApplicationList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the application at the given {@code targetIndex} in the
     * {@code model}'s company book.
     */
    public static void showApplicationAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredApplicationList().size());

        Application application = model.getFilteredApplicationList().get(targetIndex.getZeroBased());
        final String[] splitRole = application.getRole().roleName.split("\\s+");
        model.updateFilteredApplicationList(new RoleContainsKeywordsPredicate(Arrays.asList(splitRole[0])));

        assertEquals(1, model.getFilteredApplicationList().size());
    }

}
