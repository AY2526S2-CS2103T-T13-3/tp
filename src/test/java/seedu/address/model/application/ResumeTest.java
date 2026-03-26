package seedu.address.model.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ResumeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Resume(null));
    }

    @Test
    public void constructor_invalidResume_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Resume("resume.txt"));
        assertThrows(IllegalArgumentException.class, () -> new Resume("resume.png"));
        assertThrows(IllegalArgumentException.class, () -> new Resume("resume"));
    }

    @Test
    public void isValidResume() {
        assertFalse(Resume.isValidResume(null));

        assertTrue(Resume.isValidResume(""));
        assertTrue(Resume.isValidResume("resume.pdf"));
        assertTrue(Resume.isValidResume("resume.doc"));
        assertTrue(Resume.isValidResume("resume.docx"));

        assertFalse(Resume.isValidResume("resume.txt"));
        assertFalse(Resume.isValidResume("resume.png"));
        assertFalse(Resume.isValidResume("resume"));
    }

    @Test
    public void isEmpty() {
        assertTrue(new Resume("").isEmpty());
        assertFalse(new Resume("resume.pdf").isEmpty());
    }

    @Test
    public void equals() {
        Resume first = new Resume("resume.pdf");
        Resume second = new Resume("resume.pdf");
        Resume third = new Resume("other.pdf");

        assertEquals(first, second);
        assertNotEquals(first, third);
        assertNotEquals(first, null);
        assertNotEquals(first, 1);
    }

    @Test
    public void toString_returnsValue() {
        Resume resume = new Resume("resume.pdf");
        assertEquals("resume.pdf", resume.toString());
    }
}
