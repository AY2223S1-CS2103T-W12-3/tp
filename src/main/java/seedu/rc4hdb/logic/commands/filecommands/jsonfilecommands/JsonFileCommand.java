package seedu.rc4hdb.logic.commands.filecommands.jsonfilecommands;

import java.nio.file.Path;

import seedu.rc4hdb.logic.commands.filecommands.FileCommand;

/**
 * Encapsulates a command that targets a JSON file.
 */
public abstract class JsonFileCommand extends FileCommand {

    public static final String JSON_POSTFIX = ".json";

    public JsonFileCommand(Path dir, String fileName) {
        super(dir.resolve(fileName + JSON_POSTFIX));
    }

}