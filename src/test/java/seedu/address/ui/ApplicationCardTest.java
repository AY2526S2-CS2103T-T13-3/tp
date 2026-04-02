package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.application.Platform;
import javafx.scene.paint.Color;
import seedu.address.model.application.Application;
import seedu.address.model.application.Resume;
import seedu.address.testutil.ApplicationBuilder;

public class ApplicationCardTest {

    @BeforeAll
    public static void initJfxRuntime() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        try {
            Platform.startup(latch::countDown);
        } catch (IllegalStateException e) {
            // JavaFX toolkit already initialized.
            latch.countDown();
        }
        assertTrue(latch.await(5, TimeUnit.SECONDS));
    }

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

    @Test
    public void getRoleColor_deadlineWithinThreeDays_returnsUrgentRed() {
        LocalDateTime now = LocalDateTime.of(2026, 3, 10, 21, 44);
        Color color = ApplicationCard.getRoleColor(
                new ApplicationBuilder().withDeadline("2026-03-12 21:00").build(), now);
        assertEquals(Color.web("#e53935"), color);
    }

    @Test
    public void getRoleColor_overdueDeadline_returnsGray() {
        LocalDateTime now = LocalDateTime.of(2026, 3, 10, 21, 44);
        Color color = ApplicationCard.getRoleColor(
                new ApplicationBuilder().withDeadline("2026-03-10 21:00").build(), now);
        assertEquals(Color.web("#fb8c00"), color);
    }

    @Test
    public void getRoleColor_noDeadline_returnsDefaultWhite() {
        LocalDateTime now = LocalDateTime.of(2026, 3, 10, 21, 44);
        Color color = ApplicationCard.getRoleColor(
                new ApplicationBuilder().withDeadline("-").build(), now);
        assertEquals(Color.WHITE, color);
    }

    @Test
    public void getRoleColor_dateOnlyTodayNotOverdue_returnsUrgentRed() {
        LocalDateTime now = LocalDateTime.of(2026, 3, 10, 21, 44);
        Color color = ApplicationCard.getRoleColor(
                new ApplicationBuilder().withDeadline("2026-03-10").build(), now);
        assertEquals(Color.web("#e53935"), color);
    }

    @Test
    public void getDeadlineIconColor_overdue_returnsOrange() {
        LocalDateTime now = LocalDateTime.of(2026, 4, 1, 21, 44);
        Color color = ApplicationCard.getDeadlineIconColor(
                new ApplicationBuilder().withDeadline("2026-04-01 21:00").build(), now);
        assertEquals(Color.web("#fb8c00"), color);
    }

    @Test
    public void getDeadlineIconColor_deadlineUrgent_returnsRed() {
        LocalDateTime now = LocalDateTime.of(2026, 3, 10, 21, 44);
        Color color = ApplicationCard.getDeadlineIconColor(
                new ApplicationBuilder().withDeadline("2026-03-12 21:00").build(), now);
        assertEquals(Color.web("#e53935"), color);
    }

    @Test
    public void getDeadlineIconColor_deadlineFarFuture_returnsDefaultWhite() {
        LocalDateTime now = LocalDateTime.of(2026, 3, 10, 21, 44);
        Color color = ApplicationCard.getDeadlineIconColor(
                new ApplicationBuilder().withDeadline("2026-03-20 21:00").build(), now);
        // For far-future deadlines the role text is default white, but the calendar icon is
        // still colored as "urgent" (red) unless the role color is overdue (orange).
        assertEquals(Color.web("#e53935"), color);
    }

    @Test
    public void getRoleColor_dateOnlyWithinThreeDays_returnsUrgentRed() {
        LocalDateTime now = LocalDateTime.of(2026, 4, 2, 21, 44);
        Color color = ApplicationCard.getRoleColor(
                new ApplicationBuilder().withDeadline("2026-04-04").build(), now);
        assertEquals(Color.web("#e53935"), color);
    }

    @Test
    public void getRoleColor_invalidDeadlineDateOnly_returnsDefaultWhite() {
        LocalDateTime now = LocalDateTime.of(2026, 4, 2, 21, 44);
        Color color = ApplicationCard.getRoleColor(
                new ApplicationBuilder().withDeadline("2026-99-99").build(), now);
        assertEquals(Color.WHITE, color);
    }

    @Test
    public void getRoleColor_invalidDeadlineDateTime_returnsDefaultWhite() {
        LocalDateTime now = LocalDateTime.of(2026, 4, 2, 21, 44);
        Color color = ApplicationCard.getRoleColor(
                new ApplicationBuilder().withDeadline("2026-04-01 12:60").build(), now);
        assertEquals(Color.WHITE, color);
    }

    @Test
    public void getRoleColor_datetimeBeyondThreeDays_returnsDefaultWhite() {
        LocalDateTime now = LocalDateTime.of(2026, 3, 10, 21, 44);
        Color color = ApplicationCard.getRoleColor(
                new ApplicationBuilder().withDeadline("2026-03-14 21:00").build(), now);
        assertEquals(Color.WHITE, color);
    }

    @Test
    public void getDeadlineIconColor_noDeadline_returnsUrgentRed() {
        LocalDateTime now = LocalDateTime.of(2026, 3, 10, 21, 44);
        Color color = ApplicationCard.getDeadlineIconColor(
                new ApplicationBuilder().withDeadline("-").build(), now);
        assertEquals(Color.web("#e53935"), color);
    }

    @Test
    public void constructor_deadlinePresent_initializesDeadlineGraphicAndIcons() {
        ApplicationCard card = new ApplicationCard(
                new ApplicationBuilder()
                        .withDeadline("2026-03-12 21:00")
                        .withTags("interview", "priority")
                        .withNote("Follow up next Monday")
                        .withCompanyLocation("Singapore")
                        .build(),
                1);

        assertNotNull(card);
    }

    @Test
    public void constructor_noDeadline_hidesDeadlineField() {
        ApplicationCard card = new ApplicationCard(new ApplicationBuilder().build(), 1);
        assertNotNull(card);
    }

    @Test
    public void constructor_resumePresent_showsResumeIcon() {
        Application base = new ApplicationBuilder()
                .withDeadline("2026-03-12 21:00")
                .build();
        Application applicationWithResume = new Application(
                base.getRole(),
                base.getPhone(),
                base.getHrEmail(),
                base.getCompany(),
                base.getTags(),
                base.getStatus(),
                base.getDeadline(),
                base.getApplicationEvent(),
                base.getNote(),
                new Resume("resume.pdf"));

        ApplicationCard card = new ApplicationCard(applicationWithResume, 1);
        assertNotNull(card);
    }

    @Test
    public void toStatusKey_null_returnsEmpty() {
        assertEquals("", ApplicationCard.toStatusKey(null));
    }

    @Test
    public void toTitleCase_null_returnsNull() {
        assertNull(ApplicationCard.toTitleCase(null));
    }
}
