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

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import seedu.address.model.application.Application;
import seedu.address.model.application.OnlineAssessment;
import seedu.address.model.application.Status;
import seedu.address.testutil.ApplicationBuilder;

public class ApplicationCardTest {

    private static final LocalDateTime VALID_DATETIME = LocalDateTime.of(2026, 12, 31, 23, 59);

    @BeforeAll
    public static void initJfxRuntime() throws Exception {
        System.setProperty("java.awt.headless", "true");
        System.setProperty("prism.order", "sw");
        System.setProperty("prism.text", "t2k");
        System.setProperty("testfx.robot", "glass");
        System.setProperty("testfx.headless", "true");
        System.setProperty("glass.platform", "Monocle");
        System.setProperty("monocle.platform", "Headless");

        CountDownLatch latch = new CountDownLatch(1);
        try {
            Platform.startup(() -> {
                // Prevent the FX toolkit from shutting down between tests
                Platform.setImplicitExit(false);
                latch.countDown();
            });
        } catch (IllegalStateException e) {
            // Platform already started by another test class — still set ImplicitExit
            Platform.runLater(() -> {
                Platform.setImplicitExit(false);
                latch.countDown();
            });
        }
        assertTrue(latch.await(10, TimeUnit.SECONDS), "JavaFX platform failed to start");
    }

    // ==================== Helper to run on FX thread ====================

    /**
     * Runs the given {@code action} on the JavaFX Application Thread and waits
     * for it to complete. Any exception thrown inside the action is rethrown
     * in the test thread.
     */
    private void runOnFxThread(ThrowingRunnable action) throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<Throwable> error = new AtomicReference<>();
        Platform.runLater(() -> {
            try {
                action.run();
            } catch (Throwable t) {
                error.set(t);
            } finally {
                latch.countDown();
            }
        });
        boolean completed = latch.await(15, TimeUnit.SECONDS);
        if (!completed) {
            throw new Exception("FX thread timed out — JavaFX toolkit may have shut down. "
                    + "Ensure Platform.setImplicitExit(false) is set in @BeforeAll.");
        }
        if (error.get() != null) {
            throw new Exception(error.get());
        }
    }

    @FunctionalInterface
    private interface ThrowingRunnable {
        void run() throws Exception;
    }

    // ==================== Tests ====================

    @Test
    public void constructor_setsDisplayedFieldsCorrectly() throws Exception {
        runOnFxThread(() -> {
            Application application = new ApplicationBuilder()
                    .withCompanyName("Google")
                    .withCompanyLocation("")
                    .withRole("Intern")
                    .withPhone("91234567")
                    .withHrEmail("hr@google.com")
                    .build();

            ApplicationCard applicationCard = new ApplicationCard(application, 1);

            assertEquals(application, applicationCard.application);
            assertEquals("1. ", getLabelText(applicationCard, "id"));
            assertEquals("Google", getLabelText(applicationCard, "companyName"));
            assertEquals("Intern", getLabelText(applicationCard, "role"));
            assertEquals("91234567", getLabelText(applicationCard, "phone"));
            assertEquals("hr@google.com", getLabelText(applicationCard, "hrEmail"));
            assertFalse(getLabel(applicationCard, "status").isVisible());
            assertFalse(getLabel(applicationCard, "status").isManaged());
            assertFalse(getLabel(applicationCard, "companyLocation").isVisible());
            assertFalse(getLabel(applicationCard, "companyLocation").isManaged());
            assertFalse(getLabel(applicationCard, "deadline").isVisible());
            assertFalse(getLabel(applicationCard, "deadline").isManaged());
            assertFalse(getLabel(applicationCard, "note").isVisible());
            assertFalse(getLabel(applicationCard, "note").isManaged());
        });
    }

    @Test
    public void constructor_withCompanyLocation_setsLocationVisibleAndText() throws Exception {
        runOnFxThread(() -> {
            Application application = new ApplicationBuilder()
                    .withCompanyName("Google")
                    .withCompanyLocation("Singapore")
                    .withRole("Intern")
                    .withPhone("91234567")
                    .withHrEmail("hr@google.com")
                    .build();

            ApplicationCard applicationCard = new ApplicationCard(application, 1);

            assertEquals("Google", getLabelText(applicationCard, "companyName"));
            assertEquals("Singapore", getLabelText(applicationCard, "companyLocation"));
        });
    }

    @Test
    public void constructor_withDeadline_setsDeadlineVisibleAndText() throws Exception {
        runOnFxThread(() -> {
            Application application = new ApplicationBuilder()
                    .withCompanyName("Google")
                    .withCompanyLocation("Singapore")
                    .withRole("Intern")
                    .withPhone("91234567")
                    .withHrEmail("hr@google.com")
                    .withDeadline("2026-12-31")
                    .build();

            ApplicationCard applicationCard = new ApplicationCard(application, 1);

            Label deadlineLabel = getLabel(applicationCard, "deadline");
            assertTrue(deadlineLabel.isVisible());
            assertTrue(deadlineLabel.isManaged());
            assertEquals("Deadline: " + application.getDeadline().value,
                    getLabelText(applicationCard, "deadline"));
        });
    }

    @Test
    public void constructor_withNote_setsNoteVisibleAndText() throws Exception {
        runOnFxThread(() -> {
            Application application = new ApplicationBuilder()
                    .withCompanyName("Google")
                    .withRole("Intern")
                    .withPhone("91234567")
                    .withHrEmail("hr@google.com")
                    .withNote("Follow up in 3 days")
                    .build();

            ApplicationCard applicationCard = new ApplicationCard(application, 1);

            Label noteLabel = getLabel(applicationCard, "note");
            assertTrue(noteLabel.isVisible());
            assertTrue(noteLabel.isManaged());
            assertEquals("Note: " + application.getNote().value,
                    getLabelText(applicationCard, "note"));
        });
    }

    @Test
    public void constructor_withTags_displaysAllTagsSorted() throws Exception {
        runOnFxThread(() -> {
            Application application = new ApplicationBuilder()
                    .withCompanyName("Google")
                    .withCompanyLocation("Singapore")
                    .withRole("Intern")
                    .withPhone("91234567")
                    .withHrEmail("hr@google.com")
                    .withTags("ztag", "atag")
                    .build();

            ApplicationCard applicationCard = new ApplicationCard(application, 1);

            FlowPane tagsPane = getTagsPane(applicationCard);
            assertEquals(3, tagsPane.getChildren().size());
            assertEquals("atag", ((Label) tagsPane.getChildren().get(0)).getText());
            assertEquals("ztag", ((Label) tagsPane.getChildren().get(1)).getText());
            assertEquals("applied", ((Label) tagsPane.getChildren().get(2)).getText());
        });
    }

    @Test
    public void constructor_statusLabelHiddenAndStatusTagAdded() throws Exception {
        runOnFxThread(() -> {
            Application application = new ApplicationBuilder()
                    .withCompanyName("Google")
                    .withCompanyLocation("Singapore")
                    .withRole("Intern")
                    .withPhone("91234567")
                    .withHrEmail("hr@google.com")
                    .build();

            ApplicationCard applicationCard = new ApplicationCard(application, 1);

            assertFalse(getLabel(applicationCard, "status").isVisible());
            assertFalse(getLabel(applicationCard, "status").isManaged());
            assertTrue(getTagsPane(applicationCard).getChildren().stream()
                    .map(node -> (Label) node)
                    .anyMatch(label -> label.getText().equals("applied")));
        });
    }

    @Test
    public void constructor_withExistingTags_addsStatusTagAsWell() throws Exception {
        runOnFxThread(() -> {
            Application application = new ApplicationBuilder()
                    .withCompanyName("Google")
                    .withCompanyLocation("Singapore")
                    .withRole("Intern")
                    .withPhone("91234567")
                    .withHrEmail("hr@google.com")
                    .withTags("atag", "ztag")
                    .build();

            ApplicationCard applicationCard = new ApplicationCard(application, 1);
            FlowPane tagsPane = getTagsPane(applicationCard);

            assertEquals(3, tagsPane.getChildren().size());
            assertEquals("atag", ((Label) tagsPane.getChildren().get(0)).getText());
            assertEquals("ztag", ((Label) tagsPane.getChildren().get(1)).getText());
            assertEquals("applied", ((Label) tagsPane.getChildren().get(2)).getText());
        });
    }

    @Test
    public void constructor_withoutUserTags_addsOnlyStatusTag() throws Exception {
        runOnFxThread(() -> {
            Application application = new ApplicationBuilder()
                    .withCompanyName("Google")
                    .withCompanyLocation("Singapore")
                    .withRole("Intern")
                    .withPhone("91234567")
                    .withHrEmail("hr@google.com")
                    .build();

            ApplicationCard applicationCard = new ApplicationCard(application, 1);
            FlowPane tagsPane = getTagsPane(applicationCard);

            assertEquals(1, tagsPane.getChildren().size());
            assertEquals("applied", ((Label) tagsPane.getChildren().get(0)).getText());
        });
    }

    @Test
    public void constructor_withDifferentStatus_statusTagMatchesLowercaseStatus() throws Exception {
        runOnFxThread(() -> {
            Application application = new ApplicationBuilder()
                    .withCompanyName("Google")
                    .withCompanyLocation("Singapore")
                    .withRole("Intern")
                    .withPhone("91234567")
                    .withHrEmail("hr@google.com")
                    .withStatus(Status.OFFERED)
                    .build();

            ApplicationCard applicationCard = new ApplicationCard(application, 1);

            assertTrue(getTagsPane(applicationCard).getChildren().stream()
                    .map(node -> (Label) node)
                    .anyMatch(label -> label.getText().equals("offered")));
        });
    }

    @Test
    public void constructor_withRegularTags_regularTagsDoNotUseUrgentStyle() throws Exception {
        runOnFxThread(() -> {
            Application application = new ApplicationBuilder()
                    .withCompanyName("Google")
                    .withRole("Intern")
                    .withPhone("91234567")
                    .withHrEmail("hr@google.com")
                    .withTags("atag", "ztag")
                    .build();

            ApplicationCard applicationCard = new ApplicationCard(application, 1);
            FlowPane tagsPane = getTagsPane(applicationCard);

            Label firstTag = (Label) tagsPane.getChildren().get(0);
            Label secondTag = (Label) tagsPane.getChildren().get(1);

            assertFalse(firstTag.getStyleClass().contains("tag-urgent"));
            assertFalse(secondTag.getStyleClass().contains("tag-urgent"));
        });
    }

    @Test
    public void constructor_withUrgentTag_hasUrgentStyleClass() throws Exception {
        runOnFxThread(() -> {
            String reminderTag = seedu.address.logic.commands.ReminderCommand.REMINDER_TAG_NAME;
            Application application = new ApplicationBuilder()
                    .withTags(reminderTag)
                    .build();

            ApplicationCard applicationCard = new ApplicationCard(application, 1);
            FlowPane tagsPane = getTagsPane(applicationCard);

            Label urgentLabel = tagsPane.getChildren().stream()
                    .filter(node -> node instanceof Label)
                    .map(node -> (Label) node)
                    .filter(label -> label.getText().equals(reminderTag))
                    .findFirst()
                    .orElseThrow(() -> new AssertionError("Urgent tag label not found"));

            assertTrue(urgentLabel.getStyleClass().contains("tag-urgent"));
        });
    }

    @Test
    public void constructor_withUppercaseUrgentTag_stillHasUrgentStyleClass() throws Exception {
        runOnFxThread(() -> {
            Application application = new ApplicationBuilder()
                    .withTags("URGENT")
                    .build();

            ApplicationCard applicationCard = new ApplicationCard(application, 1);
            FlowPane tagsPane = getTagsPane(applicationCard);

            Label urgentLabel = tagsPane.getChildren().stream()
                    .filter(node -> node instanceof Label)
                    .map(node -> (Label) node)
                    .filter(label -> label.getText().equals("URGENT"))
                    .findFirst()
                    .orElseThrow(() -> new AssertionError("Uppercase urgent tag label not found"));

            assertTrue(urgentLabel.getStyleClass().contains("tag-urgent"));
        });
    }

    @Test
    public void constructor_withMixedCaseUrgentTag_hasUrgentStyleClass() throws Exception {
        runOnFxThread(() -> {
            String reminderTag = seedu.address.logic.commands.ReminderCommand.REMINDER_TAG_NAME;
            String mixedCaseTag = reminderTag.substring(0, 1).toUpperCase()
                    + reminderTag.substring(1).toLowerCase();
            Application application = new ApplicationBuilder()
                    .withTags(mixedCaseTag)
                    .build();

            ApplicationCard applicationCard = new ApplicationCard(application, 1);
            FlowPane tagsPane = getTagsPane(applicationCard);

            Label urgentLabel = tagsPane.getChildren().stream()
                    .filter(node -> node instanceof Label)
                    .map(node -> (Label) node)
                    .filter(label -> label.getText().equalsIgnoreCase(reminderTag))
                    .findFirst()
                    .orElseThrow(() -> new AssertionError("Mixed case urgent tag label not found"));

            assertTrue(urgentLabel.getStyleClass().contains("tag-urgent"),
                    "Mixed case urgent tag should have 'tag-urgent' CSS class");
        });
    }

    @Test
    public void constructor_statusApplied_hasAppliedStyleClass() throws Exception {
        runOnFxThread(() -> {
            Application application = new ApplicationBuilder().withStatus(Status.APPLIED).build();
            ApplicationCard applicationCard = new ApplicationCard(application, 1);
            Label statusTag = getStatusTag(applicationCard, "applied");
            assertTrue(statusTag.getStyleClass().contains("status-applied"));
        });
    }

    @Test
    public void constructor_statusOffered_hasOfferedStyleClass() throws Exception {
        runOnFxThread(() -> {
            Application application = new ApplicationBuilder().withStatus(Status.OFFERED).build();
            ApplicationCard applicationCard = new ApplicationCard(application, 1);
            Label statusTag = getStatusTag(applicationCard, "offered");
            assertTrue(statusTag.getStyleClass().contains("status-offered"));
        });
    }

    @Test
    public void constructor_statusRejected_hasRejectedStyleClass() throws Exception {
        runOnFxThread(() -> {
            Application application = new ApplicationBuilder().withStatus(Status.REJECTED).build();
            ApplicationCard applicationCard = new ApplicationCard(application, 1);
            Label statusTag = getStatusTag(applicationCard, "rejected");
            assertTrue(statusTag.getStyleClass().contains("status-rejected"));
        });
    }

    @Test
    public void constructor_statusInterviewing_hasInterviewingStyleClass() throws Exception {
        runOnFxThread(() -> {
            Application application = new ApplicationBuilder().withStatus(Status.INTERVIEWING).build();
            ApplicationCard applicationCard = new ApplicationCard(application, 1);
            Label statusTag = getStatusTag(applicationCard, "interviewing");
            assertTrue(statusTag.getStyleClass().contains("status-interviewing"));
        });
    }

    @Test
    public void constructor_statusWithdrawn_hasWithdrawnStyleClass() throws Exception {
        runOnFxThread(() -> {
            Application application = new ApplicationBuilder().withStatus(Status.WITHDRAWN).build();
            ApplicationCard applicationCard = new ApplicationCard(application, 1);
            Label statusTag = getStatusTag(applicationCard, "withdrawn");
            assertTrue(statusTag.getStyleClass().contains("status-withdrawn"));
        });
    }

    @Test
    public void constructor_allStatuses_addsCorrectStyleClass() throws Exception {
        runOnFxThread(() -> {
            for (Status status : Status.values()) {
                Application application = new ApplicationBuilder()
                        .withStatus(status)
                        .build();
                ApplicationCard applicationCard = new ApplicationCard(application, 1);
                String expectedStyleClass = "status-" + status.toString().toLowerCase();
                Label statusTag = getStatusTag(applicationCard, status.toString().toLowerCase());
                assertTrue(statusTag.getStyleClass().contains(expectedStyleClass),
                        "Status tag should have '" + expectedStyleClass + "' CSS class");
            }
        });
    }

    @Test
    public void constructor_withNoApplicationEvent_eventButtonHidden() throws Exception {
        runOnFxThread(() -> {
            Application application = new ApplicationBuilder()
                    .withCompanyName("Google")
                    .withRole("Intern")
                    .withPhone("91234567")
                    .withHrEmail("hr@google.com")
                    .build();

            ApplicationCard applicationCard = new ApplicationCard(application, 1);
            Button eventButton = getEventButton(applicationCard);

            assertFalse(eventButton.isVisible());
            assertFalse(eventButton.isManaged());
        });
    }

    @Test
    public void constructor_withApplicationEvent_eventButtonVisible() throws Exception {
        runOnFxThread(() -> {
            OnlineAssessment event = new OnlineAssessment(
                    "home", VALID_DATETIME, "HackerRank", "www.hackerrank.com");
            Application application = new ApplicationBuilder()
                    .withCompanyName("Google")
                    .withRole("Intern")
                    .withPhone("91234567")
                    .withHrEmail("hr@google.com")
                    .withApplicationEvent(event)
                    .build();

            ApplicationCard applicationCard = new ApplicationCard(application, 1);
            Button eventButton = getEventButton(applicationCard);

            assertTrue(eventButton.isVisible());
            assertTrue(eventButton.isManaged());
        });
    }

    @Test
    public void constructor_withApplicationEventWithNotes_eventButtonVisible() throws Exception {
        runOnFxThread(() -> {
            OnlineAssessment event = new OnlineAssessment(
                    "office", VALID_DATETIME, "LeetCode", "www.leetcode.com", "bring ID");
            Application application = new ApplicationBuilder()
                    .withCompanyName("Google")
                    .withRole("Intern")
                    .withPhone("91234567")
                    .withHrEmail("hr@google.com")
                    .withApplicationEvent(event)
                    .build();

            ApplicationCard applicationCard = new ApplicationCard(application, 1);
            Button eventButton = getEventButton(applicationCard);

            assertTrue(eventButton.isVisible());
            assertTrue(eventButton.isManaged());
        });
    }

    @Test
    public void constructor_withNoApplicationEvent_eventDetailsWindowNotShowing() throws Exception {
        runOnFxThread(() -> {
            Application application = new ApplicationBuilder()
                    .withCompanyName("Google")
                    .withRole("Intern")
                    .withPhone("91234567")
                    .withHrEmail("hr@google.com")
                    .build();

            ApplicationCard applicationCard = new ApplicationCard(application, 1);
            EventDetailsWindow window = getEventDetailsWindow(applicationCard);

            assertFalse(window.isShowing());
        });
    }

    @Test
    public void constructor_withApplicationEvent_eventDetailsWindowNotShowingUntilClicked() throws Exception {
        runOnFxThread(() -> {
            OnlineAssessment event = new OnlineAssessment(
                    "home", VALID_DATETIME, "HackerRank", "www.hackerrank.com");
            Application application = new ApplicationBuilder()
                    .withCompanyName("Google")
                    .withRole("Intern")
                    .withPhone("91234567")
                    .withHrEmail("hr@google.com")
                    .withApplicationEvent(event)
                    .build();

            ApplicationCard applicationCard = new ApplicationCard(application, 1);
            EventDetailsWindow window = getEventDetailsWindow(applicationCard);

            assertFalse(window.isShowing());
        });
    }

    @Test
    public void constructor_allOptionalFieldsPresent_allVisible() throws Exception {
        runOnFxThread(() -> {
            OnlineAssessment event = new OnlineAssessment(
                    "home", VALID_DATETIME, "HackerRank", "www.hackerrank.com");
            Application application = new ApplicationBuilder()
                    .withCompanyName("Google")
                    .withCompanyLocation("Singapore")
                    .withRole("Intern")
                    .withPhone("91234567")
                    .withHrEmail("hr@google.com")
                    .withDeadline("2026-12-31")
                    .withNote("Follow up")
                    .withApplicationEvent(event)
                    .build();

            ApplicationCard applicationCard = new ApplicationCard(application, 1);

            assertTrue(getLabel(applicationCard, "companyLocation").isVisible());
            assertTrue(getLabel(applicationCard, "companyLocation").isManaged());
            assertTrue(getLabel(applicationCard, "deadline").isVisible());
            assertTrue(getLabel(applicationCard, "deadline").isManaged());
            assertTrue(getLabel(applicationCard, "note").isVisible());
            assertTrue(getLabel(applicationCard, "note").isManaged());
            assertTrue(getEventButton(applicationCard).isVisible());
            assertTrue(getEventButton(applicationCard).isManaged());
        });
    }

    @Test
    public void handleEventButtonClick_windowNotShowing_showsWindow() throws Exception {
        runOnFxThread(() -> {
            OnlineAssessment event = new OnlineAssessment(
                    "home", VALID_DATETIME, "HackerRank", "www.hackerrank.com");
            Application application = new ApplicationBuilder()
                    .withCompanyName("Google")
                    .withRole("Intern")
                    .withPhone("91234567")
                    .withHrEmail("hr@google.com")
                    .withApplicationEvent(event)
                    .build();

            ApplicationCard applicationCard = new ApplicationCard(application, 1);
            EventDetailsWindow window = getEventDetailsWindow(applicationCard);

            assertFalse(window.isShowing());

            invokeHandleEventButtonClick(applicationCard);

            assertTrue(window.isShowing());

            // Clean up
            window.hide();
        });
    }

    @Test
    public void handleEventButtonClick_windowAlreadyShowing_focusesWindow() throws Exception {
        runOnFxThread(() -> {
            OnlineAssessment event = new OnlineAssessment(
                    "home", VALID_DATETIME, "HackerRank", "www.hackerrank.com");
            Application application = new ApplicationBuilder()
                    .withCompanyName("Google")
                    .withRole("Intern")
                    .withPhone("91234567")
                    .withHrEmail("hr@google.com")
                    .withApplicationEvent(event)
                    .build();

            ApplicationCard applicationCard = new ApplicationCard(application, 1);
            EventDetailsWindow window = getEventDetailsWindow(applicationCard);

            // Show the window first
            window.show();
            assertTrue(window.isShowing());

            // Clicking again should focus — window stays showing
            invokeHandleEventButtonClick(applicationCard);
            assertTrue(window.isShowing());

            // Clean up
            window.hide();
        });
    }

    // ==================== Reflection helpers ====================

    private String getLabelText(ApplicationCard card, String fieldName) throws Exception {
        Field field = ApplicationCard.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        return ((Label) field.get(card)).getText();
    }

    private Label getLabel(ApplicationCard card, String fieldName) throws Exception {
        Field field = ApplicationCard.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        return (Label) field.get(card);
    }

    private FlowPane getTagsPane(ApplicationCard card) throws Exception {
        Field field = ApplicationCard.class.getDeclaredField("tags");
        field.setAccessible(true);
        return (FlowPane) field.get(card);
    }

    private Button getEventButton(ApplicationCard card) throws Exception {
        Field field = ApplicationCard.class.getDeclaredField("eventButton");
        field.setAccessible(true);
        return (Button) field.get(card);
    }

    private EventDetailsWindow getEventDetailsWindow(ApplicationCard card) throws Exception {
        Field field = ApplicationCard.class.getDeclaredField("eventDetailsWindow");
        field.setAccessible(true);
        return (EventDetailsWindow) field.get(card);
    }

    private void invokeHandleEventButtonClick(ApplicationCard card) throws Exception {
        Method method = ApplicationCard.class.getDeclaredMethod("handleEventButtonClick");
        method.setAccessible(true);
        method.invoke(card);
    }

    private Label getStatusTag(ApplicationCard card, String statusText) throws Exception {
        FlowPane tagsPane = getTagsPane(card);
        return tagsPane.getChildren().stream()
                .filter(node -> node instanceof Label)
                .map(node -> (Label) node)
                .filter(label -> label.getText().equals(statusText))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Status tag not found: " + statusText));
    }
}
