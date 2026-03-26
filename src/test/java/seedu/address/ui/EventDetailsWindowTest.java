package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.application.Platform;
import javafx.scene.control.Label;
import seedu.address.model.application.ApplicationEvent;
import seedu.address.model.application.OnlineAssessment;

public class EventDetailsWindowTest {

    private static final DateTimeFormatter DISPLAY_FORMATTER =
            DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm");
    private static final LocalDateTime VALID_DATETIME = LocalDateTime.of(2026, 12, 31, 23, 59);
    private static final LocalDateTime ANOTHER_DATETIME = LocalDateTime.of(2026, 6, 15, 10, 0);

    private EventDetailsWindow eventDetailsWindow;

    @BeforeAll
    public static void initJfxRuntime() throws Exception {
        // Enable headless mode for Linux CI environments
        System.setProperty("java.awt.headless", "true");
        System.setProperty("prism.order", "sw");
        System.setProperty("prism.text", "t2k");
        System.setProperty("testfx.robot", "glass");
        System.setProperty("testfx.headless", "true");
        System.setProperty("glass.platform", "Monocle");
        System.setProperty("monocle.platform", "Headless");

        CountDownLatch latch = new CountDownLatch(1);
        try {
            Platform.startup(latch::countDown);
        } catch (IllegalStateException e) {
            // Platform already started
            latch.countDown();
        }
        assertTrue(latch.await(5, TimeUnit.SECONDS), "JavaFX platform failed to start");
    }

    @BeforeEach
    public void setUp() throws Exception {
        // EventDetailsWindow must be created on the FX thread
        runOnFxThread(() -> eventDetailsWindow = new EventDetailsWindow());
    }

    /**
     * Runs the given {@code task} on the JavaFX Application Thread and waits
     * for it to complete. Any exception thrown by the task is rethrown.
     */
    private void runOnFxThread(RunnableWithException task) throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<Exception> exception = new AtomicReference<>();
        Platform.runLater(() -> {
            try {
                task.run();
            } catch (Exception e) {
                exception.set(e);
            } finally {
                latch.countDown();
            }
        });
        assertTrue(latch.await(10, TimeUnit.SECONDS), "JavaFX task timed out");
        if (exception.get() != null) {
            throw exception.get();
        }
    }

    @FunctionalInterface
    private interface RunnableWithException {
        void run() throws Exception;
    }

    // ==================== setEventDetails — OnlineAssessment ====================

    @Test
    public void setEventDetails_onlineAssessment_titleSetCorrectly() throws Exception {
        runOnFxThread(() -> {
            OnlineAssessment oa = new OnlineAssessment(
                    "home", VALID_DATETIME, "HackerRank", "www.hackerrank.com");
            eventDetailsWindow.setEventDetails(oa);
            assertEquals("Online Assessment Details", getLabelText("titleLabel"));
        });
    }

    @Test
    public void setEventDetails_onlineAssessment_locationSetCorrectly() throws Exception {
        runOnFxThread(() -> {
            OnlineAssessment oa = new OnlineAssessment(
                    "home", VALID_DATETIME, "HackerRank", "www.hackerrank.com");
            eventDetailsWindow.setEventDetails(oa);
            assertEquals("home", getLabelText("locationLabel"));
        });
    }

    @Test
    public void setEventDetails_onlineAssessment_dateTimeFormattedCorrectly() throws Exception {
        runOnFxThread(() -> {
            OnlineAssessment oa = new OnlineAssessment(
                    "home", VALID_DATETIME, "HackerRank", "www.hackerrank.com");
            eventDetailsWindow.setEventDetails(oa);
            assertEquals(VALID_DATETIME.format(DISPLAY_FORMATTER), getLabelText("dateTimeLabel"));
        });
    }

    @Test
    public void setEventDetails_onlineAssessment_platformSetCorrectly() throws Exception {
        runOnFxThread(() -> {
            OnlineAssessment oa = new OnlineAssessment(
                    "home", VALID_DATETIME, "HackerRank", "www.hackerrank.com");
            eventDetailsWindow.setEventDetails(oa);
            assertEquals("HackerRank", getLabelText("platformLabel"));
        });
    }

    @Test
    public void setEventDetails_onlineAssessment_linkSetCorrectly() throws Exception {
        runOnFxThread(() -> {
            OnlineAssessment oa = new OnlineAssessment(
                    "home", VALID_DATETIME, "HackerRank", "www.hackerrank.com");
            eventDetailsWindow.setEventDetails(oa);
            assertEquals("www.hackerrank.com", getLabelText("linkLabel"));
        });
    }

    @Test
    public void setEventDetails_onlineAssessment_defaultNotes() throws Exception {
        runOnFxThread(() -> {
            OnlineAssessment oa = new OnlineAssessment(
                    "home", VALID_DATETIME, "HackerRank", "www.hackerrank.com");
            eventDetailsWindow.setEventDetails(oa);
            assertEquals(OnlineAssessment.EMPTY_NOTES_VALUE, getLabelText("notesLabel"));
        });
    }

    @Test
    public void setEventDetails_onlineAssessmentWithNotes_notesSetCorrectly() throws Exception {
        runOnFxThread(() -> {
            OnlineAssessment oa = new OnlineAssessment(
                    "home", VALID_DATETIME, "HackerRank", "www.hackerrank.com", "bring ID");
            eventDetailsWindow.setEventDetails(oa);
            assertEquals("bring ID", getLabelText("notesLabel"));
        });
    }

    @Test
    public void setEventDetails_onlineAssessment_allFieldsSetTogether() throws Exception {
        runOnFxThread(() -> {
            OnlineAssessment oa = new OnlineAssessment(
                    "office", ANOTHER_DATETIME, "LeetCode", "www.leetcode.com", "custom notes");
            eventDetailsWindow.setEventDetails(oa);

            assertEquals("Online Assessment Details", getLabelText("titleLabel"));
            assertEquals("office", getLabelText("locationLabel"));
            assertEquals(ANOTHER_DATETIME.format(DISPLAY_FORMATTER), getLabelText("dateTimeLabel"));
            assertEquals("LeetCode", getLabelText("platformLabel"));
            assertEquals("www.leetcode.com", getLabelText("linkLabel"));
            assertEquals("custom notes", getLabelText("notesLabel"));
        });
    }

    // ==================== setEventDetails — unknown/future subclass ====================

    @Test
    public void setEventDetails_unknownSubclass_fallbackTitleSet() throws Exception {
        runOnFxThread(() -> {
            ApplicationEvent futureEvent = new ApplicationEvent("Singapore", VALID_DATETIME) {};
            eventDetailsWindow.setEventDetails(futureEvent);
            assertEquals("Event Details", getLabelText("titleLabel"));
        });
    }

    @Test
    public void setEventDetails_unknownSubclass_locationStillSet() throws Exception {
        runOnFxThread(() -> {
            ApplicationEvent futureEvent = new ApplicationEvent("Singapore", VALID_DATETIME) {};
            eventDetailsWindow.setEventDetails(futureEvent);
            assertEquals("Singapore", getLabelText("locationLabel"));
        });
    }

    @Test
    public void setEventDetails_unknownSubclass_dateTimeStillSet() throws Exception {
        runOnFxThread(() -> {
            ApplicationEvent futureEvent = new ApplicationEvent("Singapore", VALID_DATETIME) {};
            eventDetailsWindow.setEventDetails(futureEvent);
            assertEquals(VALID_DATETIME.format(DISPLAY_FORMATTER), getLabelText("dateTimeLabel"));
        });
    }

    @Test
    public void setEventDetails_unknownSubclass_fallbackFieldsSetToNa() throws Exception {
        runOnFxThread(() -> {
            ApplicationEvent futureEvent = new ApplicationEvent("Singapore", VALID_DATETIME) {};
            eventDetailsWindow.setEventDetails(futureEvent);
            assertEquals("N/A", getLabelText("platformLabel"));
            assertEquals("N/A", getLabelText("linkLabel"));
            assertEquals("N/A", getLabelText("notesLabel"));
        });
    }

    // ==================== setEventDetails — called multiple times ====================

    @Test
    public void setEventDetails_calledTwice_updatesCorrectly() throws Exception {
        runOnFxThread(() -> {
            OnlineAssessment first = new OnlineAssessment(
                    "home", VALID_DATETIME, "HackerRank", "www.hackerrank.com");
            OnlineAssessment second = new OnlineAssessment(
                    "office", ANOTHER_DATETIME, "LeetCode", "www.leetcode.com", "notes");

            eventDetailsWindow.setEventDetails(first);
            eventDetailsWindow.setEventDetails(second);

            assertEquals("office", getLabelText("locationLabel"));
            assertEquals("LeetCode", getLabelText("platformLabel"));
            assertEquals(ANOTHER_DATETIME.format(DISPLAY_FORMATTER), getLabelText("dateTimeLabel"));
        });
    }

    // ==================== Window visibility ====================

    @Test
    public void isShowing_beforeShow_returnsFalse() throws Exception {
        runOnFxThread(() -> assertFalse(eventDetailsWindow.isShowing()));
    }

    @Test
    public void isShowing_afterHide_returnsFalse() throws Exception {
        runOnFxThread(() -> {
            eventDetailsWindow.hide();
            assertFalse(eventDetailsWindow.isShowing());
        });
    }

    // ==================== show and focus ====================

    @Test
    public void show_thenIsShowing_returnsTrue() throws Exception {
        runOnFxThread(() -> {
            eventDetailsWindow.show();
            assertTrue(eventDetailsWindow.isShowing());
            // clean up
            eventDetailsWindow.hide();
        });
    }

    @Test
    public void show_thenHide_isShowingReturnsFalse() throws Exception {
        runOnFxThread(() -> {
            eventDetailsWindow.show();
            eventDetailsWindow.hide();
            assertFalse(eventDetailsWindow.isShowing());
        });
    }

    @Test
    public void focus_doesNotThrow() throws Exception {
        runOnFxThread(() -> {
            // focus on a non-showing window should not throw
            eventDetailsWindow.focus();
        });
    }

    // ==================== Helper ====================

    private String getLabelText(String fieldName) throws Exception {
        Field field = EventDetailsWindow.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        Label label = (Label) field.get(eventDetailsWindow);
        return label.getText();
    }
}
