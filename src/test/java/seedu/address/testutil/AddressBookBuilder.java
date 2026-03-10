package seedu.company.testutil;

import seedu.company.model.CompanyBook;
import seedu.company.model.application.Application;

/**
 * A utility class to help with building Companybook objects.
 * Example usage: <br>
 *     {@code CompanyBook ab = new CompanyBookBuilder().withPerson("John", "Doe").build();}
 */
public class CompanyBookBuilder {

    private CompanyBook companyBook;

    public CompanyBookBuilder() {
        companyBook = new CompanyBook();
    }

    public CompanyBookBuilder(CompanyBook companyBook) {
        this.companyBook = companyBook;
    }

    /**
     * Adds a new {@code Person} to the {@code CompanyBook} that we are building.
     */
    public CompanyBookBuilder withPerson(Application application) {
        companyBook.addPerson(application);
        return this;
    }

    public CompanyBook build() {
        return companyBook;
    }
}
