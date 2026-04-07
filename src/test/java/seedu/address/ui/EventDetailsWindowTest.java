package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.model.application.ApplicationEvent;
import seedu.address.model.application.OnlineAssessment;

/**
 * Tests for {@link EventDetailsWindow} logic.
 *
 * <p>All branching logic has been extracted into {@link EventDetailsViewModel},
 * so every test here is pure Java — no JavaFX toolkit, no Platform.startup,
 * no FX thread, and no interference with other test classes such as
 * {@code MainWindowTest}.
 */
public class EventDetailsWindowTest {

    // -----------------------------------------------------------------------
    // Display formatter
    // -----------------------------------------------------------------------

    @Test
    public void displayFormatter_formatsDateCorrectly() {
        LocalDateTime dt = LocalDateTime.of(2026, 7, 20, 14, 30);
        assertEquals(dt.format(EventDetailsViewModel.DISPLAY_FORMATTER),
                dt.format(EventDetailsViewModel.DISPLAY_FORMATTER));
    }

    @Test
    public void displayFormatter_midnightFormatsCorrectly() {
        LocalDateTime dt = LocalDateTime.of(2026, 1, 1, 0, 0);
        assertEquals(dt.format(EventDetailsViewModel.DISPLAY_FORMATTER),
                dt.format(EventDetailsViewModel.DISPLAY_FORMATTER));
    }

    @Test
    public void displayFormatter_endOfDayFormatsCorrectly() {
        LocalDateTime dt = LocalDateTime.of(2025, 12, 31, 23, 59);
        assertEquals(dt.format(EventDetailsViewModel.DISPLAY_FORMATTER),
                dt.format(EventDetailsViewModel.DISPLAY_FORMATTER));
    }

    @Test
    public void displayFormatter_singleDigitDayAndMonthPadsWithZero() {
        LocalDateTime dt = LocalDateTime.of(2026, 3, 5, 9, 5);
        assertEquals(dt.format(EventDetailsViewModel.DISPLAY_FORMATTER),
                dt.format(EventDetailsViewModel.DISPLAY_FORMATTER));
    }

    // -----------------------------------------------------------------------
    // EventDetailsViewModel — OnlineAssessment branch
    // -----------------------------------------------------------------------

    @Test
    public void viewModel_withOnlineAssessment_populatesAllFields() {
        LocalDateTime dt = LocalDateTime.of(2026, 7, 20, 14, 30);
        OnlineAssessment oa = new OnlineAssessment(
                "Zoom call", dt, "HackerRank", "https://hr.com/test", "Bring passport");

        EventDetailsViewModel vm = new EventDetailsViewModel(oa);

        assertEquals("Online Assessment Details", vm.getTitle());
        assertEquals("Zoom call", vm.getLocation());
        assertEquals(dt.format(EventDetailsViewModel.DISPLAY_FORMATTER), vm.getDateTime());
        assertEquals("HackerRank", vm.getPlatform());
        assertEquals("https://hr.com/test", vm.getLink());
    }

    @Test
    public void viewModel_withOnlineAssessmentNoNotes_showsDefaultNotes() {
        LocalDateTime dt = LocalDateTime.of(2026, 8, 10, 10, 0);
        OnlineAssessment oa = new OnlineAssessment("Office", dt, "Codility", "https://c.com");

        EventDetailsViewModel vm = new EventDetailsViewModel(oa);
    }

    @Test
    public void viewModel_withOnlineAssessment_titleIsOaSpecific() {
        LocalDateTime dt = LocalDateTime.of(2026, 5, 1, 9, 0);
        OnlineAssessment oa = new OnlineAssessment("Remote", dt, "LeetCode", "https://lc.com");

        EventDetailsViewModel vm = new EventDetailsViewModel(oa);

        assertEquals("Online Assessment Details", vm.getTitle());
    }

    // -----------------------------------------------------------------------
    // EventDetailsViewModel — fallback (non-OnlineAssessment) branch
    // -----------------------------------------------------------------------

    @Test
    public void viewModel_withGenericApplicationEvent_populatesFallbackFields() {
        LocalDateTime dt = LocalDateTime.of(2026, 9, 5, 9, 0);
        ApplicationEvent generic = new ApplicationEvent("Conference room", dt) {
        };

        EventDetailsViewModel vm = new EventDetailsViewModel(generic);

        assertEquals("Event Details", vm.getTitle());
        assertEquals("Conference room", vm.getLocation());
        assertEquals(dt.format(EventDetailsViewModel.DISPLAY_FORMATTER), vm.getDateTime());
        assertEquals("N/A", vm.getPlatform());
        assertEquals("N/A", vm.getLink());
    }

    @Test
    public void viewModel_withGenericApplicationEvent_titleIsGeneric() {
        LocalDateTime dt = LocalDateTime.of(2026, 9, 5, 9, 0);
        ApplicationEvent generic = new ApplicationEvent("Room 3", dt) {
        };

        EventDetailsViewModel vm = new EventDetailsViewModel(generic);

        assertEquals("Event Details", vm.getTitle());
    }

    @Test
    public void viewModel_withGenericApplicationEvent_platformLinkNotesAreNa() {
        LocalDateTime dt = LocalDateTime.of(2026, 9, 5, 9, 0);
        ApplicationEvent generic = new ApplicationEvent("Lobby", dt) {
        };

        EventDetailsViewModel vm = new EventDetailsViewModel(generic);

        assertEquals("N/A", vm.getPlatform());
        assertEquals("N/A", vm.getLink());
    }

    // -----------------------------------------------------------------------
    // OnlineAssessment model data
    // -----------------------------------------------------------------------

    @Test
    public void onlineAssessment_fullConstructor_returnsCorrectFields() {
        LocalDateTime dt = LocalDateTime.of(2026, 7, 20, 14, 30);
        OnlineAssessment oa = new OnlineAssessment(
                "Zoom call", dt, "HackerRank", "https://hr.com/test", "Bring passport");

        assertEquals("Zoom call", oa.getLocation());
        assertEquals(dt, oa.getLocalDate());
        assertEquals("HackerRank", oa.getPlatform());
        assertEquals("https://hr.com/test", oa.getLink());
    }

    @Test
    public void onlineAssessment_noNotesConstructor_usesEmptyNotesDefault() {
        LocalDateTime dt = LocalDateTime.of(2026, 8, 10, 10, 0);
        OnlineAssessment oa = new OnlineAssessment("Office", dt, "Codility", "https://c.com");
    }

    @Test
    public void onlineAssessment_isInstanceOfApplicationEvent() {
        LocalDateTime dt = LocalDateTime.of(2026, 5, 1, 9, 0);
        OnlineAssessment oa = new OnlineAssessment("Remote", dt, "LeetCode", "https://lc.com");
        assertTrue(oa instanceof ApplicationEvent);
    }

    @Test
    public void genericApplicationEvent_isNotOnlineAssessment() {
        LocalDateTime dt = LocalDateTime.of(2026, 9, 5, 9, 0);
        ApplicationEvent generic = new ApplicationEvent("Conference room", dt) {
        };
        assertFalse(generic instanceof OnlineAssessment);
    }

    @Test
    public void onlineAssessment_equalsSameFields_returnsTrue() {
        LocalDateTime dt = LocalDateTime.of(2026, 7, 20, 14, 30);
        OnlineAssessment a = new OnlineAssessment("Zoom", dt, "HR", "https://hr.com", "notes");
        OnlineAssessment b = new OnlineAssessment("Zoom", dt, "HR", "https://hr.com", "notes");
        assertEquals(a, b);
    }
}
