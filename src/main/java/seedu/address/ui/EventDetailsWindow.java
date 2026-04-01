package seedu.address.ui;

import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.application.ApplicationEvent;
import seedu.address.model.application.OnlineAssessment;

/**
 * Window that displays the details of an {@code ApplicationEvent}.
 */
public class EventDetailsWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(EventDetailsWindow.class);
    private static final String FXML = "EventDetailsWindow.fxml";
    private static final DateTimeFormatter DISPLAY_FORMATTER =
            DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm");

    @FXML
    private Label titleLabel;
    @FXML
    private Label locationLabel;
    @FXML
    private Label dateTimeLabel;
    @FXML
    private Label platformLabel;
    @FXML
    private Label linkLabel;
    @FXML
    private Label notesLabel;

    /**
     * Creates a new {@code EventDetailsWindow}.
     *
     * @param root Stage to use as the root of the window.
     */
    public EventDetailsWindow(Stage root) {
        super(FXML, root);
    }

    /**
     * Creates a new {@code EventDetailsWindow}.
     */
    public EventDetailsWindow() {
        this(new Stage());
    }

    /**
     * Populates the window with details from the given {@code ApplicationEvent}.
     *
     * @param event the event whose details are to be displayed.
     */
    public void setEventDetails(ApplicationEvent event) {
        locationLabel.setText(event.getLocation());
        dateTimeLabel.setText(event.getLocalDate().format(DISPLAY_FORMATTER));

        if (event instanceof OnlineAssessment oa) {
            titleLabel.setText("Online Assessment Details");
            platformLabel.setText(oa.getPlatform());
            linkLabel.setText(oa.getLink());
            notesLabel.setText(oa.getNotes());
        } else {
            // Fallback for future ApplicationEvent subclasses
            titleLabel.setText("Event Details");
            platformLabel.setText("N/A");
            linkLabel.setText("N/A");
            notesLabel.setText("N/A");
        }
    }

    /**
     * Shows the event details window.
     */
    public void show() {
        logger.fine("Showing event details window.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the event details window is currently showing.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Focuses on the event details window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Hides the event details window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Handles the close button click.
     */
    @FXML
    private void handleClose() {
        hide();
    }
}
