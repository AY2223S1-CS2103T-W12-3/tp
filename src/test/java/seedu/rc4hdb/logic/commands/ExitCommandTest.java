package seedu.rc4hdb.logic.commands;

import static seedu.rc4hdb.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.rc4hdb.logic.commands.ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT;

import org.junit.jupiter.api.Test;

import seedu.rc4hdb.model.Model;
import seedu.rc4hdb.model.ModelManager;
import seedu.rc4hdb.storage.Storage;

public class ExitCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();
    private Storage storageStub = new CommandTestStubs.StorageStub();

    @Test
    public void execute_exit_success() {
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true);
        assertCommandSuccess(new ExitCommand(), model, storageStub, expectedCommandResult, expectedModel, storageStub);
    }
}
