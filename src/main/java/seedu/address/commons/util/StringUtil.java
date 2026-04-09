package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;

/**
 * Helper functions for handling strings.
 */
public class StringUtil {

    /**
     * Returns true if the {@code sentence} contains the {@code word}.
     *   Ignores case, but a full word match is required.
     *   <br>examples:<pre>
     *       containsWordIgnoreCase("ABc def", "abc") == true
     *       containsWordIgnoreCase("ABc def", "DEF") == true
     *       containsWordIgnoreCase("ABc def", "AB") == false //not a full word match
     *       </pre>
     * @param sentence cannot be null
     * @param word cannot be null, cannot be empty, must be a single word
     */
    public static boolean containsWordIgnoreCase(String sentence, String word) {
        requireNonNull(sentence);
        requireNonNull(word);

        String preppedWord = word.trim();
        checkArgument(!preppedWord.isEmpty(), "Word parameter cannot be empty");
        checkArgument(preppedWord.split("\\s+").length == 1, "Word parameter should be a single word");

        String preppedSentence = sentence;
        String[] wordsInPreppedSentence = preppedSentence.split("\\s+");

        return Arrays.stream(wordsInPreppedSentence)
                .anyMatch(preppedWord::equalsIgnoreCase);
    }

    /**
     * Checks if the {@code sentence} contains all keywords provided in the {@code input} string.
     * Each keyword is treated as a case-insensitive substring. This is particularly useful
     * for flexible searches (e.g., searching "sof dev" to find "Software Developer").
     * * <p>Data Normalization:
     * <ul>
     * <li>The {@code input} is trimmed and split by one or more whitespace characters.</li>
     * <li>Comparison is strictly case-insensitive.</li>
     * </ul>
     * * <p>Example usage:
     * <pre>
     * containsAllKeywordsAsSubstrings("Software Engineer", "soft eng") == true
     * containsAllKeywordsAsSubstrings("Google Singapore", "Goo SING") == true
     * containsAllKeywordsAsSubstrings("Frontend", "back") == false
     * </pre>
     * * @param sentence The target text to be searched. Must not be null.
     * @param input A string containing one or more whitespace-separated keywords. Must not be null.
     * @return True if every keyword in the input is found as a substring within the sentence.
     * @throws NullPointerException if {@code sentence} or {@code input} is null.
     */
    public static boolean containsAllKeywordsAsSubstrings(String sentence, String input) {
        requireNonNull(sentence);
        requireNonNull(input);

        String preppedKeyword = input.trim().toLowerCase();
        checkArgument(!preppedKeyword.isEmpty(), "Keyword parameter cannot be empty");
        String[] keywords = preppedKeyword.split("\\s+");

        String lowerSentence = sentence.toLowerCase();

        // Ensure every keyword exists within the sentence (AND logic)
        return Arrays.stream(keywords)
                .allMatch(keyword -> lowerSentence.contains(keyword));
    }

    /**
     * Returns a detailed message of the t, including the stack trace.
     */
    public static String getDetails(Throwable t) {
        requireNonNull(t);
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        return t.getMessage() + "\n" + sw.toString();
    }

    /**
     * Returns true if {@code s} represents a non-zero unsigned integer
     * e.g. 1, 2, 3, ..., {@code Integer.MAX_VALUE} <br>
     * Will return false for any other non-null string input
     * e.g. empty string, "-1", "0", "+1", and " 2 " (untrimmed), "3 0" (contains whitespace), "1 a" (contains letters)
     * @throws NullPointerException if {@code s} is null.
     */
    public static boolean isNonZeroUnsignedInteger(String s) {
        requireNonNull(s);

        try {
            int value = Integer.parseInt(s);
            return value > 0 && !s.startsWith("+"); // "+1" is successfully parsed by Integer#parseInt(String)
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
}
