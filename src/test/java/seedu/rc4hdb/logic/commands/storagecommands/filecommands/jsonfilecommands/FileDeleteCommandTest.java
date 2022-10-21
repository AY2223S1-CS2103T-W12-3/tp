package seedu.rc4hdb.logic.commands.storagecommands.filecommands.jsonfilecommands;

import static seedu.rc4hdb.logic.commands.storagecommands.StorageCommandTestUtil.assertCommandFailure;
import static seedu.rc4hdb.logic.commands.storagecommands.StorageCommandTestUtil.assertCommandSuccess;
import static seedu.rc4hdb.logic.commands.storagecommands.StorageCommandTestUtil.assertFileDoesNotExist;
import static seedu.rc4hdb.logic.commands.storagecommands.filecommands.jsonfilecommands.JsonFileCommand.JSON_POSTFIX;
import static seedu.rc4hdb.testutil.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.rc4hdb.commons.util.FileUtil;
import seedu.rc4hdb.logic.StorageStub;
import seedu.rc4hdb.logic.commands.exceptions.CommandException;
import seedu.rc4hdb.storage.JsonResidentBookStorage;
import seedu.rc4hdb.storage.JsonUserPrefsStorage;
import seedu.rc4hdb.storage.ResidentBookStorage;
import seedu.rc4hdb.storage.Storage;
import seedu.rc4hdb.storage.StorageManager;
import seedu.rc4hdb.storage.UserPrefsStorage;

/**
 * Unit tests for {@link FileDeleteCommand}.
 */
public class FileDeleteCommandTest {

    @TempDir
    public Path testFolder;

    private Storage storage;

    @BeforeEach
    public void setUp() {
        JsonResidentBookStorage residentBookStorage = new JsonResidentBookStorage(getTempJsonFilePath("test"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempJsonFilePath("testPrefs"));
        storage = new StorageManager(residentBookStorage, userPrefsStorage);
    }

    private Path getTempJsonFilePath(String fileName) {
        return testFolder.resolve(fileName + JSON_POSTFIX);
    }

    private String getTempFilePathString(String fileName) {
        return testFolder.resolve(fileName).toString();
    }

    @Test
    public void execute_fileExists_fileDeleted() throws Exception {
        ResidentBookStorage expectedResidentBookStorage =
                new JsonResidentBookStorage(storage.getResidentBookFilePath());
        UserPrefsStorage expectedUserPrefsStorage = new JsonUserPrefsStorage(storage.getUserPrefsFilePath());
        Storage expectedStorage = new StorageManager(expectedResidentBookStorage, expectedUserPrefsStorage);
        String expectedMessage = String.format(FileDeleteCommand.MESSAGE_SUCCESS, "AlreadyExists.json");

        Path filePath = getTempJsonFilePath("AlreadyExists");
        String targetFilePathString = getTempFilePathString("AlreadyExists");
        FileDeleteCommand fileDeleteCommand = new FileDeleteCommand(targetFilePathString);
        FileUtil.createFile(filePath);

        assertCommandSuccess(fileDeleteCommand, storage, expectedMessage, expectedStorage);
        assertFileDoesNotExist(filePath);
    }

    @Test
    public void execute_currentFile_throwsCommandException() throws Exception {
        Path targetFilePath = getTempJsonFilePath("test");
        String targetFilePathString = getTempFilePathString("test");

        String expectedMessage = String.format(JsonFileCommand.MESSAGE_TRYING_TO_EXECUTE_ON_CURRENT_FILE, "test.json");
        FileDeleteCommand fileDeleteCommand = new FileDeleteCommand(targetFilePathString);
        FileUtil.createIfMissing(targetFilePath);

        assertCommandFailure(fileDeleteCommand, storage, expectedMessage);
    }

    @Test
    public void execute_fileDoesNotExist_throwsCommandException() {
        String expectedMessage = String.format(FileDeleteCommand.MESSAGE_FILE_NON_EXISTENT, "DoesNotExist.json");

        String targetFilePathString = getTempFilePathString("DoesNotExist");
        FileDeleteCommand fileDeleteCommand = new FileDeleteCommand(targetFilePathString);

        assertCommandFailure(fileDeleteCommand, storage, expectedMessage);
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        String expectedMessage = String.format(FileDeleteCommand.MESSAGE_FAILED, "deleting");

        String targetFilePathString = getTempFilePathString("AlreadyExists");
        FileDeleteCommand fileDeleteCommand = new FileDeleteCommand(targetFilePathString);
        storage = new StorageStubThrowsIoException();

        assertThrows(CommandException.class, expectedMessage, () -> fileDeleteCommand.execute(storage));
    }

    /**
     * A storage stub class that throws an {@code IOException} when the
     * @see #deleteResidentBookFile(Path) method is invoked.
     */
    private static class StorageStubThrowsIoException extends StorageStub {
        public static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

        @Override
        public void deleteResidentBookFile(Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }

        @Override
        public Path getResidentBookFilePath() {
            return null;
        }

        @Override
        public Path getUserPrefsFilePath() {
            return null;
        }
    }
}
