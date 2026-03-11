package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.application.Application;
import seedu.address.model.application.Company;
import seedu.address.model.application.HrEmail;
import seedu.address.model.application.Phone;
import seedu.address.model.application.Role;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Application[] getSampleApplications() {
        return new Application[] {
            new Application(new Role("Software Engineer"), new Phone("87438807"),
                        new HrEmail("hr@google.com"), new Company("Google"),
                        getTagSet("interview", "priority")),
            new Application(new Role("Data Analyst"), new Phone("99272758"),
                        new HrEmail("recruitment@meta.com"), new Company("Meta"),
                        getTagSet("applied", "pending")),
            new Application(new Role("Quantitative Researcher"), new Phone("93210283"),
                        new HrEmail("careers@janestreet.com"), new Company("Jane Street"),
                        getTagSet("rejected")),
            new Application(new Role("Frontend Developer"), new Phone("91031282"),
                        new HrEmail("jobs@shopee.com"), new Company("Shopee"),
                        getTagSet("offer")),
            new Application(new Role("Backend Engineer"), new Phone("92492021"),
                        new HrEmail("hr@tiktok.com"), new Company("TikTok"),
                        getTagSet("interview")),
            new Application(new Role("Product Manager"), new Phone("92624417"),
                        new HrEmail("talent@grab.com"), new Company("Grab"),
                        getTagSet("applied"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Application sampleApplication : getSampleApplications()) {
            sampleAb.addApplication(sampleApplication);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }
}
