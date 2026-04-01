package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ApplicationCardTest {

    // ── formatPhone ──────────────────────────────────────────────────────────

    @Test
    public void formatPhone_normalValue_returnsValue() {
        assertEquals("91234567", ApplicationCard.formatPhone("91234567"));
    }

    @Test
    public void formatPhone_emptyString_returnsEmpty() {
        assertEquals("", ApplicationCard.formatPhone(""));
    }

    // ── formatHrEmail ────────────────────────────────────────────────────────

    @Test
    public void formatHrEmail_normalValue_returnsValue() {
        assertEquals("hr@google.com", ApplicationCard.formatHrEmail("hr@google.com"));
    }

    @Test
    public void formatHrEmail_emptyString_returnsEmpty() {
        assertEquals("", ApplicationCard.formatHrEmail(""));
    }

    // ── formatCompanyName ────────────────────────────────────────────────────

    @Test
    public void formatCompanyName_normalValue_returnsValue() {
        assertEquals("Google", ApplicationCard.formatCompanyName("Google"));
    }

    @Test
    public void formatCompanyName_emptyString_returnsEmpty() {
        assertEquals("", ApplicationCard.formatCompanyName(""));
    }

    // ── formatCompanyLocation ────────────────────────────────────────────────

    @Test
    public void formatCompanyLocation_normalValue_returnsValue() {
        assertEquals("Singapore", ApplicationCard.formatCompanyLocation("Singapore"));
    }

    @Test
    public void formatCompanyLocation_emptyString_returnsEmpty() {
        assertEquals("", ApplicationCard.formatCompanyLocation(""));
    }

    // ── formatDeadline ───────────────────────────────────────────────────────

    @Test
    public void formatDeadline_normalValue_returnsValue() {
        assertEquals("2026-12-31", ApplicationCard.formatDeadline("2026-12-31"));
    }

    @Test
    public void formatDeadline_emptyString_returnsEmpty() {
        assertEquals("", ApplicationCard.formatDeadline(""));
    }

    // ── formatNote ───────────────────────────────────────────────────────────

    @Test
    public void formatNote_normalValue_returnsValue() {
        assertEquals("Follow up next Monday", ApplicationCard.formatNote("Follow up next Monday"));
    }

    @Test
    public void formatNote_emptyString_returnsEmpty() {
        assertEquals("", ApplicationCard.formatNote(""));
    }

    // ── formatResume ─────────────────────────────────────────────────────────

    @Test
    public void formatResume_normalValue_returnsValue() {
        assertEquals("resume.pdf", ApplicationCard.formatResume("resume.pdf"));
    }

    @Test
    public void formatResume_emptyString_returnsEmpty() {
        assertEquals("", ApplicationCard.formatResume(""));
    }

    // ── format methods return raw value (no icon prefix) ────────────────────

    @Test
    public void formatMethods_doNotAddIconPrefix() {
        assertFalse(ApplicationCard.formatPhone("91234567").contains("☎"));
        assertFalse(ApplicationCard.formatHrEmail("hr@google.com").contains("✉"));
        assertFalse(ApplicationCard.formatCompanyName("Google").contains("▣"));
        assertFalse(ApplicationCard.formatCompanyLocation("Singapore").contains("⌂"));
        assertFalse(ApplicationCard.formatDeadline("2026-12-31").contains("◷"));
        assertFalse(ApplicationCard.formatNote("Follow up").contains("✎"));
        assertFalse(ApplicationCard.formatResume("resume.pdf").contains("▣"));
    }

    @Test
    public void formatMethods_allReturnExactInput() {
        assertEquals("91234567", ApplicationCard.formatPhone("91234567"));
        assertEquals("hr@google.com", ApplicationCard.formatHrEmail("hr@google.com"));
        assertEquals("Google", ApplicationCard.formatCompanyName("Google"));
        assertEquals("Singapore", ApplicationCard.formatCompanyLocation("Singapore"));
        assertEquals("2026-12-31", ApplicationCard.formatDeadline("2026-12-31"));
        assertEquals("Follow up", ApplicationCard.formatNote("Follow up"));
        assertEquals("resume.pdf", ApplicationCard.formatResume("resume.pdf"));
    }

    @Test
    public void formatMethods_emptyStrings_returnEmpty() {
        assertEquals("", ApplicationCard.formatPhone(""));
        assertEquals("", ApplicationCard.formatHrEmail(""));
        assertEquals("", ApplicationCard.formatCompanyName(""));
        assertEquals("", ApplicationCard.formatCompanyLocation(""));
        assertEquals("", ApplicationCard.formatDeadline(""));
        assertEquals("", ApplicationCard.formatNote(""));
        assertEquals("", ApplicationCard.formatResume(""));
    }

    // ── toStatusKey ──────────────────────────────────────────────────────────

    @Test
    public void toStatusKey_applied_returnsApplied() {
        assertEquals("applied", ApplicationCard.toStatusKey("APPLIED"));
    }

    @Test
    public void toStatusKey_interviewing_returnsInterviewing() {
        assertEquals("interviewing", ApplicationCard.toStatusKey("INTERVIEWING"));
    }

    @Test
    public void toStatusKey_offered_returnsOffered() {
        assertEquals("offered", ApplicationCard.toStatusKey("OFFERED"));
    }

    @Test
    public void toStatusKey_rejected_returnsRejected() {
        assertEquals("rejected", ApplicationCard.toStatusKey("REJECTED"));
    }

    @Test
    public void toStatusKey_withdrawn_returnsWithdrawn() {
        assertEquals("withdrawn", ApplicationCard.toStatusKey("WITHDRAWN"));
    }

    @Test
    public void toStatusKey_emptyString_returnsEmpty() {
        assertEquals("", ApplicationCard.toStatusKey(""));
    }

    @Test
    public void toStatusKey_withUnderscore_replaceWithHyphen() {
        assertEquals("in-progress", ApplicationCard.toStatusKey("IN_PROGRESS"));
    }

    // ── toTitleCase ──────────────────────────────────────────────────────────

    @Test
    public void toTitleCase_applied_returnsApplied() {
        assertEquals("Applied", ApplicationCard.toTitleCase("APPLIED"));
    }

    @Test
    public void toTitleCase_interviewing_returnsInterviewing() {
        assertEquals("Interviewing", ApplicationCard.toTitleCase("INTERVIEWING"));
    }

    @Test
    public void toTitleCase_offered_returnsOffered() {
        assertEquals("Offered", ApplicationCard.toTitleCase("OFFERED"));
    }

    @Test
    public void toTitleCase_rejected_returnsRejected() {
        assertEquals("Rejected", ApplicationCard.toTitleCase("REJECTED"));
    }

    @Test
    public void toTitleCase_withdrawn_returnsWithdrawn() {
        assertEquals("Withdrawn", ApplicationCard.toTitleCase("WITHDRAWN"));
    }

    @Test
    public void toTitleCase_withUnderscore_replacesWithSpace() {
        assertEquals("In progress", ApplicationCard.toTitleCase("IN_PROGRESS"));
    }

    @Test
    public void toTitleCase_emptyString_returnsEmpty() {
        assertEquals("", ApplicationCard.toTitleCase(""));
    }

    @Test
    public void toTitleCase_singleChar_returnsUppercase() {
        assertEquals("A", ApplicationCard.toTitleCase("a"));
    }

    @Test
    public void toTitleCase_firstLetterIsUppercase() {
        String result = ApplicationCard.toTitleCase("APPLIED");
        assertTrue(Character.isUpperCase(result.charAt(0)));
    }

    @Test
    public void toTitleCase_remainingLettersAreLowercase() {
        String result = ApplicationCard.toTitleCase("APPLIED");
        String rest = result.substring(1);
        assertTrue(rest.equals(rest.toLowerCase()));
    }
}
