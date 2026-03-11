package seedu.address.testutil;

import seedu.address.model.AddressBook;
import seedu.address.model.application.Application;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 * {@code AddressBook ab = new AddressBookBuilder().withApplication("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private AddressBook companyBook;

    public AddressBookBuilder() {
        companyBook = new AddressBook();
    }

    public AddressBookBuilder(AddressBook companyBook) {
        this.companyBook = companyBook;
    }

    /**
     * Adds a new {@code Application} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withApplication(Application application) {
        companyBook.addApplication(application);
        return this;
    }

    public AddressBook build() {
        return companyBook;
    }
}
