package seedu.address.model.application;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalApplications.GOOGLE_SWE;
import static seedu.address.testutil.TypicalApplications.META_DA;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.ApplicationBuilder;

public class NoteContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        NoteContainsKeywordsPredicate firstPredicate =
                new NoteContainsKeywordsPredicate(Collections.singletonList("follow"));
        NoteContainsKeywordsPredicate secondPredicate =
                new NoteContainsKeywordsPredicate(Collections.singletonList("recruiter"));

        assertTrue(firstPredicate.equals(firstPredicate));
        assertTrue(firstPredicate.equals(new NoteContainsKeywordsPredicate(Collections.singletonList("follow"))));
        assertFalse(firstPredicate.equals(1));
        assertFalse(firstPredicate.equals(null));
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_noteContainsKeywords_returnsTrue() {
        // Exact words match
        NoteContainsKeywordsPredicate predicate =
                new NoteContainsKeywordsPredicate(Arrays.asList("follow", "recruiter"));

        Application applicationWithFollow = new ApplicationBuilder(GOOGLE_SWE)
                .withNote("Follow up next Monday")
                .build();

        Application applicationWithRecruiter = new ApplicationBuilder(META_DA)
                .withNote("Met recruiter at career fair")
                .build();

        assertTrue(predicate.test(applicationWithFollow));
        assertTrue(predicate.test(applicationWithRecruiter));

        NoteContainsKeywordsPredicate partialPredicate =
                new NoteContainsKeywordsPredicate(Arrays.asList("foll", "RECRUIT"));

        assertTrue(partialPredicate.test(applicationWithFollow));
        assertTrue(partialPredicate.test(applicationWithRecruiter));
    }

    @Test
    public void test_noteDoesNotContainKeywords_returnsFalse() {
        NoteContainsKeywordsPredicate predicate =
                new NoteContainsKeywordsPredicate(Arrays.asList("follow", "recruiter"));

        Application application = new ApplicationBuilder(GOOGLE_SWE)
                .withNote("Send thank you email")
                .build();

        assertFalse(predicate.test(application));
    }

    @Test
    public void test_emptyNote_returnsFalse() {
        NoteContainsKeywordsPredicate predicate =
                new NoteContainsKeywordsPredicate(Collections.singletonList("follow"));

        Application application = new ApplicationBuilder(GOOGLE_SWE)
                .withNote("")
                .build();

        assertFalse(predicate.test(application));
    }
}
