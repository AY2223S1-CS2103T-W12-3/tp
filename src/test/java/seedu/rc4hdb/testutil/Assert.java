package seedu.rc4hdb.testutil;

import java.nio.file.Path;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.function.Executable;

import seedu.rc4hdb.commons.util.FileUtil;

/**
 * A set of assertion methods useful for writing tests.
 */
public class Assert {

    /**
     * Asserts that the {@code executable} throws the {@code expectedType} Exception.
     * This is a wrapper method that invokes {@link Assertions#assertThrows(Class, Executable)}, to maintain consistency
     * with our custom {@link #assertThrows(Class, String, Executable)} method.
     * To standardize API calls in this project, users should use this method instead of
     * {@link Assertions#assertThrows(Class, Executable)}.
     */
    public static void assertThrows(Class<? extends Throwable> expectedType, Executable executable) {
        Assertions.assertThrows(expectedType, executable);
    }

    /**
     * Asserts that the {@code executable} throws the {@code expectedType} Exception with the {@code expectedMessage}.
     * If there's no need for the verification of the exception's error message, call
     * {@link #assertThrows(Class, Executable)} instead.
     *
     * @see #assertThrows(Class, Executable)
     */
    public static void assertThrows(Class<? extends Throwable> expectedType, String expectedMessage,
            Executable executable) {
        Throwable thrownException = Assertions.assertThrows(expectedType, executable);
        Assertions.assertEquals(expectedMessage, thrownException.getMessage());
    }

    /**
     * Asserts that the contents of the files corresponding to {@code expectedFilePath} and {@code actualFilePath} are
     * the same.
     * @param expectedFilePath The path that corresponds to the expected file.
     * @param actualFilePath The path that corresponds to the actual file.
     */
    public static void assertFileContentEqual(Path expectedFilePath, Path actualFilePath) {
        Assertions.assertTrue(FileUtil.isFileExists(expectedFilePath) && FileUtil.isFileExists(actualFilePath));
        String expectedContents = Assertions.assertDoesNotThrow(() -> FileUtil.readFromFile(expectedFilePath));
        String actualContents = Assertions.assertDoesNotThrow(() -> FileUtil.readFromFile(actualFilePath));
        Assertions.assertEquals(expectedContents, actualContents);
    }

}
