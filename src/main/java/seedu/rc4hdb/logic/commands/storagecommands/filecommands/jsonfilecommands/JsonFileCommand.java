package seedu.rc4hdb.logic.commands.storagecommands.filecommands.jsonfilecommands;

import java.nio.file.Path;

import seedu.rc4hdb.logic.commands.storagecommands.filecommands.FileCommand;

/**
 * Encapsulates a command that targets a JSON file.
 */
public abstract class JsonFileCommand extends FileCommand {

    public static final String JSON_POSTFIX = ".json";

    public JsonFileCommand(String filePathString) {
        super(Path.of(filePathString + JSON_POSTFIX));
    }

}
