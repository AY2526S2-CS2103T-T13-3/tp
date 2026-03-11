package seedu.address.model.tag;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TagTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Tag(null));
    }

    @Test
    public void constructor_invalidTagRole_throwsIllegalArgumentException() {
        String invalidTagRole = "";
        assertThrows(IllegalArgumentException.class, () -> new Tag(invalidTagRole));
    }

    @Test
    public void isValidTagRole() {
        // null tag role
        assertThrows(NullPointerException.class, () -> Tag.isValidTagName(null));
    }

}
