package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ApplicationCardTest {

    @Test
    public void formatPhone_returnsExpectedText() {
        assertEquals("☎ 91234567", ApplicationCard.formatPhone("91234567"));
    }

    @Test
    public void formatHrEmail_returnsExpectedText() {
        assertEquals("✉ hr@google.com", ApplicationCard.formatHrEmail("hr@google.com"));
    }

    @Test
    public void formatCompanyName_returnsExpectedText() {
        assertEquals("▣ Google", ApplicationCard.formatCompanyName("Google"));
    }

    @Test
    public void formatCompanyLocation_returnsExpectedText() {
        assertEquals("⌂ Singapore", ApplicationCard.formatCompanyLocation("Singapore"));
    }

    @Test
    public void formatDeadline_returnsExpectedText() {
        assertEquals("◷ 2026-12-31", ApplicationCard.formatDeadline("2026-12-31"));
    }

    @Test
    public void formatNote_returnsExpectedText() {
        assertEquals("✎ Follow up next Monday", ApplicationCard.formatNote("Follow up next Monday"));
    }

    @Test
    public void formatResume_returnsExpectedText() {
        assertEquals("▣ resume.pdf", ApplicationCard.formatResume("resume.pdf"));
    }

    @Test
    public void formatPhone_emptyString_returnsIconOnly() {
        assertEquals("☎ ", ApplicationCard.formatPhone(""));
    }

    @Test
    public void formatHrEmail_emptyString_returnsIconOnly() {
        assertEquals("✉ ", ApplicationCard.formatHrEmail(""));
    }

    @Test
    public void formatCompanyName_emptyString_returnsIconOnly() {
        assertEquals("▣ ", ApplicationCard.formatCompanyName(""));
    }

    @Test
    public void formatCompanyLocation_emptyString_returnsIconOnly() {
        assertEquals("⌂ ", ApplicationCard.formatCompanyLocation(""));
    }

    @Test
    public void formatDeadline_emptyString_returnsIconOnly() {
        assertEquals("◷ ", ApplicationCard.formatDeadline(""));
    }

    @Test
    public void formatNote_emptyString_returnsIconOnly() {
        assertEquals("✎ ", ApplicationCard.formatNote(""));
    }

    @Test
    public void formatResume_emptyString_returnsIconOnly() {
        assertEquals("▣ ", ApplicationCard.formatResume(""));
    }

    @Test
    public void formatPhone_normalValue_returnsExpectedText() {
        assertEquals("☎ 91234567", ApplicationCard.formatPhone("91234567"));
    }

    @Test
    public void formatHrEmail_normalValue_returnsExpectedText() {
        assertEquals("✉ hr@google.com", ApplicationCard.formatHrEmail("hr@google.com"));
    }

    @Test
    public void formatCompanyName_normalValue_returnsExpectedText() {
        assertEquals("▣ Google", ApplicationCard.formatCompanyName("Google"));
    }

    @Test
    public void formatCompanyLocation_normalValue_returnsExpectedText() {
        assertEquals("⌂ Singapore", ApplicationCard.formatCompanyLocation("Singapore"));
    }

    @Test
    public void formatDeadline_normalValue_returnsExpectedText() {
        assertEquals("◷ 2026-12-31", ApplicationCard.formatDeadline("2026-12-31"));
    }

    @Test
    public void formatNote_normalValue_returnsExpectedText() {
        assertEquals("✎ Follow up next Monday", ApplicationCard.formatNote("Follow up next Monday"));
    }

    @Test
    public void formatResume_normalValue_returnsExpectedText() {
        assertEquals("▣ resume.pdf", ApplicationCard.formatResume("resume.pdf"));
    }
}
