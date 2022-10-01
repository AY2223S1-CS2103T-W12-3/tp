package seedu.rc4hdb.logic.commands;

import seedu.rc4hdb.logic.commands.exceptions.CommandException;
import seedu.rc4hdb.model.Model;
import seedu.rc4hdb.storage.Storage;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command may operate on.
     * @param storage {@code Storage} which the command may operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute(Model model, Storage storage) throws CommandException;

}
