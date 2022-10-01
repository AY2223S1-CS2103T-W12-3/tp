package seedu.rc4hdb.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.rc4hdb.model.AddressBook;
import seedu.rc4hdb.model.Model;
import seedu.rc4hdb.storage.Storage;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";


    @Override
    public CommandResult execute(Model model, Storage storage) {
        requireNonNull(model);
        model.setAddressBook(new AddressBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
