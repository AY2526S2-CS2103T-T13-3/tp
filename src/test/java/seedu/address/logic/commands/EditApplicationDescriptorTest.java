package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_LOCATION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HREMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditCommand.EditApplicationDescriptor;
import seedu.address.testutil.EditApplicationDescriptorBuilder;

public class EditApplicationDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditApplicationDescriptor descriptorWithSameValues = new EditApplicationDescriptor(DESC_AMY);
        assertTrue(DESC_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_AMY.equals(DESC_AMY));

        // null -> returns false
        assertFalse(DESC_AMY.equals(null));

        // different types -> returns false
        assertFalse(DESC_AMY.equals(5));

        // different values -> returns false
        assertFalse(DESC_AMY.equals(DESC_BOB));

        // different role -> returns false
        EditApplicationDescriptor editedAmy = new EditApplicationDescriptorBuilder(DESC_AMY)
                .withRole(VALID_ROLE_BOB)
                .build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different phone -> returns false
        editedAmy = new EditApplicationDescriptorBuilder(DESC_AMY).withPhone(VALID_PHONE_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different hrEmail -> returns false
        editedAmy = new EditApplicationDescriptorBuilder(DESC_AMY).withHrEmail(VALID_HREMAIL_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different company name -> returns false
        editedAmy = new EditApplicationDescriptorBuilder(DESC_AMY)
                .withCompanyName(VALID_COMPANY_NAME_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different company location -> returns false
        editedAmy = new EditApplicationDescriptorBuilder(DESC_AMY)
                .withCompanyLocation(VALID_COMPANY_LOCATION_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditApplicationDescriptorBuilder(DESC_AMY).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(DESC_AMY.equals(editedAmy));
    }

    @Test
    public void toStringMethod() {
        EditApplicationDescriptor editApplicationDescriptor = new EditApplicationDescriptor();
        String expected = EditApplicationDescriptor.class.getCanonicalName() + "{role="
                + editApplicationDescriptor.getRole().orElse(null) + ", phone="
                + editApplicationDescriptor.getPhone().orElse(null) + ", hrEmail="
                + editApplicationDescriptor.getHrEmail().orElse(null) + ", companyName="
                + editApplicationDescriptor.getCompanyName().orElse(null) + ", companyLocation="
                + editApplicationDescriptor.getCompanyLocation().orElse(null) + ", tags="
                + editApplicationDescriptor.getTags().orElse(null) + ", status="
                + editApplicationDescriptor.getStatus().orElse(null) + "}";
        assertEquals(expected, editApplicationDescriptor.toString());
    }
}
