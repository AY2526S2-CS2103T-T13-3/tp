package seedu.address.model.application;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Objects;

/**
 * Represents a Company's name in Hired!.
 * Guarantees: immutable; is valid as declared in {@link #isValidCompanyName(String)}
 */
public class Company {

    public static final String MESSAGE_CONSTRAINTS_NAME =
            "Company names can take any values, and it should not be blank";
    public static final String MESSAGE_CONSTRAINTS_LOCATION =
            "Location can be empty or contain letters, numbers, spaces, comma, period or dash.";

    /*
     * The first character of the company name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String NAME_VALIDATION_REGEX = "[^\\s].*";
    public static final String LOCATION_VALIDATION_REGEX = ".*";

    public final String companyName;
    public final String companyLocation;

    /**
     * Constructs a {@code Company}.
     *
     * @param companyName A valid company name.
     */
    public Company(String companyName, String companyLocation) {
        requireNonNull(companyName);
        checkArgument(isValidCompanyName(companyName), MESSAGE_CONSTRAINTS_NAME);
        checkArgument(isValidCompanyLocation(companyLocation), MESSAGE_CONSTRAINTS_LOCATION);
        this.companyName = companyName;
        this.companyLocation = companyLocation;
    }

    /**
     * Returns true if a given string is a valid company name.
     */
    public static boolean isValidCompanyName(String test) {
        return test.matches(NAME_VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string is a valid company location format.
     * can be postal code, specific places/country.
     */
    public static boolean isValidCompanyLocation(String location) {
        return location.matches(LOCATION_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return companyLocation.isEmpty()
                ? companyName
                : companyName + " (" + companyLocation + ")";
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Company)) {
            return false;
        }

        Company otherCompany = (Company) other;
        return companyName.equals(otherCompany.companyName)
                && companyLocation.equals(otherCompany.companyLocation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyName, companyLocation);
    }

}
