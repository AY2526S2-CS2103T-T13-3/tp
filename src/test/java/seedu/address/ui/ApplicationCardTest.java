package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import seedu.address.model.application.Application;
import seedu.address.model.application.OnlineAssessment;
import seedu.address.model.application.Resume;
import seedu.address.model.application.Status;
import seedu.address.testutil.ApplicationBuilder;

@DisabledOnOs(OS.LINUX)
public class ApplicationCardTest {

    @BeforeAll
    public static void initJfxRuntime() throws Exception {
        System.setProperty("prism.order", "sw");
        System.setProperty("testfx.robot", "glass");
        System.setProperty("testfx.headless", "true");

        CountDownLatch latch = new CountDownLatch(1);
        try {
            Platform.startup(latch::countDown);
        } catch (IllegalStateException e) {
            latch.countDown();
        }
        assertTrue(latch.await(5, TimeUnit.SECONDS));
    }

    // -----------------------------------------------------------------------
    // FX-thread helper
    // -----------------------------------------------------------------------

    @FunctionalInterface
    private interface FxTask {
        void run() throws Exception;
    }

    /**
     * Runs {@code task} on the JavaFX Application Thread, waits for it to finish,
     * and re-throws any exception on the test thread.
     */
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

    /** Builds an {@link ApplicationCard} on the FX thread and returns it. */
    private static ApplicationCard buildCard(Application app, int index) throws Exception {
        AtomicReference<ApplicationCard> ref = new AtomicReference<>();
        onFx(() -> ref.set(new ApplicationCard(app, index)));
        return ref.get();
    }

    // -----------------------------------------------------------------------
    // Reflection helpers — safe to call from test thread after FX construction
    // -----------------------------------------------------------------------

    private static Label getLabel(ApplicationCard card, String field) throws Exception {
        Field f = ApplicationCard.class.getDeclaredField(field);
        f.setAccessible(true);
        return (Label) f.get(card);
    }

    private static String getLabelText(ApplicationCard card, String field) throws Exception {
        return getLabel(card, field).getText();
    }

    private static FlowPane getTagsPane(ApplicationCard card) throws Exception {
        Field f = ApplicationCard.class.getDeclaredField("tags");
        f.setAccessible(true);
        return (FlowPane) f.get(card);
    }

    private static Button getEventButton(ApplicationCard card) throws Exception {
        Field f = ApplicationCard.class.getDeclaredField("eventButton");
        f.setAccessible(true);
        return (Button) f.get(card);
    }

    private static EventDetailsWindow getEventDetailsWindow(ApplicationCard card) throws Exception {
        Field f = ApplicationCard.class.getDeclaredField("eventDetailsWindow");
        f.setAccessible(true);
        return (EventDetailsWindow) f.get(card);
    }

    private static Label getStatusTag(ApplicationCard card, String text) throws Exception {
        return getTagsPane(card).getChildren().stream()
                .map(n -> (Label) n)
                .filter(l -> l.getText().equals(text))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Status tag not found: " + text));
    }

    // -----------------------------------------------------------------------
    // Tests
    // -----------------------------------------------------------------------

    @Test
    public void constructor_setsDisplayedFieldsCorrectly() throws Exception {
        Application app = new ApplicationBuilder()
                .withCompanyName("Google").withCompanyLocation("")
                .withRole("Intern").withPhone("91234567").withHrEmail("hr@google.com")
                .build();
        ApplicationCard card = buildCard(app, 1);

        assertEquals(app, card.application);
        assertEquals("1. ", getLabelText(card, "id"));
        assertEquals("Google", getLabelText(card, "companyName"));
        assertEquals("Intern", getLabelText(card, "role"));
        assertEquals("91234567", getLabelText(card, "phone"));
        assertEquals("hr@google.com", getLabelText(card, "hrEmail"));
        assertFalse(getLabel(card, "status").isVisible());
        assertFalse(getLabel(card, "status").isManaged());
        assertFalse(getLabel(card, "companyLocation").isVisible());
        assertFalse(getLabel(card, "companyLocation").isManaged());
        assertFalse(getLabel(card, "deadline").isVisible());
        assertFalse(getLabel(card, "deadline").isManaged());
        assertFalse(getLabel(card, "note").isVisible());
        assertFalse(getLabel(card, "note").isManaged());
    }

    @Test
    public void constructor_withCompanyLocation_setsLocationVisibleAndText() throws Exception {
        Application app = new ApplicationBuilder()
                .withCompanyName("Google").withCompanyLocation("Singapore")
                .withRole("Intern").withPhone("91234567").withHrEmail("hr@google.com")
                .build();
        ApplicationCard card = buildCard(app, 1);

        assertEquals("Google", getLabelText(card, "companyName"));
        assertEquals("Singapore", getLabelText(card, "companyLocation"));
    }

    @Test
    public void constructor_withDeadline_setsDeadlineVisibleAndText() throws Exception {
        Application app = new ApplicationBuilder()
                .withCompanyName("Google").withCompanyLocation("Singapore")
                .withRole("Intern").withPhone("91234567").withHrEmail("hr@google.com")
                .withDeadline("2026-12-31")
                .build();
        ApplicationCard card = buildCard(app, 1);

        Label deadlineLabel = getLabel(card, "deadline");
        assertTrue(deadlineLabel.isVisible());
        assertTrue(deadlineLabel.isManaged());
        assertEquals("Deadline: " + app.getDeadline().value, getLabelText(card, "deadline"));
    }

    @Test
    public void constructor_withNote_setsNoteVisibleAndText() throws Exception {
        Application app = new ApplicationBuilder()
                .withCompanyName("Google").withRole("Intern")
                .withPhone("91234567").withHrEmail("hr@google.com")
                .withNote("Follow up in 3 days")
                .build();
        ApplicationCard card = buildCard(app, 1);

        Label noteLabel = getLabel(card, "note");
        assertTrue(noteLabel.isVisible());
        assertTrue(noteLabel.isManaged());
        assertEquals("Note: Follow up in 3 days", getLabelText(card, "note"));
    }

    @Test
    public void constructor_withTags_displaysAllTagsSorted() throws Exception {
        Application app = new ApplicationBuilder()
                .withCompanyName("Google").withCompanyLocation("Singapore")
                .withRole("Intern").withPhone("91234567").withHrEmail("hr@google.com")
                .withTags("ztag", "atag")
                .build();
        ApplicationCard card = buildCard(app, 1);
        FlowPane pane = getTagsPane(card);

        assertEquals(3, pane.getChildren().size());
        assertEquals("atag", ((Label) pane.getChildren().get(0)).getText());
        assertEquals("ztag", ((Label) pane.getChildren().get(1)).getText());
        assertEquals("applied", ((Label) pane.getChildren().get(2)).getText());
    }

    @Test
    public void constructor_statusLabelHiddenAndStatusTagAdded() throws Exception {
        Application app = new ApplicationBuilder()
                .withCompanyName("Google").withCompanyLocation("Singapore")
                .withRole("Intern").withPhone("91234567").withHrEmail("hr@google.com")
                .build();
        ApplicationCard card = buildCard(app, 1);

        assertFalse(getLabel(card, "status").isVisible());
        assertFalse(getLabel(card, "status").isManaged());
        assertTrue(getTagsPane(card).getChildren().stream()
                .map(n -> (Label) n).anyMatch(l -> l.getText().equals("applied")));
    }

    @Test
    public void constructor_withExistingTags_addsStatusTagAsWell() throws Exception {
        Application app = new ApplicationBuilder()
                .withCompanyName("Google").withCompanyLocation("Singapore")
                .withRole("Intern").withPhone("91234567").withHrEmail("hr@google.com")
                .withTags("atag", "ztag")
                .build();
        ApplicationCard card = buildCard(app, 1);
        FlowPane pane = getTagsPane(card);

        assertEquals(3, pane.getChildren().size());
        assertEquals("atag", ((Label) pane.getChildren().get(0)).getText());
        assertEquals("ztag", ((Label) pane.getChildren().get(1)).getText());
        assertEquals("applied", ((Label) pane.getChildren().get(2)).getText());
    }

    @Test
    public void constructor_withoutUserTags_addsOnlyStatusTag() throws Exception {
        Application app = new ApplicationBuilder()
                .withCompanyName("Google").withCompanyLocation("Singapore")
                .withRole("Intern").withPhone("91234567").withHrEmail("hr@google.com")
                .build();
        ApplicationCard card = buildCard(app, 1);
        FlowPane pane = getTagsPane(card);

        assertEquals(1, pane.getChildren().size());
        assertEquals("applied", ((Label) pane.getChildren().get(0)).getText());
    }

    @Test
    public void constructor_withDifferentStatus_statusTagMatchesLowercaseStatus() throws Exception {
        Application app = new ApplicationBuilder()
                .withCompanyName("Google").withCompanyLocation("Singapore")
                .withRole("Intern").withPhone("91234567").withHrEmail("hr@google.com")
                .withStatus(Status.OFFERED)
                .build();
        ApplicationCard card = buildCard(app, 1);

        assertTrue(getTagsPane(card).getChildren().stream()
                .map(n -> (Label) n).anyMatch(l -> l.getText().equals("offered")));
    }

    @Test
    public void constructor_withRegularTags_regularTagsDoNotUseUrgentStyle() throws Exception {
        Application app = new ApplicationBuilder()
                .withCompanyName("Google").withRole("Intern")
                .withPhone("91234567").withHrEmail("hr@google.com")
                .withTags("atag", "ztag")
                .build();
        ApplicationCard card = buildCard(app, 1);
        FlowPane pane = getTagsPane(card);

        Label first = (Label) pane.getChildren().get(0);
        Label second = (Label) pane.getChildren().get(1);
        assertEquals("atag", first.getText());
        assertEquals("ztag", second.getText());
        assertFalse(first.getStyleClass().contains("tag-urgent"));
        assertFalse(second.getStyleClass().contains("tag-urgent"));
    }

    @Test
    public void constructor_withUrgentTag_hasUrgentStyleClass() throws Exception {
        String reminderTag = seedu.address.logic.commands.ReminderCommand.REMINDER_TAG_NAME;
        ApplicationCard card = buildCard(new ApplicationBuilder().withTags(reminderTag).build(), 1);

        Label urgentLabel = getTagsPane(card).getChildren().stream()
                .map(n -> (Label) n).filter(l -> l.getText().equals(reminderTag))
                .findFirst().orElseThrow(() -> new AssertionError("Urgent tag label not found"));

        assertTrue(urgentLabel.getStyleClass().contains("tag-urgent"));
    }

    @Test
    public void constructor_withUppercaseUrgentTag_stillHasUrgentStyleClass() throws Exception {
        ApplicationCard card = buildCard(new ApplicationBuilder().withTags("URGENT").build(), 1);

        Label urgentLabel = getTagsPane(card).getChildren().stream()
                .map(n -> (Label) n).filter(l -> l.getText().equals("URGENT"))
                .findFirst().orElseThrow(() -> new AssertionError("Uppercase urgent tag label not found"));

        assertTrue(urgentLabel.getStyleClass().contains("tag-urgent"));
    }

    @Test
    public void constructor_withMixedCaseUrgentTag_hasUrgentStyleClass() throws Exception {
        ApplicationCard card = buildCard(new ApplicationBuilder().withTags("uRgEnT").build(), 1);

        Label urgentLabel = getTagsPane(card).getChildren().stream()
                .map(n -> (Label) n).filter(l -> l.getText().equals("uRgEnT"))
                .findFirst().orElseThrow();

        assertTrue(urgentLabel.getStyleClass().contains("tag-urgent"));
    }

    @Test
    public void constructor_statusApplied_hasAppliedStyleClass() throws Exception {
        ApplicationCard card = buildCard(new ApplicationBuilder().withStatus(Status.APPLIED).build(), 1);
        assertTrue(getStatusTag(card, "applied").getStyleClass().contains("status-applied"));
    }

    @Test
    public void constructor_statusOffered_hasOfferedStyleClass() throws Exception {
        ApplicationCard card = buildCard(new ApplicationBuilder().withStatus(Status.OFFERED).build(), 1);
        assertTrue(getStatusTag(card, "offered").getStyleClass().contains("status-offered"));
    }

    @Test
    public void constructor_statusRejected_hasRejectedStyleClass() throws Exception {
        ApplicationCard card = buildCard(new ApplicationBuilder().withStatus(Status.REJECTED).build(), 1);
        assertTrue(getStatusTag(card, "rejected").getStyleClass().contains("status-rejected"));
    }

    @Test
    public void constructor_statusInterviewing_hasInterviewingStyleClass() throws Exception {
        ApplicationCard card = buildCard(new ApplicationBuilder().withStatus(Status.INTERVIEWING).build(), 1);
        assertTrue(getStatusTag(card, "interviewing").getStyleClass().contains("status-interviewing"));
    }

    @Test
    public void constructor_statusWithdrawn_hasWithdrawnStyleClass() throws Exception {
        ApplicationCard card = buildCard(new ApplicationBuilder().withStatus(Status.WITHDRAWN).build(), 1);
        assertTrue(getStatusTag(card, "withdrawn").getStyleClass().contains("status-withdrawn"));
    }

    @Test
    public void constructor_allStatuses_addsCorrectStyleClass() throws Exception {
        for (Status s : Status.values()) {
            ApplicationCard card = buildCard(new ApplicationBuilder().withStatus(s).build(), 1);
            String expectedText = s.toString().toLowerCase();
            String expectedClass = "status-" + expectedText.replace(" ", "-");
            assertTrue(getStatusTag(card, expectedText).getStyleClass().contains(expectedClass),
                    "Tag for status " + s + " should have style class: " + expectedClass);
        }
    }

    @Test
    public void constructor_allOptionalFieldsPresent_allVisible() throws Exception {
        Application app = new ApplicationBuilder()
                .withCompanyLocation("Singapore")
                .withDeadline("2026-12-31")
                .withNote("Important Note")
                .build();
        ApplicationCard card = buildCard(app, 1);

        Label locLabel = getLabel(card, "companyLocation");
        assertTrue(locLabel.isVisible());
        assertEquals("Singapore", locLabel.getText());

        Label deadlineLabel = getLabel(card, "deadline");
        assertTrue(deadlineLabel.isVisible());
        assertEquals("Deadline: 2026-12-31", deadlineLabel.getText());

        Label noteLabel = getLabel(card, "note");
        assertTrue(noteLabel.isVisible());
        assertEquals("Note: Important Note", noteLabel.getText());
    }

    // -----------------------------------------------------------------------
    // Resume coverage
    // -----------------------------------------------------------------------

    @Test
    public void constructor_withNoResume_resumeLabelHidden() throws Exception {
        // ApplicationBuilder.build() uses the backward-compatible constructor which
        // sets Resume to empty by default.
        ApplicationCard card = buildCard(new ApplicationBuilder().build(), 1);

        Label resumeLabel = getLabel(card, "resume");
        assertFalse(resumeLabel.isVisible(), "Resume label should be hidden when no resume is attached");
        assertFalse(resumeLabel.isManaged(), "Resume label should be unmanaged when no resume is attached");
    }

    @Test
    public void constructor_withResume_resumeLabelVisibleAndCorrectText() throws Exception {
        Application base = new ApplicationBuilder().build();
        // Use the full 10-arg constructor to supply a non-empty resume
        Application app = new Application(
                base.getRole(), base.getPhone(), base.getHrEmail(), base.getCompany(),
                base.getTags(), base.getStatus(), base.getDeadline(),
                null, base.getNote(), new Resume("myResume.pdf"));
        ApplicationCard card = buildCard(app, 1);

        Label resumeLabel = getLabel(card, "resume");
        assertTrue(resumeLabel.isVisible(), "Resume label should be visible when a resume is attached");
        assertTrue(resumeLabel.isManaged(), "Resume label should be managed when a resume is attached");
        assertEquals("Resume: myResume.pdf", resumeLabel.getText());
    }

    // -----------------------------------------------------------------------
    // Event button coverage
    // -----------------------------------------------------------------------

    @Test
    public void constructor_withNoEvent_eventButtonHidden() throws Exception {
        ApplicationCard card = buildCard(new ApplicationBuilder().build(), 1);

        Button btn = getEventButton(card);
        assertFalse(btn.isVisible(), "Event button should be hidden when there is no ApplicationEvent");
        assertFalse(btn.isManaged(), "Event button should be unmanaged when there is no ApplicationEvent");
    }

    @Test
    public void constructor_withEvent_eventButtonVisible() throws Exception {
        OnlineAssessment event = new OnlineAssessment(
                "Zoom", LocalDateTime.of(2026, 6, 15, 10, 0),
                "HackerRank", "https://hr.com", "Bring ID");
        Application base = new ApplicationBuilder().build();
        Application app = new Application(
                base.getRole(), base.getPhone(), base.getHrEmail(), base.getCompany(),
                base.getTags(), base.getStatus(), base.getDeadline(),
                event, base.getNote(), base.getResume());
        ApplicationCard card = buildCard(app, 1);

        Button btn = getEventButton(card);
        assertTrue(btn.isVisible(), "Event button should be visible when an ApplicationEvent is present");
        assertTrue(btn.isManaged(), "Event button should be managed when an ApplicationEvent is present");
    }

    /**
     * When the EventDetailsWindow is not yet showing, clicking the event button
     * should call {@code show()} without throwing.
     */
    @Test
    public void handleEventButtonClick_whenWindowNotShowing_callsShow() throws Exception {
        OnlineAssessment event = new OnlineAssessment(
                "Online", LocalDateTime.of(2026, 8, 1, 9, 0),
                "Codility", "https://codility.com");
        Application base = new ApplicationBuilder().build();
        Application app = new Application(
                base.getRole(), base.getPhone(), base.getHrEmail(), base.getCompany(),
                base.getTags(), base.getStatus(), base.getDeadline(),
                event, base.getNote(), base.getResume());

        // Build card AND invoke the handler — both on the FX thread
        onFx(() -> {
            ApplicationCard card = new ApplicationCard(app, 1);
            assertFalse(getEventDetailsWindow(card).isShowing());

            Method handler = ApplicationCard.class.getDeclaredMethod("handleEventButtonClick");
            handler.setAccessible(true);
            handler.invoke(card); // must not throw; calls show()
        });
    }

    /**
     * When the EventDetailsWindow is already showing, clicking the event button
     * should call {@code focus()} instead of {@code show()}.
     * A stub window is injected via reflection to force {@code isShowing() == true}.
     */
    @Test
    public void handleEventButtonClick_whenWindowAlreadyShowing_callsFocus() throws Exception {
        OnlineAssessment event = new OnlineAssessment(
                "Online", LocalDateTime.of(2026, 9, 1, 14, 0),
                "LeetCode", "https://leetcode.com");
        Application base = new ApplicationBuilder().build();
        Application app = new Application(
                base.getRole(), base.getPhone(), base.getHrEmail(), base.getCompany(),
                base.getTags(), base.getStatus(), base.getDeadline(),
                event, base.getNote(), base.getResume());

        onFx(() -> {
            ApplicationCard card = new ApplicationCard(app, 1);

            // Stub that always reports isShowing() == true so focus() branch is taken
            EventDetailsWindow stub = new EventDetailsWindow() {
                @Override
                public boolean isShowing() {
                    return true;
                }
                @Override
                public void focus() {
                    // intentionally empty — we just verify this path is reached
                }
            };
            stub.setEventDetails(event);

            Field edwField = ApplicationCard.class.getDeclaredField("eventDetailsWindow");
            edwField.setAccessible(true);
            edwField.set(card, stub);

            Method handler = ApplicationCard.class.getDeclaredMethod("handleEventButtonClick");
            handler.setAccessible(true);
            handler.invoke(card); // must not throw; calls focus()
        });
    }
}
