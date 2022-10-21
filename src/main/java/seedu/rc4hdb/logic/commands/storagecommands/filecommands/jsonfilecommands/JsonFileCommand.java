package seedu.rc4hdb.logic.commands.storagecommands.filecommands.jsonfilecommands;

import java.nio.file.Path;

import seedu.rc4hdb.logic.commands.storagecommands.filecommands.FileCommand;

/**
 * Encapsulates a command that targets a file.
 */
public abstract class JsonFileCommand extends FileCommand {

    public static final String JSON_POSTFIX = ".json";

    public static final String MESSAGE_TRYING_TO_EXECUTE_ON_CURRENT_FILE = "%s is the current working data file. "
            + "Try another file.";

    public JsonFileCommand(String fileName) {
        super(Path.of(fileName + JSON_POSTFIX));
    }

}
