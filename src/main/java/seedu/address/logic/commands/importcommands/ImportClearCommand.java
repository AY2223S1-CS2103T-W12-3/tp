package seedu.address.logic.commands.importcommands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;

/**
 * Imports data from a .csv file by clearing the existing list.
 */
public class ImportClearCommand extends ImportCommand {

    public static final String COMMAND_WORD = "import clear";

    public ImportClearCommand(String filePathString) {
        super(filePathString);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.setAddressBook(new AddressBook());

        UniquePersonList toAdd = readFile();
        for (Person person : toAdd) {
            if (!model.hasPerson(person)) {
                model.addPerson(person);
            }
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
