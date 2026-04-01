package seedu.address.ui;

import static seedu.address.logic.commands.ReminderCommand.REMINDER_TAG_NAME;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.application.Application;
import seedu.address.model.application.ApplicationEvent;

/**
 * A UI component that displays information of a {@code Application}.
 */
public class ApplicationCard extends UiPart<Region> {

    private static final String FXML = "ApplicationListCard.fxml";

    public final Application application;

    @FXML
    private HBox cardPane;
    @FXML
    private Label role;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label companyName;
    @FXML
    private Label companyLocation;
    @FXML
    private Label status;
    @FXML
    private Label hrEmail;
    @FXML
    private FlowPane tags;
    @FXML
    private Label deadline;
    @FXML
    private Label note;
    @FXML
    private Label resume;
    @FXML
    private Button eventButton;

    private final EventDetailsWindow eventDetailsWindow;

    /**
     * Creates a {@code ApplicationCard} with the given {@code Application} and index to display.
     */
    public ApplicationCard(Application application, int displayedIndex) {
        super(FXML);
        this.application = application;
        this.eventDetailsWindow = new EventDetailsWindow();

        id.setText(displayedIndex + ". ");
        role.setText(application.getRole().roleName);
        phone.setText(application.getPhone().value);
        hrEmail.setText(application.getHrEmail().value);
        companyName.setText(application.getCompany().companyName);

        if (application.getCompany().companyLocation.isEmpty()) {
            companyLocation.setVisible(false);
            companyLocation.setManaged(false);
        } else {
            companyLocation.setText(application.getCompany().companyLocation);
        }

        if (application.getDeadline().isEmpty()) {
            deadline.setVisible(false);
            deadline.setManaged(false);
        } else {
            deadline.setText("Deadline: " + application.getDeadline().value);
        }

        if (application.getNote().value.isEmpty()) {
            note.setVisible(false);
            note.setManaged(false);
        } else {
            note.setText("Note: " + application.getNote().value);
        }

        if (application.getResume().isEmpty()) {
            resume.setVisible(false);
            resume.setManaged(false);
        } else {
            resume.setText("Resume: " + application.getResume().value);
        }

        status.setVisible(false);
        status.setManaged(false);

        application.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> {
                    Label tagLabel = new Label(tag.tagName);
                    if (tag.tagName.equalsIgnoreCase(REMINDER_TAG_NAME)) {
                        tagLabel.getStyleClass().add("tag-urgent");
                    }
                    tags.getChildren().add(tagLabel);
                });

        String statusText = application.getStatus().toString().toLowerCase();
        Label statusTag = new Label(statusText);
        statusTag.getStyleClass().add("status-" + statusText.replace(" ", "-"));
        tags.getChildren().add(statusTag);

        // Show event button only if an ApplicationEvent exists
        ApplicationEvent event = application.getApplicationEvent();
        if (event != null) {
            eventButton.setVisible(true);
            eventButton.setManaged(true);
            eventDetailsWindow.setEventDetails(event);
        } else {
            eventButton.setVisible(false);
            eventButton.setManaged(false);
        }
    }

    /**
     * Handles click on the event button — shows or focuses the event details window.
     */
    @FXML
    private void handleEventButtonClick() {
        if (eventDetailsWindow.isShowing()) {
            eventDetailsWindow.focus();
        } else {
            eventDetailsWindow.show();
        }
    }
}
