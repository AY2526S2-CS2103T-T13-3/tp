package seedu.company.testutil;

import static seedu.company.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.company.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.company.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.company.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.company.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.company.logic.commands.AddCommand;
import seedu.company.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.company.model.application.Application;
import seedu.company.model.tag.Tag;

/**
 * A utility class for Person.
 */
public class PersonUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddCommand(Application application) {
        return AddCommand.COMMAND_WORD + " " + getPersonDetails(application);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getPersonDetails(Application application) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + application.getName().fullName + " ");
        sb.append(PREFIX_PHONE + application.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + application.getEmail().value + " ");
        sb.append(PREFIX_COMPANY + application.getCompany().value + " ");
        application.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditPersonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getCompany().ifPresent(company -> sb.append(PREFIX_COMPANY).append(company.value).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
