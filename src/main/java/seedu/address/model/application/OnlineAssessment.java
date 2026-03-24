package seedu.address.model.application;

import seedu.address.logic.parser.Prefix;

/**
 * Represents online assessment that is appointed during the application
 */
public class OnlineAssessment extends ApplicationEvent {
    public static final String EMPTY_NOTES_VALUE = "No notes set";
    private final String platform;
    private final String link;
    private final String notes;

    /**
     * Constructs an OnlineAssessment class
     * @param location location of online assessment
     * @param platform platform of online assessment
     * @param link link of online assessment
     * @param notes extra notes of online assessment
     */
    public OnlineAssessment(String location, String platform, String link, String notes) {
        super(location);
        this.platform = platform;
        this.link = link;
        this.notes = notes;
    }

    /**
     * Constructs an OnlineAssessment class
     * @param location location of online assessment
     * @param platform platform of online assessment
     * @param link link of online assessment
     */
    public OnlineAssessment(String location, String platform, String link) {
        super(location);
        this.platform = platform;
        this.link = link;
        this.notes = EMPTY_NOTES_VALUE;
    }

    public String getPlatform() {
        return platform;
    }

    public String getLink() {
        return link;
    }

    public String getNotes() {
        return notes;
    }
}

