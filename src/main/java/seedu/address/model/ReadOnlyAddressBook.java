package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.application.Application;

/**
 * Unmodifiable view of an Hired!
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the applications list.
     * This list will not contain any duplicate applications.
     */
    ObservableList<Application> getApplicationList();

}
