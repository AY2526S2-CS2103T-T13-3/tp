package seedu.address.model.application;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that an {@code Application}'s note matches any of the keywords given.
 */
public class NoteContainsKeywordsPredicate implements Predicate<Application> {
    private final List<String> keywords;

    public NoteContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Application application) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsSubstringIgnoreCase(application.getNote().toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof NoteContainsKeywordsPredicate)) {
            return false;
        }

        NoteContainsKeywordsPredicate otherPredicate = (NoteContainsKeywordsPredicate) other;
        return keywords.equals(otherPredicate.keywords);
    }
}
