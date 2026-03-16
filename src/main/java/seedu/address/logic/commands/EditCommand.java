package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HREMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_APPLICATIONS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.application.Application;
import seedu.address.model.application.Company;
import seedu.address.model.application.HrEmail;
import seedu.address.model.application.Phone;
import seedu.address.model.application.Role;
import seedu.address.model.application.Status;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing application in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the application identified "
            + "by the index number used in the displayed application list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_ROLE + "ROLE] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_HREMAIL + "EMAIL] "
            + "[" + PREFIX_COMPANY_NAME + "COMPANY_NAME] "
            + "[" + PREFIX_COMPANY_LOCATION + "COMPANY_LOCATION] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_HREMAIL + "hr@example.com";

    public static final String MESSAGE_EDIT_APPLICATION_SUCCESS = "Edited Application: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_APPLICATION = "This application already exists in the address book.";

    private final Index index;
    private final EditApplicationDescriptor editApplicationDescriptor;

    /**
     * @param index of the application in the filtered application list to edit
     * @param editApplicationDescriptor details to edit the application with
     */
    public EditCommand(Index index, EditApplicationDescriptor editApplicationDescriptor) {
        requireNonNull(index);
        requireNonNull(editApplicationDescriptor);

        this.index = index;
        this.editApplicationDescriptor = new EditApplicationDescriptor(editApplicationDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Application> lastShownList = model.getFilteredApplicationList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_APPLICATION_DISPLAYED_INDEX);
        }

        Application applicationToEdit = lastShownList.get(index.getZeroBased());
        Application editedApplication = createEditedApplication(applicationToEdit, editApplicationDescriptor);

        if (!applicationToEdit.isSameApplication(editedApplication) && model.hasApplication(editedApplication)) {
            throw new CommandException(MESSAGE_DUPLICATE_APPLICATION);
        }

        model.setApplication(applicationToEdit, editedApplication);
        model.updateFilteredApplicationList(PREDICATE_SHOW_ALL_APPLICATIONS);
        return new CommandResult(String.format(MESSAGE_EDIT_APPLICATION_SUCCESS, Messages.format(editedApplication)));
    }

    /**
     * Creates and returns a {@code Application} with the details of {@code applicationToEdit}
     * edited with {@code editApplicationDescriptor}.
     */
    private static Application createEditedApplication(Application applicationToEdit,
                                                       EditApplicationDescriptor editApplicationDescriptor) {
        assert applicationToEdit != null;

        Role updatedRole = editApplicationDescriptor.getRole().orElse(applicationToEdit.getRole());
        Phone updatedPhone = editApplicationDescriptor.getPhone().orElse(applicationToEdit.getPhone());
        HrEmail updatedHrEmail = editApplicationDescriptor.getHrEmail().orElse(applicationToEdit.getHrEmail());

        String updatedName = editApplicationDescriptor.getCompanyName()
                .orElse(applicationToEdit.getCompany().companyName);
        String updatedLocation = editApplicationDescriptor.getCompanyLocation()
                .orElse(applicationToEdit.getCompany().companyLocation);
        Company updatedCompany = new Company(updatedName, updatedLocation);

        Set<Tag> updatedTags = editApplicationDescriptor.getTags().orElse(applicationToEdit.getTags());
        Status updatedStatus = editApplicationDescriptor.getStatus().orElse(applicationToEdit.getStatus());

        return new Application(updatedRole, updatedPhone, updatedHrEmail, updatedCompany, updatedTags, updatedStatus);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof EditCommand)) {
            return false;
        }

        EditCommand otherEditCommand = (EditCommand) other;
        return index.equals(otherEditCommand.index)
                && editApplicationDescriptor.equals(otherEditCommand.editApplicationDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editApplicationDescriptor", editApplicationDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the application with. Each non-empty field value will replace the
     * corresponding field value of the application.
     */
    public static class EditApplicationDescriptor {
        private Role role;
        private Phone phone;
        private HrEmail hrEmail;
        private String companyName;
        private String companyLocation;
        private Set<Tag> tags;
        private Status status;

        public EditApplicationDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditApplicationDescriptor(EditApplicationDescriptor toCopy) {
            setRole(toCopy.role);
            setPhone(toCopy.phone);
            setHrEmail(toCopy.hrEmail);
            setCompanyName(toCopy.companyName);
            setCompanyLocation(toCopy.companyLocation);
            setTags(toCopy.tags);
            setStatus(toCopy.status);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(role, phone, hrEmail, companyName, companyLocation, tags);
        }

        public void setRole(Role role) {
            this.role = role;
        }

        public Optional<Role> getRole() {
            return Optional.ofNullable(role);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setHrEmail(HrEmail hrEmail) {
            this.hrEmail = hrEmail;
        }

        public Optional<HrEmail> getHrEmail() {
            return Optional.ofNullable(hrEmail);
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public Optional<String> getCompanyName() {
            return Optional.ofNullable(companyName);
        }

        public void setCompanyLocation(String companyLocation) {
            this.companyLocation = companyLocation;
        }

        public Optional<String> getCompanyLocation() {
            return Optional.ofNullable(companyLocation);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        /**
         * Sets {@code status} to this object's {@code status}.
         * @param status to be set to the object's status
         */
        public void setStatus(Status status) {
            this.status = status;
        }

        /**
         * Returns the status of the application to be edited, if specified.
         *
         * @return an {@code Optional} containing the {@code Status} if set,
         *         or {@code Optional.empty()} if no status was provided.
         */
        public Optional<Status> getStatus() {
            return Optional.ofNullable(status);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            if (!(other instanceof EditApplicationDescriptor)) {
                return false;
            }

            EditApplicationDescriptor otherEditApplicationDescriptor = (EditApplicationDescriptor) other;
            return Objects.equals(role, otherEditApplicationDescriptor.role)
                    && Objects.equals(phone, otherEditApplicationDescriptor.phone)
                    && Objects.equals(hrEmail, otherEditApplicationDescriptor.hrEmail)
                    && Objects.equals(companyName, otherEditApplicationDescriptor.companyName)
                    && Objects.equals(companyLocation, otherEditApplicationDescriptor.companyLocation)
                    && Objects.equals(tags, otherEditApplicationDescriptor.tags)
                    && Objects.equals(status, otherEditApplicationDescriptor.status);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("role", role)
                    .add("phone", phone)
                    .add("hrEmail", hrEmail)
                    .add("companyName", companyName)
                    .add("companyLocation", companyLocation)
                    .add("tags", tags)
                    .add("status", status)
                    .toString();
        }
    }
}
