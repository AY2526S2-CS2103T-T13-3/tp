package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.application.Application;

/**
 * An UI component that displays information of a {@code Application}.
 */
public class ApplicationCard extends UiPart<Region> {

    private static final String FXML = "ApplicationListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

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

    /**
     * Creates a {@code ApplicationCard} with the given {@code Application} and index to display.
     */
    public ApplicationCard(Application application, int displayedIndex) {
        super(FXML);
        this.application = application;
        id.setText(displayedIndex + ". ");
        role.setText(application.getRole().roleName);
        phone.setText(application.getPhone().value);
        hrEmail.setText(application.getHrEmail().value);
        companyName.setText(application.getCompany().companyName);
        deadline.setText("Deadline: " + application.getDeadline().value);

        // company location
        String loc = application.getCompany().companyLocation;
        if (loc.isEmpty()) {
            companyLocation.setVisible(false);
            companyLocation.setManaged(false);
        } else {
            companyLocation.setText(loc);
        }

        // deadline
        String deadlineValue = application.getDeadline().value;
        if (application.getDeadline().isEmpty()) {
            deadline.setVisible(false);
            deadline.setManaged(false);
        } else {
            deadline.setText("Deadline: " + deadlineValue);
        }

        // status.setText("Status: " + application.getStatus().toString());
        status.setVisible(false);
        status.setManaged(false);

        application.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));

        String statusText = application.getStatus().toString().toLowerCase();
        Label statusTag = new Label(statusText);
        tags.getChildren().add(statusTag);
    }
}
