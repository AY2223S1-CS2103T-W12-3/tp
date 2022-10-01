package seedu.rc4hdb.logic.commands;

import static seedu.rc4hdb.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.rc4hdb.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.rc4hdb.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.rc4hdb.model.Model;
import seedu.rc4hdb.model.ModelManager;
import seedu.rc4hdb.model.UserPrefs;
import seedu.rc4hdb.model.person.Person;
import seedu.rc4hdb.storage.Storage;
import seedu.rc4hdb.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;
    private Storage storageStub = new CommandTestStubs.StorageStub();

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() {
        Person validPerson = new PersonBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addPerson(validPerson);

        assertCommandSuccess(new AddCommand(validPerson), model, storageStub,
                String.format(AddCommand.MESSAGE_SUCCESS, validPerson), expectedModel, storageStub);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person personInList = model.getAddressBook().getPersonList().get(0);
        assertCommandFailure(new AddCommand(personInList), model, storageStub, AddCommand.MESSAGE_DUPLICATE_PERSON);
    }

}
