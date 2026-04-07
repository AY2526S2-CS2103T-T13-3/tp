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
        // Equality case: same values, different values, different types, and null
        NoteContainsKeywordsPredicate firstPredicate =
                new NoteContainsKeywordsPredicate(Collections.singletonList("follow"));
        NoteContainsKeywordsPredicate secondPredicate =
                new NoteContainsKeywordsPredicate(Collections.singletonList("recruiter"));

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        assertTrue(firstPredicate.equals(new NoteContainsKeywordsPredicate(Collections.singletonList("follow"))));

        // different type -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different values -> returns false
        // Same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // Same values -> returns true
        assertTrue(firstPredicate.equals(new NoteContainsKeywordsPredicate(Collections.singletonList("follow"))));

        // Different type -> returns false
        assertFalse(firstPredicate.equals(1));

        // Null -> returns false
        assertFalse(firstPredicate.equals(null));

        // Different predicate -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_noteContainsKeywords_returnsTrue() {
        // EP: note contains one of the given keywords -> returns true
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

        // EP: partial keyword match is accepted
        NoteContainsKeywordsPredicate partialPredicate =
                new NoteContainsKeywordsPredicate(Arrays.asList("foll", "RECRUIT"));

        assertTrue(partialPredicate.test(applicationWithFollow));
        assertTrue(partialPredicate.test(applicationWithRecruiter));
    }

    @Test
    public void test_noteDoesNotContainKeywords_returnsFalse() {
        // EP: note contains none of the keywords -> returns false
        NoteContainsKeywordsPredicate predicate =
                new NoteContainsKeywordsPredicate(Arrays.asList("follow", "recruiter"));

        Application application = new ApplicationBuilder(GOOGLE_SWE)
                .withNote("Send thank you email")
                .build();

        assertFalse(predicate.test(application));
    }

    @Test
    public void test_emptyNote_returnsFalse() {
        // EP: empty note -> returns false
        NoteContainsKeywordsPredicate predicate =
                new NoteContainsKeywordsPredicate(Collections.singletonList("follow"));

        Application application = new ApplicationBuilder(GOOGLE_SWE)
                .withNote("")
                .build();

        assertFalse(predicate.test(application));
    }

    @Test
    public void test_caseInsensitiveMatch_returnsTrue() {
        // EP: matching is case-insensitive -> returns true
        NoteContainsKeywordsPredicate predicate =
                new NoteContainsKeywordsPredicate(Arrays.asList("FOLLOW", "ReCrUiTeR"));

        Application applicationWithLowercase = new ApplicationBuilder(GOOGLE_SWE)
                .withNote("follow up after interview")
                .build();

        Application applicationWithMixedCase = new ApplicationBuilder(META_DA)
                .withNote("Met Recruiter during networking session")
                .build();

        assertTrue(predicate.test(applicationWithLowercase));
        assertTrue(predicate.test(applicationWithMixedCase));
    }

    @Test
    public void test_multipleKeywordsOneMatch_returnsTrue() {
        // EP: multiple keywords provided, one matching keyword is enough -> returns true
        NoteContainsKeywordsPredicate predicate =
                new NoteContainsKeywordsPredicate(Arrays.asList("deadline", "recruiter", "offer"));

        Application application = new ApplicationBuilder(META_DA)
                .withNote("Met recruiter at career fair")
                .build();

        assertTrue(predicate.test(application));
    }

    @Test
    public void test_emptyKeywordList_returnsFalse() {
        // EP: no keywords provided -> returns false
        NoteContainsKeywordsPredicate predicate =
                new NoteContainsKeywordsPredicate(Collections.emptyList());

        Application application = new ApplicationBuilder(GOOGLE_SWE)
                .withNote("Follow up next Monday")
                .build();

        assertFalse(predicate.test(application));
    }
}
