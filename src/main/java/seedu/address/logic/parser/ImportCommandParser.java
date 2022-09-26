package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.importcommands.ImportAddCommand;
import seedu.address.logic.commands.importcommands.ImportClearCommand;
import seedu.address.logic.commands.importcommands.ImportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ImportCommand object
 */
public class ImportCommandParser implements Parser<ImportCommand> {

    private static final Pattern IMPORT_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    @Override
    public ImportCommand parse(String args) throws ParseException {
        final Matcher matcher = IMPORT_COMMAND_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = "import " + matcher.group("commandWord");
        final String arguments = matcher.group("arguments").trim();
        switch (commandWord) {

        case ImportAddCommand.COMMAND_WORD:
            return new ImportAddCommand(arguments);

        case ImportClearCommand.COMMAND_WORD:
            return new ImportClearCommand(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
