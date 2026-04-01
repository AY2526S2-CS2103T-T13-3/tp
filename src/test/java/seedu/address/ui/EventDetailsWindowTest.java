package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import seedu.address.model.application.ApplicationEvent;
import seedu.address.model.application.OnlineAssessment;

/**
 * Tests for {@link EventDetailsWindow}.
 *
 * <p>Every branch in {@code setEventDetails}, plus {@code show()}, {@code isShowing()},
 * {@code focus()}, {@code hide()}, and the private {@code handleClose()} handler are covered.
 *
 * <p>All JavaFX object construction and mutation is marshalled onto the FX Application
 * Thread via the {@link #onFx(FxTask)} helper to avoid {@link IllegalStateException}.
 */
@DisabledOnOs(OS.LINUX)
public class EventDetailsWindowTest {

    private static final DateTimeFormatter DISPLAY_FORMATTER =
            DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm");

    /** The window under test — recreated on the FX thread before each test. */
    private EventDetailsWindow window;

    @BeforeAll
    public static void initJfxRuntime() throws Exception {
        System.setProperty("prism.order", "sw");
        System.setProperty("testfx.robot", "glass");
        System.setProperty("testfx.headless", "true");

        CountDownLatch latch = new CountDownLatch(1);
        try {
            Platform.startup(latch::countDown);
        } catch (IllegalStateException e) {
            latch.countDown(); // already started
        }
        assertTrue(latch.await(5, TimeUnit.SECONDS), "JavaFX toolkit did not start in time");
    }

    @BeforeEach
    public void setUp() throws Exception {
        // EventDetailsWindow extends UiPart<Stage> — must be created on the FX thread.
        AtomicReference<EventDetailsWindow> ref = new AtomicReference<>();
        onFx(() -> ref.set(new EventDetailsWindow()));
        window = ref.get();
    }

    // -----------------------------------------------------------------------
    // FX-thread helper
    // -----------------------------------------------------------------------

    @FunctionalInterface
    private interface FxTask {
        void run() throws Exception;
    }

    private static void onFx(FxTask task) throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<Throwable> error = new AtomicReference<>();
        Platform.runLater(() -> {
            try {
                task.run();
            } catch (Throwable t) {
                error.set(t);
            } finally {
                latch.countDown();
            }
        });
        assertTrue(latch.await(10, TimeUnit.SECONDS), "FX thread timed out");
        if (error.get() != null) {
            throw new RuntimeException(error.get());
        }
    }

    // -----------------------------------------------------------------------
    // Reflection helper
    // -----------------------------------------------------------------------

    private String getLabelText(String fieldName) throws Exception {
        Field f = EventDetailsWindow.class.getDeclaredField(fieldName);
        f.setAccessible(true);
        return ((Label) f.get(window)).getText();
    }

    // -----------------------------------------------------------------------
    // setEventDetails — OnlineAssessment branch
    // -----------------------------------------------------------------------

    /**
     * When the event is an {@link OnlineAssessment}, all five labels must be
     * populated with OA-specific data (title = "Online Assessment Details").
     */
    @Test
    public void setEventDetails_withOnlineAssessment_populatesLabelsCorrectly() throws Exception {
        LocalDateTime dt = LocalDateTime.of(2026, 7, 20, 14, 30);
        OnlineAssessment oa = new OnlineAssessment(
                "Zoom call", dt, "HackerRank", "https://hr.com/test", "Bring passport");

        onFx(() -> window.setEventDetails(oa));

        assertEquals("Online Assessment Details", getLabelText("titleLabel"));
        assertEquals("Zoom call", getLabelText("locationLabel"));
        assertEquals(dt.format(DISPLAY_FORMATTER), getLabelText("dateTimeLabel"));
        assertEquals("HackerRank", getLabelText("platformLabel"));
        assertEquals("https://hr.com/test", getLabelText("linkLabel"));
        assertEquals("Bring passport", getLabelText("notesLabel"));
    }

    /**
     * An {@link OnlineAssessment} created without explicit notes should display
     * the default {@link OnlineAssessment#EMPTY_NOTES_VALUE} in the notes label.
     */
    @Test
    public void setEventDetails_withOnlineAssessmentNoNotes_showsDefaultNotes() throws Exception {
        LocalDateTime dt = LocalDateTime.of(2026, 8, 10, 10, 0);
        OnlineAssessment oa = new OnlineAssessment("Office", dt, "Codility", "https://c.com");

        onFx(() -> window.setEventDetails(oa));

        assertEquals(OnlineAssessment.EMPTY_NOTES_VALUE, getLabelText("notesLabel"));
    }

    // -----------------------------------------------------------------------
    // setEventDetails — fallback (non-OnlineAssessment) branch
    // -----------------------------------------------------------------------

    /**
     * When the event is a generic (non-OnlineAssessment) subclass of
     * {@link ApplicationEvent}, the window should fall through to the else-branch
     * and display "Event Details" / "N/A" values.
     */
    @Test
    public void setEventDetails_withGenericApplicationEvent_populatesFallbackLabels() throws Exception {
        LocalDateTime dt = LocalDateTime.of(2026, 9, 5, 9, 0);
        // Anonymous concrete subclass — NOT an OnlineAssessment
        ApplicationEvent generic = new ApplicationEvent("Conference room", dt) { };

        onFx(() -> window.setEventDetails(generic));

        assertEquals("Event Details", getLabelText("titleLabel"));
        assertEquals("Conference room", getLabelText("locationLabel"));
        assertEquals(dt.format(DISPLAY_FORMATTER), getLabelText("dateTimeLabel"));
        assertEquals("N/A", getLabelText("platformLabel"));
        assertEquals("N/A", getLabelText("linkLabel"));
        assertEquals("N/A", getLabelText("notesLabel"));
    }

    // -----------------------------------------------------------------------
    // isShowing()
    // -----------------------------------------------------------------------

    /** A freshly constructed window should not be showing. */
    @Test
    public void isShowing_newWindow_returnsFalse() {
        assertFalse(window.isShowing(), "New EventDetailsWindow should not be showing");
    }

    // -----------------------------------------------------------------------
    // show() / hide()
    // -----------------------------------------------------------------------

    /**
     * After {@code show()} the window is visible; after {@code hide()} it is not.
     */
    @Test
    public void show_thenHide_windowShowsAndHides() throws Exception {
        onFx(() -> window.show());
        assertTrue(window.isShowing(), "Window should be showing after show()");

        onFx(() -> window.hide());
        assertFalse(window.isShowing(), "Window should not be showing after hide()");
    }

    // -----------------------------------------------------------------------
    // focus()
    // -----------------------------------------------------------------------

    /** {@code focus()} must not throw whether or not the window is visible. */
    @Test
    public void focus_doesNotThrow() throws Exception {
        onFx(() -> window.focus()); // window is not showing — must still not throw
    }

    // -----------------------------------------------------------------------
    // handleClose() — private FXML handler
    // -----------------------------------------------------------------------

    /**
     * {@code handleClose()} (the close-button FXML handler) delegates to {@code hide()}.
     * The window should no longer be showing after the handler is invoked.
     */
    @Test
    public void handleClose_hidesWindow() throws Exception {
        onFx(() -> window.show());
        assertTrue(window.isShowing(), "Pre-condition: window should be showing");

        onFx(() -> {
            Method m = EventDetailsWindow.class.getDeclaredMethod("handleClose");
            m.setAccessible(true);
            m.invoke(window);
        });

        assertFalse(window.isShowing(), "Window should be hidden after handleClose()");
    }

    // -----------------------------------------------------------------------
    // Stage-based constructor
    // -----------------------------------------------------------------------

    /**
     * The Stage-based constructor should back the window with the provided Stage
     * (i.e. {@code getRoot()} returns a non-null Stage).
     */
    @Test
    public void constructor_withStage_usesProvidedStage() throws Exception {
        AtomicReference<Stage> rootRef = new AtomicReference<>();
        onFx(() -> {
            Stage myStage = new Stage();
            EventDetailsWindow edw = new EventDetailsWindow(myStage);
            rootRef.set(edw.getRoot());
        });
        assertNotNull(rootRef.get(), "getRoot() should return the Stage supplied to the constructor");
    }
}
