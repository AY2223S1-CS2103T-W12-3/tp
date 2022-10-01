package seedu.rc4hdb.logic.commands;

import static seedu.rc4hdb.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.rc4hdb.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.rc4hdb.model.AddressBook;
import seedu.rc4hdb.model.Model;
import seedu.rc4hdb.model.ModelManager;
import seedu.rc4hdb.model.UserPrefs;
import seedu.rc4hdb.storage.Storage;

public class ClearCommandTest {

    private Storage storageStub = new CommandTestStubs.StorageStub();

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, storageStub, ClearCommand.MESSAGE_SUCCESS, expectedModel,
                storageStub);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setAddressBook(new AddressBook());

        assertCommandSuccess(new ClearCommand(), model, storageStub, ClearCommand.MESSAGE_SUCCESS, expectedModel,
                storageStub);
    }

}
