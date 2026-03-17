package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HREMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand.EditApplicationDescriptor;
import seedu.address.model.application.Application;
import seedu.address.model.tag.Tag;

/**
 * A utility class for Application.
 */
public class ApplicationUtil {

    /**
     * Returns an add command string for adding the {@code application}.
     */
    public static String getAddCommand(Application application) {
        return AddCommand.COMMAND_WORD + " " + getApplicationDetails(application);
    }

    /**
     * Returns the part of command string for the given {@code application}'s details.
     */
    public static String getApplicationDetails(Application application) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_ROLE + application.getRole().roleName + " ");
        sb.append(PREFIX_PHONE + application.getPhone().value + " ");
        sb.append(PREFIX_HREMAIL + application.getHrEmail().value + " ");
        sb.append(PREFIX_COMPANY_NAME + application.getCompany().companyName + " ");
        // Note: AddCommandParser currently does not tokenize PREFIX_COMPANY_LOCATION.
        // Keep add-command details aligned with parser behavior.
        application.getTags().stream().forEach(
                s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditApplicationDescriptor}'s details.
     */
    public static String getEditApplicationDescriptorDetails(EditApplicationDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getRole().ifPresent(role -> sb.append(PREFIX_ROLE).append(role.roleName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getHrEmail().ifPresent(hrEmail -> sb.append(PREFIX_HREMAIL).append(hrEmail.value).append(" "));
        descriptor.getCompanyName().ifPresent(name ->
                sb.append(PREFIX_COMPANY_NAME).append(name).append(" "));
        descriptor.getCompanyLocation().ifPresent(location ->
                sb.append(PREFIX_COMPANY_LOCATION).append(location).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG).append(" ");
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }

        descriptor.getStatus().ifPresent(s -> sb.append(PREFIX_STATUS).append(s.name()).append(" "));
        return sb.toString();
    }
}
