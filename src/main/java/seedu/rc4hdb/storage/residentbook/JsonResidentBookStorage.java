package seedu.rc4hdb.storage.residentbook;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
import seedu.rc4hdb.commons.core.LogsCenter;
import seedu.rc4hdb.commons.exceptions.DataConversionException;
import seedu.rc4hdb.commons.exceptions.IllegalValueException;
import seedu.rc4hdb.commons.util.FileUtil;
import seedu.rc4hdb.commons.util.JsonUtil;
import seedu.rc4hdb.model.ReadOnlyResidentBook;
import seedu.rc4hdb.model.ResidentBook;
import seedu.rc4hdb.ui.ObservableItem;

/**
 * A class to access ResidentBook data stored as a json file on the hard disk.
 */
public class JsonResidentBookStorage implements ResidentBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonResidentBookStorage.class);

    private ObservableItem<Path> filePath;

    public JsonResidentBookStorage(Path filePath) {
        this.filePath = new ObservableItem<>(filePath);
    }

    public Path getResidentBookFilePath() {
        return filePath.getValue();
    }

    @Override
    public ObservableValue<Path> getObservableResidentBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyResidentBook> readResidentBook() throws DataConversionException {
        return readResidentBook(filePath.getValue());
    }

    /**
     * Similar to {@link #readResidentBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyResidentBook> readResidentBook(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableResidentBook> jsonResidentBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableResidentBook.class);
        if (!jsonResidentBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonResidentBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    public void saveResidentBook(ReadOnlyResidentBook residentBook) throws IOException {
        saveResidentBook(residentBook, filePath.getValue());
    }

    /**
     * Similar to {@link #saveResidentBook(ReadOnlyResidentBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveResidentBook(ReadOnlyResidentBook residentBook, Path filePath) throws IOException {
        requireNonNull(residentBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableResidentBook(residentBook), filePath);
    }

    /**
     * Deletes the resident book data file corresponding to {@code filePath}.
     *
     * @param filePath path of the data file to be deleted. Cannot be null.
     */
    public void deleteResidentBookFile(Path filePath) throws IOException {
        requireNonNull(filePath);
        FileUtil.deleteFile(filePath);
    }

    /**
     * Creates the resident book data file corresponding to {@code filePath}.
     *
     * @param filePath path of the data file to be created. Cannot be null.
     */
    public void createResidentBookFile(Path filePath) throws IOException {
        requireNonNull(filePath);
        FileUtil.createFile(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableResidentBook(new ResidentBook()), filePath);
    }

    @Override
    public void setResidentBookFilePath(Path filePath) {
        requireNonNull(filePath);
        this.filePath.setValue(filePath);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof JsonResidentBookStorage)) {
            return false;
        }

        // state check
        JsonResidentBookStorage other = (JsonResidentBookStorage) obj;
        return filePath.equals(other.filePath);
    }

}