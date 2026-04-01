package seedu.address.ui;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import seedu.address.model.application.Application;

/**
 * A UI component that displays information of a {@code Application}.
 */
public class ApplicationCard extends UiPart<Region> {

    private static final String FXML = "ApplicationListCard.fxml";
    private static final Color ICON_COLOR = Color.WHITE;
    private static final int ICON_SIZE = 14;
    private static final DateTimeFormatter DEADLINE_DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final Color ROLE_COLOR_DEFAULT = Color.WHITE;
    private static final Color ROLE_COLOR_URGENT = Color.web("#e53935");
    private static final Color ROLE_COLOR_OVERDUE = Color.web("#fb8c00");

    /** The application displayed by this card. */
    public final Application application;

    @FXML private HBox cardPane;
    @FXML private Label role;
    @FXML private Label id;
    @FXML private FlowPane tags;
    @FXML private Label deadline;
    @FXML private VBox detailsBox;

    /**
     * Creates a {@code ApplicationCard} with the given {@code Application} and index to display.
     */
    public ApplicationCard(Application application, int displayedIndex) {
        super(FXML);
        this.application = application;

        id.setText(displayedIndex + ". ");
        role.setText(application.getRole().roleName);
        LocalDateTime now = LocalDateTime.now();
        Color roleColor = getRoleColor(application, now);
        role.setStyle("-fx-text-fill: " + toHexColor(roleColor) + ";");

        if (application.getDeadline().isEmpty()) {
            deadline.setVisible(false);
            deadline.setManaged(false);
        } else {
            deadline.setText(application.getDeadline().value);
            deadline.setGraphic(calendarIcon(getDeadlineIconColor(application, now)));
        }

        String statusText = application.getStatus().toString();
        Label statusChip = new Label(toTitleCase(statusText));
        statusChip.getStyleClass().addAll("chip", "chip-status-" + toStatusKey(statusText));
        tags.getChildren().add(statusChip);

        application.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> {
                    Label chip = new Label(tag.tagName);
                    chip.getStyleClass().add("chip");
                    tags.getChildren().add(chip);
                });

        detailsBox.getChildren().add(
                iconRow(FontAwesomeSolid.BUILDING, application.getCompany().companyName));

        if (!application.getCompany().companyLocation.isEmpty()) {
            detailsBox.getChildren().add(
                    iconRow(FontAwesomeSolid.MAP_MARKER_ALT,
                            application.getCompany().companyLocation));
        }

        detailsBox.getChildren().add(
                iconRow(FontAwesomeSolid.PHONE, application.getPhone().value));

        detailsBox.getChildren().add(
                iconRow(FontAwesomeSolid.ENVELOPE, application.getHrEmail().value));

        if (!application.getNote().value.isEmpty()) {
            detailsBox.getChildren().add(
                    iconRow(FontAwesomeSolid.STICKY_NOTE, application.getNote().value));
        }

        if (!application.getResume().isEmpty()) {
            detailsBox.getChildren().add(
                    iconRow(FontAwesomeSolid.FILE_ALT, application.getResume().value));
        }
    }

    /**
     * Creates an HBox row with a FontAwesome icon and a text label.
     */
    private HBox iconRow(FontAwesomeSolid iconCode, String text) {
        FontIcon fi = new FontIcon(iconCode);
        fi.setIconSize(ICON_SIZE);
        fi.setIconColor(ICON_COLOR);

        Label lbl = new Label(text);
        lbl.getStyleClass().add("detail-label");

        HBox row = new HBox(8, fi, lbl);
        row.setAlignment(Pos.CENTER_LEFT);
        return row;
    }

    /**
     * Creates a red calendar icon for the deadline field.
     */
    private FontIcon calendarIcon(Color iconColor) {
        FontIcon fi = new FontIcon(FontAwesomeSolid.CALENDAR_ALT);
        fi.setIconSize(ICON_SIZE);
        fi.setIconColor(iconColor);
        return fi;
    }

    /**
     * Converts a status enum string to a CSS class key.
     * Example: "IN_PROGRESS" becomes "in-progress".
     */
    static String toStatusKey(String status) {
        if (status == null || status.isEmpty()) {
            return "";
        }
        return status.toLowerCase().replace("_", "-");
    }

    /**
     * Converts a string to title case, replacing underscores with spaces.
     * Example: "IN_PROGRESS" becomes "In progress".
     */
    static String toTitleCase(String s) {
        if (s == null || s.isEmpty()) {
            return s;
        }
        String lower = s.toLowerCase().replace("_", " ");
        return Character.toUpperCase(lower.charAt(0)) + lower.substring(1);
    }

    static String formatPhone(String value) {
        return value;
    }

    static String formatHrEmail(String value) {
        return value;
    }

    static String formatCompanyName(String value) {
        return value;
    }

    static String formatCompanyLocation(String value) {
        return value;
    }

    static String formatDeadline(String value) {
        return value;
    }

    static String formatNote(String value) {
        return value;
    }

    static String formatResume(String value) {
        return value;
    }

    static Color getRoleColor(Application application, LocalDateTime now) {
        LocalDateTime deadlineDateTime = parseDeadlineAsDateTime(application.getDeadline().value);
        if (deadlineDateTime == null) {
            return ROLE_COLOR_DEFAULT;
        }

        if (deadlineDateTime.isBefore(now)) {
            return ROLE_COLOR_OVERDUE;
        }

        if (!deadlineDateTime.isAfter(now.plusDays(3))) {
            return ROLE_COLOR_URGENT;
        }

        return ROLE_COLOR_DEFAULT;
    }

    static Color getDeadlineIconColor(Application application, LocalDateTime now) {
        Color roleColor = getRoleColor(application, now);
        if (ROLE_COLOR_OVERDUE.equals(roleColor)) {
            return ROLE_COLOR_OVERDUE;
        }
        return ROLE_COLOR_URGENT;
    }

    private static LocalDateTime parseDeadlineAsDateTime(String rawDeadline) {
        try {
            if (rawDeadline.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}")) {
                return LocalDateTime.parse(rawDeadline, DEADLINE_DATE_TIME_FORMATTER);
            }
            if (rawDeadline.matches("\\d{4}-\\d{2}-\\d{2}")) {
                return LocalDate.parse(rawDeadline).atTime(LocalTime.MAX);
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    private static String toHexColor(Color color) {
        int r = (int) Math.round(color.getRed() * 255);
        int g = (int) Math.round(color.getGreen() * 255);
        int b = (int) Math.round(color.getBlue() * 255);
        return String.format("#%02x%02x%02x", r, g, b);
    }
}
