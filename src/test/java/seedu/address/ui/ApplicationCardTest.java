package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import seedu.address.model.application.Application;
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
        if (!latch.await(5, TimeUnit.SECONDS)) {
            throw new RuntimeException("The JavaFX toolkit has timed out during startup. "
                    + "Please check if the JDK architecture matches");
        }
    }

    @Test
    public void constructor_setsDisplayedFieldsCorrectly() throws Exception {
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

        Label companyLocationLabel = getLabel(applicationCard, "companyLocation");
        assertFalse(companyLocationLabel.isVisible());
        assertFalse(companyLocationLabel.isManaged());

        Label deadlineLabel = getLabel(applicationCard, "deadline");
        assertFalse(deadlineLabel.isVisible());
        assertFalse(deadlineLabel.isManaged());

        Label noteLabel = getLabel(applicationCard, "note");
        assertFalse(noteLabel.isVisible());
        assertFalse(noteLabel.isManaged());
    }

    @Test
    public void constructor_withCompanyLocation_setsLocationVisibleAndText() throws Exception {
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
    }

    @Test
    public void constructor_withDeadline_setsDeadlineVisibleAndText() throws Exception {
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
        assertEquals("Deadline: " + application.getDeadline().value, getLabelText(applicationCard, "deadline"));
    }

    @Test
    public void constructor_withNote_setsNoteVisibleAndText() throws Exception {
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
        assertEquals("Note: " + application.getNote().value, getLabelText(applicationCard, "note"));
    }

    @Test
    public void constructor_withTags_displaysAllTagsSorted() throws Exception {
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
    }

    @Test
    public void constructor_statusLabelHiddenAndStatusTagAdded() throws Exception {
        Application application = new ApplicationBuilder()
                .withCompanyName("Google")
                .withCompanyLocation("Singapore")
                .withRole("Intern")
                .withPhone("91234567")
                .withHrEmail("hr@google.com")
                .build();

        ApplicationCard applicationCard = new ApplicationCard(application, 1);

        Label statusLabel = getLabel(applicationCard, "status");
        FlowPane tagsPane = getTagsPane(applicationCard);

        assertFalse(statusLabel.isVisible());
        assertFalse(statusLabel.isManaged());

        assertTrue(tagsPane.getChildren().stream()
                .map(node -> (Label) node)
                .anyMatch(label -> label.getText().equals("applied")));
    }

    @Test
    public void constructor_withExistingTags_addsStatusTagAsWell() throws Exception {
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
    }

    @Test
    public void constructor_withoutUserTags_addsOnlyStatusTag() throws Exception {
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
    }

    @Test
    public void constructor_withDifferentStatus_statusTagMatchesLowercaseStatus() throws Exception {
        Application application = new ApplicationBuilder()
                .withCompanyName("Google")
                .withCompanyLocation("Singapore")
                .withRole("Intern")
                .withPhone("91234567")
                .withHrEmail("hr@google.com")
                .withStatus(Status.OFFERED)
                .build();

        ApplicationCard applicationCard = new ApplicationCard(application, 1);
        FlowPane tagsPane = getTagsPane(applicationCard);

        assertTrue(tagsPane.getChildren().stream()
                .map(node -> (Label) node)
                .anyMatch(label -> label.getText().equals("offered")));
    }

    @Test
    public void constructor_withRegularTags_regularTagsDoNotUseUrgentStyle() throws Exception {
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

        assertEquals("atag", firstTag.getText());
        assertEquals("ztag", secondTag.getText());
        assertFalse(firstTag.getStyleClass().contains("tag-urgent"));
        assertFalse(secondTag.getStyleClass().contains("tag-urgent"));
    }

    @Test
    public void constructor_withUrgentTag_hasUrgentStyleClass() throws Exception {
        String reminderTag = seedu.address.logic.commands.ReminderCommand.REMINDER_TAG_NAME;
        Application application = new ApplicationBuilder()
                .withTags(reminderTag)
                .build();

        ApplicationCard applicationCard = new ApplicationCard(application, 1);
        FlowPane tagsPane = getTagsPane(applicationCard);

        Label urgentLabel = (Label) tagsPane.getChildren().stream()
                .filter(node -> node instanceof Label)
                .map(node -> (Label) node)
                .filter(label -> label.getText().equals(reminderTag))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Urgent tag label not found"));

        assertTrue(urgentLabel.getStyleClass().contains("tag-urgent"),
                "Urgent tag should have 'tag-urgent' CSS class");
    }

    @Test
    public void constructor_withUppercaseUrgentTag_stillHasUrgentStyleClass() throws Exception {
        Application application = new ApplicationBuilder()
                .withTags("URGENT")
                .build();

        ApplicationCard applicationCard = new ApplicationCard(application, 1);
        FlowPane tagsPane = getTagsPane(applicationCard);

        Label urgentLabel = (Label) tagsPane.getChildren().stream()
                .filter(node -> node instanceof Label)
                .map(node -> (Label) node)
                .filter(label -> label.getText().equals("URGENT"))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Uppercase urgent tag label not found"));

        assertTrue(urgentLabel.getStyleClass().contains("tag-urgent"));
    }

    @Test
    public void constructor_statusApplied_hasAppliedStyleClass() throws Exception {
        Application application = new ApplicationBuilder().withStatus(Status.APPLIED).build();
        ApplicationCard applicationCard = new ApplicationCard(application, 1);

        Label statusTag = getStatusTag(applicationCard, "applied");
        assertTrue(statusTag.getStyleClass().contains("status-applied"),
                "Status tag should have 'status-applied' CSS class");
    }

    @Test
    public void constructor_statusOffered_hasOfferedStyleClass() throws Exception {
        Application application = new ApplicationBuilder().withStatus(Status.OFFERED).build();
        ApplicationCard applicationCard = new ApplicationCard(application, 1);

        Label statusTag = getStatusTag(applicationCard, "offered");
        assertTrue(statusTag.getStyleClass().contains("status-offered"),
                "Status tag should have 'status-offered' CSS class");
    }

    @Test
    public void constructor_statusRejected_hasRejectedStyleClass() throws Exception {
        Application application = new ApplicationBuilder().withStatus(Status.REJECTED).build();
        ApplicationCard applicationCard = new ApplicationCard(application, 1);

        Label statusTag = getStatusTag(applicationCard, "rejected");
        assertTrue(statusTag.getStyleClass().contains("status-rejected"),
                "Status tag should have 'status-rejected' CSS class");
    }

    @Test
    public void constructor_statusInterviewing_hasInterviewingStyleClass() throws Exception {
        Application application = new ApplicationBuilder().withStatus(Status.INTERVIEWING).build();
        ApplicationCard applicationCard = new ApplicationCard(application, 1);

        Label statusTag = getStatusTag(applicationCard, "interviewing");
        assertTrue(statusTag.getStyleClass().contains("status-interviewing"),
                "Status tag should have 'status-interviewing' CSS class");
    }

    @Test
    public void constructor_statusWithdrawn_hasWithdrawnStyleClass() throws Exception {
        Application application = new ApplicationBuilder().withStatus(Status.WITHDRAWN).build();
        ApplicationCard applicationCard = new ApplicationCard(application, 1);

        Label statusTag = getStatusTag(applicationCard, "withdrawn");
        assertTrue(statusTag.getStyleClass().contains("status-withdrawn"),
                "Status tag should have 'status-withdrawn' CSS class");
    }

    private String getLabelText(ApplicationCard card, String fieldName) throws Exception {
        Field field = ApplicationCard.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        Label label = (Label) field.get(card);
        return label.getText();
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

    private Label getStatusTag(ApplicationCard card, String statusText) throws Exception {
        FlowPane tagsPane = getTagsPane(card);
        return (Label) tagsPane.getChildren().stream()
                .filter(node -> node instanceof Label)
                .map(node -> (Label) node)
                .filter(label -> label.getText().equals(statusText))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Status tag not found: " + statusText));
    }

    @Test
    public void constructor_allStatuses_addsCorrectStyleClass() throws Exception {
        for (Status s : Status.values()) {
            Application application = new ApplicationBuilder().withStatus(s).build();
            ApplicationCard applicationCard = new ApplicationCard(application, 1);

            String expectedStatusText = s.toString().toLowerCase();
            String expectedStyleClass = "status-" + expectedStatusText.replace(" ", "-");

            Label statusTag = getStatusTag(applicationCard, expectedStatusText);
            assertTrue(statusTag.getStyleClass().contains(expectedStyleClass),
                    "Tag for status " + s + " should have style class: " + expectedStyleClass);
        }
    }

    @Test
    public void constructor_withMixedCaseUrgentTag_hasUrgentStyleClass() throws Exception {
        Application application = new ApplicationBuilder()
                .withTags("uRgEnT")
                .build();

        ApplicationCard applicationCard = new ApplicationCard(application, 1);
        FlowPane tagsPane = getTagsPane(applicationCard);

        Label urgentLabel = (Label) tagsPane.getChildren().stream()
                .filter(node -> node instanceof Label)
                .map(node -> (Label) node)
                .filter(label -> label.getText().equals("uRgEnT"))
                .findFirst()
                .orElseThrow();

        assertTrue(urgentLabel.getStyleClass().contains("tag-urgent"),
                "Mixed case 'uRgEnT' should still trigger 'tag-urgent' style");
    }

    @Test
    public void constructor_allOptionalFieldsPresent_allVisible() throws Exception {
        Application application = new ApplicationBuilder()
                .withCompanyLocation("Singapore")
                .withDeadline("2026-12-31")
                .withNote("Important Note")
                .build();

        ApplicationCard applicationCard = new ApplicationCard(application, 1);

        Label locLabel = getLabel(applicationCard, "companyLocation");
        assertTrue(locLabel.isVisible());
        assertEquals("Singapore", locLabel.getText());

        Label deadlineLabel = getLabel(applicationCard, "deadline");
        assertTrue(deadlineLabel.isVisible());
        assertEquals("Deadline: 2026-12-31", deadlineLabel.getText());

        Label noteLabel = getLabel(applicationCard, "note");
        assertTrue(noteLabel.isVisible());
        assertEquals("Note: Important Note", noteLabel.getText());
    }
}
