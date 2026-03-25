package seedu.address.model.application;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalApplications.ALICE;
import static seedu.address.testutil.TypicalApplications.BENSON;

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
        NoteContainsKeywordsPredicate predicate =
                new NoteContainsKeywordsPredicate(Arrays.asList("follow", "recruiter"));

        Application applicationWithFollow = new ApplicationBuilder(ALICE)
                .withNote("Follow up next Monday")
                .build();

        Application applicationWithRecruiter = new ApplicationBuilder(BENSON)
                .withNote("Met recruiter at career fair")
                .build();

        assertTrue(predicate.test(applicationWithFollow));
        assertTrue(predicate.test(applicationWithRecruiter));
    }

    @Test
    public void test_noteDoesNotContainKeywords_returnsFalse() {
        NoteContainsKeywordsPredicate predicate =
                new NoteContainsKeywordsPredicate(Arrays.asList("follow", "recruiter"));

        Application application = new ApplicationBuilder(ALICE)
                .withNote("Send thank you email")
                .build();

        assertFalse(predicate.test(application));
    }

    @Test
    public void test_emptyNote_returnsFalse() {
        NoteContainsKeywordsPredicate predicate =
                new NoteContainsKeywordsPredicate(Collections.singletonList("follow"));

        Application application = new ApplicationBuilder(ALICE)
                .withNote("")
                .build();

        assertFalse(predicate.test(application));
    }
}
