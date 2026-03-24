package seedu.address.model.application;



/**
 * Represents any event that is arranged during the application
 * Can either be an interview or an online assessment
 */
public abstract class ApplicationEvent {
    private final String location;

    /**
     * Constructs an ApplicationEvent class with dateTime and location parameters
     * @param location location of event
     */
    public ApplicationEvent(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }
}
