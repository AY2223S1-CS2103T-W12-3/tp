package seedu.rc4hdb.logic.commands;

import static seedu.rc4hdb.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.rc4hdb.logic.commands.HelpCommand.SHOWING_HELP_MESSAGE;

import org.junit.jupiter.api.Test;

import seedu.rc4hdb.model.Model;
import seedu.rc4hdb.model.ModelManager;
import seedu.rc4hdb.storage.Storage;

public class HelpCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();
    private Storage storageStub = new CommandTestStubs.StorageStub();

    @Test
    public void execute_help_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_HELP_MESSAGE, true, false);
        assertCommandSuccess(new HelpCommand(), model, storageStub, expectedCommandResult, expectedModel, storageStub);
    }
}
