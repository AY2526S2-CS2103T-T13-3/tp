package seedu.address.testutil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.application.Application;

/**
 * A utility class for test cases.
 */
public class TestUtil {

    /**
     * Folder used for temp files created during testing. Ignored by Git.
     */
    private static final Path SANDBOX_FOLDER = Paths.get("src", "test", "data", "sandbox");

    /**
     * Appends {@code fileRole} to the sandbox folder path and returns the resulting path.
     * Creates the sandbox folder if it doesn't exist.
     */
    public static Path getFilePathInSandboxFolder(String fileRole) {
        try {
            Files.createDirectories(SANDBOX_FOLDER);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return SANDBOX_FOLDER.resolve(fileRole);
    }

    /**
     * Returns the middle index of the application in the {@code model}'s application list.
     */
    public static Index getMidIndex(Model model) {
        return Index.fromOneBased(model.getFilteredApplicationList().size() / 2);
    }

    /**
     * Returns the last index of the application in the {@code model}'s application list.
     */
    public static Index getLastIndex(Model model) {
        return Index.fromOneBased(model.getFilteredApplicationList().size());
    }

    /**
     * Returns the application in the {@code model}'s application list at {@code index}.
     */
    public static Application getApplication(Model model, Index index) {
        return model.getFilteredApplicationList().get(index.getZeroBased());
    }
}
